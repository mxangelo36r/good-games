import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import useAuth from "../hooks/useAuth";

const USER_DEFAULT = {
    password: "",
    email: ""
}

function Login() {
    const [user, setUser] = useState(USER_DEFAULT);
    const { signIn, errors, removeErrors } = useAuth();

    useEffect(() => {
        removeErrors();
    }, [])

    const handleChange = (event) => {
        const newUser = {...user};

        if(event.target.type === 'checkbox'){
            newUser[event.target.name] = event.target.checked;
        }else{
            newUser[event.target.name] = event.target.value;
        }

        setUser(newUser);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        
        signIn(user);
    }

    const renderErrors = () => {
        if (errors.length > 0) {
            return (
                <div className="alert alert-danger" role="alert">
                    <p>The following errors were found:</p>
                    <br/>
                    <ul>
                        {errors.map((error, index) => (
                            <li key={index}>{error}</li>
                        ))}
                    </ul>
                </div>
            )
        }
    }

    return (
        <main className="container">
            <header>
                <div className="p-3 pd-md-4 mx-auto text-center">
                    <h1>Login to Good Games</h1>
                    <p className="fs-5 text-body-secondary">
                        Login to your account or If you’re new <Link to={"/signUp"}>Sign Up</Link>
                    </p>
                </div>
                {renderErrors()}
            </header>
            <section>
                <form onSubmit={handleSubmit}>
                    <fieldset className="form-group">
                        <label htmlFor="email">Email</label>
                        <input
                        id="email"
                        name="email"
                        type="email"
                        className="form form-control"
                        value={user.email}
                        onChange={handleChange}
                        />
                    </fieldset>
                    <fieldset className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                        id="password"
                        name="password"
                        type="password"
                        className="form form-control"
                        value={user.password}
                        onChange={handleChange}
                        />
                    </fieldset>
                    <div className="hstack gap-3 mt-4">
                        <button className="btn btn-success mr-2" type="submit">Sign Up</button>
                        <Link className="btn btn-warning" type="button" to={"/"}>Cancel</Link>
                    </div>
                </form>
            </section>
        </main>
    )
}

export default Login;