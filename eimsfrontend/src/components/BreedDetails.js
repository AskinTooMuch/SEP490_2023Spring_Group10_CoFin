import React, { useEffect, useState } from 'react';
import axios from '../api/axios';
import { useNavigate, useLocation } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
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
    //Dependency
    const [breedLoaded, setBreedLoaded] = useState(false);
    const [imageLoaded, setImageLoaded] = useState(false);
    const [loadedSpecie, setLoadedSpecie] = useState(false);

    //URL
    const SPECIE_LIST = '/api/specie/list';
    const BREED_GET = '/api/breed/detail/breedId';
    const BREED_GET_IMAGE = '/api/breed/detail/breedId/image';
    const BREED_DELETE = '/api/breed/delete';

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
    //List data
    const [specieList, setSpecieList] = useState([]);

    //DTO
    const [editBreedDTO, setEditBreedDTO] = useState(
        {
            breedId: "",
            specieId: "",
            breedName: "",
            specieName: "",
            averageWeightMale: "",
            averageWeightFemale: "",
            commonDisease: "",
            growthTime: "",
            image: "",
            status: ""
        }
    );

    const [x64image, setX64image] = useState();

    //Get sent params with data
    const { state } = useLocation();
    const { id } = state;
    //Get specie list
    useEffect(() => {
        if (loadedSpecie) return;
        loadSpecieList();
        setLoadedSpecie(true);
    }, []);

    // Request specie list and load the specie list into the dropdowns
    const loadSpecieList = async () => {
        const result = await axios.get(SPECIE_LIST,
            { params: { userId: sessionStorage.getItem("curUserId") } },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: false
            });
        setSpecieList(result.data);
        console.log(specieList);
    }

    //Get breed details
    useEffect(() => {
        console.log("Get breed");
        loadBreed();
    }, [breedLoaded]);

    const loadBreed = async () => {
        const result = await axios.get(BREED_GET,
            { params: { breedId: id } },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: false
            });
        // Set inf
        editBreedDTO.breedId = result.data.breedId;
        editBreedDTO.specieId = result.data.specieId;
        editBreedDTO.breedName = result.data.breedName;
        editBreedDTO.specieName = result.data.specieName;
        editBreedDTO.averageWeightMale = result.data.averageWeightMale;
        editBreedDTO.averageWeightFemale = result.data.averageWeightFemale;
        editBreedDTO.commonDisease = result.data.commonDisease;
        editBreedDTO.growthTime = result.data.growthTime;
        editBreedDTO.status = result.data.status;
        setBreedLoaded(true);
    }

    //Get image:
    useEffect(() => {
        console.log("Get breed");
        loadImage();
    }, [imageLoaded]);

    const loadImage = async () => {
        const result = await axios.get(BREED_GET_IMAGE,
            { params: { breedId: id } },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: false
            });
        // Set inf
        setX64image(result.data);
        setImageLoaded(true);
    }

    //handle on image change
    useEffect(() => {
        if (images.length < 1) return;
        const newImageUrls = [];
        images.forEach(image => newImageUrls.push(URL.createObjectURL(image)));
        setImageURLs(newImageUrls);
    }, [images]);

    const onImageChange = (e) => {
        setImages([...e.target.files]);
    }

    //Handle on input change
    const handleEditChange = (event, field) => {
        let actualValue = event.target.value
        setEditBreedDTO({
            ...editBreedDTO,
            [field]: actualValue
        })
    }
    //Handle submit delete breed
    const submitDeteteBreed = async () => {
        console.log("Delete breed " + id);
        try {
            const response = await axios.get(BREED_DELETE,
                { params: { breedId: editBreedDTO.breedId } },
                {
                  headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                  },
                  withCredentials: false
                });
            toast.success("Xóa loại thành công");
            navigate("/egg");
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else if (err.response?.status === 400) {
                toast.error('Yêu cầu không đúng định dạng');
            } else if (err.response?.status === 401) {
                toast.error('Unauthorized');
            }
            else {
                toast.error('Yêu cầu không đúng định dạng');
            }
        }
    }
    //Handle delete breed

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
                                            <select className="form-select" aria-label="Default select example"
                                                onChange={e => handleEditChange(e, "specieId")}>
                                                <option disabled>Open this select menu</option>
                                                { /**JSX to load options */}
                                                {specieList &&
                                                    specieList.map((item, index) => (
                                                        item.status &&
                                                        <option value={item.specieId}>{item.specieName}</option>
                                                    ))
                                                }
                                            </select>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Tên loại<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input
                                                required
                                                value={editBreedDTO.breedName}
                                                onChange={e => handleEditChange(e, "breedName")} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Cân nặng trung bình con đực<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input required style={{ width: "100%" }}
                                                value={editBreedDTO.averageWeightMale}
                                                onChange={e => handleChange(e, "averageWeightMale")} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Cân nặng trung bình con cái<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input required style={{ width: "100%" }}
                                                value={editBreedDTO.averageWeightFemale}
                                                onChange={e => handleChange(e, "averageWeightFemale")} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Thời gian lớn lên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input
                                                required
                                                value={editBreedDTO.growthTime}
                                                onChange={e => handleEditChange(e, "growthTime")} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Các bệnh thường gặp</p>
                                        </div>
                                        <div className="col-md-6">
                                            <input
                                                value={editBreedDTO.commonDisease}
                                                onChange={e => handleEditChange(e, "commonDisease")} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Hình ảnh</p>
                                        </div>
                                        <div className="col-md-6">
                                            <input id="updateBreedImg" type="file" multiple accept="image/*" onChange={onImageChange} />
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
                                    <input style={{ display: "block" }} value={editBreedDTO.specieName} placeholder='Gà ' disabled />
                                </p>
                            </div>
                            <div className="col-md-4">
                                <p>Hình ảnh</p>
                                <img style={{ position: "absolute", width: "20%", minHeight: "10%" }} alt='' src={`data:image/jpeg;base64,${x64image}`} />
                            </div>
                            {/*Buttons for edit and delete(deactivate) breed */}
                            <div className="col-md-4 ">
                                <div className='button'>
                                    <button id="startEditBreed" className='btn btn-light ' onClick={handleShow}>Sửa</button>
                                    <button id="startDeleteBreed" className='btn btn-light ' onClick={() => submitDeteteBreed()} >Xoá</button>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Tên loại
                                    <input style={{ display: "block" }} value={editBreedDTO.breedName} placeholder='Gà ri' disabled />
                                </p>
                            </div>
                            <div className="col-md-4">
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Cân nặng trung bình con đực
                                    <input style={{ display: "block" }} value={editBreedDTO.averageWeightMale} placeholder='0.8 kg' disabled />
                                </p>
                            </div>
                            <div className="col-md-4" />
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Cân nặng trung bình con cái
                                    <input style={{ display: "block" }} value={editBreedDTO.averageWeightFemale} placeholder='0.8 kg' disabled />
                                </p>
                            </div>
                            <div className="col-md-4" />
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Thời gian lớn lên
                                    <input style={{ display: "block" }} value={editBreedDTO.growthTime} placeholder='20 ngày' disabled />
                                </p>
                            </div>
                            <div className="col-md-4" />
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Các bệnh thường gặp
                                    <textarea style={{ display: "block" }} value={editBreedDTO.commonDisease} placeholder='Cúm gia cầm, đậu gà' disabled />
                                </p>
                            </div>
                            <div className="col-md-4" />
                        </div>
                    </div>
                </div>
            </BreedDetails>
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