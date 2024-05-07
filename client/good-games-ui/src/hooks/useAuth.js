import { useContext } from "react";
import authContext from "../contexts/AuthContext";

function useAuth() {
    return useContext(authContext);
}

export default useAuth;