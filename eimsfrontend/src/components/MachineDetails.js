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
import { Modal } from 'react-bootstrap'
import ConfirmBox from './ConfirmBox';
import axios from 'axios';
//Toast
import { ToastContainer, toast } from 'react-toastify';
function MachineDetails(props) {
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

MachineDetails.propTypes = {
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
    const [machineLoaded, setMachineLoaded] = useState(false);
    //URL
    const MACHINE_UPDATE_SAVE = "/api/machine/update/save";
    const MACHINE_UPDATE_GET = "/api/machine/update/get";
    const MACHINE_GET = "/api/machine/get";
    const MACHINE_DELETE = "/api/machine/delete";
    //ConfirmBox
    function openDelete() {
        setOpen(true);
    }
    const [open, setOpen] = useState(false);
    //Show-hide Popup
    const userRef = useRef();
    const [value, setValue] = React.useState(0);
    const navigate = useNavigate();
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    //Data holding objects
    const [listEggLocation, setListEggLocation] = useState([]);

    //DTO
    // DTO for update machine
    const [updateMachineDTO, setUpdateMachineDTO] = useState({
        machineId: "",
        machineName: "",
        status: ""
    })

    // DTO for display machine detail
    const [machineDetailDTO, setMachineDetailDTO] = useState({
        machineId: "",
        machineName: "",
        machineTypeName: "",
        curCapacity: "",
        maxCapacity: "",
        addedDate: "",
        eggs: "",
        status: ""
    })

    //Get sent params
    const { state } = useLocation();
    const { id } = state;

    //Get customer details
    useEffect(() => {
        console.log("Get machine");
        loadMachine();
    }, [machineLoaded]);

    const loadMachine = async () => {
        const result = await axios.get(MACHINE_GET,
            { params: { machineId: id } },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: false
            });
        console.log(result);

        // Set inf
        updateMachineDTO.machineId = result.data.machineId;
        updateMachineDTO.machineName = result.data.machineName;
        updateMachineDTO.status = result.data.status;

        machineDetailDTO.machineId = result.data.machineId;
        machineDetailDTO.machineName = result.data.machineName;
        machineDetailDTO.machineTypeName = result.data.machineTypeName;
        machineDetailDTO.curCapacity = result.data.curCapacity;
        machineDetailDTO.maxCapacity = result.data.maxCapacity;
        machineDetailDTO.addedDate = result.data.addedDate;
        machineDetailDTO.listEggLocation = result.data.eggs;
        machineDetailDTO.status = result.data.status;
        setListEggLocation(result.data.eggs);
        setMachineLoaded(true);
    }

    //Handle Change functions:
    //Update machine
    const handleUpdateMachineChange = (event, field) => {
        let actualValue = event.target.value
        setUpdateMachineDTO({
            ...updateMachineDTO,
            [field]: actualValue
        })
    }

    //Handle Submit functions
    //Handle submit update machine
    const handleUpdateMachineSubmit = async (event) => {
        event.preventDefault();
        let response;
        try {
            response = await axios.put(MACHINE_UPDATE_SAVE,
                updateMachineDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: false
                }
            );

            loadMachine();
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

    // Handle dalete machine
    const hanldeDeleteMachine = async (event) => {
        if (listEggLocation.length > 0) {
            toast.error("Chuyển trứng sang máy khác trước khi xóa");
            setOpen(false);
        } else {
            let response;
            try {
                response = await axios.delete(MACHINE_DELETE,
                    { params: { machineId: id } },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Access-Control-Allow-Origin': '*'
                        },
                        withCredentials: false
                    }
                );
                console.log(response);
                navigate("/machine", { state: "Xóa thành công" });
            } catch (err) {
                if (!err?.response) {
                    toast.error('Server không phản hồi');
                } else {
                    toast.error(response);
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
                    <Tab style={{ textTransform: "capitalize" }} label="Danh sách máy" {...a11yProps(0)} />
                    <Tab style={{ textTransform: "capitalize" }} label="Trở về danh sách Máy" {...a11yProps(1)} onClick={() => navigate("/machine")} />
                </Tabs>
            </Box>
            <MachineDetails value={value} index={0}>
                <div className='container'>
                    <h3 style={{ textAlign: "center" }}>Thông tin chi tiết máy</h3>
                    <Modal show={show} onHide={handleClose}
                        size="lg"
                        aria-labelledby="contained-modal-title-vcenter"
                        centered >
                        <form onSubmit={handleUpdateMachineSubmit}>
                            <Modal.Header closeButton onClick={handleClose}>
                                <Modal.Title>Sửa thông tin máy</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <div className="">
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Tên máy<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input id="updateMachineName" style={{ width: "100%" }} required
                                                value={updateMachineDTO.machineName}
                                                onChange={(e) => handleUpdateMachineChange(e, "machineName")} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Trạng thái<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        {updateMachineDTO.status === 1 ?
                                            <div className="col-md-6">
                                                <select onChange={(e) => handleUpdateMachineChange(e, "status")} class="form-select" aria-label="Default select example">
                                                    <option value="1" selected>Hoạt động</option>
                                                    <option value="2">Dừng hoạt động</option>
                                                </select>
                                            </div>
                                            :
                                            <div className="col-md-6">
                                                <select onChange={(e) => handleUpdateMachineChange(e, "status")} class="form-select" aria-label="Default select example">
                                                    <option value="1">Hoạt động</option>
                                                    <option value="2" selected>Dừng hoạt động</option>
                                                </select>
                                            </div>
                                        }
                                    </div>

                                </div>

                            </Modal.Body>
                            <div className='model-footer'>
                                <button id="confirmUpdateMachine" style={{ width: "30%" }} className="col-md-6 btn-light" type="submit">
                                    Cập nhật
                                </button>
                                <button id="cancelUpdateMachine" style={{ width: "20%" }} onClick={handleClose} className="btn btn-light" type="button">
                                    Huỷ
                                </button>
                            </div>
                        </form>
                    </Modal>

                    <div className='detailbody'>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Tên máy</p>
                            </div>
                            <div className="col-md-4">
                                <p>{machineDetailDTO.machineName}</p>
                            </div>
                            <div className="col-md-4 ">
                                <div className='button'>
                                    <button className='btn btn-light ' onClick={handleShow} id="startEditMachine" >Sửa</button>
                                    <button className='btn btn-light ' id="startDeleteMachine" onClick={openDelete}>Xoá</button>
                                </div>
                            </div>
                        </div>
                        <ConfirmBox open={open} closeDialog={() => setOpen(false)} title={machineDetailDTO.machineName} deleteFunction={() => hanldeDeleteMachine(machineDetailDTO.machineId)}
                          />
                        <div className="row">
                            <div className="col-md-4">
                                <p>Loại máy</p>
                            </div>
                            <div className="col-md-4">
                                <p>{machineDetailDTO.machineTypeName}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Sức chứa</p>
                            </div>
                            <div className="col-md-4">
                                <p>{machineDetailDTO.curCapacity}/{machineDetailDTO.maxCapacity}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Ngày thêm vào hệ thống</p>
                            </div>
                            <div className="col-md-4">
                                <p>{machineDetailDTO.addedDate}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Trạng thái</p>
                            </div>
                            {machineDetailDTO.status === 1 ?
                                <div className="col-md-4">
                                    <p className="text-green">Hoạt động</p>
                                </div>
                                :
                                <div className="col-md-4">
                                    <p className="text-red">Dừng hoạt động</p>
                                </div>
                            }
                        </div>
                    </div>

                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Mã lô</th>
                                <th scope="col">Tiến trình</th>
                                <th scope="col">Số lượng</th>
                                <th scope="col">Thời gian hoàn thành</th>
                            </tr>
                        </thead>
                        <tbody>
                            {listEggLocation && listEggLocation.length > 0 ?
                                listEggLocation.map((item, index) =>
                                    <tr>
                                        <th scope="row">{item.eggBatchId}</th>
                                        <td>Ngày </td>
                                        <td>{item.amount}</td>
                                        <td>26/01/2023</td>
                                    </tr>
                                ) : "Nothing"
                            }
                        </tbody>
                    </table>
                </div>
            </MachineDetails>
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