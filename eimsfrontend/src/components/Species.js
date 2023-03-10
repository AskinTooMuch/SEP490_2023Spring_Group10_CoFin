import React, { useState, useEffect } from 'react';
import axios from '../api/axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { Modal } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import ConfirmBox from './ConfirmBox';
const Species = () => {
  const [open, setOpen] = useState(false);
  const [specieList, setSpecieList] = useState([]);
  //ConfirmBox
  function openDelete(data) {
    setOpen(true);
  }
  // Create new specie JSON
  const [newSpecieDTO, setNewSpecieDTO] = useState({
    userId: sessionStorage.getItem("curUserId"),
    specieName: '',
    incubationPeriod: '',
    embryolessDate: '',
    diedEmbryoDate: '',
    hatchingDate: ''
  })
  // Save specie JSON
  const [editSpecieDTO, setEditSpecieDTO] = useState({
    specieId: "",
    specieName: "",
    incubationPeriod: "",
    embryolessDate: "",
    diedEmbryoDate: "",
    hatchingDate: ""
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
    if (!validate("edit")) return;
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
      setEditSpecieDTO({
        specieId: "",
        specieName: "",
        incubationPeriod: "",
        embryolessDate: "",
        diedEmbryoDate: "",
        hatchingDate: ""
      });
      console.log(response)
      loadSpecieList();
      toast.success("Sửa thông tin loài thành công");
      setShow2(false);
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else if (err.response?.status === 400) {
        toast.error('Yêu cầu không đúng định dạng');
      } else if (err.response?.status === 401) {
        toast.error('Không có quyền thực hiện hành động này');
      } else {
        toast.error('Yêu cầu không đúng định dạng');
      }
    }
  }

  //Validate inputs
  function validate(type) {
    let dtos;
    switch (type) {
      case "new": dtos = newSpecieDTO;
        break;
      case "edit": dtos = editSpecieDTO;
        break;
      default: return;
    }
    if (dtos.specieName.trim() === "") {
      toast.error("Tên loài không để trống");
      return false;
    }
    return true;
  }
  // Handle submit to send request to API (Create new specie)
  const handleNewSpecieSubmit = async (event) => {
    event.preventDefault();
    console.log(newSpecieDTO);
    //Validate and toasts
    if (!validate("new")) return;

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
        incubationPeriod: "",
        embryolessDate: "",
        diedEmbryoDate: "",
        hatchingDate: ""
      });
      console.log(response)
      loadSpecieList();
      toast.success("Tạo loài mới thành công");
      setShow(false);
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else if (err.response?.status === 400) {
        toast.error('Yêu cầu không đúng định dạng');
      } else if (err.response?.status === 401) {
        toast.error('Không có quyền thực hiện hành động này');
      } else {
        toast.error('Yêu cầu không đúng định dạng');
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
      setOpen(false);
      loadSpecieList();
      toast.success("Xóa loài thành công");
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else if (err.response?.status === 400) {
        toast.error('Yêu cầu không đúng định dạng');
      } else if (err.response?.status === 401) {
        toast.error('Không có quyền thực hiện hành động này');
      } else {
        toast.error('Yêu cầu không đúng định dạng');
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
    console.log(specieList);
  }

  // Load data into edit specie fields
  function LoadData(index) {
    console.log(index);
    setEditSpecieDTO(specieList[index]);
    // editSpecieDTO.specieId = specieList[index].specieId;
    // editSpecieDTO.specieName = specieList[index].specieName;
    // editSpecieDTO.incubationPeriod = specieList[index].incubationPeriod;
    // editSpecieDTO.embryolessDate = specieList[index].embryolessDate;
    // editSpecieDTO.diedEmbryoDate = specieList[index].diedEmbryoDate;
    // editSpecieDTO.
    setShow2(true);
  }

  return (
    <div>
      <nav className="navbar justify-content-between">
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
        <button className='btn btn-light' onClick={handleShow}>+ Thêm</button>
        <Modal show={show} onHide={handleClose}
          size="lg"
          aria-labelledby="contained-modal-title-vcenter"
          centered >
          <form onSubmit={handleNewSpecieSubmit}>
            <Modal.Header closeButton onClick={handleClose}>
              <Modal.Title>Thêm loài mới</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              {/* Add new specie form */}
              <div className="changepass">
                {/**Basic inf */}
                <div className="row">
                  <div className="col-md-6 ">
                    <p>Tên loài<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                  </div>
                  <div className="col-md-6">
                    <input placeholder='Gà, Ngan, Vịt, v.v'
                      onChange={e => handleNewSpecieChange(e, "specieName")}
                      value={newSpecieDTO.specieName}
                      required
                      className="form-control" />
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-6 ">
                    <p>Thời gian ấp<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                  </div>
                  <div className="col-md-6">
                    <input placeholder='Số ngày ấp'
                      onChange={e => handleNewSpecieChange(e, "incubationPeriod")}
                      value={newSpecieDTO.incubationPeriod}
                      type="number"
                      min="0"
                      required
                      className="form-control" />
                  </div>
                </div>
                {/**Incubation phase inf: only 3 params are needed; the rest could be auto-generated*/}
                <div className="row">
                  <div className="col-md-6 ">
                    <p>Ngày xác định các giai đoạn ấp của trứng</p>
                  </div>
                  <div className="col-md-6">
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-6 ">
                    <p>Trứng trắng/tròn (trứng không có phôi)&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                  </div>
                  <div className="col-md-6">
                    <input placeholder='Ngày thứ ...'
                      onChange={e => handleNewSpecieChange(e, "embryolessDate")}
                      value={newSpecieDTO.embryolessDate}
                      type="number"
                      min="0"
                      max={newSpecieDTO.incubationPeriod}
                      required
                      className="form-control" />
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-6 ">
                    <p>Trứng loãng/tàu (phôi chết non)&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                  </div>
                  <div className="col-md-6">
                    <input placeholder='Ngày thứ ...'
                      onChange={e => handleNewSpecieChange(e, "diedEmbryoDate")}
                      value={newSpecieDTO.diedEmbryoDate}
                      type="number"
                      min={newSpecieDTO.embryolessDate}
                      max={newSpecieDTO.incubationPeriod}
                      required
                      className="form-control" />
                  </div>
                  <div className="col-md-6 ">
                    <p>Trứng đang nở&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                  </div>
                  <div className="col-md-6">
                    <input placeholder='Ngày thứ ...'
                      onChange={e => handleNewSpecieChange(e, "hatchingDate")}
                      type="number"
                      min={newSpecieDTO.diedEmbryoDate}
                      max={newSpecieDTO.incubationPeriod}
                      value={newSpecieDTO.hatchingDate}
                      required
                      className="form-control" />
                  </div>
                </div>
              </div>
            </Modal.Body>
            <div className='model-footer'>
              <button style={{ width: "20%" }} type="submit" className="col-md-6 btn-light" >
                Tạo
              </button>
              <button className='btn btn-light' style={{ width: "20%" }} onClick={handleClose}>
                Huỷ
              </button>
            </div>
          </form>
        </Modal>


      </nav>
      <div>
        {/*Table for list of species */}
        <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
          <div className="u-clearfix u-sheet u-sheet-1">
            <div className="u-expanded-width u-table u-table-responsive u-table-1">
              <table className="u-table-entity u-table-entity-1" id="list_specie_table">
                <colgroup>
                  <col width="5%" />
                  <col width="40%" />
                  <col width="35%" />
                  <col width="20%" />
                </colgroup>
                <thead className="u-palette-4-base u-table-header u-table-header-1">
                  <tr style={{ height: "21px" }}>
                    <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">STT</th>
                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2" scope="col">Tên loài</th>
                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2" scope="col">Tổng thời gian ấp nở</th>
                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2" scope="col"> </th>
                  </tr>
                </thead>
                <tbody id="specie_list_table_body" className="u-table-body">
                  { /**JSX to load rows */}
                  {
                    specieList.map((item, index) => (
                      item.status &&
                      <tr key={item.specieId} data-key={index} className='trclick2' style={{ height: "21px" }}>
                        <th className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5" scope="row" onClick={() => LoadData(index)}>{index + 1} </th>
                        <td className="u-border-1 u-border-grey-30 u-table-cell" onClick={() => LoadData(index)}>{item.specieName}</td>
                        <td className="u-border-1 u-border-grey-30 u-table-cell" onClick={() => LoadData(index)}>{item.incubationPeriod} (ngày)</td>
                        <td className="u-border-1 u-border-grey-30 u-table-cell" style={{textAlign:"center"}}><button className='btn btn-light' style={{ width: "50%" }} onClick={() => openDelete(index)}>Xoá</button>
                          <ConfirmBox open={open} closeDialog={() => setOpen(false)} title={item.specieName} deleteFunction={() => handleDelete(index)}
                          />
                        </td>
                      </tr>
                    ))
                  }
                  {/**Popup edit spicies */}
                  <Modal show={show2} onHide={() => handleClose2}
                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered
                    onSubmit={handleEditSpecieSubmit}>
                    {/**Edit species */}
                    <form>
                      <Modal.Header closeButton onClick={handleClose2}>
                        <Modal.Title>Sửa thông tin loài</Modal.Title>
                      </Modal.Header>
                      <Modal.Body>
                        <div className="speiciesfix">
                          {/**Basic inf */}
                          <div className="row">
                            <div className="col-md-6 ">
                              <p>Tên loài<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                            </div>
                            <div className="col-md-6">
                              <input placeholder='Gà, Ngan, Vịt, v.v' id="editSpecieName"
                                value={editSpecieDTO.specieName}
                                onChange={e => handleEditSpecieChange(e, "specieName")}
                                required
                                className='form-control' />
                            </div>
                          </div>
                          <div className="row">
                            <div className="col-md-6 ">
                              <p>Thời gian ấp<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                            </div>
                            <div className="col-md-6">
                              <input placeholder='Số ngày ấp'
                                id="editIncubationPeriod"
                                value={editSpecieDTO.incubationPeriod}
                                onChange={e => handleEditSpecieChange(e, "incubationPeriod")}
                                required
                                type="number"
                                min="0"
                                className='form-control' />
                            </div>
                          </div>
                          {/**Incubation phase inf: only 3 params are needed; the rest could be auto-generated*/}
                          <div className="row">
                            <div className="col-md-6 ">
                              <p>Ngày xác định các giai đoạn ấp của trứng</p>
                            </div>
                            <div className="col-md-6">
                            </div>
                          </div>
                          <div className="row">
                            <div className="col-md-6 ">
                              <p>Trứng trắng/tròn (trứng không có phôi)&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                            </div>
                            <div className="col-md-6">
                              <input placeholder='Ngày thứ ...'
                                onChange={e => handleEditSpecieChange(e, "embryolessDate")}
                                value={editSpecieDTO.embryolessDate}
                                type="number"
                                min="0"
                                max={editSpecieDTO.incubationPeriod}
                                required
                                className="form-control" />
                            </div>
                          </div>
                          <div className="row">
                            <div className="col-md-6 ">
                              <p>Trứng loãng/tàu (phôi chết non)&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                            </div>
                            <div className="col-md-6">
                              <input placeholder='Ngày thứ ...'
                                onChange={e => handleEditSpecieChange(e, "diedEmbryoDate")}
                                value={editSpecieDTO.diedEmbryoDate}
                                type="number"
                                min={editSpecieDTO.embryolessDate}
                                max={editSpecieDTO.incubationPeriod}
                                required
                                className="form-control" />
                            </div>
                            <div className="col-md-6 ">
                              <p>Trứng đang nở&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                            </div>
                            <div className="col-md-6">
                              <input placeholder='Ngày thứ ...'
                                onChange={e => handleEditSpecieChange(e, "hatchingDate")}
                                type="number"
                                min={editSpecieDTO.diedEmbryoDate}
                                max={editSpecieDTO.incubationPeriod}
                                value={editSpecieDTO.hatchingDate}
                                required
                                className="form-control" />
                            </div>
                          </div>
                        </div>
                      </Modal.Body>
                      <div className='model-footer'>
                        <button style={{ width: "30%" }} type='submit' className="col-md-6 btn-light">
                          Cập nhật
                        </button>
                        <button style={{ width: "20%" }} onClick={handleClose2} className="btn btn-light">
                          Huỷ
                        </button>
                      </div>
                    </form>
                  </Modal>
                </tbody>
              </table>
            </div>
          </div>
        </section>
        {/**Thông báo */}
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