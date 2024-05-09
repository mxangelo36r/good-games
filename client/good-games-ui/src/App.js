import { BrowserRouter as Router } from "react-router-dom";
import { Route, Routes } from 'react-router-dom';

import './App.css';
import Navbar from "./components/Navbar";
import Home from "./views/Home";
import SignUp from "./views/SignUp";
import Login from "./views/Login";
import { AuthProvider } from "./contexts/AuthContext";
import GameSearch from "./views/GameSearch";
import Game from "./views/Game";
import ReviewedGames from "./views/ReviewedGames";
import NotFound from "./views/NotFound";

function App() {
  return (
    <Router>
      <AuthProvider>
        <Navbar/>
        <Routes>
          <Route path="/" element={<Home/>}/>
          <Route path="/signup" element={<SignUp/>}/>
          <Route path="/login" element={<Login/>}/>
          <Route path="/gameSearch/:query" element={<GameSearch/>}/>
          <Route path="/game/:id" element={<Game/>}/>
          <Route path="/games/reviewed" element={<ReviewedGames />}/>
          <Route path="*" element={<NotFound />}/>
        </Routes>
      </AuthProvider>
    </Router>
  );
}

export default App;
