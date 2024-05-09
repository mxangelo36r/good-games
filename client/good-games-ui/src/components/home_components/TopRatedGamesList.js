import React, { useState, useEffect } from "react";
import TopRatedGameCard from "./TopRatedGameCard";
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
				<h4>Sorry, no upcoming reservations.</h4>
			</div>
		);
	} else if (!loading && topRatedGames.length > 0) {
		return (
			<div className="mb-3 text-center">
				<h4>Top-Rated Games</h4>
				{topRatedGames.map((game, index) => {
					return <TopRatedGameCard key={index} game={game} />;
				})}
			</div>
		);
	}
}

export default TopRatedGamesList;
