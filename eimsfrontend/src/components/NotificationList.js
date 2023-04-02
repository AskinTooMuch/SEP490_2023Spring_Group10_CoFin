import { faBell } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { Modal } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import "../css/notification.css"
import axios from '../api/axios';
import { ToastContainer, toast } from 'react-toastify';

const NotificationList = () => {
    // Dependency
    const [dataLoaded, setDataLoaded] = useState(false);

    // API URLs
    const NOTIFICATION_NEW = '/api/notification/all/new'
    const NOTIFICATION_OLD = '/api/notification/all/old'
    const NOTIFICATION_DELETE = '/api/notification/delete'


    // DTOs
    const [listNotiNew, setListNotiNew] = useState([]);
    const [listNotiOld, setListNotiOld] = useState([]);
    const [notiDetail, setNotiDetail] = useState({
        notificationId: "",
        eggBatchId: "",
        date: "",
        notificationBrief: ""
    });

    // Show-hide Popup
    const [show, setShow] = useState(false);

    const handleClosePopup = () => {
        setNotiDetail({
            notificationId: "",
            eggBatchId: "",
            date: "",
            notificationBrief: ""
        })
        setShow(false);
    }

    const handleShowPopup = (item) => {
        setNotiDetail({
            notificationId: item.notificationId,
            eggBatchId: item.eggBatchId,
            date: item.date,
            notificationBrief: item.notificationBrief
        });
        setShow(true);
    }

    useEffect(() => {
        if (dataLoaded) return;
        loadListNotiNew();
        loadListNotiOld();
        setDataLoaded(true);
    }, []);

    // Get 2 lists of noti
    // List new notification
    const loadListNotiNew = async () => {
        try {
            const result = await axios.get(NOTIFICATION_NEW,
                {
                    params: { facilityId: sessionStorage.getItem("facilityId") },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setListNotiNew(result.data);
            loadListNotiOld();
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

    // List old notification
    const loadListNotiOld = async () => {
        try {
            const result = await axios.get(NOTIFICATION_OLD,
                {
                    params: { facilityId: sessionStorage.getItem("facilityId") },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setListNotiOld(result.data);
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


    // Hanlde mark as done (delete noti)
    const deleteNoti = async (notificationId) => {
        try {
            const result = await axios.delete(NOTIFICATION_DELETE,
                {
                    params: { notificationId: notificationId },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            handleClosePopup();
            loadListNotiNew();
        loadListNotiOld();
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

    let navigate = useNavigate();

    return (
        <div>
            <section className="section-50">
                <div className="container">
                    <h3 className="m-b-50 heading-line">Thông báo <FontAwesomeIcon icon={faBell} /></h3>
                    <div className="notification-ui_dd-content">
                        {/* unread notification */}
                        {
                            listNotiNew && listNotiNew.length > 0
                                ?
                                listNotiNew.map((item) =>
                                    <div className="notification-list notification-list--unread" onClick={() => handleShowPopup(item)}>
                                        <div className="notification-list_content">
                                            <div className="notification-list_detail">
                                                <p><b>{"Lô " + item.eggBatchId + ":"}</b> {item.notificationBrief}</p>
                                                <p className="text-muted"><small>{item.date}</small></p>
                                            </div>
                                        </div>
                                    </div>
                                )
                                : ''
                        }

                        {/* read notification */}
                        {
                            listNotiOld && listNotiOld.length > 0
                                ?
                                listNotiOld.map((item) =>
                                    <div className="notification-list" onClick={() => handleShowPopup(item)}>
                                        <div className="notification-list_content">
                                            <div className="notification-list_detail">
                                                <p><b>{"Lô " + item.eggBatchId + ": "}</b> {item.notificationBrief}</p>
                                                <p className="text-muted"><small>{item.date}</small></p>
                                            </div>
                                        </div>
                                    </div>
                                )
                                : ''
                        }

                        {/* Popup detail notification */}
                        <Modal show={show} onHide={handleClosePopup}
                            size="lg"
                            aria-labelledby="contained-modal-title-vcenter"
                            centered >
                            <Modal.Header closeButton onClick={handleClosePopup}>
                                <Modal.Title>Thông báo</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <div className="row">
                                    <div className="">
                                        <p>{notiDetail.notificationBrief}
                                        </p>
                                        <p className='ptime'>{notiDetail.date}</p>
                                    </div>
                                </div>
                            </Modal.Body>
                            <div className='model-footer'>
                                <button style={{ width: "30%" }} className="col-md-6 btn-light" type="button"
                                    onClick={() => navigate('/eggbatchdetail', { state: { id: notiDetail.eggBatchId } })}>
                                    Cập nhật lô
                                </button>
                                <button style={{ width: "20%" }} onClick={handleClosePopup} className="btn btn-light" type="button">
                                    Thoát
                                </button>
                                <button style={{ width: "30%", float: "left" }} className="col-md-6 btn-light" type="button"
                                    onClick={() => deleteNoti(notiDetail.notificationId)}>
                                    Xác nhận xong
                                </button>
                            </div>
                        </Modal>


                        <div className="text-center">
                            <Link className="dark-link">Load more activity</Link>
                        </div>
                    </div>
                </div>
            </section >
        </div >
    );
}

export default NotificationList;
