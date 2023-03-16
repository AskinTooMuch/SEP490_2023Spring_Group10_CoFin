import React from 'react';
import { useNavigate } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import SearchIcon from '@mui/icons-material/Search';

const ImportBill = () => {
    let navigate = useNavigate();
    const routeChange = () => {
        let path = '/importbilldetail';
        navigate(path);
    }
    return (
        <>
            <nav className="navbar justify-content-between">
                <button className='btn btn-light' onClick={()=>navigate("/createimportbill")}>+ Thêm</button>
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
                                    <col width="20%" />
                                    <col width="15%" />
                                    <col width="15%" />
                                    <col width="15%" />
                                    <col width="15%" />
                                </colgroup>
                                <thead className="u-palette-4-base u-table-header u-table-header-1">
                                    <tr style={{ height: "21px" }}>
                                        <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Mã hoá đơn</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Nhà cung cấp</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Ngày nhập</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Tổng giá</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Đã thanh toán</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-7">Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody className="u-table-body">
                                    <tr style={{ height: "76px" }} onClick={routeChange}>
                                        <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">1</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">AGD14-12-53</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Phạm Anh B</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">24/01/2023</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">10.000.000 VNĐ</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">10.000.000 VNĐ</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell text-green">Đã thanh toán đủ</td>
                                    </tr>
                                    <tr style={{ height: "76px" }}>
                                        <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-9">2</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">SJA81-02-85</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">Bùi Thanh C</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">21/01/2023</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">14.000.000 VNĐ</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell">10.000.000 VNĐ</td>
                                        <td className="u-border-1 u-border-grey-30 u-table-cell text-red">Chưa thanh toán đủ</td>
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
export default ImportBill;