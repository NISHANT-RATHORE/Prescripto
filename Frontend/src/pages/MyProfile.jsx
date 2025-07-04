// eslint-disable-next-line no-unused-vars
import React, {useState, useContext} from 'react';
import {AppContext} from '../context/AppContext.jsx';
import {assets} from '../assets/prescripto_assets/assets/assets_frontend/assets';
import {toast} from 'react-toastify';
import axios from 'axios';

const MyProfile = () => {
    const {userData, setUserData, token, backend_url, loadUserProfileData} = useContext(AppContext);
    const [isEdit, setIsEdit] = useState(false);
    const [image, setImage] = useState(null);

    const updateUserData = async () => {
        try {
            const formData = new FormData();

            // Only append non-null and non-empty fields
            if (userData.name) formData.append('name', userData.name);
            if (userData.phone) formData.append('phone', userData.phone);
            if (userData.address1) formData.append('address1', userData.address1);
            if (userData.address2) formData.append('address2', userData.address2);
            if (userData.gender) formData.append('gender', userData.gender);
            if (userData.dob) formData.append('dob', userData.dob);
            if (image) formData.append('image', image);

            const response = await axios.put(`${backend_url}/patient/updateProfile`, formData, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
                params: {
                    username: userData.email, // Correctly formatted params object
                },
            });

            if (response.status === 200) {
                toast.success('Profile data updated successfully');
                await loadUserProfileData();
                setIsEdit(false);
                setImage(null);
            } else {
                toast.error('Failed to update profile data');
            }
        } catch (error) {
            console.error(error);
            toast.error("Failed to update profile data");
        }
    };

    return userData && (
        <div className='max-w-lg flex flex-col gap-2 text-sm'>
            {isEdit ? (
                <label htmlFor='image'>
                    <div className='inline-block relative cursor-pointer'>
                        <img className='w-36 rounded opacity-75'
                             src={image ? URL.createObjectURL(image) : userData.image} alt="Profile"/>
                        <img className='w-10 absolute bottom-12 right-12' src={image ? '' : assets.upload_icon}
                             alt="Upload Icon"/>
                    </div>
                    <input onChange={(e) => setImage(e.target.files[0])} type="file" id="image" hidden/>
                </label>
            ) : (
                <img className='w-36 rounded' src={userData.image} alt="Profile"/>
            )}

            {isEdit ? (
                <input
                    className='bg-gray-50 text-3xl font-medium max-w-60 mt-4'
                    type="text"
                    value={userData.name}
                    onChange={(e) => setUserData(prev => ({...prev, name: e.target.value}))}
                />
            ) : (
                <p className='font-medium text-3xl text-neutral-800 mt-4'>{userData.name}</p>
            )}

            <hr className='bg-zinc-400 h-[1px] border-none'/>

            <div>
                <p className='text-neutral-500 underline mt-3'>CONTACT INFORMATION</p>
                <div className='grid grid-cols-[1fr_3fr] gap-y-2.5 mt-3 text-neutral-700'>
                    <p className='font-medium'>Email id:</p>
                    <p className='text-blue-500'>{userData.email}</p>

                    <p className='font-medium'>Phone:</p>
                    {isEdit ? (
                        <input
                            className='bg-gray-100 max-w-52'
                            type="text"
                            value={userData.phone || ''}
                            onChange={(e) => setUserData(prev => ({...prev, phone: e.target.value}))}
                        />
                    ) : (
                        <p className='text-blue-500'>{userData.phone}</p>
                    )}

                    <p className='font-medium'>Address</p>
                    {isEdit ? (
                        <p>
                            <input
                                className='bg-gray-50'
                                onChange={(e) => setUserData(prev => ({...prev, address1: e.target.value}))}
                                value={userData.address1 || ''}
                                type="text"
                            />
                            <br/>
                            <input
                                className='bg-gray-50'
                                onChange={(e) => setUserData(prev => ({...prev, address2: e.target.value}))}
                                value={userData.address2 || ''}
                                type="text"
                            />
                        </p>
                    ) : (
                        <p className='text-gray-500'>
                            {userData.address1}
                            <br/>
                            {userData.address2}
                        </p>
                    )}
                </div>
            </div>

            <div>
                <p className='text-neutral-500 underline mt-3'>BASIC INFORMATION</p>
                <div className='grid grid-cols-[1fr_3fr] gap-y-2.5 mt-3 text-neutral-700'>
                    <p className='font-medium'>GENDER</p>
                    {isEdit ? (
                        <select
                            className='max-w-20 bg-gray-100'
                            onChange={(e) => setUserData(prev => ({...prev, gender: e.target.value}))}
                            value={userData.gender || ""}>
                            <option value="">Select Gender</option>
                            {/* Optional empty selection */}
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                        </select>
                    ) : (
                        <p className='text-gray-400'>{userData.gender}</p>
                    )}

                    <p className='font-medium'>Birthday:</p>
                    {isEdit ? (
                        <input
                            className='max-w-28 bg-gray-100'
                            type="date"
                            value={userData.dob}
                            onChange={(e) => setUserData(prev => ({...prev, dob: e.target.value}))}
                        />
                    ) : (
                        <p className='text-gray-400'>{userData.dob}</p>
                    )}
                </div>
            </div>

            <div className='mt-10'>
                {isEdit ? (
                    <button
                        className='border border-primary px-8 py-2 rounded-full hover:bg-primary hover:text-white transition-all'
                        onClick={updateUserData}
                    >
                        Save Information
                    </button>
                ) : (
                    <button
                        className='border border-primary px-8 py-2 rounded-full hover:bg-primary hover:text-white transition-all'
                        onClick={() => setIsEdit(true)}
                    >
                        Edit
                    </button>
                )}
            </div>
        </div>
    );
};

export default MyProfile;


