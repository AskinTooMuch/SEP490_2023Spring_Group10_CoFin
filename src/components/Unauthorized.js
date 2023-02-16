import { useNavigate } from "react-router-dom"
import block from "../pics/block.jpg"
const Unauthorized = () => {
    const navigate = useNavigate();

    const goBack = () => navigate("/login");

    return (
        <section>
            <div style={{textAlign:"center"}}>
                <img src={block} alt="block" height={200} width={200} />
            </div>
            <br />
            <p>You do not have access to the requested page.
                Please login.
            </p>
            <div className="flexGrow">
                <button className="btn btn-success" onClick={goBack}>Login</button>
            </div>
        </section>
    )
}

export default Unauthorized