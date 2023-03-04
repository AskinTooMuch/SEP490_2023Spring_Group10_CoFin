import React, { useState } from 'react';
import '../css/machine.css'
import { useNavigate } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import SearchIcon from '@mui/icons-material/Search';
import { Modal } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
const Customer = () => {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    let navigate = useNavigate();
    const routeChange = () => {
        let path = '/customerdetail';
        navigate(path);
    }
    return (
        <div>
            <nav className="navbar justify-content-between">
                <button className='btn btn-light' onClick={handleShow}>+ Thêm</button>
                <Modal show={show} onHide={handleClose}
                    size="lg" aria-labelledby="contained-modal-title-vcenter" centered >
                    <form>
                        <Modal.Header closeButton onClick={handleClose}>
                            <Modal.Title>Thêm </Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div className="changepass">
                                <div className="row">
                                    <div className="col-md-3">
                                        <p>Họ và tên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-3">
                                        <input required style={{ width: "100%" }} placeholder="Lê Tùng Nah" />
                                    </div>
                                    <div className="col-md-3">
                                        <p>Số nhà<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-3">
                                        <input required style={{ width: "100%" }} placeholder="0" />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-3">
                                        <p>Số điện thoại</p>
                                    </div>
                                    <div className="col-md-3">
                                        <input style={{ width: "100%" }} placeholder="0" />
                                    </div>
                                    <div className="col-md-3">
                                        <p>Phường </p>
                                    </div>
                                    <div className="col-md-3">
                                        <select className='form-control mt-1'>
                                            <option selected value="">Chọn phường</option>
                                        </select>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-3">
                                        <p>Email</p>
                                    </div>
                                    <div className="col-md-3">
                                        <input style={{ width: "100%" }} placeholder="abc@gmail" />
                                    </div>
                                    <div className="col-md-3">
                                        <p>Quận/Huyện </p>
                                    </div>
                                    <div className="col-md-3">
                                        <select className='form-control mt-1'>
                                            <option selected value="">Chọn quận</option>
                                        </select>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-3">

                                    </div>
                                    <div className="col-md-3">

                                    </div>
                                    <div className="col-md-3">
                                        <p>Thành phố </p>
                                    </div>
                                    <div className="col-md-3">
                                        <select className='form-control mt-1'>
                                            <option selected value="">Chọn thành phố</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </Modal.Body>
                        <div className='model-footer'>
                            <button style={{ width: "20%" }} type="submit" className="col-md-6 btn-light" >
                                Tạo
                            </button>
                            <button className='btn btn-light' style={{ width: "20%" }} onClick={handleClose}>
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
                            <input type="text" className="form-control" placeholder="Tìm kiếm" aria-label="Username" aria-describedby="basic-addon1" />
                        </div>
                    </form>
                </div>
            </nav>
            <div>
                <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                    <div className="u-clearfix u-sheet u-sheet-1">

                        <div className="u-expanded-width u-table u-table-responsive u-table-1">
                            <table className="u-table-entity u-table-entity-1">
                                <colgroup>
                                    <col width="5%" />
                                    <col width="35%" />
                                    <col width="30%" />
                                    <col width="30%" />
                                </colgroup>
                                <thead className="u-palette-4-base u-table-header u-table-header-1">
                                    <tr style={{ height: "21px" }}>
                                        <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Tên khách hàng</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Địa chỉ</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Số điện thoại</th>
                                    </tr>
                                </thead>
                                <tbody className="u-table-body">
                                    <tr style={{ height: "76px" }} onClick={routeChange}>
                                        <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">1</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Lê Tùng Nah</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">180d P.Thái Thịnh, Thịnh Quang, Đống Đa, Hà Nội</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">09124719471</td>
                                    </tr>
                                    <tr style={{ height: "76px" }}>
                                        <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-9">2</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Bùi Thanh Vừng</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">TT Xuân Mai, Hà Nội</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">09124719471</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    );
}

export default Customer;