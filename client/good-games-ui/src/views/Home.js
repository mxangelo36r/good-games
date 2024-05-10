import { useState } from "react";
import { Link } from "react-router-dom";
import ReservationList from "../components/home_components/ReservationList";
import TopRatedGamesList from "../components/home_components/TopRatedGamesList";

function Home() {
	// const [bestGames, setBestGames] = useState([]);
	// const [bestReservation, setBesReservation] = useState([]);

	// const renderHighestReviewedGames = () => {
	//     if (bestReservation.length === 0) {
	//         return (
	//             <div className="mb-3 text-center">
	//                 <h4>Sorry, cannot find any reviewed games.</h4>
	//             </div>
	//         )
	//     }

	//     return bestGames.map((game) => (
	//         <div className="card">
	//             <img src="" className="card-img-top" alt="popular game"/>
	//             <div className="card-body">
	//                 <h5 className="card-title">{game}</h5>
	//                 <p className="card-text"></p>
	//                 <Link className="btn btn-primary" to={`/review/${game.game_id}`}>See Reviews</Link>
	//             </div>
	//         </div>
	//     ));
	// }

	// const renderPopularAvailableReservation = () => {
	//     if (bestReservation.length === 0) {
	//         return (
	//             <div className="mb-3 text-center">
	//                 <h4>Sorry, no upcoming reservations.</h4>
	//             </div>
	//         )
	//     }

	//     return bestReservation.map((reservation) => (
	//         <div className="card">
	//             <div className="card-body">
	//                 <h5 className="card-title">{reservation.name}</h5>
	//                 <h6 className="card-subtitle mb-6 text-body-secondary">Reservations {reservation}/{reservation.maxAttendees}</h6>
	//                 <p className="card-text">{reservation.description}</p>
	//                 <Link className="btn btn-primary" to={`/reservation/${reservation.id}`}>See Reservations</Link>
	//             </div>
	//         </div>
	//     ))
	// }

	return (
		<main className="container">
			<header>
				<div className="p- pd-md-4 mx-auto text-center">
					<h1 className="">Good Games</h1>
					<h2>Bringing People Together One Game at a Time</h2>
					<p className="fs-5 text-body-secondary">
						Welcome to <b>Good Games</b>, where the thrill of rolling dice and
						strategizing moves comes alive! Whether you’re a seasoned board game
						enthusiast or a curious newcomer, our platform is your gateway to an
						exciting world of dice-based adventures. 🎲
						<hr />
						<b>Discover and Review:</b> Find the perfect game for your next
						gathering or help others find their ideal game.
						<br />
						<b>Connect and Play:</b> Looking for fellow players? Search locally for a casual match or an epic showdown!
						<br />
						<b>Join the Community:</b> Become part of our vibrant community of
						board game aficionados.
					</p>
				</div>
			</header>
			<section className="row">
				<h2 className="text-center mb-3">Top-Rated Games</h2>
				<TopRatedGamesList />
			</section>
			<section className="p-3 pd-md-4 mx-auto text-center">
                <h2 className="mb-3">Upcoming Events</h2>
				<ReservationList />
			</section>
		</main>
	);
}

export default Home;
