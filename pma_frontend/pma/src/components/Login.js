import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, Link } from 'react-router-dom';
import Swal from 'sweetalert2';

import Header from './Header';
import { login } from '../actions/authActions';

import './Login.css';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const authStatus = useSelector((state) => state.auth.status);
  
  const handleSubmit = async (event) => {
    event.preventDefault();
    const result = await dispatch(login({ email, password }));
    
    if (result.meta.requestStatus === 'fulfilled') {
      const userRole = result.payload.role;
      const projectId = result.payload.projectId; // Get dynamic project ID
      
      if (userRole === "MANAGER") {
        navigate('/dashboard'); // Redirect to manager dashboard
      } else if (userRole === "DEVELOPER") {
        if (projectId) {
          navigate(`/taskDashboard/${projectId}`); // Redirect to task dashboard with dynamic project ID
        } else {
          Swal.fire({
            title: 'Project Not Assigned',
            text: 'No project ID found for the user.',
            icon: 'warning',
            confirmButtonText: 'OK',
          });
        }
      } else {
        Swal.fire({
          title: 'Role Undefined',
          icon: 'info',
          confirmButtonText: 'OK',
        });
      }
    } else if (result.meta.requestStatus === 'rejected') {
      Swal.fire({
        title: 'Login Failed',
        text: 'Invalid username or password. Please try again.',
        icon: 'error',
        confirmButtonText: 'OK',
      });
    }
  };
    
  return (
    <div className="landing-page">
      <Header />
      <div className="login-form">
        <h1>Log In</h1>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <input
              type="email"
              id="email"
              name="email"
              placeholder="Email Address"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              id="password"
              name="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="submit-button">Submit</button>
        </form>
        <div className="register-link">
          <p>Not registered yet? <Link to="/signup">Create an account</Link></p>
        </div>
      </div>
    </div>
  );
};

export default Login;