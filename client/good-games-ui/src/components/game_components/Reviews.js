import { useEffect, useState } from "react";
import useAuth from "../../hooks/useAuth";
import Modal from "../Modal";
import { useNavigate } from "react-router-dom";

// const REVIEW_DEFAULT = {
//     userId: 0,
//     gameId: 0,
//     text: "",
//     rating: 1,
// }

function Reviews(props) {
    const [reviews, setReviews] = useState([]);
    const [errors, setErrors] = useState([])
    const [showModal, setShowModal] = useState(false);
    const [review, setReview] = useState({});
    const [totalReviews, setTotalReviews] = useState(0);

    const { isLoggedIn, isAdmin, isUser, getUserId, getUsername } = useAuth();

    console.log(props)
    const url = 'http://localhost:8080/api/reviews'
    const totalReviewsUrl = `http://localhost:8080/api/game/totalreviews/${props.gameId}`;
    const navigate = useNavigate();

    useEffect(() => {
        setReviews(props.reviews)
        fetchTotalReviews();
    }, [props.reviews])

    const handleChange = (event) => {
        const newReview = {...review};

        if(event.target.type === 'checkbox'){
            newReview[event.target.name] = event.target.checked;
        }else{
            newReview[event.target.name] = event.target.value;
        }

        setReview(newReview);
    }

    const fetchTotalReviews = () => {
        fetch(totalReviewsUrl)
            .then((response) => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected Status Code: ${response.status}`);
                }
            })
            .then((data) => setTotalReviews(data))
            .catch(console.log);
    };

    const handleValueChange = (event) => {
        const newReview = {...review};
        newReview.rating = parseInt(event.target.value);
        setReview(newReview);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        
        if (review.reviewId === undefined) {
            addReview();
        } else {
            updateReview();
        }
    }
    const addReview = () => {
        const newReview = {...review};
        newReview.userId = getUserId()
        newReview.gameId = props.gameId;

        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newReview)
        };

        fetch(`${url}/review`, init)
        .then(response => {
            if (response.status === 201 || response.status === 400) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => {
            if (data.reviewId) {
                // add data to existing array (no reload)
                data.userName = getUsername();
                data.gameName = props.gameName;

                const newReviews = [...reviews];
                newReviews.push(data);
                setReviews(newReviews);
                handleModalClose();
            } else {
                setErrors(data);
            }
        })
        .catch(console.log)
    }

    const updateReview = () => {
        const id = review.reviewId;
        const userId = getUserId();
        
        const init = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(review)
        }

        fetch(`${url}/${id}/${userId}`, init)
        .then(response => {
            if (response.status === 204) {
                return null;
            } else if (response.status === 400) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => {
            if(!data) {
                // add data to existing array (no reload)
                const newReviews = [...reviews];
                const index = newReviews.findIndex(e => e.reviewId === id);
                newReviews[index] = review;
                setReviews(newReviews);
                setReview({
                    text: ""
                });
                handleModalClose();
            } else {
                setErrors(data);
            }
        })
        .catch(console.log);
    }

    const handleEdit = (r) => {
        const newReview = {...r};

        setReview(newReview);
        setShowModal(true);
    }

    const handleDelete = (id) => {
        const userId = getUserId();

        if (window.confirm("Delete this review?")) {
            const init = {
                method: 'DELETE'
            };
            
            fetch(`${url}/${id}/${userId}`, init)
            .then(response => {
                if (response.status === 204) {
                    const newReviews = reviews.filter(review => review.reviewId !== id);
                    setReviews(newReviews);
                } else if (response.status === 400) {
                    return response.json();
                }else {
                    return Promise.reject(`Unexpected Status Code: ${response.status}`)
                }
            })
            .then(data => {
                if (data) {
                    console.log(data)
                }
            })
            .catch(console.log);
        }
    }

    const handleModalOpen = () => {
        setShowModal(true);
    }

    const handleModalClose = () => {
        setShowModal(false);
        setErrors([]);
        setReview({
            text: ""
        });
    }

    const renderAvgScores = () => {
        const totalScore = reviews.reduce((prev, curr) => curr.rating + prev , 0)
        if (totalScore === 0) {
            return 0;
        }
        return totalScore / reviews.length;
    }

    const renderScores = () => {
        const tracker = [0,0,0,0,0,0,0,0,0,0];
        const percentage = [0,0,0,0,0,0,0,0,0,0];

        reviews.forEach(review => {
            tracker[review.rating - 1] += 1;
        });

        const sum = tracker.reduce((partialSum, a) => partialSum + a, 0);
        tracker.forEach((num, index) => {
            if (sum === 0) {
                percentage[index] = 0;
            } else {
                percentage[index] = (num/sum) * 100;
            }
        })

        return tracker.map((value, index) => (
            <tr key={index}>
                <td className="rating-label">{index + 1}</td>
                <td className="rating-bar">
                    <div className="bar-container">
                        <div className="bar" style={{ width: `${percentage[index]}%`}}></div>
                    </div>
                </td>
            </tr>
        ))
    }

    const renderTotalReviews = () => {
        return reviews.length ;
    }

    const renderReviews = () => {
        
        return reviews.map((review, index) => (
            <div className="card col-12 mb-2" key={index}>
                <div className="card-body">
                    <h5 className="card-title">{review.userName}</h5>
                    <h6 className="card-subtitle mb-2 text-body-secondary">Rating ({review.rating}/10)</h6>
                    <p className="card-text">"{review.text}"</p>
                    {isLoggedIn() && (
                        <>
                            {(isAdmin() || isUser(review.userId)) && (
                                <div className="mt-2">
                                <button className="btn btn-primary btn-sm me-2" onClick={() => handleEdit(review)}>Edit</button>
                                <button className="btn btn-danger btn-sm" onClick={() => handleDelete(review.reviewId)}>Delete</button>
                            </div>
                            )}
                        </>
                    )}                    
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

    if (reviews) {
        return (
            <>
                <section className="card p-4 mt-3">
                    <div className="row">
                        <div className="col-12">
                            <h3 className="card-title">Ratings</h3>
                            <div className="d-flex gap-5" >
                                <p>Average Score: {reviews ? renderAvgScores() : ""}</p>
                            </div>
                            <table className="rating-table">
                                <tbody>
                                    {renderScores()}
                                </tbody>
                               
                            </table>
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
                                            <button type="button" className="btn-close"  onClick={handleModalClose}></button>
                                        </div>
                                        <div className="modal-body">
                                            {renderErrors()}
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
                                            <button type="button" className="btn btn-secondary" onClick={handleModalClose}>Close</button>
                                            <button type="submit" className="btn btn-primary">Save Review</button>
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