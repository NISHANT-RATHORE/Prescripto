import React from 'react'
import Header from "../Components/Header.jsx";
import SpecialityMenu from "../Components/SpecialityMenu.jsx";
import TopDoctors from "../Components/TopDoctors.jsx";
import Banner from "../Components/Banner.jsx";

const Home = () => {
    return (
        <div>
            <Header/>
            <SpecialityMenu/>
            <TopDoctors/>
            <Banner/>
        </div>
    )
}
export default Home
