import React from 'react';
import Cookies from 'universal-cookie';
import NavLink from './NavLink';
import { Button, ButtonToolbar, DropdownButton, MenuItem } from 'react-bootstrap';

const cookies = new Cookies();

class Navbar extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            collapsed: true,
            isLoggedIn: ""
        };
        this.logout = this.logout.bind(this);
        this.toggleCollapse = this.toggleCollapse.bind(this);
    }

    toggleCollapse() {
      var self = this;
        let newCollapsed = !this.state.collapsed;
        self.setState({collapsed: newCollapsed});
    }

    logout() {
      var self = this;
        console.log("Logging out.");
        cookies.remove("judge.token");
        cookies.remove("judge.role");
        self.setState({isLoggedIn: false});
        window.location = '/';
    }

    render() {
        const collapsed = this.state.collapsed;
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
                    <a class="nav navbar-brand"><NavLink to="/">Project Judge</NavLink></a>
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
                    <ul class="nav navbar-nav navbar-right">
                      <li onClick={this.logout}>
                        <span class="glyphicon glyphicon-log-out"></span>
                        <Button bsStyle="link" onClick={this.logout}>Log out</Button>
                      </li>
                    </ul>
                </div>
            </div>
        </nav>
        );
    }
}

export default Navbar;
