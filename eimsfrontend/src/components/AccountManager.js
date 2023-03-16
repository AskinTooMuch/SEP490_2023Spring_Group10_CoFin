import React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Registrators from './Registrators';
function AccountManager(props) {
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

AccountManager.propTypes = {
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
        }} value={value} onChange={handleChange} aria-label="basic tabs example">
          <Tab id="accountListTab" style={{ textTransform: "capitalize" }} label="Danh sách tài khoản" {...a11yProps(0)} />
          <Tab id="accountTab" style={{ textTransform: "capitalize" }} label="Danh sách chờ duyệt" {...a11yProps(1)} />
        </Tabs>
      </Box>
      <AccountManager value={value} index={0}>
      </AccountManager>
      <AccountManager value={value} index={1}>
        <Registrators />
      </AccountManager>
    </Box>
  );
}