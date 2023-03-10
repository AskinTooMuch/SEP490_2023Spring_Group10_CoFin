import React, { useState, useEffect, useRef } from 'react';
import '../css/machine.css'
import { useNavigate } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import SearchIcon from '@mui/icons-material/Search';
import { Modal } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from 'axios';
//Toast
import { ToastContainer, toast } from 'react-toastify';

const Customer = () => {
    const userRef = useRef();
    const [dataLoaded, setDataLoaded] = useState(false);
    //Show-hide Popup
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    //Data holding objects
    const [customerList, setCustomerList] = useState([]);
    //Address consts
    //Full Json addresses
    const [fullAddresses, setFullAddresses] = useState('');
    const [city, setCity] = useState([
        { value: '', label: 'Chọn Tỉnh/Thành phố' }
    ]);
    //Customer Address data
    const [district, setDistrict] = useState(''); //For populate dropdowns
    const [ward, setWard] = useState('');
    const [cityIndex, setCityIndex] = useState(); //Save the index of selected dropdowns
    const [districtIndex, setDistrictIndex] = useState();
    const [wardIndex, setWardIndex] = useState();
    const [street, setStreet] = useState();
    const [addressJson, setAddressJson] = useState({
        city: "",
        district: "",
        ward: "",
        street: ""
    });

    //API URLs
    const CUSTOMER_ALL = '/api/customer/all';
    const CUSTOMER_DETAIL = "/api/customer/get";
    const CUSTOMER_CREATE = "/api/customer/create";
    const CUSTOMER_UPDATE_GET = "/api/customer/update/get";
    const CUSTOMER_UPDATE_SAVE = "/api/customer/update/save";
    const CUSTOMER_DELETE = "/api/customer/delete";
    const CUSTOMER_SEARCH = "/api/customer/search";


    //DTOs
    //CreateSupplierDTO
    const [createCustomerDTO, setCreateCustomerDTO] = useState({
        userId: sessionStorage.getItem("curUserId"),
        customerName: "",
        customerPhone: "",
        customerAddress: "",
        customerMail: ""
    })

    //Handle Change functions:
    //CreateCustomer
    const handleCreateCustomerChange = (event, field) => {
        let actualValue = event.target.value
        setCreateCustomerDTO({
            ...createCustomerDTO,
            [field]: actualValue
        })
    }

    // Set value for address fields
    //User
    useEffect(() => {
        console.log("Load address");
        loadAddress();
    }, [dataLoaded]);

    const loadAddress = async () => {
        const result = await axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
            {});
        setFullAddresses(result.data);
        console.log("Full address");
        console.log(fullAddresses);
        // Set inf

        const cityList = fullAddresses.slice();
        for (let i in cityList) {
            cityList[i] = { value: cityList[i].Id, label: cityList[i].Name }
        }
        setCity(cityList);
        setDataLoaded(true);
    }

    function loadDistrict(index) {
        console.log("City " + index);
        setCityIndex(index);
        const districtOnIndex = fullAddresses[index].Districts;
        const districtList = districtOnIndex.slice();
        for (let i in districtList) {
            districtList[i] = { value: districtList[i].Id, label: districtList[i].Name }
        }
        setDistrict(districtList);
    }

    //Load user ward list
    function loadWard(index) {
        console.log("District " + index);
        setDistrictIndex(index);
        const wardOnIndex = fullAddresses[cityIndex].Districts[index].Wards;
        const wardList = wardOnIndex.slice();
        for (let i in wardList) {
            wardList[i] = { value: wardList[i].Id, label: wardList[i].Name }
        }
        setWard(wardList);
    }

    function saveWard(index) {
        console.log("Ward " + index);
        setWardIndex(index);
    }

    function saveAddressJson(s) {
        setStreet(s);
        addressJson.city = fullAddresses[cityIndex].Name;
        addressJson.district = fullAddresses[cityIndex].Districts[districtIndex].Name;
        addressJson.ward = fullAddresses[cityIndex].Districts[districtIndex].Wards[wardIndex].Name;
        addressJson.street = street;
        createCustomerDTO.customerAddress = JSON.stringify(addressJson);
    }


    //Handle Submit functions
    //Handle submit new Customer
    const handleCreateCustomerSubmit = async (event) => {
        event.preventDefault();
        saveAddressJson(street);
        let response;
        try {
            response = await axios.post(CUSTOMER_CREATE,
                createCustomerDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: false
                }
            );
            setCreateCustomerDTO({
                userId: sessionStorage.getItem("curUserId"),
                customerName: "",
                customerPhone: "",
                customerAddress: "",
                customerMail: ""
            });
            console.log(response);
            loadCustomerList();
            toast.success("Tạo thành công");
            setShow(false);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                toast.error(response);
            }
        }
    }


    // Get list of Customer and show
    // Get Customer list
    useEffect(() => {
        loadCustomerList();
    }, []);



    // Request Customer list and load the customer list into the table rows
    const loadCustomerList = async () => {
        const result = await axios.get(CUSTOMER_ALL,
            { params: { userId: sessionStorage.getItem("curUserId") } },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: false
            });
        setCustomerList(result.data);
        console.log(customerList);
    }


    //Navigate to detail Page
    let navigate = useNavigate();
    const routeChange = (cid) => {
        //let path = '/customerdetail';
        navigate('/customerdetail', { state: { id: cid } });
    }
    return (
        <div>
            <nav className="navbar justify-content-between">
                <button className='btn btn-light' onClick={handleShow}>+ Thêm</button>
                {/* Start: form to add new customer */}
                <Modal show={show} onHide={handleClose}
                    size="lg" aria-labelledby="contained-modal-title-vcenter" centered >
                    <form onSubmit={handleCreateCustomerSubmit}>
                        <Modal.Header closeButton onClick={handleClose}>
                            <Modal.Title>Thêm khách hàng</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div className="changepass">
                                <div className="row">
                                    <div className="col-md-3">
                                        <p>Họ và tên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-3">
                                        <input className="form-control mt-1" style={{ width: "100%" }} onChange={e => handleCreateCustomerChange(e, "customerName")} />
                                    </div>
                                    {/*City*/}
                                    <div className="col-md-3">
                                        <label htmlFor="uprovince" >Tỉnh/Thành Phố <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-3">
                                        <select className="form-control mt-1" id="uprovince"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => loadDistrict(e.target.value)}
                                            value={cityIndex}
                                            required>
                                            <option value="" disabled>Chọn Tỉnh/Thành phố</option>
                                            {city &&
                                                city.map((item, index) => (
                                                    <option value={index}>{item.label}</option>
                                                ))
                                            }
                                        </select>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-3">
                                        <p>Số điện thoại<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-3">
                                        <input className="form-control mt-1" style={{ width: "100%" }} onChange={e => handleCreateCustomerChange(e, "customerPhone")} />
                                    </div>
                                    {/*District*/}
                                    <div className="col-md-3">
                                        <label htmlFor="udistrict" >Quận/Huyện <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-3">
                                        <select className="form-control mt-1" id="udistrict"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => loadWard(e.target.value)}
                                            value={districtIndex}
                                            required>
                                            <option value="" disabled>Chọn Quận/Huyện</option>
                                            {district &&
                                                district.map((item, index) => (
                                                    <option value={index}>{item.label}</option>
                                                ))
                                            }
                                        </select>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-3">
                                        <p>Email</p>
                                    </div>
                                    <div className="col-md-3">
                                        <input className="form-control mt-1" style={{ width: "100%" }} onChange={e => handleCreateCustomerChange(e, "customerMail")} />
                                    </div>
                                    {/*User ward*/}
                                    <div className="col-md-3">
                                        <label htmlFor="uward" >Phường/Xã <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-3">
                                        <select className="form-control mt-1" id="uward"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => saveWard(e.target.value)}
                                            value={wardIndex}
                                            required>
                                            <option value="" disabled>Chọn Phường/Xã</option>
                                            {ward &&
                                                ward.map((item, index) => (
                                                    <option value={index}>{item.label}</option>
                                                ))
                                            }
                                        </select>
                                    </div>
                                </div>
                                <div className="row">
                                    {/*User Street*/}
                                    <div className="col-md-6">
                                    </div>
                                    <div className="col-md-3">
                                        <label htmlFor="uhomenum">Số nhà <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-3">
                                        <input type="text" id="uhomenum"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => saveAddressJson(e.target.value)}
                                            required
                                            className="form-control " />
                                    </div>
                                </div>
                            </div>
                        </Modal.Body>
                        <div className='model-footer'>
                            <button style={{ width: "20%" }} type="submit" className="col-md-6 btn-light" >
                                Tạo
                            </button>
                            <button className='btn btn-light' style={{ width: "20%" }} onClick={handleClose} type='button'>
                                Huỷ
                            </button>
                        </div>
                    </form>
                </Modal>

                {/* End: form to add new customer */}
                {/* Start: Filter and sort table */}
                <div className='filter my-2 my-lg-0'>
                    <p><FilterAltIcon />Lọc</p>
                    <p><ImportExportIcon />Sắp xếp</p>
                    <form className="form-inline">
                        <div className="input-group">
                            <div className="input-group-prepend">
                                <button ><span className="input-group-text" ><SearchIcon /></span></button>
                            </div>
                            <input type="text" className="form-control" placeholder="Tìm kiếm" aria-label="Username" aria-describedby="basic-addon1" />
                        </div>
                    </form>
                </div>
                {/* End: Filter and sort table */}
            </nav>
            {/* Start: Table for customer list */}
            <div>
                <table className="table table-bordered">
                    <thead>
                        <tr>
                            <th scope="col">STT</th>
                            <th scope="col">Khách hàng</th>
                            <th scope="col">Số điện thoại</th>
                            <th scope="col">Doanh thu theo tháng</th>
                            <th scope="col">Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            customerList && customerList.length > 0 ?
                                customerList.map((item, index) =>
                                    <tr className='trclick' onClick={() => routeChange(item.customerId)} key={item.userId}>
                                        <th scope="row">{index + 1}</th>
                                        <td>{item.customerName}</td>
                                        <td>{item.customerPhone}</td>
                                        <td>100.000.000 VNĐ</td>
                                        {item.status === 1
                                            ? <td className='text-green'>
                                                Đang hoạt động
                                            </td>
                                            : <td className='text-red'>
                                                Ngừng hoạt động
                                            </td>
                                        }
                                    </tr>
                                ) : 'Loading'
                        }
                    </tbody>
                </table>
            </div>
            {/* End: Table for customer list */}
            <ToastContainer position="top-left"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="colored" />
        </div>
    );
}

export default Customer;