import React, { useEffect, useState } from 'react';
import axios from '../api/axios';
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
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
    const routeChange = () => {
        let path = '/breeddetail';
        navigate(path);
    }
    const [images, setImages] = useState([]);
    const [imageURLs, setImageURLs] = useState([]);
    //URL
    const NEW_BREED = '/api/breed/new';
    const SPECIE_LIST = '/api/specie/list';

    //Specie List
    const [loadedSpecie, setLoadedSpecie] = useState(false);
    const [specieList, setSpecieList] = useState([]);

    //Breed DTOs
    const [newBreedDTO, setNewBreedDTO] = useState(
        {
            specieId: "",
            breedName: "",
            averageWeightMale: "",
            averageWeightFemale: "",
            commonDisease: "",
            growthTime: "",
            image: "",
        }
    );
    //Get specie list
    useEffect(() => {
        if (loadedSpecie) return;
        loadSpecieList();
        setLoadedSpecie(true);
    }, []);

    // Request specie list and load the specie list into the table rows
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
        formData.append('averageWeightMale', newBreedDTO.averageWeightMale);
        formData.append('averageWeightFemale', newBreedDTO.averageWeightFemale);
        formData.append('commonDisease', newBreedDTO.commonDisease);
        formData.append('growthTime', newBreedDTO.growthTime);
        formData.append("image", newBreedDTO.image);
        console.log(formData);
        try {
            const response = await axios.post(NEW_BREED,
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: false
                }
            );
            console.log(JSON.stringify(response?.data));
            setNewBreedDTO('');
            toast.success("Tạo loài mới thành công")
            setShow(false)
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
                                    <div className="col-md-6 ">
                                        <p>Loài<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <select id="createBreedSpecie" className="form-control mt-1" aria-label="Default select example">
                                            <option disabled value="">Open this select menu</option>
                                            { /**JSX to load options */}
                                            {
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
                                        <input required id = "createSpecieName"
                                            style={{ width: "100%" }}
                                            onChange={e => handleChange(e, "breedName")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Cân nặng trung bình con đực<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input id="createBreedMaleAvg" required style={{ width: "100%" }}
                                            onChange={e => handleChange(e, "averageWeightMale")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Cân nặng trung bình con cái<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input id="createBreedFemaleAvg" required style={{ width: "100%" }}
                                            onChange={e => handleChange(e, "averageWeightFemale")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6 ">
                                        <p>Thời gian lớn lên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input id="createBreedGrownTime" required style={{ width: "100%" }}
                                            onChange={e => handleChange(e, "growthTime")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6 ">
                                        <p>Các bệnh thường gặp</p>
                                    </div>
                                    <div className="col-md-6">
                                        <textarea id="createBreedCommondisease" style={{ width: "100%" }}
                                            onChange={e => handleChange(e, "commonDisease")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6 ">
                                        <p>Hình ảnh</p>
                                    </div>
                                    <div className="col-md-6">
                                        <input id="createBreedImg" type="file" multiple accept="image/*" onChange={onImageChange} />
                                        {imageURLs.map(imageSrc => <img style={{ width: "100%", minHeight: "100%" }} alt='' src={imageSrc} />)}
                                    </div>
                                </div>
                            </div>
                        </Modal.Body>
                        <div className='model-footer'>
                            <button style={{ width: "20%" }} type="submit" className="col-md-6 btn-light" id="confirmCreateBreed">
                                Tạo
                            </button>
                            <button className='btn btn-light' style={{ width: "20%" }} onClick={handleClose} id="cancelCreateBreed">
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
                            <input id = "searchBreed" type="text" className="form-control" placeholder="Tìm kiếm" aria-label="Username" aria-describedby="basic-addon1" />
                        </div>
                    </form>
                </div>
            </nav>
            <div>
                <table className="table table-bordered">
                    <thead>
                        <tr>
                            <th scope="col">STT</th>
                            <th scope="col">Tên loại</th>
                            <th scope="col">Cân nặng trung bình</th>
                            <th scope="col">Thời gian lớn</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr className='trclick' onClick={routeChange}>

                            <th scope="row">1</th>
                            <td>Gà ri</td>
                            <td>0.8 kg</td>
                            <td>20 ngày</td>

                        </tr>
                        <tr>
                            <th scope="row">2</th>
                            <td>Vịt cỏ</td>
                            <td>0.8 kg</td>
                            <td>20 ngày</td>
                        </tr>

                    </tbody>
                </table>
            </div>
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
        </div>
    );
}
export default Breed;