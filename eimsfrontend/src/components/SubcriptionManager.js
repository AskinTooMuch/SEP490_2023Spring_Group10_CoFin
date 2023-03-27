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
import { Modal } from 'react-bootstrap'
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
function SubcriptionManager(props) {
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

SubcriptionManager.propTypes = {
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
    const routeChange = () => {
        let path = '/subcriptiondetail';
        navigate(path);
    }
    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
                <Tabs sx={{
                    '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
                    '& .Mui-selected': { color: "#d25d19" },
                }} value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab style={{ textTransform: "capitalize" }} label="Danh sách gói" {...a11yProps(0)} />
                </Tabs>
            </Box>
            <SubcriptionManager value={value} index={0}>
                <nav className="navbar justify-content-between">
                    <button className='btn btn-light' onClick={handleShow}>+ Thêm</button>
                    <Modal show={show} onHide={handleClose}
                        size="lg"
                        aria-labelledby="contained-modal-title-vcenter"
                        centered >
                        <form>
                            <Modal.Header closeButton onClick={handleClose}>
                                <Modal.Title>Tạo gói đăng ký</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Tên gói<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input required style={{ width: "100%" }} className="form-control " />
                                    </div>
                                    <div className="col-md-6">
                                        <p>Giá gói<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input required style={{ width: "100%" }} placeholder="0" className="form-control " />
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Thời gian hiệu lực<FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                    </div>
                                    <div className="col-md-6">
                                        <input required style={{ width: "100%" }} placeholder="0" className="form-control " />
                                    </div>
                                    <div className="col-md-6">
                                        <p>Nội dung chi tiết</p>
                                    </div>
                                    <div className="col-md-6">
                                        <textarea style={{ width: "100%" }} className="form-control " />
                                    </div>
                                </div>

                            </Modal.Body>
                            <div className='model-footer'>
                                <button style={{ width: "30%" }} className="col-md-6 btn-light" onClick={handleClose}>
                                    Tạo
                                </button>
                                <button style={{ width: "20%" }} onClick={handleClose} className="btn btn-light">
                                    Huỷ
                                </button>
                            </div>
                        </form>
                    </Modal>
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
                    <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                        <div className="u-clearfix u-sheet u-sheet-1">

                            <div className="u-expanded-width u-table u-table-responsive u-table-1">
                                <table className="u-table-entity u-table-entity-1">
                                    <colgroup>
                                        <col width="5%" />
                                        <col width="19%" />
                                        <col width="19%" />
                                        <col width="19%" />
                                        <col width="19%" />
                                        <col width="19%" />
                                    </colgroup>
                                    <thead className="u-palette-4-base u-table-header u-table-header-1">
                                        <tr style={{ height: "21px" }}>
                                            <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                            <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Tên gói</th>
                                            <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Ngày tạo</th>
                                            <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Giá gói</th>
                                            <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Thời gian hiệu lực</th>
                                            <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Trạng thái</th>
                                        </tr>
                                    </thead>
                                    <tbody className="u-table-body">
                                        <tr style={{ height: "76px" }} onClick={routeChange}>
                                            <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">1</td>
                                            <td className="u-border-1 u-border-grey-30 u-table-cell">Gói 1 tháng</td>
                                            <td className="u-border-1 u-border-grey-30 u-table-cell">12/12/2022</td>
                                            <td className="u-border-1 u-border-grey-30 u-table-cell">100.000 VNĐ</td>
                                            <td className="u-border-1 u-border-grey-30 u-table-cell">30 Ngày</td>
                                            <td className="u-border-1 u-border-grey-30 u-table-cell text-green">Có hiệu lực</td>
                                        </tr>
                                        <tr style={{ height: "76px" }}>
                                            <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">1</td>
                                            <td className="u-border-1 u-border-grey-30 u-table-cell">Gói 1 năm</td>
                                            <td className="u-border-1 u-border-grey-30 u-table-cell">12/12/2022</td>
                                            <td className="u-border-1 u-border-grey-30 u-table-cell">1.000.000 VNĐ</td>
                                            <td className="u-border-1 u-border-grey-30 u-table-cell">365 Ngày</td>
                                            <td className="u-border-1 u-border-grey-30 u-table-cell text-red">Vô hiệu lực</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </section>
                </div>
            </SubcriptionManager>


        </Box>
    );
}