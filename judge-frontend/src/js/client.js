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
import AppBar from 'material-ui/AppBar';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import * as constants from './modules/util.js'

const cookies = new Cookies();
//cookies.set("judgeBasicToken", "Basic YWRtaW46YWRtaW4=")
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
            <div>
              <MuiThemeProvider>
                <div>
                <AppBar
                   title="Login"
                 />
                 <TextField
                   hintText="Enter your Username"
                   floatingLabelText="Username"
                   onChange = {(event,newValue) => this.setState({username:newValue})}
                   />
                 <br/>
                   <TextField
                     type="password"
                     hintText="Enter your Password"
                     floatingLabelText="Password"
                     onChange = {(event,newValue) => this.setState({password:newValue})}
                     />
                   <br/>
                   <RaisedButton label="Submit" primary={true} onClick={(event) => this.handleClick(event)}
                 />
               </div>
            </MuiThemeProvider>
          </div>
        }

        return (
            <div>
                {content}
            </div>
        );
    }

    handleClick(event){
        var apiBaseUrl = "http://localhost:8080/";
        var self = this;
        var payload={
          "username":this.state.username,
          "password":this.state.password
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
