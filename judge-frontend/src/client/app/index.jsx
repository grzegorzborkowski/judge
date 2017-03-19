import React from 'react';
import {render} from 'react-dom';
import Problem from './Problem.jsx';

class App extends React.Component {

    render() {
        return (
                <Problem />
        );
    }
}

render(<App/>, document.getElementById('app'));