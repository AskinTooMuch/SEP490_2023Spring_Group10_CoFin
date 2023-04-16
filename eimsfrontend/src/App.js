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
import ImportBill from './components/ImportBill';
import AccountManager from './components/AccountManager';
import SubcriptionManager from './components/SubcriptionManager';
import CreateImportBill from './components/CreateImportBill';
import ImportBillDetail from './components/ImportBillDetail';
import NotificationList from './components/NotificationList';
import SubscriptionDetail from './components/SubscriptionDetail';
import CreateExportBill from './components/CreateExportBill';
import ExportBill from './components/ExportBill';
import ExportBillDetail from './components/ExportBillDetail';
import SubscriptionPayment from './components/SubscriptionPayment';
import SubcriptionInfo from './components/SubscriptionInfo';
import TestOTP from './components/TestOTP';
import { ToastContainer } from 'react-toastify';
import ChangeOldPassword from './components/ChangeOldPassword';
function App() {
  return (
    <>
      <Nav />
      <Routes>
        {/*Owner pages */}
        <Route element={<PrivateRoute roleRequired="2" />} >
          <Route path="/subcribe" element={<Subscription />} />
          <Route path="/subscriptionPayment" element={<SubscriptionPayment />} />
          <Route path="/egg" element={<Egg />} />
          <Route path="/machine" element={<Machine />} />
          <Route path="/order" element={<Order />} />
          <Route path="/dashboard" element={<DashBoard />} />
          <Route path="/eggbatch" element={<EggBatch />} />
          <Route path="/finance" element={<FinanManage />} />
          <Route path="/employee" element={<Employee />} />
          <Route path="/importbill" element={<ImportBill />} />
          <Route path="/exportbill" element={<ExportBill />} />
          <Route path="/createimportbill" element={<CreateImportBill />} />
          <Route path="/createexportbill" element={<CreateExportBill />} />
          {/*Details pages */}
          <Route path="/employeedetail" element={<EmployeeDetails />} />
          <Route path="/eggbatchdetail" element={<EggBatchDetail />} />
          <Route path="/breeddetail" element={<BreedDetails />} />
          <Route path="/machinedetail" element={<MachineDetails />} />
          <Route path="/supplierdetail" element={<SupplierDetails />} />
          <Route path="/customerdetail" element={<CustomerDetails />} />
          <Route path="/importbilldetail" element={<ImportBillDetail />} />
          <Route path="/exportbilldetail" element={<ExportBillDetail />} />
          <Route path="/subscriptionInfo" element={<SubcriptionInfo />} />
        </Route>

        {/*Moderator pages */}
        <Route element={<PrivateRoute roleRequired="4" />} >
          <Route path="/subcriptiondetail" element={<SubscriptionDetail />} />
          <Route path='/accountmanage' element={<AccountManager />} />
          <Route path='/subcribemanage' element={<SubcriptionManager />} />
          <Route path='/changeoldpassword' element={<ChangeOldPassword />} />
        </Route>

        {/*Employee pages */}
        <Route element={<PrivateRoute roleRequired="3" />} >
          <Route path="/dashboard" element={<DashBoard />} />
          <Route path="/egg" element={<Egg />} />
          <Route path="/machine" element={<Machine />} />
        </Route>

        {/*All roles pages */}
        <Route element={<PrivateRoute />} >
          <Route path="/profile" element={<Profile />} />
          <Route path="/notificationlist" element={<NotificationList />} />
        </Route>

        {/* Public routes */}
        <Route path="/" element={<HomePage />} />
        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />
        <Route path='testotp' element={<TestOTP />} />
        <Route path="registerotp" element={<RegisterOTP />} />
        <Route path="forgotpassword" element={<ForgotPassword />} />
        <Route path="changepassword" element={<ChangePassword />} />

        {/* Permission denied route */}
        <Route path="unauthorized" element={<Unauthorized />} />
      </Routes>
      <ToastContainer position="top-left"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="colored"
        style={{ zIndex: "10000" }} />
    </>
  );
}

export default App;
