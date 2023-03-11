import React from 'react';
import Login from './components/Login'
import Register from './components/Register';
import HomePage from './components/HomePage';
import { Routes, Route } from 'react-router-dom';
import Nav from './components/Nav';
import PrivateRoute from './utils.js/PrivateRoute';
import Unauthorized from './components/Unauthorized';
import DashBoard from './components/DashBoard';
import Profile from './components/Profile';
import Order from './components/Order';
import Machine from './components/Machine'
import Egg from './components/Eggs'
import EggBatchDetail from './components/EggBatchDetail'
import BreedDetails from './components/BreedDetails'
import MachineDetails from './components/MachineDetails'
import SupplierDetails from './components/SupplierDetails'
import Subscription from './components/Subscription';
import ForgotPassword from './components/ForgotPassword';
import EggBatch from './components/EggBatch';
import FinanManage from './components/FinanManage';
import Employee from './components/Employee';
import EmployeeDetails from './components/EmployeeDetails';
import CustomerDetails from './components/CustomerDetails';
import ChangePassword from './components/ChangePassword';
import RegisterOTP from './components/RegisterOTP'
function App() {
  return (
    <>
      <Nav />
      <Routes>
        <Route element={<PrivateRoute />} >
          <Route path="/subcribe" element={<Subscription />} />
          <Route path="/egg" element={<Egg />} />
          <Route path="/machine" element={<Machine />} />
          <Route path="/order" element={<Order />} />
          <Route path="/dashboard" element={<DashBoard />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/eggbatch" element={<EggBatch />} />
          <Route path="/finance" element={<FinanManage />} />
          <Route path="/employee" element={<Employee />} />
          {/*Details pages */}
          <Route path="/employeedetail" element={<EmployeeDetails />} />
          <Route path="/eggbatchdetail" element={<EggBatchDetail />} />
          <Route path="/breeddetail" element={<BreedDetails />} />
          <Route path="/machinedetail" element={<MachineDetails />} />
          <Route path="/supplierdetail" element={<SupplierDetails />} />
          <Route path="/customerdetail" element={<CustomerDetails />} />
        </Route>

        {/* public routes */}
        <Route path="/" element={<HomePage />} />
        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />
        <Route path="registerotp" element={<RegisterOTP />} />
        <Route path="forgotpassword" element={<ForgotPassword />} />
        <Route path="changepassword" element={<ChangePassword />} />
        <Route path="unauthorized" element={<Unauthorized />} />
      </Routes>

    </>
  );
}

export default App;
