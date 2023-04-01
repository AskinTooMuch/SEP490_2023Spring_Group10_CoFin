import React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Supplier from './Supplier'
import Customer from './Customer'
import ImportBill from './ImportBill';
import ExportBill from './ExportBill';
function Order(props) {
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

Order.propTypes = {
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
        <Tabs sx={{
            '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
            '& .Mui-selected': { color: "#d25d19" },
          }}value={value} onChange={handleChange} aria-label="basic tabs example">
          <Tab id = "importBillTab" style={{ textTransform: "capitalize" }} label="Hoá đơn nhập" {...a11yProps(0)} />
          <Tab id = "exportBillTab" style={{ textTransform: "capitalize" }} label="Hoá đơn xuất" {...a11yProps(1)} />
          <Tab id = "supplierTab" style={{ textTransform: "capitalize" }} label="Nhà cung cấp" {...a11yProps(2)} />
          <Tab id = "customerTab" style={{ textTransform: "capitalize" }} label="Khách hàng" {...a11yProps(3)} />
        </Tabs>
      </Box>
      <Order value={value} index={0}>
        <ImportBill/>
      </Order>
      <Order value={value} index={1}>
        <ExportBill/>
      </Order>
      <Order value={value} index={2}>
        <Supplier/>
      </Order>
      <Order value={value} index={3}>
        <Customer/>
      </Order>
    </Box>
  );
}