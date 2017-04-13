import React from 'react'
import NavLink from './NavLink'

export default React.createClass({
    render() {
        return (
            <div>
                <h2>Problems</h2>
                <ul>
                    <li><NavLink to="/problem/1">Problem number 1</NavLink></li>
                </ul>
                {this.props.children}
            </div>
        )
    }
})
