import React, { useState, useRef, useEffect } from 'react';
import axios from '../api/axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { Modal, Button } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
const Species = () => {
  const NEW_SPECIE_URL = "/api/specie/new";
  const errRef = useRef();
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);


  // Create new specie JSON
  const [newSpecieDTO, setNewSpecieDTO] = useState({
    phone: sessionStorage.getItem("curPhone"),
    specieName: "",
    incubationPeriod: ""
  })
  //
  //Handle change: update new specie JSON
  const handleChange = (event, field) => {
    let actualValue = event.target.value
    setNewSpecieDTO({
      ...newSpecieDTO,
      [field]: actualValue
    })
  }

  // Handle submit to send request to API
  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(NEW_SPECIE_URL,
        newSpecieDTO,
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: false
        }
      );
      console.log(JSON.stringify(response?.data));
      setNewSpecieDTO('');
      toast.success("Tạo loài mới thành công")
      setShow(false)
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else if (err.response?.status === 400) {
        toast.error('Mật khẩu cũ không đúng');
      } else if (err.response?.status === 401) {
        toast.error('Unauthorized');
      } else {
        toast.error('Sai mật khẩu');
      }
      errRef.current.focus();
    }
  }
  //


  return (
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
                  <input placeholder='Gà, Ngan, Vịt, v.v' onChange={e => handleChange(e, "specieName")}
                    value={newSpecieDTO.specieName} />
                </div>
              </div>
              <div className="row">
                <div className="col-md-6 ">
                  <p>Thời gian ấp<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                </div>
                <div className="col-md-6">
                  <input placeholder='Số ngày ấp' onChange={e => handleChange(e, "incubationPeriod")}
                    value={newSpecieDTO.incubationPeriod} />
                </div>
              </div>
            </div>

          </Modal.Body>

          <Modal.Footer>
            <Button variant="danger" style={{ width: "20%" }} onClick={handleClose}>
              Huỷ
            </Button>

            <Button variant="success" style={{ width: "20%" }} className="col-md-6" onClick={handleSubmit}>
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
              <td><button className='btn btn-danger' style={{ width: "50%" }}>Xoá</button></td>

            </tr>
            <tr>
              <th scope="row">2</th>
              <td>Vịt</td>
              <td>3 ngày 6 tiếng</td>
              <td><button className='btn btn-danger' style={{ width: "50%" }}>Xoá</button></td>
            </tr>

          </tbody>
        </table>
      </div>
    </>
  );
}
export default Species;