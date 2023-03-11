import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import '../css/machine.css'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Modal } from 'react-bootstrap'
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
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const navigate = useNavigate();
    return (

        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                <Tabs sx={{
                    '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
                    '& .Mui-selected': { color: "#d25d19" },
                }} value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Máy ấp/nở" {...a11yProps(0)} />
                    <Tab style={{ textTransform: "capitalize" }} label="Trở về trang Máy" {...a11yProps(1)} onClick={() => navigate("/machine")} />
                </Tabs>
            </Box>
            <MachineDetails value={value} index={0}>
                <div className='container'>
                    <h3 style={{ textAlign: "center" }}>Thông tin chi tiết máy</h3>
                    <Modal show={show} onHide={handleClose}
                        size="lg"
                        aria-labelledby="contained-modal-title-vcenter"
                        centered >
                        <form>
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
                                            <input id="updateMachineName" style={{ width: "100%" }}/>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Trạng thái<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <select id = "updateMachineStatus" class="form-select" aria-label="Default select example">
                                                <option selected>Open this select menu</option>
                                                <option value="1" className='text-green'>Hoạt động bình thường</option>
                                                <option value="2">Two</option>
                                                <option value="3">Three</option>
                                            </select>
                                        </div>
                                    </div>

                                </div>

                            </Modal.Body>
                            <div className='model-footer'>
                                <button id="confirmUpdateMachine" style={{ width: "30%" }} className="col-md-6 btn-light" onClick={handleClose}>
                                    Cập nhật
                                </button>
                                <button id="cancelUpdateMachine" style={{ width: "20%" }} onClick={handleClose} className="btn btn-light">
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
                                <p>Máy 13</p>
                            </div>
                            <div className="col-md-4 ">
                                <div className='button'>
                                    <button className='btn btn-light ' onClick={handleShow} id="startEditMachine" >Sửa</button>
                                    <button className='btn btn-light ' id = "startDeleteMachine" >Xoá</button>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Loại máy</p>
                            </div>
                            <div className="col-md-4">
                                <p>Máy ấp</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Sức chứa</p>
                            </div>
                            <div className="col-md-4">
                                <p>1000/1000</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Tiến trình hiện tại</p>
                            </div>
                            <div className="col-md-4">
                                <p>Đang ấp</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Ngày thêm vào hệ thống</p>
                            </div>
                            <div className="col-md-4">
                                <p>06/12/2022</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Trạng thái</p>
                            </div>
                            <div className="col-md-4">
                                <p className="text-green">Hoạt động bình thường</p>
                            </div>
                        </div>

                    </div>

                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Mã lô</th>
                                <th scope="col">Tiến trình</th>
                                <th scope="col">Số trứng trong máy</th>
                                <th scope="col">Thời gian hoàn thành</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>

                                <th scope="row">ASD123</th>
                                <td>Đang ấp</td>
                                <td>1000/1000</td>
                                <td>26/01/2023</td>
                            </tr>

                        </tbody>
                    </table>
                </div>
            </MachineDetails>


        </Box>

    );
}