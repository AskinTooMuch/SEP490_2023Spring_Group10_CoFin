import React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Supplier from './Supplier'
import Cost from './Cost';
import Payroll from './Payroll';
import ImportReport from './ImportReport';
function FinanManage(props) {
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

FinanManage.propTypes = {
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
    <Box sx={{ width: '100%' }}>
      <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
        <Tabs  sx={{
            '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
            '& .Mui-selected': { color: "#d25d19" },
          }}
        value={value} onChange={handleChange} aria-label="basic tabs example">
          <Tab id="costTab" style={{ textTransform: "capitalize" }} label="Chi Phí" {...a11yProps(0)} />
          <Tab id="payrollTab" style={{ textTransform: "capitalize" }} label="Tiền lương" {...a11yProps(1)} />
          <Tab id="importReportTab" style={{ textTransform: "capitalize" }} label="Báo cáo nhập" {...a11yProps(2)} />
          <Tab id="exportReportTab" style={{ textTransform: "capitalize" }} label="Báo cáo xuất" {...a11yProps(3)} />
          <Tab id="incomeReportTab" style={{ textTransform: "capitalize" }} label="Doanh thu" {...a11yProps(4)} />
        </Tabs>
      </Box>
      <FinanManage value={value} index={0}>
       <Cost/>
      </FinanManage>
      <FinanManage value={value} index={1}>
        <Payroll/>
      </FinanManage>
      <FinanManage value={value} index={2}>
        <ImportReport/>
      </FinanManage>
      <FinanManage value={value} index={3}>
        Item four
      </FinanManage>
      <FinanManage value={value} index={3}>
        Item four
      </FinanManage>
    </Box>
  );
}