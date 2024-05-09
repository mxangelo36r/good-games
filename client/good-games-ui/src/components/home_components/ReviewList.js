import React, {useState, useEffect} from 'react'
import ReviewCard from './ReviewCard'
import Loading from '../Loading';

function ReviewList() {
  const [reviews, setReviews] = useState([]);
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
			.then((data) => setReviews(data))
			.then(setLoading(false))
			.catch(console.log);
	}, [url]);

console.log(reviews)

  if(loading) {
    return (
      <Loading name="Reviews" />
    )
  } else if (!loading && reviews.length === 0) {
		return (
			<div className="mb-3 text-center">
				<h4>Sorry, no upcoming reservations.</h4>
			</div>
		);
	} else if (!loading && reviews.length > 0) {
    return (
      <div className="mb-3 text-center">
        <h4>Highest Reviewed Games</h4>        
        {reviews.map((review, index) => {return <ReviewCard key={index} review={review} />})}
      </div>
    )
  }
}

export default ReviewList