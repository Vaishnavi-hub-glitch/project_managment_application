import React from 'react';
import './Header.css'; 
import { Link } from 'react-router-dom';

const Header = ({ onLogin, onSignup }) => {
    return (
        <nav className="navbar">
            <div className="nav-left">
                <h2>Personal Project Management </h2>
                <Link className="link" to="/landingpage">
                  Dashboard
                </Link>
               
            </div>
            {/* <div className="nav-right">
                 <Link to="/signup" className="signup_login_link">Sign Up</Link> 
                 <Link to="/login" className="signup_login_link">Login</Link> 
                 </div> */}
        </nav>
    );
};

export default Header;