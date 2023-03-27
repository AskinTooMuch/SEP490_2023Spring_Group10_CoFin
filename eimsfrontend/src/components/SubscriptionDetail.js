import React, { useState } from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import '../css/machine.css'
import { Modal } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
function SubcriptionDetail(props) {
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

SubcriptionDetail.propTypes = {
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
    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                <Tabs sx={{
                    '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
                    '& .Mui-selected': { color: "#d25d19" },
                }} value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Danh sách gói" {...a11yProps(0)} />
                </Tabs>
            </Box>
            <SubcriptionDetail value={value} index={0}>
                <h2>Thông tin gói</h2>
                <div className='sub-wrapper container'>
                    <div className="row">
                        <div className="col-md-4">
                            <p>Tên gói</p>
                        </div>
                        <div className="col-md-4">
                            <p>Gói 1 tháng</p>
                        </div>
                        <div className="col-md-4">
                            <div className='button'>
                                <button className='btn btn-light ' onClick={handleShow}>Sửa</button>
                                <button className='btn btn-light '>Xoá</button>
                            </div>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-4">
                            <p>Ngày tạo</p>
                        </div>
                        <div className="col-md-4">
                            <p>12/12/2022</p>
                        </div>
                        <div className="col-md-4">
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-4">
                            <p>Giá gói</p>
                        </div>
                        <div className="col-md-4">
                            <p>100.000 VNĐ</p>
                        </div>
                        <div className="col-md-4">
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-4">
                            <p>Thời gian hiệu lực</p>
                        </div>
                        <div className="col-md-4">
                            <p>30 ngày</p>
                        </div>
                        <div className="col-md-4">
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-4">
                            <p>Nội dung</p>
                        </div>
                        <div className="col-md-4">
                            <textarea style={{ width: "100%", height:"200px" }} className="form-control " />
                        </div>
                        <div className="col-md-4">
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-4">
                            <p>Trạng thái</p>
                        </div>
                        <div className="col-md-4">
                            <p className='text-green'>Có hiệu lực</p>
                        </div>
                        <div className="col-md-4">
                        </div>
                    </div>
                </div>
                <Modal show={show} onHide={handleClose}
                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered >
                    <form>
                        <Modal.Header closeButton onClick={handleClose}>
                            <Modal.Title>Cập nhật gói đăng ký</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div className="row">
                                <div className="col-md-6">
                                    <p>Tên gói<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <input required style={{ width: "100%" }} className="form-control " />
                                </div>
                                <div className="col-md-6">
                                    <p>Giá gói<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <input required style={{ width: "100%" }} placeholder="0" className="form-control " />
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-6">
                                    <p>Thời gian hiệu lực<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <input required style={{ width: "100%" }} placeholder="0" className="form-control " />
                                </div>
                                <div className="col-md-6">
                                    <p>Nội dung chi tiết</p>
                                </div>
                                <div className="col-md-6">
                                    <textarea style={{ width: "100%" }} className="form-control " />
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-6">
                                    <p>Trạng thái<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <select required className="form-control " >
                                        <option defaultValue="default">Chọn trạng thái</option>
                                        <option value="1" className='text-green'>Có hiệu lực</option>
                                    </select>
                                </div>
                            </div>

                        </Modal.Body>
                        <div className='model-footer'>
                            <button style={{ width: "30%" }} className="col-md-6 btn-light" onClick={handleClose}>
                                Cập nhật
                            </button>
                            <button style={{ width: "20%" }} onClick={handleClose} className="btn btn-light">
                                Huỷ
                            </button>
                        </div>
                    </form>
                </Modal>
            </SubcriptionDetail>


        </Box>
    );
}