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

    const [subscription, setSubscription] = useState("");
    const [discount, setDiscount] = useState("");
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
            const result = await axios.get(SUBSCRIPTION_GET_ID,
                {
                    params: { subscriptionId: id },
                    withCredentials: true
                });
            setSubscription(result.data);
            console.log(subscription);
            const resultDiscount = await axios.get(DISCOUNT_GET,
                {
                    params: { facilityId: sessionStorage.getItem("facilityId") },
                    withCredentials: true
                });
            setDiscount(resultDiscount.data);
            if (result.data.cost - resultDiscount.data < 0) {setFinal(0);} else {setFinal(result.data.cost - resultDiscount.data); }
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
        <div className='row'>
            <div className='col-md-3' />
            <div className='col-md-6'>
                {   subscription!=""
                ?<div>
                    <h2>Thanh toán</h2>
                <p>Gói {subscription.subscriptionId}</p>
                <p> - Giới hạn {subscription.machineQuota} máy sử dụng cùng lúc.</p>
                <p> - Sử dụng trong vòng {subscription.duration} ngày kể từ lúc thanh toán thành công.</p> 
                <p>Giá gói   : {subscription.cost.toLocaleString('vi', {style : 'currency', currency : 'VND'})}</p>
                <p>Giảm giá  : {discount.toLocaleString('vi', {style : 'currency', currency : 'VND'})}</p>
                <p>Thành tiền: {final.toLocaleString('vi', {style : 'currency', currency : 'VND'})}</p>
                </div>
                    
                :<></>

                }
                
                <Elements stripe={stripeTestPromise}>
                    <PaymentForm data={{id : id, final: final}}/>
                </Elements>
            </div>
            <div className='col-md-3'></div>
            
        </div>
    )
}