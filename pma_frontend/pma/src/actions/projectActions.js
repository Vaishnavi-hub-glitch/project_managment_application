import { createAsyncThunk } from '@reduxjs/toolkit';

// Fetch Projects
export const fetchProjects = createAsyncThunk(
  'projects/fetchProjects',
  async () => {
    const response = await fetch('http://localhost:8092/projects/list', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`, // Add the JWT token
      },
    });

    if (!response.ok) {
      throw new Error('Failed to fetch projects');
    }

    const projects = await response.json();
    return projects;
  }
);

// Update Project
export const updateProjectThunk = createAsyncThunk(
  'projects/updateProject',
  async (updatedProject) => {
    const response = await fetch(`http://localhost:8092/projects/update/${updatedProject.projectId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`, // Add the JWT token
      },
      body: JSON.stringify(updatedProject),
    });

    if (!response.ok) {
      throw new Error('Failed to update project');
    }

    return response.json();
  }
);

// Delete Project
export const deleteProjectThunk = createAsyncThunk(
  'projects/deleteProject',
  async (projectId) => {
    const response = await fetch(`http://localhost:8092/projects/delete/${projectId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`, // Add the JWT token
      },
    });

    if (!response.ok) {
      throw new Error('Failed to delete project');
    }

    return projectId;
  }
);

// Fetch User Names and IDs
export const fetchUserNames = createAsyncThunk(
  'users/fetchUserNames',
  async () => {
    const response = await fetch('http://localhost:8092/user/list', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`, // Add the JWT token
      },
    });

    if (!response.ok) {
      throw new Error('Failed to fetch user names');
    }

    const users = await response.json();
    return users.map(user => ({ userId: user.userId, name: user.name })); // Return both userId and name
  }
);