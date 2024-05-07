import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const convert = require('xml-js');

const convertToObj = (data) => {
    const newGames = [];
    data.elements[0].elements.map((e) => {
        if (e.attributes.type === "boardgame") {
            const newObj = {
                bgg_id: e.attributes.id,
                name: e.elements[0].attributes.value,
                year_published: e.elements[1] ? e.elements[1].attributes.value : 0
            }
            newGames.push(newObj);
        }
    })
    return newGames;
}

function GameSearch() {
    const [games, setGames] = useState([])
    const [isLoading, setIsLoading] = useState(false);

    const bgg_url = 'https://api.geekdo.com/xmlapi2/search'
    const { query } = useParams();

    useEffect(() => {
        if (query) {
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
                setGames(convertToObj(obj));
            })
            .catch(console.log);
        }
    }, [])

    const renderRows = () => {
        return games.map((game, index) => (
            <tr key={index}>
                <td></td>
                <td>{game.bgg_id}</td>
                <td>{game.name}</td>
                <td></td>
                <td>{game.year_published}</td>
            </tr>
        ))
    }

    return (
        <main className="container mt-4">
            <section>
                <table className="table">
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
        </main>
    )
}

export default GameSearch;