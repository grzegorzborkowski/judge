import React from 'react';
import Cookies from 'universal-cookie';
import NavLink from './NavLink';

export default class Navbar extends React.Component {
    constructor() {
        super()
        this.state = {
            collapsed: true,
        };
    }

    toggleCollapse() {
        const collapsed = !this.state.collapsed;
        this.setState({collapsed});
    }

    render() {
        const { collapsed } = this.state;
        const navClass = collapsed ? "collapse" : "";
        const cookies = new Cookies();
        const isTeacher = (cookies.get("judge.role")) ? (cookies.get("judge.role").indexOf("teacher") > -1) : false;
        const isAdmin = (cookies.get("judge.role")) ? (cookies.get("judge.role").indexOf("admin") > -1) : false;
        return (
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" onClick={this.toggleCollapse.bind(this)} >
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class={"navbar-collapse " + navClass} id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        {/*<li><NavLink to="/" onlyActiveOnIndex>Home</NavLink></li>*/}
                        <li><NavLink to="/accountManagement">My Account</NavLink> </li>
                        <li><NavLink to="/problems"> Problems</NavLink> </li>
                        {/*<li><NavLink to="/statistics">Statistics</NavLink></li>*/}
                        <li><NavLink to="/submissions">My Submissions</NavLink></li>
                        {(isTeacher || isAdmin) ?
                          (<li><NavLink to="/problemCreator">Problem Creator</NavLink></li>) : (null)
                        }
                        {(isTeacher || isAdmin) ?
                          (<li><NavLink to="/categoryManagement">Category Management</NavLink></li>) : (null)
                        }
                        {(isTeacher || isAdmin) ?
                          (<li><NavLink to="/addUsers">Add Users</NavLink></li>) : (null)
                        }
                    </ul>
                </div>
            </div>
        </nav>
        );
    }
}
