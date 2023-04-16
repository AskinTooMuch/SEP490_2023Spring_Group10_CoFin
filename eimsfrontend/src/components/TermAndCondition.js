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
                            <option disabled>Chọn ngôn ngữ</option>
                            <option defaultValue value="vietnam">Tiếng Việt</option>
                            <option value="english" >English</option>
                        </select>
                    </div>
                    {selectValue === "vietnam" ?
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
                    :
                        <div>
                            <h5>Terms and Conditions</h5>
                            <p>Last edited: 16/04/2023</p>
                            <p>Greeting Users,</p>
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
                </div>
                <h4></h4>
            </div>
        </div>
    )

}
export default TermAndCondition;