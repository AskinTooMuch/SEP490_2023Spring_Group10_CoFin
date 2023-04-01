import React from 'react';
import { useNavigate } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import SearchIcon from '@mui/icons-material/Search';

const ExportBill = () => {

    let navigate = useNavigate();
    const routeChange = () => {
        navigate('/exportbilldetail');
    }

    return (
        <>
            <nav className="navbar justify-content-between">
                <button className='btn btn-light' onClick={() => navigate("/createexportbill")}>+ Thêm</button>
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
                                    <col width="10%" />
                                    <col width="20%" />
                                    <col width="22%" />
                                    <col width="13%" />
                                    <col width="13%" />
                                    <col width="17%" />
                                </colgroup>
                                <thead className="u-palette-4-base u-table-header u-table-header-1">
                                    <tr style={{ height: "21px" }}>
                                        <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Mã hoá đơn</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Khách hàng</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Ngày bán</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Tổng giá</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Đã thanh toán</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-7">Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody className="u-table-body">

                                    <tr className='trclick' style={{ height: "76px" }} onClick={() => routeChange()}>
                                        <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">1</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">AYGQ84-17-12</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Lê Tùng Nah</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">11/02/2023</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">50.000.000 VNĐ</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">50.000.000 VNĐ</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell text-green">Đã thanh toán đủ</td>
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
export default ExportBill;