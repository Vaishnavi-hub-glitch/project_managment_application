import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import Swal from 'sweetalert2';
import Header from './Header';
import { fetchUserNames } from '../actions/projectActions';
import './CreateProjectForm.css';
import {updateProjectThunk} from '../actions/projectActions';

const EditProjectForm = () => {
    const [projectName, setProjectName] = useState('');
    const [projectDescription, setProjectDescription] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [estimatedEndDate, setEstimatedEndDate] = useState('');
    const [selectedUserIds, setSelectedUserIds] = useState([]);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { projectId } = useParams();
    const userNames = useSelector(state => state.projects.userNames);

    const projectDuration = 30;

    useEffect(() => {
        fetchProjectDetails();
        dispatch(fetchUserNames());
    }, [dispatch, projectId]);

    const fetchProjectDetails = async () => {
        try {
            const response = await fetch(`http://localhost:8092/projects/list/${projectId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`,
                },
            });

            if (response.ok) {
                const project = await response.json();
                setProjectName(project.projectName);
                setProjectDescription(project.projectDescription);
                setStartDate(formatDate(project.startDate));
                setEndDate(formatDate(project.endDate));
                setEstimatedEndDate(formatDate(project.endDate));
                setSelectedUserIds(project.userList || []);
            } else {
                console.error('Failed to fetch project details');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

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

    const handleUserSelectionChange = (userId) => {
        setSelectedUserIds((prevIds) => {
            if (prevIds.includes(userId)) {
                return prevIds.filter((id) => id !== userId);
            } else {
                return [...prevIds, userId];
            }
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const projectData = {
            projectId : projectId, 
            projectName,
            projectDescription,
            startDate,
            endDate,
            userList: selectedUserIds,
        };

        try {
            const response = dispatch(updateProjectThunk(projectData))

            if (response) {
                const updatedProject = response;
                console.log('Project updated:', updatedProject);

                Swal.fire({
                    title: 'Success!',
                    text: 'Project updated successfully.',
                    icon: 'success',
                    confirmButtonText: 'OK'
                }).then(() => {
                    fetchProjectDetails();
                    navigate('/projectlist');
                });
            } else {
                console.error('Project update failed');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const formatDate = (dateString) => {
        if (!dateString) return '';
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    };

    return (
        <div className="landing-page">
            <Header />
            <form className="create-project-form" onSubmit={handleSubmit}>
                <h2>Create/Edit Project Form</h2>
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
                <div className="form-group">
    <label htmlFor="userName">Assign Users</label>
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '10px', marginTop: '10px' }}>
        {userNames.map(user => (
            <div
                key={user.userId}
                style={{
                    display: 'flex',
                    alignItems: 'center',
                    border: '1px solid #ddd',
                    padding: '10px',
                    borderRadius: '5px',
                    backgroundColor: '#f9f9f9',
                    transition: 'background-color 0.3s ease, box-shadow 0.3s ease',
                    cursor: 'pointer'
                }}
                onMouseEnter={(e) => {
                    e.currentTarget.style.backgroundColor = '#e6f7ff';
                    e.currentTarget.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.1)';
                }}
                onMouseLeave={(e) => {
                    e.currentTarget.style.backgroundColor = '#f9f9f9';
                    e.currentTarget.style.boxShadow = 'none';
                }}
            >
                <label style={{ cursor: 'pointer', display: 'flex', alignItems: 'center', fontWeight: 'bold', color: '#333' }}>
                    <input
                        type="checkbox"
                        value={user.userId}
                        checked={selectedUserIds.includes(user.userId)}
                        onChange={() => handleUserSelectionChange(user.userId)}
                        style={{ marginRight: '10px' }}
                    />
                    <span>{user.name}</span>
                </label>
            </div>
        ))}
    </div>
</div>

                <button type="submit">Update Project</button>
                <button type="button" onClick={() => navigate('/projectlist')}>Cancel</button>
            </form>
            {estimatedEndDate && <p>Estimated End Date: {estimatedEndDate}</p>}
        </div>
    );
};

export default EditProjectForm;