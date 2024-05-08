import React, { useState, useEffect } from "react";
import ReservationCard from "./ReservationCard";

function ReservationHome() {
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
			<>
				<h4 className="sr-only">Loading Reservations</h4>
				<div className="spinner-border mb-3 text-center" role="status"></div>
			</>
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
				<h4>Upcoming Reservations</h4>
                {reservations.map((reservation, index) => {return <ReservationCard key={index} reservation={reservation} />})}
			</div>
		);
	}
}

export default ReservationHome;