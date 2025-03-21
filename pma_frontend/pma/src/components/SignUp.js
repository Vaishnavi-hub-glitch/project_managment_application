import React, { useState } from 'react';
import Header from './Header'; 
import { useNavigate, Link } from 'react-router-dom';
import Swal from 'sweetalert2'; // Import SweetAlert2 for notifications

const Signup = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [errors, setErrors] = useState({});
    const navigate = useNavigate();

    const validateName = (name) => {
        const nameRegex = /^[A-Z][a-zA-Z]*$/; 
        return nameRegex.test(name);
    };

    const validateEmail = (email) => {
        return email.endsWith('@yash.com');
    };
    
    const validatePassword = (password) => {
        return password.length >= 6; 
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
                email: 'Email must end with @yash.com.',
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
            newErrors.email = 'Email must end with @yash.com.';
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
                body: JSON.stringify({ name, email, password }),   
            });

            if (response.ok) {
                Swal.fire({
                    title: 'Success!',
                    text: 'You have successfully registered.',
                    icon: 'success',
                    confirmButtonText: 'OK',
                }).then(() => {
                    navigate('/login'); // Redirect to login page after success
                });
            } else {
                console.log(await response.json()); // Log the response for debugging
                throw new Error('Signup failed');
            }
        } catch (error) {
            setErrors({ submit: 'Registration failed. Please try again.' });
            Swal.fire({
                title: 'Error!',
                text: 'Registration failed. Please try again.',
                icon: 'error',
                confirmButtonText: 'OK',
            });
        }
    };
    
    return (
        <div className="landing-page">
            <Header />
            <form className="login-form" onSubmit={handleSubmit}>
                <h1>Signup</h1>
                <p>Create your account</p>
                <div className="form-group">
                    <input
                        type="text"
                        value={name}
                        placeholder='Name'
                        onChange={handleNameChange}
                    />
                    {errors.name && <p style={{ color: 'red' }}>{errors.name}</p>}
                </div>
                <div className="form-group">
                    <input
                        type="email"
                        value={email}
                        placeholder='Email'
                        onChange={handleEmailChange}
                    />
                    {errors.email && <p style={{ color: 'red' }}>{errors.email}</p>}
                </div>
                <div className="form-group">
                    <input
                        type="password"
                        value={password}
                        placeholder='Password'
                        onChange={handlePasswordChange}
                    />
                    {errors.password && <p style={{ color: 'red' }}>{errors.password}</p>}
                </div>
                <div className="form-group">
                    <input
                        type="password"
                        value={confirmPassword}
                        placeholder='Confirm Password'
                        onChange={handleConfirmPasswordChange}
                    />
                    {errors.confirmPassword && <p style={{ color: 'red' }}>{errors.confirmPassword}</p>}
                </div>
                <button type="submit" className="submit-button">Sign Up</button>
                {errors.submit && <p style={{ color: 'red' }}>{errors.submit}</p>}
                <div className="login-link">
                    <p>Already have an account? <Link to="/login">Login here</Link></p>
                </div>
            </form>
        </div>
    );
};

export default Signup;