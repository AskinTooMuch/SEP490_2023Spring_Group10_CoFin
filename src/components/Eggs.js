import React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Species from './Species';
function Egg(props) {
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

Egg.propTypes = {
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
          <Tab style={{ textTransform: "capitalize" }} label="Loài" {...a11yProps(0)} />
          <Tab style={{ textTransform: "capitalize" }} label="Loại" {...a11yProps(1)} />
          <Tab style={{ textTransform: "capitalize" }} label="Lô trứng" {...a11yProps(2)} />
        </Tabs>
      </Box>
      <Egg value={value} index={0}>
        <Species/>
      </Egg>
      <Egg value={value} index={1}>
        Item Two
      </Egg>
      <Egg value={value} index={2}>
        z
      </Egg>
     
    </Box>
  );
}