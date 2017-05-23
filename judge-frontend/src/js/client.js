import React from 'react';
import { render } from 'react-dom';
import { Router, Route, hashHistory, IndexRoute } from 'react-router';
import App from './modules/App';
import Statistics from './modules/Statistics';
import Submissions from './modules/Submissions';
import Submission from './modules/Submission';
import Home from './modules/Home';
import Problem from './modules/Problem';
import Problems from './modules/Problems';
import FacebookLogin from 'react-facebook-login';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class LoginControl extends React.Component {
    constructor(props) {
        super(props);
        this.state = {isLoggedIn: false};
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
                        <Route path="/problem/:problemName" component={Problem} />

                        <Route path="/submissions" component={Submissions}>
                            <Route path="/submissions/:userID/:submissionID" component={Submission}/>
                        </Route>
                        <Route path="/statistics" component={Statistics}/>
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