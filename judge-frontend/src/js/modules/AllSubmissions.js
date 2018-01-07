import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import { Table } from 'react-bootstrap';
import { Button } from 'react-bootstrap';
import {Link} from 'react-router';
import { RoleAwareComponent } from 'react-router-role-authorization';
import Cookies from 'universal-cookie';


const cookies = new Cookies();

class AllSubmissions extends RoleAwareComponent {

    constructor(props) {
        super(props);
        this.state = {
            id: "",
            data: []
        };

        this.userRoles = cookies.get("judge.role");
        this.allowedRoles = ["teacher", "admin"];

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.findSubmission = this.findSubmission.bind(this);
    }

    componentWillMount() {
        var self = this;
        axios.get(constants.BACKEND_ADDRESS + constants.ALL_SUBMISSIONS)
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

    handleInputChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    handleSubmit(event) {
        event.preventDefault();
        this.findSubmission();
    }

    findSubmission(){
      var self = this;
      console.log(this.state.id)
        axios.get(constants.BACKEND_ADDRESS + constants.SUBMISSION_PER_ID_ENDPOINT +
            this.state.id
        , {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
          if(response['data'] != null && response['data'] != 'undefined' && response['data'] != '') {
            let data = [response['data']];
            self.setState({
                data
            });
          } else {
            alert("There is no such submission");
          }
        });
    }

    render() {
        var options = { hour: 'numeric', minute: 'numeric', month: 'long', day: 'numeric', year: 'numeric'};

        const data = this.state.data;

        return (
          <div>
            <h2>All Submissions</h2>

            <div>
            <form onSubmit={this.handleSubmit}>
              <label>
                  Submission ID:
                  <br/>
                  <input
                      name="id"
                      rows="1"
                      type="number"
                      min="0"
                      max="2147483647"
                      value={this.state.id}
                      onChange={this.handleInputChange} />
              </label>
              <br/>
              <Button
                  type="submit">Find
              </Button>
            </form>
            </div>
            <br/>

            <div>
                <Table bordered condensed hover>
                    <thead>
                    <tr>
                        <th>Submission ID</th>
                        <th>Problem title</th>
                        <th>Status</th>
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
                            <td>{submission.problem.title}</td>
                            <td>{constants.getExecutionStatus(submission.compilationCode, submission.runCode)}</td>
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

export default AllSubmissions;
