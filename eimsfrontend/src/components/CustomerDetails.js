import React, { useEffect, useState, useRef } from 'react';
import { useNavigate, useLocation } from "react-router-dom";
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import '../css/machine.css'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Modal } from 'react-bootstrap'
import axios from 'axios';
//Toast
import { ToastContainer, toast } from 'react-toastify';

function CustomerDetails(props) {
    const { children, value, index, ...other } = props;
    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

CustomerDetails.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

export default function BasicTabs() {
    //Dependency
    const [addressLoaded, setAddressLoaded] = useState(false);
    const [addressValLoaded, setAddressValLoaded] = useState(false);
    const [customerLoaded, setCustomerLoaded] = useState(false);

    //URL
    const CUSTOMER_UPDATE_SAVE = "/api/customer/update/save";
    const CUSTOMER_UPDATE_GET = "/api/customer/update/get";

    const userRef = useRef();
    const [value, setValue] = React.useState(0);
    const navigate = useNavigate();
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    //DTO
    const [updateCustomerDTO, setUpdateCustomerDTO] = useState({
        userId: sessionStorage.getItem("curUserId"),
        customerId: "",
        customerName: "",
        customerPhone: "",
        customerAddress: "",
        customerMail: "",
        status: ""
    })

    //Address consts
    //Full Json addresses
    const [fullAddresses, setFullAddresses] = useState('');
    const [city, setCity] = useState([
        { value: '', label: 'Chọn Tỉnh/Thành phố' }
    ]);
    const [district, setDistrict] = useState(''); //For populate dropdowns
    const [ward, setWard] = useState('');
    const [cityIndex, setCityIndex] = useState(); //Save the index of selected dropdowns
    const [districtIndex, setDistrictIndex] = useState();
    const [wardIndex, setWardIndex] = useState();
    const [street, setStreet] = useState();

    // Set value for address fields
    //User
    useEffect(() => {
        loadAddress();
    }, [addressLoaded]);

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
        setAddressLoaded(true);
    }

    //Get sent params
    const { state } = useLocation();
    const { id } = state;
    const [addressJson, setAddressJson] = useState({
        city: "",
        district: "",
        ward: "",
        street: ""
    });

    const [updateAddressJson, setUpdateAddressJson] = useState({
        city: "",
        district: "",
        ward: "",
        street: ""
    });

    //Get customer details
    useEffect(() => {
        console.log("Get customer");
        loadCustomer();
    }, [customerLoaded]);

    const loadCustomer = async () => {
        try {
            const result = await axios.get(CUSTOMER_UPDATE_GET,
                {
                    params: { customerId: id },
                    withCredentials: true
                });
            // Set inf
            setAddressJson(JSON.parse(result.data.customerAddress));
            updateCustomerDTO.userId = sessionStorage.getItem("curUserId");
            updateCustomerDTO.customerId = result.data.customerId;
            updateCustomerDTO.customerName = result.data.customerName;
            updateCustomerDTO.customerPhone = result.data.customerPhone;
            updateCustomerDTO.customerAddress = result.data.customerAddress;
            updateCustomerDTO.customerMail = result.data.customerMail;
            updateCustomerDTO.status = result.data.status;
            setCustomerLoaded(true);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi')
            } else {
                if ((err.response.data === null) || (err.response.data === '') ) {
          toast.error('Có lỗi xảy ra, vui lòng thử lại');
        } else {
          toast.error(err.response.data);
        }
            }
        }

    }

    const handleUpdateClick = () => {
        handleShow();
        // Get index of dropdowns
        console.log(addressJson);
        for (let i in city) {
            console.log(i);
            if (addressJson.city === city[i].label) {
                setCityIndex(i);
                addressJson.city = fullAddresses[i].Name;
                console.log("City " + i);
                setCityIndex(i);
                const districtOnIndex = fullAddresses[i].Districts;
                const districtList = districtOnIndex.slice();
                for (let i in districtList) {
                    districtList[i] = { value: districtList[i].Id, label: districtList[i].Name }
                }
                setDistrict(districtList);
                for (let j in districtList) {
                    if (addressJson.district === districtList[j].label) {
                        setDistrictIndex(j);
                        console.log(j);
                        addressJson.district = fullAddresses[i].Districts[j].Name;
                        console.log("District " + j);
                        setDistrictIndex(j);
                        const wardOnIndex = fullAddresses[i].Districts[j].Wards;
                        const wardList = wardOnIndex.slice();
                        for (let i in wardList) {
                            wardList[i] = { value: wardList[i].Id, label: wardList[i].Name }
                        }
                        setWard(wardList);
                        for (let k in wardList) {
                            if (addressJson.ward === wardList[k].label) {
                                setWardIndex(k);
                                addressJson.ward = fullAddresses[i].Districts[j].Wards[k].Name;
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        setStreet(addressJson.street);
        setUpdateAddressJson(addressJson);
    }

    //Function for populating dropdowns
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
    function loadWard(districtIndex, cIndex) {
        if (cIndex === -1) {
            cIndex = cityIndex;
        }
        console.log("District " + districtIndex);
        setDistrictIndex(districtIndex);
        const wardOnIndex = fullAddresses[cIndex].Districts[districtIndex].Wards;
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
        setUpdateAddressJson({
            city: fullAddresses[cityIndex].Name,
            district: fullAddresses[cityIndex].Districts[districtIndex].Name,
            ward: fullAddresses[cityIndex].Districts[districtIndex].Wards[wardIndex].Name,
            street: s
          });
        updateCustomerDTO.customerAddress = JSON.stringify(updateAddressJson);
    }

    //Handle Change functions:
    //Update Customer
    const handleUpdateCustomerChange = (event, field) => {
        let actualValue = event.target.value
        setUpdateCustomerDTO({
            ...updateCustomerDTO,
            [field]: actualValue
        })
    }

    //Cancel update: Revert changes
    const handleCancel = () => {
        handleClose();
        setUpdateAddressJson(addressJson);
        setStreet(addressJson.street);
    }

    //Handle Submit functions
    //Handle submit update customer
    const handleUpdateCustomerSubmit = async (event) => {
        event.preventDefault();
        saveAddressJson(street);
        let response;
        try {
            response = await axios.put(CUSTOMER_UPDATE_SAVE,
                updateCustomerDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            loadCustomer();
            console.log(response);
            toast.success(response.data);
            setShow(false);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                if ((err.response.data === null) || (err.response.data === '') ) {
          toast.error('Có lỗi xảy ra, vui lòng thử lại');
        } else {
          toast.error(err.response.data);
        }
            }
        }
    }

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                <Tabs sx={{
                    '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
                    '& .Mui-selected': { color: "#d25d19" },
                }} value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Khách hàng" {...a11yProps(0)} />
                    <Tab style={{ textTransform: "capitalize" }} label="Trở về trang Đơn hàng" {...a11yProps(1)} onClick={() => navigate("/order")} />
                </Tabs>
            </Box>
            <CustomerDetails value={value} index={0}>
                <div className='container'>
                    <h3 style={{ textAlign: "center" }}>Thông tin Khách hàng</h3>
                    <Modal show={show} onHide={handleClose}
                        size="lg"
                        aria-labelledby="contained-modal-title-vcenter"
                        centered >
                        <form onSubmit={handleUpdateCustomerSubmit}>
                            <Modal.Header closeButton onClick={handleClose}>
                                <Modal.Title>Thông tin khách hàng</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <div className="">
                                <div className="row">
                                    <div className="col-md-4">
                                        <label htmlFor='customerName' className='col-form-label'>Họ và tên&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-8">
                                        <input id="customerName"
                                            className="form-control mt-1"
                                            style={{ width: "100%" }}
                                            required
                                            placeholder='Tên/biệt danh gợi nhớ'
                                            value={updateCustomerDTO.customerName}
                                            onChange={e => handleUpdateCustomerChange(e, "customerName")} />
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
                                            value={updateCustomerDTO.customerPhone}
                                            onChange={e => handleUpdateCustomerChange(e, "customerPhone")} />
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
                                            value={updateCustomerDTO.customerMail}
                                            onChange={e => handleUpdateCustomerChange(e, "customerEmail")} />
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
                                            required>
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
                                            required>
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
                                            required>
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
                                            required
                                            className="form-control mt-1"
                                            value={street}
                                            placeholder='Địa chỉ cụ thể' />
                                    </div>
                                </div>
                                <div className="row">
                                        <div className="col-md-4">
                                            <label htmlFor='updateCustomerStatus'>Trạng thái&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                        </div>
                                        <div className="col-md-8">
                                            <select id="updateCustomerStatus" class="form-select mt-1" aria-label="Default select example"
                                                onChange={(e) => handleUpdateCustomerChange(e, "status")}>
                                                <option value="1" className='text-green'>Đang hoạt động</option>
                                                <option value="0" className='text-red'>Ngừng hoạt động</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </Modal.Body>
                            <div className='model-footer'>
                                <button id="confirmUpdateCustomer" style={{ width: "30%" }} className="col-md-6 btn-light" type='submit'>
                                    Cập nhật
                                </button>
                                <button id="cancelUpdateCustomer" style={{ width: "20%" }} className="btn btn-light" onClick={handleCancel} type='button'>
                                    Huỷ
                                </button>
                            </div>
                        </form>
                    </Modal>
                    <div className='detailbody'>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Họ và tên</p>
                            </div>
                            <div className="col-md-4">
                                <p>{updateCustomerDTO.customerName}</p>
                            </div>
                            <div className="col-md-4 ">
                                <div className='button'>
                                    <button id="startEditCustomer" className='btn btn-light ' onClick={handleUpdateClick}>Sửa</button>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Số điện thoại</p>
                            </div>
                            <div className="col-md-4">
                                <p>{updateCustomerDTO.customerPhone}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Email</p>
                            </div>
                            <div className="col-md-4">
                                <p>{updateCustomerDTO.customerMail}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Địa chỉ</p>
                            </div>
                            <div className="col-md-4">
                                <p>{addressJson.street + ", " + addressJson.ward + ", " + addressJson.district + ", " + addressJson.city}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Trạng thái</p>
                            </div>
                            <div className="col-md-4">
                                {updateCustomerDTO.status === 1
                                    ? <p className='text-green'>
                                        Đang hoạt động
                                    </p>
                                    : <p className='text-red'>
                                        Ngừng hoạt động
                                    </p>
                                }
                            </div>
                        </div>
                    </div>
                </div>
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
            </CustomerDetails>
        </Box>

    );
}