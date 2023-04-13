import React, { useState, useEffect } from 'react';
import { useLocation } from "react-router-dom";
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
import { toast } from 'react-toastify';
import WithPermission from '../utils.js/WithPermission';
function Machine(props) {
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

Machine.propTypes = {
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
    // Dependency
    const [dataLoaded, setDataLoaded] = useState(false);
    //URL
    const MACHINE_ALL = "/api/machine/all";
    const MACHINE_CREATE = "/api/machine/create";
    const MACHINE_GET = "/api/machine/get";

    //Show-hide Popup
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const [value, setValue] = React.useState(0);
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    //Data holding objects
    const [machineList, setMachineList] = useState([]);
    //Get sent params
    const { state } = useLocation();
    var mess = true;
    //DTOs
    //CreateMachineDTO
    const [createMachineDTO, setCreateMachineDTO] = useState({
        facilityId: sessionStorage.getItem("facilityId"),
        machineTypeId: 1,
        machineName: "",
        maxCapacity: ""
    })

    //Handle Change functions:
    //CreateMachine
    const handleCreateMachineChange = (event, field) => {
        let actualValue = event.target.value
        setCreateMachineDTO({
            ...createMachineDTO,
            [field]: actualValue
        })
    }

    //Handle Submit functions
    //Handle submit new Machine
    const handleCreateMachineSubmit = async (event) => {
        event.preventDefault();
        let response;
        try {
            response = await axios.post(MACHINE_CREATE,
                createMachineDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            setCreateMachineDTO({
                facilityId: sessionStorage.getItem("facilityId"),
                machineTypeId: 1,
                machineName: "",
                maxCapacity: ""
            });
            console.log(response);
            loadMachineList();
            toast.success("Tạo thành công");
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

    // Get list of Machine and show
    // Get Machine list
    useEffect(() => {
        if (dataLoaded) return;
        loadMachineList();
        setDataLoaded(true);
    }, []);

    // Request Machine list and load the Machine list into the table rows
    const loadMachineList = async () => {
        try {
            const result = await axios.get(MACHINE_ALL,
                {
                    params: { facilityId: sessionStorage.getItem("facilityId") },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setMachineList(result.data);

            // Toast Delete Machine success
            if (mess) {
                toast.success(state);
                mess = false;
            }
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
    const routeChange = (mId) => {
        //let path = '/machinedetail';
        navigate('/machinedetail', { state: { id: mId } });
    }

    return (
        <div className='container'>
            <Box sx={{ width: '100%' }}>
                <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                    <Tabs sx={{
                        '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
                        '& .Mui-selected': { color: "#d25d19" },
                    }} value={value} onChange={handleChange} aria-label="basic tabs example">
                        <Tab style={{ textTransform: "capitalize" }} label="Danh sách máy" {...a11yProps(0)} />
                    </Tabs>
                </Box>
                {/* Start: form to add new machine */}
                <Machine value={value} index={0}>
                    <nav className="navbar justify-content-between">
                        <WithPermission roleRequired="2">
                            <button className='btn btn-light' onClick={handleShow} id="startCreateMachine">+ Thêm</button>
                        </WithPermission>
                        <Modal show={show} onHide={handleClose}
                            size="lg"
                            aria-labelledby="contained-modal-title-vcenter"
                            centered >
                            <form onSubmit={handleCreateMachineSubmit}>
                                <Modal.Header closeButton onClick={handleClose}>
                                    <Modal.Title>Thêm máy</Modal.Title>
                                </Modal.Header>
                                <Modal.Body>
                                    <div className="changepass">
                                        {/*Machine name*/}
                                        <div className="row">
                                            <div className="col-md-6 ">
                                                <p>Tên máy<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                            </div>
                                            <div className="col-md-6">
                                                <input className="form-control mt-1"
                                                    id="createMachineName" style={{ width: "100%" }}
                                                    onChange={(e) => handleCreateMachineChange(e, "machineName")} />
                                            </div>
                                        </div>
                                        {/*Machine type*/}
                                        <div className="row">
                                            <div className="col-md-6">
                                                <p>Loại máy<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                            </div>
                                            <div className="col-md-6">
                                                <select onChange={(e) => handleCreateMachineChange(e, "machineTypeId")} id="createMachineType" className="form-select" aria-label="Default select example">
                                                    <option value="1" selected>Máy ấp </option>
                                                    <option value="2" >Máy nở</option>
                                                </select>
                                            </div>
                                        </div>
                                        {/*Machine capacity*/}
                                        <div className="row">
                                            <div className="col-md-6">
                                                <p>Sức chứa<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                            </div>
                                            <div className="col-md-6">
                                                <input className="form-control mt-1"
                                                    id="createMachineCapacity" style={{ width: "100%" }} type="number"
                                                    onChange={(e) => handleCreateMachineChange(e, "maxCapacity")} />
                                            </div>
                                        </div>
                                    </div>
                                </Modal.Body>
                                <div className='model-footer'>
                                    <button style={{ width: "20%" }} type="submit" className="col-md-6 btn-light" id="confirmCreateMachine">
                                        Tạo
                                    </button>
                                    <button className='btn btn-light' style={{ width: "20%" }} onClick={handleClose} id="cancelCreateMachine" type="button">
                                        Huỷ
                                    </button>
                                </div>
                            </form>
                        </Modal>
                        {/* End: form to add new machine*/}
                        {/* Start: Filter and sort table */}
                        <div className='filter my-2 my-lg-0'>
                            <p><FilterAltIcon />Lọc</p>
                            <p><ImportExportIcon />Sắp xếp</p>
                            <form className="form-inline">
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                        <button ><span className="input-group-text" ><SearchIcon /></span></button>
                                    </div>
                                    <input id="searchMachine" type="text" className="form-control" placeholder="Tìm kiếm" aria-label="Username" aria-describedby="basic-addon1" />
                                </div>
                            </form>
                        </div>
                        {/* End: Filter and sort table */}
                    </nav>

                    {/* Start: Table for machine list */}
                    <div>
                        <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                            <div className="u-clearfix u-sheet u-sheet-1">
                                <div className="u-expanded-width u-table u-table-responsive u-table-1">
                                    <table className="u-table-entity u-table-entity-1">
                                        <colgroup>
                                            <col width="10%" />
                                            <col width="20%" />
                                            <col width="20%" />
                                            <col width="20%" />
                                            <col width="20%" />
                                        </colgroup>
                                        <thead className="u-palette-4-base u-table-header u-table-header-1">
                                            <tr style={{ height: "21px" }}>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                                <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Tên máy</th>
                                                <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Loại máy</th>
                                                <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Sức chứa</th>
                                                <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Trạng thái</th>
                                            </tr>
                                        </thead>
                                        <tbody className="u-table-body">
                                            {
                                                machineList && machineList.length > 0 ?
                                                    machineList.map((item, index) =>
                                                        <tr className='trclick' onClick={() => routeChange(item.machineId)}>
                                                            <th className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-1" scope="row">{index + 1}</th>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{item.machineName}</td>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{item.machineTypeName}</td>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{item.curCapacity.toLocaleString()}/{item.maxCapacity.toLocaleString()}</td>

                                                            {item.active === 1
                                                                ? <td className="u-border-1 u-border-grey-30 u-table-cell text-green">
                                                                    Đang hoạt động
                                                                </td>
                                                                : <td className="u-border-1 u-border-grey-30 u-table-cell text-red">
                                                                    Ngừng hoạt động
                                                                </td>
                                                            }
                                                        </tr>
                                                    ) :
                                                    <tr>
                                                        <td colSpan='5'>Chưa có máy nào được lưu lên hệ thống</td>
                                                    </tr>
                                            }

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </section>
                    </div>
                    {/* End: Table for machine list */}
                </Machine>
            </Box>
        </div>
    );
}