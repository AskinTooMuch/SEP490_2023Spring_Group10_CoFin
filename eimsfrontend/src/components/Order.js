import React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Supplier from './Supplier'
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
          <Typography>{children}</Typography>
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
        <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
          <Tab style={{ textTransform: "capitalize" }} label="Hoá đơn nhập" {...a11yProps(0)} />
          <Tab style={{ textTransform: "capitalize" }} label="Hoá đơn xuất" {...a11yProps(1)} />
          <Tab style={{ textTransform: "capitalize" }} label="Nhà cung cấp" {...a11yProps(2)} />
          <Tab style={{ textTransform: "capitalize" }} label="Khách hàng" {...a11yProps(3)} />
        </Tabs>
      </Box>
      <Order value={value} index={0}>
        Item One
      </Order>
      <Order value={value} index={1}>
        Item Two
      </Order>
      <Order value={value} index={2}>
        <Supplier/>
      </Order>
      <Order value={value} index={3}>
        Item four
      </Order>
    </Box>
  );
}