import React, {useContext, useEffect, useState} from 'react';
import {assets} from '../assets/prescripto_assets/assets/assets_frontend/assets';
import {NavLink, useNavigate} from "react-router-dom";
import {AppContext} from "../context/AppContext.jsx";

const Navbar = () => {
    const navigate = useNavigate();
    const [showMenu, setShowMenu] = useState(false);
    const {token, setToken, userData} = useContext(AppContext);
    const [showDropdown, setShowDropdown] = useState(false);

    const logout = () => {
        navigate('/');
        setToken(false);
        localStorage.removeItem('token');
    };

    useEffect(() => {
        console.log(token);
        console.log(userData);
    }, []);

    const handleLogoutClick = () => {
        setShowDropdown(false);
        logout();
    };

    return (
        <div className='flex items-center justify-between text-sm py-4 mb-5 border-b border-b-gray-400'>
            <img onClick={() => navigate('/')} className='w-44 cursor-pointer' src={assets.logo} alt="logo"/>
            <ul className='hidden md:flex items-start gap-5 font-medium'>
                <NavLink to='/'>
                    <li className='py-1'><b>Home</b></li>
                    <hr className='border-none outline-none h-0.5 bg-primary w-3/5 m-auto hidden'/>
                </NavLink>

                <NavLink to='/doctors'>
                    <li className='py-1'><b>All Doctors</b></li>
                    <hr className='border-none outline-none h-0.5 bg-primary w-3/5 m-auto hidden'/>
                </NavLink>

                <NavLink to='/about'>
                    <li className='py-1'><b>About</b></li>
                    <hr className='border-none outline-none h-0.5 bg-primary w-3/5 m-auto hidden'/>
                </NavLink>

                <NavLink to='/contact'>
                    <li className='py-1'><b>Contact</b></li>
                    <hr className='border-none outline-none h-0.5 bg-primary w-3/5 m-auto hidden'/>
                </NavLink>
            </ul>
            <div className='flex items-center gap-4'>
                {token
                && userData
                    ? (
                        <div className='flex items-center gap-2 cursor-pointer relative'>
                            <img onClick={() => setShowDropdown(true)} className='w-8 rounded-full'
                                 src={userData.image ? userData.image : assets.upload_icon} alt="profile"/> <img className='w-2.5'
                                                                           src={assets.dropdown_icon}
                                                                           alt="drop-down"/>
                            {showDropdown && (
                                <div className='absolute top-0 right-0 pt-14 text-base font-medium text-gray-600 z-20'>
                                    <div className='min-w-48 bg-stone-100 rounded flex flex-col gap-4 p-4'>
                                        {/* <p onClick={() => navigate('/my-profile')} className='hover:text-black cursor-pointer'>My Profile</p>
                                    <p onClick={() => navigate('/my-appointments')} className='hover:text-black cursor-pointer'>My Appointments</p>
                                    <p onClick={logout} className='hover:text-black cursor-pointer'>Logout</p> */}
                                        <NavLink onClick={() => setShowDropdown(false)} to='/my-profile'
                                                 className='hover:text-black cursor-pointer'>My Profile</NavLink>
                                        <NavLink onClick={() => setShowDropdown(false)} to='/my-appointments'
                                                 className='hover:text-black cursor-pointer'>My Appointments</NavLink>
                                        <NavLink to='/' onClick={handleLogoutClick}
                                                 className='hover:text-black cursor-pointer'>Logout</NavLink>
                                    </div>
                                </div>
                            )}
                        </div>
                    ) : (
                        <button onClick={() => navigate('/login')}
                                className='bg-primary text-white px-8 py-3 rounded-full font-light hidden md:block'>
                            Create Account
                        </button>
                    )}
                <img onClick={() => setShowMenu(true)} className='w-6 md:hidden' src={assets.menu_icon} alt="menu"/>
                {/* --------Mobile menu--------------- */}
                <div
                    className={`${showMenu ? 'fixed w-full' : 'h-0 w-0'} md:hidden right-0 top-0 bottom-0 z-20 overflow-hidden bg-white transition-all`}>
                    <div className='flex items-center justify-center px-5 py-6'>
                        <img className='w-36' src={assets.logo} alt="logo"/>
                        <img className='w-7' onClick={() => setShowMenu(false)} src={assets.cross_icon} alt="close"/>
                    </div>
                    <ul className='flex flex-col items-center gap-2 mt-5 px-5 text-lg font-medium'>
                        {!token && (
                            <NavLink onClick={() => setShowMenu(false)} to='/login'>
                                <p className='px-4 py-2 rounded inline-block'>Create Account</p>
                            </NavLink>
                        )}
                        <NavLink onClick={() => setShowMenu(false)} to='/'><p
                            className='px-4 py-2 rounded inline-block'>Home</p></NavLink>
                        <NavLink onClick={() => setShowMenu(false)} to='/doctors'><p
                            className='px-4 py-2 rounded inline-block'>All Doctors</p></NavLink>
                        <NavLink onClick={() => setShowMenu(false)} to='/about'><p
                            className='px-4 py-2 rounded inline-block'>About</p></NavLink>
                        <NavLink onClick={() => setShowMenu(false)} to='/contact'><p
                            className='px-4 py-2 rounded inline-block'>Contact</p></NavLink>
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default Navbar;