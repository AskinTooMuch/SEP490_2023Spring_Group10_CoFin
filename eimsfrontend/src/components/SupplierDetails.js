import React, { useEffect, useState, useRef } from 'react';
import { useNavigate, useParams, useLocation } from "react-router-dom";
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import '../css/machine.css'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Modal, Button } from 'react-bootstrap'
import axios from 'axios';
//Toast
import { ToastContainer, toast } from 'react-toastify';

function SupplierDetails(props) {
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

SupplierDetails.propTypes = {
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
    const [supplierLoaded, setSupplierLoaded] = useState(false);

    //URL
    const SUPPLIER_UPDATE = "/api/supplier/update/save";
    const SUPPLIER_GET = "/api/supplier/get";
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
    const [updateSupplierDTO, setUpdateSupplierDTO] = useState({
        userId: sessionStorage.getItem("curUserId"),
        supplierId: "",
        supplierName: "",
        facilityName: "",
        supplierPhone: "",
        supplierAddress: "",
        supplierMail: "",
        fertilizedRate: "",
        maleRate: "",
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
    //Get supplier details
    useEffect(() => {
        console.log("Get Supplier");
        loadSupplier();
    }, [addressLoaded]);

    const loadSupplier = async () => {
        try {
            const result = await axios.get(SUPPLIER_GET,
                {
                    params: { supplierId: id },
                    withCredentials: true
                });
            // Set inf
            setAddressJson(JSON.parse(result.data.supplierAddress));
            updateSupplierDTO.userId = sessionStorage.getItem("curUserId");
            updateSupplierDTO.supplierId = result.data.supplierId;
            updateSupplierDTO.supplierName = result.data.supplierName;
            updateSupplierDTO.facilityName = result.data.facilityName;
            updateSupplierDTO.supplierPhone = result.data.supplierPhone;
            updateSupplierDTO.supplierAddress = result.data.supplierAddress;
            updateSupplierDTO.supplierMail = result.data.supplierMail;
            updateSupplierDTO.fertilizedRate = result.data.fertilizedRate;
            updateSupplierDTO.maleRate = result.data.maleRate;
            updateSupplierDTO.status = result.data.status;
            setSupplierLoaded(true);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                toast.error(err.response.data);
            }
        }
    }

    const handleUpdateClick = () => {
        handleShow();
        // Get index of dropdowns
        console.log(addressJson);
        for (let i in city) {
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
        console.log("ward " + wardIndex);
        setStreet(s);
        addressJson.city = fullAddresses[cityIndex].Name;
        addressJson.district = fullAddresses[cityIndex].Districts[districtIndex].Name;
        addressJson.ward = fullAddresses[cityIndex].Districts[districtIndex].Wards[wardIndex].Name;
        addressJson.street = street;
        updateSupplierDTO.supplierAddress = JSON.stringify(addressJson);
    }

    //Handle Change functions:
    //CreateSupplier
    const handleUpdateSupplierChange = (event, field) => {
        let actualValue = event.target.value
        setUpdateSupplierDTO({
            ...updateSupplierDTO,
            [field]: actualValue
        })
    }

    //Handle Submit functions
    //Handle submit new supplier
    const handleUpdateSupplierSubmit = async (event) => {
        event.preventDefault();
        saveAddressJson(street);
        let response;
        try {
            response = await axios.put(SUPPLIER_UPDATE,
                updateSupplierDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            //loadSupplier(id);
            console.log(response);
            toast.success("Cập nhật thành công");
            setShow(false);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                toast.error(response);
            }
        }
    }

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Nhà cung cấp" {...a11yProps(0)} />
                    <Tab style={{ textTransform: "capitalize" }} label="Trở về trang đơn hàng" {...a11yProps(1)} onClick={() => navigate("/order")} />
                </Tabs>
            </Box>
            <SupplierDetails value={value} index={0}>
                <div className='container'>
                    <h3 style={{ textAlign: "center" }}>Thông tin chi tiết nhà cung cấp</h3>
                    <form><Modal show={show} onHide={handleClose}
                        size="lg"
                        aria-labelledby="contained-modal-title-vcenter"
                        centered >
                        <Modal.Header closeButton onClick={handleClose}>
                            <Modal.Title>Sửa thông tin nhà cung cấp</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div className="">
                                <div className="row">
                                    <div className="col-md-4">
                                        <label htmlFor='updateSupplierName' className='col-form-label'>Họ và tên&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-8">
                                        <input required id="updateSupplierName"
                                            value={updateSupplierDTO.supplierName}
                                            onChange={(e) => handleUpdateSupplierChange(e, "supplierName")}
                                            className="form-control mt-1"
                                            placeholder='Tên/biệt danh gợi nhớ'/>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-4">
                                    <label htmlFor='updateSupplierPhoneNumber' className='col-form-label'>Số điện thoại&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-8">
                                        <input required id="updateSupplierPhoneNumber"
                                            value={updateSupplierDTO.supplierPhone}
                                            onChange={(e) => handleUpdateSupplierChange(e, "supplierPhone")}
                                            className="form-control mt-1"
                                            placeholder='Số điện thoại Việt Nam'/>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-4">
                                    <label htmlFor='updateSupplierEmail' className='col-form-label'>Email</label>
                                    </div>
                                    <div className="col-md-8">
                                        <input id="updateSupplierEmail"
                                            value={updateSupplierDTO.supplierMail}
                                            onChange={(e) => handleUpdateSupplierChange(e, "supplierMail")}
                                            className="form-control mt-1"
                                            placeholder='Địa chỉ thư điện tử'/>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-4">
                                        <label htmlFor='updateSupplierFacilityName' className='col-form-label'>Tên cơ sở&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife}/></label>
                                    </div>
                                    <div className="col-md-8">
                                        <input required id="updateSupplierFacilityName"
                                            value={updateSupplierDTO.facilityName}
                                            onChange={(e) => handleUpdateSupplierChange(e, "facilityName")}
                                            className="form-control mt-1"
                                            placeholder='Tên cơ sở cung cấp'/>
                                    </div>
                                </div>
                                {/*City*/}
                                <div className="row">
                                    <div className="col-md-4">
                                        <label htmlFor='uprovince' className='col-form-label'>Thành phố&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife}/></label>
                                    </div>
                                    <div className="col-md-8">
                                        <select className="form-control mt-1" id="uprovince"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => loadDistrict(e.target.value)}
                                            value={cityIndex}
                                            required>
                                            <option value="" disabled>Chọn Tỉnh/Thành phố</option>
                                            {city &&
                                                city.map((item, index) => (
                                                    <>
                                                        {item.label === addressJson.city
                                                            ? <option value={index} selected>{item.label}</option>
                                                            : <option value={index}>{item.label}</option>
                                                        }
                                                    </>
                                                ))
                                            }
                                        </select>
                                    </div>
                                </div>
                                {/*District*/}
                                <div className="row">
                                    <div className="col-md-4">
                                        <label htmlFor='udistrict' className='col-form-label'>Quận/Huyện&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife}/></label>
                                    </div>
                                    <div className="col-md-8">
                                        <select className="form-control mt-1" id="udistrict"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => loadWard(e.target.value, -1)}
                                            value={districtIndex}
                                            required>
                                            <option value="" disabled>Chọn Quận/Huyện</option>
                                            {district &&
                                                district.map((item, index) => (
                                                    <>
                                                        {item.label === addressJson.district
                                                            ? <option value={index} selected>{item.label}</option>
                                                            : <option value={index}>{item.label}</option>
                                                        }
                                                    </>
                                                ))
                                            }
                                        </select>
                                    </div>
                                </div>
                                {/*Ward*/}
                                <div className="row">
                                    <div className="col-md-4">
                                        <label htmlFor='uward' className='col-form-label'>Phường xã&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife}/></label>
                                    </div>
                                    <div className="col-md-8">
                                        <select className="form-control mt-1" id="uward"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => saveWard(e.target.value)}
                                            value={wardIndex}
                                            required>
                                            <option value="" disabled>Chọn Phường/Xã</option>
                                            {ward &&
                                                ward.map((item, index) => (
                                                    <>
                                                        {item.label === addressJson.ward
                                                            ? <option value={index} selected>{item.label}</option>
                                                            : <option value={index}>{item.label}</option>
                                                        }
                                                    </>
                                                ))
                                            }
                                        </select>
                                    </div>
                                </div>
                                {/*Street*/}
                                <div className="row">
                                    <div className="col-md-4">
                                        <label htmlFor='uhomenum' className='col-form-label'>Số nhà&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife}/></label>
                                    </div>
                                    <div className="col-md-8">
                                        <input type="text" id="uhomenum"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => saveAddressJson(e.target.value)}
                                            required
                                            className="form-control mt-1"
                                            value={addressJson.street}
                                            placeholder='Địa chỉ cụ thể'/>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-4">
                                        <label htmlFor='' className='col-form-label'>Trạng thái&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife}/></label>
                                    </div>
                                    <div className="col-md-8">
                                        <select className="form-select mt-1" aria-label="Default select example" id="updateSupplierStatus"
                                            onChange={(e) => handleUpdateSupplierChange(e, "status")}>
                                            <option value="1" className='text-green'>Đang hoạt động</option>
                                            <option value="0" className='text-red'>Ngừng hoạt động</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="danger" style={{ width: "20%" }} onClick={handleClose} id="cancelUpdateSupplier" >
                                Huỷ
                            </Button>
                            <Button variant="success" style={{ width: "30%" }} className="col-md-6" id="confirmUpdateSupplier" onClick={handleUpdateSupplierSubmit}>
                                Cập nhật
                            </Button>
                        </Modal.Footer>
                    </Modal>
                    </form>
                    <div className='detailbody'>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Họ và tên</p>
                            </div>
                            <div className="col-md-4">
                                <p>{updateSupplierDTO.supplierName}</p>
                            </div>
                            <div className="col-md-4 ">
                                <div className='button'>
                                    <button id="startEditSupplier" className='btn btn-success' onClick={handleUpdateClick}>Sửa</button>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Số điện thoại</p>
                            </div>
                            <div className="col-md-4">
                                <p>{updateSupplierDTO.supplierPhone}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Email</p>
                            </div>
                            <div className="col-md-4">
                                <p>{updateSupplierDTO.supplierMail}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Tên cơ sở</p>
                            </div>
                            <div className="col-md-4">
                                <p>{updateSupplierDTO.facilityName}</p>
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
                                <p>Tỉ lệ trứng thụ tinh</p>
                            </div>
                            <div className="col-md-4">
                                <p>{updateSupplierDTO.fertilizedRate}/10</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Tỉ lệ thành gà trống</p>
                            </div>
                            <div className="col-md-4">
                                <p>{updateSupplierDTO.maleRate}/10</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Trạng thái</p>
                            </div>
                            <div className="col-md-4">
                                {updateSupplierDTO.status === 1
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
            </SupplierDetails>
        </Box>
    );
}