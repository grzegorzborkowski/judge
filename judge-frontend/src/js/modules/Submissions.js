import React from 'react'
import NavLink from './NavLink'
import axios from 'axios';
import * as constants from './util.js'

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
            <table class="table">
                <thead>
                <tr>
                    <th> Submission ID </th>
                    <th> ProblemID </th>
                    <th> Problem Title </th>
                    <th> Compilation Code </th>
                    <th> RunCode</th>
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

                    </tr>
                )}
                </tbody>

            </table>
           </div>
          {this.props.children}
      </div>
    )
  }
}

export default Submissions;