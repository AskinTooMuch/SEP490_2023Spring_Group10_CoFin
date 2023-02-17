import React, { useState } from 'react';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { Modal, Button } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
const Species = () => {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    
    return(
        <>
        <nav className="navbar justify-content-between">
          <button className='btn btn-light' onClick={handleShow}>+ Thêm</button>
          <form><Modal show={show} onHide={handleClose}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered >
            <Modal.Header closeButton onClick={handleClose}>
              <Modal.Title>Thêm loài mới</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <div className="changepass">
                <div className="row">
                  <div className="col-md-6 ">
                    <p>Tên loài<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                  </div>
                  <div className="col-md-6">
                    <input />
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-6 ">
                    <p>Thời gian ấp lần 1<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                  </div>
                  <div className="col-md-6">
                    <input />
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-6">
                    <p>Thời gian ấp lần 2<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                  </div>
                  <div className="col-md-6">
                    <input />
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-6 ">
                    <p>Thời gian ấp lần 3<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                  </div>
                  <div className="col-md-6">
                    <input />
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-6 ">
                    <p>Thời gian nở<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
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

              <Button variant="success" style={{ width: "20%" }} className="col-md-6" onClick={handleClose}>
                Tạo
              </Button>

            </Modal.Footer>
          </Modal>
          </form>
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
          <table class="table table-bordered">
            <thead>
              <tr>
                <th scope="col">STT</th>
                <th scope="col">Tên loài</th>
                <th scope="col">Tổng thời gian ấp nở</th>
                <th scope="col"> </th>
              </tr>
            </thead>
            <tbody>
              <tr className='trclick' >

                <th scope="row">1</th>
                <td>Gà</td>
                <td>3 ngày 12 tiếng</td>
                <td><button className='btn btn-danger' style={{width:"50%"}}>Xoá</button></td>
                
              </tr>
              <tr>
                <th scope="row">2</th>
                <td>Vịt</td>
                <td>3 ngày 6 tiếng</td>
                <td><button className='btn btn-danger'style={{width:"50%"}}>Xoá</button></td>
              </tr>
              
            </tbody>
          </table>
        </div>
        </>
    );
}
export default Species;