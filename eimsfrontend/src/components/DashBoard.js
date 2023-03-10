import * as React from 'react';
import { ProgressBar } from 'react-bootstrap';
import "../css/dashboard.css"
const Dashboard = () => {
  const progress = 65;
  return (
    <div>
      <div class="container">
        <div class="row">
          <div class="col-md-3 col-sm-6">
            <div class="serviceBox orange">
              <div class="service-icon">
                <span>Máy ấp 40</span>
              </div>
              <p class="description">
                <span>Lô HDC216</span>
                <ProgressBar now={100} variant="success" label={`${100}% `} />

                <span>Lô CUS937</span>
                <ProgressBar now={70} variant="warning" label={`${70}% `} />

                <span>Lô CUS937</span>
                <ProgressBar now={progress} variant="info" label={`${progress}% `} />

                <span>Lô CCC696</span>
                <ProgressBar now={20} variant="danger" label={`${20}% `} />

              </p>
              <h3 class="title">3000/3000</h3>
            </div>
          </div>
          <div class="col-md-3 col-sm-6">
            <div class="serviceBox orange">
              <div class="service-icon">
                <span>Máy ấp 13</span>
              </div>
              <p class="description">
                <span>Lô HDC216</span>
                <ProgressBar now={progress} variant="success" label={`${progress}% `} />

                <span>Lô TQN712</span>
                <ProgressBar now={70} variant="warning" label={`${70}% `} />

                <span>Lô CLS451</span>
                <ProgressBar now={100} variant="info" label={`${100}% `} />

                <span>Lô CCC696</span>
                <ProgressBar now={20} variant="danger" label={`${20}% `} />
              </p>
              <h3 class="title">3000/3000</h3>
            </div>
          </div>
          <div class="col-md-3 col-sm-6">
            <div class="serviceBox orange">
              <div class="service-icon">
                <span>Máy nở 61</span>
              </div>
              <p class="description">
                <span>Lô HDC216</span>
                <ProgressBar now={100} variant="success" label={`${100}% `} />

                <span>Lô CUS937</span>
                <ProgressBar now={70} variant="warning" label={`${70}% `} />

                <span>Lô CUS937</span>
                <ProgressBar now={progress} variant="info" label={`${progress}% `} />

                <span>Lô CCC696</span>
                <ProgressBar now={20} variant="danger" label={`${20}% `} />
              </p>
              <h3 class="title">900/1000</h3>
            </div>
          </div>
          <div class="col-md-3 col-sm-6">
            <div class="serviceBox orange">
              <div class="service-icon">
                <span>Máy nở 1</span>
              </div>
              <p class="description">
                <span>Lô HDC216</span>
                <ProgressBar now={100} variant="success" label={`${100}% `} />

                <span>Lô CUS937</span>
                <ProgressBar now={70} variant="warning" label={`${70}% `} />

                <span>Lô CUS937</span>
                <ProgressBar now={progress} variant="info" label={`${progress}% `} />

                <span>Lô CCC696</span>
                <ProgressBar now={20} variant="danger" label={`${20}% `} />
              </p>
              <h3 class="title">3000/3000</h3>
            </div>
          </div>

        </div>
      </div>

    </div >
  );
}

export default Dashboard