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
                                <p>Last edited: 20/04/2023</p>
                                <p>Welcome to our Egg Incubation Management System website ("EIMS").
                                    By using EIMS, you agree to these terms and conditions ("Terms").
                                    Please read them carefully before using EIMS.
                                </p>
                                <p>If you have any disagreement to the terms and conditions, please stop using the system immediately.</p>
                                <p>We reserve the right to modify these Terms at any time.
                                    The modified Terms will be effective immediately upon posting on EIMS.
                                    Your continued use of our Site after the posting of the modified Terms constitutes your agreement to abide and be bound by them.
                                    If you have any disagreement to the new terms and conditions, please stop using the system immediately.</p>
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
                                <p><b>5. Contact Us</b>
                                    <br />If you have any questions or concerns about these Terms or EIMS, please contact us at email address nguyenvanchuc@gmail.com or phone number (+84)969044714. </p>
                            </div>
                            :
                            <div>
                                <h5>Điều khoản và Điều kiện sử dụng</h5>
                                <p>Chỉnh sửa lần cuối: 20/04/2023</p>
                                <p>Chào mừng đến với trang web hệ thống quản lý ấp nở trứng (gọi tắt là EIMS).
                                    Bằng cách sử dụng EIMS, bạn đồng ý với những điều khoản và điều kiện sử dụng (gọi tắt là điều khoản).
                                    Xin vui lòng đọc kỹ điều khoản sử dụng trước khi bắt đầu sử dụng hệ thống EIMS.</p>
                                <p>Nếu bạn có bất kỳ phản đối nào với bất kỳ mục nào của điều khoản sử dụng, xin hãy ngừng sử dụng hệ thống ngay lập tức.</p>
                                <p>Chúng  tôi (EIMS) có quyền thay đổi và bổ sung những điều khoản này tại bất kỳ thời điểm nào.
                                    Những điều khoản mới sẽ có hiệu lực ngay khi được cập nhật trên EIMS.
                                    Khi tiếp tục sử dụng EIMS sau khi những điều khoản mới được cập nhật, bạn mặc định đồng ý với những điều khoản mới này.
                                    Nếu như có bất kỳ phản đối nào với điều khoản mới, xin hãy ngừng sử dụng hệ thống EIMS ngay lập tức.</p>
                                <p><b>1. Đăng ký</b>
                                    <br />a. Để sử dụng EIMS, bạn cần đăng ký và tạo tài khoản. Bạn đồng ý cung cấp thông tin đầy đủ và chính xác trong toàn bộ quá trình đăng ký.
                                    <br />b. Bạn có trách nhiệm cho việc giữ bảo mật cho thông tin đăng nhập và cho toàn bộ các hoạt động của tài khoản này. Bạn phải thông báo lại ngay lập tức cho EIMS về bất cứ những  hoạt động trái phép nào hoặc bất kỳ các vi phạm bảo mật nào khác.</p>
                                <p><b>2. Đăng ký dịch vụ và thanh toán</b>
                                    <br />a. EIMS cung cấp dịch vụ đăng ký cho quy trình quản lý ấp trứng. Bạn có thể đăng ký gói sử dụng bằng cách chọn gói và cung cấp thông tin thanh toán qua cổng thanh toán Stripe của chúng tôi. Bằng cách đăng ký dịch vụ của chúng tôi, bạn đồng ý thanh toán các khoản phí liên quan đến gói bạn đã chọn.
                                    <br />b. EIMS có quyền thay đổi thông tin gói đăng ký và phí liên quan tại bất kỳ thời điểm nào. Chúng tôi sẽ thông báo về những thay đổi này qua EIMS, dịch vụ SMS hoặc thư điện tử.
                                    <br />c. Chúng tôi cung cấp giảm giá dựa trên thời gian hoạt động còn lại của gói đăng ký của bạn. Bất kỳ hành vi khai thác nào thông qua chính sách chiết khấu này có dẫn đến lợi thế không công bằng, kể cả cho bạn hoặc EIMS phải được báo cáo ngay lập tức cho chúng tôi để điều tra thêm.</p>
                                <p><b>3. Đăng tải hình ảnh</b>
                                    <br />a. EIMS cho phép bạn tải lên hình ảnh trứng và liên quan tới trứng cho mục đích quản lý. Bạn hoàn toàn chịu trách nhiệm về những hình ảnh bạn đăng tải lên và quyền hoặc giấy phép cần thiết để sử dụng những hình ảnh đó.
                                    <br />b. Bạn cấp cho chúng tôi giấy phép không độc quyền, toàn cầu, miễn phí bản quyền và vĩnh viễn để sử dụng, lưu trữ, sao chép và hiển thị các hình ảnh đã tải lên của bạn nhằm mục đích cung cấp dịch vụ của EIMS.</p>
                                <p><b>4. Đảm bảo bảo hành, các trách nhiệm và giới hạn pháp lý</b>
                                    <br />a. EIMS được cung cấp "nguyên trạng" và "sẵn có" mà không có bất kỳ bảo đảm nào dưới bất kỳ hình thức nào, rõ ràng hay ngụ ý. Chúng tôi không đảm bảo rằng EIMS sẽ không bị gián đoạn hoặc không có lỗi và chúng tôi không đảm bảo tính chính xác, đầy đủ hoặc hữu ích của bất kỳ thông tin nào được cung cấp qua EIMS.
                                    <br />b. Trong phạm vi tối đa được pháp luật cho phép, chúng tôi sẽ không chịu trách nhiệm pháp lý đối với bất kỳ thiệt hại trực tiếp, gián tiếp, ngẫu nhiên, đặc biệt, do hậu quả hoặc trừng phạt nào phát sinh từ hoặc liên quan đến việc sử dụng hoặc không thể sử dụng EIMS.</p>
                                <p><b>5. Liên hệ chúng tôi</b>
                                    <br />Nếu bạn có bất cứ câu hỏi hay thắc mắc nào về những điểu khoản này của EIMS, xin hãy liên hệ chúng tôi qua địa chỉ hòm thư điện tử nguyenvanchuc@gmail.com hoặc số điện thoại (+84)969044714.</p>
                            </div>
                    }
                </div >
            </div >
        </div >
    )

}
export default TermAndCondition;