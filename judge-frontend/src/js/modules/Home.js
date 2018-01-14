import React from 'react';
import list_img from '../../../images/solve_problem_list.png'
import upper_img from '../../../images/solve_problem_structs.png'
import student_img from '../../../images/solve_problem_submit.png'


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
                <b>To solve a problem, choose one from the list first.</b>
                <br/>
                <br/>
                <img src={list_img} />
                <br/>
                <br/>
                <b>In upper section, you can see problem's description and structure definitions</b>
                <br/>
                <br/>
                <img src={upper_img} />
                <br/>
                <br/>
                <b>Here, you should write your code. When you submit it, you will receive the results of the run of your code</b>
                <br/>
                <br/>
                <img src={student_img} />
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
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
