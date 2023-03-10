import React, { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import '../css/machine.css'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Modal, Button } from 'react-bootstrap'
import chicpic from '../pics/gari.png'
function EggBatchDetail(props) {
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

EggBatchDetail.propTypes = {
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
                    <Tab style={{ textTransform: "capitalize" }} label="Lô Trứng" {...a11yProps(0)} />
                    <Tab style={{ textTransform: "capitalize" }} label="Trở về trang Trứng" {...a11yProps(1)} onClick={() => navigate("/egg")} />
                </Tabs>
            </Box>
            <EggBatchDetail value={value} index={0}>
                <div className='container'>
                    <h3 style={{ textAlign: "center" }}>Trứng gà ri</h3>
                    <div className='detailbody'>

                        <div className="row">
                            <div className="col-md-3">
                                <p>Mã lô</p>
                            </div>
                            <div className="col-md-3">
                                <p>GFJ816</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Mã hoá đơn</p>
                            </div>
                            <div className="col-md-3">
                                <p>HDJ71-71-25</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Nhà cung cấp</p>
                            </div>
                            <div className="col-md-3">
                                <p>Phạm Anh B</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Ngày nhập</p>
                            </div>
                            <div className="col-md-3">
                                <p>2401/2023</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Tên loài</p>
                            </div>
                            <div className="col-md-3">
                                <p>Gà</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Tên loại</p>
                            </div>
                            <div className="col-md-3">
                                <p>Gà ri</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Số lượng</p>
                            </div>
                            <div className="col-md-3">
                                <p>3000 quả</p>
                            </div>
                            <div className="col-md-3 ">
                                <p></p>
                            </div>
                            <div className="col-md-3">
                                <p></p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Giai đoạn hiện tại</p>
                            </div>
                            <div className="col-md-3">
                                <p>2</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Trạng thái</p>
                            </div>
                            <div className="col-md-3">
                                <p className='text-blue'>Đang ấp</p>
                            </div>
                        </div>
                        <div>Thông báo cần chiếu trứng</div>
                    </div>

                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col"></th>
                                <th scope="col">Trứng vỡ</th>
                                <th scope="col">Trứng trắng</th>
                                <th scope="col">Trứng loãng</th>
                                <th scope="col">Trứng tắc</th>
                                <th scope="col">Con</th>
                                <th scope="col">Con đực</th>
                                <th scope="col">Con cái</th>
                                <th scope="col">Hao hụt</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th scope="row">Ban đầu</th>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>

                            </tr>
                            <tr>
                                <th scope="row">Hiện tại</th>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>
                                <td>0</td>

                            </tr>
                        </tbody>
                    </table>
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Vị trí</th>
                                <th scope="col">Số lượng</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th scope="row">Máy 13</th>
                                <td>1000</td>
                            </tr>
                            <tr>
                                <th scope="row">Máy 17</th>
                                <td>1000</td>
                            </tr>
                            <tr>
                                <th scope="row">Máy 23</th>
                                <td>500</td>
                            </tr>
                        </tbody>
                    </table>

                </div>
                <button className='btn btn-success' style={{ width: "20%", float: "right" }} id="startUpdateEggBatch">Cập nhật</button>
            </EggBatchDetail>


        </Box>

    );
}