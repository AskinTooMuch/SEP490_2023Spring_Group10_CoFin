import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import React, { useState } from 'react';
import { Modal } from 'react-bootstrap'
import { useTheme } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import { AppBar } from '@mui/material';
import "../css/report.css"
const ImportReport = () => {
    //Hide show popup
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    //Tab change
    const [value, setValue] = React.useState(0);
    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };
    const handleChangeIndex = (index: number) => {
        setValue(index);
    };
    return (
        <div>
            <Modal show={show} onHide={handleClose}
                size="lg" aria-labelledby="contained-modal-title-vcenter" centered >
                <Modal.Header closeButton onClick={handleClose}>
                    <Modal.Title>Phạm Ngọc A</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className='container'>
                        <Box >
                            <AppBar position="absolute">
                                <Tabs
                                    value={value}
                                    onChange={handleChange}
                                    textColor="inherit"
                                    variant="fullWidth"
                                    aria-label="full width tabs example"
                                >
                                    <Tab label="Tháng" {...a11yProps(0)} />
                                    <Tab label="Năm" {...a11yProps(1)} />

                                </Tabs>
                            </AppBar>
                            <TabPanel value={value} index={0} >
                                <br />
                                <label>Năm </label>
                                <select
                                    id="select" className='form-control m-3 mt-0' style={{ display: "inline-block", width: "20%" }} >
                                    <option defaultValue="Chọn Năm">Chọn năm</option>
                                    <option value='2022'>2022</option>
                                </select>
                                <div style={{ overflow: "scroll", maxHeight: "300px" }}>
                                    <table className="table table-bordered" >
                                        <thead>
                                            <tr>
                                                <th scope="col">Tháng</th>
                                                <th scope="col">Tổng chi phí</th>
                                                <th scope="col">Đã thanh toán</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <th scope="row">1</th>
                                                <td>0</td>
                                                <td>0</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">2</th>
                                                <td>0</td>
                                                <td>0</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">3</th>
                                                <td>0</td>
                                                <td>0</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">4</th>
                                                <td>0</td>
                                                <td>0</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">5</th>
                                                <td>0</td>
                                                <td>0</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">6</th>
                                                <td>0</td>
                                                <td>0</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">7</th>
                                                <td>0</td>
                                                <td>0</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">8</th>
                                                <td>1.000.000</td>
                                                <td>1.000.000</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </TabPanel>
                            <TabPanel value={value} index={1} >
                                <br />
                                <div style={{ overflow: "scroll", maxHeight: "300px" }}>
                                    <table className="table table-bordered" >
                                        <thead>
                                            <tr>
                                                <th scope="col">Năm</th>
                                                <th scope="col">Tổng chi phí</th>
                                                <th scope="col">Đã thanh toán</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <th scope="row">2023</th>
                                                <td>0</td>
                                                <td>0</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">2022</th>
                                                <td>100.000.000</td>
                                                <td>100.000.000</td>
                                            </tr>

                                        </tbody>
                                    </table>
                                </div>
                            </TabPanel>
                        </Box>
                    </div>
                </Modal.Body>
            </Modal>
            <div className="input-date">
                Từ <input type="date"></input> Đến <input type="date"></input>
            </div>

            {/* Start: Table for import list */}
            <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                <div className="u-clearfix u-sheet u-sheet-1">
                    <div className="u-expanded-width u-table u-table-responsive u-table-1">
                        <table className="u-table-entity u-table-entity-1">
                            <colgroup>
                                <col width="5%" />
                                <col width="35%" />
                                <col width="30%" />
                                <col width="30%" />
                            </colgroup>
                            <thead className="u-palette-4-base u-table-header u-table-header-1">
                                <tr style={{ height: "21px" }}>
                                    <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Nhà cung cấp</th>
                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Tổng chi phí</th>
                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Đã thanh toán</th>
                                </tr>
                            </thead>
                            <tbody className="u-table-body">
                                <tr style={{ height: "76px" }} onClick={handleShow}>
                                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">1</td>
                                    <td className="u-border-1 u-border-grey-30 u-table-cell">Phạm Ngọc A</td>
                                    <td className="u-border-1 u-border-grey-30 u-table-cell">100.000.000 VNĐ</td>
                                    <td className="u-border-1 u-border-grey-30 u-table-cell ">100.000.000 VNĐ</td>
                                </tr>
                                <tr style={{ height: "76px" }}>
                                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-9">2</td>
                                    <td className="u-border-1 u-border-grey-30 u-table-cell">Bùi Thanh A</td>
                                    <td className="u-border-1 u-border-grey-30 u-table-cell">120.000.000 VNĐ</td>
                                    <td className="u-border-1 u-border-grey-30 u-table-cell ">100.000.000 VNĐ</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>
        </div>
    );
}
interface TabPanelProps {
    children?: React.ReactNode;
    dir?: string;
    index: number;
    value: number;
}

function TabPanel(props: TabPanelProps) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`full-width-tabpanel-${index}`}
            aria-labelledby={`full-width-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

function a11yProps(index: number) {
    return {
        id: `full-width-tab-${index}`,
        'aria-controls': `full-width-tabpanel-${index}`,
    };
}
export default ImportReport;