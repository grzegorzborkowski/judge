import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import { Table } from 'react-bootstrap';
import {Link} from 'react-router';

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
        axios.get(constants.BACKEND_ADDRESS + constants.SUBMISSIONS_FOR_PROBLEM_FOR_USER_ENDPOINT + self.state.id )
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
        var options = { hour: 'numeric', minute: 'numeric', month: 'long', day: 'numeric', year: 'numeric'};

        return (
            <div>
              <h2>Submissions</h2>
              <div>
                  <Table bordered condensed hover>
                      <thead>
                      <tr>
                          <th>Submission ID</th>
                          <th>Compilation</th>
                          <th>Execution</th>
                          <th>Tests passed</th>
                          <th>Runtime [s]</th>
                          <th>View</th>
                          <th>Date</th>
                          <th>Result</th>
                      </tr>
                      </thead>
                      <tbody>
                      {this.state.data.map(submission =>
                          <tr key={submission.id}>
                              <td>{submission.id}</td>
                              <td>{constants.getCompilationStatus(submission.compilationCode)}</td>
                              <td>{constants.getRunStatus(submission.runCode)}</td>
                              <td>{submission.testsPositive}/{submission.testsTotal}</td>
                              <td>{submission.timeTaken} </td>
                              <td><Link to={`/problem/${submission.problem.id}/${submission.id}`}>Click</Link></td>
                              <td>{new Date(submission.date).toLocaleDateString(window.navigator.userLanguage || window.navigator.language, options)} </td>
                              <td>{constants.getResultIcon(submission.runCode,
                                submission.testsPositive, submission.testsTotal)}</td>
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
