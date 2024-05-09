import React, { useState, useEffect } from "react";
import GameCard from "../components/GameCard";
import Loading from "../components/Loading";

function ReviewedGames() {
	const [allGames, setAllGames] = useState([]);
	const [loading, setLoading] = useState(true);
	const url = `http://localhost:8080/api/game`;

	useEffect(() => {
		fetch(url)
			.then((response) => {
				if (response.status === 200) {
					return response.json();
				} else {
					return Promise.reject(`Unexpected Status Code: ${response.status}`);
				}
			})
			.then((data) => setAllGames(data))
			.then(setLoading(false))
			.catch(console.log);
	}, [url]);

	console.log(allGames);

	if (loading) {
		return <Loading name="Loading Reviewed Games" />;
	} else if (!loading && allGames.length === 0) {
		return (
			<div className="mb-3 text-center">
				<h4>Sorry, no reviewed games.</h4>
			</div>
		);
	} else if (!loading && allGames.length > 0) {
		return (
			<main className="container p-3 pd-md-4 mx-auto text-center">
	
				<section className="row p-3 pd-md-4 mx-auto text-center">
					{allGames.map((game, index) => {
						return <GameCard key={index} game={game} />;
					})}
				</section>
			</main>
		);
	}
}

export default ReviewedGames;
