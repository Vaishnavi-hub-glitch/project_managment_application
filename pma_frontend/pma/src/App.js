import './App.css';
import LandingPage from './components/LandingPage';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import Signup from './components/SignUp';


function App() {
  return (
    <Router>
      <Routes>
       <Route path="/" element={<LandingPage />} /> 
       <Route path='/login' element={<Login/>} />
       <Route path='/signup' element={<Signup/>}/>
       <Route path='/landingpage' element={<LandingPage/>}/>
      
      </Routes>
    </Router>
  );
}

export default App;
