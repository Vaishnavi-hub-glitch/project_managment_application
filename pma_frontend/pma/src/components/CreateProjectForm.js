import React, { useState } from 'react';
import Header from './Header';
import './CreateProjectForm.css';

const CreateProjectForm = () => {
    const [projectName, setProjectName] = useState('');
    const [projectDescription, setProjectDescription] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        // Handle form submission logic here
        console.log('Project Name:', projectName);
        console.log('Project Description:', projectDescription);
        console.log('Start Date:', startDate);
        console.log('End Date:', endDate);
    };

    return (
        <div className="landing-page">
            <Header />
            <h2>Add Project</h2>
            <form className="create-project-form" onSubmit={handleSubmit}>
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
                        onChange={(e) => setStartDate(e.target.value)} 
                        required 
                    />
                </div>
                <div className="form-group">
                <label htmlFor="endDate">End Date</label>
                    <input 
                        type="date" 
                        id="endDate" 
                        value={endDate} 
                        onChange={(e) => setEndDate(e.target.value)} 
                        required 
                    />
                </div>
                <button type="submit">Create Project</button>
            </form>
        </div>
    );
};

export default CreateProjectForm;
