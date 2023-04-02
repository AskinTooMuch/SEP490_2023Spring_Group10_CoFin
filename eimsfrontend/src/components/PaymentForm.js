import React, { useState, useRef, useEffect } from 'react';
import { CardElement, useElements, useStripe } from '@stripe/react-stripe-js';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';

const CARD_OPTIONS = {
    iconStyle: 'solid',
    style: {
        base: {
            iconColor: '#C4F0FF',
            color: '#000000',
            fontWeight: 500,
            fontFamily: 'Roboto, Open Sands, Segoe UI, sans-serif',
            fontSize: '16px',
            fontSmoothing: 'antialiased',
            ':-webkit-autofill': { color: "#fce883" },
            '::placeholder': { color: '#87BBFD' }
        },
        invalid: {
            iconColor: '#FFC7EE',
            color: '#FFC7EE'
        }
    }
}

export default function PaymentForm() {
    const CREATE_PAYMENT = '/api/subscribe/charge';
    const [success, setSuccess] = useState(false);
    const stripe = useStripe();
    const elements = useElements();

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log("Get card things");
        const { error, paymentMethod } = await stripe.createPaymentMethod({
            type: 'card',
            card: elements.getElement(CardElement)
        });

        if (!error) {
            console.log("Not error, sending to backend");
            try {
                const { id } = paymentMethod;
                const response = await axios.post(CREATE_PAYMENT,
                    {
                        amount: 500000,
                        currency: 'vnd',
                        method: id
                    },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Access-Control-Allow-Origin': '*'
                        },
                        withCredentials: true
                    });

                console.log("Successful payment");
                setSuccess(true);
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
        } else {
            console.log(error.message);
        }
    }
    return (
        <>
            {!success
                ? <form onSubmit={handleSubmit}>
                    <fieldset className='formGroup'>
                        <div className='formRow'>
                            <CardElement options={CARD_OPTIONS} />
                        </div>
                    </fieldset>
                    <button>Pay</button>
                </form>
                : <div>
                    <h2>Success</h2>
                </div>
            }
            <ToastContainer position="top-left"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="colored" />
        </>
    )
}