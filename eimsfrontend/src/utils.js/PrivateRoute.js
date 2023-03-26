import React from 'react'
import { Outlet, Navigate } from 'react-router-dom'
import Layout from '../components/Layout';


const useAuth = () => {
    const user = sessionStorage.getItem("curUserId");
    const userRole = sessionStorage.getItem("roleId")
    if (user) {
        return {
            auth: true,
            role1: userRole,
            role2: userRole
        }
    } else {
        return {
            auth: false,
            role1: null,
            role2: null
        }
    }
}
/**
1	ROLE_USER
2	ROLE_OWNER
3	ROLE_EMPLOYEE
4	ROLE_MODERATOR
5	ROLE_ADMIN */
type ProtectedRouteType = {
    roleRequired?: 1 | 2 | 3 | 4 | 5 
}

const PrivateRoute = (props: ProtectedRouteType) => {
    // let auth = {'token':false};
    const { auth, role1, role2 } = useAuth();

    if (props.roleRequired) {
        return auth ? (props.roleRequired === role1 || role2 ? (
            <Layout><Outlet /></Layout>
        ) : (
            <Navigate to="/unauthorized" />
        )
        ) : (
            <Navigate to="/login" />
        )
    } else {
        return auth ? <Layout> <Outlet /></Layout> : <Navigate to="/login" />
    }



}

export default PrivateRoute