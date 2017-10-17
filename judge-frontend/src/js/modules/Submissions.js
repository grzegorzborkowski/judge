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
    axios.get(constants.BACKEND_ADDRESS + constants.SUBMISSION_ENDPOINT)
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
                    <th> Submission ID </th>
                    <th> ProblemID </th>
                    <th> Problem Title </th>
                    <th> Compilation Code </th>
                    <th> RunCode</th>
                    <th> Number of Passed Tests</th>
                    <th> Number of Tests</th>
                    <th> Passed Percentage</th>
                    <th> Time Taken</th>
                </tr>
                </thead>
                <tbody>
                {this.state.data.map(submission =>
                    <tr key={submission.id}>
                        <td>{submission.id} </td>
                        <td> {submission.problem.id}</td>
                        <td> {submission.problem.title}</td>
                        <td>{submission.compilationCode}</td>
                        <td>{submission.runCode}</td>
                        <td>{submission.testsPositive}</td>
                        <td>{submission.testsTotal}</td>
                        <td>{submission.testsPositive * 100 / submission.testsTotal} %</td>
                        <td>{submission.timeTaken}</td>

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