import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function ReservationCard({ reservation, index }) {
	console.log(reservation);

	let location = reservation.location;
	let time = reservation.time;

    const formatTime = (string) => {
        return string.slice(0, -3)
    }

    time = formatTime(time)
    console.log(time)

	return (
		<div className="card mb-3 bg-light" key={reservation.reservationId}>
			<div className="card-body">
				<h5 className="card-title">{location.name}</h5>
				<h6 className="card-subtitle mb-6 text-body-secondary">
					{location.address}, {location.city} {location.state}{" "}
					{location.postalCode}
				</h6>
                <hr />
                <div className="d-sm-flex justify-content-evenly mx-auto text-center">
                    <p className="card-text d-flex flex-row justify-content-between">Date: {reservation.date}</p>
                    <p className="card-text d-flex flex-row justify-content-between">
                        Time: {parseInt(reservation.time) % 12 === 0 ? 12 : parseInt(reservation.time) % 12}
                        {reservation.time.slice(0, -3).slice(2)}
                        {parseInt(reservation.time) >= 12 ? "pm" : "am"}
                    </p>
                    <p className="card-text d-flex flex-row justify-content-between">Hosted By: {reservation.hostName}</p>
                </div>
				<Link className="btn btn-primary mt-3" to={`/reservation/${reservation.id}`}>
					See Reservations
				</Link>
			</div>
		</div>
	);
}

export default ReservationCard;