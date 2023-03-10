import React, { useState, useEffect, useRef } from 'react';
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
    console.log("Load address");
    loadAddress();
  }, [dataLoaded]);

  const loadAddress = async () => {
    const result = await axios.get( "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
      {});
    setFullAddresses(result.data);
    console.log("Full address");
    console.log(fullAddresses);
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
    addressJson.city = fullAddresses[cityIndex].Name;
    addressJson.district = fullAddresses[cityIndex].Districts[districtIndex].Name;
    addressJson.ward = fullAddresses[cityIndex].Districts[districtIndex].Wards[wardIndex].Name;
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
          withCredentials: false
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
      console.log(response);
      loadSupplerList();
      toast.success("Tạo thành công");
      setShow(false);
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else {
        toast.error(response);
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
  const routeChange = (sid) => {
    // let path = '/supplierdetail';
    // navigate(path, { state: { id: id, supplier: supplierList[sid]}});
    navigate('/supplierdetail', { state: { id: sid } });
  }

  return (
    <div>
      <nav className="navbar justify-content-between">
        <button className='btn btn-light' onClick={handleShow}>+ Thêm</button>
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
                <div className="col-md-3">
                  <p>Họ và tên<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                </div>
                <div className="col-md-3">
                  <input name = "supplierName" className="form-control mt-1" style={{ width: "100%" }} onChange={e => handleNewSupplierChange(e, "supplierName")} />
                </div>
                {/*City*/}
                <div className="col-md-3">
                  <label htmlFor="uprovince" >Tỉnh/Thành Phố <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                </div>
                <div className="col-md-3">
                  <select className="form-control mt-1" id="uprovince" name="supplierCity"
                    ref={userRef}
                    autoComplete="off"
                    onChange={(e) => loadDistrict(e.target.value)}
                    value={cityIndex}
                    required>
                    <option value="" disabled>Chọn Tỉnh/Thành phố</option>
                    {city &&
                      city.map((item, index) => (
                        <option value={index}>{item.label}</option>
                      ))
                    }
                  </select>
                </div>
              </div>
              <div className="row">
                <div className="col-md-3">
                  <p>Số điện thoại<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                </div>
                <div className="col-md-3">
                  <input className="form-control mt-1" style={{ width: "100%" }} onChange={e => handleNewSupplierChange(e, "supplierPhone")} />
                </div>
                {/*District*/}
                <div className="col-md-3">
                  <label htmlFor="udistrict" >Quận/Huyện <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                </div>
                <div className="col-md-3">
                  <select className="form-control mt-1" id="udistrict" name="supplierDistrict"
                    ref={userRef}
                    autoComplete="off"
                    onChange={(e) => loadWard(e.target.value)}
                    value={districtIndex}
                    required>
                    <option value="" disabled>Chọn Quận/Huyện</option>
                    {district &&
                      district.map((item, index) => (
                        <option value={index}>{item.label}</option>
                      ))
                    }
                  </select>
                </div>
              </div>
              <div className="row">
                <div className="col-md-3">
                  <p>Email</p>
                </div>
                <div className="col-md-3">
                  <input className="form-control mt-1" style={{ width: "100%" }} onChange={e => handleNewSupplierChange(e, "supplierMail")} />
                </div>
                {/*User ward*/}
                <div className="col-md-3">
                  <label htmlFor="uward" >Phường/Xã <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                </div>
                <div className="col-md-3">
                  <select className="form-control mt-1" id="uward" name="supplierWard"
                    ref={userRef}
                    autoComplete="off"
                    onChange={(e) => saveWard(e.target.value)}
                    value={wardIndex}
                    required>
                    <option value="" disabled>Chọn Phường/Xã</option>
                    {ward &&
                      ward.map((item, index) => (
                        <option value={index}>{item.label}</option>
                      ))
                    }
                  </select>
                </div>
              </div>
              <div className="row">
              <div className="col-md-3">
                  <p>Tên cơ sở<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                </div>
                <div className="col-md-3">
                  <input className="form-control mt-1" style={{ width: "100%" }} onChange={e => handleNewSupplierChange(e, "facilityName")} />
                </div>
                {/*User Street*/}
                <div className="col-md-3">
                  <label htmlFor="uhomenum">Số nhà <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                </div>
                <div className="col-md-3">
                  <input type="text" id="uhomenum"
                    ref={userRef}
                    autoComplete="off"
                    onChange={(e) => saveAddressJson(e.target.value)}
                    required
                    className="form-control " />
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
              <th scope="col">Số điện thoại</th>
              <th scope="col">Doanh thu theo tháng</th>
              <th scope="col">Trạng thái</th>
            </tr>
          </thead>
          <tbody>
            {
              supplierList && supplierList.length > 0 ?
                supplierList.map((item, index) => 
                  <tr className='trclick' onClick={() => routeChange(item.supplierId)} key={item.userId}>
                    <th scope="row">{index+1}</th>
                    <td>{item.supplierName}</td>
                    <td>{item.supplierPhone}</td>
                    <td>100.000.000 VNĐ</td>
                    {item.status === 1
                      ?<td className='text-green'>
                        Đang hoạt động
                      </td>
                      :<td className='text-red'>
                        Ngừng hoạt động
                      </td>
                    }
                  </tr>
                ) : 'Loading'
            }
          </tbody>
        </table>
      </div>
      {/* End: Table for supplier list */}
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
  );
}
export default Supplier;