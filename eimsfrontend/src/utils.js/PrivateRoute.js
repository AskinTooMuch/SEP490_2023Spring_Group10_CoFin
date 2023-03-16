import React from 'react'
import { Outlet, Navigate } from 'react-router-dom'
import Layout from '../components/Layout';

{/**
1	ROLE_USER
2	ROLE_OWNER
3	ROLE_EMPLOYEE
4	ROLE_MODERATOR
5	ROLE_ADMIN */}
const useAuth = () => {
    const user = sessionStorage.getItem("curUserId");
    const userRole = sessionStorage.getItem("roleId")
    if (user) {
        return {
            auth: true,
            role: userRole,
        }
    } else {
        return {
            auth: false,
            role: null
        }
    }
}

type ProtectedRouteType = {
    roleRequired?: 1 | 2 | 3 | 4 | 5
}

const PrivateRoute = (props: ProtectedRouteType) => {
    // let auth = {'token':false};
    const { auth, role } = useAuth();

    if (props.roleRequired) {
        return auth ? (props.roleRequired === role ? (
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