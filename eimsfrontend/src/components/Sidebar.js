import React, { useEffect, useState } from "react";
import styled from 'styled-components'
import SidebarItemsOwner from "./SidebarItemsOwner";
import { Link, useLocation } from "react-router-dom";
import WithPermission from "../utils.js/WithPermission";
import SidebarItemsModer from "./SidebarItemsModer";
import SidebarItemsEmployee from "./SidebarItemsEmployee";

function Sidebar(props, { defaultActive, }) {
  const location = useLocation();
  const lastActiveIndexString = localStorage.getItem("lastActiveIndex");
  const lastActiveIndex = Number(lastActiveIndexString);
  const [activeIndex, setActiveIndex] = useState(lastActiveIndex || defaultActive);

  function changeActiveIndex(newIndex) {
    localStorage.setItem("lastActiveIndex", newIndex)
    setActiveIndex(newIndex)
  }

  function getPath(path) {
    if (path.charAt(0) !== "/") {
      return "/" + path;
    }
    return path;
  }

  useEffect(() => {
    const activeItem = SidebarItemsOwner.findIndex(item => getPath(item.route) === getPath(location.pathname))
    changeActiveIndex(activeItem);
  }, [location])
  return (
    <div >
      <SidebarParent>
        <div style={{ position: 'fixed' }}>
          {/**Sidenav for Owner */}
          <WithPermission roleRequired='2'>
            {
              SidebarItemsOwner.map((item, index) => {
                return (
                  <Link to={item.route}>
                    <SidebarItemOwner key={item.id}
                      active={index === activeIndex}
                    >
                      {item.icon}<p>{item.name}</p>
                    </SidebarItemOwner>
                  </Link>
                );
              })
            }
          </WithPermission>
          {/**Sidenav for Moderator */}
          <WithPermission roleRequired='4'>
            {
              SidebarItemsModer.map((item, index) => {
                return (
                  <Link to={item.route}>
                    <SidebarItemModer key={[item.id]}
                    >
                      {item.icon}<p>{item.name}</p>
                    </SidebarItemModer>
                  </Link>
                );
              })
            }
          </WithPermission>
          {/**Sidenav for Employee */}
          <WithPermission roleRequired='3'>
            {
              SidebarItemsEmployee.map((item, index) => {
                return (
                  <Link to={item.route}>
                    <SidebarItemEmployee key={[item.id]}
                    >
                      {item.icon}<p>{item.name}</p>
                    </SidebarItemEmployee>
                  </Link>
                );
              })
            }
          </WithPermission>
        </div>
        <div className="behind-the-scenes" />
      </SidebarParent>
    </div>
  );
}

export default Sidebar;

const SidebarParent = styled.div`
  background: #f59d6e;
  border-radius:0 15px 15px 0;
  a {
    text-decoration: none;
    font-size: 20px;
  }
  
  & > div {
    width: 250px;
    height: 100vh;
  }
  
  .behind-the-scenes {
    width: 250px;
  }
`;

const SidebarItemOwner = styled.div`
  transition: all 0.25s ease-in-out;
  background: ${props => props.active ? "#F46110" : ""};
  margin: 4px 12px;
  border-radius: 4px;
  p {
    color: white;
    font-weight: bold;
    text-decoration: none;
  }
  &:hover {
    cursor:pointer;
    background-color: #F46110;
  }
  
  &:hover:not(:first-child) {
    background: #009b4d;
  }
`;

const SidebarItemModer = styled.div`
  transition: all 0.25s ease-in-out;
  background: ${props => props.active ? "#F46110" : ""};
  margin: 4px 12px;
  border-radius: 4px;
  p {
    color: white;
    font-weight: bold;
    text-decoration: none;
  }
  &:hover {
    cursor:pointer;
    background-color: #F46110;
  }
  
  &:hover:not(:first-child) {
    background: #009b4d;
  }
`;

const SidebarItemEmployee = styled.div`
  transition: all 0.25s ease-in-out;
  background: ${props => props.active ? "#F46110" : ""};
  margin: 4px 12px;
  border-radius: 4px;
  p {
    color: white;
    font-weight: bold;
    text-decoration: none;
  }
  &:hover {
    cursor:pointer;
    background-color: #F46110;
  }
  
  &:hover:not(:first-child) {
    background: #009b4d;
  }
`;