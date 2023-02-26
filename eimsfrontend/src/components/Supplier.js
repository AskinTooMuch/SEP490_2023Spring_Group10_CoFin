import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { Modal, Button } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from 'axios';
//Toast
import { ToastContainer, toast } from 'react-toastify';
const Supplier = () => {
  //Show-hide Popup
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  //Data holding objects
  const [supplierList, setSupplierList] = useState([]);

  //API URLs
  const SUPPLIER_ALL = '/api/supplier/all';
  const SUPPLIER_DETAIL = "/api/supplier";
  const SUPPLIER_CREATE = "/api/supplier/create";
  const SUPPLIER_UPDATE = "/api/supplier/update";
  const SUPPLIER_DELETE = "/api/supplier/delete";
  const SUPPLIER_SEARCH = "/api/supplier/search";

  //DTOs
  //CreateSupplierDTO
  const [newSupplierDTO, setNewSupplierDTO] = useState({
    userId: sessionStorage.getItem("curUserId"),
    supplierName: "",
    supplierPhone: "",
    supplierAddress: "",
    supplierMail: ""
  })


  //Handle Change functions:
  //CreateSupplier
  const handleNewSupplierChange = (event, field) => {
    let actualValue = event.target.value
    setNewSupplierDTO({
      ...newSupplierDTO,
      [field]: actualValue
    })
  }

  //Handle Submit functions
  //Handle submit new supplier
  const handleNewSupplierSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(SUPPLIER_CREATE,
        newSupplierDTO,
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: false
        }
      );
      setNewSupplierDTO({
        userId: sessionStorage.getItem("curUserId"),
        supplierName: "",
        supplierPhone: "",
        supplierAddress: "",
        supplierMail: ""
      });
      console.log(response);
      loadSupplerList();
      toast.success("Tạo loại mới thành công");
      setShow(false);
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else switch(err.response?.status) {
        case 400: toast.error('Request không hợp lệ');
        case 401: toast.error('Unauthorized');
      }
    }
  }

  // Get list of supplier and show
  //Get supplier list
  useEffect(() => {
    loadSupplerList();
  }, []);

  // Request supplier list and load the supplier list into the table rows
  const loadSupplerList = async () => {
    const result = await axios.get(SUPPLIER_ALL,
      { params: { userId: sessionStorage.getItem("curUserId") } },
      {
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*'
        },
        withCredentials: false
      });
    setSupplierList(result.data);
    console.log(supplierList);
  }

  //Navigate to detail Page
  let navigate = useNavigate();
  const routeChange = () => {
    let path = '/supplierdetail';
    navigate(path);
  }

  return (
    <div>
      <nav className="navbar justify-content-between">
        <button className='btn btn-light' onClick={handleShow}>+ Thêm</button>
        {/* Start: form to add new supplier */}
        <form><Modal show={show} onHide={handleClose}
          size="lg" aria-labelledby="contained-modal-title-vcenter" centered >
          <Modal.Header closeButton onClick={handleClose}>
            <Modal.Title>Thêm thông tin nhà cung cấp</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div className="changepass">
              <div className="row">
                <div className="col-md-3">
                  <p>Họ và tên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                </div>
                <div className="col-md-3">
                  <input style={{ width: "100%" }} onChange={e => handleNewSupplierChange(e, "supplierName")}/>
                </div>
                <div className="col-md-3">
                  <p>Số nhà <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                </div>
                <div className="col-md-3">
                  <input style={{ width: "100%" }} disabled/>
                </div>
              </div>
              <div className="row">
                <div className="col-md-3">
                  <p>Số điện thoại<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                </div>
                <div className="col-md-3">
                  <input style={{ width: "100%" }} onChange={e => handleNewSupplierChange(e, "supplierPhone")}/>
                </div>
                <div className="col-md-3">
                  <p>Đường <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                </div>
                <div className="col-md-3">
                  <input style={{ width: "100%" }} disabled/>
                </div>
              </div>
              <div className="row">
                <div className="col-md-3">
                  <p>Email</p>
                </div>
                <div className="col-md-3">
                  <input style={{ width: "100%" }} onChange={e => handleNewSupplierChange(e, "supplierMail")}/>
                </div>
                <div className="col-md-3">
                  <p>Quận/Huyện <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                </div>
                <div className="col-md-3">
                  <input style={{ width: "100%" }} disabled/>
                </div>
              </div>
              <div className="row">
                <div className="col-md-3">
                  <p>Tên cơ sở<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                </div>
                <div className="col-md-3">
                  <input style={{ width: "100%" }} disabled/>
                </div>
                <div className="col-md-3">
                  <p>Thành phố <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                </div>
                <div className="col-md-3">
                  <input style={{ width: "100%" }} onChange={e => handleNewSupplierChange(e, "supplierAddress")}/>
                </div>
              </div>
            </div>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="danger" style={{ width: "20%" }} onClick={handleClose}>
              Huỷ
            </Button>
            <Button variant="success" style={{ width: "20%" }} className="col-md-6" onClick={handleNewSupplierSubmit}>
              Tạo
            </Button>
          </Modal.Footer>
        </Modal>
        </form>
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
        <table className="table table-bordered">
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
            {
              supplierList && supplierList.length > 0 ?
                supplierList.map(supplier =>

                  <tr className='trclick' onClick={routeChange} key={supplier.userId}>

                    <th scope="row">{supplier.supplierId}</th>
                    <td>{supplier.supplierName}</td>
                    <td>{supplier.supplierName}</td>
                    <td>{supplier.supplierPhone}</td>
                    <td>100.000.000 VNĐ</td>
                    <td className='text-green'>{supplier.status}</td>
                  </tr>

                ) : 'Loading'
            }
          </tbody>
        </table>
      </div>
      {/* End: Table for supplier list */}
    </div>
  );
}
export default Supplier;