import React, { useState } from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import '../css/machine.css'
import { useNavigate } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import ImportExportIcon from '@mui/icons-material/ImportExport';
import SearchIcon from '@mui/icons-material/Search';
import { Modal, Button } from 'react-bootstrap'
import { faStarOfLife} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
function Machine(props) {
    const { children, value, index, ...other } = props;


    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography component="span">{children}</Typography>
                </Box>
            )}
        </div>
    );
}

Machine.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

export default function BasicTabs() {
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    let navigate = useNavigate(); 
    const routeChange = () =>{ 
      let path = '/machinedetail'; 
      navigate(path);
    }
    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Máy ấp/nở" {...a11yProps(0)} />
                </Tabs>
            </Box>
            <Machine value={value} index={0}>
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
                                    <select className="form-select" aria-label="Default select example">
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
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">STT</th>
                                <th scope="col">Tên máy</th>
                                <th scope="col">Loại máy</th>
                                <th scope="col">Sức chứa</th>
                                <th scope="col">Tiến trình</th>
                                <th scope="col">Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr className='trclick' onClick={routeChange}>
                            
                                <th scope="row">1</th>
                                <td>Máy 13</td>
                                <td>Máy ấp</td>
                                <td>1000/1000</td>
                                <td>Đang ấp</td>
                                <td className='text-green'>Đang hoạt động</td>
                            </tr>
                            <tr>
                                <th scope="row">2</th>
                                <td>Máy 29</td>
                                <td>Máy ấp</td>
                                <td>0/1000</td>
                                <td>Không có tiến trình</td>
                                <td className='text-green'>Đang hoạt động</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td>Máy 30</td>
                                <td>Máy nở</td>
                                <td>0/1000</td>
                                <td>Không có tiến trình</td>
                                <td className='text-red'>Ngừng hoạt động</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </Machine>


        </Box>
    );
}