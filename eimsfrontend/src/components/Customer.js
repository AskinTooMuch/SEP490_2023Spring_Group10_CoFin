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
import { toast } from 'react-toastify';

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
    const CUSTOMER_CREATE = "/api/customer/create";


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
        loadAddress();
    }, [dataLoaded]);

    const loadAddress = async () => {
        const result = await axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
            {});
        setFullAddresses(result.data);
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
        if (cityIndex >= 0) {
            addressJson.city = fullAddresses[cityIndex].Name;
        }
        setStreet(s);
        if (districtIndex >= 0) {
            addressJson.district = fullAddresses[cityIndex].Districts[districtIndex].Name;
        }
        setStreet(s);
        if (wardIndex >= 0) {
            addressJson.ward = fullAddresses[cityIndex].Districts[districtIndex].Wards[wardIndex].Name;
        }
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
                    withCredentials: true
                }
            );
            setCreateCustomerDTO({
                userId: sessionStorage.getItem("curUserId"),
                customerName: "",
                customerPhone: "",
                customerAddress: "",
                customerMail: ""
            });
            setAddressJson({
                city: "",
                district: "",
                ward: "",
                street: ""
              });

            console.log(response);
            loadCustomerList();
            toast.success(response.data);
            setShow(false);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                if ((err.response.data === null) || (err.response.data === '')) {
                    toast.error('Có lỗi xảy ra, vui lòng thử lại');
                } else {
                    toast.error(err.response.data);
                }
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
        try {
            const result = await axios.get(CUSTOMER_ALL,
                {
                    params: { userId: sessionStorage.getItem("curUserId") },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setCustomerList(result.data);
            console.log(customerList);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                if ((err.response.data === null) || (err.response.data === '')) {
                    toast.error('Có lỗi xảy ra, vui lòng thử lại');
                } else {
                    toast.error(err.response.data);
                }
            }
        }
    }

    //Navigate to detail Page
    let navigate = useNavigate();
    const routeChange = (cid) => {
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
                                    <div className="col-md-4">
                                        <label htmlFor='customerName' className='col-form-label'>Họ và tên&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-8">
                                        <input id="customerName"
                                            className="form-control mt-1"
                                            style={{ width: "100%" }}
                                            placeholder='Tên/biệt danh gợi nhớ'
                                            onChange={e => handleCreateCustomerChange(e, "customerName")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-4">
                                        <label htmlFor='customerPhoneNumber' className='col-form-label'>Điện thoại&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-8">
                                        <input id="customerPhoneNumber"
                                            className="form-control mt-1"
                                            style={{ width: "100%" }}
                                            placeholder='Số điện thoại Việt Nam'
                                            onChange={e => handleCreateCustomerChange(e, "customerPhone")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-4">
                                        <label htmlFor='customerEmail' className='col-form-label'>Email</label>
                                    </div>
                                    <div className="col-md-8">
                                        <input id="customerEmail"
                                            className="form-control mt-1"
                                            style={{ width: "100%" }}
                                            placeholder='Địa chỉ thư điện tử'
                                            onChange={e => handleCreateCustomerChange(e, "customerMail")} />
                                    </div>
                                </div>
                                <div className='row'>
                                    {/*City*/}
                                    <div className="col-md-4">
                                        <label htmlFor="uprovince" className='col-form-label'>Tỉnh/Thành Phố&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-8">
                                        <select className="form-control mt-1" id="uprovince"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => loadDistrict(e.target.value)}
                                            value={cityIndex}
                                        >
                                            <option value="" disabled selected>Chọn Tỉnh/Thành phố</option>
                                            {city &&
                                                city.map((item, index) => (
                                                    <option value={index}>{item.label}</option>
                                                ))
                                            }
                                        </select>
                                    </div>
                                </div>
                                <div className='row'>
                                    {/*District*/}
                                    <div className="col-md-4">
                                        <label htmlFor="udistrict" className='col-form-label'>Quận/Huyện&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-8">
                                        <select className="form-control mt-1" id="udistrict" name="supplierDistrict"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => loadWard(e.target.value)}
                                            value={districtIndex}
                                        >
                                            <option value="" disabled selected>Chọn Quận/Huyện</option>
                                            {district &&
                                                district.map((item, index) => (
                                                    <option value={index}>{item.label}</option>
                                                ))
                                            }
                                        </select>
                                    </div>
                                </div>
                                <div className='row'>
                                    {/*User ward*/}
                                    <div className="col-md-4">
                                        <label htmlFor="uward" className='col-form-label'>Phường/Xã&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-8">
                                        <select className="form-control mt-1" id="uward" name="supplierWard"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => saveWard(e.target.value)}
                                            value={wardIndex}
                                        >
                                            <option value="" disabled selected>Chọn Phường/Xã</option>
                                            {ward &&
                                                ward.map((item, index) => (
                                                    <option value={index}>{item.label}</option>
                                                ))
                                            }
                                        </select>
                                    </div>
                                </div>
                                <div className='row'>
                                    {/*User Street*/}
                                    <div className="col-md-4">
                                        <label htmlFor="uhomenum" className='col-form-label'>Số nhà&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-8">
                                        <input type="text" id="uhomenum"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => saveAddressJson(e.target.value)}
                                            className="form-control mt-1"
                                            placeholder='Địa chỉ cụ thể' />
                                    </div>
                                </div>
                            </div>
                        </Modal.Body>
                        <div className='model-footer'>
                            <button id="confirmCreateCustomer" style={{ width: "20%" }} type="submit" className="col-md-6 btn-light" >
                                Tạo
                            </button>
                            <button id="cancelCreateCustomer" className='btn btn-light' style={{ width: "20%" }} onClick={handleClose} type='button'>
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
            
        </div>
    );
}

export default Customer;