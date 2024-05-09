import React, { useState, useEffect } from "react";
import ReservationCard from "./ReservationCard";
import Loading from "../Loading";

function ReservationList() {
	const [reservations, setReservations] = useState([]);
	const [loading, setLoading] = useState(true);
	const url = `http://localhost:8080/api/reservation`;

	useEffect(() => {
		fetch(url)
			.then((response) => {
				if (response.status === 200) {
					return response.json();
				} else {
					return Promise.reject(`Unexpected Status Code: ${response.status}`);
				}
			})
			.then((data) => setReservations(data))
			.then(setLoading(false))
			.catch(console.log);
	}, [url]);

	if (loading) {
		return (
			<Loading name="Reservations" />
		);
	} else if (!loading && reservations.length === 0) {
		return (
			<div className="mb-3 text-center">
				<h4>Sorry, no upcoming reservations.</h4>
			</div>
		);
	} else if (!loading && reservations.length > 0) {
		return (
			<div className="mb-3 text-center">
                {reservations.map((reservation, index) => {return <ReservationCard key={index} reservation={reservation} />})}
			</div>
		);
	}
}

export default ReservationList;