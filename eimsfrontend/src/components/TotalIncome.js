import React, { useState, useEffect } from 'react';
import SearchIcon from '@mui/icons-material/Search';
import "../css/totalincome.css"
import axios from 'axios';
//Toast
import { toast } from 'react-toastify';
const TotalIncome = () => {
    // Dependency
    const [dataLoaded, setDataLoaded] = useState(false);

    // API URLs
    const GET_IN_MONTH = 'api/incomeReport/inMonth';
    const GET_IN_YEAR = 'api/incomeReport/inYear';

    // Data holding objects
    const [inMonthList, setInMonthList] = useState({
        costList: [],
        payrollList: [],
        importList: [],
        exportList: [],
        incomeNow: "",
        incomeLast: ""
    });

    const [inYearList, setInYearList] = useState({
        yearList: [],
        costList: [],
        payrollList: [],
        importList: [],
        exportList: [],
        incomeNow: "",
        incomeLast: ""
    });


    // var year to search
    var year = new Date().getFullYear();

    // Set value
    useEffect(() => {
        if (dataLoaded) return;
        //loadInMonth(year);
        loadInYear();
        setDataLoaded(true);
    }, [dataLoaded]);


    // load report in month for current year
    const loadDefaultInMonth = async () => {
        try {
            const result = await axios.get(GET_IN_MONTH,
                {
                    params: {
                        userId: sessionStorage.getItem("curUserId"),
                        year: year
                    },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setInMonthList({
                costList: result.data.costList,
                payrollList: result.data.payrollList,
                importList: result.data.importList,
                exportList: result.data.exportList,
                incomeNow: result.data.incomeNow,
                incomeLast: result.data.incomeLast
            });
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

    // load report in year
    const loadInYear = async () => {
        try {
            const result = await axios.get(GET_IN_YEAR,
                {
                    params: {
                        userId: sessionStorage.getItem("curUserId")
                    },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setInYearList({
                yearList: result.data.yearList,
                costList: result.data.costList,
                payrollList: result.data.payrollList,
                importList: result.data.importList,
                exportList: result.data.exportList,
                incomeNow: result.data.incomeNow,
                incomeLast: result.data.incomeLast
            })
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

    // handle choose year
    const handleFilter = (event) => {
        let actualValue = event.target.value;
        loadInMonth(actualValue);
    }

    const loadInMonth = async (yearChosen) => {
        try {
            const result = await axios.get(GET_IN_MONTH,
                {
                    params: {
                        userId: sessionStorage.getItem("curUserId"),
                        year: yearChosen
                    },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setInMonthList({
                costList: result.data.costList,
                payrollList: result.data.payrollList,
                importList: result.data.importList,
                exportList: result.data.exportList,
                incomeNow: result.data.incomeNow,
                incomeLast: result.data.incomeLast
            })
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
                <input type="radio" name="slider" id="month" defaultChecked />
                <input type="radio" name="slider" id="year" />
                <nav>
                    <label htmlFor="month" className="month">Tháng</label>
                    <label htmlFor="year" className="year">Năm</label>
                    <div className="slider"></div>
                </nav>
                <br />
                <section>
                    <div className="content content-1">
                        <select onChange={(e) => handleFilter(e)}
                            style={{ width: "fit-content" }} id="selectEgg" name="selectEgg" className="form-select" aria-label="Default select example">
                            <option value={0} disabled selected>Chọn năm</option>
                            {
                                inYearList.yearList && inYearList.yearList.length > 0
                                    ? inYearList.yearList.map((item) =>
                                        <option value={item}>{item}</option>
                                    )
                                    : ''
                            }
                        </select>
                        <br />
                        <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                            <div className="u-clearfix u-sheet u-sheet-1">
                                <div className="u-expanded-width u-table u-table-responsive u-table-1">
                                    <table className="u-table-entity u-table-entity-1">
                                        <colgroup>
                                            <col width="4%" />
                                            <col width="16%" />
                                            <col width="16%" />
                                            <col width="16%" />
                                            <col width="16%" />
                                            <col width="16%" />
                                            <col width="16%" />
                                        </colgroup>
                                        <thead className="u-palette-4-base u-table-header u-table-header-1">
                                            <tr>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Tháng</th>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Nhập (đ)</th>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Xuất (đ)</th>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Tiền lương (đ)</th>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Chi phí (đ)</th>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Lợi nhuận (đ)</th>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Thực thu (đ)</th>
                                            </tr>
                                        </thead>
                                        <tbody className="u-table-body">
                                            {
                                                inMonthList.costList && inMonthList.costList.length > 0
                                                    ? inMonthList.costList.map((item, index) =>
                                                        <tr>
                                                            <th className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-1" scope="row">{index + 1}</th>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{inMonthList.importList[index].paid.toLocaleString()}/{inMonthList.importList[index].total.toLocaleString()}</td>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{inMonthList.exportList[index].paid.toLocaleString()}/{inMonthList.exportList[index].total.toLocaleString()}</td>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{inMonthList.payrollList[index].total.toLocaleString()}</td>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{inMonthList.costList[index].paid.toLocaleString()}/{inMonthList.costList[index].total.toLocaleString()}</td>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{(- inMonthList.costList[index].total - inMonthList.payrollList[index].total - inMonthList.importList[index].total + inMonthList.exportList[index].total).toLocaleString()}</td>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{(- inMonthList.costList[index].paid - inMonthList.payrollList[index].total - inMonthList.importList[index].paid + inMonthList.exportList[index].paid).toLocaleString()}</td>
                                                        </tr>
                                                    ) : ''
                                            }
                                        </tbody>
                                    </table>

                                    <div className='total-icome' >
                                        <p>Lợi nhuận cuối: <span id="incomeTrue">{inMonthList.incomeLast.toLocaleString('vi', { style: 'currency', currency: 'VND' })}</span></p>
                                    </div>
                                    <div className='total-icome' >
                                        <p>Thực thu: <span id="incomeNow">{inMonthList.incomeNow.toLocaleString('vi', { style: 'currency', currency: 'VND' })}</span></p>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                    <br />

                    <div className="content content-2">
                        <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                            <div className="u-clearfix u-sheet u-sheet-1">
                                <div className="u-expanded-width u-table u-table-responsive u-table-1">
                                    <table className="u-table-entity u-table-entity-1">
                                        <colgroup>
                                            <col width="4%" />
                                            <col width="16%" />
                                            <col width="16%" />
                                            <col width="16%" />
                                            <col width="16%" />
                                            <col width="16%" />
                                            <col width="16%" />
                                        </colgroup>
                                        <thead className="u-palette-4-base u-table-header u-table-header-1">
                                            <tr>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Năm</th>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Nhập (đ)</th>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Xuất (đ)</th>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Tiền lương (đ)</th>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Chi phí (đ)</th>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Lợi nhuận (đ)</th>
                                                <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1" scope="col">Thực thu (đ)</th>
                                            </tr>
                                        </thead>
                                        {
                                            inYearList.costList && inYearList.costList.length > 0
                                                ? inYearList.costList.map((item, index) =>
                                                    <tbody className="u-table-body">
                                                        <tr>
                                                            <th className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-1" scope="row">{inYearList.yearList[index]}</th>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{inYearList.importList[index].paid.toLocaleString()}/{inYearList.importList[index].total.toLocaleString()}</td>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{inYearList.exportList[index].paid.toLocaleString()}/{inYearList.exportList[index].total.toLocaleString()}</td>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{inYearList.payrollList[index].paid.toLocaleString()}/{inYearList.payrollList[index].total.toLocaleString()}</td>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{inYearList.costList[index].paid.toLocaleString()}/{inYearList.costList[index].total.toLocaleString()}</td>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{(- inYearList.costList[index].total - inYearList.payrollList[index].total - inYearList.importList[index].total + inYearList.exportList[index].total).toLocaleString()}</td>
                                                            <td className="u-border-1 u-border-grey-30 u-table-cell">{(- inYearList.costList[index].paid - inYearList.payrollList[index].total - inYearList.importList[index].paid + inYearList.exportList[index].paid).toLocaleString()}</td>
                                                        </tr>
                                                    </tbody>
                                                )
                                                : ''
                                        }
                                    </table>
                                    <div className='total-icome' >
                                        <p>Lợi nhuận cuối: <span id="incomeLast">{inYearList.incomeLast.toLocaleString('vi', { style: 'currency', currency: 'VND' })}</span></p>
                                    </div>
                                    <div className='total-icome' >
                                        <p>Thực thu: <span id="incomeNow">{inYearList.incomeNow.toLocaleString('vi', { style: 'currency', currency: 'VND' })}</span></p>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </section>
            </div>
        </div>
    );
}
export default TotalIncome;