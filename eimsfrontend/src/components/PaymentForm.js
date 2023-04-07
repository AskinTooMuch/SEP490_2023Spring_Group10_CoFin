import React, { useState, useRef, useEffect } from 'react';
import { CardElement, useElements, useStripe } from '@stripe/react-stripe-js';
import axios from 'axios';
import { toast } from 'react-toastify';
import "../css/subscribe.css"
const CARD_OPTIONS = {
    iconStyle: 'solid',
    style: {
        base: {
            iconColor: '#ffb58d',
            color: '#f46110',
            lineHeight: '40px',
            fontWeight: 300,
            fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
            fontSize: '20px',
            '::placeholder': {
                color: '#ffb58d',
            }
        }
    }
}

export default function PaymentForm(props) {
    const CREATE_PAYMENT = '/api/subscribe/charge';
    const [success, setSuccess] = useState(false);
    const stripe = useStripe();
    const elements = useElements();

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log("Get card things");
        if (props.data.final !== 0) {
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
                            subscriptionId: props.data.id,
                            facilityId: sessionStorage.getItem("facilityId"),
                            amount: props.data.final,
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
                console.log("Axios" + error.message);
            }

        } else {
            try {
                const { id } = "paymentMethod";
                const response = await axios.post(CREATE_PAYMENT,
                    {
                        subscriptionId: props.data.id,
                        facilityId: sessionStorage.getItem("facilityId"),
                        amount: props.data.final,
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
        }



    }

    return (
        <>
            {!success
                ? <form onSubmit={handleSubmit}>
                    {props.data.final === 0
                        ? <></>
                        : <fieldset className='formGroup' style={{ borderRadius: "15px" }}>
                            <div className='formRow'>
                                <CardElement style={{

                                }} options={CARD_OPTIONS} />
                            </div>
                        </fieldset>

                    }
                    <br />
                    <div style={{ textAlign: "center" }}>
                        <button style={{ display: "inline-block", width: "50%" }} className='btn btn-light'>Thanh toán</button>
                    </div>
                </form>
                : <div>
                    <b className='text-success'>Thanh toán thành công {props.data.final.toLocaleString('vi', { style: 'currency', currency: 'VND' })} cho gói {props.data.id}. </b>
                </div>
            }

        </>
    )
}