import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function TopRatedGameCard({ game }) {
	const [averageRating, setAverageRating] = useState(0);
	const url = `http://localhost:8080/api/game/rating/${game.gameId}`;

    useEffect(() => {
		fetch(url)
			.then((response) => {
				if (response.status === 200) {
					return response.json();
				} else {
					return Promise.reject(`Unexpected Status Code: ${response.status}`);
				}
			})
			.then((data) => setAverageRating(data))
			.catch(console.log);
	}, [url]);


	return (
		<div className="col-xs-11 col-md-5 m-1 card mb-3 bg-light mx-auto d-flex justify-content-evenly" key={game.gameId}>
			<div className="card-body">
				<h5 className="card-title">{game.name}</h5>
				<h6 className="card-subtitle mb-6 text-body-secondary">
					Rating: {averageRating} / 10
				</h6>
			</div>
			<div className="mb-3">
				<Link className="btn btn-info p-2" to={`/game/${game.bggId}`}>
					More Info
				</Link>
			</div>
		</div>
	);
}

export default TopRatedGameCard;
