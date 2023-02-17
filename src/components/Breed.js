import React, { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { Modal, Button } from 'react-bootstrap'
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
    const [images,setImages] = useState([]);
    const [imageURLs, setImageURLs] = useState([]);

    useEffect(() =>{
        if(images.length<1) return;
        const newImageUrls = [];
        images.forEach(image => newImageUrls.push(URL.createObjectURL(image)));
        setImageURLs(newImageUrls);
    }, [images]);

    const onImageChange= (e) => {
        setImages([...e.target.files]);
    }
    return (
        <>
            <nav className="navbar justify-content-between">
                <button className='btn btn-light' onClick={handleShow}>+ Thêm</button>
                <form><Modal show={show} onHide={handleClose}
                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered >
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
                                    <select class="form-select" aria-label="Default select example">
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
                                    <input />
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-6">
                                    <p>Cân nặng trung bình<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <input />
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-6 ">
                                    <p>Thời gian lớn lên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <input />
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-6 ">
                                    <p>Các bệnh thường gặp</p>
                                </div>
                                <div className="col-md-6">
                                    <input />
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-6 ">
                                    <p>Hình ảnh</p>
                                </div>
                                <div className="col-md-6">
                                    <input type="file" multiple accept="image/*" onChange={onImageChange}/>
                                    {imageURLs.map(imageSrc => <img style={{width:"100%",minHeight:"100%"}} alt='' src={imageSrc}/>)}
                                </div>
                            </div>
                        </div>

                    </Modal.Body>

                    <Modal.Footer>
                        <Button variant="danger" style={{ width: "20%" }} onClick={handleClose}>
                            Huỷ
                        </Button>

                        <Button variant="success" style={{ width: "20%" }} className="col-md-6" onClick={handleClose}>
                            Tạo
                        </Button>

                    </Modal.Footer>
                </Modal>
                </form>
                <div className='filter my-2 my-lg-0'>
                    <p><FilterAltIcon />Lọc</p>
                    <p><ImportExportIcon />Sắp xếp</p>
                    <form className="form-inline">
                        <div className="input-group">
                            <div className="input-group-prepend">
                                <button ><span className="input-group-text" ><SearchIcon /></span></button>
                            </div>
                            <input type="text" className="form-control" placeholder="Tìm kiếm" aria-label="Username" aria-describedby="basic-addon1" />
                        </div>
                    </form>
                </div>
            </nav>
            <div>
                <table class="table table-bordered">
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
        </>
    );
}
export default Breed;