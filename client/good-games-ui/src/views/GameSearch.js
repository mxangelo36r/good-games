import { useEffect, useState } from "react";
import { json, useParams } from "react-router-dom";

const convert = require('xml-js');

function GameSearch(props) {
    const [games, setGames] = useState([])
    const [searchQuery, setSearchQuery] = useState("");
    const [limit, setLimit] = useState(50);
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
                console.log(obj.elements[0].elements[0].elements[0]);
                console.log(obj)
            })
            .catch(console.log);

            setSearchQuery(query);
        }
    }, [])
    
    const handleChange = (event) => {
        setSearchQuery(event.target.value);
    }

    const handleSubmit = () => {
        fetch(`${bgg_url}?search=${searchQuery}&exact=${limit}`)
        .then(response => {
            if (response.status === 200) {
                console.log(response)
                return response.body;
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => setGames(data))
        .catch(console.log);
    }

    const renderRows = () => {
        return games.map((game, index) => (
            <tr key={index}>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        ))
    }

    return (
        <main className="container mt-4">
            <header>
                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <input
                        id="searchQuery"
                        name="searchQuery"
                        type="search"
                        className="form-control"
                        placeholder=""
                        aria-label="Search"
                        aria-describedby="btn-search"
                        value={searchQuery}
                        onChange={handleChange}
                        />
                        <button className="btn btn-outline-success" type="button" id="btn-search">Search</button>
                    </div>
                </form>
            </header>
            <section>
                <table className="table">
                    <caption>List of search results</caption>
                    <thead>
                        <tr>
                            <th scope="col"></th>
                            <th scope="col">Game Name</th>
                            <th scope="col">Rating</th>
                            <th scope="col">Year Published</th>
                        </tr>
                    </thead>
                    <tbody className="table-group-divider">
                        {/* {renderRows()} */}
                    </tbody>
                </table>
            </section>
        </main>
    )
}

export default GameSearch;