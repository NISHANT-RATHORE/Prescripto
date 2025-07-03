import React, { useContext } from 'react'
import Login from "./pages/Login.jsx";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { AdminContext } from './Context/AdminContext.jsx';
import Navbar from './Compoenents/Navbar.jsx';
import Sidebar from './Compoenents/Sidebar.jsx';
import { Route, Routes } from 'react-router-dom';
import Dashboard from './pages/Admin/Dashboard.jsx';
import AllAppoinments from './pages/Admin/AllAppoinments.jsx';
import AddDoctor from './pages/Admin/AddDoctor.jsx';
import DoctorsList from './pages/Admin/DoctorsList.jsx';

const App = () => {
    const { aToken } = useContext(AdminContext)

    return aToken ? (
        <div className='bg-[#F8F9FD]'>
            <ToastContainer />
            <Navbar />
            <div className='flex items-start'>
                <Sidebar />
                <Routes>
                    <Route path='/' element={<></>} />
                    {/*<Route path='/admin-dashboard' element={<Dashboard/>} />*/}
                    <Route path='/all-appoinments' element={<AllAppoinments/>} />
                    <Route path='/add-doctor' element={<AddDoctor/>} />
                    <Route path='/doctor-list' element={<DoctorsList/>} />
                </Routes>
            </div>
        </div>
    ) : <>
        <Login />
        <ToastContainer />
    </>
}
export default App
