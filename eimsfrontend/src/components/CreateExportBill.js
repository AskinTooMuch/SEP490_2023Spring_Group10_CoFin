import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import { toast } from 'react-toastify';
import { Modal } from 'react-bootstrap';

function TableRows({ rowsData, deleteTableRows, handleChange, eggBatchList }) {
    return (
        rowsData.map((data, index) => {
            const { eggBatchId, breedName, phaseNumber, phaseDescription, curAmount, exportAmount, price, vaccine } = data;
            return (
                <tr key={index}>
                    <td>{eggBatchId}</td>
                    <td>{breedName}</td>
                    <td>{phaseDescription}</td>
                    <td><div name="curAmount" className="form-control" >{curAmount} </div></td>
                    <td><input type="number" value={exportAmount} onChange={(evnt) => (handleChange(index, evnt))} name="exportAmount" className="form-control" /> </td>
                    <td><input type="number" value={price} onChange={(evnt) => (handleChange(index, evnt))} name="price" className="form-control" /> </td>
                    {
                        phaseNumber == 7 || phaseNumber == 8 || phaseNumber == 9
                            ? <td><input type="number" value={vaccine} onChange={(evnt) => (handleChange(index, evnt))} name="vaccine" className="form-control" /> </td>
                            : <td><input disabled type="number" value={vaccine} name="vaccine" className="form-control" /> </td>
                    }

                    <td><input disabled type="text" value={(exportAmount * price + vaccine * exportAmount).toLocaleString('vi', { style: 'currency', currency: 'VND' })} name="total" className="form-control" /> </td>
                    <td><button className="btn btn-outline-danger" type='button' onClick={() => (deleteTableRows(index))}>x</button></td>
                </tr>
            )
        })
    )
}
const CreateExportBill = () => {
    // Dependency
    const [dataLoaded, setDataLoaded] = useState(false);
    //
    let navigate = useNavigate();
    const [rowsData, setRowsData] = useState([]);

    // API URLs
    const GET_DATA = 'api/export/getData';
    const CREATE_EXPORT_SAVE = 'api/export/create'
    const CUSTOMER_ACTIVE_GET = '/api/customer/allActive';
    const EGG_PRODUCT_AVAILBLE_ALL = "/api/eggProduct/available/all";

    // show
    const [show2, setShow2] = useState(false);
    const handleClose2 = () => setShow2(false);
    const handleShow2 = () => setShow2(true);
    //Data holding objects
    const [allList, setAllList] = useState({
        eggStocks: [],
        poultryStocks: []
    });

    //Add table rows
    const addTableRows = (item) => {
        rowsData.splice(rowsData.length, 0, {
            eggProductId: item.productId,
            curAmount: item.curAmount,
            exportAmount: 0,
            eggBatchId: item.eggBatchId,
            breedName: item.breedName,
            phaseNumber: item.phaseNumber,
            phaseDescription: item.phaseDescription,
            price: 0,
            vaccine: 0
        });
        setShow2(false);
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
        if (name === "exportAmount" || name === "price" || name === "vaccine") {
            cal();
        }
    }
    // DTOs
    // List Customer active
    const [customerList, setCustomerList] = useState([]);

    // data
    const [eggBatchList, setEggBatchList] = useState({
        eggBatch: "",
        eggProductList: []
    })

    // Create export Dto
    const [createExportDTO, setCreateExportDTO] = useState({
        customerId: "",
        userId: sessionStorage.getItem("curUserId"),
        facilityId: sessionStorage.getItem("facilityId"),
        eggProductList: []
    })

    //Handle Change functions:
    //Create Export
    const handleCreateExportChange = (event, field) => {
        let actualValue = event.target.value
        setCreateExportDTO({
            ...createExportDTO,
            [field]: actualValue
        })
        if (field === "customerId") {
            show();
        }
    }

    // Load supplier list
    useEffect(() => {
        if (dataLoaded) return;
        loadData();
        loadCustomers();
        loadAllAvailable();
        setDataLoaded(true);
    }, []);

    // Load data to create
    const loadData = async () => {
        let response;
        try {
            response = await axios.get(GET_DATA,
                {
                    params: { facilityId: sessionStorage.getItem("facilityId") },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setEggBatchList(response.data);
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

    // Load customer list
    const loadCustomers = async () => {
        let response;
        try {
            response = await axios.get(CUSTOMER_ACTIVE_GET,
                {
                    params: { userId: sessionStorage.getItem("curUserId") },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setCustomerList(response.data);
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

    // Load list egg product available
    const loadAllAvailable = async () => {
        try {
            const result = await axios.get(EGG_PRODUCT_AVAILBLE_ALL,
                {
                    params: { facilityId: sessionStorage.getItem("facilityId") },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setAllList({
                eggStocks: result.data.eggStocks,
                poultryStocks: result.data.poultryStocks
            });

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

    // Handle Submit create export
    const handleCreateExportSubmit = async (event) => {
        event.preventDefault();
        console.log(rowsData);
        createExportDTO.eggProductList = rowsData;
        console.log("dto:" + JSON.stringify(createExportDTO));
        let response;
        try {
            response = await axios.post(CREATE_EXPORT_SAVE,
                createExportDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setCreateExportDTO({
                customerId: "",
                userId: sessionStorage.getItem("curUserId"),
                facilityId: sessionStorage.getItem("facilityId"),
                eggProductList: []
            })
            navigate("/exportbill", { state: "Tạo hóa đơn thành công" });

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
        if (id == 0) {
            document.getElementById('phone').innerHTML = "";
        } else {
            customerList.map((item) => {
                if (item.customerId == id) {
                    document.getElementById('phone').innerHTML = item.customerPhone;
                }
            })
        }
    }

    // calculate total
    function cal() {
        var total = 0;
        rowsData.map((item) => {
            total += (item.exportAmount * item.price);
            total += (item.exportAmount * item.vaccine);
        }
        )
        console.log(total);
        var s = total.toLocaleString('vi', { style: 'currency', currency: 'VND' });
        document.getElementById('total').innerHTML = "Tổng hóa đơn: " + s;
    }

    return (
        <>
            <h2>Tạo hoá đơn xuất</h2>
            <form onSubmit={handleCreateExportSubmit}>
                <div className='container'>
                    <div className='detailbody'>
                        <div className="row">
                            <div className="col-md-3" style={{ textAlign: "right" }}>
                                <p>Khách hàng</p>
                            </div>
                            <div className="col-md-3">
                                <select onChange={(e) => handleCreateExportChange(e, "customerId")}
                                    id="select" className="form-control mt-1" >
                                    <option value="0">Chọn khách hàng</option>
                                    {
                                        customerList &&
                                            customerList.length > 0 ?
                                            customerList.map((item, index) =>
                                                <option value={item.customerId}>{item.customerName}</option>
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
                        </div>
                    </div>
                    <br />
                    <table className="table table-bordered">
                        <colgroup>
                            <col width="6%" />
                            <col width="10%" />
                            <col width="13%" />
                            <col width="13%" />
                            <col width="14%" />
                            <col width="14%" />
                            <col width="14%" />
                            <col width="16%" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th scope="col">Mã lô</th>
                                <th scope="col">Loại</th>
                                <th scope="col">Sản phẩm</th>
                                <th scope="col">Số lượng còn lại</th>
                                <th scope="col">Số lượng mua</th>
                                <th scope="col">Đơn giá</th>
                                <th scope="col">Vaccine</th>
                                <th scope="col">Thành tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <TableRows rowsData={rowsData} deleteTableRows={deleteTableRows} handleChange={handleChange}
                                eggBatchList={eggBatchList} />
                        </tbody>
                    </table>
                    <div style={{ textAlign: "center" }}>
                        <button className="btn btn-light" type='button' onClick={handleShow2} >+</button>
                        <Modal show={show2} onHide={handleClose2}
                            dialogClassName="modal-big"
                            aria-labelledby="contained-modal-title-vcenter"
                            centered >
                            <Modal.Header closeButton onClick={handleClose2}>
                                <Modal.Title>Sản phẩm có sẵn</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <div className="table-wrapper-scroll-y my-custom-scrollbar-exportbill">
                                    <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                                        <div className="u-clearfix u-sheet u-sheet-1">
                                            <div className="u-expanded-width u-table u-table-responsive u-table-1">
                                                <table style={{ overflowY: "scroll" }} className="u-table-entity u-table-entity-1">
                                                    {
                                                        allList.eggStocks.length === 0 && allList.poultryStocks.length === 0
                                                            ? 'Hiện tại không có sản phẩm nào'
                                                            :
                                                            <>
                                                                <thead className="u-palette-4-base u-table-header u-table-header-1">
                                                                    <tr style={{ height: "21px" }}>
                                                                        <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Loại</th>
                                                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Mã lô</th>
                                                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Sản phẩm</th>
                                                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Số lượng trong kho</th>
                                                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Ngày xuất</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody className="u-table-body">

                                                                    {
                                                                        allList.eggStocks && allList.eggStocks.length > 0
                                                                            ? allList.eggStocks.map((item, index) =>
                                                                                <tr onClick={() => addTableRows(item)} className='trclick' style={{ height: "76px" }} >
                                                                                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-1">{index + 1}</td>
                                                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.breedName}</td>
                                                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.eggBatchId}</td>
                                                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.phaseDescription}</td>
                                                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.curAmount}</td>
                                                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.incubationDate.replace("T", " ")}</td>
                                                                                </tr>
                                                                            )
                                                                            : ''
                                                                    }
                                                                    {
                                                                        allList.poultryStocks && allList.poultryStocks.length > 0
                                                                            ? allList.poultryStocks.map((item, index) =>
                                                                                <tr onClick={() => addTableRows(item)} className='trclick' style={{ height: "76px" }} >
                                                                                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">{index + 1}</td>
                                                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.breedName}</td>
                                                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.eggBatchId}</td>
                                                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.phaseDescription}</td>
                                                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.curAmount}</td>
                                                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.incubationDate.replace("T", " ")}</td>
                                                                                </tr>
                                                                            )
                                                                            : ''
                                                                    }
                                                                </tbody>
                                                            </>
                                                    }
                                                </table>
                                            </div>
                                        </div>
                                    </section>
                                </div>
                            </Modal.Body>
                        </Modal>
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


export default CreateExportBill;