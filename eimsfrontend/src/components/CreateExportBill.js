import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";

function TableRows({ rowsData, deleteTableRows, handleChange }) {
    return (
        rowsData.map((data, index) => {
            const { specieId, breedId, stockId, codeId, amountLeft, amountBuy, price, vaccine } = data;
            return (
                <tr key={index}>
                    <td>
                        <select onChange={(evnt) => (handleChange(index, evnt))} name="specieId"
                            id="selectSpecie" className="form-control mt-1" >
                            <option defaultValue="chọn loài">Chọn loài</option>
                            <option value="specieId">Gà</option>
                        </select>
                    </td>
                    <td>
                        <select onChange={(evnt) => (handleChange(index, evnt))} name="breedId"
                            id="selectBreed" className="form-control mt-1" >
                            <option defaultValue="chọn loại">Chọn loại</option>
                            <option value="breedId">Gà mờ</option>
                        </select>
                    </td>
                    <td>
                        <select onChange={(evnt) => (handleChange(index, evnt))} name="stockId"
                            id="selectStock" className="form-control mt-1" >
                            <option defaultValue="chọn sản phẩm">Chọn sản phẩm</option>
                            <option value="stockId">Trứng trắng</option>
                        </select>
                    </td>
                    <td>
                        <select onChange={(evnt) => (handleChange(index, evnt))} name="codeId"
                            id="selectCode" className="form-control mt-1" >
                            <option defaultValue="chọn mã lô">Chọn mã lô</option>
                            <option value="codeId">JDH871</option>
                        </select>
                    </td>
                    <td><input type="number" value={amountLeft} onChange={(evnt) => (handleChange(index, evnt))} name="amountLeft" className="form-control" /> </td>
                    <td><input type="number" value={amountBuy} onChange={(evnt) => (handleChange(index, evnt))} name="amountBuy" className="form-control" /> </td>
                    <td><input type="number" value={price} onChange={(evnt) => (handleChange(index, evnt))} name="price" className="form-control" /> </td>
                    <td><input type="number" value={vaccine} onChange={(evnt) => (handleChange(index, evnt))} name="vaccine" className="form-control" /> </td>
                    <td><input disabled type="text" value={(amountBuy * price).toLocaleString('vi', { style: 'currency', currency: 'VND' })} name="total" className="form-control" /> </td>
                    <td><button className="btn btn-outline-danger" type='button' onClick={() => (deleteTableRows(index))}>x</button></td>
                </tr>
            )
        })
    )
}
const CreateExportBill = () => {
    let navigate = useNavigate();
    const [rowsData, setRowsData] = useState([]);

    // API URLs

    //Add table rows
    const addTableRows = () => {
        const rowsInput = {
            specieId: '',
            breedId: '',
            stockId: '',
            codeId: '',
            amountLeft: '',
            amountBuy: '',
            price: '',
            vaccine: ''
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
        if (name === "amount" || name === "price") {
            cal();
        }
    }

    // calculate total
    function cal() {
        var total = 0;
        rowsData.map((item) => {
            total += item.amountBuy * item.price;
        }
        )
        var s = total.toLocaleString('vi', { style: 'currency', currency: 'VND' });
        document.getElementById('total').innerHTML = "Tổng hóa đơn: " + s;
    }

    function curTime() {
        let x = new Date().toLocaleString();
        return x;
    }

    return (
        <>
            <h2>Tạo hoá đơn xuất</h2>
            <form >
                <div className='container'>
                    <div className='detailbody'>
                        <div className="row">
                            <div className="col-md-3" style={{ textAlign: "right" }}>
                                <p>Khách hàng</p>
                            </div>
                            <div className="col-md-3">
                                <select id="select" className="form-control mt-1" >
                                    <option value="0">Chọn khách hàng</option>
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
                            <div className="col-md-3" style={{ textAlign: "right" }}>
                                <p>Ngày bán</p>
                            </div>
                            <div className="col-md-3 ">
                                <input type="datetime-local" id="datetime" name="datetime" />
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
                                <th scope="col">Sản phẩm</th>
                                <th scope="col">Mã lô</th>
                                <th scope="col">Số lượng còn lại</th>
                                <th scope="col">Số lượng mua</th>
                                <th scope="col">Đơn giá</th>
                                <th scope="col">Vaccine</th>
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
                        <p style={{ textAlign: "right", fontWeight: "bold" }} id="total">
                        </p>
                        <button style={{ width: "10%" }} className="col-md-6 btn-light" type='submit'>
                            Tạo
                        </button>
                        <button style={{ width: "10%" }} onClick={() => navigate("/exportbill")} className="btn btn-light" type='button'>
                            Huỷ
                        </button>
                    </div>
                </div>
            </form>
        </>

    );
}


export default CreateExportBill;