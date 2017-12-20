import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import { Table } from 'react-bootstrap';

class Submissions extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: []
        };
    }

  componentWillMount() {
    var self = this;
    axios.get(constants.BACKEND_ADDRESS + constants.SUBMISSIONS_FOR_USER_ENDPOINT)
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
                    <th>Submission ID</th>
                    <th>Problem ID</th>
                    <th>Problem title</th>
                    <th>Compilation</th>
                    <th>Execution</th>
                    <th>Tests passed</th>
                    <th>Runtime [s]</th>
                    <th>Code</th>
                    <th>Result</th>
                </tr>
                </thead>
                <tbody>
                {this.state.data.map(submission =>
                    <tr key={submission.id}>
                        <td>{submission.id}</td>
                        <td>{submission.problem.id}</td>
                        <td>{submission.problem.title}</td>
                        <td>{constants.getCompilationStatus(submission.compilationCode)}</td>
                        <td>{constants.getRunStatus(submission.runCode)}</td>
                        <td>{submission.testsPositive}/{submission.testsTotal}</td>
                        <td>{submission.timeTaken} </td>
                        <td><a onClick={() => { alert(submission.code) }}>Click</a></td>
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

export default Submissions;
