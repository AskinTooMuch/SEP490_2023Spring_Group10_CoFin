import React, { useState, useRef, useEffect } from 'react';
import { Elements } from '@stripe/react-stripe-js';
import { loadStripe} from '@stripe/stripe-js';
import { CardElement, useElements, useStripe } from '@stripe/react-stripe-js';
import axios from 'axios';
import PaymentForm from './PaymentForm';


const PUBLIC_KEY = 'pk_test_51MsHItAecmTOEDnIXCk62F10thHcVgo1OcclEEjndJsVYHa7iWzepovn5BhuaAVFlcNUrxfiaQBSeIrVw9leFSKG00rPwBVjLz';

const stripeTestPromise = loadStripe(PUBLIC_KEY);

export default function SubscriptionPayment() {
    
    return (
        <Elements stripe = {stripeTestPromise}>
            <PaymentForm/>
        </Elements>
    )
}