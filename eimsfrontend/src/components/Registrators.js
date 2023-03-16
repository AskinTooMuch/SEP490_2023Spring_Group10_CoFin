import React, { useState } from 'react';
import { Modal } from 'react-bootstrap';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import SearchIcon from '@mui/icons-material/Search';
const Registrators = () => {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    return (
        <>
            <nav className="navbar justify-content-between">
                <Modal show={show} onHide={handleClose}
                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered >
                    <form>
                        <Modal.Header closeButton onClick={handleClose}>
                            <Modal.Title>Thông tin cơ sở</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div className="row">
                                <div className="col-md-3">
                                    <p>Chủ cơ sở</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Nguyễn Văn A</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Tên</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Công ty TNHH Hảo Hán</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-3">
                                    <p>Số điện thoại</p>
                                </div>
                                <div className="col-md-3">
                                    <p>09124719471</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Ngày thành lập</p>
                                </div>
                                <div className="col-md-3">
                                    <p>31/03/2010</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-3">
                                    <p>Ngày sinh</p>
                                </div>
                                <div className="col-md-3">
                                    <p>....</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Mã đăng ký kinh doanh</p>
                                </div>
                                <div className="col-md-3">
                                    <p>0314537155</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-3">
                                    <p>Email</p>
                                </div>
                                <div className="col-md-3">
                                    <p>....</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Số nhà</p>
                                </div>
                                <div className="col-md-3">
                                    <p>123</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-3">
                                    <p>Địa chỉ thường trú</p>
                                </div>
                                <div className="col-md-3">
                                    <p>25 Đại lộ Hồ Chí Minh, P.Nguyễn Trãi, Thành phố Hải Dương, Hải Dương</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Thành phố</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Hải Dương</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-6">
                                </div>
                                <div className="col-md-3">
                                    <p>Quận/Huyện</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Thôn Hưng Long Xã</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-6">
                                </div>
                                <div className="col-md-3">
                                    <p>Phường</p>
                                </div>
                                <div className="col-md-3">
                                    <p>...</p>
                                </div>
                            </div> <div className="row">
                                <div className="col-md-6">
                                </div>
                                <div className="col-md-3">
                                    <p>Hotline</p>
                                </div>
                                <div className="col-md-3">
                                    <p>09123781245</p>
                                </div>
                            </div>
                        </Modal.Body>
                        <div className='model-footer'>
                            <button style={{ width: "20%" }} className="col-md-6 btn-light" onClick={handleClose}>
                                Duyệt
                            </button>
                            <button style={{ width: "20%" }} onClick={handleClose} className="btn btn-light">
                                Từ chối
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
                                    <col width="15%" />
                                    <col width="15%" />
                                    <col width="20%" />
                                    <col width="15%" />
                                    <col width="15%" />
                                    <col width="15%" />
                                </colgroup>
                                <thead className="u-palette-4-base u-table-header u-table-header-1">
                                    <tr style={{ height: "21px" }}>
                                        <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Chủ cơ sở</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Số điện thoại</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Tên cơ sở</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Ngày thành lập</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Mã đăng ký kinh doanh</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-7">Ngày đăng ký</th>
                                    </tr>
                                </thead>
                                <tbody className="u-table-body">
                                    <tr style={{ height: "76px" }} onClick={handleShow} >
                                        <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">1</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Nguyễn Hoàng Dương</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">09124719471</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Công ty TNHH Hảo Hán</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">31/03/2010</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">0314537155</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">2/2/2023</td>
                                    </tr>
                                    <tr style={{ height: "76px" }}>
                                        <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">2</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Bùi Thanh Vừng</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">09124719471</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Công ty TNHH Xuân Mai</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">24/03/2015</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">0716391951</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">3/2/2023</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </>
    );
}

export default Registrators;