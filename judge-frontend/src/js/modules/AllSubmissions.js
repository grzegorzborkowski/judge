import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import { Table } from 'react-bootstrap';
import TableFilter from 'react-table-filter';
import styles from './example.css';

// https://github.com/cheekujha/react-table-filter/blob/master/examples/index.js
class AllSubmissions extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: []
        };
        this._filterUpdated = this._filterUpdated.bind(this);
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


    _filterUpdated(newData){
        this.setState({
            data: newData
        });
    }

    render() {
        var options = { hour: 'numeric', minute: 'numeric', month: 'long', day: 'numeric', year: 'numeric'};

        const data = this.state.data;
        const elementsHTML = data.map(submission => {
            return (
                <tr key={submission.id}>
                    <td>{submission.id}</td>
                    <td>{submission.problem.id}</td>
                    <td>{submission.problem.title}</td>
                    <td>{constants.getCompilationStatus(submission.compilationCode)}</td>
                    <td>{constants.getRunStatus(submission.runCode)}</td>
                    <td>{submission.testsPositive}/{submission.testsTotal}</td>
                    <td>{submission.timeTaken} </td>
                    <td><a onClick={() => {
                        alert(submission.code)
                    }}>Click</a></td>
                    <td>{new Date(submission.date).toLocaleDateString(window.navigator.userLanguage || window.navigator.language, options)} </td>
                    <td>{constants.getResultIcon(submission.runCode,
                        submission.testsPositive, submission.testsTotal)} </td>
                </tr>
            );
        });


        return (

            <div>
                  <table>
                    <thead>
                <TableFilter
                    rows={data}
                    onFilterUpdate={this._filterUpdated}>
                    <th filterkey="id">
                        Submission ID
                    </th>
                    <th filterkey="problemid">
                        Problem ID
                    </th>

                </TableFilter>
                    </thead>

                <tbody>
                    {elementsHTML}
                </tbody>
                  </table>
            </div>
        )
    }
}

export default AllSubmissions;
