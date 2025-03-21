import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchProjects, deleteProjectThunk } from '../actions/projectActions'; 
import './ProjectList.css';
import Header from './Header';
import { useNavigate } from 'react-router-dom';

const ProjectList = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const projects = useSelector((state) => state.projects.items);
  const projectStatus = useSelector((state) => state.projects.status);
  const error = useSelector((state) => state.projects.error);

  useEffect(() => {
    if (projectStatus === 'idle') {
      dispatch(fetchProjects());
    }
  }, [projectStatus, dispatch]);

  const handleUpdate = (projectId) => {
    navigate(`/editproject/${projectId}`);
  };

  const handleDelete = (projectId) => {
    dispatch(deleteProjectThunk(projectId));
  };

  const handleProjectClick = (projectId) => {
    navigate(`/taskDashboard/${projectId}`);
  };

  const handleHomeClick = () => {
    navigate('/dashboard');
  };

  const handleExportClick = () => {
    const token = localStorage.getItem('jwtToken');
    fetch('http://localhost:8092/report/projects/export', {
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    })
      .then((response) => response.blob())
      .then((blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'projects.xlsx';
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
      })
      .catch((error) => {
        console.error('Error exporting projects:', error);
      });
  };

  if (projectStatus === 'loading') {
    return <div>Loading...</div>;
  }

  if (projectStatus === 'failed') {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="landing-page">
      <Header />
      <button onClick={handleHomeClick} style={{ position: 'absolute', top: '10px', right: '110px', marginTop: '-0rem', backgroundColor: '#777696', fontWeight: 'bold', marginRight:'3rem' }}>
        <i className="fas fa-home"></i> Home
      </button>
      <button onClick={handleExportClick} style={{ position: 'absolute', top: '10px', right: '10px', marginTop: '-0rem', backgroundColor: 'green', fontWeight: 'bold' }}>
        <i className="fas fa-file-excel"></i> Export Data
      </button>
      <div className="project-list">
        <h1>Project List</h1>
        <table>
          <thead>
            <tr>
              <th>Project ID</th>
              <th>Project Name</th>
              <th>Project Description</th>
              <th>Start Date</th>
              <th>End Date</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {projects.map((project) => (
              <tr key={project.projectId}>
                <td>
                  <button onClick={() => handleProjectClick(project.projectId)} style={{ background: 'none', border: 'none', color: 'blue', textDecoration: 'underline', cursor: 'pointer' }}>
                    {project.projectId}
                  </button>
                </td>
                <td>{project.projectName}</td>
                <td>{project.projectDescription}</td>
                <td>{project.startDate}</td>
                <td>{project.endDate}</td>
                <td>
                  <button onClick={() => handleUpdate(project.projectId)}><i className="fas fa-edit"></i> Update</button>
                  <button onClick={() => handleDelete(project.projectId)}><i className="fas fa-trash-alt"></i> Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ProjectList;