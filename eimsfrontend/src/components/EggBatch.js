import React, { useState, useRef, useEffect } from 'react';
import axios from '../api/axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { useNavigate } from 'react-router-dom';
const EggBatch = () => {
    let navigate = useNavigate();
    const routeChange = () => {
        let path = '/eggbatchdetail';
        navigate(path);
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
                            <th scope="col">Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody id="specie_list_table_body">

                        <tr onClick={routeChange}>
                            <th scope="row">1</th>
                            <td>GFJ816</td>
                            <td>Trứng gà ri</td>
                            <td>HDJ71-71-25</td>
                            <td>Phạm Anh B</td>
                            <td>3000 quả</td>
                            <td>24/01/2023</td>
                            <td className='text-blue'>Đang ấp</td>
                        </tr>
                        <tr >
                            <th scope="row">2</th>
                            <td>HGD826</td>
                            <td>Trứng gà ri</td>
                            <td>HDJ71-71-25</td>
                            <td>Phạm Anh B</td>
                            <td>3000 quả</td>
                            <td>24/01/2023</td>
                            <td className='text-green'>Hoàn thành</td>
                        </tr>
                        <tr >
                            <th scope="row">3</th>
                            <td>NCH124</td>
                            <td>Trứng gà ri</td>
                            <td>HDJ71-71-25</td>
                            <td>Phạm Anh B</td>
                            <td>3000 quả</td>
                            <td>24/01/2023</td>
                            <td className='text-red'>Đang chờ</td>
                        </tr>

                    </tbody>
                </table>
            </div>
        </div>
    );
}
export default EggBatch;