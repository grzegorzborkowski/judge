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

render((
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
    </Router>
), document.getElementById('app'))