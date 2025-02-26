import React from 'react';
import Header from './Header';
import './Dashboard.css';
import '@fortawesome/fontawesome-free/css/all.min.css';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
    const navigate = useNavigate();

    const handleCreateProjectClick = () => {
        navigate('/createproject');
    };
  return (
    <div className="landing-page">
      <Header />
    {/* <h6>Projects</h6> */}
      <button className="create-project-button" onClick={handleCreateProjectClick}>
        <i className="fas fa-plus-circle"></i> Create a Project
      </button>
      {/* <hr className="divider" /> */}
      <div className="dashboard-container">
        <div className="project-info">
          <h3>Spring / React Project</h3>
          <p>Project to create a Kanban Board with Spring Boot and React</p>
        </div>
        <div className="project-actions">
          <button className="project-board">
            <i className="fas fa-flag-checkered"></i> Project Board
          </button>
          <button className="project-update">
            <i className="fas fa-edit"></i> Update Project Info
          </button>
          <button className="project-delete">
            <i className="fas fa-minus-circle"></i> Delete Project
          </button>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
