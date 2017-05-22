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
        const responseFacebook = (response) => {
            var token = response["accessToken"];
            cookies.set("token",token);
            console.log("My token: ", cookies.get("token"));
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
                <div>
                    <h2>To use Judge app please log in:</h2>
                    <FacebookLogin
                        appId="667095053501525"
                        language="en_US"
                        autoLoad={true}
                        callback={responseFacebook}
                        fields="id,name,email,picture"/>
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