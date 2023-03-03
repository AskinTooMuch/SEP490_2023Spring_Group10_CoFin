import React, { useState } from 'react';
import { useNavigate, useParams } from "react-router-dom";
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import '../css/machine.css'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Modal, Button } from 'react-bootstrap'

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
    const [value, setValue] = React.useState(0);
    const navigate = useNavigate();
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    let params = useParams();
    console.log("Params" + params.itemId);

    return (

        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Nhà cung cấp" {...a11yProps(0)} />
                    <Tab style={{ textTransform: "capitalize" }} label="Trở về trang đơn hàng" {...a11yProps(1)} onClick={() => navigate("/order")}/>
                </Tabs>
            </Box>
            <SupplierDetails value={value} index={0}>
                    <div className='container'>
                        <h3 style={{ textAlign: "center" }}>Thông tin chi tiết máy</h3>
                        <form><Modal show={show} onHide={handleClose}
                            size="lg"
                            aria-labelledby="contained-modal-title-vcenter"
                            centered >
                            <Modal.Header closeButton onClick={handleClose}>
                                <Modal.Title>Sửa thông tin máy</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <div className="">
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Họ và tên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Số điện thoại<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Email</p>
                                        </div>
                                        <div className="col-md-6">
                                            <input />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Tên cơ sở<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Địa chỉ<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Số nhà<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Đường<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Quận/Huyện<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Thành phố<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Trạng thái<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <select class="form-select" aria-label="Default select example">
                                                <option selected>Open this select menu</option>
                                                <option value="1" className='text-green'>Hoạt động bình thường</option>
                                                <option value="2">Two</option>
                                                <option value="3">Three</option>
                                            </select>
                                        </div>
                                    </div>

                                </div>

                            </Modal.Body>

                            <Modal.Footer>
                                <Button variant="danger" style={{ width: "20%" }} onClick={handleClose}>
                                    Huỷ
                                </Button>

                                <Button variant="success" style={{ width: "30%" }} className="col-md-6" onClick={handleClose}>
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
                                    <p>Phạm Anh B</p>
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
                                    <p>012345678910</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-4">
                                    <p>Email</p>
                                </div>
                                <div className="col-md-4">
                                    <p>example@gmail.com</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-4">
                                    <p>Tên cơ sở</p>
                                </div>
                                <div className="col-md-4">
                                    <p>Trnag Trại Gà Thuân Vải</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-4">
                                    <p>Địa chỉ</p>
                                </div>
                                <div className="col-md-4">
                                    <p>W69C+3CM, Vĩnh Lai, Cẩm Giàng, Hải Dương 03623</p>
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
                                    <p className="text-green">Hoạt động bình thường</p>
                                </div>
                            </div>

                        </div>


                    </div>
                </SupplierDetails>



        </Box>

    );
}