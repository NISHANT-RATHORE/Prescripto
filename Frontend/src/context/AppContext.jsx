import {createContext, useCallback, useEffect, useState} from "react";
import axios from "axios";
import {toast} from "react-toastify"

export const AppContext = createContext();

const AppContextProvider = (props) => {

    const currencySymbol = '$';
    // const auth_url = import.meta.env.VITE_BACKEND_AUTH_SERVICE
    // const doctor_url = import.meta.env.VITE_BACKEND_DOCTOR_SERVICE
    // const patient_url = import.meta.env.VITE_BACKEND_PATIENT_SERVICE

    const backend_url = import.meta.env.VITE_BACKEND_URL;

    const [doctors, setDoctors] = useState([])
    const [token, setToken] = useState(localStorage.getItem('token') ? localStorage.getItem('token') : false)
    const [userData, setUserData] = useState(false)
    const [userId, setUserId] = useState("")

    const getDoctorsData = async () => {
        try {
            const response = await axios.get(`${backend_url}/doctor/list`)
            console.log(response.data)
            if (response.status === 200) {
                setDoctors(response.data)
            }
        } catch (error) {
            console.log(error)
            toast.error(error.message)
        }
    }

    // useEffect(() => {
    //     getDoctorsData()
    // }, []);

    const ping = async () => {
        try {
            const response = await axios.get(`${backend_url}/auth/ping`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (response.status === 200 && response.data) {
                console.log(response.data)
                setUserId(response.data); // Set userId from the response
            }
        } catch (error) {
            console.log(error);
            toast.error(error.message);
        }
    };


    // eslint-disable-next-line react-hooks/exhaustive-deps
    const loadUserProfileData = useCallback(async () => {
        try {
            console.log("Token:", token);
            console.log("UserId:", userId);
            const response = await axios.get(`${backend_url}/patient/getProfile`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
                params: {
                    userId: userId, // Pass userId as a request parameter
                },
            });
            if (response.status === 200) {
                console.log("User profile data:", response.data);
                setUserData(response.data);
            } else {
                toast.error('Failed to load profile data');
            }
        } catch (error) {
            console.error("Error fetching user profile:", error);
            toast.error(error.message);
        }
    }, [backend_url, token, userId]);

    const value = {
        doctors, currencySymbol, getDoctorsData,
        token, setToken, ping,
        // auth_url, doctor_url, patient_url,
        backend_url,
        userData, setUserData,
        loadUserProfileData,
        userId, setUserId,
    }

    useEffect(() => {
        getDoctorsData()
    }, [])
    useEffect(() => {
        if (token) {
            ping();
        }
    }, [token]);
    useEffect(() => {
        console.log("Token:", token);
        console.log("UserId:", userId);
        if (token && userId) {
            loadUserProfileData(); // Call loadUserProfileData only when token and userId are available
        }
    }, [token, userId, loadUserProfileData]);


    return (
        <AppContext.Provider value={value}>
            {/* eslint-disable-next-line react/prop-types */}
            {props.children}
        </AppContext.Provider>
    )
}

export default AppContextProvider
