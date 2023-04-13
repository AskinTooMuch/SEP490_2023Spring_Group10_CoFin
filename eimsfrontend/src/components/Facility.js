import React, { useState, useEffect } from 'react';
import { Modal } from 'react-bootstrap';
import { toast } from 'react-toastify';
import axios from 'axios';
const Facility = () => {
    // Dependency
    const [dataLoaded, setDataLoaded] = useState(false);


    //URLs
    const USER_BY_ROLE = "/api/user/allByRole";

    // Show-hide popup
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    // dto
    const [onwerList, setOwnerList] = useState([]);
    const [morderatorList, setMorderatorList] = useState([]);

    useEffect(() => {
        if (dataLoaded) return;
        loadOwners();
        loadModerators();
        setDataLoaded(true);
    }, [dataLoaded]);

    // Load list owners
    const loadOwners = async () => {
        try {
            const result = await axios.get(USER_BY_ROLE,
                {
                    params: { roleId: 2 },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setOwnerList(result.data);
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
    // Load list moderators
    const loadModerators = async () => {
        try {
            const result = await axios.get(USER_BY_ROLE,
                {
                    params: { roleId: 4 },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setMorderatorList(result.data);
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

    return (
        <div>
            <div className="tab-wrapper">
                <input type="radio" name="slider" id="egg" defaultChecked />
                <input type="radio" name="slider" id="chicken" />
                <nav>
                    <label htmlFor="egg" className="egg">Khách hàng</label>
                    <label htmlFor="chicken" className="chicken">Điều hành</label>
                    <div className="slider"></div>
                </nav>
                <section>
                    {/* Tab Khách hàng */}
                    <div className="content content-1">
                        <br />
                        <div>
                            <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                                <div className="u-clearfix u-sheet u-sheet-1">
                                    <div className="u-expanded-width u-table u-table-responsive u-table-1">
                                        <table className="u-table-entity u-table-entity-1">
                                            <colgroup>
                                                <col width="5%" />
                                                <col width="20%" />
                                                <col width="15%" />
                                                <col width="15%" />
                                                <col width="20%" />
                                                <col width="15%" />
                                                <col width="10%" />
                                            </colgroup>
                                            <thead className="u-palette-4-base u-table-header u-table-header-1">
                                                <tr style={{ height: "21px" }}>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Tên khách hàng</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Số điện thoại</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Ngày sinh</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Tên cơ sở</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Mã số kinh doanh</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Trạng thái</th>
                                                </tr>
                                            </thead>
                                            <tbody className="u-table-body">
                                                {
                                                    onwerList && onwerList.length > 0
                                                        ? onwerList.map((item, index) =>
                                                            <tr className='trclick' style={{ height: "76px" }} >
                                                                <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">{index + 1}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.userName}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.phone}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.dob}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.facilityName}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.businessLicenseNumber}</td>
                                                                {
                                                                    item.status === 2
                                                                        ?
                                                                        <td className="u-border-1 u-border-grey-30 u-table-cell text-green">
                                                                            Đang hoạt động
                                                                        </td>
                                                                        :
                                                                        <td className="u-border-1 u-border-grey-30 u-table-cell text-red">
                                                                            Dừng hoạt động
                                                                        </td>
                                                                }
                                                            </tr>
                                                        )
                                                        :
                                                        <tr>
                                                            <td colSpan='7'>Hiện tại không có khách hàng nào</td>
                                                        </tr>
                                                }

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>

                    {/* Tab Điều hành */}
                    <div className="content content-2">
                        <br />
                        <div>
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
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Tên</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Số điện thoại</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Trạng thái</th>
                                                </tr>
                                            </thead>
                                            <tbody className="u-table-body">
                                                {
                                                    morderatorList && morderatorList.length > 0 ?
                                                        morderatorList.map((item, index) =>
                                                            <tr className='trclick'>
                                                                <th className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-1" scope="row">{index + 1}</th>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.userName}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.phone}</td>

                                                                {
                                                                    item.status === 2
                                                                        ?
                                                                        <td className="u-border-1 u-border-grey-30 u-table-cell text-green">
                                                                            Đang hoạt động
                                                                        </td>
                                                                        :
                                                                        <td className="u-border-1 u-border-grey-30 u-table-cell text-red">
                                                                            Dừng hoạt động
                                                                        </td>
                                                                }
                                                            </tr>
                                                        ) :
                                                        <tr>
                                                            <td colSpan='5'>Chưa có máy nào được lưu lên hệ thống</td>
                                                        </tr>
                                                }

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
                </section>
            </div>
        </div >

    );
}

export default Facility;