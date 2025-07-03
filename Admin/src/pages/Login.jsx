import React, { useContext, useState } from 'react';
import axios from 'axios';
import { AdminContext } from '../Context/AdminContext.jsx';
import { toast } from 'react-toastify';

const Login = () => {
    const [State, setState] = useState('Admin');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const { setAToken } = useContext(AdminContext);
    const backendUrl = import.meta.env.VITE_BACKEND_URL;



    const onSubmitHandler = async (event) => {
        event.preventDefault();
        const userData = { username, password };
        console.log(backendUrl)
        try {
            const response = await axios.post(`${backendUrl}/auth/admin/login`, userData, {
                headers: {
                    'Content-Type': 'application/json',
                    // 'Authorization': authHeader,
                },
            });
            console.log(response.data.accessToken)
            if (response.status === 200) {
                localStorage.setItem('token', response.data.accessToken);
                setAToken(response.data.accessToken);
                toast.success('Login Successful');
            }
        } catch (error) {
            toast.error('Login Failed');
        }
    };

    return (
        <form onSubmit={onSubmitHandler} className='min-h-[80vh] flex items-center'>
            <div className='flex flex-col gap-3 m-auto items-center p-8 min-w-[340px] sm:min-w-96 border rounded-xl text-[#5E5E5E] text-sm shadow-lg'>
                <p className='text-2xl font-semibold m-auto'>
                    <span className='text-primary'>{State}</span> Login
                </p>
                <div className='w-full'>
                    <p>Email</p>
                    <input
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className='border border-[#DADADA] rounded w-full p-2 mt-1'
                        type='text'
                        required
                    />
                </div>
                <div className='w-full'>
                    <p>Password</p>
                    <input
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className='border border-[#DADADA] rounded w-full p-2 mt-1'
                        type='password'
                        required
                    />
                </div>
                <button className='bg-primary text-white w-full py-2 rounded-md text-base'>Login</button >
            </div>
        </form>
    );
};

export default Login;