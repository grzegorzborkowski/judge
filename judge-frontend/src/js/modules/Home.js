import React from 'react';
import list_img from '../../../images/solve_problem_list.png'
import upper_img from '../../../images/solve_problem_structs.png'
import student_img from '../../../images/solve_problem_submit.png'

import { Table } from 'react-bootstrap';

export default React.createClass({
  render() {
    return (
        <div>
            <h2>
                Welcome to Project Judge website.
            </h2>
            <br/>
            <br/>
            <div>
                <b>Hello, fellow student!</b>
                <br/>
                You've entered main page of <i>Project Judge</i> - learning tool for fresh programmers!
                <br/>
                <br/>
                <Table responsive hover>
                    <tbody>
                        <tr>
                            <td>To solve a problem, choose one from the list first.</td>
                            <td><img src={list_img} /></td>
                        </tr>
                        <tr>
                            <td>In upper section, you can see problem's description and structure definitions</td>
                            <td><img src={upper_img} /></td>
                        </tr>
                        <tr>
                            <td>Here, you should write your code. When you submit it, you will receive the results of the run of your code</td>
                            <td><img src={student_img} /></td>
                        </tr>
                    </tbody>
                </Table>

                <div>
                  <br/>
                  <i>Project Judge</i> has been created as a bachelor thesis project in 2018 by:
                  <br/>
                  Anna Anioł, Grzegorz Borkowski, Jakub Tustanowski
                  <br/>
                  <br/>
                </div>
            </div>
        </div>
    )
  }
})
