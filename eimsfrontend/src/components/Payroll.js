import React, { useState, useEffect, useRef } from 'react';
import { useNavigate, useLocation } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { Modal, button } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from 'axios';
//Toast
import {  toast } from 'react-toastify';
const Payroll = () => {
    //Show-hide Popup
    const [show, setShow] = useState(false);
    const handleClose = () => {
        setCreatePayrollDTO({
            payrollId: "",
            ownerId: sessionStorage.getItem("curUserId"),
            userId: "",
            employeeName: "",
            employeePhone: "",
            payrollItem: "",
            payrollAmount: "",
            issueDate: "",
            note: "",
            status: ""
        });
        setShow(false);
    }
    const handleShow = () => setShow(true);

    const [show2, setShow2] = useState(false);
    const handleClose2 = () => {
        setPayrollDetailDTO({
            payrollId: "",
            ownerId: sessionStorage.getItem("curUserId"),
            userId: "",
            employeeName: "",
            employeePhone: "",
            payrollItem: "",
            payrollAmount: "",
            issueDate: "",
            note: "",
            status: ""
        });
        setShow2(false);
    }
    const handleShow2 = () => setShow2(true);
    //Data holding objects

    //URL
    const PAYROLL_CREATE = "/api/payroll/create";
    const PAYROLL_CREATE_GET = "/api/employee/all";
    const PAYROLL_ALL = "/api/payroll/all";
    const PAYROLL_GET = "/api/payroll/get";
    const PAYROLL_UPADATE_SAVE = "/api/payroll/update/save";
    const PAYROLL_SEARCH = "/api/payroll/search"

    const [value, setValue] = React.useState(0);
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    //Data holding objects
    const [payrollList, setPayrollList] = useState([]);
    const [employeeList, setEmployeeList] = useState([]);
    const [searchKey, setSearchKey] = useState("");
    //Get sent params
    const { state } = useLocation();
    //DTOs
    //CreatePayrollDTO
    const [createPayrollDTO, setCreatePayrollDTO] = useState({
        payrollId: "",
        ownerId: sessionStorage.getItem("curUserId"),
        employeeId: "",
        employeeName: "",
        employeePhone: "",
        payrollItem: "",
        payrollAmount: "",
        issueDate: "",
        note: "",
        status: ""
    })

    //Handle Change functions:
    //CreatePayroll
    const handleCreatePayrollChange = (event, field) => {
        let actualValue = event.target.value
        setCreatePayrollDTO({
            ...createPayrollDTO,
            [field]: actualValue
        })
    }

    //EditPayroll
    const handleEditPayrollChange = (event, field) => {
        let actualValue = event.target.value
        setPayrollDetailDTO({
            ...payrollDetailDTO,
            [field]: actualValue
        })
    }
    //Search
    const handleSearchPayrollChange = (event) => {
        let actualValue = event.target.value;
        setSearchKey(actualValue);
    }

    //PayrollDetailDTO
    const [payrollDetailDTO, setPayrollDetailDTO] = useState({
        payrollId: "",
        ownerId: sessionStorage.getItem("curUserId"),
        employeeId: "",
        employeeName: "",
        employeePhone: "",
        payrollItem: "",
        payrollAmount: "",
        issueDate: "",
        note: "",
        status: ""
    })

    // Get list of payroll and show
    // Get payroll list
    useEffect(() => {
        loadPayrollList();
    }, []);

    // Request Payroll list and load the payroll list into the table rows
    const loadPayrollList = async () => {
        const result = await axios.get(PAYROLL_ALL,
            {
                params: { ownerId: sessionStorage.getItem("curUserId") },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setPayrollList(result.data);
        console.log(payrollList.length)


        // Toast Delete Payroll success
        console.log("state:====" + state)
        if (state != null) toast.success(state);
    }

    // Request Payroll list that meet search keyword
    const searchPayrollList = async (event) => {
        event.preventDefault();
        let response;
        try {
            response = await axios.get(PAYROLL_SEARCH,
                {
                    params: {
                        ownerId: sessionStorage.getItem("curUserId"),
                        searchKey: searchKey
                    },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setPayrollList(response.data);
            console.log(payrollList.length)
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                toast.error(err.response.data);
            }
        }
    }

    // Request Payroll detail of a payroll
    const loadPayrollDetail = async (findPayrollId) => {
        const result = await axios.get(PAYROLL_GET,
            {
                params: { payrollId: findPayrollId },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setPayrollDetailDTO(result.data);
        console.log(payrollDetailDTO)
        handleShow2();
        // Toast Delete payroll success
        console.log("state:====" + state)
        if (state != null) toast.success(state);

    }

    //handle get data to create
    const handleGetDataToCreate = async () => {
        const result = await axios.get(PAYROLL_CREATE_GET,
            {
                params: { facilityId: sessionStorage.getItem("facilityId") },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setEmployeeList(result.data);
        console.log(employeeList.length)
        handleShow();
        // Toast Delete Payroll success
        console.log("state:====" + state)
        if (state != null) toast.success(state);
    }

    //Handle Submit functions
    //Handle submit new Payroll
    const handleCreatePayrollSubmit = async (event) => {
        event.preventDefault();
        let response;
        try {
            response = await axios.post(PAYROLL_CREATE,
                createPayrollDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            setCreatePayrollDTO({
                payrollId: "",
                ownerId: sessionStorage.getItem("curUserId"),
                userId: "",
                employeeName: "",
                employeePhone: "",
                payrollItem: "",
                payrollAmount: "",
                issueDate: "",
                note: "",
                status: ""
            });
            console.log(response);
            loadPayrollList();
            toast.success(response.data);
            setShow(false);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                toast.error(err.response.data);
            }
        }
    }

    const handleEditPayrollSubmit = async (event) => {
        event.preventDefault();
        let response;
        try {
            response = await axios.post(PAYROLL_UPADATE_SAVE,
                payrollDetailDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            setPayrollDetailDTO({
                payrollId: "",
                ownerId: sessionStorage.getItem("curUserId"),
                userId: "",
                employeeName: "",
                employeePhone: "",
                payrollItem: "",
                payrollAmount: "",
                issueDate: "",
                note: "",
                status: ""
            });
            console.log(response);
            loadPayrollList();
            toast.success(response.data);
            setShow2(false);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                toast.error(err.response.data);
            }
        }
    }

    return (
        <div>
            <nav className="navbar justify-content-between">
                <button className='btn btn-light' onClick={handleGetDataToCreate}>+ Thêm</button>
                {/* Start: form to add new payroll */}
                <Modal show={show} onHide={handleClose}
                    size="lg" aria-labelledby="contained-modal-title-vcenter" centered >
                    <form onSubmit={handleCreatePayrollSubmit}>
                        <Modal.Header closeButton onClick={handleClose}>
                            <Modal.Title>Tạo khoản tiền lương </Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div className="changepass">
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Nhân viên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <select
                                            id="select" className="form-control mt-1" 
                                            onChange={(e) => handleCreatePayrollChange(e, "employeeId")} >
                                            <option defaultValue="-1" disabled selected>Chọn nhân viên</option>
                                            {
                                                employeeList.map((emItem, emIndex) =>
                                                    <option value={emItem.employeeId}>{emItem.employeeName}</option>
                                                )
                                            }
                                        </select>

                                    </div>
                                    <div className="col-md-6">
                                        <p>Ngày trả <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input type="date" style={{ width: "100%" }} placeholder="0"
                                            onChange={(e) => handleCreatePayrollChange(e, "issueDate")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Khoản tiền <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input style={{ width: "100%" }} placeholder="Tiền thưởng "
                                            onChange={(e) => handleCreatePayrollChange(e, "payrollItem")} />
                                    </div>
                                    <div className="col-md-6">
                                        <p>Số tiền </p>
                                    </div>
                                    <div className="col-md-6">
                                        <input style={{ width: "100%" }} placeholder="1.000.000"
                                            onChange={(e) => handleCreatePayrollChange(e, "payrollAmount")} />
                                    </div>
                                    <div className="col-md-6">
                                        <p>Ghi chú </p>
                                    </div>
                                    <div className="col-md-6">
                                        <textarea style={{ width: "100%" }}
                                            onChange={(e) => handleCreatePayrollChange(e, "note")} />
                                    </div>
                                </div>
                            </div>
                        </Modal.Body>
                        <div className='model-footer'>
                            <button style={{ width: "20%" }} type="submit" className="col-md-6 btn-light" >
                                Tạo
                            </button>
                            <button className='btn btn-light' type='button' style={{ width: "20%" }} onClick={handleClose}>
                                Huỷ
                            </button>
                        </div>
                    </form>
                </Modal>
                {/* Start: Filter and sort table */}
                <div className='filter my-2 my-lg-0'>
                    <p><FilterAltIcon />Lọc</p>
                    <p><ImportExportIcon />Sắp xếp</p>
                    <form className="form-inline" onSubmit={searchPayrollList}>
                        <div className="input-group">
                            <div className="input-group-prepend">
                                <button type='submit'><span className="input-group-text" ><SearchIcon /></span></button>
                            </div>
                            <input type="text" className="form-control" placeholder="Tìm kiếm" aria-label="Username" aria-describedby="basic-addon1" 
                                onChange={(e) => handleSearchPayrollChange((e))} />
                        </div>
                    </form>
                </div>
                {/* End: Filter and sort table */}
            </nav>
            <div>
                <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                    <div className="u-clearfix u-sheet u-sheet-1">
                        <div className="u-expanded-width u-table u-table-responsive u-table-1">
                            <table className="u-table-entity u-table-entity-1">
                                <colgroup>
                                    <col width="22%" />
                                    <col width="22%" />
                                    <col width="16%" />
                                    <col width="20%" />
                                    <col width="20%" />
                                </colgroup>
                                <thead className="u-palette-4-base u-table-header u-table-header-1">
                                    <tr style={{ height: "21px" }}>
                                        <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">Nhân viên</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Số điện thoại</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Ngày trả</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Khoản tiền</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Số tiền</th>
                                    </tr>
                                </thead>
                                <tbody className="u-table-body">
                                    {
                                        payrollList && payrollList.length > 0 ?
                                            payrollList.map((item, index) =>
                                                <tr style={{ height: "76px" }} onClick={(e) => loadPayrollDetail(item.payrollId)}>
                                                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">{item.employeeName}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.employeePhone}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.issueDate}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.payrollItem} <span style={{ float: "right" }}>VNĐ</span></td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.payrollAmount} <span style={{ float: "right" }}>VNĐ</span></td>

                                                </tr>
                                            ) :
                                            <tr>
                                                <td colSpan='5'>Chưa có khoản tiền lương nào được lưu lên hệ thống</td>
                                            </tr>
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>

            </div>

            <Modal show={show2} onHide={handleClose2}
                size="lg" aria-labelledby="contained-modal-title-vcenter" centered >
                <form onSubmit={handleEditPayrollSubmit}>
                    <Modal.Header closeButton onClick={handleClose2}>
                        <Modal.Title>Cập nhật khoản tiền lương</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className="changepass">
                            <div className="row">
                                <div className="col-md-6">
                                    <p>Nhân viên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <p>{payrollDetailDTO.employeeName}</p>
                                </div>
                                <div className="col-md-6">
                                    <p>Ngày trả <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <input type="date" style={{ width: "100%" }} placeholder="0"
                                        value={payrollDetailDTO.issueDate}
                                        onChange={(e) => handleEditPayrollChange(e, "issueDate")} />
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-6">
                                    <p>Khoản tiền <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <input  style={{ width: "100%" }} placeholder="Tiền thưởng "
                                        value={payrollDetailDTO.payrollItem}
                                        onChange={(e) => handleEditPayrollChange(e, "payrollItem")} />
                                </div>
                                <div className="col-md-6">
                                    <p>Số tiền </p>
                                </div>
                                <div className="col-md-6">
                                    <input style={{ width: "100%" }} placeholder="1.000.000"
                                        value={payrollDetailDTO.payrollAmount}
                                        onChange={(e) => handleEditPayrollChange(e, "payrollAmount")} />
                                </div>
                                <div className="col-md-6">
                                    <p>Ghi chú </p>
                                </div>
                                <div className="col-md-6">
                                    <textarea style={{ width: "100%" }}
                                        value={payrollDetailDTO.note}
                                        onChange={(e) => handleEditPayrollChange(e, "note")} />
                                </div>
                            </div>
                        </div>
                    </Modal.Body>
                    <div className='model-footer'>
                        <button style={{ width: "30%" }} className="col-md-6 btn-light" type='submit'>
                            Cập nhật
                        </button>
                        <button style={{ width: "20%" }} type="button" onClick={handleClose2} className="btn btn-light">
                            Huỷ
                        </button>
                    </div>
                </form>
            </Modal>
            {/* End: Table for payroll */}
           
        </div>
    );
}
export default Payroll;