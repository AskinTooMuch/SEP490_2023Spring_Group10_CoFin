import React, { useEffect, useState } from 'react';
import axios from '../api/axios';
import { useNavigate } from "react-router-dom";
import { toast } from 'react-toastify';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { Modal } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
const Breed = () => {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    let navigate = useNavigate();
    const routeChange = (index) => {
        navigate('/breeddetail', { state: { id: index } });
    }
    const [images, setImages] = useState([]);
    const [imageURLs, setImageURLs] = useState([]);
    //URL
    const NEW_BREED = '/api/breed/new';
    const SPECIE_LIST = '/api/specie/list';
    const BREED_LIST = '/api/breed/detail/userId';

    //Specie List
    const [loadedSpecie, setLoadedSpecie] = useState(false);
    const [specieList, setSpecieList] = useState([]);
    //BreedList
    const [loadedBreed, setLoadedBreed] = useState(false);
    const [breedList, setBreedList] = useState([]);

    //Breed DTOs
    const [newBreedDTO, setNewBreedDTO] = useState(
        {
            specieId: "",
            breedName: "",
            averageWeightMale: "",
            averageWeightFemale: "",
            commonDisease: "",
            growthTime: "",
            image: null
        }
    );
    //Get specie list
    useEffect(() => {
        if (loadedSpecie) return;
        loadSpecieList();
        setLoadedSpecie(true);
    }, []);

    // Request specie list and load the specie list into the dropdowns
    const loadSpecieList = async () => {
        try {
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

    //Get breed list
    useEffect(() => {
        if (loadedBreed) return;
        loadBreedList();
        setLoadedBreed(true);
    })

    // Request breed list and load the breed list into the table rows
    const loadBreedList = async () => {
        try {
            const result = await axios.get(BREED_LIST,
                {
                    params: { userId: sessionStorage.getItem("curUserId") },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setBreedList(result.data);
            console.log(breedList);
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

    //Handle on image change
    useEffect(() => {
        if (images.length < 1) return;
        const newImageUrls = [];
        images.forEach(image => newImageUrls.push(URL.createObjectURL(image)));
        setImageURLs(newImageUrls);
    }, [images]);

    const onImageChange = async (e) => {
        const file = e.target.files[0];
        newBreedDTO.image = file;
        console.log(newBreedDTO.image);
        setImages([...e.target.files]);
    }

    //Handle on input change
    const handleChange = (event, field) => {
        let actualValue = event.target.value
        setNewBreedDTO({
            ...newBreedDTO,
            [field]: actualValue
        })
    }

    //Send request new breed through Axios
    const handleNewSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData();
        formData.append('specieId', newBreedDTO.specieId);
        formData.append('breedName', newBreedDTO.breedName);
        if (newBreedDTO.averageWeightMale === '') {
            formData.append('averageWeightMale', 0);
        } else {
            formData.append('averageWeightMale', newBreedDTO.averageWeightMale);
        }
        if (newBreedDTO.averageWeightFemale === '') {
            formData.append('averageWeightFemale', 0);
        } else {
            formData.append('averageWeightFemale', newBreedDTO.averageWeightFemale);
        }
        if (newBreedDTO.growthTime === '') {
            formData.append('growthTime', 0);
        } else {
            formData.append('growthTime', newBreedDTO.growthTime);
        }
        formData.append('commonDisease', newBreedDTO.commonDisease);
        if (newBreedDTO.image !== null) {
            formData.append('image', newBreedDTO.image);
        }
        try {
            const response = await axios.post(NEW_BREED,
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            loadBreedList();
            toast.success("Tạo loài mới thành công");
            setShow(false);
            setNewBreedDTO({
                specieId: "",
                breedName: "",
                averageWeightMale: "",
                averageWeightFemale: "",
                commonDisease: "",
                growthTime: "",
                image: null
            });
            setImageURLs([]);
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

    const handleNewCancel = () => {
        handleClose();
        setNewBreedDTO({
            specieId: "",
            breedName: "",
            averageWeightMale: "",
            averageWeightFemale: "",
            commonDisease: "",
            growthTime: "",
            image: null
        });
        setImageURLs([]);
    }

    return (
        <div>
            <nav className="navbar justify-content-between">
                <button className='btn btn-light' onClick={handleShow} id="startCreateBreed">+ Thêm</button>
                <Modal show={show} onHide={handleClose}
                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered >
                    <form onSubmit={handleNewSubmit}>
                        <Modal.Header closeButton onClick={handleClose}>
                            <Modal.Title>Thêm loại trứng</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div className="changepass">
                                <div className="row">
                                    <div className="col-md-4">
                                        <label className='col-form-label'>Loài&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-8">
                                        <select className="form-control mt-1" aria-label="Default select example"
                                            onChange={e => handleChange(e, "specieId")}>
                                            <option disabled value="" selected>Chọn loài</option>
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
                                    <div className="col-md-4">
                                        <label className='col-form-label'>Tên loại&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-8">
                                        <input className="form-control mt-1" id="createSpecieName"
                                            style={{ width: "100%" }}
                                            onChange={e => handleChange(e, "breedName")}
                                            placeholder="Gà tre/Gà ri/Gà Đông Cảo/..."
                                        />
                                    </div>
                                </div>
                                <div className='row'>
                                    <div className='col-md-12'>
                                        <label className='col-form-label'>Cân nặng trung bình: </label>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-4">
                                        <span className='col-form-label'>Con đực (kg)&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></span>
                                    </div>
                                    <div className="col-md-8">
                                        <input className="form-control mt-1" id="createBreedMaleAvg" style={{ width: "100%" }}
                                            placeholder="kg"
                                            type='number'
                                            step='0.01'
                                            onChange={e => handleChange(e, "averageWeightMale")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-4">
                                        <span className='col-form-label'>Con cái (kg)&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></span>
                                    </div>
                                    <div className="col-md-8">
                                        <input className="form-control mt-1" id="createBreedFemaleAvg" style={{ width: "100%" }}
                                            placeholder="kg"
                                            type='number'
                                            step='0.01'
                                            onChange={e => handleChange(e, "averageWeightFemale")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-4">
                                        <label className='col-form-label'>Thời gian lớn lên (ngày)&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                    </div>
                                    <div className="col-md-8">
                                        <input className="form-control mt-1" id="createBreedGrownTime" style={{ width: "100%" }}
                                            placeholder="ngày"
                                            type='number'
                                            onChange={e => handleChange(e, "growthTime")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-4">
                                        <label className='col-form-label'>Các bệnh thường gặp</label>
                                    </div>
                                    <div className="col-md-8">
                                        <textarea className="form-control mt-1" id="createBreedCommondisease" style={{ width: "100%" }}
                                            onChange={e => handleChange(e, "commonDisease")}
                                        />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-4">
                                        <label className='col-form-label'>Hình ảnh</label>
                                    </div>
                                    <div className="col-md-8">
                                        <input className="form-control mt-1" id="createBreedImg" type="file" multiple accept="image/*" onChange={onImageChange} />
                                        {imageURLs.map(imageSrc => <img style={{ maxWidth: "60vw", maxHeight: "40vh" }} alt='' src={imageSrc} />)}
                                    </div>
                                </div>
                            </div>
                        </Modal.Body>
                        <div className='model-footer'>
                            <button style={{ width: "20%" }} type="submit" className="col-md-6 btn-light mt-2" id="confirmCreateBreed">
                                Tạo
                            </button>
                            <button className='btn btn-light' type="button mt-2" style={{ width: "20%" }} onClick={handleNewCancel} id="cancelCreateBreed">
                                Huỷ
                            </button>
                        </div>
                    </form>
                </Modal>

                <div className='filter my-2 my-lg-0'>
                    <p><FilterAltIcon />Lọc</p>
                    <p><ImportExportIcon />Sắp xếp</p>
                    <form className="form-inline">
                        <div className="input-group">
                            <div className="input-group-prepend">
                                <button ><span className="input-group-text" ><SearchIcon /></span></button>
                            </div>
                            <input id="searchBreed" type="text" className="form-control" placeholder="Tìm kiếm" aria-label="Username" aria-describedby="basic-addon1" />
                        </div>
                    </form>
                </div>
            </nav>
            <div>
                <table className="table table-bordered">
                    <thead>
                        <tr>
                            <th scope="col">STT</th>
                            <th scope="col">Tên loài</th>
                            <th scope="col">Tên loại</th>
                            <th scope="col">Cân nặng con đực</th>
                            <th scope="col">Cân nặng con cái</th>
                            <th scope="col">Thời gian lớn</th>
                        </tr>
                    </thead>
                    <tbody>
                        { /**JSX to load breed list */}
                        {breedList &&
                            breedList.map((item, index) => (
                                item.status &&
                                <tr className='trclick' onClick={() => routeChange(item.breedId)} key={item.breedId}>
                                    <th scope="row">{index + 1}</th>
                                    <td>{item.specieName}</td>
                                    <td>{item.breedName}</td>
                                    <td>{item.averageWeightMale}</td>
                                    <td>{item.averageWeightFemale}</td>
                                    <td>{item.growthTime}</td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
            
        </div>
    );
}
export default Breed;