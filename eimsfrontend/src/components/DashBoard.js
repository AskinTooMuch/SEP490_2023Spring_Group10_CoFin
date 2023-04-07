import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from "react-router-dom";
import { ProgressBar } from 'react-bootstrap';
import "../css/dashboard.css"
import WithPermission from '../utils.js/WithPermission';
import axios from 'axios';
import { toast } from 'react-toastify';

const Dashboard = () => {
  // Dependency
  const [dataLoaded, setDataLoaded] = useState(false);

  //API URLs
  const MACHINE_DASHBOARD = '/api/machine/dashboard'

  //Data holding objects
  const [machineList, setMachineList] = useState([]);

  // 
  useEffect(() => {
    if (dataLoaded) return;
    loadMachineList();
    setDataLoaded(true);
  }, []);

  // Get export list
  const loadMachineList = async () => {
    try {
      const result = await axios.get(MACHINE_DASHBOARD,
        { params: { facilityId: sessionStorage.getItem("facilityId") } },
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: true
        });
      console.log(JSON.stringify(result.data))
      setMachineList(result.data);
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else {
        if ((err.response.data === null) || (err.response.data === '')) {
          toast.error('Có lỗi xảy ra, vui lòng thử lại');
        } else {
          toast.error(err.response.data);
        }
      }
    }
  }

  let navigate = useNavigate();
  const routeChange = (mid) => {
    navigate('/machinedetail', { state: { id: mid } });
  }

  const progress = 65;
  return (
    <div>
      <WithPermission roleRequired='2'>
        <div className="container">
          <div className="row">
            {
              machineList && machineList.length > 0
                ? machineList.map((item) =>
                  <div className="col-md-3 col-sm-6">
                    <div className="serviceBox orange" onClick={() => routeChange(item.machineId)}>
                      <div className="service-icon">
                        <span>{item.machineTypeName}:{item.machineName}</span>
                      </div>
                      <p className="description">
                        {
                          item.eggs && item.eggs.length > 0
                            ? item.eggs.map((itemm) =>
                              <>
                                <span>Mã lô {itemm.eggBatchId}: {itemm.incubationDateToNow}/{itemm.incubationPeriod} ngày </span>
                                <p>Số lượng {itemm.amount}</p>
                                <ProgressBar now={Math.round(itemm.incubationDateToNow / itemm.incubationPeriod * 100)} variant="success" label={` `} />
                              </>
                            )
                            : ''
                        }
                      </p>
                      <h3 className="title">{item.curCapacity}/{item.maxCapacity}</h3>
                    </div>
                  </div>
                )
                : 
                <div>
                <h4>
                  Hiện tại không có máy nào đang ấp
                </h4>
                </div>
                
            }
          </div>
        </div>
      </WithPermission>
      
    </div >
  );
}

export default Dashboard