import './App.css';
import LandingPage from './components/LandingPage';
import { Routes, Route } from 'react-router-dom'; // Correct import
import Login from './components/Login';
import Signup from './components/SignUp';
import Dashboard from './components/Dashboard';
import CreateProjectForm from './components/CreateProjectForm';
import EditProjectForm from './components/EditProjectForm';
import ProtectedRoute from './components/ProtectRoute';
import ProjectList from './components/ProjectList';
import TaskDashboard from './components/TaskDashboard'
import CreateTask from './components/CreateTask';
import UpdateTask from './components/UpdateTask';

function App() {
  return (
    <Routes>
      <Route path="/" element={<LandingPage />} /> 
      <Route path='/login' element={<Login />} />
      {/* <Route path="/dashboard" element={<ProtectedRoute />}>
        <Route path="" element={<Dashboard />} />
      </Route> */}
      <Route path='/signup' element={<Signup />} />
      {/* <Route path='/dashboard' element={<Dashboard />} /> */}

      <Route
        path="/dashboard"
        element={<ProtectedRoute><Dashboard /></ProtectedRoute>}/>

      <Route path='/landingpage' element={<LandingPage />} />

      <Route path='/createproject' element={<CreateProjectForm />} />
      <Route path='/editproject/:projectId' element={<EditProjectForm />} />
      <Route path='/updatetask/:taskId' element={<UpdateTask/>} />
      <Route path='/projectlist' element={<ProjectList />} />
      <Route path="/taskDashboard/:projectId" element={<TaskDashboard />} />
      <Route path='/:projectId/createtask' element={<CreateTask/>} />
    </Routes>
  );
}

export default App;
