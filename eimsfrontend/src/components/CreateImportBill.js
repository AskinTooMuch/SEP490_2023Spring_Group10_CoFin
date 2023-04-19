import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import { toast } from 'react-toastify';

function TableRows({ rowsData, deleteTableRows, handleChange, breedList }) {
    return (
        rowsData.map((data, index) => {
            const { breedId, amount, price } = data;
            return (
                <tr key={index}>
                    <td>
                        <select onChange={(evnt) => (handleChange(index, evnt))} name="breedId"
                            id="selectBreed" className="form-control mt-1" >
                            <option defaultValue="chọn loại">Chọn loại</option>
                            {
                                breedList &&
                                    breedList.length > 0 ?
                                    breedList.map((item, index) =>
                                        <option value={item.breedId}>{item.breedName}</option>
                                    ) : ''

                            }
                        </select>
                    </td>
                    <td><input type="number" step="1" value={amount} onChange={(evnt) => (handleChange(index, evnt))} name="amount" className="form-control" /> </td>
                    <td><input type="number" step="1" value={price} onChange={(evnt) => (handleChange(index, evnt))} name="price" className="form-control" /> </td>
                    <td><input disabled type="text" value={(amount * price).toLocaleString('vi', { style: 'currency', currency: 'VND' })} name="total" className="form-control" /> </td>
                    <td><button className="btn btn-outline-danger" type='button' onClick={() => (deleteTableRows(index))}>x</button></td>
                </tr>
            )
        })
    )
}
const CreateImportBill = () => {
    // Dependency
    const [dataLoaded, setDataLoaded] = useState(false);
    //
    let navigate = useNavigate();
    const [rowsData, setRowsData] = useState([]);

    // API URLs
    const CREATE_IMPORT_SAVE = '/api/import/create';
    const SUPPLIER_ACTIVE_GET = '/api/supplier/allActive';
    const BREED_LIST_GET = 'api/breed/detail/userId'

    //Add table rows
    const addTableRows = () => {
        const rowsInput = {
            breedId: '',
            amount: 0,
            price: 0
        }
        setRowsData([...rowsData, rowsInput])
    }
    //Delete table rows
    const deleteTableRows = (index) => {
        const rows = [...rowsData];
        rows.splice(index, 1);
        setRowsData(rows);

        var total = 0;
        rows.map((item) => {
            total += item.amount * item.price;
        }
        )
        var s = total.toLocaleString('vi', { style: 'currency', currency: 'VND' });
        document.getElementById('total').innerHTML = "Tổng hóa đơn: " + s;
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
    // DTOs
    // List Supplier active
    const [supplierList, setSupplierList] = useState([]);

    // Create import Dto
    const [createImportDTO, setCreateImportDTO] = useState({
        supplierId: "",
        userId: sessionStorage.getItem("curUserId"),
        facilityId: sessionStorage.getItem("facilityId"),
        importDate: "",
        eggBatchList: []
    })
    // Breed list
    const [breedList, setBreedList] = useState([])

    //Handle Change functions:
    //Create Import
    const handleCreateImportChange = (event, field) => {
        let actualValue = event.target.value
        setCreateImportDTO({
            ...createImportDTO,
            [field]: actualValue
        })
        if (field === "supplierId") {
            show();
        }
    }

    // Load supplier list
    useEffect(() => {
        if (dataLoaded) return;
        loadSuppliers();
        loadBreeds();
    }, []);

    // Load supplier list
    const loadSuppliers = async () => {
        let response;
        try {
            response = await axios.get(SUPPLIER_ACTIVE_GET,
                {
                    params: { userId: sessionStorage.getItem("curUserId") },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setSupplierList(response.data);
            setDataLoaded(true);
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

    // Load breed list
    const loadBreeds = async () => {
        let response;
        try {
            response = await axios.get(BREED_LIST_GET,
                {
                    params: { userId: sessionStorage.getItem("curUserId") },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setBreedList(response.data);
            setDataLoaded(true)
            console.log(breedList);
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

    // Handle Submit create import
    const handleCreateImportSubmit = async (event) => {
        event.preventDefault();
        console.log(rowsData);
        createImportDTO.eggBatchList = rowsData;
        let response;
        try {
            response = await axios.post(CREATE_IMPORT_SAVE,
                createImportDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setCreateImportDTO({
                supplierId: "",
                userId: sessionStorage.getItem("curUserId"),
                facilityId: sessionStorage.getItem("facilityId"),
                importDate: "",
                eggBatchList: []
            })
            navigate("/importbill", { state: "Tạo hóa đơn thành công" });

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

    // Display phone number of supplier
    function show() {
        var select = document.getElementById('select');
        var id = select.options[select.selectedIndex].value;
        if (id === 0) {
            document.getElementById('phone').innerHTML = "";
        } else {
            supplierList.map((item) => {
                if (item.supplierId == id) {
                    document.getElementById('phone').innerHTML = item.supplierPhone;
                }
            })
        }
    }

    // calculate total
    function cal() {
        var total = 0;
        rowsData.map((item) => {
            total += item.amount * item.price;
        }
        )
        var s = total.toLocaleString('vi', { style: 'currency', currency: 'VND' });
        document.getElementById('total').innerHTML = "Tổng hóa đơn: " + s;
    }

    return (
        <>
            <h2 style={{"text-align": "center"}}>Tạo hoá đơn nhập</h2>
            <form onSubmit={handleCreateImportSubmit}>
                <div className='container'>
                    <div className='detailbody'>
                        <div className="row">
                            <div className="col-md-3" style={{ textAlign: "right" }}>
                                <p>Nhà cung cấp</p>
                            </div>
                            <div className="col-md-3">
                                <select onChange={(e) => handleCreateImportChange(e, "supplierId")}
                                    id="select" className="form-control mt-1" >
                                    <option value="0">Chọn nhà cung cấp</option>
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
                            <div className="col-md-3" style={{ textAlign: "right" }}>
                                <p>Ngày nhập</p>
                            </div>
                            <div className="col-md-3 ">
                                <input type="datetime-local" id="datetime" name="datetime"
                                    onChange={(e) => handleCreateImportChange(e, "importDate")} />
                            </div>
                            <div className="col-md-3">
                            </div>
                        </div>
                    </div>
                    <br />
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Loại</th>
                                <th scope="col">Số lượng (trứng)</th>
                                <th scope="col">Đơn giá</th>
                                <th scope="col">Thành tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <TableRows rowsData={rowsData} deleteTableRows={deleteTableRows} handleChange={handleChange} breedList={breedList} />
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
                        <button style={{ width: "10%" }} onClick={() => navigate("/order")} className="btn btn-light" type='button'>
                            Huỷ
                        </button>
                    </div>
                </div>
            </form>
        </>

    );
}


export default CreateImportBill;