import React from 'react';
import Header from './Header'; // Import the Header component
import './Login.css'; // Import the CSS file for styling

const Login = () => {
    const handleSubmit = (event) => {
        event.preventDefault(); // Prevent the default form submission
        // Handle login logic here (e.g., API call)
        console.log("Form submitted");
    };

    return (
        <div className="landing-page">
            <Header/>
            <div className="login-form">
                <h2>Log In</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="email">Email Address:</label>
                        <input type="email" id="email" name="email" required />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password:</label>
                        <input type="password" id="password" name="password" required />
                    </div>
                    <button type="submit" className="submit-button">Submit</button>
                </form>
            </div>
        </div>
    );
};

export default Login;


