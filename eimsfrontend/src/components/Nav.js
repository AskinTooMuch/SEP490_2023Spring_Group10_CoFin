import React from 'react'
import { Link, useNavigate } from 'react-router-dom';
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
const Nav = () => {
    const auth = sessionStorage.getItem("curUserId");
    const navigate = useNavigate();

    const dashboard = () => {
        navigate("/dashboard")
    }
    const account = () => {
        navigate("/accountmanage")
    }
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
            <MenuItem>
                <IconButton
                    size="large"
                    aria-label="show 17 new notifications"
                    color="inherit"
                >
                    <Badge badgeContent={17} color="error">
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
                                        <WithPermission roleRequired="4">
                                            <IconButton size="large" color="inherit" onClick={account}>
                                                <Badge color="error">
                                                    <DashboardIcon />
                                                </Badge>
                                            </IconButton>
                                        </WithPermission>
                                        <IconButton
                                            size="large"
                                            aria-label="show 17 new notifications"
                                            color="inherit"
                                        >
                                            <Badge badgeContent={17} color="error">
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
                                    <Button className="signupbutton" color="inherit"><Link style={{ textDecoration: "none" }} to="/registerotp">Đăng ký</Link></Button>
                                    <Button className="loginbutton" color="inherit"><Link style={{ textDecoration: "none" }} to="/login">Đăng nhập</Link></Button>
                                </Box>
                        }

                    </Toolbar>
                </AppBar>
                {renderMobileMenu}
                {renderMenu}
            </Box>
        </div>

    )
}

export default Nav