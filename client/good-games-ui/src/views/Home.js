import { useState } from "react";
import { Link } from "react-router-dom";
import ReservationList from "../components/home_components/ReservationList";
import TopRatedGamesList from "../components/home_components/TopRatedGamesList";

function Home() {
    const [bestGames, setBestGames] = useState([]);
    const [bestReservation, setBesReservation] = useState([]);


    const renderHighestReviewedGames = () => {
        if (bestReservation.length === 0) {
            return (
                <div className="mb-3 text-center">
                    <h4>Sorry, cannot find any reviewed games.</h4>
                </div>
            )
        }

        return bestGames.map((game) => (
            <div className="card">
                <img src="" className="card-img-top" alt="popular game"/>
                <div className="card-body">
                    <h5 className="card-title">{game}</h5>
                    <p className="card-text"></p>
                    <Link className="btn btn-primary" to={`/review/${game.game_id}`}>See Reviews</Link>
                </div>
            </div>
        ));
    }

    const renderPopularAvailableReservation = () => {
        if (bestReservation.length === 0) {
            return (
                <div className="mb-3 text-center">
                    <h4>Sorry, no upcoming reservations.</h4>
                </div>
            )
        }

        return bestReservation.map((reservation) => (
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">{reservation.name}</h5>
                    <h6 className="card-subtitle mb-6 text-body-secondary">Reservations {reservation}/{reservation.maxAttendees}</h6>
                    <p className="card-text">{reservation.description}</p>
                    <Link className="btn btn-primary" to={`/reservation/${reservation.id}`}>See Reservations</Link>
                </div>
            </div>
        ))
    }

    return (
        <main className="container">
            <header>
                <div className="p-3 pd-md-4 mx-auto text-center">
                    <h1 className="display-4 fw-normal text-body-emphasis">Good Games</h1>
                    <p className="fs-5 text-body-secondary">
                    Welcome to <b>Good Games</b>, where the thrill of rolling dice and strategizing moves comes alive! Whether you’re a seasoned board game enthusiast or a curious newcomer, our platform is your gateway to an exciting world of dice based adventures. 🎲
                    <br/>
                    <br/>
                    <b>Discover and Review:</b> Dive into our extensive collection of board games, from classic favorites to hidden gems. Read reviews, share your thoughts, and find the perfect game for your next gathering.
                    <br/>
                    <br/>
                    <b>Connect and Play:</b> Looking for fellow players? Search local game rooms, reserve a spot, and challenge opponents face-to-face. Whether it’s a casual match or an epic showdown, the camaraderie is real, and the fun is guaranteed.
                    <br/>
                    <br/>
                    <b>Join the Community:</b> Become part of our vibrant community of board game aficionados. Share opinions, play with others, and celebrate victories together. Let’s roll the dice and create memories that last a lifetime! 🎉
                    </p>
                </div>
            </header>
            <section className="row">
                <TopRatedGamesList />
            </section>
            <section className="p-3 pd-md-4 mx-auto text-center">
                <ReservationList />
            </section>
        </main>
    )
}

export default Home;