import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import '../css/machine.css'
import { Modal } from 'react-bootstrap';
import ClearIcon from '@mui/icons-material/Clear';
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
    const [value, setValue] = React.useState(0);
    const navigate = useNavigate();
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
    const handleChangeData = (index, evnt) => {
        const { name, value } = evnt.target;
        const rowsInput = [...rowsData];
        rowsInput[index][name] = value;
        setRowsData(rowsInput);
    }
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [show2, setShow2] = useState(false);
    const handleClose2 = () => setShow2(false);
    const handleShow2 = () => setShow2(true);
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
                    <h3 style={{ textAlign: "center" }}>Trứng gà ri</h3>
                    <div className='detailbody'>

                        <div className="row">
                            <div className="col-md-3">
                                <p>Mã lô</p>
                            </div>
                            <div className="col-md-3">
                                <p>GFJ816</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Mã hoá đơn</p>
                            </div>
                            <div className="col-md-3">
                                <p>HDJ71-71-25</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Nhà cung cấp</p>
                            </div>
                            <div className="col-md-3">
                                <p>Phạm Anh B</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Ngày nhập</p>
                            </div>
                            <div className="col-md-3">
                                <p>2401/2023</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Tên loài</p>
                            </div>
                            <div className="col-md-3">
                                <p>Gà</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Tên loại</p>
                            </div>
                            <div className="col-md-3">
                                <p>Gà ri</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Số lượng</p>
                            </div>
                            <div className="col-md-3">
                                <p>3000 quả</p>
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
                                <p>Giai đoạn hiện tại</p>
                            </div>
                            <div className="col-md-3">
                                <p>2</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Trạng thái</p>
                            </div>
                            <div className="col-md-3">
                                <p className='text-blue'>Đang ấp</p>
                            </div>
                        </div>
                        <div>Thông báo cần chiếu trứng</div>
                    </div>

                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col"></th>
                                <th scope="col">Trứng vỡ</th>
                                <th scope="col">Trứng trắng</th>
                                <th scope="col">Trứng loãng</th>
                                <th scope="col">Trứng tắc</th>
                                <th scope="col">Con</th>
                                <th scope="col">Con đực</th>
                                <th scope="col">Con cái</th>
                                <th scope="col">Hao hụt</th>
                            </tr>
                        </thead>
                        <tbody>
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

                            </tr>
                        </tbody>
                    </table>
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Vị trí</th>
                                <th scope="col">Số lượng</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th scope="row">Máy 13</th>
                                <td>1000</td>
                            </tr>
                            <tr>
                                <th scope="row">Máy 17</th>
                                <td>1000</td>
                            </tr>
                            <tr>
                                <th scope="row">Máy 23</th>
                                <td>500</td>
                            </tr>
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
                                                    <label>Số lượng trứng hư tổn</label>
                                                </div>
                                                <div className="col-md-3">
                                                    <input className='form-control' required />
                                                </div>
                                                <div className="col-md-3 ">
                                                    <label>Số lượng trứng loại</label>
                                                </div>
                                                <div className="col-md-3">
                                                    <input className='form-control' required />
                                                </div>
                                            </div>
                                            <br />
                                            <div className="row">
                                                <div className="col-md-3" >
                                                    <label>Số lượng trứng bị vỡ</label>
                                                </div>
                                                <div className="col-md-3">
                                                    <input className='form-control' required />
                                                </div>
                                                <div className="col-md-3 ">
                                                    <label>Số lượng trứng còn lại</label>
                                                </div>
                                                <div className="col-md-3">
                                                    <p>2000</p>
                                                </div>
                                            </div>
                                        </div>
                                        <br />
                                        <div className='clear'>
                                            <table className="table table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th scope="col">Vị trí máy</th>
                                                        <th scope="col">Số lượng vị trí trống</th>
                                                        <th scope="col">Số lượng trứng hiện tại</th>
                                                        <th scope="col">Cập nhật số trứng</th>
                                                    </tr>
                                                </thead>
                                                <tbody >
                                                    <tr>
                                                        <td>Máy 13</td>
                                                        <td>0</td>
                                                        <td>1000</td>
                                                        <td>0</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Máy 17</td>
                                                        <td>0</td>
                                                        <td>1000</td>
                                                        <td>0</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Máy 23</td>
                                                        <td>500</td>
                                                        <td>500</td>
                                                        <td>0</td>
                                                    </tr>
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
                    <td className='td'><button className="btn btn-outline-danger" type='button' onClick={() => (deleteTableRows(index))}><ClearIcon/></button></td>
                </tr>
            )
        })
    )
}