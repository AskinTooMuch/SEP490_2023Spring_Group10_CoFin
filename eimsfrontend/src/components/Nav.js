import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import CloseIcon from '@mui/icons-material/Close';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Badge from '@mui/material/Badge';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import AccountCircle from '@mui/icons-material/AccountCircle';
import NotificationsIcon from '@mui/icons-material/Notifications';
import MoreIcon from '@mui/icons-material/MoreVert';
import SettingsIcon from '@mui/icons-material/Settings';
import { Button } from '@mui/material';
import HelpIcon from '@mui/icons-material/Help';
import LogoutIcon from '@mui/icons-material/Logout';
import DashboardIcon from '@mui/icons-material/Dashboard';
import "../css/navbar.css"
import logo from '../pics/EIMSlogo.png'
import WithPermission from '../utils.js/WithPermission';
import FiberManualRecordIcon from '@mui/icons-material/FiberManualRecord';
import { Modal } from 'react-bootstrap';
import axios from '../api/axios';
import { ToastContainer, toast } from 'react-toastify';
const Nav = () => {
    // Auth
    const auth = sessionStorage.getItem("curUserId");
    const navigate = useNavigate();

    // API URL
    const NOTIFICATION_NEWEST = '/api/notification/top'

    // Navigate
    const dashboard = () => {
        navigate("/dashboard")
    }
    const account = () => {
        navigate("/accountmanage")
    }
    const egg = () => {
        navigate("/egg")
    }
    // Handle
    const [notify, setNotify] = React.useState(null);
    const open = Boolean(notify);
    const handleClickNoti = () => {
        loadNotificationList();
        setNotify(notificationList);
    };
    const handleCloseNoti = () => {
        setNotify(null);
    };

    const [anchorEl, setAnchorEl] = React.useState(null);
    const [mobileMoreAnchorEl, setMobileMoreAnchorEl] = React.useState(null);

    const isMenuOpen = Boolean(anchorEl);
    const isMobileMenuOpen = Boolean(mobileMoreAnchorEl);

    const handleProfileMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMobileMenuClose = () => {
        setMobileMoreAnchorEl(null);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
        handleMobileMenuClose();
    };
    const logout = () => {
        sessionStorage.clear();
        handleMenuClose();
        navigate("/")
    }
    const handleMobileMenuOpen = (event) => {
        setMobileMoreAnchorEl(event.currentTarget);
    };

    // Menu
    const menuId = 'primary-search-account-menu';
    const renderMenu = (
        <Menu
            anchorEl={anchorEl}
            anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
            }}
            id={menuId}
            keepMounted
            transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
            }}
            PaperProps={{
                elevation: 0,
                sx: {
                    overflow: 'visible',
                    filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',
                    mt: 5
                }
            }}
            open={isMenuOpen}
            onClose={handleMenuClose}
        >
            <Link to="/profile" ><MenuItem style={{ fontSize: "medium" }} onClick={handleMenuClose} id="profile"><AccountCircle fontSize="small" /> Thông tin cá nhân</MenuItem></Link>
            <MenuItem style={{ fontSize: "medium" }} onClick={handleMenuClose} id="setting"><SettingsIcon fontSize="small" /> Cài đặt</MenuItem>
            <MenuItem style={{ fontSize: "medium" }} onClick={handleMenuClose} id="help"><HelpIcon fontSize="small" /> Trợ giúp & hỗ trợ</MenuItem>
            <MenuItem style={{ fontSize: "medium" }} onClick={logout} id="logout"><LogoutIcon fontSize="small" /> Đăng xuất</MenuItem>
        </Menu>
    );

    const mobileMenuId = 'primary-search-account-menu-mobile';
    const renderMobileMenu = (
        <Menu
            anchorEl={mobileMoreAnchorEl}
            anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
            }}
            id={mobileMenuId}
            keepMounted
            transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
            }}
            open={isMobileMenuOpen}
            onClose={handleMobileMenuClose}
        >
            <WithPermission roleRequired="2">
                <MenuItem onClick={dashboard}>
                    <IconButton size="large" aria-label="show 4 new mails" color="inherit">
                        <DashboardIcon />
                    </IconButton>
                    <p>Dashboard</p>
                </MenuItem>
            </WithPermission>
            <WithPermission roleRequired="4">
                <MenuItem onClick={account}>
                    <IconButton size="large" aria-label="show 4 new mails" color="inherit">
                        <DashboardIcon />
                    </IconButton>
                    <p>Quản lý tài khoản</p>
                </MenuItem>
            </WithPermission>
            <MenuItem onClick={handleClickNoti}>
                <IconButton
                    size="large"
                    aria-controls={open ? 'notify-menu' : undefined}
                    aria-haspopup="true"
                    aria-expanded={open ? 'true' : undefined}
                    color="inherit"
                >
                    <Badge badgeContent={<FiberManualRecordIcon color="error" />} >
                        <NotificationsIcon />
                    </Badge>
                </IconButton>
                <p>Notifications</p>
            </MenuItem>
            <MenuItem onClick={handleProfileMenuOpen}>
                <IconButton
                    size="large"
                    aria-label="account of current user"
                    aria-controls="primary-search-account-menu"
                    aria-haspopup="true"
                    color="inherit"
                >
                    <AccountCircle />
                </IconButton>
                <p>Profile</p>
            </MenuItem>
        </Menu>
    );
    //Show-hide Popup
    const [show, setShow] = useState(false);
    const handleClosePopup = () => setShow(false);
    const handleShowPopup = () => setShow(true);

    // DTO
    // top 5 newest notification
    const [notificationList, setNotificationList] = useState([]);
    // Get top 5 
    const loadNotificationList = async () => {
        try {
            const result = await axios.get(NOTIFICATION_NEWEST,
            {
                params: { facilityId: sessionStorage.getItem("facilityId") },
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: true
            });
        setNotificationList(result.data);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                if (err.response.data === '' || err.response.data === null) {
                    toast.error('Lỗi không xác định');
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

    // View all notification
    const viewAllNoti = () => {
        handleCloseNoti();
        navigate("/notificationlist");
    }

    // Go to update egg batch
    const goUpdateEggBatch = (item) => {
        handleCloseNoti();
        navigate("/eggbatchdetail", { state: { id: item.eggBatchId } })
    }

    return (
        <div>
            <Box sx={{ flexGrow: 1 }}>
                <AppBar position="static">
                    <Toolbar>
                        <Typography
                            variant="h6"
                            noWrap
                            component="div"
                            sx={{ display: { xs: 'none', sm: 'block' } }}
                        >
                            <Link className='title' to="/"><img src={logo} alt="logo" height={70}></img>EIMS</Link>
                        </Typography>
                        {
                            auth ?

                                <>
                                    <Box sx={{ flexGrow: 1 }} />
                                    <Box sx={{ display: { xs: 'none', md: 'flex' } }}>
                                        <WithPermission roleRequired="2">
                                            <IconButton size="large" color="inherit" onClick={dashboard}>
                                                <Badge color="error">
                                                    <DashboardIcon />
                                                </Badge>
                                            </IconButton>
                                        </WithPermission>
                                        <WithPermission roleRequired="3">
                                            <IconButton size="large" color="inherit" onClick={egg}>
                                                <Badge color="error">
                                                    <DashboardIcon />
                                                </Badge>
                                            </IconButton>
                                        </WithPermission>
                                        <WithPermission roleRequired="4">
                                            <IconButton size="large" color="inherit" onClick={account}>
                                                <Badge color="error">
                                                    <DashboardIcon />
                                                </Badge>
                                            </IconButton>
                                        </WithPermission>
                                        <IconButton
                                            size="large"
                                            onClick={handleClickNoti}
                                            aria-controls={open ? 'notify-menu' : undefined}
                                            aria-haspopup="true"
                                            aria-expanded={open ? 'true' : undefined}
                                            color="inherit"
                                        >
                                            <Badge badgeContent={<FiberManualRecordIcon color="error" />}>
                                                <NotificationsIcon />
                                            </Badge>
                                        </IconButton>
                                        <IconButton
                                            size="large"
                                            edge="end"
                                            aria-label="account of current user"
                                            aria-controls={menuId}
                                            aria-haspopup="true"
                                            onClick={handleProfileMenuOpen}
                                            color="inherit"
                                        >
                                            <AccountCircle />
                                        </IconButton>
                                    </Box>
                                    <Box sx={{ display: { xs: 'flex', md: 'none' } }}>
                                        <IconButton
                                            size="large"
                                            aria-label="show more"
                                            aria-controls={mobileMenuId}
                                            aria-haspopup="true"
                                            onClick={handleMobileMenuOpen}
                                            color="inherit"
                                        >
                                            <MoreIcon />
                                        </IconButton>
                                    </Box>
                                </>
                                :
                                <Box sx={{ flexGrow: 1 }}>
                                    <Link style={{ textDecoration: "none", color: "white" }} to="/registerotp"><Button className="signupbutton" color="inherit">Đăng ký</Button></Link>
                                    <Link style={{ textDecoration: "none", color: "white" }} to="/login"><Button className="loginbutton" color="inherit">Đăng nhập</Button></Link>
                                </Box>
                        }

                    </Toolbar>
                </AppBar>
                {renderMobileMenu}
                {renderMenu}
            </Box>
            <Menu
                notify={notify}
                id="notify-menu"
                open={open}
                onClose={handleCloseNoti}
                PaperProps={{
                    elevation: 0,
                    sx: {
                        overflow: 'hidden',
                        filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',

                    },
                    style: {
                        width: '350px',
                        borderRadius: '15px'
                    },
                }}
                transformOrigin={{ horizontal: 'right', vertical: 'bottom' }}
                anchorOrigin={{ horizontal: 'right', vertical: 'top' }}
            >
                {/* Dropdown Notification List (only 5) */}
                <div className='notify'>
                    <div className='notification'>
                        <h3 >Thông báo</h3>
                    </div>
                    <button className='close' onClick={handleCloseNoti}><CloseIcon /></button>
                    {
                        notificationList && notificationList.length > 0
                            ?
                            notificationList.map((item) =>
                            <div className="notifi-item" onClick={() => goUpdateEggBatch(item)}>
                                <div className="text">
                                    <h4>{item.notificationBrief}</h4>
                                    <p>({item.date})</p>
                                </div>
                            </div>
                            )
                            :
                            <div className="notifi-item">
                                <div className="text">
                                    <h4>Hiện tại không có thông báo nào</h4>
                                </div>
                            </div>
                    }
                    <br/><br/>
                    <i><button className='mybtn' onClick={() => viewAllNoti()} >Xem tất cả</button></i>
                </div>
            </Menu>
            <ToastContainer position="top-left"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="colored" />
        </div>

    )
}

export default Nav