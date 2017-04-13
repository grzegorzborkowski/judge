import React from 'react'
import NavLink from './NavLink'

export default React.createClass({
  render() {
    return (
      <div>
        <h2>Submissions</h2>
        <ul>
          <li><NavLink to="/submissions/1/submission-1">Submission number 1</NavLink></li>
          <li><NavLink to="/submissions/2/submission-2">Submission number 2</NavLink></li>
        </ul>
          {this.props.children}
      </div>
    )
  }
})
