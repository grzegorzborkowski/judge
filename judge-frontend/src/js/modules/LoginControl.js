import React from 'react';
import { render } from 'react-dom';
import { Router, Route, hashHistory, IndexRoute } from 'react-router';
import App from './App';
import Solutions from './Solutions';
import Submissions from './Submissions';
import Submission from './Submission';
import Home from './Home';
import Problem from './Problem';
import ProblemCreator from './ProblemCreator';
import ProblemEditor from './ProblemEditor';
import Problems from './Problems';
import CategoryManagement from './CategoryManagement';
import AddUsers from './AddUsers';
import AddStudents from './AddStudents';
import AddTeacher from './AddTeacher';
import AccountManagement from './AccountManagement';
import Cookies from 'universal-cookie';
import axios from 'axios';
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import * as constants from './util.js'

const cookies = new Cookies();

class LoginControl extends React.Component {
    constructor(props) {
        super(props);
        if(cookies.get("judge.token")) this.state = {isLoggedIn: true};
        else this.state = {isLoggedIn: false};

        this.validateForm = this.validateForm.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.sendLoginRequest = this.sendLoginRequest.bind(this);

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
                        <Route path="/accountManagement" component={AccountManagement}>
                        </Route>
                        <Route path="/problems" components={Problems}>
                        </Route>
                        <Route path="/problem/:problemID" component={Problem} />
                        <Route path="/problem/:problemID/:submissionID" component={Problem} />
                        <Route path="/submissions" component={Submissions}>
                        <Route path="/submissions/:userID/:submissionID" component={Submission}/>
                        </Route>
                        <Route authorize={['teacher', 'admin']} path="/problemCreator" components={ProblemCreator}>
                        </Route>
                        <Route authorize={['teacher', 'admin']} path="/problemEditor/:problemID" components={ProblemEditor}>
                        </Route>
                        <Route authorize={['teacher', 'admin']} path="/categoryManagement" components={CategoryManagement}>
                        </Route>
                        <Route authorize={['teacher', 'admin']} path="/addUsers" components={AddUsers}>
                        </Route>
                        <Route authorize={['teacher', 'admin']} path="/addUsers/students" components={AddStudents}>
                        </Route>
                        <Route authorize={['teacher', 'admin']} path="/addUsers/teacher" components={AddTeacher}>
                        </Route>

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
                var judgeToken = response.data.token;
                var roleArray = [response.data.role];
                console.log("Login successfull");
                cookies.set("judge.token",judgeToken);
                cookies.set("judge.role",roleArray);
                cookies.set("judge.username",username);
                window.location.reload();
              }else{
                console.log("Login failed");
              }
            })
            .catch(function (error) {
              alert("Bad credentials!");
              console.log(error);
            })
      }
}

export default LoginControl;
