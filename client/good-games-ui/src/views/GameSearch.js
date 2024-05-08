import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

const convert = require('xml-js');

const convertObj = (data, serverList) => {
    const newGames = [];
    data.elements[0].elements.forEach((e) => {
        if (e.attributes.type === "boardgame") {
            const newObj = {
                bgg_id: e.attributes.id,
                name: e.elements[0].attributes.value,
                year_published: e.elements[1] ? e.elements[1].attributes.value : 0
            }

            const game = serverList.find((e) => e.bggId === parseInt(newObj.bgg_id));
            if (game) {
                newObj.game = game;
            }

            newGames.push(newObj);
        }
    });
    return newGames;
}

function GameSearch() {
    const [games, setGames] = useState([])
    const [isLoading, setIsLoading] = useState(false);

    const url = 'http://localhost:8080/api/game'
    const bgg_url = 'https://api.geekdo.com/xmlapi2/search'
    const { query } = useParams();

    useEffect(() => {
        if (query) {
            setIsLoading(true);

            fetch(`${bgg_url}?query=${query}`)
            .then(response => {
                if (response.status === 200) {
                    return response.text();
                } else {
                    return Promise.reject(`Unexpected status code: ${response.status}`);
                }
            })
            .then(data => {
                const obj = convert.xml2js(data);
                let serverGames = [];

                fetch(url)
                .then(response => {
                    if (response.status === 200) {
                        return response.json();
                    } else {
                        return Promise.reject(`Unexpected status code: ${response.status}`);
                    }
                })
                .then(data => {
                    serverGames = data;
                    const gamesList = convertObj(obj, serverGames);
                    setGames(gamesList);
                })
                .catch( (error) => {
                    console.log(error);
                    const gamesList = convertObj(obj, serverGames);
                    setGames(gamesList);
                });

                setIsLoading(false);
            })
            .catch(console.log);
        }
    }, [query])


    const renderRows = () => {
        return games.map((game, index) => (
            <tr key={index} className={game.game ? "table-success" : ""}>
                <td></td>
                <td>{game.bgg_id}</td>
                <td><Link to={`/game/${game.bgg_id}`} className="text-underline-hover">{game.name}</Link></td>
                <td></td>
                <td>{game.year_published}</td>
            </tr>
        ))
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
                <section>
                    <table className="table table-hover">
                        <caption>List of search results</caption>
                        <thead>
                            <tr>
                                <th scope="col"></th>
                                <th scope="col">BGG ID</th>
                                <th scope="col">Game Name</th>
                                <th scope="col">Rating</th>
                                <th scope="col">Year Published</th>
                            </tr>
                        </thead>
                        <tbody className="table-group-divider">
                            {renderRows()}
                        </tbody>
                    </table>
                </section>
            )}
            
        </main>
    )
}

export default GameSearch;