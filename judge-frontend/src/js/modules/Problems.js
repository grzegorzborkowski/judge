import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import {Link} from 'react-router';
import { Table } from 'react-bootstrap';

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

                <Table bordered condensed hover>
                    <thead>
                    <tr>
                        <th>Problem ID</th>
                        <th>Problem title</th>
                        <th>Solutions</th>
                        <th>Editor</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.problems.map(problem =>
                        <tr key={problem.id}>

                            <td><Link to={`/problem/${problem.id}`}>{problem.id}</Link></td>
                            <td><Link to={`/problem/${problem.id}`}>{problem.title}</Link></td>
                            <td><Link to={`/solutions/${problem.id}`}>View</Link></td>
                            <td><Link to={`/problemEditor/${problem.id}`}>Edit</Link></td>
                        </tr>
                    )}
                    </tbody>
                </Table>


            </div>
        )
    }

}

export default Problems;
