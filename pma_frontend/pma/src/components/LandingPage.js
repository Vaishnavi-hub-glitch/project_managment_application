import React from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import Header from './Header'; 
import './LandingPage.css';

const LandingPage = () => {
    const navigate = useNavigate(); // Initialize useNavigate

    const handleLogin = () => {
        navigate('/login'); // Navigate to the login page
    };

    const handleSignup = () => {
        navigate('/signup'); // Navigate to the signup page
    };

    return (
        <div className="landing-page">
            <Header/>
            <div className="content">
                <h1>Personal Project Management Tool</h1>
                <div className='h4'>
                    <h4>Create your account to join active projects or start your own</h4>
                </div>
                    <div className="button">
                    <button  onClick={handleSignup}>Sign Up</button>
                    <button className='login_button' onClick={handleLogin}>Login</button>
                </div>
            </div>
        </div>
    );
};

export default LandingPage;
