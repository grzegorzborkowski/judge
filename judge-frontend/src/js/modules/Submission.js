import React from 'react'

export default React.createClass({
  render() {
    return (
      <div>
        <h2>{this.props.params.submissionID}</h2>
      </div>
    )
  }
})
