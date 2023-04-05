import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import React, { useState, useEffect } from 'react';
import { useLocation } from "react-router-dom";
import { Modal } from 'react-bootstrap'
import { useTheme } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import { AppBar } from '@mui/material';
import "../css/report.css"
import axios from 'axios';
//Toast
import {  toast } from 'react-toastify';
const ImportReport = () => {
    //Hide show popup
    const [show, setShow] = useState(false);
    const handleClose = () => {
        setShow(false);
        setReportItemList([]);
        setReportItemYearList([]);
    }
    const handleShow = () => setShow(true);

    //Tab change
    const [value, setValue] = React.useState(0);
    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };
    const handleChangeIndex = (index: number) => {
        setValue(index);
    };

    //URL
    const IMPORT_REPORT_ALL = "/api/importReport/all";
    const IMPORT_YEAR_LIST = "/api/importReport/getImportReceiptYear";
    const IMPORT_MONTHLY_REPORT_ITEM = "/api/importReport/getByMonth";
    const IMPORT_YEAR_REPORT_ITEM = "/api/importReport/getByYear";

    //Data holding objects
    const [reportList, setReportList] = useState([]);

    //Get sent params
    const { state } = useLocation();

    // Get list of repport and show
    // Get report list
    useEffect(() => {
        loadReportList();
    }, []);

    // Request report list and load the report list into the table rows
    const loadReportList = async () => {
        const result = await axios.get(IMPORT_REPORT_ALL,
            {
                params: { userId: sessionStorage.getItem("curUserId") },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setReportList(result.data);
        console.log(reportList.length)


        // Toast Delete report success
        console.log("state:====" + state)
        if (state != null) toast.success(state);
    }

    //handle show detail
    const [yearList, setYearList] = useState([]);
    const [reportItemList, setReportItemList] = useState([]);
    const [reportItemYearList, setReportItemYearList] = useState([]);
    const [supplierId, setSupplierId] = useState("");

    const handleViewDetail = async (supplierId2) => {
        setSupplierId(supplierId2);
        console.log("setting supplierId" + supplierId);

        const result = await axios.get(IMPORT_YEAR_LIST,
            {
                params: {
                    userId: sessionStorage.getItem("curUserId"),
                    supplierId: supplierId2
                },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setYearList(result.data);
        console.log(setYearList.length);

        const result2 = await axios.get(IMPORT_YEAR_REPORT_ITEM,
            {
                params: {
                    userId: sessionStorage.getItem("curUserId"),
                    supplierId: supplierId2
                },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setReportItemYearList(result2.data);
        console.log(setReportItemYearList.length);

        handleShow();
        // Toast Delete Payroll success
        console.log("state:====" + state)
        if (state != null) toast.success(state);
    }

    const handleChangeYear = (event) => {
        let actualValue = event.target.value;
        setReportItemList([]);
        handleViewMonthlyReport(actualValue);
    }

    const handleViewMonthlyReport = async (year) => {
        const result2 = await axios.get(IMPORT_MONTHLY_REPORT_ITEM,
            {
                params: {
                    supplierId: supplierId,
                    year: year
                },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setReportItemList(result2.data);
        console.log(reportList.length);
    }

    return (
        <div>
            <Modal show={show} onHide={handleClose}
                size="lg" aria-labelledby="contained-modal-title-vcenter" centered >
                <Modal.Header closeButton onClick={handleClose}>
                    <Modal.Title>Phạm Ngọc A</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className='container'>
                        <Box >
                            <AppBar position="absolute">
                                <Tabs
                                    value={value}
                                    onChange={handleChange}
                                    textColor="inherit"
                                    variant="fullWidth"
                                    aria-label="full width tabs example"
                                >
                                    <Tab label="Tháng" {...a11yProps(0)} />
                                    <Tab label="Năm" {...a11yProps(1)} />

                                </Tabs>
                            </AppBar>
                            <TabPanel value={value} index={0} >
                                <br />
                                <label>Năm </label>
                                <select
                                    id="select" className='form-control m-3 mt-0' style={{ display: "inline-block", width: "20%" }}
                                    onChange={(e) => handleChangeYear(e)}>
                                    <option defaultValue="Chọn Năm">Chọn năm</option>
                                    {
                                        yearList && yearList.length > 0
                                            ? yearList.map((item, index) =>
                                                <option value={item}>{item}</option>
                                            ) : <option value="">Không có</option>
                                    }
                                </select>
                                <div style={{ overflow: "scroll", maxHeight: "300px" }}>
                                    <table className="table table-bordered" >
                                        <thead>
                                            <tr>
                                                <th scope="col">Tháng</th>
                                                <th scope="col">Tổng chi phí</th>
                                                <th scope="col">Đã thanh toán</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {
                                                reportItemList && reportItemList.length > 0
                                                    ? reportItemList.map((item, index) =>
                                                        <tr>
                                                            <th scope="row">{item.reportName}</th>
                                                            <td>{item.total}</td>
                                                            <td>{item.paid}</td>
                                                        </tr>
                                                    ) : <tr>
                                                        <th colSpan='3'>Không có dữ liệu</th>
                                                    </tr>
                                            }
                                        </tbody>
                                    </table>
                                </div>
                            </TabPanel>
                            <TabPanel value={value} index={1} >
                                <br />
                                <div style={{ overflow: "scroll", maxHeight: "300px" }}>
                                    <table className="table table-bordered" >
                                        <thead>
                                            <tr>
                                                <th scope="col">Năm</th>
                                                <th scope="col">Tổng chi phí</th>
                                                <th scope="col">Đã thanh toán</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {
                                                reportItemYearList && reportItemYearList.length > 0
                                                    ? reportItemYearList.map((item, index) =>
                                                        <tr>
                                                            <th scope="row">{item.reportName}</th>
                                                            <td>{item.total}</td>
                                                            <td>{item.paid}</td>
                                                        </tr>
                                                    ) : <tr>
                                                        <th colSpan='3'>Không có dữ liệu</th>
                                                    </tr>
                                            }

                                        </tbody>
                                    </table>
                                </div>
                            </TabPanel>
                        </Box>
                    </div>
                </Modal.Body>
            </Modal>

            {/* Start: Table for import list */}
            <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                <div className="u-clearfix u-sheet u-sheet-1">
                    <div className="u-expanded-width u-table u-table-responsive u-table-1">
                        <table className="u-table-entity u-table-entity-1">
                            <colgroup>
                                <col width="5%" />
                                <col width="35%" />
                                <col width="30%" />
                                <col width="30%" />
                            </colgroup>
                            <thead className="u-palette-4-base u-table-header u-table-header-1">
                                <tr style={{ height: "21px" }}>
                                    <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Nhà cung cấp</th>
                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Tổng chi phí</th>
                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Đã thanh toán</th>
                                </tr>
                            </thead>
                            <tbody className="u-table-body">
                                {
                                    reportList && reportList.length > 0
                                        ? reportList.map((item, index) =>
                                            <tr style={{ height: "76px" }} onClick={() => handleViewDetail(item.supplierId)}>
                                                <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">{index + 1}</td>
                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.supplierName}</td>
                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.total} <span style={{ float: "right" }}>VNĐ</span></td>
                                                <td className="u-border-1 u-border-grey-30 u-table-cell ">{item.paid} <span style={{ float: "right" }}>VNĐ</span></td>
                                            </tr>)
                                        : <tr>
                                            <td colSpan='5'>Chưa có hóa đơn nào được lưu trên hệ thống</td>
                                        </tr>
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>
            {/* End: Table for supplier list */}
            
        </div>
    );
}
interface TabPanelProps {
    children?: React.ReactNode;
    dir?: string;
    index: number;
    value: number;
}

function TabPanel(props: TabPanelProps) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`full-width-tabpanel-${index}`}
            aria-labelledby={`full-width-tab-${index}`}
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

function a11yProps(index: number) {
    return {
        id: `full-width-tab-${index}`,
        'aria-controls': `full-width-tabpanel-${index}`,
    };
}
export default ImportReport;