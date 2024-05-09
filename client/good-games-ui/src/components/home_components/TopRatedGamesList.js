import React, { useState, useEffect } from "react";
import GameCard from "../GameCard";
import Loading from "../Loading";

function TopRatedGamesList() {
	const [topRatedGames, setTopRatedGames] = useState([]);
	const [loading, setLoading] = useState(true);
	const url = `http://localhost:8080/api/game/topfive`;

	useEffect(() => {
		fetch(url)
			.then((response) => {
				if (response.status === 200) {
					return response.json();
				} else {
					return Promise.reject(`Unexpected Status Code: ${response.status}`);
				}
			})
			.then((data) => setTopRatedGames(data))
			.then(setLoading(false))
			.catch(console.log);
	}, [url]);

	if (loading) {
		return <Loading name="Top-Rated Games" />;
	} else if (!loading && topRatedGames.length === 0) {
		return (
			<div className="mb-3 text-center">
				<h4>Sorry, no reviewed games.</h4>
			</div>
		);
	} else if (!loading && topRatedGames.length > 0) {
		return (
			<div className="mb-3 text-center">				
				<div className="row">
					{topRatedGames.map((game, index) => {
						return <GameCard key={index} game={game} />;
					})}
				</div>
			</div>
		);
	}
}

export default TopRatedGamesList;
