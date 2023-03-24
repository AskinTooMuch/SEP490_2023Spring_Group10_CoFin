import { ImportantDevicesTwoTone } from '@mui/icons-material';
import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import { display } from '@mui/system';

function TableRows({ rowsData, deleteTableRows, handleChange }) {
    return (
        rowsData.map((data, index) => {
            const { species, breed, number, price, total } = data;
            return (
                <tr key={index}>
                    <td><input type="text" value={species} onChange={(evnt) => (handleChange(index, evnt))} name="species" className="form-control" /></td>
                    <td><input type="text" value={breed} onChange={(evnt) => (handleChange(index, evnt))} name="breed" className="form-control" /> </td>
                    <td><input type="text" value={number} onChange={(evnt) => (handleChange(index, evnt))} name="number" className="form-control" /> </td>
                    <td><input type="text" value={price} onChange={(evnt) => (handleChange(index, evnt))} name="price" className="form-control" /> </td>
                    <td><input type="text" value={total} onChange={(evnt) => (handleChange(index, evnt))} name="total" className="form-control" /> </td>
                    <td><button className="btn btn-outline-danger" type='button' onClick={() => (deleteTableRows(index))}>x</button></td>
                </tr>
            )
        })
    )
}
const CreateImportBill = () => {
    // Dependency
    const [dataLoaded, setDataLoaded] = useState(false);
    let navigate = useNavigate();
    const [rowsData, setRowsData] = useState([]);

    // API URLs
    const CREATE_IMPORT_SAVE = '/api/import/create';
    const SUPPLIER_ACTIVE_GET = '/api/supplier/allActive';

    //Add table rows
    const addTableRows = () => {
        const rowsInput = {
            species: '',
            breed: '',
            number: '',
            price: '',
            total: ''
        }
        setRowsData([...rowsData, rowsInput])
    }
    //Delete table rows
    const deleteTableRows = (index) => {
        const rows = [...rowsData];
        rows.splice(index, 1);
        setRowsData(rows);
    }
    //On Change data input
    const handleChange = (index, evnt) => {
        const { name, value } = evnt.target;
        const rowsInput = [...rowsData];
        rowsInput[index][name] = value;
        setRowsData(rowsInput);
    }
    // DTOs
    // List active
    const [supplierList, setSupplierList] = useState([])
    // Create import Dto
    const [createImportDTO, setCreateImportDTO] = useState({
        supplierId: "",
        userId: sessionStorage.getItem("curUserId"),
        facilityId: sessionStorage.getItem("facilityId"),
        importDate: "",
        eggBatchList: []
    })

    //Handle Change functions:
    //Create Import
    const handleCreateImportChange = (event, field) => {
        let actualValue = event.target.value
        setCreateImportDTO({
            ...createImportDTO,
            [field]: actualValue
        })
        if (field == "supplierId"){
            show();
        }
            
    }
    // Load supplier list
    useEffect(() => {
        loadSuppliers();
    }, [dataLoaded]);

    const loadSuppliers = async () => {
        let response;
        try {
            response = await axios.get(SUPPLIER_ACTIVE_GET,
                {
                    params: { facilityId: sessionStorage.getItem("facilityId") },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setSupplierList(response.data);
            setDataLoaded(true)
            console.log(supplierList);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                toast.error(err.response.data);
            }
        }
    }

    // Display phone number of supplier
    function show() {
        var select = document.getElementById('select');
        var id = select.options[select.selectedIndex].value;
        supplierList.map((item) => {
            item.supplierId == id 
            ?document.getElementById('phone').innerHTML = item.supplierPhone
            :console.log("");
        }
        )
    }

    return (
        <>
            <h2>Tạo hoá đơn nhập</h2>
            <form>
                <div className='container'>
                    <div className='detailbody'>
                        <div className="row">
                            <div className="col-md-3" style={{ textAlign: "right" }}>
                                <p>Nhà cung cấp</p>
                            </div>
                            <div className="col-md-3">
                                <select onChange={(e) => handleCreateImportChange(e, "supplierId")}
                                 id="select" className="form-control mt-1" required>
                                    <option defaultValue="Chọn nhà cung cấp">Chọn nhà cung cấp</option>
                                    {
                                        supplierList &&
                                            supplierList.length > 0 ?
                                            supplierList.map((item, index) =>
                                                <option value={item.supplierId}>{item.supplierName}</option>
                                            ) : ''

                                    }
                                </select>
                            </div>
                            <div className="col-md-3 " style={{ textAlign: "right" }}>
                                <p>Số điện thoại</p>
                            </div>
                            <div className="col-md-3">
                                <p id="phone" name="phone"></p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                            </div>
                            <div className="col-md-3" style={{ textAlign: "right" }}>
                                <p>Ngày nhập</p>
                            </div>
                            <div className="col-md-3 ">
                                <input required type="date" 
                                onChange={(e) => handleCreateImportChange(e, "importDate")}/>
                            </div>
                            <div className="col-md-3">
                            </div>
                        </div>
                    </div>
                    <br />
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Loài</th>
                                <th scope="col">Loại</th>
                                <th scope="col">Số lượng (trứng)</th>
                                <th scope="col">Đơn giá</th>
                                <th scope="col">Thành tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <TableRows rowsData={rowsData} deleteTableRows={deleteTableRows} handleChange={handleChange} />
                        </tbody>
                    </table>
                    <div style={{ textAlign: "center" }}>
                        <button className="btn btn-light" type='button' onClick={addTableRows} >+</button>
                    </div>
                    <div className='model-footer'>
                        <label>Tổng giá trị: 49.000.000 VNĐ</label>
                        <button style={{ width: "10%" }} className="col-md-6 btn-light" type='submit'>
                            Tạo
                        </button>
                        <button style={{ width: "10%" }} onClick={() => navigate("/order")} className="btn btn-light">
                            Huỷ
                        </button>
                    </div>
                </div>
            </form>
            <ToastContainer position="top-left"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="colored" />
        </>

    );
}


export default CreateImportBill;