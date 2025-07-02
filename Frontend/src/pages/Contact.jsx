import React from 'react';
import { assets } from "../assets/prescripto_assets/assets/assets_frontend/assets.js";

const Contact = () => {
    return (
        <div className='text-center text-2xl pt-10 text-gray-500'>
            <div>CONTACT <span className='text-gray-700 font-semibold'>US</span></div>
            <div className='my-10 flex flex-col justify-center md:flex-row gap-10 mb-28 text-sm'>
                <img className='w-full md:max-w-[360px]' src={assets.contact_image} alt="Contact" />
                <div className='flex flex-col justify-center items-start gap-6 font-semibold'>
                    <p className='font-semibold text-lg text-gray-600 '>OUR OFFICE</p>
                    <p className='text-gray-500 text-left'> 00000 Willms Station <br />Suite 000, Washington, USA</p>
                    <p className='text-gray-500 text-left'>Tel: 7983546134 <br />Email: nishantrathore2002@gmail.com</p>
                    <p className='font-semibold text-lg text-gray-600 text-left'>CAREERS AT PRESCRIPTO</p>
                    <p className='text-gray-500 text-left'>Learn more about our teams and job openings.</p>
                    <button className='border border-black px-8 py-4 text-sm hover:bg-black hover:text-white transition-all duration-500' >Explore Jobs</button>
                </div>
            </div>
        </div>
    );
};

export default Contact;