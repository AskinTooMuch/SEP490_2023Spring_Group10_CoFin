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
const Payroll = () => {
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
                {/* Start: form to add new payroll */}
                <Modal show={show} onHide={handleClose}
                    size="lg" aria-labelledby="contained-modal-title-vcenter" centered >
                    <form>
                        <Modal.Header closeButton onClick={handleClose}>
                            <Modal.Title>Tạo khoản tiền lương </Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div className="changepass">
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Nhân viên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <select
                                            id="select" className="form-control mt-1" >
                                            <option defaultValue="Chọn nhân viên">Chọn nhân viên</option>
                                            <option value="1">Nguyễn Hoàng Dương</option>
                                        </select>

                                    </div>
                                    <div className="col-md-6">
                                        <p>Ngày nhập <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input type="date" style={{ width: "100%" }} placeholder="0" />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Khoản tiền <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input style={{ width: "100%" }} placeholder="Tiền thưởng " />
                                    </div>
                                    <div className="col-md-6">
                                        <p>Số tiền </p>
                                    </div>
                                    <div className="col-md-6">
                                        <input style={{ width: "100%" }} placeholder="1.000.000" />
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
            <div>
                <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                    <div className="u-clearfix u-sheet u-sheet-1">
                        <div className="u-expanded-width u-table u-table-responsive u-table-1">
                            <table className="u-table-entity u-table-entity-1">
                                <colgroup>
                                    <col width="3%" />
                                </colgroup>
                                <thead className="u-palette-4-base u-table-header u-table-header-1">
                                    <tr style={{ height: "21px" }}>
                                        <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Nhân viên</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Ngày nhập</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Khoản tiền</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Số tiền</th>
                                    </tr>
                                </thead>
                                <tbody className="u-table-body">
                                    <tr style={{ height: "76px" }} onClick={handleShow2}>
                                        <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">1</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Phạm Ngọc A</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">27/10/2022</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Tiền lương Tháng 12</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell ">5.000.000 VNĐ</td>
                                    </tr>
                                    <tr style={{ height: "76px" }}>
                                        <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-9">2</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Phạm Ngọc A</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">15/11/2022</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Thưởng Tết</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell ">2.000.000 VNĐ</td>
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
                        <Modal.Title>Cập nhật khoản tiền lương</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className="changepass">
                            <div className="row">
                                <div className="col-md-6">
                                    <p>Nhân viên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <p>Nguyễn Hoàng Dương</p>
                                </div>
                                <div className="col-md-6">
                                    <p>Ngày nhập <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <input type="date" style={{ width: "100%" }} placeholder="0" />
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-6">
                                    <p>Khoản tiền <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <input style={{ width: "100%" }} placeholder="Tiền thưởng " />
                                </div>
                                <div className="col-md-6">
                                    <p>Số tiền </p>
                                </div>
                                <div className="col-md-6">
                                    <input style={{ width: "100%" }} placeholder="1.000.000" />
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
                        <button style={{ width: "30%" }} className="col-md-6 btn-light" type='submit'>
                            Cập nhật
                        </button>
                        <button style={{ width: "20%" }} onClick={handleClose2} className="btn btn-light">
                            Huỷ
                        </button>
                    </div>
                </form>
            </Modal>
            {/* End: Table for payroll */}
        </div>
    );
}
export default Payroll;