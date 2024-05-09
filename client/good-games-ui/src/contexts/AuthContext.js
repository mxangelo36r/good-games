import { createContext, useState } from "react";
import { useNavigate } from "react-router-dom";

const authContext = createContext();

export function AuthProvider({ children }) {
    const auth = useProvideAuth();
    return (<authContext.Provider value={auth}>{children}</authContext.Provider>);
}

function useProvideAuth() {
    const [user, setUser] = useState(null);
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

    const isUser = (id) => {
        if (user.userId === id) {
            return true;
        } else {

        }
    }

    const getUserId = () => {
        return user.userId;
    }

    const checkAuth = (nextState, replace) => {
        if (!user) {
            replace({ pathname: "/" })
        }
    }

    const removeErrors = () => {
        setErrors([])
    }

    const signIn = (loginDetails) => {
        setErrors([]);
        setIsLoading(true);

        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginDetails)
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
                setUser(data);
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
            console.log()
            if (response.status === 201 || response.status === 400) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => {
            if (data.userId) {
                setUser(data);
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
        isUser,
        getUserId,
        checkAuth,
        removeErrors,
        signIn,
        signUp,
        signOut
    }
}
export default authContext;
