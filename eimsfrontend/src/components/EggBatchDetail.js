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
    const EGGBATCH_UPDATE_DONE = "/api/eggBatch/update/done";
    const MACHINE_NOT_FULL_GET = "/api/machine/notFull";

    //Show-hide Popup
    const [value, setValue] = React.useState(0);
    const navigate = useNavigate();
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);

    const handleShow = () => {
        if (eggBatchDetail.status == 1 && eggBatchDetail.needAction == 1) {
            const rows = [...rowsData];
            rows.splice(0, rows.length);

            for (let i = 0; i < eggBatchDetail.machineList.length; i++) {
                rows.splice(i, 0, {
                    machineId: eggBatchDetail.machineList[i].machineId,
                    machineName: eggBatchDetail.machineList[i].machineName,
                    machineTypeId: eggBatchDetail.machineList[i].machineTypeId,
                    amountCurrent: eggBatchDetail.machineList[i].curCapacity,
                    capacity: eggBatchDetail.machineList[i].maxCapacity,
                    amountUpdate: eggBatchDetail.machineList[i].amount
                });
            }

            setRowsData(rows);
            setShow(true);
        } else if (eggBatchDetail.status == 0) {
            toast.warning("Lô trứng đã hoàn thành, không thể cập nhật")
        } else if (eggBatchDetail.status == 1 && eggBatchDetail.needAction == 0) {
            toast.warning("Chưa đến giai đoạn cập nhật lô trứng")
        }

    }
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
    // add to machine list
    const addTableRows = (item) => {
        rowsData.splice(rowsData.length, 0, {
            machineId: item.machineId,
            machineName: item.machineName,
            machineTypeId: item.machineTypeId,
            amountCurrent: item.curCapacity,
            capacity: item.maxCapacity,
            amountUpdate: item.amount
        });
        setShow2(false);
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
    // Machine list to choose
    const [machineList, setMachineList] = useState([])

    // Egg batch detail
    const [eggBatchDetail, setEggBatchDetail] = useState({
        eggBatchId: id,
        importId: "",
        supplierId: "",
        supplierName: "",
        importDate: "",
        specieId: "",
        specieName: "",
        breedId: "",
        breedName: "",
        startDate: "",
        progressInDays: "",
        incubationPeriod: "",
        amount: "",
        progress: "",
        phase: "",
        needAction: "",
        dateAction: "",
        status: "",
        eggProductList: [],
        machineList: [],
        machineNotFullList: []
    })

    // Update egg batch
    const [updateEggBatchDTO, setUpdateEggBatchDTO] = useState({
        eggBatchId: 0,
        phaseNumber: "",
        eggWasted: "",
        amount: "",
        needAction: eggBatchDetail.needAction,
        eggLocationUpdateEggBatchDTOS: []
    })

    const [done, setDone] = useState({
        done: ""
    });

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
            {
                params: { eggBatchId: id },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
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
        eggBatchDetail.startDate = result.data.startDate;
        eggBatchDetail.progressInDays = result.data.progressInDays;
        eggBatchDetail.incubationPeriod = result.data.incubationPeriod;
        eggBatchDetail.amount = result.data.amount;
        eggBatchDetail.progress = result.data.progress;
        eggBatchDetail.phase = result.data.phase;
        eggBatchDetail.status = result.data.status;
        eggBatchDetail.needAction = result.data.needAction;
        eggBatchDetail.dateAction = result.data.dateAction;
        eggBatchDetail.eggProductList = result.data.eggProductList;
        eggBatchDetail.machineList = result.data.machineList;
        eggBatchDetail.machineNotFullList = result.data.machineNotFullList;

        if (result.data.progress == 0) {
            remain.remain = result.data.amount;
        }
        if (result.data.progress != 0) {
            remain.remain = eggBatchDetail.eggProductList[2].curAmount + eggBatchDetail.eggProductList[6].curAmount;
        }
        setEggBatchLoaded(true);
    }

    const loadNotFullMachines = async () => {
        const result = await axios.get(MACHINE_NOT_FULL_GET,
            {
                params: { facilityId: sessionStorage.getItem("facilityId") },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        // Set inf
        setMachineNotFullList(result.data)
    }

    const routeChange = (mid) => {
        navigate('/machinedetail', { state: { id: mid } });
    }

    // display total amount
    function cal() {
        if (document.getElementById('remain') != null) {
            if (eggBatchDetail.eggProductList[8].amount == 0) {
                let a = Number(document.getElementById("eggWasted").value);
                let b = Number(document.getElementById("amount").value);

                let sum = remain.remain - (a + b);
                document.getElementById('remain').innerHTML = sum;
            } else {
                document.getElementById('remain').innerHTML = "";
            }
        }

    }

    // Variable check done
    var needAction = 0;
    function setCheck() {
        if (document.querySelector('#needActionTrue:checked')) {
            needAction = 0;
            updateEggBatchDTO.needAction = needAction;
        } else {
            needAction = 1;
            updateEggBatchDTO.needAction = needAction;
        }
    }


    //Handle Change functions:
    //Update EggBatch
    const handleUpdateEggBatchChange = (event, field) => {
        let actualValue = event.target.value
        setUpdateEggBatchDTO({
            ...updateEggBatchDTO,
            [field]: actualValue
        })
        var e = document.getElementById("select");
        if (e != null) {
            var value = e.options[e.selectedIndex].value;
            if (value == "") {
                document.getElementById('amount').disabled = true;
            }
            if (value != "") {
                document.getElementById('amount').disabled = false;
            }
        }

        if ((field == "amount" && value != 5) || field == "eggWasted") {
            cal();
        }
    }


    // Handle Submit functions
    const handleUpdateEggBatchSubmit = async (event) => {
        event.preventDefault();
        console.log(rowsData)
        updateEggBatchDTO.eggBatchId = id;
        updateEggBatchDTO.eggLocationUpdateEggBatchDTOS = rowsData;
        console.log(JSON.stringify(updateEggBatchDTO))
        let response;
        try {
            response = await axios.put(EGGBATCH_UPDATE,
                updateEggBatchDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            setShow(false);
            setUpdateEggBatchDTO({
                eggBatchId: id,
                phaseNumber: "",
                eggWasted: "",
                amount: "",
                eggLocationUpdateEggBatchDTOS: [],
                done: true
            });
            setRowsData([]);
            loadEggBatch();
            setEggBatchLoaded(false);
            toast.success("Cập nhật lô trứng thành công");
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                if ((err.response.data === null) || (err.response.data === '')) {
                    toast.error('Có lỗi xảy ra, vui lòng thử lại');
                } else {
                    toast.error(err.response.data);
                }
                console.log(err.response.data);
            }
        }
    }


    // Handle Update done 
    const handleUpdateEggBatchDone = async () => {
        console.log(eggBatchDetail.eggBatchId);
        let response;
        try {
            response = await axios.put(EGGBATCH_UPDATE_DONE, {},
                {
                    params: {
                        "eggBatchId": eggBatchDetail.eggBatchId,
                        "done": true
                    }
                },
                {

                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            setShow(false);
            loadEggBatch();
            setEggBatchLoaded(false);
            toast.success("Đã hoàn thành lô trứng");
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                if ((err.response.data === null) || (err.response.data === '')) {
                    toast.error('Có lỗi xảy ra, vui lòng thử lại');
                } else {
                    toast.error(err.response.data);
                }
                console.log(err.response.data);
            }
        }
    }

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
                                <p>{eggBatchDetail.importDate.replace("T", " ")}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Tên loài</p>
                            </div>
                            <div className="col-md-3">
                                <p>{eggBatchDetail.specieName}</p>
                            </div>
                            {
                                eggBatchDetail.startDate !== null
                                    ? <>
                                        <div className="col-md-3 ">
                                            <p>Thời gian bắt đầu ấp</p>
                                        </div>
                                        <div className="col-md-3">
                                            <p>{eggBatchDetail.startDate.replace("T", " ")}</p>
                                        </div>
                                    </>
                                    : ''
                            }


                        </div>
                        <div className="row">
                            <div className="col-md-3 ">
                                <p>Tên loại</p>
                            </div>
                            <div className="col-md-3">
                                <p>{eggBatchDetail.breedName}</p>
                            </div>
                            {
                                eggBatchDetail.progress !== 0
                                    ?
                                    <>
                                        <div className="col-md-3 ">
                                            <p>Ngày dự tính giai đoạn tiếp theo</p>
                                        </div>
                                        <div className="col-md-3">
                                            <p>{eggBatchDetail.dateAction.slice(0, 10)}</p>
                                        </div>
                                    </>
                                    : ''
                            }

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
                                <p>Giai đoạn hiện tại:  </p>
                            </div>
                            <div className="col-md-3">
                                <p>({eggBatchDetail.progress}){eggBatchDetail.phase}
                                </p>
                            </div>
                            <div className="col-md-3">
                            </div>
                            <div className="col-md-3">
                                {
                                    eggBatchDetail.status === 0
                                        ? <td className=''>Đã hoàn thành</td>
                                        : ''
                                }
                                {
                                    eggBatchDetail.status === 1 && eggBatchDetail.needAction === 1
                                        ? <td className='text-red'>Cần cập nhật</td>
                                        : ''
                                }
                                {
                                    eggBatchDetail.status === 1 && eggBatchDetail.needAction === 0 && eggBatchDetail.progress < 5
                                        ? <td className='text-green'>Đang ấp</td>
                                        : ''
                                }
                                {
                                    eggBatchDetail.status === 1 && eggBatchDetail.needAction === 0 && eggBatchDetail.progress >= 5
                                        ? <td className='text-green'>Đang nở</td>
                                        : ''
                                }
                            </div>
                        </div>
                    </div>
                    <div>
                        <p></p>
                    </div>
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Giai đoạn</th>
                                <th scope="col" width="8%"></th>
                                <th scope="col" width="8%">0</th>
                                <th scope="col" width="8%">1</th>
                                <th scope="col" width="8%">2</th>
                                <th scope="col" width="8%">3</th>
                                <th scope="col" width="8%">4</th>
                                <th scope="col" width="8%">5</th>
                                <th scope="col" width="8%">6</th>
                                <th scope="col" width="8%">7</th>
                                <th scope="col" width="8%">8</th>
                                <th scope="col" width="8%">9</th>
                            </tr>
                            <tr>
                                <th scope="col"></th>
                                <th scope="col" width="8%">Hao hụt</th>
                                <th scope="col" width="8%">Trứng dập</th>
                                <th scope="col" width="8%">Trứng đang ấp</th>
                                <th scope="col" width="8%">Trứng trắng </th>
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
                            eggBatchDetail.eggProductList && eggBatchDetail.eggProductList.length > 0 ?
                                <tbody>
                                    <tr>
                                        <th scope="row">Ban đầu</th>
                                        <td>{eggBatchDetail.eggProductList[0].amount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[1].amount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[2].amount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[3].amount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[4].amount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[5].amount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[6].amount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[7].amount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[8].amount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[9].amount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[10].amount.toLocaleString()}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Hiện tại</th>
                                        <td>{eggBatchDetail.eggProductList[0].curAmount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[1].curAmount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[2].curAmount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[3].curAmount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[4].curAmount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[5].curAmount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[6].curAmount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[7].curAmount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[8].curAmount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[9].curAmount.toLocaleString()}</td>
                                        <td>{eggBatchDetail.eggProductList[10].curAmount.toLocaleString()}</td>
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
                                        <tr className='trclick' style={{ height: "76px", textAlign: "left" }} onClick={() => routeChange(item.machineId)}>
                                            <th scope="row">{item.machineName}</th>
                                            <td>{item.amount.toLocaleString()}</td>
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
                        <form onSubmit={handleUpdateEggBatchSubmit}>
                            <Modal.Header closeButton onClick={handleClose}>
                                <Modal.Title>Cập nhật Thông tin lô trứng</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>

                                <div className='container'>
                                    <div className='detailbody'>
                                        <br />
                                        <div className="row">
                                            <div className="col-md-3" >
                                                <label>Giai đoạn hiện tại: </label>
                                            </div>
                                            <div className="col-md-3" >
                                                <label>({eggBatchDetail.progress}) {eggBatchDetail.phase}</label>
                                            </div>
                                        </div>
                                        <br />
                                        <div className="row">
                                            <div className="col-md-3" >
                                                <label>Trứng hao hụt: </label>
                                            </div>
                                            <div className="col-md-3">
                                                <input className='form-control' id="eggWasted" name="eggWasted" type="number"
                                                    onChange={(e) => handleUpdateEggBatchChange(e, "eggWasted")} />
                                            </div>
                                            {
                                                eggBatchDetail.eggProductList && eggBatchDetail.eggProductList.length > 0
                                                    && eggBatchDetail.eggProductList[2].amount !== 0
                                                    ? <>
                                                        <div className="col-md-3" >
                                                            <label>Số trứng đang ấp: </label>
                                                        </div>
                                                        <div className="col-md-3">
                                                            <input disabled className='form-control'
                                                                value={eggBatchDetail.eggProductList[2].amount.toLocaleString()} />
                                                        </div>
                                                    </>
                                                    : ''
                                            }
                                        </div>
                                        <br />
                                        <div className="row">
                                            <div className="col-md-3 ">
                                                <label>Loại trứng cập nhật</label>
                                            </div>
                                            <div className="col-md-3">
                                                <select onChange={(e) => handleUpdateEggBatchChange(e, "phaseNumber")} id="select" className="form-select" aria-label="Default select example">
                                                    <option value="">Chọn</option>
                                                    <option value="0">Trứng vỡ/dập</option>
                                                    <option value="2">Trứng trắng/tròn, trứng không có phôi</option>
                                                    <option value="3">Trứng loãng/tàu, phôi chết non</option>
                                                    <option value="4">Trứng lộn</option>
                                                    <option value="5">Trứng đang nở</option>
                                                    <option value="6">Trứng tắc</option>
                                                    <option value="8">Con đực</option>
                                                    <option value="9">Con cái</option>
                                                </select>
                                            </div>
                                            {
                                                eggBatchDetail.eggProductList && eggBatchDetail.eggProductList.length > 0
                                                    && eggBatchDetail.eggProductList[6].amount !== 0
                                                    ? <>
                                                        <div className="col-md-3" >
                                                            <label>Số trứng đang nở: </label>
                                                        </div>
                                                        <div className="col-md-3">
                                                            <input disabled className='form-control'
                                                                value={eggBatchDetail.eggProductList[6].amount.toLocaleString()} />
                                                        </div>
                                                    </>
                                                    : ''
                                            }
                                        </div>
                                        <br />
                                        <div className="row">
                                            <div className="col-md-3" >
                                                <label>Số lượng cập nhật</label>
                                            </div>
                                            <div className="col-md-3">
                                                <input disabled className='form-control' id="amount" name="amount" type="number"
                                                    onChange={(e) => handleUpdateEggBatchChange(e, "amount")} />
                                            </div>
                                            {
                                                eggBatchDetail.progress == 0
                                                    || (eggBatchDetail.progress <= 5 && eggBatchDetail.eggProductList[2].amount !== 0)
                                                    ?
                                                    <>
                                                        <div className="col-md-3 ">
                                                            <label>Số lượng trứng còn lại</label>
                                                            <p>(tổng trứng trong máy)</p>
                                                        </div>
                                                        <div className="col-md-3">
                                                            <p className='form-control' id="remain" name="remain">{remain.remain.toLocaleString()}</p>
                                                        </div>
                                                    </>
                                                    : ''
                                            }
                                            {
                                                eggBatchDetail.progress >= 7
                                                    ?
                                                    <>
                                                        <div className="col-md-3 ">
                                                            <label>Số lượng con nở</label>
                                                        </div>
                                                        <div className="col-md-3">
                                                            <p className='form-control' id="" name="">{eggBatchDetail.eggProductList[8].amount}</p>
                                                        </div>
                                                    </>
                                                    : ''
                                            }
                                            {
                                                eggBatchDetail.progress !== 6
                                                    ? <div className='row'>
                                                        <div className="col-md-3" >
                                                            <label for="needActionTrue">Xác nhận hoàn thành giai đoạn</label>
                                                        </div>
                                                        <div className="col-md-3" >
                                                            <input type="checkbox" id="needActionTrue" name="needActionTrue" checked onChange={setCheck} />
                                                        </div>
                                                    </div>
                                                    : ''
                                            }
                                        </div>
                                    </div>
                                    <br />
                                    <div className='clear'>
                                        <table className="table table-bordered">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Máy</th>
                                                    <th scope="col">Chứa</th>
                                                    <th scope="col">Vị trí trống</th>
                                                    <th scope="col">Số trứng nhập máy</th>
                                                </tr>
                                            </thead>
                                            <tbody>
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
                                                <Modal.Title>Những máy còn trống</Modal.Title>
                                            </Modal.Header>
                                            <Modal.Body>
                                                <div className="table-wrapper-scroll-y my-custom-scrollbar">
                                                    <table style={{ overflowY: "scroll" }} className="table table-bordered">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">Máy</th>
                                                                <th scope="col">Chứa</th>
                                                                <th scope="col">Vị trí trống</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody >
                                                            {
                                                                eggBatchDetail.machineNotFullList && eggBatchDetail.machineNotFullList.length > 0 ?
                                                                    eggBatchDetail.machineNotFullList.map((item, index) =>
                                                                        <tr className='trclick' onClick={() => addTableRows(item, index)}>
                                                                            <td>{item.machineName}</td>
                                                                            <td>{item.curCapacity.toLocaleString()}/{item.maxCapacity.toLocaleString()}</td>
                                                                            <td>{(item.maxCapacity - item.curCapacity).toLocaleString()}</td>
                                                                        </tr>
                                                                    ) : 'Nothing'
                                                            }
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </Modal.Body>
                                        </Modal>
                                    </div>
                                </div>
                            </Modal.Body>
                            <div className='model-footer'>
                                <button style={{ width: "20%" }} className="col-md-6 btn-light" type="submit">
                                    Xác nhận
                                </button>
                                <button style={{ width: "10%" }} onClick={handleClose} type='button' className="btn btn-light">
                                    Huỷ
                                </button>
                                <button style={{ width: "20%", float: "left" }} onClick={handleUpdateEggBatchDone} className="col-md-6 btn-light" type="button">
                                    Hoàn thành
                                </button>
                            </div>
                        </form>
                    </Modal>
                </div>
            </EggBatchDetail>
            <ToastContainer position="top-left"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="colored"
                style={{ zIndex: "10000" }} />
        </Box>
    );
}

function TableRows({ rowsData, deleteTableRows, handleChangeData }) {
    return (
        rowsData.map((data, index) => {
            const { machineName, amountCurrent, capacity, amountUpdate } = data;
            return (
                <tr key={index}>
                    <td>
                        <div name="machineName" className="form-control" >
                            {machineName}
                        </div>
                    </td>
                    <td>
                        <div name="current" className="form-control" >
                            {amountCurrent.toLocaleString()} / {capacity.toLocaleString()}
                        </div>
                    </td>
                    <td>
                        <div name="emptySlot" className="form-control" >
                            {(capacity - amountCurrent).toLocaleString()}
                        </div>
                    </td>
                    <td><input type="number" value={amountUpdate} onChange={(evnt) => (handleChangeData(index, evnt))} name="amountUpdate" className="form-control" /> </td>
                    <td className='td'><button className="btn btn-outline-danger" type='button' onClick={() => (deleteTableRows(index))}><ClearIcon /></button></td>
                </tr>
            )
        })
    )
}