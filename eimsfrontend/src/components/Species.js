import React, { useState, useEffect } from 'react';
import axios from '../api/axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { Modal } from 'react-bootstrap'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import ConfirmBox from './ConfirmBox';
import { faInfoCircle, faStarOfLife } from "@fortawesome/free-solid-svg-icons";
const Species = () => {
  const [open, setOpen] = useState(false);
  const [specieList, setSpecieList] = useState([]);
  //ConfirmBox
  function openDelete() {
    setOpen(true);
  }
  // Create new specie JSON
  const [newSpecieDTO, setNewSpecieDTO] = useState({
    userId: sessionStorage.getItem("curUserId"),
    specieName: "",
    incubationPeriod: "",
    embryolessDate: "",
    diedEmbryoDate: "",
    hatchingDate: "",
    balutDate: ""
  })
  // Save specie JSON
  const [editSpecieDTO, setEditSpecieDTO] = useState({
    specieId: "",
    specieName: "",
    incubationPeriod: "",
    embryolessDate: "",
    diedEmbryoDate: "",
    hatchingDate: "",
    balutDate: ""
  })
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [show2, setShow2] = useState(false);
  const handleClose2 = () => setShow2(false);

  // Define urls
  const SPECIE_LIST = '/api/specie/list';
  const SPECIE_EDIT_SAVE = '/api/specie/edit/save';
  const SPECIE_EDIT_GET = '/api/specie/edit/get';
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
    try {
      const response = await axios.post(SPECIE_EDIT_SAVE,
        editSpecieDTO,
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: true
        }
      );
      setEditSpecieDTO({
        specieId: "",
        specieName: "",
        incubationPeriod: "",
        embryolessDate: "",
        diedEmbryoDate: "",
        hatchingDate: "",
        balutDate: ""
      });
      loadSpecieList();
      toast.success(response.data);
      setShow2(false);
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
          withCredentials: true
        }
      );
      setNewSpecieDTO({
        userId: sessionStorage.getItem("curUserId"),
        specieName: "",
        incubationPeriod: "",
        embryolessDate: "",
        diedEmbryoDate: "",
        hatchingDate: "",
        balutDate: ""
      });
      loadSpecieList();
      toast.success(response.data);
      setShow(false);
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
  //

  // Handle submit to send request to API (Delete specie)
  const handleDelete = async (index) => {
    try {
      const response = await axios.get(SPECIE_DELETE,
        {
          params: { specieId: specieList[index].specieId },
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: true
        });
      console.clear(response)
      loadSpecieList();
      setOpen(false);
      toast.success(response.data);
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
  //

  // Get list of specie and show
  //Get specie list
  useEffect(() => {
    loadSpecieList();
  }, []);

  // Request specie list and load the specie list into the table rows
  const loadSpecieList = async () => {
    try {
      const result = await axios.get(SPECIE_LIST,
        {
          params: { userId: sessionStorage.getItem("curUserId") },
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: true
        });
      setSpecieList(result.data);
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

  // Load data into edit specie fields
  const LoadData = async (index) => {
    try {
      const result = await axios.get(SPECIE_EDIT_GET,
        {
          params: { specieId: specieList[index].specieId },
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: true
        });
      setEditSpecieDTO(result.data);
      setShow2(true);
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

  const handleNewCancel = () => {
    handleClose();
    setNewSpecieDTO({
      userId: sessionStorage.getItem("curUserId"),
      specieName: "",
      incubationPeriod: "",
      embryolessDate: "",
      diedEmbryoDate: "",
      hatchingDate: "",
      balutDate: ""
    });
  }

  const handleEditCancel = () => {
    handleClose2();
  }

  return (
    <div>
      <nav className="navbar justify-content-between">
        <button id="startCreateSpecie" className='btn btn-light' onClick={handleShow}>+ Thêm</button>
        <div className='filter my-2 my-lg-0'>
          <p><FilterAltIcon />Lọc</p>
          <p><ImportExportIcon />Sắp xếp</p>
          <form className="form-inline">
            <div className="input-group">
              <div className="input-group-prepend">
                <button><span className="input-group-text" ><SearchIcon /></span></button>
              </div>
              <input type="text" className="form-control" placeholder="Tìm kiếm" aria-label="Username" aria-describedby="basic-addon1" />
            </div>
          </form>
        </div>

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
                    <label className='col-form-label'>Tên loài&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                  </div>
                  <div className="col-md-6">
                    <input placeholder='Gà, Ngan, Vịt, v.v'
                      onChange={e => handleNewSpecieChange(e, "specieName")}
                      value={newSpecieDTO.specieName}
                      className="form-control mt-1" />
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-6 ">
                    <label className='col-form-label'>Thời gian ấp - số ngày&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                  </div>
                  <div className="col-md-6">
                    <input placeholder='Số ngày ấp'
                      onChange={e => handleNewSpecieChange(e, "incubationPeriod")}
                      value={newSpecieDTO.incubationPeriod}
                      type="number"
                      className="form-control mt-1" />
                  </div>
                </div>
                {/**Incubation phase inf: only 3 params are needed; the rest could be auto-generated*/}
                <div className="row">
                  <div className="col-md-12">
                    <label className='col-form-label'>Ngày xác định các giai đoạn ấp của trứng. </label>
                  </div>
                </div>
                <div className="row align-items-center">
                  <div className="col-md-6">
                    <span className='col-form-label'>Trứng trắng/tròn - số ngày&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></span>
                    <span id="embryolessNote"
                      data-text="Ngày có thể xác định được trứng có phôi hay không.
                      Là giai đoạn đầu tiên trong quá trình ấp.
                      Được tính bằng số nguyên lớn hơn 0."
                      className="tip valid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                  </div>
                  <div className="col-md-6">
                    <input placeholder='Ngày thứ ...'
                      onChange={e => handleNewSpecieChange(e, "embryolessDate")}
                      value={newSpecieDTO.embryolessDate}
                      type="number"
                      className="form-control mt-1" />
                  </div>
                </div>
                <div className="row align-items-center">
                  <div className="col-md-6">
                    <span className='col-form-label'>Trứng loãng/tàu - số ngày&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></span>
                    <span id="diedEmbryoDate"
                      data-text="Ngày có thể xác định được phôi trứng còn sống hay không. 
                      Là giai đoạn sau khi xác định trứng trắng và trước khi xác định trứng lộn.
                      Được tính bằng số nguyên lớn hơn 0."
                      className="tip valid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                  </div>
                  <div className="col-md-6">
                    <input placeholder='Ngày thứ ...'
                      onChange={e => handleNewSpecieChange(e, "diedEmbryoDate")}
                      value={newSpecieDTO.diedEmbryoDate}
                      type="number"
                      className="form-control mt-1" />
                  </div>
                </div>
                <div className='row align-items-center'>
                  <div className="col-md-6">
                    <span className='col-form-label'>Trứng lộn - số ngày&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></span>
                    <span id="balutDate"
                      data-text="Ngày có thể xác định được trứng lộn. 
                      Là giai đoạn sau ngày xác định trứng loãng và trước ngày đưa trứng đi nở.
                      Được tính bằng số nguyên lớn hơn 0."
                      className="tip valid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                  </div>
                  <div className="col-md-6">
                    <input placeholder='Ngày thứ ...'
                      onChange={e => handleNewSpecieChange(e, "balutDate")}
                      type="number"
                      value={newSpecieDTO.balutDate}
                      className="form-control mt-1" />
                  </div>
                </div>
                <div className='row align-items-center'>
                  <div className="col-md-6">
                    <span>Trứng đang nở - số ngày&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></span>
                    <span id="hatchingDate"
                      data-text="Ngày đưa trứng đưa vào máy nở. 
                      Là giai đoạn cuối cùng trước khi nở ra con thành phẩm.
                      Được tính bằng số nguyên lớn hơn 0."
                      className="tip valid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                  </div>
                  <div className="col-md-6">
                    <input placeholder='Ngày thứ ...'
                      onChange={e => handleNewSpecieChange(e, "hatchingDate")}
                      type="number"
                      value={newSpecieDTO.hatchingDate}
                      className="form-control mt-1" />
                  </div>
                </div>
              </div>
            </Modal.Body>
            <div className='model-footer'>
              <button style={{ width: "20%" }} type="submit" className="col-md-6 btn-light" >
                Tạo
              </button>
              <button className='btn btn-light' style={{ width: "20%" }} onClick={handleNewCancel} type='button'>
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
                        <td className="u-border-1 u-border-grey-30 u-table-cell" style={{ textAlign: "center" }}><button className='btn btn-light' style={{ width: "50%" }} onClick={() => openDelete()}>Xoá</button>
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
                              <label className='col-form-label'>Tên loài&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                            </div>
                            <div className="col-md-6">
                              <input placeholder='Gà, Ngan, Vịt, v.v' id="editSpecieName"
                                onChange={e => handleEditSpecieChange(e, "specieName")}
                                value={editSpecieDTO.specieName}
                                className='form-control mt-1' />
                            </div>
                          </div>
                          <div className="row">
                            <div className="col-md-6 ">
                              <label className='col-form-label'>Thời gian ấp - số ngày&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                            </div>
                            <div className="col-md-6">
                              <input placeholder='Số ngày ấp'
                                id="editIncubationPeriod"
                                onChange={e => handleEditSpecieChange(e, "incubationPeriod")}
                                value={editSpecieDTO.incubationPeriod}
                                type="number"
                                className='form-control mt-1' />
                            </div>
                          </div>
                          {/**Incubation phase inf: only 3 params are needed; the rest could be auto-generated*/}
                          <div className="row">
                            <div className="col-md-12">
                              <label className='col-form-label'>Ngày xác định các giai đoạn ấp của trứng. </label>
                            </div>
                          </div>
                          <div className="row align-items-center">
                            <div className="col-md-6 ">
                              <span className='col-form-label'>Trứng trắng/tròn - số ngày&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></span>
                              <span id="embryolessNoteEdit"
                                data-text="Ngày có thể xác định được trứng có phôi hay không. 
                                Là giai đoạn đầu tiên trong quá trình ấp.
                                Được tính bằng số nguyên lớn hơn 0."
                                className="tip valid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                            </div>
                            <div className="col-md-6">
                              <input placeholder='Ngày thứ ...'
                                onChange={e => handleEditSpecieChange(e, "embryolessDate")}
                                value={editSpecieDTO.embryolessDate}
                                type="number"
                                className="form-control mt-1" />
                            </div>
                          </div>
                          <div className="row align-items-center">
                            <div className="col-md-6">
                              <span className='col-form-label'>Trứng loãng/tàu - số ngày&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></span>
                              <span id="diedEmbryoDateEdit"
                                data-text="Ngày có thể xác định được phôi trứng còn sống hay không. 
                                Là giai đoạn sau khi xác định trứng trắng và trước khi xác định trứng lộn.
                                Được tính bằng số nguyên lớn hơn 0."
                                className="tip valid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                            </div>
                            <div className="col-md-6">
                              <input placeholder='Ngày thứ ...'
                                onChange={e => handleEditSpecieChange(e, "diedEmbryoDate")}
                                value={editSpecieDTO.diedEmbryoDate}
                                type="number"
                                className="form-control mt-1" />
                            </div>
                          </div>
                          <div className='row align-items-center'>
                            <div className="col-md-6">
                              <span className='col-form-label'>Trứng lộn - số ngày&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></span>
                              <span id="balutDateEdit"
                                data-text="Ngày có thể xác định được trứng lộn. 
                                Là giai đoạn sau ngày xác định trứng loãng và trước ngày đưa trứng đi nở.
                                Được tính bằng số nguyên lớn hơn 0."
                                className="tip valid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                            </div>
                            <div className="col-md-6">
                              <input placeholder='Ngày thứ ...'
                                onChange={e => handleEditSpecieChange(e, "balutDate")}
                                type="number"
                                value={editSpecieDTO.balutDate}
                                className="form-control mt-1" />
                            </div>
                          </div>
                          <div className='row align-items-center'>
                            <div className="col-md-6 ">
                              <span>Trứng đang nở - số ngày&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></span>
                              <span id="hatchingDateEit"
                                data-text="Ngày đưa trứng đưa vào máy nở. 
                                Là giai đoạn cuối cùng trước khi nở ra con thành phẩm.
                                Được tính bằng số nguyên lớn hơn 0."
                                className="tip valid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                            </div>
                            <div className="col-md-6">
                              <input placeholder='Ngày thứ ...'
                                onChange={e => handleEditSpecieChange(e, "hatchingDate")}
                                type="number"
                                value={editSpecieDTO.hatchingDate}
                                className="form-control mt-1" />
                            </div>
                          </div>
                        </div>
                      </Modal.Body>
                      <div className='model-footer'>
                        <button style={{ width: "30%" }} type='submit' className="col-md-6 btn-light">
                          Cập nhật
                        </button>
                        <button style={{ width: "20%" }} onClick={handleEditCancel} type='button' className="btn btn-light">
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