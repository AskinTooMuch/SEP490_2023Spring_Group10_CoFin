import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { Modal } from 'react-bootstrap';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
function ImportBillDetail(props) {
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

ImportBillDetail.propTypes = {
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
    //Show-hide Popup
    const [value, setValue] = React.useState(0);
    const navigate = useNavigate();
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                <Tabs sx={{
                    '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
                    '& .Mui-selected': { color: "#d25d19" },
                }} value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Chi tiết hoá đơn" {...a11yProps(0)} />
                    <Tab style={{ textTransform: "capitalize" }} label="Trở về Đơn hàng" {...a11yProps(1)} onClick={() => navigate("/order")} />
                </Tabs>
            </Box>
            <ImportBillDetail value={value} index={0}>
            <h2>Thông tin chi tiết hoá đơn</h2>
                <div className='container'>
                    <div className='detailbody'>
                        <div className="row" >
                            <div className="col-md-3" >
                                <p>Nhà cung cấp</p>
                            </div>
                            <div className="col-md-3">
                                <p>Bùi Thanh C</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Số điện thoại</p>
                            </div>
                            <div className="col-md-3">
                                <p>09726164856</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Ngày nhập</p>
                            </div>
                            <div className="col-md-3" >
                                <p>06/02/2023</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Mã hoá đơn</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>SJA81-02-85</p>
                            </div>
                        </div>
                    </div>
                
                <br />
                <table className="table table-bordered" >
                    <thead>
                        <tr>
                            <th scope="col">STT</th>
                            <th scope="col">Mã lô</th>
                            <th scope="col">Tên lô</th>
                            <th scope="col">Số lượng (trứng)</th>
                            <th scope="col">Đơn giá</th>
                            <th scope="col">Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>NCH124</td>
                            <td>Trứng Gà ri</td>
                            <td>1000</td>
                            <td>3400</td>
                            <td>3.400.000</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>HGD826</td>
                            <td>Trứng Gà tre</td>
                            <td>500</td>
                            <td>660</td>
                            <td>3.300.000</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>HFJ712</td>
                            <td>Trứng Gà trọi</td>
                            <td>500</td>
                            <td>30000</td>
                            <td>15.000.000</td>
                        </tr>
                    </tbody>
                </table>
                
                <div className='row'>
                    <div className="col-md-6"></div>
                    <div className="col-md-3"></div>
                    <div className="col-md-3">
                        <p>Tổng giá trị: 49.000.000 VNĐ</p>
                        <p>Đã thanh toán: 10.000.000 VNĐ</p>
                        <p>Trạng thái: <span className='text-red'>Chưa thanh toán đủ</span></p>
                    </div>
                </div>
                <div className='model-footer'>
                    <button style={{ width: "20%" }} className="col-md-6 btn-light" onClick={handleShow}>
                        Cập nhật số tiền
                    </button>
                    <Modal show={show} onHide={handleClose}
                        size="lg"
                        aria-labelledby="contained-modal-title-vcenter"
                        centered >
                        <form >
                            <Modal.Header closeButton onClick={handleClose}>
                                <Modal.Title>Cập nhật số tiền thanh toán</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Số tiền đã thanh toán</p>
                                    </div>
                                    <div className="col-md-6">
                                        <input required style={{ width: "100%" }}
                                             />
                                    </div>
                                </div>
                            </Modal.Body>
                            <div className='model-footer'>
                                <button style={{ width: "30%" }} className="col-md-6 btn-light">
                                    Cập nhật
                                </button>
                                <button style={{ width: "20%" }} onClick={handleClose} className="btn btn-light">
                                    Huỷ
                                </button>
                            </div>
                        </form>
                    </Modal>
                </div>
                </div>
            </ImportBillDetail>
        </Box>

    );
}