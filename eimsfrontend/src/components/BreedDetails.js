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
import { Modal } from 'react-bootstrap'
import chicpic from '../pics/gari.png'
function BreedDetails(props) {
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

BreedDetails.propTypes = {
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
    const [images, setImages] = useState([]);
    const [imageURLs, setImageURLs] = useState([]);

    useEffect(() => {
        if (images.length < 1) return;
        const newImageUrls = [];
        images.forEach(image => newImageUrls.push(URL.createObjectURL(image)));
        setImageURLs(newImageUrls);
    }, [images]);

    const onImageChange = (e) => {
        setImages([...e.target.files]);
    }
    return (

        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                <Tabs sx={{
                    '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
                    '& .Mui-selected': { color: "#d25d19" },
                }} value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Loại" {...a11yProps(0)} />
                    <Tab style={{ textTransform: "capitalize" }} label="Trở về trang Trứng" {...a11yProps(1)} onClick={() => navigate("/egg")} />
                </Tabs>
            </Box>
            <BreedDetails value={value} index={0}>
                <div className='container'>
                    <h3 style={{ textAlign: "center" }}>Thông tin loại</h3>
                    <Modal show={show} onHide={handleClose}
                        size="lg"
                        aria-labelledby="contained-modal-title-vcenter"
                        centered >
                        <form>
                            <Modal.Header closeButton onClick={handleClose}>
                                <Modal.Title>Sửa thông tin loại trứng</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <div className="changepass">
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Loài<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <select id="updateBreedSpecie" class="form-select" aria-label="Default select example">
                                                <option selected>Open this select menu</option>
                                                <option value="1" >One</option>
                                                <option value="2">Two</option>
                                                <option value="3">Three</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Tên loại<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input required id = "updateSpecieName"></input>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Cân nặng trung bình con đực<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input required id = "updateBreedMaleAvg"></input>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Cân nặng trung bình con cái<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input required id = "updateBreedFemaleAvg"></input>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Thời gian lớn lên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input id="updateBreedGrownTime" required></input>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Các bệnh thường gặp</p>
                                        </div>
                                        <div className="col-md-6">
                                            <input id="updateBreedCommondisease" />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Hình ảnh</p>
                                        </div>
                                        <div className="col-md-6">
                                            <input id = "updateBreedImg" type="file" multiple accept="image/*" onChange={onImageChange} />
                                            {imageURLs.map(imageSrc => <img style={{ width: "100%", minHeight: "100%" }} alt='' src={imageSrc} />)}
                                        </div>
                                    </div>
                                </div>

                            </Modal.Body>
                            <div className='model-footer'>
                                <button style={{ width: "30%" }} className="col-md-6 btn-light" type='submit' id="confirmUpdateBreed">
                                    Cập nhật
                                </button>
                                <button style={{ width: "20%" }} onClick={handleClose} className="btn btn-light" id="cancelUpdateBreed">
                                    Huỷ
                                </button>
                            </div>
                        </form>
                    </Modal>


                    <div className='detailbody'>

                        <div className="row">

                            <div className="col-md-4">
                                <p >Tên loài
                                    <input style={{ display: "block" }} placeholder='Gà ' disabled />
                                </p>
                            </div>
                            <div className="col-md-4">

                                <p>Hình ảnh</p>
                                <img alt='chicpic' style={{ position: "absolute", width: "20%", minHeight: "10%" }} src={chicpic}></img>

                            </div>
                            <div className="col-md-4 ">
                                <div className='button'>
                                    <button id="startEditBreed" className='btn btn-light ' onClick={handleShow}>Sửa</button>
                                    <button id="startDeleteBreed" className='btn btn-light ' >Xoá</button>
                                </div>

                            </div>

                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Tên loại
                                    <input style={{ display: "block" }} placeholder='Gà ri' disabled />
                                </p>
                            </div>
                            <div className="col-md-4">

                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Cân nặng trung bình
                                    <input style={{ display: "block" }} placeholder='0.8 kg' disabled />
                                </p>
                            </div>
                            <div className="col-md-4">

                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Thời gian lớn lên
                                    <input style={{ display: "block" }} placeholder='20 ngày' disabled />
                                </p>
                            </div>
                            <div className="col-md-4">

                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Các bệnh thường gặp
                                    <textarea style={{ display: "block" }} placeholder='Cúm gia cầm, đậu gà' disabled />
                                </p>
                            </div>
                            <div className="col-md-4">

                            </div>
                        </div>


                    </div>


                </div>
            </BreedDetails>



        </Box>

    );
}