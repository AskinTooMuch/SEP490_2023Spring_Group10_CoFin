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
import { ToastContainer, toast } from 'react-toastify';
import { EditOff } from '@mui/icons-material';
import { hover } from '@testing-library/user-event/dist/hover';
const Cost = () => {
    //Show-hide Popup
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [show2, setShow2] = useState(false);
    const handleClose2 = () => setShow2(false);
    const handleShow2 = () => setShow2(true);

    //URL
    const COST_CREATE = "/api/cost/create";
    const COST_ALL = "/api/cost/all";
    const COST_GET = "/api/cost/get";
    const COST_UPDATE_SAVE = "/api/cost/update/save"

    const [value, setValue] = React.useState(0);
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    //Data holding objects
    const [costList, setCostList] = useState([]);
    //Get sent params
    const { state } = useLocation();
    //DTOs
    //CreateCostDTO
    const [createCostDTO, setCreateCostDTO] = useState({
        userId: sessionStorage.getItem("curUserId"),
        facilityId: sessionStorage.getItem("facilityId"),
        costItem: "",
        costAmount: "0",
        paidAmount: "0",
        issueDate: "",
        note: ""
    })

    //Handle Change functions:
    //CreateCost
    const handleCreateCostChange = (event, field) => {
        let actualValue = event.target.value
        setCreateCostDTO({
            ...createCostDTO,
            [field]: actualValue
        })
    }
    //EditCost
    const handleEditCostChange = (event, field) => {
        let actualValue = event.target.value
        setEditCostDTO({
            ...editCostDTO,
            [field]: actualValue
        })
    }

    //Handle Submit functions
    //Handle submit new Cost
    const handleCreateCostSubmit = async (event) => {
        event.preventDefault();
        let response;
        try {
            response = await axios.post(COST_CREATE,
                createCostDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            setCreateCostDTO({
                userId: sessionStorage.getItem("curUserId"),
                facilityId: sessionStorage.getItem("facilityId"),
                costItem: "",
                costAmount: "",
                paidAmount: "",
                issueDate: "",
                note: ""
            });
            console.log(response);
            loadCostList();
            toast.success(response.data);
            setShow(false);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                if ((err.response.data === null) || (err.response.data === '') ) {
          toast.error('Có lỗi xảy ra, vui lòng thử lại');
        } else {
          toast.error(err.response.data);
        }
            }
        }
    }

    // Get list of cost and show
    // Get cost list
    useEffect(() => {
        loadCostList();
    }, []);

    // Request Cost list and load the cost list into the table rows
    const loadCostList = async () => {
        const result = await axios.get(COST_ALL,
            {
                params: { userId: sessionStorage.getItem("curUserId") },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setCostList(result.data);
        console.log(costList.length)


        // Toast Delete Cost success
        console.log("state:====" + state)
        if (state != null) toast.success(state);
    }

    //EditCostDTO
    const [editCostDTO, setEditCostDTO] = useState({
        userId: "",
        facilityId: "",
        costItem: "",
        costAmount: "",
        paidAmount: "",
        issueDate: "",
        note: ""
    })

    // Request Cost detail and load the cost list into the table rows
    const loadCostDetail = async (findCostId) => {
        const result = await axios.get(COST_GET,
            {
                params: { costId: findCostId },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setEditCostDTO(result.data);
        console.log(editCostDTO)
        handleShow2();
        // Toast Delete Cost success
        console.log("state:====" + state)
        if (state != null) toast.success(state);
    }

    const handleEditCostSubmit = async (event) => {
        event.preventDefault();
        let response;
        try {
            response = await axios.post(COST_UPDATE_SAVE,
                editCostDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            setEditCostDTO({
                userId: "",
                facilityId: "",
                costItem: "",
                costAmount: "",
                paidAmount: "",
                issueDate: "",
                note: ""
            })
            loadCostList();
            console.log(response);
            toast.success("Cập nhật thành công");
            handleClose2();
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                if ((err.response.data === null) || (err.response.data === '') ) {
          toast.error('Có lỗi xảy ra, vui lòng thử lại');
        } else {
          toast.error(err.response.data);
        }
            }
        }
    }


    return (
        <div>
            <nav className="navbar justify-content-between">
                <button className='btn btn-light' onClick={handleShow}>+ Thêm</button>
                {/* Start: form to add new supplier */}
                <Modal show={show} onHide={handleClose}
                    size="lg" aria-labelledby="contained-modal-title-vcenter" centered >
                    <form>
                        <Modal.Header closeButton onClick={handleClose}>
                            <Modal.Title>Thêm thông tin chi phí </Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div className="changepass">
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Tên chi phí<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input required style={{ width: "100%" }} placeholder="Tiền sửa máy tháng 1/2023"
                                            onChange={(e) => handleCreateCostChange(e, "costItem")} />
                                    </div>
                                    <div className="col-md-6">
                                        <p>Tổng chi phí <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input required style={{ width: "100%" }} placeholder="0"
                                            type='number'
                                            min='0'
                                            onChange={(e) => handleCreateCostChange(e, "costAmount")} />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Đã thanh toán</p>
                                    </div>
                                    <div className="col-md-6">
                                        <input style={{ width: "100%" }} placeholder="0"
                                            type='number'
                                            min="0"
                                            onChange={(e) => handleCreateCostChange(e, "paidAmount")}
                                            max={createCostDTO.costAmount} />
                                    </div>
                                    <div className="col-md-6">
                                        <p>Ghi chú </p>
                                    </div>
                                    <div className="col-md-6">
                                        <textarea style={{ width: "100%" }}
                                            onChange={(e) => handleCreateCostChange(e, "note")} />
                                    </div>
                                </div>
                            </div>
                        </Modal.Body>
                        <div className='model-footer'>
                            <button style={{ width: "20%" }} type="submit" className="col-md-6 btn-light" onClick={handleCreateCostSubmit}>
                                Tạo
                            </button>
                            <button className='btn btn-light' type="button" style={{ width: "20%" }} onClick={handleClose}>
                                Huỷ
                            </button>
                        </div>
                    </form>
                </Modal>
                {/* End: form to add new supplier */}
                {/* Start: Filter and sort table */}
                <div className='filter my-2 my-lg-0'>
                    <p><FilterAltIcon />Lọc</p>
                    <p><ImportExportIcon />Sắp xếp</p>
                    <form className="form-inline">
                        <div className="input-group">
                            <div className="input-group-prepend">
                                <button ><span className="input-group-text" ><SearchIcon /></span></button>
                            </div>
                            <input type="text" className="form-control" placeholder="Tìm kiếm" aria-label="Username" aria-describedby="basic-addon1" />
                        </div>
                    </form>
                </div>
                {/* End: Filter and sort table */}
            </nav>
            {/* Start: Table for supplier list */}
            <div>
                <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                    <div className="u-clearfix u-sheet u-sheet-1">

                        <div className="u-expanded-width u-table u-table-responsive u-table-1">
                            <table className="u-table-entity u-table-entity-1">
                                <colgroup>
                                    <col width="20%" />
                                    <col width="20%" />
                                    <col width="20%" />
                                    <col width="20%" />
                                    <col width="20%" />
                                </colgroup>
                                <thead className="u-palette-4-base u-table-header u-table-header-1">
                                    <tr style={{ height: "21px" }}>
                                        <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">Tên chi phí</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Ngày nhập</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Tổng chi phí</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Đã thanh toán</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody className="u-table-body">
                                    {
                                        costList && costList.length > 0 ?
                                            costList.map((item, index) =>
                                                <tr style={{ height: "76px" }} onClick={(e) => loadCostDetail(item.costId)}>
                                                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">{item.costItem}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.issueDate}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.costAmount} <span style={{ float: "right" }}>VNĐ</span></td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.paidAmount} <span style={{ float: "right" }}>VNĐ</span></td>
                                                    {
                                                        item.paidAmount === item.costAmount
                                                            ? <td className="u-border-1 u-border-grey-30 u-table-cell text-green">Đã thanh toán đủ</td>
                                                            : <td className="u-border-1 u-border-grey-30 u-table-cell text-red">Chưa thanh toán đủ</td>
                                                    }
                                                </tr>
                                            ) : 
                                            <tr>
                                            <td colSpan='5'>Chưa có chi phí nào được lưu trên hệ thống</td>
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
                <form onSubmit={handleEditCostSubmit}>
                    <Modal.Header closeButton onClick={handleClose2}>
                        <Modal.Title>Cập nhật thông tin chi phí</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className="changepass">
                            <div className="row">
                                <div className="col-md-6">
                                    <p>Tên chi phí<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                </div>
                                <div className="col-md-6">
                                    <input required style={{ width: "100%" }} placeholder="Tiền mua máy nở"
                                        value={editCostDTO.costItem}
                                        onChange={(e) => handleEditCostChange(e, "costItem")} />
                                </div>
                                <div className="col-md-6">
                                    <p>Tổng chi phí <FontAwesomeIcon className="star" icon={faStarOfLife}
                                    /></p>
                                </div>
                                <div className="col-md-6">
                                    <input required style={{ width: "100%" }} placeholder="0"
                                        type='number'
                                        step='0.000000000000000001'
                                        min='0'
                                        onChange={(e) => handleEditCostChange(e, "costAmount")}
                                        value={editCostDTO.costAmount} />
                                </div>

                            </div>
                            <div className="row">
                                <div className="col-md-6">
                                    <p>Đã thanh toán</p>
                                </div>
                                <div className="col-md-6">
                                    <input style={{ width: "100%" }}
                                        type='number'
                                        step='0.000000000000000001'
                                        min='0'
                                        value={editCostDTO.paidAmount}
                                        onChange={(e) => handleEditCostChange(e, "paidAmount")} />
                                </div>
                                <div className="col-md-6">
                                    <p>Ghi chú</p>
                                </div>
                                <div className="col-md-6">
                                    <textarea style={{ width: "100%" }}
                                        value={editCostDTO.note}
                                        onChange={(e) => handleEditCostChange(e, "note")} />
                                </div>
                            </div>
                        </div>
                    </Modal.Body>
                    <div className='model-footer'>
                        <button style={{ width: "30%" }} className="col-md-6 btn-light" type='submit'>
                            Cập nhật
                        </button>
                        <button style={{ width: "20%" }} type='button' onClick={handleClose2} className="btn btn-light">
                            Huỷ
                        </button>
                    </div>
                </form>
            </Modal>

            {/* End: Table for supplier list */}
            <ToastContainer position="top-left"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="colored" />

        </div>
    );
}
export default Cost;