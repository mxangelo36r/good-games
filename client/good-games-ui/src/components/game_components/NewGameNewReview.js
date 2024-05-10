import { useEffect, useState } from "react";
import useAuth from "../../hooks/useAuth";
import Modal from "../Modal";
import { useNavigate } from "react-router-dom";


function NewGameNewReview({ name, id, resetGame }) {
  const [nextId, setNextId] = useState(0);
	const [reviews, setReviews] = useState([]);
	const [errors, setErrors] = useState([]);
	const [showModal, setShowModal] = useState(false);
	const [review, setReview] = useState({});
	const { isLoggedIn, isAdmin, isUser, getUserId, getUsername } = useAuth();
	const [game, setGame] = useState({});
	const url = "http://localhost:8080/api/reviews";
	const addGameUrl = "http://localhost:8080/api/game";
	const navigate = useNavigate();

  useEffect(() => {
    
  }, [review])

	const handleChange = (event) => {
		const newReview = { ...review };

		if (event.target.type === "checkbox") {
			newReview[event.target.name] = event.target.checked;
		} else {
			newReview[event.target.name] = event.target.value;
		}
		setReview(newReview);
	};

	const handleValueChange = (event) => {
		const newReview = { ...review };
		newReview.rating = parseInt(event.target.value);
		setReview(newReview);
	};

	const handleSubmit = async (event) => {
		event.preventDefault();
		addReview();
	};

	const addGame = async () => {
		console.log(name, id);
		const init = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({ name: name, bggId: id }),
		};

		await fetch(addGameUrl, init)
			.then((response) => {
				if (response.status === 201 || response.status === 400) {
					return response.json();
				} else {
					return Promise.reject(`Unexpected status code: ${response.status}`);
				}
			})
			.then((data) => {
				if (data.gameId) {
					// setGame({
					// 	data
					// });
					data.reviews = [];
					// console.log(data);
					resetGame(data);
				} else {
					setErrors(data);
				}
			})
			.catch(console.log);
	};

	const handleModalOpen = () => {
		// setShowModal(true);
    	addGame();
	};

	const handleModalClose = () => {
		setShowModal(false);
		setErrors([]);
		setReview({
			text: "",
		});
	};

	const renderRadios = () => {
		return [...Array(10).keys()].map((key) => (
			<div key={key} className="form-check form-check-inline">
				<input
					className="form-check-input"
					type="radio"
					name="rating"
					id={`star-${key + 1}`}
					checked={review.rating === key + 1}
					onChange={handleValueChange}
					value={key + 1}
				/>
				<label className="form-check-label">{`${key + 1}`}</label>
			</div>
		));
	};

	const renderErrors = () => {
		if (errors.length > 0) {
			return (
				<div className="alert alert-danger" role="alert">
					<p>The following errors were found:</p>
					<br />
					<ul>
						{errors.map((error, index) => (
							<li key={index}>{error}</li>
						))}
					</ul>
				</div>
			);
		}
	};

  const getGameId = async () => {
    await fetch(`http://localhost:8080/api/game/bggId/${id}`)
    .then(response => {
      if (response.status === 200 || response.status === 400) {
        return response.json();
      } else {
        return Promise.reject(`Unexpected status code: ${response.status}`);
      }
    })
    .then((data) => {
      if(data) {
        setGame(data);
      } else{
        setGame({});
      }
      console.log(game)
      console.log(data)
    })
  }
  
	const addReview = async () => {
    console.log(game)
    getGameId()
		const newReview = { ...review };
		newReview.userId = getUserId();
		newReview.gameId = game.gameId;
		console.log(game);
		const init = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(newReview),
		};

		await fetch(`${url}/review`, init)
			.then((response) => {
				if (response.status === 201 || response.status === 400) {
					return response.json();
				} else {
					return Promise.reject(`Unexpected status code: ${response.status}`);
				}
			})
			.then((data) => {
				if (data.reviewId) {
					// add data to existing array (no reload)
					data.userName = getUsername();
					data.gameName = name;

					const newReviews = [...reviews];
					newReviews.push(data);
					setReviews(newReviews);
					handleModalClose();
					window.location.reload();
				} else {
					setErrors(data);
				}
			})
			.catch(console.log);
	};

	return (
		<div className="text-center">
			{isLoggedIn() && (
				<>
					<button
						className="btn btn-primary"
						type="button"
						onClick={handleModalOpen}
					>
						Be the First to Review This Game!
					</button>
					<Modal isOpen={showModal} onClose={handleModalClose}>
						<form onSubmit={handleSubmit}>
							<div className="modal-header">
								<h1 className="modal-title fs-5" id="newReviewModalLabel">
									New Review
								</h1>
								<button
									type="button"
									className="btn-close"
									onClick={handleModalClose}
								></button>
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
								<div className="mt-3">{renderRadios()}</div>
							</div>
							<div className="modal-footer">
								<button
									type="button"
									className="btn btn-secondary"
									onClick={handleModalClose}
								>
									Close
								</button>
								<button type="submit" className="btn btn-primary">
									Save Review
								</button>
							</div>
						</form>
					</Modal>
				</>
			)}
		</div>
	);
}

export default NewGameNewReview;
