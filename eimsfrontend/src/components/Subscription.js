import React from 'react';
import { useState, useRef, useEffect } from 'react';
import axios from "axios";
import "../css/subscribe.css"
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { ToastContainer, toast } from 'react-toastify';
import { useNavigate } from "react-router-dom";
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import WithPermission from "../utils.js/WithPermission";
function Subscription(props) {
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

Subscription.propTypes = {
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
  //URL
  const GET_ALL_ACTIVE_SUBSCRIPTION = '/api/subscription/getActive';

  //DATA
  const [subscriptionList, setSubscriptionList] = useState([]);

  // Get list of subscription packs and show
  //Get subscription pack list
  useEffect(() => {
    loadActiveSubscription();
  }, []);

  // Request supplier list and load the supplier list into the table rows
  const loadActiveSubscription = async () => {
    try {
      const result = await axios.get(GET_ALL_ACTIVE_SUBSCRIPTION,
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: true
        });
      setSubscriptionList(result.data);
      console.log(subscriptionList);
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
    navigate('/subscriptionPayment', { state: { id: sid } });
  }
  const [value, setValue] = React.useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <WithPermission roleRequired='2'>
      <Box sx={{ width: '100%' }}>
        <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
          <Tabs sx={{
            '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
            '& .Mui-selected': { color: "#d25d19" },
          }} value={value} onChange={handleChange} aria-label="basic tabs example">
            <Tab id="subcription" style={{ textTransform: "capitalize" }} label="Danh sách gói" {...a11yProps(0)} />
            <Tab id="history" style={{ textTransform: "capitalize" }} label="Lịch sử" {...a11yProps(1)} />
          </Tabs>
        </Box>
        {/* Print out subscription list */}
        {
          subscriptionList && subscriptionList.length > 0
            ? subscriptionList.map((item, index) =>
              <Subscription value={value} index={0}>
                <div className="wrapper">
                  {item.recommended
                    ?
                    <div className="table premium">
                      <div className="ribbon"><span>Khuyến nghị</span></div>
                      <div className="price-section">
                        <div className="price-area">
                          <div className="inner-area">
                            <span className="text">VNĐ</span>
                            <span className="price">{item.cost.toLocaleString("de-DE")}</span>
                          </div>
                        </div>
                      </div>
                      <div className="package-name">Gói {item.subscriptionId}</div>
                      <ul className="features">
                        <li>
                          <span className="list-name">Thời gian hiệu lực {item.duration} ngày.</span>
                          <span className="icon check"><i className="fas fa-check"></i></span>
                        </li>
                        <li>
                          <span className="list-name">Tối đa sử dụng cho {item.machineQuota} máy.</span>
                          <span className="icon check"><i className="fas fa-check"></i></span>
                        </li>
                      </ul>
                      <div className="btn" onClick={() => routeChange(item.subscriptionId)}><button>Mua</button></div>
                    </div>
                    : <div className="table basic">
                      <div className="price-section">
                        <div className="price-area">
                          <div className="inner-area">
                          <span className="text">VNĐ</span>
                            <span className="price">{item.cost.toLocaleString("de-DE")}</span>
                          </div>
                        </div>
                      </div>
                      <div className="package-name">Gói {item.subscriptionId}</div>
                      <ul className="features">
                        <li>
                          <span className="list-name">Thời gian hiệu lực {item.duration} ngày.</span>
                          <span className="icon check"><i className="fas fa-check"></i></span>
                        </li>
                        <li>
                          <span className="list-name">Tối đa sử dụng cho {item.machineQuota} máy.</span>
                          <span className="icon check"><i className="fas fa-check"></i></span>
                        </li>
                      </ul>
                      <div className="btn" onClick={() => routeChange(item.subscriptionId)}><button>Mua</button></div>
                    </div>
                  }
                </div>
              </Subscription>
            )
            : 'Không tìm thấy gói đăng ký khả dụng'
        }
      <Subscription value={value} index={1}>
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
                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-2">Tên gói</th>
                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-3">Ngày đăng ký</th>
                    <th className="u-border-1 u-border-palette-4-base u-palette-2-base u-table-cell u-table-cell-5">Giá gói</th>
                  </tr>
                </thead>
                <tbody className="u-table-body">
                  <tr style={{ height: "76px" }}>
                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-5">1</td>
                    <td className="u-border-1 u-border-grey-30 u-table-cell">Gói 1 tháng</td>
                    <td className="u-border-1 u-border-grey-30 u-table-cell">01/12/2022</td>
                    <td className="u-border-1 u-border-grey-30 u-table-cell ">100.000 VNĐ</td>
                  </tr>
                  <tr style={{ height: "76px" }}>
                    <td className="u-border-1 u-border-grey-30 u-first-column u-grey-5 u-table-cell u-table-cell-9">2</td>
                    <td className="u-border-1 u-border-grey-30 u-table-cell">Gói 1 tháng</td>
                    <td className="u-border-1 u-border-grey-30 u-table-cell">01/01/2023</td>
                    <td className="u-border-1 u-border-grey-30 u-table-cell ">100.000 VNĐ</td>
                  </tr>
                </tbody>
              </table>
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

          </div>
        </section>
      </Subscription>
    </Box>
    </WithPermission >

  );
}
