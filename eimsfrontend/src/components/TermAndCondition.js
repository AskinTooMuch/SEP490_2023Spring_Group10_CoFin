import React, { useState } from 'react'
import '../css/term.css'
import { Modal } from 'react-bootstrap';
const TermAndCondition = () => {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    return (
        <>
            <section className="u-clearfix u-white u-section-1" id="sec-3c81" data-animation-name="" data-animation-duration="0" data-animation-delay="0" data-animation-direction="">
                <div className="u-custom-color-2 u-expanded-width u-shape u-shape-rectangle u-shape-1"></div>
                <div style={{ minHeight: "0px" }} className="u-align-center u-border-20 u-border-custom-color-1 u-border-no-left u-border-no-right u-border-no-top u-container-style u-custom-border u-group u-radius-46 u-shape-round u-white u-group-1">
                    <div className="u-container-layout u-valign-middle-xs u-container-layout-1">
                        <h2 className="u-text u-text-custom-color-1 u-text-default u-text-1">Điều khoản và Điều kiện</h2>
                        <br />
                        <button onClick={handleShow} className="btn btn-light" style={{ width: "30%" }}>Hiển thị</button>
                        <Modal show={show} onHide={handleClose}
                            size="lg"
                            aria-labelledby="contained-modal-title-vcenter"
                            centered >
                            <Modal.Body>
                                <div className='terms-box'>
                                    <div className='terms-text'>
                                        <h4 style={{ fontFamily: "Montserrat", fontWeight: "bold" }}>Điều khoản và Điều kiện</h4>
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
                                </div>
                            </Modal.Body>
                        </Modal>
                    </div>
                </div>
            </section>
        </>
    )

}
export default TermAndCondition;