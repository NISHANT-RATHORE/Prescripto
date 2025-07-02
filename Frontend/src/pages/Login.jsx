import React, {useContext, useEffect, useState} from 'react';
import axios from 'axios';
import {AppContext} from "../context/AppContext.jsx";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

const Login = () => {
    const [state, setState] = useState('Sign Up'); // Ensure initial state is 'Sign Up'
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const {backend_url, token, setToken} = useContext(AppContext)
    const navigate = useNavigate();

    const onSubmitHandler = async (event) => {
        event.preventDefault();
        let response;
        try {
            if (state === 'Sign Up') {
                response = await axios.post(`${backend_url}/auth/patient/sign-up`, {username, password, name}, {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
                if (response.status === 200) {
                    // set state to login after successful registration
                    setState('login');
                    navigate('/login')
                    toast.success("User registered successfully")
                    console.log('User registered successfully');
                } else {
                    toast.error('User registration failed')
                }
            } else {
                const userData = {username, password}
                // const authHeader = `Basic ${btoa(`${username}:${password}`)}`
                response = await axios.post(`${backend_url}/auth/patient/login`, userData, {
                    headers: {
                        'Content-Type': 'application/json',
                        // 'Authorization': authHeader
                    },
                });
                if(response.status === 200){
                    localStorage.setItem('token', response.data.accessToken)
                    setToken(response.data.accessToken)
                    toast.success("User logged in successfully")
                } else {
                    toast.error('User login failed')
                }
            }
        } catch (error) {
            console.error('There was an error!', error);
            if (error.response && error.response.status === 401) {
                toast.error('An error occurred, please try again later');
            } else {
                toast.error('An error occurred, please try again later');
            }
    }}

    useEffect(() => {
        if (token) {
            navigate('/')
        }
    }, [token]);

    return (
        <form className='min-h-[80vh] flex items-center' onSubmit={onSubmitHandler}>
            <div
                className='flex flex-col gap-3 m-auto items-start p-8 min-w-[340px] sm:min-w-96 border rounded-xl text-[#5E5E5E] text-sm shadow-lg'>
                <p className='text-2xl font-semibold'>
                    {state === 'Sign Up' ? "Create Account" : "Login"}
                </p>
                <p>
                    Please {state === 'Sign Up' ? "sign up" : "log in"} to book appointment
                </p>

                {
                    state === 'Sign Up' && <div className='w-full '>
                        <p>Full Name</p>
                        <input className='border border-[#DADADA] rounded w-full p-2 mt-1' type="text" value={name}
                               onChange={(e) => setName(e.target.value)} required/>
                    </div>
                }

                {
                    state === 'Sign Up' && <div className='w-full '>
                        <p>Email</p>
                        <input className='border border-[#DADADA] rounded w-full p-2 mt-1' type="email" value={username}
                               onChange={(e) => setUsername(e.target.value)} required/>
                    </div>
                }

                {
                    state === 'login' && <div className='w-full '>
                        <p>Username</p>
                        <input className='border border-[#DADADA] rounded w-full p-2 mt-1' type="text" value={username}
                               onChange={(e) => setUsername(e.target.value)} required/>
                    </div>
                }

                <div className='w-full'>
                    <p>Password</p>
                    <input className='border border-[#DADADA] rounded w-full p-2 mt-1' type="password" value={password}
                           onChange={(e) => setPassword(e.target.value)} required/>
                </div>
                <button type="submit"
                    className='bg-primary text-white w-full py-2 my-2 rounded-md text-base'>{state === 'Sign Up' ? "Create Account" : "Login"}</button>
                {
                    state === 'Sign Up' ?
                        <p>Already have an account? <span onClick={() => setState('login')}
                                                          className='text-primary cursor-pointer underline'>Login Here</span>
                        </p> : <p>
                            Create a new account <span onClick={() => setState('Sign Up')}
                                                       className='text-primary cursor-pointer underline'>Sign Up</span>
                        </p>
                }
            </div>
        </form>
    );
};

export default Login;