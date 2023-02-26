import React, { useState, useEffect } from 'react';
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

  const [specieList, setSpecieList] = useState([]);
  // Create new specie JSON
  const [newSpecieDTO, setNewSpecieDTO] = useState({
    userId: sessionStorage.getItem("curUserId"),
    specieName: "",
    incubationPeriod: ""
  })
  // Save specie JSON
  const [editSpecieDTO, setEditSpecieDTO] = useState({
    specieId: "",
    specieName: "",
    incubationPeriod: ""
  })
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [show2, setShow2] = useState(false);
  const handleClose2 = () => setShow2(false);
 
  // Define urls
  const SPECIE_LIST = '/api/specie/list';
  const SPECIE_EDIT_SAVE = '/api/specie/edit/save';
  const SPECIE_DELETE = '/api/specie/delete';
  const SPECIE_NEW = "/api/specie/new";

  //Handle change: update new specie JSON
  const handleNewSpecieChange = (event, field) => {
    let actualValue = event.target.value
    setNewSpecieDTO({
      ...newSpecieDTO,
      [field]: actualValue
    })
  }

  //Handle change: update edit specie JSON
  const handleEditSpecieChange = (event, field) => {
    let actualValue = event.target.value
    setEditSpecieDTO({
      ...editSpecieDTO,
      [field]: actualValue
    })
  }

  //Handle submit: update specie
  const handleEditSpecieSubmit = async (event) => {
    event.preventDefault();
    console.log(editSpecieDTO);
    try {
      const response = await axios.post(SPECIE_EDIT_SAVE,
        editSpecieDTO,
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: false
        }
      );
      editSpecieDTO.specieId = "";
      editSpecieDTO.specieName = "";
      editSpecieDTO.incubationPeriod = "";
      console.log(response)
      loadSpecieList();
      toast.success("Sửa thông tin loài thành công");
      setShow2(false);
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
    }
  }
  // Handle submit to send request to API (Create new specie)
  const handleNewSpecieSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(SPECIE_NEW,
        newSpecieDTO,
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: false
        }
      );
      setNewSpecieDTO({
        userId: sessionStorage.getItem("curUserId"),
        specieName: "",
        incubationPeriod: ""
      });
      console.log(response)
      loadSpecieList();
      toast.success("Tạo loài mới thành công");
      setShow(false);
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
    }
  }
  //

  // Handle submit to send request to API (Create new specie)
  const handleDelete = async (index) => {
    try {
      const response = await axios.get(SPECIE_DELETE,
        { params: { specieId: specieList[index].specieId } },
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: false
        });
        console.clear(response)
      loadSpecieList();
      toast.success("Xóa loài thành công");
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
    }
  }
  //

  // Get list of specie and show
  //Get specie list
  useEffect(() => {
    loadSpecieList();
  }, []);

  // Request specie list and load the specie list into the table rows
  const loadSpecieList = async () => {
    const result = await axios.get(SPECIE_LIST,
      { params: { userId: sessionStorage.getItem("curUserId") } },
      {
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*'
        },
        withCredentials: false
      });
    setSpecieList(result.data);
  }

  // Load data into edit specie fields
  function LoadData(index) {
    console.log(index);
    editSpecieDTO.specieId = specieList[index].specieId;
    editSpecieDTO.specieName = specieList[index].specieName;
    editSpecieDTO.incubationPeriod = specieList[index].incubationPeriod;
    setShow2(true);
  }

  return (
    <div>
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
                  <input placeholder='Gà, Ngan, Vịt, v.v' onChange={e => handleNewSpecieChange(e, "specieName")}
                    value={newSpecieDTO.specieName} />
                </div>
              </div>
              <div className="row">
                <div className="col-md-6 ">
                  <p>Thời gian ấp<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                </div>
                <div className="col-md-6">
                  <input placeholder='Số ngày ấp' onChange={e => handleNewSpecieChange(e, "incubationPeriod")}
                    value={newSpecieDTO.incubationPeriod} />
                </div>
              </div>
            </div>

          </Modal.Body>

          <Modal.Footer>
            <Button variant="danger" style={{ width: "20%" }} onClick={handleClose}>
              Huỷ
            </Button>

            <Button variant="success" style={{ width: "20%" }} className="col-md-6" onClick={handleNewSpecieSubmit}>
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
        <table className="table table-bordered" id="list_specie_table">
          <thead>
            <tr>
              <th scope="col">STT</th>
              <th scope="col">Tên loài</th>
              <th scope="col">Tổng thời gian ấp nở</th>
              <th scope="col"> </th>
            </tr>
          </thead>
          <tbody id="specie_list_table_body">
            {
              specieList.map((item, index) => (
                <tr key={item.specieId} data-key={index} className='trclick2'>
                  <th scope="row" onClick={() => LoadData(index)}>{index + 1} </th>
                  <td onClick={() => LoadData(index)}>{item.specieName}</td>
                  <td onClick={() => LoadData(index)}>{item.incubationPeriod} (ngày)</td>
                  <td><button className='btn btn-danger' style={{ width: "50%" }} onClick={() => handleDelete(index)}>Xoá</button></td>
                </tr>
              ))
            }

            <form><Modal show={show2} onHide={() => handleClose2}
              size="lg"
              aria-labelledby="contained-modal-title-vcenter"
              centered >
              <Modal.Header closeButton onClick={handleClose2}>
                <Modal.Title>Sửa thông tin loài</Modal.Title>
              </Modal.Header>
              <Modal.Body>
                <div className="speiciesfix">
                  <div className="row">
                    <div className="col-md-6 ">
                      <p>Tên loài<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                    </div>
                    <div className="col-md-6">
                      <input placeholder='Gà, Ngan, Vịt, v.v' id="editSpecieName"
                        value={editSpecieDTO.specieName} onChange={e => handleEditSpecieChange(e, "specieName")} />
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-md-6 ">
                      <p>Thời gian ấp<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                    </div>
                    <div className="col-md-6">
                      <input placeholder='Số ngày ấp' id="editIncubationPeriod" type="number"
                        value={editSpecieDTO.incubationPeriod} onChange={e => handleEditSpecieChange(e, "incubationPeriod")} />
                    </div>
                  </div>
                </div>

              </Modal.Body>

              <Modal.Footer>
                <Button variant="danger" style={{ width: "20%" }} onClick={handleClose2}>
                  Huỷ
                </Button>

                <Button variant="success" style={{ width: "20%" }} className="col-md-6" onClick={handleEditSpecieSubmit}>
                  Cập nhật
                </Button>

              </Modal.Footer>
            </Modal>
            </form>
          </tbody>
        </table>
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
    </div>
  );
}
export default Species;