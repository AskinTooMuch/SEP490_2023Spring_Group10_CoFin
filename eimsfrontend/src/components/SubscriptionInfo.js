import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import '../css/machine.css';
import { toast } from 'react-toastify';
import axios from 'axios';
import { useLocation } from "react-router-dom";
function SubcriptionInfo(props) {
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

SubcriptionInfo.propTypes = {
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
    const SUBSCRIPTION_GET = "/api/subscription/getById";
    //Get sent params
    const { state } = useLocation();
    const { id } = state;
    const [subscription, setSubscription] = useState();

    //Get subscription details
    useEffect(() => {
        console.log("load");
        loadSubscription();
    }, []);

    const loadSubscription = async () => {
        try {
            const result = await axios.get(SUBSCRIPTION_GET,
                {
                    params: { subscriptionId: id },
                    withCredentials: true
                });
            // Set inf
            setSubscription(result.data);
            console.log(result.data);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
                console.log(err);
            } else {
                if ((err.response.data === null) || (err.response.data === '')) {
                    toast.error('Có lỗi xảy ra, vui lòng thử lại');
                } else {
                    toast.error(err.response.data);
                }
            }
        }
    }

    return (
        <div className='row'>
            <div className='col-md-3'/>
            {subscription &&
                <div className='col-md-6 col-sm-6'>
                    <div className={`pricingTable ${subscription.recommended ? 'orange' : 'green'}`}>
                        <h3 className="title">GÓI {subscription.subscriptionId}</h3>
                        <div className="price-value ">
                            <p className="amount">{subscription.cost.toLocaleString("de-DE")}</p>
                            <p className="currency">VNĐ</p>
                        </div>
                        <ul className="pricing-content">
                            <li>Thời gian hiệu lực <b>{subscription.duration} ngày</b>.</li>
                            <li>Tối đa sử dụng cho <b>{subscription.machineQuota} máy</b>.</li>
                        </ul>
                    </div>
                </div>
            }
        </div>


    );
}