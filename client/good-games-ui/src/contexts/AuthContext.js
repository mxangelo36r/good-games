import { createContext, useState } from "react";
import { useNavigate } from "react-router-dom";

const authContext = createContext();

export function AuthProvider({ children }) {
    const auth = useProvideAuth();
    return (<authContext.Provider value={auth}>{children}</authContext.Provider>);
}

const DUMMY_USER = {
    id: 1,
    name: "Paul1",
    email: "pman@email.com",
    password: "password",
    role: "ADMIN"
}

function useProvideAuth() {
    const [user, setUser] = useState(DUMMY_USER);
    const [errors, setErrors] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    const url = 'http://localhost:8080/api/user';
    const navigate = useNavigate();

    const isLoggedIn = () => {
        if (user) {
            return true;
        } else {
            return false;
        }
    }

    const isAdmin = () => {
        if (user.role === "ADMIN") {
            return true;
        } else {
            return false;
        }
    }

    const signIn = (newUser) => {
        setErrors([]);
        setIsLoading(true);

        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newUser)
        };

        fetch(`${url}/login`, init)
        .then(response => {
            setIsLoading(false)
            if (response.status === 200 || response.status === 400) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`)
            }
        })
        .then(data => {
            if (data.userId) {
                navigate('/');
            } else {
                setErrors(data);
            }
        })
        .catch(console.log);
    }

    const signUp = (newUser) => {
        setErrors([]);
        setIsLoading(true);

        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newUser)
        };

        fetch(`${url}/signup`, init)
        .then(response => {
            setIsLoading(false);
            if (response.status === 201 || response.status === 400) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => {
            if (data.userId) {
                navigate('/');
            } else {
                setErrors(data);
            }
        })
        .catch(console.log);
    }

    const signOut = () => {
        setErrors([]);
        setUser(null);
    }

    return {
        errors,
        isLoading,
        isLoggedIn,
        isAdmin,
        signIn,
        signUp,
        signOut
    }
}
export default authContext;
