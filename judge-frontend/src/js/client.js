import React from 'react';
import { render } from 'react-dom';
import { Router, Route, hashHistory, IndexRoute } from 'react-router';
import App from './modules/App';
import Solutions from './modules/Solutions';
import Submissions from './modules/Submissions';
import Submission from './modules/Submission';
import Home from './modules/Home';
import Problem from './modules/Problem';
import ProblemCreator from './modules/ProblemCreator';
import Problems from './modules/Problems';
import FacebookLogin from 'react-facebook-login';
import Cookies from 'universal-cookie';
import axios from 'axios';
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import * as constants from './modules/util.js'

import '../css/Login.css';

const cookies = new Cookies();
axios.defaults.headers.common['Authorization'] = cookies.get("judgeToken");

class LoginControl extends React.Component {
    constructor(props) {
        super(props);
        if(cookies.get("judgeToken")) this.state = {isLoggedIn: true};
        else this.state = {isLoggedIn: false};
    }

    render() {
        const isLoggedIn = this.state.isLoggedIn;
        console.log("Rendering. isLoggedIn :",isLoggedIn);

        let content = null;

        if (isLoggedIn) {
            content =
                <Router history={hashHistory}>
                    <Route path="/" component={App}>
                        <IndexRoute component={Home}/>
                        <Route path="/problems" components={Problems}>
                        </Route>
                        <Route path="/problem/:problemID" component={Problem} />

                        <Route path="/submissions" component={Submissions}>
                        <Route path="/submissions/:userID/:submissionID" component={Submission}/>
                        </Route>
                        <Route path="/problemCreator" components={ProblemCreator}></Route>
                        <Route path="/solutions/:problemID" component={Solutions}/>
                    </Route>
                </Router>;
        } else {
            content =
              <div className="Login">
                <form onSubmit={this.handleSubmit}>
                  <FormGroup controlId="username" bsSize="large">
                    <ControlLabel>Username</ControlLabel>
                    <FormControl
                      autoFocus
                      type="username"
                      value={this.state.username}
                      onChange={this.handleChange}
                    />
                  </FormGroup>
                  <FormGroup controlId="password" bsSize="large">
                    <ControlLabel>Password</ControlLabel>
                    <FormControl
                      value={this.state.password}
                      onChange={this.handleChange}
                      type="password"
                    />
                  </FormGroup>
                  <Button
                    block
                    bsSize="large"
                    disabled={!this.validateForm()}
                    type="submit"
                  >
                    Log in
                  </Button>
                </form>
              </div>
            }

        return (
            <div>
                {content}
            </div>
        );
    }

    validateForm() {
      return this.state.username && this.state.password;
    }

    handleChange = event => {
      this.setState({
      [event.target.id]: event.target.value
      });
    }

    handleSubmit = event => {
      event.preventDefault();
      console.log(this.state.username);
      this.sendLoginRequest(this.state.username, this.state.password);
    }

    sendLoginRequest(username, password) {
            var apiBaseUrl = constants.BACKEND_ADDRESS;
            var self = this;
            var payload={
              "username":username,
              "password":password
            }
            axios.post(apiBaseUrl+'login', payload)
             .then(function (response) {
              console.log(response);

              if(response.status == 200){
                var judgeToken = response.data;
                console.log("Login successfull");
                cookies.set("judgeToken",judgeToken);
                window.location.reload();

              }else{
                console.log("Login failed");
              }
            })
            .catch(function (error) {
              console.log(error);
           });
      }
}

render(
    <LoginControl />,
    document.getElementById('app')
);
