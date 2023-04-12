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

function ExportBillDetail(props) {
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

ExportBillDetail.propTypes = {
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
    const EXPORT_GET = "/api/export/get";
    const EXPORT_UPDATE_PAID = "/api/export/update";

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
    const [eggProductList, setEggProductList] = useState([]);
    const [exportDetail, setExportDetail] = useState({
        exportId: "",
        customerId: "",
        customerName: "",
        customerPhone: "",
        exportDate: "",
        total: "",
        paid: "",
    })

    // DTO for update paid
    const [paid, setPaid] = useState({
        paid: exportDetail.paid
    })

    //Get export details
    useEffect(() => {
        if (dataLoaded) return;
        loadExport();
        setDataLoaded(true);
    }, []);

    const loadExport = async () => {
        const result = await axios.get(EXPORT_GET,
            { params: { exportId: id } },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: false
            });
        console.log(result);
        // Set inf
        exportDetail.exportId = result.data.exportId;
        exportDetail.customerId = result.data.customerId;
        exportDetail.customerName = result.data.customerName;
        exportDetail.customerPhone = result.data.customerPhone;
        exportDetail.exportDate = result.data.exportDate;
        exportDetail.total = result.data.total;
        exportDetail.paid = result.data.paid;
        paid.paid = result.data.paid;
        setEggProductList(result.data.eggProductList);
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
            response = await axios.put(EXPORT_UPDATE_PAID, {},
                {
                    params:
                    {
                        exportId: exportDetail.exportId,
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
            loadExport();
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
            response = await axios.put(EXPORT_UPDATE_PAID, {},
                {
                    params:
                    {
                        exportId: exportDetail.exportId,
                        paid: exportDetail.total
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
            loadExport();
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
            <ExportBillDetail value={value} index={0}>
                <h2>Thông tin chi tiết hoá đơn</h2>
                <div className='container'>
                    <div className='detailbody'>
                        <div className="row" >
                            <div className="col-md-3" >
                                <p>Khách hàng</p>
                            </div>
                            <div className="col-md-3">
                                <p>{exportDetail.customerName}</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Số điện thoại</p>
                            </div>
                            <div className="col-md-3">
                                <p>{exportDetail.customerPhone}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p>Ngày nhập</p>
                            </div>
                            <div className="col-md-3" >
                                <p>{exportDetail.exportDate.replace("T", " ")}</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>Mã hoá đơn</p>
                            </div>
                            <div className="col-md-3 ">
                                <p>{exportDetail.exportId}</p>
                            </div>
                        </div>
                    </div>

                    <br />
                    <table className="table table-bordered" >
                        <thead>
                            <tr>
                                <th scope="col">STT</th>
                                <th scope="col">Loại</th>
                                <th scope="col">Sản phẩm</th>
                                <th scope="col">Mã lô</th>
                                <th scope="col">Số lượng mua</th>
                                <th scope="col">Đơn giá (đ)</th>
                                <th scope="col">Vaccine/Con (đ)</th>
                                <th scope="col">Thành tiền (đ)</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                eggProductList && eggProductList.length > 0 ?
                                    eggProductList.map((item, index) =>
                                        <tr className='trclick' style={{ height: "76px" }}>
                                            <td>{index + 1}</td>
                                            <td>{item.breedName}</td>
                                            <td>{item.productName}</td>
                                            <td>{item.eggBatchId}</td>
                                            <td>{item.exportAmount.toLocaleString()}</td>
                                            <td>{item.price.toLocaleString()}</td>
                                            <td>{item.vaccine.toLocaleString()}</td>
                                            <td>{(item.exportAmount * item.price + item.exportAmount * item.vaccine).toLocaleString()}</td>
                                        </tr>
                                    ) : "Nothing"
                            }
                        </tbody>
                    </table>

                    <div className='row'>
                        <div className="col-md-6"></div>
                        <div className="col-md-3"></div>
                        <div className="col-md-3">
                            <p>Tổng hóa đơn: {exportDetail.total.toLocaleString('vi', { style: 'currency', currency: 'VND' })} </p>
                            <p>Đã thanh toán: {exportDetail.paid.toLocaleString('vi', { style: 'currency', currency: 'VND' })} </p>
                            <p>Trạng thái:
                                {
                                    exportDetail.total == exportDetail.paid
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
                                            <input classname="form-control mt-1"
                                                style={{ width: "100%" }} type="number"
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
                                content={"Xác nhận cập nhật số tiền đã trả cho hóa đơn xuất mã " + exportDetail.exportId
                                    + ": " + paid.paid.toLocaleString('vi', { style: 'currency', currency: 'VND' })} deleteFunction={(e) => handleUpdatePaidSubmit(e)}
                            />
                            <ConfirmBox open={open2} closeDialog={() => setOpen2(false)} title={"Xác nhận cập nhật số tiền đã trả"}
                                content={"Xác nhận trả hết cho hóa đơn xuất mã " + exportDetail.exportId
                                    + ": " + exportDetail.total.toLocaleString('vi', { style: 'currency', currency: 'VND' })} deleteFunction={(e) => handleUpdatePaidAllSubmit(e)}
                            />
                        </Modal>
                    </div>
                </div>
            </ExportBillDetail>

        </Box>
    );
}