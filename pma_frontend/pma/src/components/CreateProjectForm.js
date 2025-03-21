import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from './Header';
import './CreateProjectForm.css';

const CreateProjectForm = () => {
    const [projectName, setProjectName] = useState('');
    const [projectDescription, setProjectDescription] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [estimatedEndDate, setEstimatedEndDate] = useState('');
    const navigate = useNavigate(); // Get the navigate function from React Router

    const projectDuration = 30; // Example duration in days

    const handleStartDateChange = (e) => {
        const start = e.target.value;
        setStartDate(start);
        calculateEstimatedEndDate(start, projectDuration);
    };

    const calculateEstimatedEndDate = (startDate, durationInDays) => {
        const start = new Date(startDate);
        start.setDate(start.getDate() + durationInDays);
        const estimatedEnd = start.toISOString().split('T')[0];
        setEstimatedEndDate(estimatedEnd);
        setEndDate(estimatedEnd);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const projectData = {
            projectName,
            projectDescription,
            startDate,
            endDate,
        };

        try {
            // Send POST request to create the project
            const response = await fetch('http://localhost:8092/projects/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`, // Add the JWT token
                },
                body: JSON.stringify(projectData),
            });

            // Check if the request was successful
            if (response.ok) {
                const createdProject = await response.json();
                console.log('Project created:', createdProject);
                navigate('/projectlist'); // Navigate to the dashboard
            } else {
                console.error('Project creation failed');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <div className="landing-page">
            <Header />
           
            <form className="create-project-form" onSubmit={handleSubmit}>
            <h1>Create Project Form</h1>
                <div className="form-group">
                    <label htmlFor="projectName">Project Name</label>
                    <input
                        type="text"
                        id="projectName"
                        placeholder="Project Name"
                        value={projectName}
                        onChange={(e) => setProjectName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="projectDescription">Project Description</label>
                    <textarea
                        id="projectDescription"
                        placeholder="Project Description"
                        value={projectDescription}
                        onChange={(e) => setProjectDescription(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="startDate">Start Date</label>
                    <input
                        type="date"
                        id="startDate"
                        value={startDate}
                        onChange={handleStartDateChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="endDate">End Date (Estimated)</label>
                    <input
                        type="date"
                        id="endDate"
                        value={endDate}
                        readOnly
                    />
                </div>
                <button type="submit">Create Project</button>
            </form>
            {estimatedEndDate && <p>Estimated End Date: {estimatedEndDate}</p>}
        </div>
    );
};

export default CreateProjectForm;
