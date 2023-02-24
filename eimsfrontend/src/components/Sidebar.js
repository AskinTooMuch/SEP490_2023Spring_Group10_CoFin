import React, {useEffect, useState} from "react";
import styled from 'styled-components'
import SidebarItems from "./SidebarItems";
import {Link, useLocation} from "react-router-dom";

function Sidebar(props, {defaultActive,}) {
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
            return  "/" + path;
        }
        return path;
    }

    useEffect(()=> {
        const activeItem = SidebarItems.findIndex(item=> getPath(item.route) === getPath(location.pathname))
        changeActiveIndex(activeItem);
    }, [location])

    return (
        <>
            <SidebarParent>
                <div style={{position: 'fixed'}}>
                    {
                        SidebarItems.map((item, index)=> {
                            return (
                                <Link to={item.route}>
                                    <SidebarItem key={item.id}
                                                 active={index === activeIndex}
                                    >
                                        {item.icon}<p>{item.name}</p>
                                    </SidebarItem>
                                </Link>
                            );
                        })
                    }

                </div>
                <div className="behind-the-scenes"/>
            </SidebarParent>
        </>
    );
}

export default Sidebar;

const SidebarParent = styled.div`
  background: #262626;
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

const SidebarItem = styled.div`
  transition: all 0.25s ease-in-out;
  background: ${props => props.active ? "#009b4d" : ""};
  margin: 4px 12px;
  border-radius: 4px;
  p {
    color: white;
    font-weight: bold;
    text-decoration: none;
  }
  p:hover{
    color:black;
  }
  &:hover {
    cursor:pointer;
    background-color: #ffcc00;
  }
  
  &:hover:not(:first-child) {
    background: #009b4d;
  }
`;