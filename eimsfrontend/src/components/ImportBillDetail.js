import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, useLocation } from "react-router-dom";
import { Modal } from 'react-bootstrap';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import axios from 'axios';
import { toast } from 'react-toastify';
import ConfirmBox from './ConfirmBox';

function ImportBillDetail(props) {
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
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

ImportBillDetail.propTypes = {
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
    const [dataLoaded, setDataLoaded] = useState(false);

    //URL
    const IMPORT_GET = "/api/import/get";
    const IMPORT_UPDATE_PAID = "/api/import/update";

    //ConfirmBox
    const [open, setOpen] = useState(false);
    const [open2, setOpen2] = useState(false);

    //Show-hide Popup
    const [value, setValue] = React.useState(0);
    const navigate = useNavigate();
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    //Get sent params
    const { state } = useLocation();
    const { id } = state;

    // DTO
    const [eggBatchList, setEggBatchList] = useState([]);
    const [importDetail, setImportDetail] = useState({
        importId: "",
        supplierId: "",
        supplierName: "",
        supplierPhone: "",
        importDate: "",
        total: "",
        paid: "",
    })

    // DTO for update paid
    const [paid, setPaid] = useState({
        paid: importDetail.paid
    })

    //Get import details
    useEffect(() => {
        if (dataLoaded) return;
        loadImport();
        setDataLoaded(true);
    }, []);

    const loadImport = async () => {
        const result = await axios.get(IMPORT_GET,
            { params: { importId: id } },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: false
            });
        console.log(result);
        // Set inf
        importDetail.importId = result.data.importId;
        importDetail.supplierId = result.data.supplierId;
        importDetail.supplierName = result.data.supplierName;
        importDetail.supplierPhone = result.data.supplierPhone;
        importDetail.importDate = result.data.importDate;
        importDetail.total = result.data.total;
        importDetail.paid = result.data.paid;
        paid.paid = result.data.paid;
        setEggBatchList(result.data.eggBatchList);
    }

    //Handle Change functions:
    //Update paid
    const handleUpdatePaidChange = (event, field) => {
        let actualValue = event.target.value
        setPaid({
            ...paid,
            [field]: actualValue
        })
    }

    //Handle Submit functions
    //Handle submit update paid
    const handleUpdatePaidSubmit = async (event) => {
        event.preventDefault();
        let response;
        try {
            response = await axios.put(IMPORT_UPDATE_PAID, {},
                {
                    params:
                    {
                        importId: importDetail.importId,
                        paid: paid.paid
                    }
                },
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: false
                }
            );
            setOpen(false);
            loadImport();
            console.log(response);
            toast.success("Cập nhật số tiền đã trả thành công");
            setShow(false);
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

    const handleUpdatePaidAllSubmit = async (event) => {
        event.preventDefault();
        let response;
        try {
            response = await axios.put(IMPORT_UPDATE_PAID, {},
                {
                    params:
                    {
                        importId: importDetail.importId,
                        paid: importDetail.total
                    }
                },
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: false
                }
            );
            setOpen2(false);
            loadImport();
            console.log(response);
            toast.success("Cập nhật số tiền đã trả thành công");
            setShow(false);
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

    const routeChange = (ebi) => {
        navigate('/eggbatchdetail', { state: { id: ebi } });
    }
    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                <Tabs sx={{
                    '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
                    '& .Mui-selected': { color: "#d25d19" },
                }} value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Chi tiết hoá đơn" {...a11yProps(0)} />
                    <Tab style={{ textTransform: "capitalize" }} label="Trở về Đơn hàng" {...a11yProps(1)} onClick={() => navigate("/order")} />
                </Tabs>
            </Box>
            <ImportBillDetail value={value} index={0}>
                <h2>Thông tin chi tiết hoá đơn</h2>
                <div className='container'>
                    <div className='detailbody'>
                        <div className="row" >
                            <div className="col-md-3" >
                                <p>Nhà cung cấp</p>
                            </div>
                            <div className="col-md-3">
                                <p>{importDetail.supplierName}</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Số điện thoại</p>
                            </div>
                            <div className="col-md-3">
                                <p>{importDetail.supplierPhone}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Ngày nhập</p>
                            </div>
                            <div className="col-md-3" >
                                <p>{importDetail.importDate.replace("T", " ")}</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Mã hoá đơn</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>{importDetail.importId}</p>
                            </div>
                        </div>
                    </div>

                    <br />
                    <table className="table table-bordered" >
                        <thead>
                            <tr>
                                <th scope="col">STT</th>
                                <th scope="col">Mã lô</th>
                                <th scope="col">Tên lô</th>
                                <th scope="col">Số lượng (trứng)</th>
                                <th scope="col">Đơn giá</th>
                                <th scope="col">Thành tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                eggBatchList && eggBatchList.length > 0 ?
                                    eggBatchList.map((item, index) =>
                                        <tr className='trclick' style={{ height: "76px" }} onClick={() => routeChange(item.eggBatchId)}>
                                            <td>{index + 1}</td>
                                            <td>{item.eggBatchId}</td>
                                            <td>{item.breedName}</td>
                                            <td>{item.amount.toLocaleString()}</td>
                                            <td>{item.price.toLocaleString('vi', { style: 'currency', currency: 'VND' })}</td>
                                            <td>{(item.amount * item.price).toLocaleString('vi', { style: 'currency', currency: 'VND' })}</td>
                                        </tr>
                                    ) : "Nothing"
                            }
                        </tbody>
                    </table>

                    <div className='row'>
                        <div className="col-md-6"></div>
                        <div className="col-md-3"></div>
                        <div className="col-md-3">
                            <p>Tổng hóa đơn: {importDetail.total.toLocaleString('vi', { style: 'currency', currency: 'VND' })} </p>
                            <p>Đã thanh toán: {importDetail.paid.toLocaleString('vi', { style: 'currency', currency: 'VND' })} </p>
                            <p>Trạng thái:
                                {
                                    importDetail.total == importDetail.paid
                                        ? <span className='text-green'> Đã thanh toán đủ</span>
                                        : <span className='text-red'> Chưa thanh toán đủ</span>
                                }
                            </p>
                        </div>
                    </div>
                    <div className='model-footer'>
                        <button style={{ width: "20%" }} className="col-md-6 btn-light" onClick={handleShow}>
                            Cập nhật số tiền
                        </button>
                        <Modal show={show} onHide={handleClose}
                            size="lg"
                            aria-labelledby="contained-modal-title-vcenter"
                            centered >
                            <form>
                                <Modal.Header closeButton onClick={handleClose}>
                                    <Modal.Title>Cập nhật số tiền thanh toán</Modal.Title>
                                </Modal.Header>
                                <Modal.Body>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Số tiền đã thanh toán</p>
                                        </div>
                                        <div className="col-md-6">
                                            <input style={{ width: "100%" }} type="number"
                                                value={paid.paid} onChange={(e) => handleUpdatePaidChange(e, "paid")}
                                            />
                                        </div>
                                    </div>
                                </Modal.Body>
                                <div className='model-footer'>
                                    <button style={{ width: "30%" }} onClick={() => setOpen(true)}
                                    className="col-md-6 btn-light" type="button">
                                        Cập nhật
                                    </button>
                                    <button style={{ width: "20%" }} onClick={() => setOpen2(true)}
                                    className="btn btn-light" type="button">
                                        Đã trả hết
                                    </button>
                                    <button style={{ width: "20%" }} onClick={handleClose} className="btn btn-light" type="button">
                                        Huỷ
                                    </button>
                                </div>
                            </form>
                            <ConfirmBox open={open} closeDialog={() => setOpen(false)} title={"Xác nhận cập nhật số tiền đã trả"}
                                content={"Xác nhận cập nhật số tiền đã trả cho hóa đơn nhập mã " + importDetail.importId
                            + ": " + paid.paid.toLocaleString('vi', { style: 'currency', currency: 'VND' })} deleteFunction={(e) => handleUpdatePaidSubmit(e)}
                            />
                            <ConfirmBox open={open2} closeDialog={() => setOpen2(false)} title={"Xác nhận cập nhật số tiền đã trả"}
                                content={"Xác nhận trả hết cho hóa đơn nhập mã " + importDetail.importId
                            + ": " + importDetail.total.toLocaleString('vi', { style: 'currency', currency: 'VND' })} deleteFunction={(e) => handleUpdatePaidAllSubmit(e)}
                            />
                        </Modal>
                    </div>
                </div>
            </ImportBillDetail>

        </Box>
    );
}