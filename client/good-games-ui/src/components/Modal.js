function Modal(props) {
    const {isOpen, onClose, children} = props;

    if (!isOpen) {
        return null;
    }
    
    return (
        <>
        <div className="modal fade show" style={{display: "block"}} tabIndex="-1" role="dialog">
            <div className="modal-dialog">
                <div className="modal-content">
                    {children}
                </div>
            </div>
        </div>
        <div className="modal-backdrop fade show" ></div>
        </>
    )
}

export default Modal;