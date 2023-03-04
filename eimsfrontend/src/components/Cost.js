import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { Modal, button } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from 'axios';
//Toast
import { ToastContainer, toast } from 'react-toastify';
const Cost = () => {
    //Show-hide Popup
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [show2, setShow2] = useState(false);
    const handleClose2 = () => setShow2(false);
    const handleShow2 = () => setShow2(true);
    //Data holding objects

    return (
        <div>
            <nav className="navbar justify-content-between">
                <button className='btn btn-light' onClick={handleShow}>+ Thêm</button>
                {/* Start: form to add new supplier */}
                <Modal show={show} onHide={handleClose}
                    size="lg" aria-labelledby="contained-modal-title-vcenter" centered >
                    <form>
                        <Modal.Header closeButton onClick={handleClose}>
                            <Modal.Title>Thêm </Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div className="changepass">
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Tên chi phí<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input required style={{ width: "100%" }} placeholder="Tiền sửa máy tháng 1/2023" />
                                    </div>
                                    <div className="col-md-6">
                                        <p>Tổng chi phí <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input required style={{ width: "100%" }} placeholder="0" />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Đã thanh toán</p>
                                    </div>
                                    <div className="col-md-6">
                                        <input style={{ width: "100%" }} placeholder="0" />
                                    </div>
                                    <div className="col-md-6">
                                        <p>Ghi chú </p>
                                    </div>
                                    <div className="col-md-6">
                                        <textarea style={{ width: "100%" }} />
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
                {/* End: form to add new supplier */}
                {/* Start: Filter and sort table */}
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
                {/* End: Filter and sort table */}
            </nav>
            {/* Start: Table for supplier list */}
            <div>
                <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                    <div className="u-clearfix u-sheet u-sheet-1">

                        <div className="u-expanded-width u-table u-table-responsive u-table-1">
                            <table className="u-table-entity u-table-entity-1">
                                <colgroup>
                                    <col width="20%" />
                                    <col width="20%" />
                                    <col width="20%" />
                                    <col width="20%" />
                                    <col width="20%" />
                                </colgroup>
                                <thead className="u-palette-4-base u-table-header u-table-header-1">
                                    <tr style={{ height: "21px" }}>
                                        <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">Tên chi phí</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Ngày nhập</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Tổng chi phí</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Đã thanh toán</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody className="u-table-body">
                                    <tr style={{ height: "76px" }} onClick={handleShow2}>
                                        <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">Tiền mua máy ấp</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">27/10/2022</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">100.000.000 VNĐ</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">100.000.000 VNĐ</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell text-green">Đã thanh toán đủ</td>
                                    </tr>
                                    <tr style={{ height: "76px" }}>
                                        <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-9">Tiền mua máy nở</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">15/11/2022</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">120.000.000 VNĐ</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">100.000.000 VNĐ</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell text-red">Chưa thanh toán đủ</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>

            </div>

            <Modal show={show2} onHide={handleClose2}
                size="lg" aria-labelledby="contained-modal-title-vcenter" centered >
                <form>
                    <Modal.Header closeButton onClick={handleClose2}>
                        <Modal.Title>Cập nhật thông tin chi phí</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className="changepass">
                            <div className="row">
                                <div className="col-md-6">
                                    <p>Tên chi phí<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <input required style={{ width: "100%" }} placeholder="Tiền mua máy nở" />
                                </div>
                                <div className="col-md-6">
                                    <p>Tổng chi phí <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <input required style={{ width: "100%" }} placeholder="120.000.000" />
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-6">
                                    <p>Đã thanh toán</p>
                                </div>
                                <div className="col-md-6">
                                    <input style={{ width: "100%" }} placeholder="100.000.000" />
                                </div>
                                <div className="col-md-6">
                                    <p>Ghi chú</p>
                                </div>
                                <div className="col-md-6">
                                    <textarea style={{ width: "100%" }} />
                                </div>
                            </div>
                        </div>
                    </Modal.Body>
                    <div className='model-footer'>
                        <button style={{ width: "30%" }} className="col-md-6 btn-light" type='submit'>
                            Cập nhật
                        </button>
                        <button style={{ width: "20%" }} onClick={handleClose2} className="btn btn-light">
                            Huỷ
                        </button>
                    </div>
                </form>
            </Modal>

            {/* End: Table for supplier list */}

        </div>
    );
}
export default Cost;