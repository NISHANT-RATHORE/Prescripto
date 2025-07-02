// eslint-disable-next-line no-unused-vars
import React, {useContext, useEffect, useState} from 'react';
import {useNavigate, useParams} from 'react-router';
import {AppContext} from '../context/AppContext.jsx';
import {assets} from "../assets/prescripto_assets/assets/assets_frontend/assets.js";
import RelatedDoctors from "../Components/RelatedDoctors.jsx";
import {toast} from 'react-toastify';
import axios from 'axios';

const Appointment = () => {
    const {docId} = useParams();
    const {doctors, currencySymbol, token, getDoctorsData, userData, backend_url} = useContext(AppContext);
    const daysOfWeek = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT']

    const navigate = useNavigate();
    // const appointment_url = import.meta.env.VITE_BACKEND_APPOINTMENT_SERVICE
    // const doctor_url = import.meta.env.VITE_BACKEND_DOCTOR_SERVICE


    const [docInfo, setDocInfo] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [docSlots, setDocSlots] = useState([]);
    const [slotIndex, setSlotIndex] = useState(0);
    const [slotTime, setSlotTime] = useState('');


    const fetchDocInfo = () => {
        try {
            setLoading(true); // Set loading state to true before the operation

            // Debugging logs
            console.log("docId from URL:", docId);
            console.log("doctors array:", doctors);

            const doctor = doctors.find((item) => {
                console.log("Checking doctorId:", item.doctorId, "against docId:", docId);
                return String(item.doctorId) === String(docId);
            });

            if (doctor) {
                console.log("Doctor found:", doctor);
                setDocInfo(doctor); // Set the fetched doctor info
            } else {
                console.warn("Doctor not found for docId:", docId);
                // setError('Doctor not found');
            }
        } catch (err) {
            console.error("Error fetching doctor information:", err);
            setError('Error fetching doctor information');
        } finally {
            setLoading(false); // Reset loading state
        }
    };

    useEffect(() => {
        console.log("Calling fetchDocInfo...");
        console.log(doctors)
        // console.log(getDoctorsData)
        console.log(docId)
        console.log(docInfo)
        // console.log(loadUserProfileData)
    }, [doctors, docId]);


    const getAvailableSlots = async () => {
        setDocSlots([]);
        let today = new Date();
        for (let i = 0; i < 7; i++) {
            let currentDate = new Date(today);
            currentDate.setDate(today.getDate() + i);

            let endTime = new Date();
            endTime.setDate(today.getDate() + i);
            endTime.setHours(21, 0, 0, 0);

            if (today.getDate() === currentDate.getDate()) {
                currentDate.setHours(currentDate.getHours() > 10 ? currentDate.getHours() + 1 : 10);
                currentDate.setMinutes(currentDate.getMinutes() > 30 ? 30 : 0);
            } else {
                currentDate.setHours(10);
                currentDate.setMinutes(0);
            }

            let timeSlots = [];
            while (currentDate < endTime) {
                let formattedTime = currentDate.toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});
                timeSlots.push({
                    dateTime: new Date(currentDate),
                    time: formattedTime
                });
                currentDate.setMinutes(currentDate.getMinutes() + 30);
            }
            setDocSlots(prev => ([...prev, timeSlots]));
        }
    };

    const BookAppointment = async () => {
        if (!token) {
            toast.warn('Please login to book an appointment');
            return navigate('/login');
        }
        if (!slotTime || !docSlots[slotIndex] || !docSlots[slotIndex][0]) {
            toast.error('Please select a valid slot date and time');
            return;
        }
        try {
            const date = docSlots[slotIndex][0].dateTime;
            let day = date.getDate();
            let month = date.getMonth() + 1;
            let year = date.getFullYear();

            const slotDate = day + "_" + month + "_" + year;

            // const userProfileData = loadUserProfileData(); // Call loadUserProfileData()
            const doctorData = docInfo;
            const patientData = userData;

            console.log("appointment url: ", backend_url);
            console.log("token: ", token);

            const response = await axios.post(`${backend_url}/appointment/bookAppointment`, {
                    patientData, // Send user profile data
                    doctorData,         // Send doctor info object
                    slotDate,
                    slotTime
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            if (response.status === 200) {
                toast.success('Appointment booked successfully');
                getDoctorsData();
                navigate('/my-appointments');
            } else {
                toast.error('Failed to book appointment');
            }
        } catch (error) {
            console.log(error);
            toast.error(error.message);
        }
    };

    useEffect(() => {
        fetchDocInfo();
    }, [doctors, docId]);

    useEffect(() => {
        if (docInfo) {
            getAvailableSlots();
        }
    }, [docInfo]);

    useEffect(() => {
        console.log(docInfo)
    }, []);

    useEffect(() => {
        console.log(userData)
    }, []);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div>
            {docInfo && (
                <div className='flex flex-col sm:flex-row gap-4'>
                    <div>
                        <img className='bg-primary w-full sm:max-w-72 rounded-lg' src={docInfo.docImg}
                             alt={docInfo.name}/>
                    </div>
                    <div
                        className='flex-1 border border-gray-400 rounded-lg p-8 py-7 bg-white mx-2 sm:mx-0 mt-[-80px] sm:mt-0'>
                        <p className='flex items-center gap-2 text-2xl font-medium text-gray-900'>{docInfo.name} <img
                            className='w-5' src={assets.verified_icon}/></p>
                        <div className='flex item-center gap-2 text-sm mt-1 text-gray-600'>
                            <p>{docInfo.degree} - {docInfo.speciality}</p>
                            <button className='py-0.5 px-2 border text-xs ronded-full'>{docInfo.experience}</button>
                        </div>
                        <div>
                            <p className='flex items-center gap-1 text-sm font-medium text-gray-900 mt-3'>
                                About <img src={assets.info_icon}/>
                            </p>
                            <p className='text-sm text-gray-500 max-w-[700px] mt-1'>{docInfo.about}</p>
                        </div>
                        <p className='text-gray-500 font-medium mt-4'>
                            Appointment fee: <span className='text-gray-600'>{currencySymbol}{docInfo.fees}</span>
                        </p>
                    </div>
                </div>
            )}
            {/*-------------------------Booking Slots-------------*/}
            <div className='sm:ml-72 sm:pl-4 mt-4 font-medium text-gray-700'>
                <p>Booking slots</p>
                <div className='flex gap-3 items-center w-full overflow-x-scroll mt-4'>
                    {
                        docSlots.length && docSlots.map((item, index) => (
                            <div onClick={() => setSlotIndex(index)}
                                 className={`text-center py-6 min-w-16 rounded-full cursor-pointer ${slotIndex === index ? 'bg-primary text-white' : 'border border-gray-200'}`}
                                 key={index}>
                                <p>{item[0] && daysOfWeek[item[0].dateTime.getDay()]}</p>
                                <p>{item[0] && item[0].dateTime.getDate()}</p>
                            </div>
                        ))
                    }
                </div>
            </div>

            <div className='flex items-center gap-3  overflow-x-scroll mt-4  '>
                {docSlots.length && docSlots[slotIndex].map((item, index) => (
                    <p onClick={() => setSlotTime(item.time)}
                       className={`text-sm font-light flex-shrink-0 px-3 py-1 rounded-full cursor-pointer ${item.time === slotTime ? 'bg-primary text-white' : 'text-gray-400 border border-gray-300'}`}
                       key={index}>
                        {item.time.toLowerCase()}
                    </p>
                ))}
            </div>
            <div className='flex justify-center'>
                <button onClick={BookAppointment}
                        className='bg-primary text-white text-sm font-light px-14 py-3 rounded-full my-6'>Book an
                    appointment
                </button>
            </div>
            {/*------------Listing Related Doctors-------------------*/}
            {docInfo && (
                <RelatedDoctors docId={docId} speciality={docInfo.speciality}/>
            )}        </div>
    );
};

export default Appointment;