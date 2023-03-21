import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
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
    let navigate = useNavigate();
    const [rowsData, setRowsData] = useState([]);
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
                                <select className="form-control mt-1">
                                    <option defaultValue="Chọn nhà cung cấp">Chọn nhà cung cấp</option>
                                </select>
                            </div>
                            <div className="col-md-3 " style={{ textAlign: "right" }}>
                                <p>Số điện thoại</p>
                            </div>
                            <div className="col-md-3">
                                <p>09726164856</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                            </div>
                            <div className="col-md-3" style={{ textAlign: "right" }}>
                                <p>Ngày nhập</p>
                            </div>
                            <div className="col-md-3 ">
                                <input type="date" />
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
        </>
    );
}


export default CreateImportBill;