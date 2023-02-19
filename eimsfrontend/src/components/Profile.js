import React, { useState } from "react";
import "../css/profile.css"
import { Modal, Button } from 'react-bootstrap'
const Profile = () => {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [show2, setShow2] = useState(false);
    
    const handleClose2 = () => setShow2(false);
    const handleShow2 = () => setShow2(true);
    return (

        <div className="profile-info">
            <div className="head">
                <h3>Thông tin cá nhân</h3>
            </div>
            <div className="row outbox">
                <div className="col-sm-6 col-xs-12">
                    <div className="card">
                        <div className="card-header">Thông tin tài khoản</div>
                        <div className="card-body">
                            <form>

                                <div className="tab-pane " >
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Tài khoản</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p>0*********0</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Họ và Tên</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p>Phạm Anh Tùng</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Ngày sinh</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p>12/05/2001</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Email</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p>@gmail.com</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <Button onClick={handleShow} className="btn btn-info">Đổi mật khẩu</Button >
                                            <form><Modal show={show} onHide={handleClose}
                                                size="lg"
                                                aria-labelledby="contained-modal-title-vcenter"
                                                centered >
                                                <Modal.Header closeButton onClick={handleClose}>
                                                    <Modal.Title>Thay đổi mật khẩu</Modal.Title>
                                                </Modal.Header>
                                                <Modal.Body>

                                                    <div className="changepass">
                                                        <div className="row">
                                                            <div className="col-md-6 ">
                                                                <p>Mật khẩu cũ</p>
                                                            </div>
                                                            <div className="col-md-6">
                                                                <input />
                                                            </div>
                                                        </div>
                                                        <div className="row">
                                                            <div className="col-md-6">
                                                                <p>Mật khẩu mới</p>
                                                            </div>
                                                            <div className="col-md-6">
                                                                <input />
                                                            </div>
                                                        </div>
                                                        <div className="row">
                                                            <div className="col-md-6">
                                                                <p>Xác nhận lại</p>
                                                            </div>
                                                            <div className="col-md-6">
                                                                <input />
                                                            </div>
                                                        </div>
                                                    </div>

                                                </Modal.Body>

                                                <Modal.Footer>
                                                    <Button variant="danger" style={{ width: "20%" }} onClick={handleClose}>
                                                        Huỷ
                                                    </Button>

                                                    <Button variant="dark" style={{ width: "30%" }} className="col-md-6" onClick={handleClose}>
                                                        Đổi mật khẩu
                                                    </Button>

                                                </Modal.Footer>
                                            </Modal>
                                            </form>
                                        </div>
                                        <div className="col-md-6">
                                            <Button onClick={handleShow2} className="btn btn-info">Cập nhật</Button >
                                            <form><Modal show={show2} onHide={handleClose2}
                                                size="lg"
                                                aria-labelledby="contained-modal-title-vcenter"
                                                centered >
                                                    <h3>Chỉnh sửa thông tin cá nhân</h3>
                                                <Modal.Body>
                                                    <div class="mb-3">
                                                        <label class="form-label">Họ và Tên</label>
                                                        <input type="text" class="form-control"/>
                                                            
                                                    </div>
                                                    <div class="mb-3">
                                                        <label  class="form-label">Ngày sinh</label>
                                                        <input type="date" class="form-control"/>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label  class="form-label">Email</label>
                                                        <input type="email" class="form-control"/>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label  class="form-label">Địa chỉ</label>
                                                        <input type="text" style={{minHeight:"50px"}} class="form-control"/>
                                                    </div>
                                                </Modal.Body>

                                                <Modal.Footer>
                                                    <Button variant="danger" style={{ width: "20%" }} onClick={handleClose2}>
                                                        Huỷ
                                                    </Button>

                                                    <Button variant="dark" style={{ width: "30%" }} className="col-md-6" onClick={handleClose2}>
                                                        Xác nhận
                                                    </Button>

                                                </Modal.Footer>
                                            </Modal>
                                            </form>
                                        </div>
                                    </div>

                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-xs-12">
                    <div class="card">
                        <div class="card-header">Thông tin cơ sở</div>
                        <div class="card-body">
                            <form>

                                <div className="tab-pane " >
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Số nhà</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p>69</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Đường</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p>...</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Quận/Huyện</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p>Thạch Thất</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Thành phố</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p>Hà Nội</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Hotline</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p>096969696</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Gói đăng ký</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p>Gói 1</p>
                                        </div>
                                    </div>
                                    <div style={{ textAlign: "center" }}>

                                        <button className="btn btn-info" style={{ width: "50%" }}>Cập nhật</button>
                                    </div>

                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    )
}

export default Profile