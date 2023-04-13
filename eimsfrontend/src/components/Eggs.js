import React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Species from './Species';
import Breed from './Breed';
import EggBatch from './EggBatch';
import WithPermission from '../utils.js/WithPermission';
import StockReport from './StockReport';
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
          <Typography component="span">{children}</Typography>
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
    <div className='container'>
    <Box sx={{ width: '100%' }}>
      <WithPermission roleRequired="2">
        <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
          <Tabs sx={{
            '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
            '& .Mui-selected': { color: "#d25d19" },
          }} value={value} onChange={handleChange} aria-label="basic tabs example">
            <Tab id="eggBatchTab" style={{ textTransform: "capitalize" }} label="Lô trứng" {...a11yProps(0)} />
            <Tab id="eggStockTab" style={{ textTransform: "capitalize" }} label="Sản phẩm" {...a11yProps(1)} />
            <Tab id="speciesTab" style={{ textTransform: "capitalize" }} label="Loài" {...a11yProps(2)} />
            <Tab id="breedTab" style={{ textTransform: "capitalize" }} label="Loại" {...a11yProps(3)} />
          </Tabs>
        </Box>
        <Egg value={value} index={0}>
          <EggBatch />
        </Egg>
        <Egg value={value} index={1}>
          <StockReport/>
        </Egg>
        <Egg value={value} index={2}>
          <Species />
        </Egg>
        <Egg value={value} index={3}>
          <Breed />
        </Egg>
      </WithPermission>
      <WithPermission roleRequired="3">
        <Box sx={{ borderBottom: 1, borderColor: 'black' }}>
          <Tabs sx={{
            '& .MuiTabs-indicator': { backgroundColor: "#d25d19" },
            '& .Mui-selected': { color: "#d25d19" },
          }} value={value} onChange={handleChange} aria-label="basic tabs example">
            <Tab id="eggBatchTab" style={{ textTransform: "capitalize" }} label="Lô trứng" {...a11yProps(0)} />
          </Tabs>
        </Box>
        <Egg value={value} index={0}>
          <EggBatch />
        </Egg>
      </WithPermission>
    </Box>
    </div>
  );
}