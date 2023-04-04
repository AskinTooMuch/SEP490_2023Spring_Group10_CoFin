import React, { useState, useRef, useEffect } from 'react';
import { Elements } from '@stripe/react-stripe-js';
import { loadStripe} from '@stripe/stripe-js';
import { useNavigate, useLocation } from "react-router-dom";
import axios from 'axios';
import PaymentForm from './PaymentForm';


const PUBLIC_KEY = 'pk_test_51MsHItAecmTOEDnIXCk62F10thHcVgo1OcclEEjndJsVYHa7iWzepovn5BhuaAVFlcNUrxfiaQBSeIrVw9leFSKG00rPwBVjLz';

const stripeTestPromise = loadStripe(PUBLIC_KEY);

export default function SubscriptionPayment() {
    //Get sent params
    const { state } = useLocation();
    const { id } = state;
    
    return (
        <div className='row'>
            <div className='col-md-3'>
                <span>{id}</span>
            </div>
            <div className='col-md-6'>
                <Elements stripe = {stripeTestPromise}>
            <PaymentForm/>
            </Elements>
            </div>
            <div className='col-md-3'></div>
        
        </div>
        
    )
}