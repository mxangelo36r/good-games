import React from 'react'

function Loading(props) {
  return (
    <div className="p-3 pd-md-4 mx-auto text-center">
      <h4 className="sr-only">Loading {props.name}</h4>
      <div className="spinner-border mb-3 text-center" role="status"></div>
    </div>
  )
}

export default Loading