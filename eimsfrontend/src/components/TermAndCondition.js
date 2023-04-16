import React, { useState } from 'react'
import '../css/term.css'
const TermAndCondition = () => {
    const [selectValue, setSelectValue] = React.useState("");
    const onChange = (event) => {
        const value = event.target.value;
        setSelectValue(value);
    };
    return (
        <div className='term-body'>
            <div className='terms-box'>
                <div className='terms-text'>
                    <div>
                        <select onChange={onChange}
                            id="" name="" className="form-select" aria-label="Default select example">
                            <option defaultValue disabled>Chọn ngôn ngữ</option>
                            <option value="vietnam">Tiếng Việt</option>
                            <option value="english" >English</option>
                        </select>
                    </div>
                    {
                        selectValue === "english" ?
                            <div>
                                <h5>Terms and Conditions</h5>
                                <p>Last edited: 16/04/2023</p>
                                <p>Welcome to our Egg Incubation Management System website ("EIMS").
                                    By using EIMS, you agree to these terms and conditions ("Terms").
                                    Please read them carefully before using EIMS. </p>
                                <p><b>1. Registration</b>
                                    <br />a. To use EIMS, you must register and create an account.
                                    You agree to provide accurate and complete information during the registration process.
                                    <br />b. You are responsible for maintaining the security of your account login information and for any activity that occurs under your account.
                                    You must immediately notify us of any unauthorized use of your account or any other breach of security. </p>
                                <p><b>2. Subscription and Payment</b>
                                    <br />a. EIMS offers a subscription service for the egg incubation management process. You can sign up for a subscription by selecting a plan and providing payment information through our Stripe payment gateway. By subscribing to our service, you agree to pay the fees associated with your chosen plan.
                                    <br />b. We reserve the right to change our subscription plans and fees at any time. We will provide notice of any changes on EIMS, SMS service or by email.
                                    <br />c. We offer a discount base on the remaining active time of your subscription plan. Any exploit through this discount policy results in unfair advantage, either to you or EIMS should be reported immediately to us for further investigations. </p>
                                <p><b>3. Image Upload</b>
                                    <br />a. EIMS allows you to upload images of your eggs for management purposes. You are solely responsible for the images you upload and for obtaining any necessary permissions or licenses to use those images.
                                    <br />b. You grant us a non-exclusive, worldwide, royalty-free, and perpetual license to use, store, copy, and display your uploaded images for the purpose of providing our service to you. </p>
                                <p><b>4. Disclaimer of Warranties and Limitation of Liability</b>
                                    <br />a. EIMS is provided "as is" and "as available" without any warranties of any kind, express or implied. We do not warrant that EIMS will be uninterrupted or error-free, and we do not guarantee the accuracy, completeness, or usefulness of any information provided through EIMS.
                                    <br />b. To the fullest extent permitted by law, we will not be liable for any direct, indirect, incidental, special, consequential, or punitive damages arising out of or in connection with the use or inability to use EIMS. </p>
                                <p><b>5. Changes to these Terms</b>
                                    <br />We reserve the right to modify these Terms at any time. The modified Terms will be effective immediately upon posting on our Site. Your continued use of EIMS after the posting of the modified Terms constitutes your agreement to abide and be bound by them. </p>
                                <p><b>6. Contact Us</b>
                                    <br />If you have any questions or concerns about these Terms or EIMS, please contact us at email address nguyenvanchuc@gmail.com or phone number (+84)969044714. </p>
                            </div>
                            :
                            <div>
                                <h5>Điều khoản và Điều kiện</h5>
                                <p>Chỉnh sửa lần cuối: 16/04/2023</p>
                                <p>Gửi tới người dùng,</p>
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nostrum magni blanditiis, distinctio,
                                    pariatur incidunt, officiis laudantium voluptates doloribus voluptatem aliquid fugiat provident
                                    quod quidem ea consequuntur ex odit repellat. Laborum itaque, doloremque nihil, deleniti fuga distinctio
                                    cum perferendis culpa molestiae quis unde ipsam consectetur iste. Illo asperiores tempora debitis?</p>
                                <p>Reprehenderit quo quisquam possimus, vero voluptate non vitae omnis ab reiciendis, dolorum alias
                                    sapiente consequuntur autem soluta ea animi nesciunt necessitatibus sint molestias laboriosam accusamus?
                                    Magnam harum ex exercitationem quo ratione in similique doloremque corporis itaque voluptatibus, eaque sit
                                    quidem officiis incidunt expedita sapiente. Modi velit qui ad in doloremque.</p>
                                <p>Est, saepe error perferendis beatae rem explicabo laboriosam porro fugiat alias temporibus,
                                    ducimus voluptates quis aut enim. Delectus sed, vel numquam quam excepturi magni explicabo
                                    veritatis amet corporis maxime expedita aut error repellat minima eius vitae cum labore aspernatur
                                    accusamus blanditiis quasi incidunt saepe sunt veniam! Voluptatem, veniam dolorem? Error?</p>
                                <p>Aliquam veniam vel tempore! Expedita sequi eum corrupti quia doloremque aperiam provident,
                                    placeat et ullam, ab perferendis quaerat illo nihil a, nisi iste omnis cupiditate. Numquam
                                    voluptatem quam saepe tempore, nemo asperiores molestiae ad. Veniam adipisci ad necessitatibus
                                    voluptatibus aperiam autem cupiditate, aliquam officia hic alias similique ipsam! Odio, corporis.</p>
                                <p>Architecto cum illum maxime non tempore consequatur ad? Laboriosam culpa quae ipsam provident
                                    perferendis fuga ex expedita omnis nihil obcaecati ut soluta in odio natus quia, rerum nesciunt
                                    saepe quasi eos qui blanditiis aliquid id eum amet? Itaque eos praesentium fugit ipsum dolor distinctio
                                    delectus illum, aperiam atque sint ratione.</p>
                                <p>Dolores, sint dicta. Veritatis, est quibusdam. Eos ipsum voluptate tempora optio laboriosam ad
                                    aspernatur dicta cum commodi, quam ab quasi quos praesentium sunt nam autem aliquid, et, ratione
                                    quaerat earum? Enim reiciendis minima in praesentium asperiores atque, accusamus quia possimus excepturi
                                    dolorum porro? Ducimus voluptate nesciunt eum officia a cumque.</p>
                            </div>
                    }
                </div >
            </div >
        </div >
    )

}
export default TermAndCondition;