import { Link, useNavigate } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import { useState } from "react";
// import  logo  from '../assets/logo.png'

function Navbar() {
    const [query, setQuery] = useState("");

    const { isLoggedIn, signOut } = useAuth();
    const navigate = useNavigate();

    const handleChange = (event) => {
        setQuery(event.target.value);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        
        navigate(`gameSearch/${query}`)
    }

    const handleSignOut = () => {
        signOut();
        navigate(`/`);
    }

    const renderUserAuthentication = () => {
        // TODO: add user authentication
        if (!isLoggedIn()) {
            return (
                <>
                    <li className="nav-item">
                        <Link to={'/signup'} className="nav-link text-nowrap pixel">Sign Up</Link>
                    </li>
                    <li>
                        <Link to={'/login'} className="nav-link text-nowrap pixel">Log In</Link>
                    </li>
                </>
            )
        } else {
            return (
                <>
                    <li className="nav-item">
                        <a className="nav-link text-nowrap pixel" href="#" onClick={handleSignOut}>Sign Out</a>
                    </li>
                </>
            ) 
        }
    }

    return (
        <>
            <nav className="navbar navbar-expand-lg shadow">
                <div className="container-fluid">
                    <Link to={'/'} className="navbar-brand me-5"><img src="/logo.png" className="logoo" /></Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarContent">
                        <ul className="navbar-nav mb-2 mb-lg-0 me-5">
                            <li className="nav-item ">
                                <Link to={'/games/reviewed'} className="nav-link text-nowrap pixel">See Reviewed Games</Link>
                            </li>
                        </ul>
                        <form className="d-flex w-100 ms-auto me-4 outlined" role="search" onSubmit={handleSubmit}>
                            <div className="input-group">
                                <input
                                id="query"
                                name="query"
                                type="search"
                                className="form-control"
                                placeholder="Find Games"
                                aria-label="Search"
                                aria-describedby="btn-search"
                                value={query}
                                onChange={handleChange}
                                />
                                <button className="btnn btn-outline-secondary pixel" type="submit" id="btn-search">Search</button>
                            </div>
                        </form>
                        <ul className="navbar-nav mb-2 mb-lg-0">
                            {renderUserAuthentication()}
                        </ul>
                    </div>
                </div>
            </nav>
        </>
    )
}

export default Navbar;