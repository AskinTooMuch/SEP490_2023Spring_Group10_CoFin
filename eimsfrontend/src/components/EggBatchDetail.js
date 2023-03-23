import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, useLocation } from "react-router-dom";
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import '../css/machine.css'
import { Modal } from 'react-bootstrap';
import ClearIcon from '@mui/icons-material/Clear';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import { padding } from '@mui/system';

function EggBatchDetail(props) {
    const { children, value, index, ...other } = props;
    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography component="span">{children}</Typography>
                </Box>
            )}
        </div>
    );
}

EggBatchDetail.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

export default function BasicTabs() {
    // Dependency
    const [eggBatchLoaded, setEggBatchLoaded] = useState(false);

    //URL
    const EGGBATCH_GET = "/api/eggBatch/get";
    const EGGBATCH_UPDATE = "/api/eggBatch/update";
    const MACHINE_NOT_FULL_GET = "/api/machine/notFull";

    //Show-hide Popup
    const [value, setValue] = React.useState(0);
    const navigate = useNavigate();
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [show2, setShow2] = useState(false);
    const handleClose2 = () => setShow2(false);
    const handleShow2 = () => setShow2(true);

    // Handle Change
    const handleChangeData = (index, evnt) => {
        const { name, value } = evnt.target;
        const rowsInput = [...rowsData];
        rowsInput[index][name] = value;
        setRowsData(rowsInput);
    }
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const [rowsData, setRowsData] = useState([]);
    const addTableRows = () => {
        const rowsInput = {
            machineLocation: '',
            vacantPosition: '',
            currEgg: '',
            updateEgg: ''
        }
        setRowsData([...rowsData, rowsInput])
        setShow2(false)
    }
    const deleteTableRows = (index) => {
        const rows = [...rowsData];
        rows.splice(index, 1);
        setRowsData(rows);
    }

    //Get sent params
    const { state } = useLocation();
    const { id } = state;

    // DTO
    // Egg batch detail
    const [eggBatchDetail, setEggBatchDetail] = useState({
        eggBatchId: "",
        importId: "",
        supplierId: "",
        supplierName: "",
        importDate: "",
        specieId: "",
        specieName: "",
        breedId: "",
        breedName: "",
        growthTime: "",
        amount: "",
        progress: "",
        status: "",
        eggProductList: [],
        machineList: []
    })

    // Update egg batch
    const [updateEggBatchDTO, setUpdateEggBatchDTO] = useState({
        eggBatchId: "",
        phaseNumber: "",
        eggBatchId: "",
        phaseNumber: "",
        eggWasted: "",
        amount: "",
        eggLocationUpdateEggBatchDTOS: [],
        done: ""
    })

    // Remain egg in updating
    const [remain, setRemain] = useState({
        remain: ""
    })

    // List Machine no full update location
    const [machineNotFullList, setMachineNotFullList] = useState([])

    //Get import details
    useEffect(() => {
        loadEggBatch();
    }, [eggBatchLoaded]);

    const loadEggBatch = async () => {
        const result = await axios.get(EGGBATCH_GET,
            { params: { eggBatchId: id } },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: false
            });
        // Set inf
        eggBatchDetail.eggBatchId = result.data.eggBatchId;
        eggBatchDetail.importId = result.data.importId;
        eggBatchDetail.supplierId = result.data.supplierId;
        eggBatchDetail.supplierName = result.data.supplierName;
        eggBatchDetail.importDate = result.data.importDate;
        eggBatchDetail.specieId = result.data.specieId;
        eggBatchDetail.specieName = result.data.specieName;
        eggBatchDetail.breedId = result.data.breedId;
        eggBatchDetail.breedName = result.data.breedName;
        eggBatchDetail.growthTime = result.data.growthTime;
        eggBatchDetail.amount = result.data.amount;
        eggBatchDetail.progress = result.data.progress;
        eggBatchDetail.status = result.data.status;
        eggBatchDetail.eggProductList = result.data.eggProductList;
        eggBatchDetail.machineList = result.data.machineList;

        if (result.data.progress == 0) {
            remain.remain = result.data.amount;
        }
        if (result.data.progress != 0) {
            remain.remain = eggBatchDetail.eggProductList[2].curAmount + eggBatchDetail.eggProductList[6].curAmount;
        }

        updateEggBatchDTO.eggLocationUpdateEggBatchDTOS = result.data.machineList;
        setEggBatchLoaded(true);
    }

    const routeChange = (mid) => {
        navigate('/machinedetail', { state: { id: mid } });
    }

    function cal() {
        let a = Number(document.getElementById("eggWasted").value);
        let b = Number(document.getElementById("amount").value);

        let sum = remain.remain - (a + b);
        document.getElementById('remain').innerHTML = sum;
    }
    //Handle Change functions:
    //Update EggBatch
    const handleUpdateEggBatchChange = (event, field) => {
        let actualValue = event.target.value
        setUpdateEggBatchDTO({
            ...updateEggBatchDTO,
            [field]: actualValue
        })
        if (field == "amount" || field == "eggWasted") {
            cal();
        }
    }

    // Handle get to update egg batch funtion


    // Handle Submit functions

    return (

        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                <Tabs sx={{
                    '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
                    '& .Mui-selected': { color: "#d25d19" },
                }} value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Lô Trứng" {...a11yProps(0)} />
                    <Tab style={{ textTransform: "capitalize" }} label="Trở về trang Trứng" {...a11yProps(1)} onClick={() => navigate("/egg")} />
                </Tabs>
            </Box>
            <EggBatchDetail value={value} index={0}>
                <div className='container'>
                    <h3 style={{ textAlign: "center" }}>Thông tin lô trứng</h3>
                    <div className='detailbody'>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Mã lô</p>
                            </div>
                            <div className="col-md-3">
                                <p>{eggBatchDetail.eggBatchId}</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Mã hoá đơn</p>
                            </div>
                            <div className="col-md-3">
                                <p>{eggBatchDetail.importId}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Nhà cung cấp</p>
                            </div>
                            <div className="col-md-3">
                                <p>{eggBatchDetail.supplierName}</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Ngày nhập</p>
                            </div>
                            <div className="col-md-3">
                                <p>{eggBatchDetail.importDate}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Tên loài</p>
                            </div>
                            <div className="col-md-3">
                                <p>{eggBatchDetail.specieName}</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Tên loại</p>
                            </div>
                            <div className="col-md-3">
                                <p>{eggBatchDetail.breedName}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Số lượng</p>
                            </div>
                            <div className="col-md-3">
                                <p>{eggBatchDetail.amount}</p>
                            </div>
                            <div className="col-md-3 ">
                                <p></p>
                            </div>
                            <div className="col-md-3">
                                <p></p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Giai đoạn hiện tại {eggBatchDetail.eggProductList.length}</p>
                            </div>
                            <div className="col-md-3">
                                <p>
                                    {
                                        eggBatchDetail.progress === 0
                                            ? 'Chưa ấp'
                                            : ''
                                    }
                                    {
                                        eggBatchDetail.progress < 5 && eggBatchDetail.progress !== 0
                                            ? 'Đang ấp'
                                            : ''
                                    }
                                    {
                                        eggBatchDetail.progress > 5
                                            ? 'Đang nở'
                                            : ''
                                    }
                                </p>
                            </div>
                        </div>
                    </div>
                    <div>
                        <p></p>
                    </div>
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col"></th>
                                <th scope="col" width="8%">Hao hụt</th>
                                <th scope="col" width="8%">Trứng dập</th>
                                <th scope="col" width="8%">Trứng đang ấp</th>
                                <th scope="col" width="8%">Trứng trắng</th>
                                <th scope="col" width="8%">Trứng loãng</th>
                                <th scope="col" width="8%">Trứng lộn</th>
                                <th scope="col" width="8%">Trứng đang nở</th>
                                <th scope="col" width="8%">Trứng tắc</th>
                                <th scope="col" width="8%">Con nở</th>
                                <th scope="col" width="8%">Con đực</th>
                                <th scope="col" width="8%">Con cái</th>
                            </tr>
                        </thead>

                        {
                            eggBatchDetail.eggProductList.length > 0 ?
                                <tbody>
                                    <tr>
                                        <th scope="row">Ban đầu</th>
                                        <td>{eggBatchDetail.eggProductList[0].amount}</td>
                                        <td>{eggBatchDetail.eggProductList[1].amount}</td>
                                        <td>{eggBatchDetail.eggProductList[2].amount}</td>
                                        <td>{eggBatchDetail.eggProductList[3].amount}</td>
                                        <td>{eggBatchDetail.eggProductList[4].amount}</td>
                                        <td>{eggBatchDetail.eggProductList[5].amount}</td>
                                        <td>{eggBatchDetail.eggProductList[6].amount}</td>
                                        <td>{eggBatchDetail.eggProductList[7].amount}</td>
                                        <td>{eggBatchDetail.eggProductList[8].amount}</td>
                                        <td>{eggBatchDetail.eggProductList[9].amount}</td>
                                        <td>{eggBatchDetail.eggProductList[10].amount}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Hiện tại</th>
                                        <td>{eggBatchDetail.eggProductList[0].curAmount}</td>
                                        <td>{eggBatchDetail.eggProductList[1].curAmount}</td>
                                        <td>{eggBatchDetail.eggProductList[2].curAmount}</td>
                                        <td>{eggBatchDetail.eggProductList[3].curAmount}</td>
                                        <td>{eggBatchDetail.eggProductList[4].curAmount}</td>
                                        <td>{eggBatchDetail.eggProductList[5].curAmount}</td>
                                        <td>{eggBatchDetail.eggProductList[6].curAmount}</td>
                                        <td>{eggBatchDetail.eggProductList[7].curAmount}</td>
                                        <td>{eggBatchDetail.eggProductList[8].curAmount}</td>
                                        <td>{eggBatchDetail.eggProductList[9].curAmount}</td>
                                        <td>{eggBatchDetail.eggProductList[10].curAmount}</td>
                                    </tr>
                                </tbody>
                                : <tbody>
                                    <tr>
                                        <th scope="row">Ban đầu</th>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Hiện tại</th>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                    </tr>
                                </tbody>

                        }

                    </table>
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Vị trí</th>
                                <th scope="col">Số lượng</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                eggBatchDetail.machineList && eggBatchDetail.machineList.length > 0 ?
                                    eggBatchDetail.machineList.map((item, index) =>
                                        <tr className='trclick' style={{ height: "76px", textAlign: "left" }} onClick={() => routeChange(item.eggBatchId)}>
                                            <th scope="row">{item.machineName}</th>
                                            <td>{item.amount}</td>
                                        </tr>
                                    ) : ''
                            }
                        </tbody>
                    </table>
                </div>
                <div style={{ textAlign: "center" }}>
                    <button className='btn btn-light' id="startUpdateEggBatch" onClick={handleShow}>Cập nhật</button>
                    <Modal show={show} onHide={handleClose}
                        aria-labelledby="contained-modal-title-vcenter"
                        centered
                        dialogClassName="modal-90w">
                        <form >
                            <Modal.Header closeButton onClick={handleClose}>
                                <Modal.Title>Cập nhật Thông tin lô trứng</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <form>
                                    <div className='container'>
                                        <div className='detailbody'>
                                            <div className="row">
                                                <div className="col-md-3" >
                                                    <label>Trứng hao hụt: 0 + </label>
                                                </div>
                                                <div className="col-md-3">
                                                    <input className='form-control' id="eggWasted" name="eggWasted" type="number"
                                                    required onChange={(e) => handleUpdateEggBatchChange(e, "eggWasted")} />
                                                </div>
                                                <div className="col-md-3 ">
                                                    <label>Trứng loại</label>
                                                </div>
                                                <div className="col-md-3">
                                                    <select required onChange={(e) => handleUpdateEggBatchChange(e, "phaseNumber")} id="" className="form-select" aria-label="Default select example">
                                                        <option value="">Chọn</option>
                                                        <option value="0">Trứng vỡ/dập</option>
                                                        <option value="2">Trứng trắng/tròn, trứng không có phôi</option>
                                                        <option value="3">Trứng loãng/tàu, phôi chết non</option>
                                                        <option value="4">Trứng lộn</option>
                                                        <option value="6">Trứng tắc</option>
                                                        <option value="8">Con đực</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <br />
                                            <div className="row">
                                                <div className="col-md-3" >
                                                    <label>Số lượng</label>
                                                </div>
                                                <div className="col-md-3">
                                                    <input className='form-control' id="amount" name="amount" type="number"
                                                    required onChange={(e) => handleUpdateEggBatchChange(e, "amount")} />
                                                </div>
                                                <div className="col-md-3 ">
                                                    <label>Số lượng trứng còn lại</label>
                                                </div>
                                                <div className="col-md-3">
                                                    <p id="remain" name="remain">{remain.remain}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <br />
                                        <div className='clear'>
                                            <table className="table table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th scope="col">Máy</th>
                                                        <th scope="col">Số lượng vị trí trống</th>
                                                        <th scope="col">Số lượng trứng hiện tại</th>
                                                        <th scope="col">Cập nhật số trứng</th>
                                                    </tr>
                                                </thead>
                                                <tbody >
                                                    {
                                                        updateEggBatchDTO.eggLocationUpdateEggBatchDTOS &&
                                                            updateEggBatchDTO.eggLocationUpdateEggBatchDTOS.length > 0 ?
                                                            updateEggBatchDTO.eggLocationUpdateEggBatchDTOS.map((item, index) =>
                                                                <tr className='trclick' style={{ height: "76px", textAlign: "left" }} onClick={() => routeChange(item.eggBatchId)}>
                                                                    <th scope="row">{item.machineName}</th>
                                                                    <td>{item.maxCapacity - item.curCapacity}</td>
                                                                    <td>{item.amount}</td>
                                                                    <td>{item.amount}</td>
                                                                </tr>
                                                            ) : ''
                                                    }
                                                    <TableRows rowsData={rowsData} deleteTableRows={deleteTableRows} handleChangeData={handleChangeData} />
                                                </tbody>
                                            </table>
                                        </div>
                                        <div style={{ textAlign: "center" }}>
                                            <button className="btn btn-light" type='button' onClick={handleShow2} >+</button>
                                            <Modal show={show2} onHide={handleClose2}
                                                size="lg"
                                                aria-labelledby="contained-modal-title-vcenter"
                                                centered >
                                                <Modal.Header closeButton onClick={handleClose2}>
                                                    <Modal.Title>Những máy nở còn trống</Modal.Title>
                                                </Modal.Header>
                                                <Modal.Body>
                                                    <div className="table-wrapper-scroll-y my-custom-scrollbar">
                                                        <table style={{ overflowY: "scroll" }} className="table table-bordered">
                                                            <thead>
                                                                <tr>
                                                                    <th scope="col">Vị trí máy</th>
                                                                    <th scope="col">Số lượng vị trí trống</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody >
                                                                <tr>
                                                                    <td>Máy 24</td>
                                                                    <td>1000</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Máy 25</td>
                                                                    <td>1000</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Máy 26</td>
                                                                    <td>1000</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Máy 26</td>
                                                                    <td>1000</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Máy 26</td>
                                                                    <td>1000</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>Máy 26</td>
                                                                    <td>1000</td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </Modal.Body>
                                                <div className='model-footer'>
                                                    <button style={{ width: "30%" }} className="col-md-6 btn-light" onClick={addTableRows}>
                                                        Xác nhận
                                                    </button>
                                                    <button style={{ width: "20%" }} onClick={handleClose2} className="btn btn-light">
                                                        Huỷ
                                                    </button>
                                                </div>
                                            </Modal>
                                        </div>
                                    </div>
                                </form>
                            </Modal.Body>
                            <div className='model-footer'>
                                <button style={{ width: "20%" }} className="col-md-6 btn-light">
                                    Xác nhận
                                </button>
                                <button style={{ width: "10%" }} onClick={handleClose} type='button' className="btn btn-light">
                                    Huỷ
                                </button>
                            </div>
                        </form>
                    </Modal>
                </div>
            </EggBatchDetail>
        </Box>

    );
}

function TableRows({ rowsData, deleteTableRows, handleChangeData }) {
    return (
        rowsData.map((data, index) => {
            const { machineLocation, vacantPosition, currEgg, updateEgg } = data;
            return (
                <tr key={index}>
                    <td><input type="text" value={machineLocation} onChange={(evnt) => (handleChangeData(index, evnt))} name="machineLocation" className="form-control" /></td>
                    <td><input type="text" value={vacantPosition} onChange={(evnt) => (handleChangeData(index, evnt))} name="vacantPosition" className="form-control" /> </td>
                    <td><input type="text" value={currEgg} onChange={(evnt) => (handleChangeData(index, evnt))} name="currEgg" className="form-control" /> </td>
                    <td><input type="text" value={updateEgg} onChange={(evnt) => (handleChangeData(index, evnt))} name="updateEgg" className="form-control" /> </td>
                    <td className='td'><button className="btn btn-outline-danger" type='button' onClick={() => (deleteTableRows(index))}><ClearIcon /></button></td>
                </tr>
            )
        })
    )
}