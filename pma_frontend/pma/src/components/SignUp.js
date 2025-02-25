import React, { useState } from 'react';
import Header from './Header'; // Import your Header component
import { useNavigate } from 'react-router-dom';

const Signup = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [errors, setErrors] = useState({});
    const navigate = useNavigate();

    const validateName = (name) => {
        const nameRegex = /^[A-Z][a-zA-Z]*$/; // First letter uppercase
        return nameRegex.test(name);
    };

    const validateEmail = (email) => {
        return email.endsWith('@yash.com');
    };
    

    const validatePassword = (password) => {
        return password.length >= 6; // Minimum length for password
    };

    const handleNameChange = (e) => {
        const value = e.target.value;
        setName(value);
        if (!validateName(value)) {
            setErrors((prevErrors) => ({
                ...prevErrors,
                name: 'Name must start with an uppercase letter.',
            }));
        } else {
            setErrors((prevErrors) => ({
                ...prevErrors,
                name: undefined,
            }));
        }
    };

    const handleEmailChange = (e) => {
        const value = e.target.value;
        setEmail(value);
        if (!validateEmail(value)) {
            setErrors((prevErrors) => ({
                ...prevErrors,
                email: 'Email must be  end with @yash.com.',
            }));
        } else {
            setErrors((prevErrors) => ({
                ...prevErrors,
                email: undefined,
            }));
        }
    };

    const handlePasswordChange = (e) => {
        const value = e.target.value;
        setPassword(value);
        if (!validatePassword(value)) {
            setErrors((prevErrors) => ({
                ...prevErrors,
                password: 'Password must be at least 6 characters long.',
            }));
        } else {
            setErrors((prevErrors) => ({
                ...prevErrors,
                password: undefined,
            }));
        }
    };

    const handleConfirmPasswordChange = (e) => {
        const value = e.target.value;
        setConfirmPassword(value);
        if (value !== password) {
            setErrors((prevErrors) => ({
                ...prevErrors,
                confirmPassword: 'Passwords do not match.',
            }));
        } else {
            setErrors((prevErrors) => ({
                ...prevErrors,
                confirmPassword: undefined,
            }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const newErrors = {};
    
        if (!validateName(name)) {
            newErrors.name = 'Name must start with an uppercase letter.';
        }
        if (!validateEmail(email)) {
            newErrors.email = 'Email must be at least 8 characters long and end with @yash.com.';
        }
        if (!validatePassword(password)) {
            newErrors.password = 'Password must be at least 6 characters long.';
        }
        if (password !== confirmPassword) {
            newErrors.confirmPassword = 'Passwords do not match.';
        }
    
        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors);
            return; // Prevent further execution if there are validation errors
        }
    
        try {
            const response = await fetch('http://localhost:8092/user/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ name, email, password }), // Make sure formData is the correct object   
            });

            if(response.ok){
                navigate('/login')
            }
    
            if (!response.ok) {
                console.log(await response.json()); // Log the response for debugging
                navigate('/login');
                throw new Error('Signup failed');
            }
        } catch (error) {
            setErrors({ submit: 'Registration failed. Please try again.' });
        }
    };
    
    return (
        <div className="landing-page">
            <Header/>
           
            <form className="login-form" onSubmit={handleSubmit}>
            <h2>Signup</h2>
            <p>Create your account</p>
                <div className="form-group">
                    <label>Name:</label>
                    <input
                        type="text"
                        value={name}
                        onChange={handleNameChange}
                    />
                    {errors.name && <p style={{ color: 'red' }}>{errors.name}</p>}
                </div>
                <div className="form-group">
                    <label>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={handleEmailChange}
                    />
                    {errors.email && <p style={{ color: 'red' }}>{errors.email}</p>}
                </div>
                <div className="form-group">
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={handlePasswordChange}
                    />
                    {errors.password && <p style={{ color: 'red' }}>{errors.password}</p>}
                </div>
                <div className="form-group">
                    <label>Confirm Password:</label>
                    <input
                        type="password"
                        value={confirmPassword}
                        onChange={handleConfirmPasswordChange}
                    />
                    {errors.confirmPassword && <p style={{ color: 'red' }}>{errors.confirmPassword}</p>}
                </div>
                <button type="submit" className="submit-button">Sign Up</button>
            </form>
        </div>
    );
};

export default Signup;
