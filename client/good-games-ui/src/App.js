import { BrowserRouter as Router } from "react-router-dom";
import { Route, Routes } from 'react-router-dom';

import './App.css';
import Navbar from "./components/Navbar";
import Home from "./views/Home";
import SignUp from "./views/SignUp";

function App() {
  return (
    <Router>
      <Navbar/>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/signup" element={<SignUp/>}/>
      </Routes>
    </Router>
  );
}

export default App;
