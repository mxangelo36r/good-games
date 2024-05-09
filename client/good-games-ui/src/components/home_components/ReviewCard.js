import React from 'react'
import { Link } from 'react-router-dom'

function ReviewCard({review}) {
  return (
    <div className="card mb-3 bg-light" key={review.gameId}>
			<div className="card-body">
				<h5 className="card-title">{review.name}</h5>
				<h6 className="card-subtitle mb-6 text-body-secondary">
					Rating: {review.rating}
				</h6>
 
				<Link className="btn btn-primary" to={`/game/${review.gameId}`}>
					See Reservations
				</Link>
			</div>
		</div>
  )
}

export default ReviewCard