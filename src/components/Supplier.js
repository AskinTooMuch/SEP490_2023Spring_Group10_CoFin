import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { Modal, Button } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
const Supplier = () => {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    let navigate = useNavigate(); 
    const routeChange = () =>{ 
      let path = '/supplierdetail'; 
      navigate(path);
    }
    return(
        <>
        <nav className="navbar justify-content-between">
          <button className='btn btn-light' onClick={handleShow}>+ Thêm</button>
          <form><Modal show={show} onHide={handleClose}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered >
            <Modal.Header closeButton onClick={handleClose}>
              <Modal.Title>Thêm máy</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <div className="changepass">
                <div className="row">
                  <div className="col-md-6 ">
                    <p>Tên máy<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                  </div>
                  <div className="col-md-6">
                    <input />
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-6">
                    <p>Loại máy<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                  </div>
                  <div className="col-md-6">
                    <select class="form-select" aria-label="Default select example">
                      <option selected>Open this select menu</option>
                      <option value="1" >Oneg</option>
                      <option value="2">Two</option>
                      <option value="3">Three</option>
                    </select>
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-6">
                    <p>Sức chứa<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
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
                <th scope="col">Nhà cung cấp</th>
                <th scope="col">Tên cơ sở</th>
                <th scope="col">Số điện thoại</th>
                <th scope="col">Doanh thu theo tháng</th>
                <th scope="col">Trạng thái</th>
              </tr>
            </thead>
            <tbody>
              <tr className='trclick' onClick={routeChange}>

                <th scope="row">1</th>
                <td>Phạm Anh B</td>
                <td>Trang trại gà Thuân vài</td>
                <td>012345678910</td>
                <td>100.000.000 VNĐ</td>
                <td className='text-green'>Đang hoạt động</td>
              </tr>
              <tr>
                <th scope="row">2</th>
                <td>Bùi Thanh C</td>
                <td>Trang trại gà Đình Dương</td>
                <td>012345678910</td>
                <td>100.000.000 VNĐ</td>
                <td className='text-green'>Đang hoạt động</td>
              </tr>
              
            </tbody>
          </table>
        </div>
        </>
    );
}
export default Supplier;