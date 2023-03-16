import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import SearchIcon from '@mui/icons-material/Search';

const CreateImportBill = () => {
    let navigate = useNavigate();
    const routeChange = () => {
        let path = '/employeedetail';
        navigate(path);
    }
    const [rowsData, setRowsData] = useState([]);
    const addTableRows = () => {

        const rowsInput = {
            fullName: '',
            emailAddress: '',
            salary: ''
        }
        setRowsData([...rowsData, rowsInput])

    }
    const deleteTableRows = (index) => {
        const rows = [...rowsData];
        rows.splice(index, 1);
        setRowsData(rows);
    }

    const handleChangeRow = (index, evnt) => {
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
                            <tr>
                                <td>Gà</td>
                                <td>Gà ri</td>
                                <td>4000</td>
                                <td>3500</td>
                                <td>14.000.000</td>
                            </tr>
                            <tr>
                                <td>Gà</td>
                                <td>Gà tre</td>
                                <td>5000</td>
                                <td>7000</td>
                                <td>35.000.000</td>
                            </tr>
                            <TableRows rowsData={rowsData} handleChange={handleChangeRow} />
                        </tbody>
                    </table>
                    <div style={{ textAlign: "center" }}>
                        <button className="btn btn-light" type='button' onClick={addTableRows} >+</button>
                    </div>
                    <div style={{float:"right"}}>
                       
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

function TableRows({ rowsData, deleteTableRows, handleChangeRow }) {
    return (

        rowsData.map((data, index) => {
            const {species, breed, number, price, total  } = data;
            return (
                <tr key={index}>
                    <td><input type="text" value={species} onChange={(evnt) => (handleChangeRow(index, evnt))} name="" className="form-control" /> </td>
                    <td><input type="text" value={breed} onChange={(evnt) => (handleChangeRow(index, evnt))} name="" className="form-control" /> </td>
                    <td><input type="text" value={number} onChange={(evnt) => (handleChangeRow(index, evnt))} name="" className="form-control" /> </td>
                    <td><input type="text" value={price} onChange={(evnt) => (handleChangeRow(index, evnt))} name="" className="form-control" /> </td>
                    <td><input type="text" value={total} onChange={(evnt) => (handleChangeRow(index, evnt))} name="" className="form-control" /> </td>
                    <button className="btn btn-outline-danger"  onClick={() => (deleteTableRows(index))}>x</button>
                </tr>
            )
        })

    )

}
export default CreateImportBill;