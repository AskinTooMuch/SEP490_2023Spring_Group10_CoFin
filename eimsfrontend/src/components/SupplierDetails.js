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
    const [dataLoaded, setDataLoaded] = useState(false);
    //URL
    const SUPPLIER_UPDATE = "/api/supplier/update/save";
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
        supplierId: "",
        supplierName: "",
        facilityName: "",
        supplierPhone: "",
        supplierAddress: "",
        supplierMail: "",
        status: ""
    })

    //Get sent params
    const { state } = useLocation();
    const { id, supplier } = state;
    const [addressJson, setAddressJson] = useState({
        city: "",
        district: "",
        ward: "",
        street: ""
    });

    useEffect(() => {
        setAddressJson(JSON.parse(supplier.supplierAddress));
        updateSupplierDTO.supplierId = supplier.supplierId;
        updateSupplierDTO.supplierName = supplier.supplierName;
        updateSupplierDTO.facilityName = supplier.facilityName;
        updateSupplierDTO.supplierPhone = supplier.supplierPhone;
        updateSupplierDTO.supplierAddress = supplier.supplierAddress;
        updateSupplierDTO.supplierMail = supplier.supplierMail;
        updateSupplierDTO.status = supplier.status;
    }, []);

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
        console.log("Load address ");
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
        console.log("City " + index );
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
          withCredentials: false
        }
      );
      setUpdateSupplierDTO({
        supplierId: supplier.supplierId,
        supplierName: "",
        facilityName: "",
        supplierPhone: "",
        supplierAddress: "",
        supplierMail: "",
        status: ""
      });
      console.log(response);
      toast.success(response);
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
                                    <div className="col-md-6 ">
                                        <p>Họ và tên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input required
                                            value={updateSupplierDTO.supplierName}
                                            onChange={(e) => handleUpdateSupplierChange(e, "supplierName")}
                                            className="form-control " />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6 ">
                                        <p>Số điện thoại<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input required
                                            value={updateSupplierDTO.supplierPhone}
                                            onChange={(e) => handleUpdateSupplierChange(e, "supplierPhone")}
                                            className="form-control " />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6 ">
                                        <p>Email</p>
                                    </div>
                                    <div className="col-md-6">
                                        <input
                                            value={updateSupplierDTO.supplierMail}
                                            onChange={(e) => handleUpdateSupplierChange(e, "supplierMail")}
                                            className="form-control " />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6 ">
                                        <p>Tên cơ sở<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input required
                                            value={updateSupplierDTO.facilityName}
                                            onChange={(e) => handleUpdateSupplierChange(e, "facilityName")}
                                            className="form-control " />
                                    </div>
                                </div>
                                {/*City*/}
                                <div className="row">
                                    <div className="col-md-6 ">
                                        <p>Thành phố<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
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
                                            {/* {city &&
                                                city.map((item, index) => ( 
                                                <>
                                                    {   item.label === addressJson.city
                                                    ? <option value={index} selected>{item.label}</option>
                                                    : <option value={index}>{item.label}</option>
                                                    }
                                                </>
                                                ))
                                            } */}
                                        </select>
                                    </div>
                                </div>
                                {/*District*/}
                                <div className="row">
                                    <div className="col-md-6 ">
                                        <p>Quận/Huyện<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
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
                                            {/* {district &&
                                                district.map((item, index) => (
                                                    <>
                                                    {   item.label === addressJson.district
                                                    ? <option value={index} selected>{item.label}</option>
                                                    : <option value={index}>{item.label}</option>
                                                    }
                                                </>
                                                ))
                                            } */}
                                        </select>
                                    </div>
                                </div>
                                {/*Ward*/}
                                <div className="row">
                                    <div className="col-md-6 ">
                                        <p>Đường<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
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
                                            {/* {ward &&
                                                ward.map((item, index) => (
                                                    <>
                                                    {   item.label === addressJson.ward
                                                    ? <option value={index} selected>{item.label}</option>   
                                                    : <option value={index}>{item.label}</option>
                                                    }
                                                </>
                                                ))
                                            } */}
                                        </select>
                                    </div>
                                </div>
                                {/*Street*/}
                                <div className="row">
                                    <div className="col-md-6 ">
                                        <p>Số nhà<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input type="text" id="uhomenum"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => saveAddressJson(e.target.value)}
                                            required
                                            className="form-control" />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Trạng thái<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <select class="form-select" aria-label="Default select example"
                                        onChange={(e) => handleUpdateSupplierChange(e, "status")}>
                                            <option value="1" className='text-green'>Đang hoạt động</option>
                                            <option value="0" className='text-red'>Ngừng hoạt động</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="danger" style={{ width: "20%" }} onClick={handleClose}>
                                Huỷ
                            </Button>
                            <Button variant="success" style={{ width: "30%" }} className="col-md-6" onClick={handleUpdateSupplierSubmit}>
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
                                <p>{supplier.supplierName}</p>
                            </div>
                            <div className="col-md-4 ">
                                <div className='button'>
                                    <button className='btn btn-success ' onClick={handleShow}>Sửa</button>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Số điện thoại</p>
                            </div>
                            <div className="col-md-4">
                                <p>{supplier.supplierPhone}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Email</p>
                            </div>
                            <div className="col-md-4">
                                <p>{supplier.supplierMail}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Tên cơ sở</p>
                            </div>
                            <div className="col-md-4">
                                <p>{supplier.facilityName}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Địa chỉ</p>
                            </div>
                            <div className="col-md-4">
                                <p>{addressJson.street + " " + addressJson.ward + " " + addressJson.district + " " + addressJson.city}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Tỉ lệ trứng thụ tinh</p>
                            </div>
                            <div className="col-md-4">
                                <p>8.9/10</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Tỉ lệ thành gà trống</p>
                            </div>
                            <div className="col-md-4">
                                <p>6.2/10</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Trạng thái</p>
                            </div>
                            <div className="col-md-4">
                                {supplier.status === 1
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
            </SupplierDetails>
        </Box>
    );
}