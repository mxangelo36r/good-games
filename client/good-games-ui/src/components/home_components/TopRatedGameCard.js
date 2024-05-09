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
            .then(console.log(averageRating))
			// .then(setLoading(false))
			.catch(console.log);
	}, [url]);


	return (
		<div className="card mb-3 bg-light" key={game.gameId}>
			<div className="card-body">
				<h5 className="card-title">{game.name}</h5>
				<h6 className="card-subtitle mb-6 text-body-secondary">
					Rating: {}
				</h6>

				<Link className="btn btn-primary" to={`/game/${game.gameId}`}>
					See Reservations
				</Link>
			</div>
		</div>
	);
}

export default TopRatedGameCard;
