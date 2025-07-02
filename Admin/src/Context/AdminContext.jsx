import axios from "axios";
import { createContext, useState } from "react";
import { toast } from "react-toastify";

// eslint-disable-next-line react-refresh/only-export-components
export const AdminContext = createContext();

const AdminContextProvider = (props) => {
    const [aToken, setAToken] = useState(localStorage.getItem('token') ? localStorage.getItem('token') : '');
    const [doctors, setDoctors] = useState([]);
    const [appointments,setAppointments] = useState([])
    const [dashData , setDashData] = useState(false)

    const backendUrl = import.meta.env.VITE_BACKEND_URL;
    // const backendUrl3 = import.meta.env.VITE_BACKEND_URL3;

    // const getAllDoctors = async () => {
    //     try {
    //         const response = await axios.post(`${backendUrl}/admin/getAllDoctors`, {}, {
    //             headers: {
    //                 'Authorization': `Bearer ${aToken}`
    //             }
    //         });
    //         if (response.status === 200) {
    //             setDoctors(response.data); // Correctly access the data
    //             // toast.success("Doctors fetched successfully");
    //         } else {
    //             // console.log(response);
    //             toast.error("Error fetching doctors");
    //         }
    //     } catch (error) {
    //         toast.error(error.message);
    //         console.error(error);
    //     }
    // };

    // const cancelAppointment = async (appointmentId) => {
    //     try {
    //         const response = await axios.put(`${backendUrl3}/appointment/cancelAppointment`, null, {
    //             params: {
    //                 appointmentId: appointmentId
    //             }
    //         });
    //         console.log(response.data);
    //         if (response.status === 200) {
    //             toast.success('Appointment cancelled successfully');
    //             getAllAppointment()
    //         } else {
    //             toast.error('Failed to cancel appointment');
    //         }
    //     } catch (error) {
    //         console.log(error);
    //         toast.error('Error cancelling appointment');
    //     }
    // };

    // const changeAvailability = async (email) => {
    //     try {
    //         const response = await axios.post(`${backendUrl3}/admin/changeAvailability/${email}`, {}, {
    //             headers: {
    //                 'Authorization': `Bearer ${aToken}`
    //             }
    //         });
    //         if (response.status === 200) {
    //             toast.success("Availability changed successfully");
    //             await getAllDoctors();
    //         } else {
    //             toast.error("Error changing availability");
    //         }
    //     } catch (error) {
    //         toast.error(error.message);
    //         console.error(error);
    //     }
    // };

    // const getDashboardData = async () => {
    //     try{
    //         //http://localhost:9003/appointment/DashData
    //         const response = await axios.get(`${backendUrl3}/appointment/DashData`,{headers:{aToken}})
    //         if(response.status === 200){
    //             toast.success("Successfully fetched DashData")
    //             console.log(response.data)
    //             setDashData(response.data)
    //         } else{
    //             toast.error("failed to fetched DashData")
    //         }
    //
    //
    //     } catch (error) {
    //         toast.error(error.message);
    //         console.error(error);
    //     }
    // }

    // const getAllAppointment = async () => {
    //     try{
    //         const response = await axios.get(`${backendUrl3}/appointment/allAppointments`,{headers:{aToken}})
    //         if(response.status === 200){
    //             toast.success("Successfully fetched Apppointments")
    //             console.log(response.data)
    //             // console.log(response.data[0].userData.image)
    //             setAppointments(response.data)
    //         } else{
    //             toast.error("failed to fetched Apppointments")
    //         }
    //     } catch (error){
    //         toast.error(error.message);
    //         console.log(error)
    //     }
    // }

    const value = {
        aToken, setAToken,
        backendUrl,
        // backendUrl3
        doctors,
        // getAllDoctors,
        //changeAvailability,
        // appointments,setAppointments,
        // getAllAppointment,cancelAppointment,
        // getDashboardData,dashData
    };

    return (
        <AdminContext.Provider value={value}>
            {/* eslint-disable-next-line react/prop-types */}
            {props.children}
        </AdminContext.Provider>
    );
};

export default AdminContextProvider;