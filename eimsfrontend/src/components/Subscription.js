import "../css/subscribe.css"
import React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
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
        <Subscription value={value} index={0}>
          <div className="wrapper">
            <div className="table basic">
              <div className="price-section">
                <div className="price-area">
                  <div className="inner-area">
                    <span className="text">VNĐ</span>
                    <span className="price">200.000</span>
                  </div>
                </div>
              </div>
              <div className="package-name"></div>
              <ul className="features">
                <li>
                  <span className="list-name">One Selected Template</span>
                  <span className="icon check"><i className="fas fa-check"></i></span>
                </li>
                <li>
                  <span className="list-name">100% Responsive Design</span>
                  <span className="icon check"><i className="fas fa-check"></i></span>
                </li>
                <li>
                  <span className="list-name">Credit Remove Permission</span>
                  <span className="icon cross"><i className="fas fa-times"></i></span>
                </li>
                <li>
                  <span className="list-name">Lifetime Template Updates</span>
                  <span className="icon cross"><i className="fas fa-times"></i></span>
                </li>
              </ul>
              <div className="btn"><button>Mua</button></div>
            </div>
            <div className="table premium">
              <div className="ribbon"><span>Khuyến nghị</span></div>
              <div className="price-section">
                <div className="price-area">
                  <div className="inner-area">
                    <span className="text">VNĐ</span>
                    <span className="price">500.000</span>
                  </div>
                </div>
              </div>
              <div className="package-name"></div>
              <ul className="features">
                <li>
                  <span className="list-name">Five Existing Templates</span>
                  <span className="icon check"><i className="fas fa-check"></i></span>
                </li>
                <li>
                  <span className="list-name">100% Responsive Design</span>
                  <span className="icon check"><i className="fas fa-check"></i></span>
                </li>
                <li>
                  <span className="list-name">Credit Remove Permission</span>
                  <span className="icon check"><i className="fas fa-check"></i></span>
                </li>
                <li>
                  <span className="list-name">Lifetime Template Updates</span>
                  <span className="icon cross"><i className="fas fa-times"></i></span>
                </li>
              </ul>
              <div className="btn"><button>Mua</button></div>
            </div>
            <div className="table ultimate">
              <div className="price-section">
                <div className="price-area">
                  <div className="inner-area">
                    <span className="text">VNĐ</span>
                    <span className="price">900.000</span>
                  </div>
                </div>
              </div>
              <div className="package-name"></div>
              <ul className="features">
                <li>
                  <span className="list-name">All Existing Templates</span>
                  <span className="icon check"><i className="fas fa-check"></i></span>
                </li>
                <li>
                  <span className="list-name">100% Responsive Design</span>
                  <span className="icon check"><i className="fas fa-check"></i></span>
                </li>
                <li>
                  <span className="list-name">Credit Remove Permission</span>
                  <span className="icon check"><i className="fas fa-check"></i></span>
                </li>
                <li>
                  <span className="list-name">Lifetime Template Updates</span>
                  <span className="icon check"><i className="fas fa-check"></i></span>
                </li>
              </ul>
              <div className="btn"><button>Mua</button></div>
            </div>
          </div>
        </Subscription>
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
            </div>
          </section>
        </Subscription>
      </Box>
    </WithPermission>
  );
}
