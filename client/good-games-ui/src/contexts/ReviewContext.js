import { createContext, useState } from "react";
import { useNavigate } from "react-router-dom";

const reviewContext = createContext();

export function ReviewProvider({ children }) {
    const review = useProvideAuth();
    return (<reviewContext.Provider value={review}>{children}</reviewContext.Provider>);
}

function useProvideAuth() {
    





}


export default reviewContext;