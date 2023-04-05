import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import SearchIcon from '@mui/icons-material/Search';
import axios from 'axios';
import {  toast } from 'react-toastify';

const ImportBill = () => {
    // Dependency
    const [dataLoaded, setDataLoaded] = useState(false);

    //API URLs
    const IMPORT_ALL = '/api/import/allByFacility'

    //Data holding objects
    const [importList, setImportList] = useState([]);

    //Get sent params
    const { state } = useLocation();
    var mess = true;
    //
    useEffect(() => {
        if (dataLoaded) return;
        loadImportList();
        setDataLoaded(true);
    }, []);

    // Get import list
    const loadImportList = async () => {
        try {
            const result = await axios.get(IMPORT_ALL,
                { params: { facilityId: sessionStorage.getItem("facilityId") } },
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: false
                });
            setImportList(result.data);
            // Toast message
            if (mess) {
                toast.success(state);
                mess = false;
            }
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

    let navigate = useNavigate();
    const routeChange = (iid) => {
        navigate('/importbilldetail', { state: { id: iid } });
    }

    return (
        <>
            <nav className="navbar justify-content-between">
                <button className='btn btn-light' onClick={() => navigate("/createimportbill")}>+ Thêm</button>
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
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Nhà cung cấp</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Ngày nhập</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Tổng</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Đã thanh toán</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-7">Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody className="u-table-body">
                                    {
                                        importList && importList.length > 0 ?
                                            importList.map((item, index) =>
                                                <tr className='trclick' style={{ height: "76px" }} onClick={() => routeChange(item.importId)}>
                                                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">{index + 1}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.importId}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.supplierName}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.importDate.replace("T", " ")}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.total.toLocaleString()}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.paid.toLocaleString()}</td>
                                                    {
                                                        item.total == item.paid
                                                            ?
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell text-green">
                                                                Đã thanh toán đủ</td>
                                                            :
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell text-red">
                                                                Chưa thanh toán đủ</td>
                                                    }
                                                </tr>
                                            ) : 'Nothing'
                                    }
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