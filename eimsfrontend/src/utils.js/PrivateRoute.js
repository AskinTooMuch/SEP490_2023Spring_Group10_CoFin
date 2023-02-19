import React from 'react'
import { Outlet, Navigate} from 'react-router-dom'
import Layout from '../components/Layout';
const PrivateRoute = ({children, ...rest}) =>{
    // let auth = {'token':false};
    const auth2 = localStorage.getItem("user");
    return(
       
        auth2 ? <Layout> <Outlet/></Layout> : <Navigate to="/unauthorized"/> 
        
    )
}

export default PrivateRoute