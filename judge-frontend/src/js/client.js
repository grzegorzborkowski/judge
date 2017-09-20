import React from 'react';
import { render } from 'react-dom';
import { Router, Route, hashHistory, IndexRoute } from 'react-router';
import App from './modules/App';
import Statistics from './modules/Statistics';
import Submissions from './modules/Submissions';
import Submission from './modules/Submission';
import Home from './modules/Home';
import Problem from './modules/Problem';
import ProblemCreator from './modules/ProblemCreator';
import Problems from './modules/Problems';
import FacebookLogin from 'react-facebook-login';
import Cookies from 'universal-cookie';
import axios from 'axios';
import * as constants from './modules/util.js'

const cookies = new Cookies();

class LoginControl extends React.Component {
    constructor(props) {
        super(props);
        this.state = {isLoggedIn: false};
        this.addNewUser = this.addNewUser.bind(this);
    }

    addNewUser(response) {
        var self = this;
        axios.post(constants.BACKEND_ADDRESS + constants.ADD_STUDENT_ENDPOINT, {
            username: response["name"],
            email: response["email"],
            facebookID: response["id"]}, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            console.log(response["data"].status);
        }).catch(function (error) {
            console.log(error);
        });
    }

    render() {
        const isLoggedIn = this.state.isLoggedIn;
        console.log("Rendering. isLoggedIn :",isLoggedIn);
        const responseFacebook = (response) => {
            var token = response["accessToken"];
            var facebookID = response["id"];
            cookies.set("token",token);
            cookies.set("facebookID", facebookID);
            console.log(response);
            if (!response.status) {
                console.log("Success!");
                this.setState({isLoggedIn: true});
                this.addNewUser(response);
            } else {
                console.log("Authorization failed : " + response.status)
            }
        };
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
                        {/*<Route path="/statistics" component={Statistics}/>*/}
                    </Route>
                </Router>;
        } else {
            content =
                <div class="container">
                    <div class="center">
                        <h4>To use Judge app please log in:</h4>
                        <FacebookLogin
                            appId="667095053501525"
                            language="en_US"
                            autoLoad={true}
                            callback={responseFacebook}
                            cssClass="my-facebook-button-class"
                            fields="id,name,email,picture"/>
                    </div>
                </div>;
        }

        return (
            <div>
                {content}
            </div>
        );
    }
}

render(
    <LoginControl />,
    document.getElementById('app')
);
