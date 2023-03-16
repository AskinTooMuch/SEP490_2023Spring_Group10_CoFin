import React from 'react'

type Props = {
    roleRequired: 1 | 2 | 3 | 4 | 5,
    message?: string,
    children?: React.ReactNode
}

const useRole = () => {
    const user = sessionStorage.getItem("curUserId");
    const userRole = sessionStorage.getItem("roleId")
    if (user) {
        return userRole
    } else {
        return 1
    }
}
const WithPermission = (props: Props) => {
    const { roleRequired, message, children } = props
    const role = useRole()
    return (
        <>
            {roleRequired === role ? children : <h3>{message ? message : ''}</h3>}
        </>
    )
}

export default WithPermission