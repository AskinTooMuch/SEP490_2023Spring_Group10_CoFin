import React, { useState, useEffect } from 'react';
import SearchIcon from '@mui/icons-material/Search';
import { ToastContainer, toast } from 'react-toastify';
import axios from 'axios';

const StockReport = () => {
    // Dependency
    const [dataLoaded, setDataLoaded] = useState(false);
    //URLs
    const EGG_PRODUCT_AVAILBLE_ALL = "/api/eggProduct/available/all";
    const EGG_PRODUCT_AVAILBLE_ONE = "/api/eggProduct/available";

    //Data holding objects
    const [allList, setAllList] = useState({
        eggStocks: [],
        poultryStocks: []
    });

    // Get list of Products and show
    useEffect(() => {
        if (dataLoaded) return;
        loadAllAvailable();
        setDataLoaded(true);
    }, [dataLoaded]);

    // Load list egg product available
    const loadAllAvailable = async () => {
        try {
            const result = await axios.get(EGG_PRODUCT_AVAILBLE_ALL,
                {
                    params: { facilityId: sessionStorage.getItem("facilityId") },
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                });
            setAllList({
                eggStocks: result.data.eggStocks,
                poultryStocks: result.data.poultryStocks
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

    // phaseNumber to filter
    const [phaseNumber, setPhaseNumber] = useState({
        egg: -1,
        poultry: -1
    });

    // handle choose product
    const handleFilter = (event, field) => {
        let actualValue = event.target.value
        setPhaseNumber({
            ...phaseNumber,
            [field]: actualValue
        })
    }

    // Load list egg product available
    const loadOneAvailable = async (phaseNumber) => {
        if (phaseNumber == -1 || phaseNumber == null) {
            loadAllAvailable();
            setDataLoaded(true);
        } else {
            console.log(phaseNumber);
            var list = ["0", "2", "3", "4", "6"];
            try {
                const result = await axios.get(EGG_PRODUCT_AVAILBLE_ONE,
                    {
                        params: {
                            facilityId: sessionStorage.getItem("facilityId"),
                            phaseNumber: phaseNumber
                        },
                        headers: {
                            'Content-Type': 'application/json',
                            'Access-Control-Allow-Origin': '*'
                        },
                        withCredentials: true
                    });
                console.log(JSON.stringify(result.data))
                if (list.includes(phaseNumber)) {
                    setAllList({
                        eggStocks: result.data
                    });
                } else {
                    setAllList({
                        poultryStocks: result.data
                    });
                }
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
    }

    return (
        <div>
            <div className="tab-wrapper">
                <input type="radio" name="slider" id="egg" defaultChecked />
                <input type="radio" name="slider" id="chicken" />
                <nav>
                    <label htmlFor="egg" className="egg">Trứng</label>
                    <label htmlFor="chicken" className="chicken">Con nở</label>
                    <div className="slider"></div>
                </nav>
                <section>
                    {/* Tab kho trứng */}
                    <div className="content content-1">
                        <nav className="navbar justify-content-between" style={{ margin: "20px 0" }}>
                            <select onChange={(e) => handleFilter(e, "egg")}
                                style={{ width: "" }} id="selectEgg" name="selectEgg" className="form-select" aria-label="Default select example">
                                <option value="-1" selected>Tất cả</option>
                                <option value="0">Trứng vỡ/dập</option>
                                <option value="2">Trứng trắng/tròn, trứng không có phôi</option>
                                <option value="3">Trứng loãng/tàu, phôi chết non</option>
                                <option value="4">Trứng lộn</option>
                                <option value="6">Trứng tắc</option>
                            </select>
                            <div className="input-group-prepend">
                                <button id="filterEgg" onClick={() => loadOneAvailable(phaseNumber.egg)}><span className="input-group-text" ><SearchIcon /></span></button>
                            </div>
                        </nav>
                        <br /><br />
                        <div>
                            <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                                <div className="u-clearfix u-sheet u-sheet-1">
                                    <div className="u-expanded-width u-table u-table-responsive u-table-1">
                                        <table className="u-table-entity u-table-entity-1">
                                            <colgroup>
                                                <col width="5%" />
                                            </colgroup>
                                            <thead className="u-palette-4-base u-table-header u-table-header-1">
                                                <tr style={{ height: "21px" }}>
                                                    <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Loại</th>
                                                    <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">Mã lô</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Sản phẩm</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Số lượng trong kho</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Ngày xuất</th>
                                                </tr>
                                            </thead>
                                            <tbody className="u-table-body">
                                                {
                                                    allList.eggStocks && allList.eggStocks.length > 0
                                                        ? allList.eggStocks.map((item, index) =>
                                                            <tr className='trclick' style={{ height: "76px" }} >
                                                                <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">{index + 1}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.breedName}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.eggBatchId}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.phaseDescription}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.curAmount}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.incubationDate.replace("T", " ")}</td>
                                                            </tr>
                                                        )
                                                        : 'Nothing'
                                                }
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>

                    {/* Tab kho gà */}
                    <div className="content content-2">
                        <nav className="navbar justify-content-between" style={{ margin: "20px 0" }}>

                            <select onChange={(e) => handleFilter(e, "poultry")}
                                style={{ width: "" }} id="selectPoultry" name="selectPoultry" className="form-select" aria-label="Default select example">
                                <option value="-1" selected>Tất cả</option>
                                <option value="7">Con nở</option>
                                <option value="8">Con đực</option>
                                <option value="9">Con cái</option>
                            </select>
                            <div className="input-group-prepend">
                                <button id="filterPoultry" onClick={() => loadOneAvailable(phaseNumber.poultry)}><span className="input-group-text" ><SearchIcon /></span></button>
                            </div>
                        </nav>
                        <br /><br />
                        <div>
                            <section className="u-align-center u-clearfix u-section-1" id="sec-b42b">
                                <div className="u-clearfix u-sheet u-sheet-1">
                                    <div className="u-expanded-width u-table u-table-responsive u-table-1">
                                        <table className="u-table-entity u-table-entity-1">
                                            <colgroup>
                                                <col width="5%" />
                                            </colgroup>
                                            <thead className="u-palette-4-base u-table-header u-table-header-1">
                                                <tr style={{ height: "21px" }}>
                                                    <th className="u-border-1 u-border-custom-color-1 u-palette-2-base u-table-cell u-table-cell-1">STT</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Loại</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Sản phẩm</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Số lượng trong kho</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Ngày xuất</th>
                                                </tr>
                                            </thead>
                                            <tbody className="u-table-body">
                                                {
                                                    allList.poultryStocks && allList.poultryStocks.length > 0
                                                        ? allList.poultryStocks.map((item, index) =>
                                                            <tr className='trclick' style={{ height: "76px" }} >
                                                                <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">{index + 1}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.breedName}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.phaseDescription}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.curAmount}</td>
                                                                <td className="u-border-1 u-border-grey-30 u-table-cell">{item.incubationDate.replace("T", " ")}</td>
                                                            </tr>
                                                        )
                                                        : 'Nothing'
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
        </div >

    );
}
export default StockReport;