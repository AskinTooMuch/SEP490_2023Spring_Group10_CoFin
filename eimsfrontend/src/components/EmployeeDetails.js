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
import ConfirmBox from './ConfirmBox';
import { Modal } from 'react-bootstrap'
import axios from 'axios';

//Toast
import { toast } from 'react-toastify';

function EmployeeDetails(props) {
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

EmployeeDetails.propTypes = {
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
    const [employeeLoaded, setEmployeeLoaded] = useState(false);
    const [addressLoaded, setAddressLoaded] = useState(false);
    //URL
    const EMPLOYEE_UPDATE_SAVE = "/api/employee/update/save";
    const EMPLOYEE_GET = "/api/employee/get";
    const EMPLOYEE_DELETE = "/api/employee/delete";

    //ConfirmBox
    function openDelete() {
        setOpen(true);
    }
    const [open, setOpen] = useState(false);
    const userRef = useRef();

    //Show and hide popup
    const [value, setValue] = React.useState(0);
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const navigate = useNavigate();

    //address
    const [employeeAddress, setEmployeeAddress] = useState(
        {
            street: "",
            ward: "",
            district: "",
            city: ""
        }
    );

    // DTO for display employee detail
    const [employeeDetailDTO, setEmployeeDetailDTO] = useState({
        employeeId: "",
        employeeName: "",
        employeeDob: "",
        employeePhone: "",
        employeeAddress: "",
        email: "",
        salary: "",
        status: ""
    })

    //updateaddress
    const [updateAddress, setUpdateAddress] = useState(
        {
            city: "",
            district: "",
            ward: "",
            street: ""
        }
    );

    // DTO for update employee
    const [updateEmployeeDTO, setUpdateEmployeeDTO] = useState({
        employeeId: "",
        employeeName: "",
        employeeDob: "",
        employeePhone: "",
        employeeAddress: "",
        email: "",
        salary: "",
        status: "",
        facilityId: sessionStorage.getItem("facilityId")
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
    useEffect(() => {
        console.log("Load address");
        loadAddress();
        console.log(fullAddresses);
    }, [addressLoaded]);

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
        setAddressLoaded(true);
    }

    //Get sent param
    const { state } = useLocation();
    const { id } = state;

    //Get employee details
    useEffect(() => {
        console.log("Get employee");
        loadEmployee();
    }, [setEmployeeLoaded]);

    const loadEmployee = async () => {
        const result = await axios.get(EMPLOYEE_GET,
            {
                params: { employeeId: id },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        console.log(result);
        const responseJson = result.data;

        employeeDetailDTO.employeeId = responseJson.employeeId;
        employeeDetailDTO.employeeName = responseJson.employeeName;
        employeeDetailDTO.employeeDob = responseJson.employeeDob;
        employeeDetailDTO.employeePhone = responseJson.employeePhone;
        employeeDetailDTO.employeeAddress = responseJson.employeeAddress;
        employeeDetailDTO.email = responseJson.email;
        employeeDetailDTO.salary = responseJson.salary;
        employeeDetailDTO.status = responseJson.status;
        setEmployeeAddress(JSON.parse(employeeDetailDTO.employeeAddress));

        updateEmployeeDTO.employeeId = employeeDetailDTO.employeeId;
        updateEmployeeDTO.employeeName = employeeDetailDTO.employeeName;
        updateEmployeeDTO.employeeDob = employeeDetailDTO.employeeDob;
        updateEmployeeDTO.employeePhone = employeeDetailDTO.employeePhone;
        updateEmployeeDTO.employeeAddress = employeeDetailDTO.employeeAddress;
        updateEmployeeDTO.email = employeeDetailDTO.email;
        updateEmployeeDTO.salary = employeeDetailDTO.salary;
        updateEmployeeDTO.status = employeeDetailDTO.status;

        console.log("duong1")
        console.log(updateEmployeeDTO);
        setUpdateAddress(JSON.parse(employeeDetailDTO.employeeAddress))
        // Get index of dropdowns
        console.log("load values");
        console.log(fullAddresses);
        console.log(updateAddress);
        console.log(employeeAddress);
        setEmployeeLoaded(true);
    }

    const handleUpdateEmployeeGet = async () => {
        handleShow();
        // Get index of dropdowns
        console.log("load values");
        console.log(fullAddresses);

        const result = await axios.get(EMPLOYEE_GET,
            {
                params: { employeeId: id },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        console.log(result);
        const responseJson = result.data;
        updateEmployeeDTO.employeeId = responseJson.employeeId;
        updateEmployeeDTO.employeeName = responseJson.employeeName;
        updateEmployeeDTO.employeeDob = responseJson.employeeDob;
        updateEmployeeDTO.employeePhone = responseJson.employeePhone;
        updateEmployeeDTO.employeeAddress = responseJson.employeeAddress;
        updateEmployeeDTO.email = responseJson.email;
        updateEmployeeDTO.salary = responseJson.salary;
        updateEmployeeDTO.status = responseJson.status;
        setUpdateAddress(JSON.parse(updateEmployeeDTO.employeeAddress))

        console.log(updateAddress);
        for (let i in city) {
            console.log(i);
            if (updateAddress.city === city[i].label) {
                setCityIndex(i);
                updateAddress.city = fullAddresses[i].Name;
                console.log("City " + i);
                setCityIndex(i);
                const districtOnIndex = fullAddresses[i].Districts;
                const districtList = districtOnIndex.slice();
                for (let i in districtList) {
                    districtList[i] = { value: districtList[i].Id, label: districtList[i].Name }
                }
                setDistrict(districtList);
                for (let j in districtList) {
                    if (updateAddress.district === districtList[j].label) {
                        setDistrictIndex(j);
                        console.log(j);
                        updateAddress.district = fullAddresses[i].Districts[j].Name;
                        console.log("District " + j);
                        setDistrictIndex(j);
                        const wardOnIndex = fullAddresses[i].Districts[j].Wards;
                        const wardList = wardOnIndex.slice();
                        for (let i in wardList) {
                            wardList[i] = { value: wardList[i].Id, label: wardList[i].Name }
                        }
                        setWard(wardList);
                        for (let k in wardList) {
                            if (updateAddress.ward === wardList[k].label) {
                                setWardIndex(k);
                                updateAddress.ward = fullAddresses[i].Districts[j].Wards[k].Name;
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        setStreet(updateAddress.street);
        console.log(employeeAddress);
        setEmployeeLoaded(true);
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

    function saveEmployeeAddress(s) {
        console.log("ward " + wardIndex);
        setStreet(s);
        employeeAddress.city = fullAddresses[cityIndex].Name;
        employeeAddress.district = fullAddresses[cityIndex].Districts[districtIndex].Name;
        employeeAddress.ward = fullAddresses[cityIndex].Districts[districtIndex].Wards[wardIndex].Name;
        employeeAddress.street = street;
        updateEmployeeDTO.employeeAddress = JSON.stringify(employeeAddress);
    }

    //Handle Change functions:
    //Update Employee
    const handleUpdateEmployeeChange = (event, field) => {
        let actualValue = event.target.value
        setUpdateEmployeeDTO({
            ...updateEmployeeDTO,
            [field]: actualValue
        })
    }

    //Handle Submit functions
    //Handle submit update Employee
    const handleUpdateEmployeeSubmit = async (event) => {
        event.preventDefault();
        saveEmployeeAddress(street);
        let response;
        try {
            response = await axios.post(EMPLOYEE_UPDATE_SAVE,
                updateEmployeeDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            setUpdateEmployeeDTO({
                employeeId: "",
                employeeName: "",
                employeeDob: "",
                employeePhone: "",
                employeeAddress: "",
                email: "",
                salary: "",
                status: "",
                facilityId: sessionStorage.getItem("facilityId")
            })
            loadEmployee();
            console.log(response);
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

    // Handle dalete employee
    const hanldeDeleteEmployee = async (event) => {
        let response;
        try {
            response = await axios.delete(EMPLOYEE_DELETE,
                {
                    params: { employeeId: id },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            console.log(response);
            navigate("/employee", { state: "Xóa thành công" });
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
                <Tabs sx={{
                    '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
                    '& .Mui-selected': { color: "#d25d19" },
                }} value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Nhân viên" {...a11yProps(0)} />
                    <Tab style={{ textTransform: "capitalize" }} label="Trở về trang Nhân viên chung" {...a11yProps(1)} onClick={() => navigate("/employee")} />
                </Tabs>
            </Box>
            <EmployeeDetails value={value} index={0}>
                <div className='container'>
                    <h3 style={{ textAlign: "center" }}>Thông tin nhân viên</h3>
                    <Modal show={show} onHide={handleClose}
                        size="lg"
                        aria-labelledby="contained-modal-title-vcenter"
                        centered >
                        <form>
                            <Modal.Header closeButton onClick={handleClose}>
                                <Modal.Title>Cập nhật thông tin nhân viên</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <div className="">
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Họ và tên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input style={{ width: "100%" }}
                                                value={updateEmployeeDTO.employeeName}
                                                onChange={(e) => handleUpdateEmployeeChange(e, "employeeName")} disabled/>
                                        </div>
                                    </div>
                                    {/*Date of birth*/}
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p htmlFor="employeeDob">Ngày sinh (Ngày/Tháng/Năm) <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input type="date" id="employeeDob" style={{ width: "100%" }}
                                                ref={userRef}
                                                autoComplete="off"
                                                onChange={(e) => handleUpdateEmployeeChange(e, "employeeDob")}
                                                value={updateEmployeeDTO.employeeDob} disabled
                                            />
                                        </div></div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Số điện thoại<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input style={{ width: "100%" }}
                                                onChange={(e) => handleUpdateEmployeeChange(e, "employeePhone")}
                                                value={updateEmployeeDTO.employeePhone} disabled/>
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
                                                value={cityIndex} disabled
                                            >
                                                <option value="" disabled>Chọn Tỉnh/Thành phố</option>
                                                {city &&
                                                    city.map((item, index) => (
                                                        <>
                                                            {item.label === employeeAddress.city
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
                                        <div className="col-md-6 ">
                                            <p>Quận/Huyện<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <select className="form-control mt-1" id="udistrict"
                                                ref={userRef}
                                                autoComplete="off"
                                                onChange={(e) => loadWard(e.target.value, -1)}
                                                value={districtIndex} disabled
                                            >
                                                <option value="" disabled>Chọn Quận/Huyện</option>
                                                {district &&
                                                    district.map((item, index) => (
                                                        <>
                                                            {item.label === employeeAddress.district
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
                                        <div className="col-md-6 ">
                                            <p>Phường xã<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <select className="form-control mt-1" id="uward"
                                                ref={userRef}
                                                autoComplete="off"
                                                onChange={(e) => saveWard(e.target.value)}
                                                value={wardIndex} disabled
                                            >
                                                <option value="" disabled>Chọn Phường/Xã</option>
                                                {ward &&
                                                    ward.map((item, index) => (
                                                        <>
                                                            {item.label === employeeAddress.ward
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
                                        <div className="col-md-6 ">
                                            <p>Số nhà<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input type="text" id="uhomenum"
                                                ref={userRef}
                                                autoComplete="off"
                                                onChange={(e) => saveEmployeeAddress(e.target.value)}
                                                disabled
                                                className="form-control"
                                                value={street} /> 
                                        </div>
                                    </div>

                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Email</p>
                                        </div>
                                        <div className="col-md-6">
                                            <input style={{ width: "100%" }}
                                                onChange={(e) => handleUpdateEmployeeChange(e, "email")}
                                                value={updateEmployeeDTO.email} disabled/> 
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Tiền lương<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input style={{ width: "100%" }}
                                                type='number'
                                                onChange={(e) => handleUpdateEmployeeChange(e, "salary")}
                                                step={0.01}
                                                defaultValue={0}
                                                value={updateEmployeeDTO.salary} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Trạng thái<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <select className="form-select" aria-label="Default select example"
                                                onChange={(e) => handleUpdateEmployeeChange(e, "status")}>
                                                <option disabled>Chọn trạng thái hoạt động</option>
                                                {updateEmployeeDTO.status === 2
                                                    ? <><option value="2" className='text-green' selected>Đang hoạt động </option><option value="0" className='text-red'>Tạm nghỉ</option></>
                                                    : <><option value="2" className='text-green'>Đang hoạt động </option><option value="0" className='text-red' selected>Tạm nghỉ</option></>
                                                }
                                            </select>
                                        </div>
                                    </div>
                                </div>

                            </Modal.Body>
                            <div className='model-footer'>
                                <button style={{ width: "30%" }} className="col-md-6 btn-light" onClick={handleUpdateEmployeeSubmit}>
                                    Cập nhật
                                </button>
                                <button style={{ width: "20%" }} type='button' onClick={handleClose} className="btn btn-light">
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
                                <p>{employeeDetailDTO.employeeName}</p>
                            </div>
                            <div className="col-md-4 ">
                                <div className='button'>
                                    <button className='btn btn-light ' onClick={handleUpdateEmployeeGet}>Sửa</button>
                                    <button className='btn btn-light ' onClick={openDelete}>Xoá</button>
                                </div>
                            </div>
                        </div>
                        <ConfirmBox open={open} closeDialog={() => setOpen(false)} title={"Xóa nhân viên"} 
                        content={"Xác nhận xóa nhân viên: " + employeeDetailDTO.employeeName}
                        deleteFunction={() => hanldeDeleteEmployee(employeeDetailDTO.employeeId)}
                        />
                        <div className="row">
                            <div className="col-md-4">
                                <p>Số điện thoại</p>
                            </div>
                            <div className="col-md-4">
                                <p>{employeeDetailDTO.employeePhone}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Ngày sinh</p>
                            </div>
                            <div className="col-md-4">
                                <p>{employeeDetailDTO.employeeDob}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Địa chỉ</p>
                            </div>
                            <div className="col-md-4">
                                <p>{employeeAddress.street + ", " + employeeAddress.ward + ", " + employeeAddress.district + ", " + employeeAddress.city}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Email</p>
                            </div>
                            <div className="col-md-4">
                                <p>{employeeDetailDTO.email}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Tiền lương</p>
                            </div>
                            <div className="col-md-4">
                                <p>{employeeDetailDTO.salary.toLocaleString('vi', { style: 'currency', currency: 'VND' })} /tháng</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Trạng thái</p>
                            </div>
                            <div className="col-md-4">
                                {employeeDetailDTO.status === 2
                                    ? <p className="text-green">Hoạt động </p>
                                    : <p className="text-red">Nghỉ </p>
                                }
                            </div>
                        </div>
                    </div>

                </div>
            </EmployeeDetails>
            
        </Box>

    );
}