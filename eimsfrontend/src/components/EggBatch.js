import React, { useState, useRef, useEffect } from 'react';
import axios from '../api/axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { useNavigate } from 'react-router-dom';
const EggBatch = () => {
    const userRef = useRef();
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
                <table className="table table-bordered" id="list_specie_table">
                    <thead>
                        <tr>
                            <th scope="col">STT</th>
                            <th scope="col">Mã lô</th>
                            <th scope="col">Tên lô</th>
                            <th scope="col">Mã hoá đơn</th>
                            <th scope="col">Nhà cung cấp</th>
                            <th scope="col">Số lượng</th>
                            <th scope="col">Ngày nhập</th>
                            <th scope="col">Giai đoạn</th>
                            <th scope="col">Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody id="specie_list_table_body">
                        {
                            eggBatchList && eggBatchList.length > 0 ?
                                eggBatchList.map((item, index) =>
                                    <tr className='trclick' style={{ height: "76px" }} onClick={() => routeChange(item.eggBatchId)}>
                                        <th scope="row">{index + 1}</th>
                                        <td>{item.eggBatchId}</td>
                                        <td>{item.breedName}</td>
                                        <td>{item.importId}</td>
                                        <td>{item.supplierName}</td>
                                        <td>{item.amount.toLocaleString()}</td>
                                        <td>{item.importDate}</td>
                                        <td>{item.progress}</td>
                                        {
                                            item.status == 1
                                            ?<td>Chưa hoàn thành</td>
                                            :<td>Đã hoàn thành</td>
                                        }
                                    </tr>
                                ) : 'Nothing'
                        }

                    </tbody>
                </table>
            </div>
        </div>
    );
}
export default EggBatch;