import React, { useState, useRef, useEffect } from 'react';
import { Elements } from '@stripe/react-stripe-js';
import { loadStripe } from '@stripe/stripe-js';
import { useNavigate, useLocation } from "react-router-dom";
import axios from 'axios';
import { toast } from 'react-toastify';
import PaymentForm from './PaymentForm';


const PUBLIC_KEY = 'pk_test_51MsHItAecmTOEDnIXCk62F10thHcVgo1OcclEEjndJsVYHa7iWzepovn5BhuaAVFlcNUrxfiaQBSeIrVw9leFSKG00rPwBVjLz';

const stripeTestPromise = loadStripe(PUBLIC_KEY);

export default function SubscriptionPayment() {
    //URL
    const SUBSCRIPTION_GET_ID = "/api/subscription/getById";
    const DISCOUNT_GET = "/api/subscription/getDiscount";

    const [subscriptionInformation, setSubscriptionInformation] = useState("");
    const [final, setFinal] = useState("");

    //Get sent params
    const { state } = useLocation();
    const { id } = state;

    //Get supplier details
    useEffect(() => {
        loadSubscriptionById(id);
    }, []);

    const loadSubscriptionById = async (id) => {
        try {
            const result = await axios.get(DISCOUNT_GET,
                {
                    params:
                    {
                        subscriptionId: id,
                        facilityId: sessionStorage.getItem("facilityId")
                    },
                    withCredentials: true
                });
            setSubscriptionInformation(result.data);
            console.log(subscriptionInformation);
            if (result.data.cost - result.data.discount < 0) { setFinal(0); } else { setFinal(result.data.cost - result.data.discount); }
            console.log(final);
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

    return (
        <div className='row' style={{marginTop:"50px"}}>
            <div className='col-md-3' />
            <div className='col-md-6'>
                <div className="container">
                    <div className="pricingTable orange">
                        <h3 className="title">Gói {subscriptionInformation.subscriptionId}</h3>
                        <div className="price-value">
                            <span className="amount">500.000</span>
                            <span className="currency">VNĐ</span>
                            <span className="month">/tháng</span>
                        </div>
                        {subscriptionInformation !== ""
                            ?

                            <ul className="pricing-content">
                                <li> <b> - Giới hạn {subscriptionInformation.machineQuota} máy sử dụng cùng lúc.</b>
                                    {subscriptionInformation.machineRunning >> subscriptionInformation.machineQuota
                                        ? <b className='text-warning'>Hiện tại cơ sở đang có {subscriptionInformation.machineRunning} máy đang hoạt động.
                                            Vui lòng chuyển lô trứng về {subscriptionInformation.machineQuota} máy hoặc ít hơn
                                            trước khi chuyển sang gói này.</b>
                                        : <></>
                                    }</li>
                                <li>  <b> - Sử dụng trong vòng {subscriptionInformation.duration} ngày kể từ lúc thanh toán thành công.</b></li>
                                <li><b>Giá gói   : {subscriptionInformation.cost.toLocaleString('vi', { style: 'currency', currency: 'VND' })}</b></li>
                                <li> <b>Giảm giá  : {subscriptionInformation.discount.toLocaleString('vi', { style: 'currency', currency: 'VND' })}</b></li>
                                <li><b>Thành tiền: {final.toLocaleString('vi', { style: 'currency', currency: 'VND' })}</b></li>
                            </ul>


                            : <></>

                        }
                        <h4 style={{color:"#ffb58d"}}>Thanh toán</h4>
                        <Elements stripe={stripeTestPromise}>
                            <PaymentForm data={{ id: id, final: final }} />
                        </Elements>
                    </div>
                </div>
            </div>
            <div className='col-md-3'></div>

        </div>
    )
}