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

        fetch(`${url}/login`)
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

        fetch(`${url}/signup`)
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
        user,
        errors,
        isLoading,
        signIn,
        signUp,
        signOut
    }
}
export default authContext;
