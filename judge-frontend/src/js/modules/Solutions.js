import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import { Table } from 'react-bootstrap';

class Solutions extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            id: this.props.routeParams.problemID,
            data: []
        };
    }

    componentWillMount() {
        var self = this;
        axios.get(constants.BACKEND_ADDRESS + constants.SOLUTION_ENDPOINT + self.state.id)
            .then(function (response) {
                let data = response['data'];
                self.setState({
                    data
                });
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    render() {
        return (
            <div>
                <h2>Submissions</h2>
                <div>
                    <Table bordered condensed hover>
                        <thead>
                        <tr>
                            <th> Solution ID </th>
                            <th> Compilation Code </th>
                            <th> RunCode</th>
                            <th> Number of Passed Tests</th>
                            <th> Number of Tests</th>
                            <th> Passed Percentage</th>
                            <th> Time Taken</th>
                            <th> Code</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.data.map(solution =>
                            <tr key={solution.id}>
                                <td>{solution.id} </td>
                                <td>{solution.compilationCode}</td>
                                <td>{solution.runCode}</td>
                                <td>{solution.testsPositive}</td>
                                <td>{solution.testsTotal}</td>
                                <td>{solution.testsPositive * 100 / solution.testsTotal} %</td>
                                <td>{solution.timeTaken}</td>
                                <td><a onClick={() => { alert(solution.code) }}>Click</a></td>
                            </tr>
                        )}
                        </tbody>

                    </Table>
                </div>
                {this.props.children}
            </div>
        )
    }
}

export default Solutions;
