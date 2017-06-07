import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import {Link} from 'react-router';

class Problems extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            problems: []
        };
    }

    componentWillMount() {
        console.log("przed zaladowaniem sie rendera");
        var self = this;
        axios.get(constants.BACKEND_ADDRESS + constants.PROBLEM_ENDPOINT)
            .then(function (response) {
                let problems = response['data'];
                self.setState({
                    problems
                });
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    render() {
        return (
            <div>
                <h2>Problems</h2>
                {this.props.children}

                <div>
                    {this.state.problems.map(problem =>
                        <div>
                    <Link to={`/problem/${problem.id}`}> {problem.title}  </Link>
                        </div>
                    )}
                </div>
            </div>
        )
    }

}

export default Problems;