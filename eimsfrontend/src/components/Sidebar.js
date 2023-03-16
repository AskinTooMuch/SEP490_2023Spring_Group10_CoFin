import React, { useEffect, useState } from "react";
import styled from 'styled-components'
import SidebarItemsOwner from "./SidebarItemsOwner";
import { Link, useLocation } from "react-router-dom";
import WithPermission from "../utils.js/WithPermission";
import SidebarItemsModer from "./SidebarItemsModer";

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
    <>
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
              SidebarItemsModer.map((item2, index2) => {
                return (
                  <Link to={item2.route}>
                    <SidebarItemModer key={[item2.id]}
                      active={index2 === activeIndex}
                    >
                      {item2.icon}<p>{item2.name}</p>
                    </SidebarItemModer>
                  </Link>
                );
              })
            }
          </WithPermission>
        </div>
        <div className="behind-the-scenes" />
      </SidebarParent>
    </>
  );
}

export default Sidebar;

const SidebarParent = styled.div`
  background: #f59d6e;
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