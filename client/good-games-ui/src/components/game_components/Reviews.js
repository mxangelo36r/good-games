import { useEffect, useState } from "react";
import useAuth from "../../hooks/useAuth";
import Modal from "../Modal";

const REVIEW_DEFAULT = {
    userId: 0,
    gameId: 0,
    text: "",
    rating: 1,
}

function Reviews(props) {
    const [reviews, setReviews] = useState();
    const [showModal, setShowModal] = useState(false);
    const [review, setReview] = useState(REVIEW_DEFAULT);

    const { isLoggedIn } = useAuth();

    useEffect(() => {
        setReviews(props.reviews)
    }, [props.reviews])

    const renderScores = () => {
        return (
            ""
        )
    }

    const handleChange = (event) => {
        const newReview = {...review};

        if(event.target.type === 'checkbox'){
            newReview[event.target.name] = event.target.checked;
        }else{
            newReview[event.target.name] = event.target.value;
        }

        setReview(newReview);
    }

    const handleValueChange = (event) => {
        const newReview = {...review};
        newReview.rating = parseInt(event.target.value);
        setReview(newReview);
    }

    const handleSubmit = (event) => {
        event.preventDefault();


        console.log(review)
    }

    const handleModalOpen = () => {
        setShowModal(true);
    }

    const handleModalClose = () => {
        setShowModal(false);
    }

    const renderAvgScores = () => {
        const totalScore = reviews.reduce((prev, curr) => curr.rating + prev , 0)
        return totalScore / reviews.length;
    }

    const renderTotalReviews = () => {
        return reviews.length;
    }

    const renderReviews = () => {
        return reviews.map((review, index) => (
            <div className="card col-12 mb-2" key={index}>
                <div className="card-body">
                    <h5 className="card-title">{"NOOOOO!"}</h5>
                    <h6 className="card-subtitle mb-2 text-body-secondary">Rating ({review.rating}/10)</h6>
                    <p className="card-body">{review.text}</p>
                </div>
            </div>
        ))
    }

    const renderRadios = () => {
        return [...Array(10).keys()].map(key => (
            <div key={key} className="form-check form-check-inline">
                <input
                className="form-check-input"
                type="radio"
                name="rating"
                id={`star-${key + 1}`}
                checked={review.rating === key + 1}
                onChange={handleValueChange}
                value={key + 1}/>
                <label className="form-check-label">{`${key + 1}`}</label>
            </div>
        ))
    }

    if (reviews) {
        return (
            <>
                <section className="card p-4 mt-3">
                    <div className="row">
                        <div className="col-12">
                            <h3 className="card-title">Ratings</h3>
                            <p>Total Score: {reviews ? renderAvgScores() : ""}</p>
                            {renderScores()}
                        </div>
                    </div>
                </section>
                <section className="card p-4 mt-3 mb-3">
                    <div className="row">
                        <div className="col-10">
                            <h3 className="card-title">Reviews</h3>
                            <p>Total Reviews: {reviews ? renderTotalReviews() : ""}</p>
                        </div>
                        <div className="col-2">
                            {isLoggedIn() && (
                                <>
                                <button className="btn btn-primary" type="button" onClick={handleModalOpen}>
                                    Create Review
                                </button>
                                <Modal isOpen={showModal} onClose={handleModalClose}>
                                    <form onSubmit={handleSubmit}>
                                        <div className="modal-header">
                                            <h1 className="modal-title fs-5" id="newReviewModalLabel">New Review</h1>
                                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div className="modal-body">
                                            <fieldset className="form-group">
                                                <label htmlFor="text">Review</label>
                                                <textarea
                                                id="text"
                                                name="text"
                                                className="form form-control"
                                                value={review.text}
                                                onChange={handleChange}
                                                />
                                            </fieldset>
                                            <div className="mt-3">
                                                {renderRadios()}
                                            </div>
                                            
                                        </div>
                                        <div className="modal-footer">
                                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            <button type="submit" className="btn btn-primary" data-bs-dismiss="modal">Save Review</button>
                                        </div>
                                    </form>
                                </Modal>
                                </>
                            )}
                        </div>
    
                        <div className="col-12">
                            <div className="row">
                                {renderReviews()}
                            </div>
                        </div>
                    </div>
                </section>
            </>
        )
    }

}

export default Reviews;