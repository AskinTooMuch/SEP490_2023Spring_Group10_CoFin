import React, { useState, useEffect } from 'react';
import axios from '../api/axios';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { useNavigate } from 'react-router-dom';
const EggBatch = () => {
    const [dataLoaded, setDataLoaded] = useState(false);
    //API URLs
    const EGGBATCH_ALL = '/api/eggBatch/all'

    //Data holding objects
    const [eggBatchList, setEggBatchList] = useState([]);

    useEffect(() => {
        loadEggBatchList();
    }, [dataLoaded]);

    // Get import list
    const loadEggBatchList = async () => {
        const result = await axios.get(EGGBATCH_ALL,
            {
                params: { facilityId: sessionStorage.getItem("facilityId") },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setEggBatchList(result.data);
        setDataLoaded(true);
    }

    let navigate = useNavigate();
    const routeChange = (ebid) => {
        navigate('/eggbatchdetail', { state: { id: ebid } });
    }

    return (
        <div>
            <nav className="navbar justify-content-between">
                <div className='filter my-2 my-lg-0'>
                    <p><FilterAltIcon />Lọc</p>
                    <p><ImportExportIcon />Sắp xếp</p>
                    <form className="form-inline">
                        <div className="input-group">
                            <div className="input-group-prepend">
                                <button id="searchEggBatch"><span className="input-group-text" ><SearchIcon /></span></button>
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
                            <table className="u-table-entity u-table-entity-1" id="list_specie_table">
                                <colgroup>
                                    <col width="5%" />
                                    <col width="6%" />
                                    <col width="9%" />
                                    <col width="10%" />
                                    <col width="15%" />
                                    <col width="10%" />
                                    <col width="15%" />
                                    <col width="15%" />
                                    <col width="10%" />
                                </colgroup>
                                <thead className="u-palette-4-base u-table-header u-table-header-1">
                                    <tr style={{ height: "21px" }}>
                                        <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">STT</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2" scope="col">Mã lô</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3" scope="col">Tên loại</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4" scope="col">Mã hoá đơn</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5" scope="col">Nhà cung cấp</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6" scope="col">Số lượng</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-7" scope="col">Ngày nhập</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-8" scope="col">Tiến trình</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-9" scope="col">Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody  className="u-table-body" id="specie_list_table_body">
                                    {
                                        eggBatchList && eggBatchList.length > 0 ?
                                            eggBatchList.map((item, index) =>
                                                <tr className='trclick' style={{ height: "76px" }} onClick={() => routeChange(item.eggBatchId)}>
                                                    <th className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-1" scope="row">{index + 1}</th>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.eggBatchId}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.breedName}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.importId}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.supplierName}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.amount.toLocaleString()}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.importDate.replace("T", " ")}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">({item.progress}) Ngày {item.progressInDays}/{item.incubationPeriod}</td>
                                                    {
                                                        item.status === 0
                                                            ? <td className="u-border-1 u-border-grey-30 u-table-cell">Đã hoàn thành</td>
                                                            : ''
                                                    }
                                                    {
                                                        item.status === 1 && item.needAction === 1
                                                            ? <td className='u-border-1 u-border-grey-30 u-table-cell text-red'>Cần cập nhật</td>
                                                            : ''
                                                    }
                                                    {
                                                        item.status === 1 && item.needAction === 0 && item.progress < 5
                                                            ? <td className='u-border-1 u-border-grey-30 u-table-cell text-green'>Đang ấp</td>
                                                            : ''
                                                    }
                                                    {
                                                        item.status === 1 && item.needAction === 0 && item.progress >= 5
                                                            ? <td className='u-border-1 u-border-grey-30 u-table-cell text-green'>Đang nở</td>
                                                            : ''
                                                    }
                                                </tr>
                                            ) :
                                            <tr>
                                                <td colSpan='9'>Chưa có lô trứng nào được lưu lên hệ thống</td>
                                            </tr>
                                    }

                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </div>

    );
}
export default EggBatch;