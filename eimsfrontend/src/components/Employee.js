import React, { useState, useEffect, useRef } from 'react';
import { useParams, useLocation } from "react-router-dom";
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
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
import { padding } from '@mui/system';
function Employee(props) {
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
                    <Typography component="span">{children}</Typography>
                </Box>
            )}
        </div>
    );
}

Employee.propTypes = {
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
    //Address consts
    const userRef = useRef();
    const [dataLoaded, setDataLoaded] = useState(false);
    //Full Json addresses
    const [fullAddresses, setFullAddresses] = useState('');
    const [city, setCity] = useState([
        { value: '', label: 'Chọn Tỉnh/Thành phố' }
    ]);
    //Supplier Address data
    const [district, setDistrict] = useState(''); //For populate dropdowns
    const [ward, setWard] = useState('');
    const [cityIndex, setCityIndex] = useState(); //Save the index of selected dropdowns
    const [districtIndex, setDistrictIndex] = useState();
    const [wardIndex, setWardIndex] = useState();
    const [street, setStreet] = useState();
    const [employeeAddressJson, setEmployeeAddressJson] = useState({
        city: "",
        district: "",
        ward: "",
        street: ""
    });

    //URL
    const EMPLOYEE_CREATE = "/api/employee/create";
    const MACHINE_ALL = "/api/employee/all";

    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    let navigate = useNavigate();
    const routeChange = (employeeId) => {
        let path = '/employeedetail';
        navigate('/employeedetail', { state: { id: employeeId } });
    }

    //Data holding objects
    const [employeeList, setEmployeeList] = useState([]);
    //Get sent params
    const { state } = useLocation();
    //DTOs
    //CreateEmployeeDTO
    const [createEmployeeDTO, setCreateEmployeeDTO] = useState({
        employeeName: "",
        employeeDob: "",
        employeePhone: "",
        employeePassword: "",
        employeeAddress: "",
        email: "",
        salary: "",
        facilityId: sessionStorage.getItem("facilityId")
    })

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
        employeeAddressJson.city = fullAddresses[cityIndex].Name;
        employeeAddressJson.district = fullAddresses[cityIndex].Districts[districtIndex].Name;
        employeeAddressJson.ward = fullAddresses[cityIndex].Districts[districtIndex].Wards[wardIndex].Name;
        employeeAddressJson.street = street;
        createEmployeeDTO.employeeAddress = JSON.stringify(employeeAddressJson);
    }

    //Handle Change functions:
    //CreateEmployee
    const handleCreateEmployeeChange = (event, field) => {
        let actualValue = event.target.value
        setCreateEmployeeDTO({
            ...createEmployeeDTO,
            [field]: actualValue
        })
    }

    //Handle Submit functions
    //Handle submit new Employee
    const handleCreateEmployeeSubmit = async (event) => {
        event.preventDefault();
        saveAddressJson(street);
        let response;
        try {
            response = await axios.post(EMPLOYEE_CREATE,
                createEmployeeDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            setCreateEmployeeDTO({
                employeeName: "",
                employeeDob: "",
                employeePhone: "",
                employeePassword: "",
                employeeAddress: "",
                email: "",
                salary: "",
                facilityId: sessionStorage.getItem("facilityId")
            });
            console.log(response);
            loadEmployeeList();
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

    // Get list of Employee and show
    // Get Employee list
    useEffect(() => {
        loadEmployeeList();
    }, []);

    // Request Machine list and load the Employee list into the table rows
    const loadEmployeeList = async () => {
        const result = await axios.get(MACHINE_ALL,
            {
                params: { facilityId: sessionStorage.getItem("facilityId") },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setEmployeeList(result.data);

        // Toast Delete Emplopyee success
        console.log("state:====" + state)
        if (state != null) toast.success(state);
    }



    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                <Tabs sx={{
                    '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
                    '& .Mui-selected': { color: "#d25d19" },
                }} value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Nhân viên" {...a11yProps(0)} />
                </Tabs>
            </Box>
            <Employee value={value} index={0}>
                <nav className="navbar justify-content-between">
                    <button className='btn btn-light' onClick={handleShow}>+ Thêm</button>
                    <Modal show={show} onHide={handleClose}
                        size="lg"
                        aria-labelledby="contained-modal-title-vcenter"
                        centered >
                        <form onSubmit={handleCreateEmployeeSubmit}>
                            <Modal.Header closeButton onClick={handleClose}>
                                <Modal.Title>Thêm nhân viên</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Tên nhân viên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input style={{ width: "100%" }}
                                            onChange={(e) => handleCreateEmployeeChange(e, "employeeName")} />
                                    </div>
                                    {/*Date of birth*/}
                                    <div className="col-md-6">
                                        <p htmlFor="employeeDob">Ngày sinh (Ngày/Tháng/Năm) <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input type="date" id="employeeDob" style={{ width: "100%" }}
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => handleCreateEmployeeChange(e, "employeeDob")}
                                        />
                                    </div>
                                    <div className="col-md-6">
                                        <p>Số điện thoại<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input style={{ width: "100%" }} placeholder="0"
                                            onChange={(e) => handleCreateEmployeeChange(e, "employeePhone")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Mật khẩu<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input style={{ width: "100%" }} placeholder="0"
                                            onChange={(e) => handleCreateEmployeeChange(e, "employeePassword")} />
                                    </div>

                                    {/*City*/}
                                    <div className="col-md-6">
                                        <p htmlFor="uprovince" >Tỉnh/Thành Phố<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
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

                                    {/*District*/}
                                    <div className="col-md-6">
                                        <p htmlFor="udistrict" >Quận/Huyện <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <select className="form-control mt-1" id="udistrict" name="supplierDistrict"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => loadWard(e.target.value)}
                                            value={districtIndex}
                                        >
                                            <option value="" disable>Chọn Quận/Huyện</option>
                                            {district &&
                                                district.map((item, index) => (
                                                    <option value={index}>{item.label}</option>
                                                ))
                                            }
                                        </select>
                                    </div>

                                    {/*User ward*/}
                                    <div className="col-md-6">
                                        <p htmlFor="uward" >Phường/Xã <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <select className="form-control mt-1" id="uward" name="supplierWard"
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => saveWard(e.target.value)}
                                            value={wardIndex}
                                        >
                                            <option value="" disable>Chọn Phường/Xã</option>
                                            {ward &&
                                                ward.map((item, index) => (
                                                    <option value={index}>{item.label}</option>
                                                ))
                                            }
                                        </select>
                                    </div>

                                    <div className="col-md-6">
                                        <p htmlFor="uhomenum">Số nhà <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input type="text" id="uhomenum" style={{ width: "100%" }}
                                            ref={userRef}
                                            autoComplete="off"
                                            onChange={(e) => saveAddressJson(e.target.value)}
                                        />
                                    </div>

                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Email</p>
                                    </div>
                                    <div className="col-md-6">
                                        <input style={{ width: "100%" }}
                                            onChange={(e) => handleCreateEmployeeChange(e, "email")} />
                                    </div>
                                    <div className="col-md-6">
                                        <p>Lương<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input style={{ width: "100%" }} placeholder="0"
                                            type='number'
                                            onChange={(e) => handleCreateEmployeeChange(e, "salary")} />
                                    </div>
                                </div>
                            </Modal.Body>
                            <div className='model-footer'>
                                <button style={{ width: "30%" }} type="submit" className="col-md-6 btn-light">
                                    Tạo
                                </button>
                                <button style={{ width: "20%" }} type="button" onClick={handleClose} className="btn btn-light">
                                    Huỷ
                                </button>
                            </div>
                        </form>
                    </Modal>
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
                </nav>
                <div>
                    <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                        <div className="u-clearfix u-sheet u-sheet-1">

                            <div className="u-expanded-width u-table u-table-responsive u-table-1">
                                <table className="u-table-entity u-table-entity-1">
                                    <colgroup>
                                        <col width="5%" />
                                        <col width="35%" />
                                        <col width="30%" />
                                        <col width="30%" />
                                    </colgroup>
                                    <thead className="u-palette-4-base u-table-header u-table-header-1">
                                        <tr style={{ height: "21px" }}>
                                            <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                            <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Tên nhân viên</th>
                                            <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Số điện thoại</th>
                                            <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Trạng thái</th>
                                        </tr>
                                    </thead>
                                    <tbody className="u-table-body">
                                        {
                                            employeeList && employeeList.length > 0 ?
                                                employeeList.map((item, index) =>
                                                    <tr style={{ height: "76px" }} className='trclick' onClick={() => routeChange(item.employeeId)}>
                                                        <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-9">{index + 1}</td>
                                                        <td className="u-border-1 u-border-grey-30 u-table-cell">{item.employeeName}</td>
                                                        <td className="u-border-1 u-border-grey-30 u-table-cell">{item.employeePhone}</td>
                                                        {item.status === 2
                                                            ? <td className="u-border-1 u-border-grey-30 u-table-cell text-green">
                                                                Hoạt động
                                                            </td>
                                                            : <td className="u-border-1 u-border-grey-30 u-table-cell text-red">
                                                                Tạm nghỉ
                                                            </td>
                                                        }
                                                    </tr>
                                                ) : "Nothing"

                                        }
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </section>
                </div>
            </Employee>
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

        </Box>
    );
}