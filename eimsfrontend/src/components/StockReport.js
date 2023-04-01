import React from 'react';
import SearchIcon from '@mui/icons-material/Search';

const StockReport = () => {

    return (
        <div>
            <div className="tab-wrapper">
                <input type="radio" name="slider" id="egg" defaultChecked />
                <input type="radio" name="slider" id="chicken" />
                <nav>
                    <label htmlFor="egg" className="egg">Kho trứng</label>
                    <label htmlFor="chicken" className="chicken">Kho gà</label>
                    <div className="slider"></div>
                </nav>
                <section>
                    {/* Tab kho trứng */}
                    <div className="content content-1">
                        <nav className="navbar justify-content-between" style={{ margin: "20px 0" }}>
                            <form className="form-inline">
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                        <button ><span className="input-group-text" ><SearchIcon /></span></button>
                                    </div>
                                    <input type="text" className="form-control" placeholder="Tìm kiếm" aria-label="Username" aria-describedby="basic-addon1" />
                                </div>
                            </form>
                        </nav>
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
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Loài</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Loại</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Sản phẩm</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Số lượng trong kho</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Ngày xuất</th>
                                                </tr>
                                            </thead>
                                            <tbody className="u-table-body">
                                                <tr className='trclick' style={{ height: "76px" }} >
                                                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">1</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">Gà</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">Gà ri</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">Trứng trắng</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">100.000</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">01/02/2023</td>
                                                </tr>
                                                <tr className='trclick' style={{ height: "76px" }} >
                                                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">2</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">Gà</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">Gà ri</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">Trứng trắng</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">90.000</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">15/01/2023</td>
                                                </tr>
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
                            <form className="form-inline">
                                <div className="input-group">
                                    <div className="input-group-prepend">
                                        <button ><span className="input-group-text" ><SearchIcon /></span></button>
                                    </div>
                                    <input type="text" className="form-control" placeholder="Tìm kiếm" aria-label="Username" aria-describedby="basic-addon1" />
                                </div>
                            </form>
                        </nav>
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
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Loài</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Loại</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-4">Sản phẩm</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Số lượng trong kho</th>
                                                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-6">Ngày xuất</th>
                                                </tr>
                                            </thead>
                                            <tbody className="u-table-body">
                                                <tr className='trclick' style={{ height: "76px" }} >
                                                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">1</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">Gà</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">Gà ri</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">Trứng trắng</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">100.000</td>
                                                    <td className="u-border-1 u-border-grey-30 u-table-cell">01/02/2023</td>
                                                </tr>
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
export default StockReport;