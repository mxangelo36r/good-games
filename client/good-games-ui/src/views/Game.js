import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const convert = require('xml-js');

const convertObj = (data) => {
    const newGame = {
        publishers: []
    };
    data.elements[0].elements[0].elements.forEach((e) => {
        if (e.name === "thumbnail") {
            newGame.thumbnail = e.elements[0].text;
        }
        if (e.name === "image") {
            newGame.image = e.elements[0].text;
        }
        if (e.name === "name" && e.attributes.type === "primary") {
            newGame.name = e.attributes.value;
        }
        if (e.name === "name" && e.attributes.type === "alternate") {
            newGame.altName = e.attributes.value;
        }
        if (e.name === "description") {
            newGame.description = e.elements[0].text;
        }
        if (e.name === "yearpublished") {
            newGame.yearPublished = e.attributes.value;
        }
        if (e.name === "minplayers") {
            newGame.minPlayers = e.attributes.value;
        }
        if (e.name === "maxplayers") {
            newGame.maxPlayers = e.attributes.value;
        }
        if (e.name === "minplaytime") {
            newGame.minPlayTime = e.attributes.value;
        }
        if (e.name === "maxplaytime") {
            newGame.maxPlayTime = e.attributes.value;
        }
        if (e.name === "minage") {
            newGame.minAge = e.attributes.value;
        }
        if (e.name === "link" && e.attributes.type === "boardgamepublisher") {
            newGame.publishers.push(e.attributes.value);
        }
    });
    return newGame;
}

function Game() {
    const [game, setGame] = useState({});
    const [isLoading, setIsLoading] = useState(true);

    const bgg_url = 'https://api.geekdo.com/xmlapi2/thing';
    const { id } = useParams();

    useEffect(() => {
        if (id) {
            fetch(`${bgg_url}?id=${id}`)
            .then(response => {
                if (response.status === 200) {
                    return response.text();
                } else {
                    return Promise.reject(`Unexpected status code: ${response.status}`);
                }
            })
            .then(data => {
                const obj = convert.xml2js(data);
                setIsLoading(false);
                setGame(convertObj(obj));
            })
            .catch(console.log);
        }
    }, [id])

    const renderPublishers = () => {
        if (game.publishers.length > 0) {
            const maxGet = game.publishers.length > 5 ? 5 : game.publishers.length;
            const list = game.publishers.slice(0, maxGet);
            return list.map((p, index) => {
                return p + "; ";
            })
        }
    }

    return (
        <main className="container mt-4">
            {isLoading ? (
                <div className="overlay">
                    <div className="d-flex justify-content-center">
                        <div className="spinner-border" role="status">
                            <span className="visually-hidden">Loading...</span>
                        </div>
                    </div>
                </div>
            ) : (
                <section className="card p-2">
                    <div className="row g-0">
                        <div className="col-4">
                            <img src={game.image ? game.image : ""} className="rounded img-fluid" alt="Selected Game"/>
                        </div>
                        <div className="col-8">
                            <div className="row card-body">
                                <div className="col-12">
                                    <h3 className="card-title">{game.name ? game.name : "Name Not Found"}</h3>
                                    {game.altName ? (<h5 className="card-subtitle mb-2 text-body-secondary">{game.altName}</h5>) : ""}
                                    {game.description ? (<p dangerouslySetInnerHTML={{ __html: game.description}}></p>) : "Description Not Found"}
                                    <p>Number of Reviews: </p>
                                </div>
                                <div className="col-4">
                                    <p className="card-text fw-bold">{game.minPlayers ? game.minPlayers : "0"} to {game.maxPlayers ? game.maxPlayers : "0"} Players</p>
                                </div>
                                <div className="col-4">
                                    <p className="card-text fw-bold">{game.minPlayTime ? game.minPlayTime : "0"} to {game.maxPlayTime ? game.maxPlayTime : "0"} Min</p>
                                </div>
                                <div className="col-4">
                                    <p className="card-text fw-bold">Minimum age of {game.minAge ? game.minAge : "None"}</p>
                                </div>
                                <div className="col-12 mt-4">
                                    <p className="card-text text-body-secondary">Publishers: {game.publishers ? renderPublishers(): ""}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            )}
            
            
        </main>
    )
}

export default Game;