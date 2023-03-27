
import React, { useState } from 'react';
import "../css/totalincome.css"
const TotalIncome = () => {
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
                <section>
                    <div className="content content-1">
                        <div className="input-date" style={{ textAlign: "end" }}>
                            Từ <input type="date"></input> Đến <input type="date"></input>
                        </div>

                        <table className="table table-bordered" >
                            <thead>
                                <tr>
                                    <th scope="col">Tháng</th>
                                    <th scope="col">Chi phí</th>
                                    <th scope="col">Tiền lương</th>
                                    <th scope="col">Chi</th>
                                    <th scope="col">Thu</th>
                                    <th scope="col">Doanh thu</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th scope="row">1</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">2</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">3</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">4</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">5</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">6</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">7</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">8</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">9</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">10</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">11</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">12</th>
                                    <td>5.000.000</td>
                                    <td>50.000.000</td>
                                    <td>250.000.000</td>
                                    <td>600.000.000</td>
                                    <td>295.000.000</td>
                                </tr>
                            </tbody>
                        </table>
                        <div className='total-icome' >
                            <p>Tổng doanh thu: <span>295.000.000 VNĐ</span></p>
                        </div>
                    </div>
                    <div className="content content-2">
                        <div className="input-date" style={{ textAlign: "end" }}>
                            Từ <input type="date"></input> Đến <input type="date"></input>
                        </div>

                        <table className="table table-bordered" >
                            <thead>
                                <tr>
                                    <th scope="col">Năm</th>
                                    <th scope="col">Chi phí</th>
                                    <th scope="col">Tiền lương</th>
                                    <th scope="col">Chi</th>
                                    <th scope="col">Thu</th>
                                    <th scope="col">Doanh thu</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th scope="row">2023</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">2022</th>
                                    <td>25.000.000</td>
                                    <td>10.000.000</td>
                                    <td>50.000.000</td>
                                    <td>250.000.000</td>
                                    <td>165.000.000</td>
                                </tr>
                                <tr>
                                    <th scope="row">2021</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <th scope="row">2020</th>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                    <td>0</td>
                                </tr>
                                
                            </tbody>
                        </table>
                    </div>
                </section>
            </div>
        </div>
    );
}
export default TotalIncome;