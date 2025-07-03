import React, {useContext, useEffect} from 'react';
import {AdminContext} from '../../Context/AdminContext.jsx';
import {AppContext} from '../../Context/AppContext.jsx';

const AllAppointments = () => {
    const {aToken, appointments = [], getAllAppointment} = useContext(AdminContext);
    const {calculateAge, slotDateFormat, currency} = useContext(AppContext);

    useEffect(() => {
        if (aToken) {
            getAllAppointment();
        }
    }, [aToken]);

    return (
        <div className="w-full max-w-6xl m-5">
            <p className="mb-3 text-lg font-medium">All Appointments</p>
            <div className="bg-white border rounded text-sm max-h-[80vh] overflow-y-scroll">
                <div
                    className="hidden sm:grid grid-cols-[0.5fr_3fr_1fr_3fr_3fr_1fr_1fr] grid-flow-col py-3 px-6 border-b">
                    <p>#</p>
                    <p>Patient</p>
                    <p>Age</p>
                    <p>Date & Time</p>
                    <p>Doctor</p>
                    <p>Fees</p>
                    <p>Status</p>
                </div>

                {appointments.map((item, index) => (
                    <div
                        className="flex flex-wrap justify-between max-sm:gap-2 sm:grid sm:grid-cols-[0.5fr_3fr_1fr_3fr_3fr_1fr_1fr] items-center text-gray-500 py-3 px-6 border-b hover:bg-gray-50"
                        key={item.appointmentId}
                    >
                        <p className="max-sm:hidden">{index + 1}</p>
                        <div className="flex items-center gap-2">
                            <img className="w-8 rounded-full" src={item.patientData.image} alt="Patient"/>
                            <p>{item.patientData.name}</p>
                        </div>
                        <p className="max-sm:hidden">{calculateAge(item.patientData.gender)}</p>
                        <p>{slotDateFormat(item.slotDate)}, {item.slotTime}</p>
                        <div className="flex items-center gap-2">
                            <img className="w-8 rounded-full bg-gray-200" src={item.doctorData.docImg} alt="Doctor"/>
                            <p>{item.doctorData.name}</p>
                        </div>
                        <p>{currency}{item.amount}</p>
                        <p className={item.cancelled ? 'text-red-400' : 'text-green-400'}>
                            {item.cancelled ? 'Cancelled' : 'Active'}
                        </p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default AllAppointments;