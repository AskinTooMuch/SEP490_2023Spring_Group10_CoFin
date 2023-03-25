import { faBell } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useState } from 'react';
import { Modal } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import "../css/notification.css"

const NotificationList = () => {
    //Show-hide Popup
    const [show, setShow] = useState(false);
    const handleClosePopup = () => setShow(false);
    const handleShowPopup = () => setShow(true);
    return (
        <div>
            <section class="section-50">
                <div class="container">
                    <h3 class="m-b-50 heading-line">Thông báo <FontAwesomeIcon icon={faBell} /></h3>
                    <div class="notification-ui_dd-content">
                        {/* unread notification */}
                        <div class="notification-list notification-list--unread" onClick={handleShowPopup}>
                            <div class="notification-list_content">
                                <div class="notification-list_detail">
                                    <p><b>Lô GFJ816</b> hoàn thành giai đoạn ấp lần 2 cần chiếu trứng</p>
                                    <p class="text-muted"><small>30 phút trước</small></p>
                                </div>
                            </div>
                        </div>
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
                                        <p>Lô trứng 01 có 800 quả ở máy số 1, 200 quả ở máy số 2, 300 quả ở máy số 3.
                                            Hiện đang cần thực hiện soi trứng lần 1
                                        </p>
                                        <p className='ptime'>30 phút trước</p>
                                    </div>
                                </div>
                            </Modal.Body>
                            <div className='model-footer'>
                                <button style={{ width: "30%" }} className="col-md-6 btn-light" type="submit">
                                    Cập nhật lô
                                </button>
                                <button style={{ width: "20%" }} onClick={handleClosePopup} className="btn btn-light" type="button">
                                    Thoát
                                </button>
                            </div>
                        </Modal>
                        <div class="notification-list notification-list--unread">
                            <div class="notification-list_content">
                                <div class="notification-list_detail">
                                    <p><b>Lô HFS173</b> hoàn thành giai đoạn ấp lần 1 cần chiếu trứng</p>
                                    <p class="text-muted"><small>1 giờ trước</small></p>
                                </div>
                            </div>
                        </div>
                        <div class="notification-list notification-list--unread">
                            <div class="notification-list_content">
                                <div class="notification-list_detail">
                                    <p><b>Lô GFJ816</b> hoàn thành giai đoạn ấp lần 1 cần chiếu trứng</p>
                                    <p class="text-muted"><small>2 giờ trước</small></p>
                                </div>
                            </div>
                        </div>
                        <div class="notification-list notification-list--unread">
                            <div class="notification-list_content">
                                <div class="notification-list_detail">
                                    <p><b>Lô HCD712</b> hoàn thành giai đoạn ấp lần 1 cần chiếu trứng</p>
                                    <p class="text-muted"><small>2 giờ trước</small></p>
                                </div>
                            </div>
                        </div>
                        <div class="notification-list notification-list--unread">
                            <div class="notification-list_content">
                                <div class="notification-list_detail">
                                    <p><b>Lô HCK081</b> hoàn thành giai đoạn ấp lần 1 cần chiếu trứng</p>
                                    <p class="text-muted"><small>3 giờ trước</small></p>
                                </div>
                            </div>
                        </div>

                        {/* read notification */}
                        <div class="notification-list">
                            <div class="notification-list_content">
                                <div class="notification-list_detail">
                                    <p><b>Lô GFJ816</b> hoàn thành giai đoạn ấp lần 1 cần chiếu trứng</p>
                                    <p class="text-muted"><small>2 giờ trước</small></p>
                                </div>
                            </div>
                        </div>
                        <div class="notification-list">
                            <div class="notification-list_content">
                                <div class="notification-list_detail">
                                    <p><b>Lô GFJ816</b> hoàn thành giai đoạn ấp lần 1 cần chiếu trứng</p>
                                    <p class="text-muted"><small>2 giờ trước</small></p>
                                </div>
                            </div>

                        </div>
                    </div>

                    <div class="text-center">
                        <Link class="dark-link">Load more activity</Link>
                    </div>

                </div>
            </section>

        </div>
    );
}

export default NotificationList;
