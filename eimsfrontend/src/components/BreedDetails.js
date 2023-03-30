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
import ConfirmBox from './ConfirmBox';
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
    //Confirm Delete
    const [open, setOpen] = useState(false);
    function openDelete() {
        setOpen(true);
    }
    //Dependency
    const [breedLoaded, setBreedLoaded] = useState(false);
    const [imageLoaded, setImageLoaded] = useState(false);
    const [loadedSpecie, setLoadedSpecie] = useState(false);

    //URL
    const SPECIE_LIST = '/api/specie/list';
    const BREED_GET = '/api/breed/detail/breedId';
    const BREED_GET_IMAGE = '/api/breed/detail/breedId/image';
    const BREED_DELETE = '/api/breed/delete';
    const BREED_EDIT = '/api/breed/edit';

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
            status: false
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
            {
                params: { userId: sessionStorage.getItem("curUserId") },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
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
            {
                params: { breedId: id },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
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
            {
                params: { breedId: id },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
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

    const onImageChange = async (e) => {
        const file = e.target.files[0];
        editBreedDTO.image = file;
        editBreedDTO.status = true;
        console.log(editBreedDTO.image);
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
                {
                    params: { breedId: editBreedDTO.breedId },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setOpen(false);
            toast.success(response.data);
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

    //Send request new breed through Axios
    const handleEditSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData();
        formData.append('breedId', editBreedDTO.breedId);
        formData.append('specieId', editBreedDTO.specieId);
        formData.append('breedName', editBreedDTO.breedName);
        formData.append('averageWeightMale', editBreedDTO.averageWeightMale);
        formData.append('averageWeightFemale', editBreedDTO.averageWeightFemale);
        formData.append('commonDisease', editBreedDTO.commonDisease);
        formData.append('growthTime', editBreedDTO.growthTime);
        if (editBreedDTO.image !== null && editBreedDTO.image !== "") {
            formData.append("image", editBreedDTO.image);
        }
        console.log(formData);
        try {
            const response = await axios.post(BREED_EDIT,
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            console.log(JSON.stringify(response?.data));
            toast.success("Lưu thông tin loài thành công");
            window.location.reload();
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
                        <form onSubmit={handleEditSubmit}>
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
                                            <select className="form-control mt-1" aria-label="Default select example"
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
                                            <input className="form-control mt-1"
                                                value={editBreedDTO.breedName}
                                                placeholder="Gà tre/Gà ri/Gà Đông Cảo/..."
                                                onChange={e => handleEditChange(e, "breedName")} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Cân nặng trung bình con đực<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input style={{ width: "100%" }}
                                                className="form-control mt-1"
                                                value={editBreedDTO.averageWeightMale}
                                                placeholder="(kg)"
                                                type='number'
                                                step='0.01'
                                                onChange={e => handleEditChange(e, "averageWeightMale")} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Cân nặng trung bình con cái<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input style={{ width: "100%" }}
                                                className="form-control mt-1"
                                                value={editBreedDTO.averageWeightFemale}
                                                placeholder="(kg)"
                                                type='number'
                                                step='0.01'
                                                onChange={e => handleEditChange(e, "averageWeightFemale")} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Thời gian lớn lên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                        </div>
                                        <div className="col-md-6">
                                            <input
                                                className="form-control mt-1"
                                                value={editBreedDTO.growthTime}
                                                placeholder="Số ngày"
                                                type='number'
                                                onChange={e => handleEditChange(e, "growthTime")} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Các bệnh thường gặp</p>
                                        </div>
                                        <div className="col-md-6">
                                            <input
                                                className="form-control mt-1"
                                                value={editBreedDTO.commonDisease}
                                                placeholder="Đậu gà, cúm gà, khô chân, giun sán,..."
                                                onChange={e => handleEditChange(e, "commonDisease")} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6 ">
                                            <p>Hình ảnh</p>
                                        </div>
                                        <div className="col-md-6">
                                            <input
                                                className="form-control mt-1"
                                                id="updateBreedImg"
                                                type="file" multiple
                                                accept="image/*" onChange={onImageChange} />
                                            {imageURLs.map(imageSrc => <img style={{ width: "100%", minHeight: "100%" }} alt='' src={imageSrc} />)}
                                        </div>
                                    </div>
                                </div>
                            </Modal.Body>
                            <div className='model-footer'>
                                <button style={{ width: "30%" }} className="col-md-6 btn-light" type='submit' id="confirmUpdateBreed">
                                    Cập nhật
                                </button>
                                <button style={{ width: "20%" }} type='button' onClick={handleClose} className="btn btn-light" id="cancelUpdateBreed">
                                    Huỷ
                                </button>
                            </div>
                        </form>
                    </Modal>
                    <div className='detailbody'>
                        <div className="row">
                            <div className="col-md-4">
                                <p >Tên loài
                                    <input
                                        className="form-control mt-1"
                                        style={{ display: "block" }}
                                        value={editBreedDTO.specieName}
                                        placeholder='Gà/Ngan/Ngỗng' readOnly />
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
                                    <button id="startDeleteBreed" className='btn btn-light ' onClick={() => openDelete()} >Xoá</button>
                                    <ConfirmBox open={open} closeDialog={() => setOpen(false)} title={"Xóa Loại"}
                                        content={"Xác nhận xóa loại: " + editBreedDTO.breedName}
                                        deleteFunction={() => submitDeteteBreed()} />
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Tên loại
                                    <input
                                        className="form-control mt-1"
                                        style={{ display: "block" }}
                                        value={editBreedDTO.breedName}
                                        placeholder='Gà ri/gà tre,...' readOnly />
                                </p>
                            </div>
                            <div className="col-md-4">
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Cân nặng trung bình con đực
                                    <input
                                        className="form-control mt-1"
                                        style={{ display: "block" }}
                                        value={editBreedDTO.averageWeightMale}
                                        placeholder='(kg)' readOnly />
                                </p>
                            </div>
                            <div className="col-md-4" />
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Cân nặng trung bình con cái
                                    <input
                                        className="form-control mt-1"
                                        style={{ display: "block" }}
                                        value={editBreedDTO.averageWeightFemale}
                                        placeholder='(kg)' readOnly />
                                </p>
                            </div>
                            <div className="col-md-4" />
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Thời gian lớn lên
                                    <input
                                        className="form-control mt-1"
                                        style={{ display: "block" }}
                                        value={editBreedDTO.growthTime}
                                        placeholder='Số ngày' readOnly />
                                </p>
                            </div>
                            <div className="col-md-4" />
                        </div>
                        <div className="row">
                            <div className="col-md-4">
                                <p>Các bệnh thường gặp
                                    <textarea
                                        className="form-control mt-1"
                                        style={{ display: "block" }}
                                        value={editBreedDTO.commonDisease}
                                        placeholder='Cúm gia cầm, đậu gà, ...'
                                        readOnly />
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