import * as React from 'react';
import { ProgressBar } from 'react-bootstrap';
import "../css/dashboard.css"
import WithPermission from '../utils.js/WithPermission';
const Dashboard = () => {
  const progress = 65;
  return (
    <div>
      <WithPermission roleRequired='2'>
        <div className="container">
          <div className="row">
            <div className="col-md-3 col-sm-6">
              <div className="serviceBox orange">
                <div className="service-icon">
                  <span>Máy ấp 40</span>
                </div>
                <p className="description">
                  <span>Lô HDC216</span>
                  <ProgressBar now={100} variant="success" label={`${100}% `} />

                  <span>Lô CUS937</span>
                  <ProgressBar now={70} variant="warning" label={`${70}% `} />

                  <span>Lô CUS937</span>
                  <ProgressBar now={progress} variant="info" label={`${progress}% `} />

                  <span>Lô CCC696</span>
                  <ProgressBar now={20} variant="danger" label={`${20}% `} />

                </p>
                <h3 className="title">3000/3000</h3>
              </div>
            </div>
            <div className="col-md-3 col-sm-6">
              <div className="serviceBox orange">
                <div className="service-icon">
                  <span>Máy ấp 13</span>
                </div>
                <p className="description">
                  <span>Lô HDC216</span>
                  <ProgressBar now={progress} variant="success" label={`${progress}% `} />

                  <span>Lô TQN712</span>
                  <ProgressBar now={70} variant="warning" label={`${70}% `} />

                  <span>Lô CLS451</span>
                  <ProgressBar now={100} variant="info" label={`${100}% `} />

                  <span>Lô CCC696</span>
                  <ProgressBar now={20} variant="danger" label={`${20}% `} />
                </p>
                <h3 className="title">3000/3000</h3>
              </div>
            </div>
            <div className="col-md-3 col-sm-6">
              <div className="serviceBox orange">
                <div className="service-icon">
                  <span>Máy nở 61</span>
                </div>
                <p className="description">
                  <span>Lô HDC216</span>
                  <ProgressBar now={100} variant="success" label={`${100}% `} />

                  <span>Lô CUS937</span>
                  <ProgressBar now={70} variant="warning" label={`${70}% `} />

                  <span>Lô CUS937</span>
                  <ProgressBar now={progress} variant="info" label={`${progress}% `} />

                  <span>Lô CCC696</span>
                  <ProgressBar now={20} variant="danger" label={`${20}% `} />
                </p>
                <h3 className="title">900/1000</h3>
              </div>
            </div>
            <div className="col-md-3 col-sm-6">
              <div className="serviceBox orange">
                <div className="service-icon">
                  <span>Máy nở 1</span>
                </div>
                <p className="description">
                  <span>Lô HDC216</span>
                  <ProgressBar now={100} variant="success" label={`${100}% `} />

                  <span>Lô CUS937</span>
                  <ProgressBar now={70} variant="warning" label={`${70}% `} />

                  <span>Lô CUS937</span>
                  <ProgressBar now={progress} variant="info" label={`${progress}% `} />

                  <span>Lô CCC696</span>
                  <ProgressBar now={20} variant="danger" label={`${20}% `} />
                </p>
                <h3 className="title">3000/3000</h3>
              </div>
            </div>

          </div>
        </div>
      </WithPermission>
    </div >
  );
}

export default Dashboard