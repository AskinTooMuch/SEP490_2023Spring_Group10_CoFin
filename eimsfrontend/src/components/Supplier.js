import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import SearchIcon from '@mui/icons-material/Search';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import { Modal } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from 'axios';
//Toast
import { toast } from 'react-toastify';

const Supplier = () => {
  const userRef = useRef();
  const [dataLoaded, setDataLoaded] = useState(false);
  //Show-hide Popup
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  //Data holding objects
  const [supplierList, setSupplierList] = useState([]);
  //Address consts
  //Full Json addresses
  const [fullAddresses, setFullAddresses] = useState('');
  const [city, setCity] = useState([
    { value: '', label: 'Chọn Tỉnh/Thành phố' }
  ]);
  //Supplier Address data
  const [district, setDistrict] = useState(''); //For populate dropdowns
  const [ward, setWard] = useState('');
  const [cityIndex, setCityIndex] = useState(); //Save the index of selected dropdowns
  const [districtIndex, setDistrictIndex] = useState();
  const [wardIndex, setWardIndex] = useState();
  const [street, setStreet] = useState();
  const [addressJson, setAddressJson] = useState({
    city: "",
    district: "",
    ward: "",
    street: ""
  });


  //API URLs
  const SUPPLIER_ALL = '/api/supplier/all';
  const SUPPLIER_CREATE = "/api/supplier/create";

  //DTOs
  //CreateSupplierDTO
  const [newSupplierDTO, setNewSupplierDTO] = useState({
    userId: sessionStorage.getItem("curUserId"),
    supplierName: "",
    supplierPhone: "",
    supplierAddress: "",
    facilityName: "",
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

  // Set value for address fields
  //User
  useEffect(() => {
    loadAddress();
  }, [dataLoaded]);

  const loadAddress = async () => {
    const result = await axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
      {});
    setFullAddresses(result.data);
    // Set inf

    const cityList = fullAddresses.slice();
    for (let i in cityList) {
      cityList[i] = { value: cityList[i].Id, label: cityList[i].Name }
    }
    setCity(cityList);
    setDataLoaded(true);
  }

  function loadDistrict(index) {
    console.log("City " + index);
    setCityIndex(index);
    const districtOnIndex = fullAddresses[index].Districts;
    const districtList = districtOnIndex.slice();
    for (let i in districtList) {
      districtList[i] = { value: districtList[i].Id, label: districtList[i].Name }
    }
    setDistrict(districtList);
  }

  //Load user ward list
  function loadWard(index) {
    console.log("District " + index);
    setDistrictIndex(index);
    const wardOnIndex = fullAddresses[cityIndex].Districts[index].Wards;
    const wardList = wardOnIndex.slice();
    for (let i in wardList) {
      wardList[i] = { value: wardList[i].Id, label: wardList[i].Name }
    }
    setWard(wardList);
  }

  function saveWard(index) {
    console.log("Ward " + index);
    setWardIndex(index);
  }

  function saveAddressJson(s) {
    setStreet(s);
    if (cityIndex >= 0) {
      addressJson.city = fullAddresses[cityIndex].Name;
    }
    setStreet(s);
    if (districtIndex >= 0) {
      addressJson.district = fullAddresses[cityIndex].Districts[districtIndex].Name;
    }
    setStreet(s);
    if (wardIndex >= 0) {
      addressJson.ward = fullAddresses[cityIndex].Districts[districtIndex].Wards[wardIndex].Name;
    }
    addressJson.street = street;
    newSupplierDTO.supplierAddress = JSON.stringify(addressJson);
  }



  //Handle Submit functions
  //Handle submit new supplier
  const handleNewSupplierSubmit = async (event) => {
    event.preventDefault();
    saveAddressJson(street);
    let response;
    try {
      response = await axios.post(SUPPLIER_CREATE,
        newSupplierDTO,
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: true
        }
      );
      setNewSupplierDTO({
        userId: sessionStorage.getItem("curUserId"),
        supplierName: "",
        supplierPhone: "",
        supplierAddress: "",
        facilityName: "",
        supplierMail: ""
      });
      setAddressJson({
        city: "",
        district: "",
        ward: "",
        street: ""
      });
      console.log(response);
      loadSupplerList();
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

  // Get list of supplier and show
  //Get supplier list
  useEffect(() => {
    loadSupplerList();
  }, []);

  // Request supplier list and load the supplier list into the table rows
  const loadSupplerList = async () => {
    try {
      const result = await axios.get(SUPPLIER_ALL,
        {
          params: { userId: sessionStorage.getItem("curUserId") },
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: true
        });
      setSupplierList(result.data);
      console.log(supplierList);
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

  //Navigate to detail Page
  let navigate = useNavigate();
  const routeChange = (sid) => {
    navigate('/supplierdetail', { state: { id: sid } });
  }

  return (
    <div>
      <nav className="navbar justify-content-between">
        <button className='btn btn-light' onClick={handleShow} id="startCreateSupplier">+ Thêm</button>
        {/* Start: form to add new supplier */}
        <Modal show={show} onHide={handleClose}
          size="lg" aria-labelledby="contained-modal-title-vcenter" centered >
          <form onSubmit={handleNewSupplierSubmit}>
            <Modal.Header closeButton onClick={handleClose}>
              <Modal.Title>Thêm thông tin nhà cung cấp</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <div className="changepass">
                <div className="row">
                  <div className="col-md-4">
                    <label htmlFor='supplierName' className='col-form-label'>Họ và tên&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                  </div>
                  <div className="col-md-8">
                    <input id="supplierName"
                      className="form-control mt-1"
                      style={{ width: "100%" }}
                      placeholder='Tên/biệt danh gợi nhớ'
                      onChange={e => handleNewSupplierChange(e, "supplierName")} />
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-4">
                    <label htmlFor='supplierPhoneNumber' className='col-form-label'>Điện thoại&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                  </div>
                  <div className="col-md-8">
                    <input id="supplierPhoneNumber"
                      className="form-control mt-1"
                      style={{ width: "100%" }}
                      placeholder='Số điện thoại Việt Nam'
                      onChange={e => handleNewSupplierChange(e, "supplierPhone")} />
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-4">
                    <label htmlFor='supplierEmail' className='col-form-label'>Email</label>
                  </div>
                  <div className="col-md-8">
                    <input id="supplierEmail"
                      className="form-control mt-1"
                      style={{ width: "100%" }}
                      placeholder='Địa chỉ thư điện tử'
                      onChange={e => handleNewSupplierChange(e, "supplierMail")} />
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-4">
                    <label htmlFor='supplierFacilityName' className='col-form-label'>Tên cơ sở&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                  </div>
                  <div className="col-md-8">
                    <input id="supplierFacilityName"
                      className="form-control mt-1"
                      style={{ width: "100%" }}
                      onChange={e => handleNewSupplierChange(e, "facilityName")}
                      placeholder='Tên cơ sở cung cấp' />
                  </div>
                </div>
                <div className='row'>
                  {/*City*/}
                  <div className="col-md-4">
                    <label htmlFor="uprovince" className='col-form-label'>Tỉnh/Thành Phố&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                  </div>
                  <div className="col-md-8">
                    <select className="form-control mt-1" id="uprovince"
                      ref={userRef}
                      autoComplete="off"
                      onChange={(e) => loadDistrict(e.target.value)}
                      value={cityIndex}>
                      <option value="" disabled selected>Chọn Tỉnh/Thành phố</option>
                      {city &&
                        city.map((item, index) => (
                          <option value={index}>{item.label}</option>
                        ))
                      }
                    </select>
                  </div>
                </div>
                <div className='row'>
                  {/*District*/}
                  <div className="col-md-4">
                    <label htmlFor="udistrict" className='col-form-label'>Quận/Huyện&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                  </div>
                  <div className="col-md-8">
                    <select className="form-control mt-1" id="udistrict" name="supplierDistrict"
                      ref={userRef}
                      autoComplete="off"
                      onChange={(e) => loadWard(e.target.value)}
                      value={districtIndex}>
                      <option value="" disabled selected>Chọn Quận/Huyện</option>
                      {district &&
                        district.map((item, index) => (
                          <option value={index}>{item.label}</option>
                        ))
                      }
                    </select>
                  </div>
                </div>
                <div className='row'>
                  {/*User ward*/}
                  <div className="col-md-4">
                    <label htmlFor="uward" className='col-form-label'>Phường/Xã&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                  </div>
                  <div className="col-md-8">
                    <select className="form-control mt-1" id="uward" name="supplierWard"
                      ref={userRef}
                      autoComplete="off"
                      onChange={(e) => saveWard(e.target.value)}
                      value={wardIndex}>
                      <option value="" disabled selected>Chọn Phường/Xã</option>
                      {ward &&
                        ward.map((item, index) => (
                          <option value={index}>{item.label}</option>
                        ))
                      }
                    </select>
                  </div>
                </div>
                <div className='row'>
                  {/*User Street*/}
                  <div className="col-md-4">
                    <label htmlFor="uhomenum" className='col-form-label'>Số nhà&nbsp;<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                  </div>
                  <div className="col-md-8">
                    <input type="text" id="uhomenum"
                      ref={userRef}
                      autoComplete="off"
                      onChange={(e) => saveAddressJson(e.target.value)}
                      className="form-control mt-1"
                      placeholder='Địa chỉ cụ thể' />
                  </div>
                </div>
              </div>
            </Modal.Body>
            <div className='model-footer'>
              <button style={{ width: "20%" }} type="submit" className="col-md-6 btn-light" id="confirmCreateSupplier">
                Tạo
              </button>
              <button className='btn btn-light' type='button' style={{ width: "20%" }} onClick={handleClose} id="cancelCreateSupplier">
                Huỷ
              </button>
            </div>
          </form>
        </Modal>

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
              <input id="searchSupplier" type="text" className="form-control" placeholder="Tìm kiếm" aria-label="Username" aria-describedby="basic-addon1" />
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
              <th scope="col">Số điện thoại</th>
              <th scope="col">Trạng thái</th>
            </tr>
          </thead>
          <tbody>
            {
              supplierList && supplierList.length > 0 ?
                supplierList.map((item, index) =>
                  <tr className='trclick' onClick={() => routeChange(item.supplierId)} key={item.supplierId}>
                    <th scope="row">{index + 1}</th>
                    <td>{item.supplierName}</td>
                    <td>{item.supplierPhone}</td>
                    {item.status === 1
                      ? <td className='text-green'>
                        Đang hoạt động
                      </td>
                      : <td className='text-red'>
                        Ngừng hoạt động
                      </td>
                    }
                  </tr>
                ) :
                <tr>
                  <td colSpan='5'>Hiện tại không có nhà cung cấp nào được lưu trong hệ thống</td>
                </tr>
            }
          </tbody>
        </table>
      </div>
      {/* End: Table for supplier list */}

    </div>
  );
}
export default Supplier;