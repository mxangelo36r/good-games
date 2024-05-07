import { Link } from "react-router-dom";

function Navbar() {
    const renderUserAuthentication = () => {
        // TODO: add user authentication
        if (true) {
            return (
                <>
                    <li className="nav-item">
                        <Link to={'/signup'} className="nav-link text-nowrap">Sign Up</Link>
                    </li>
                    <li>
                        <Link to={'/login'} className="nav-link text-nowrap">Log In</Link>
                    </li>
                </>
            )
        } else {
            <>
            </>
        }
    }

    return (
        <>
            <nav className="navbar navbar-expand-lg bg-body-secondary">
                <div className="container-fluid">
                    <Link to={'/'} className="navbar-brand">Good Games</Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarContent">
                        <ul className="navbar-nav mb-2 mb-lg-0 me-5">
                            <li className="nav-item">
                                <Link to={'/games/reviewed'} className="nav-link text-nowrap">See Reviewed Games</Link>
                            </li>
                        </ul>
                        <form className="d-flex w-100 ms-auto" role="search">
                            <div className="input-group">
                                <input type="search" className="form-control" placeholder="Find Games" aria-label="Search" aria-describedby="btn-search"/>
                                <button className="btn btn-outline-success" type="button" id="btn-search">Search</button>
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