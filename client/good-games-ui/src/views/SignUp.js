import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import useAuth from "../hooks/useAuth";

const USER_DEFAULT = {
    username: "",
    password: "",
    email: "",
    role: "USER"
}

function SignUp() {
    const [user, setUser] = useState(USER_DEFAULT);
    const { signUp, errors, removeErrors } = useAuth();


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

        signUp(user);
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
                    <h1>Sign Up for Good Games</h1>
                    <p className="fs-5 text-body-secondary">
                    Create a free account or <Link to={"/login"}>Log In</Link>
                    </p>
                </div>
                {renderErrors()}
            </header>
            <section>
                <form onSubmit={handleSubmit}>
                    <fieldset className="form-group">
                        <label htmlFor="name">Username</label>
                        <input
                        id="username"
                        name="username"
                        type="text"
                        className="form form-control"
                        value={user.username}
                        onChange={handleChange}
                        />
                    </fieldset>
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
                        <button className="btnn mr-2" type="submit">Sign Up</button>
                        <Link className="btnn" type="button" to={"/"}>Cancel</Link>
                    </div>
                </form>
            </section>
        </main>
    )
}

export default SignUp;