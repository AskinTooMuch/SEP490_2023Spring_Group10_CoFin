import React, { useState, useEffect } from 'react';
import { Modal } from 'react-bootstrap';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import SearchIcon from '@mui/icons-material/Search';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
const Registrators = () => {
    // API url
    const REGISTRATION_GET_LIST = '/api/registration/all';
    const REGISTRATION_GET = '/api/registration/get';
    const REGISTRATION_APPROVE = '/api/registration/approve';

    // Dependency
    const [dataLoaded, setDataLoaded] = useState(false);
    // Show-hide popup
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    // Data holding objects
    const [registrationList, setRegistrationList] = useState([]);
    const [registrationDetail, setRegistrationDetail] = useState({
        registrationId: "",
        userId: "",
        facilityId: "",
        username: "",
        phone: "",
        dob: "",
        email: "",
        address: "",
        facilityName: "",
        facilityFoundDate: "",
        businessLicenseNumber: "",
        facilityAddress: "",
        hotline: "",
        registerDate: "",
        status: ""
    });
    //address
    const [userAddress, setUserAddress] = useState(
        {
            street: "",
            ward: "",
            district: "",
            city: ""
        }
    );
    const [faciAddress, setFaciAddress] = useState(
        {
            street: "",
            ward: "",
            district: "",
            city: ""
        }
    );
    // Get list of Registration and show
    // Get Registration list
    useEffect(() => {
        if (dataLoaded) return;
        loadRegistrationList();
        setDataLoaded(true)
    }, []);

    // Request Registration list and load the Registration list into the table rows
    const loadRegistrationList = async () => {
        const result = await axios.get(REGISTRATION_GET_LIST,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setRegistrationList(result.data);
    }

    // Handle get Registration detail
    const getRegistrationDetail = async (userId) => {
        setShow(true);
        const result = await axios.get(REGISTRATION_GET,
            {
                params: { userId: userId },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setRegistrationDetail(result.data);
        setUserAddress(JSON.parse(result.data.address));
        setFaciAddress(JSON.parse(result.data.facilityAddress));
    }

    // Handle Approve or Decline registration
    // Approve
    const handleApprove = async (event) => {
        event.preventDefault();
        let response;
        try {
            response = await axios.post(REGISTRATION_APPROVE, {},
                {
                    params: {
                        userId: registrationDetail.userId,
                        facilityId: registrationDetail.facilityId,
                        approval: true
                    },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );

            console.log(response);
            toast.success("Đã phê duyệt đơn đăng ký của " + registrationDetail.username);
            setRegistrationDetail('');
            loadRegistrationList();
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

    // Decline
    const handleDecline = async (event) => {
        event.preventDefault();
        let response;
        try {
            response = await axios.post(REGISTRATION_APPROVE, {},
                {
                    params: {
                        userId: registrationDetail.userId,
                        facilityId: registrationDetail.facilityId,
                        approval: false
                    }
                    ,
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );

            console.log(response);
            toast.success("Đã từ chối đơn đăng ký của " + registrationDetail.username);
            setRegistrationDetail('');
            loadRegistrationList();
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

    return (
        <>
            <nav className="navbar justify-content-between">
                <Modal show={show} onHide={handleClose}
                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered >
                    <form>
                        <Modal.Header closeButton onClick={handleClose}>
                            <Modal.Title>Thông tin cơ sở</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div className="row">
                                <div className="col-md-3">
                                    <p>Chủ cơ sở</p>
                                </div>
                                <div className="col-md-3">
                                    <p>{registrationDetail.username}</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Tên cơ sở</p>
                                </div>
                                <div className="col-md-3">
                                    <p>{registrationDetail.facilityName}</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-3">
                                    <p>Số điện thoại</p>
                                </div>
                                <div className="col-md-3">
                                    <p>{registrationDetail.phone}</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Ngày thành lập</p>
                                </div>
                                <div className="col-md-3">
                                    <p>{registrationDetail.facilityFoundDate}</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-3">
                                    <p>Ngày sinh</p>
                                </div>
                                <div className="col-md-3">
                                    <p>{registrationDetail.dob}</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Mã đăng ký kinh doanh</p>
                                </div>
                                <div className="col-md-3">
                                    <p>{registrationDetail.businessLicenseNumber}</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-3">
                                    <p>Email</p>
                                </div>
                                <div className="col-md-3">
                                    <p>{registrationDetail.email}</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Hotline</p>
                                </div>
                                <div className="col-md-3">
                                    <p>{registrationDetail.hotline}</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-3">
                                    <p>Địa chỉ thường trú</p>
                                </div>
                                <div className="col-md-3">
                                    <p>{userAddress.ward + " " + userAddress.street + " " + userAddress.district + " " + userAddress.city}</p>
                                </div>
                                <div className="col-md-3">
                                    <p>Địa chỉ cơ sở</p>
                                </div>
                                <div className="col-md-3">
                                    <p>{faciAddress.ward + " " + faciAddress.street + " " + faciAddress.district + " " + faciAddress.city}</p>
                                </div>
                            </div>

                        </Modal.Body>
                        <div className='model-footer'>
                            <button style={{ width: "20%" }} className="col-md-6 btn-light" onClick={handleApprove}>
                                Duyệt
                            </button>
                            <button style={{ width: "20%" }} onClick={handleDecline} className="btn btn-light">
                                Từ chối
                            </button>
                        </div>
                    </form>
                </Modal>
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
            </nav>
            <div>
                <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                    <div className="u-clearfix u-sheet u-sheet-1">
                        <div className="u-expanded-width u-table u-table-responsive u-table-1">
                            <table className="u-table-entity u-table-entity-1">
                                <colgroup>
                                    <col width="5%" />
                                    <col width="15%" />
                                    <col width="15%" />
                                    <col width="20%" />
                                    <col width="15%" />
                                    <col width="15%" />
                                    <col width="15%" />
                                </colgroup>
                                <thead className="u-palette-4-base u-table-header u-table-header-1">
                                    <tr style={{ height: "21px" }}>
                                        <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Chủ cơ sở</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Số điện thoại</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Tên cơ sở</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Ngày thành lập</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Mã đăng ký kinh doanh</th>
                                        <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-7">Ngày đăng ký</th>
                                    </tr>
                                </thead>
                                <tbody className="u-table-body">
                                    {
                                        registrationList && registrationList.length > 0 ?
                                            registrationList.map((item, index) =>
                                                <tr style={{ height: "76px" }} onClick={() => getRegistrationDetail(item.userId)} >
                                                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">1</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.username}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.phone}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.facilityName}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.facilityFoundDate}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.businessLicenseNumber}</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">{item.registerDate}</td>
                                                </tr>
                                            ) : ''
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
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
        </>

    );
}

export default Registrators;