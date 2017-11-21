import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import AceEditor from 'react-ace';
import 'brace/mode/c_cpp';
import 'brace/theme/dreamweaver';
import {Link} from 'react-router';
import { Table } from 'react-bootstrap';
import { Button } from 'react-bootstrap';
import { RoleAwareComponent } from 'react-router-role-authorization';
import $ from 'jquery';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class ProblemCreator extends RoleAwareComponent {
    constructor(props) {
        super(props);
        this.state = {
            category: "",
            description: "",
            title: "",
            structures: "",
            solution: ""
        };

        this.userRoles = cookies.get("judge.role");
        this.allowedRoles = ["teacher", "admin"];

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleChangeForStructures = this.handleChangeForStructures.bind(this);
        this.handleChangeForSolution = this.handleChangeForSolution.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.submitProblem = this.submitProblem.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    handleChangeForStructures(event) {
        this.setState({structures: event});
    }

    handleChangeForSolution(event) {
        this.setState({solution: event});
    }

    handleSubmit(event) {
        event.preventDefault();
        this.submitProblem();
    }

    submitProblem(){
        axios.post(constants.BACKEND_ADDRESS + constants.PROBLEM_CREATOR_ENDPOINT, {
            category: this.state.category,
            title: this.state.title,
            description: this.state.description,
            structures: this.state.structures,
            solution: this.state.solution
        }, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
          console.log(response.data);
          alert(response.data);
        });
    }

    componentDidMount() {
        var self = this;
        axios.get(constants.BACKEND_ADDRESS + constants.PROBLEM_TEMPLATE_ENDPOINT)
            .then(function (response) {
                let data = response['data'];
                self.setState({
                    structures: data['structures'],
                    solution: data['solution']
                });
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    //TODO: change structures's textarea to CodeForm
    render() {
          const problemCreatorContent = (
            <div>
                <h2>My problem adder</h2>
                <form onSubmit={this.handleSubmit}>
                    <label>
                        Category:
                        <br/>
                        <textarea
                            name="category"
                            value={this.state.category}
                            onChange={this.handleInputChange} />
                    </label>
                    <br />
                    <label>
                        Title:
                        <br/>
                        <textarea
                            name="title"
                            value={this.state.title}
                            onChange={this.handleInputChange} />
                    </label>
                    <br />
                    <label>
                        Description:
                        <br/>
                        <textarea
                            name="description"
                            value={this.state.description}
                            onChange={this.handleInputChange} />
                    </label>
                    <br/>
                    <label>
                        Structures:
                        <br/>
                        <AceEditor mode="c_cpp"
                                   theme="dreamweaver"
                                   value={this.state.structures}
                                   width="600px"
                                   fontSize={18}
                                   wrapEnabled={true}
                                   name="structures"
                                   onChange={this.handleChangeForStructures}/>
                    </label>
                    <br/>
                    <label>
                        Solution:
                        <br/>
                        <AceEditor mode="c_cpp"
                         theme="dreamweaver"
                         value={this.state.solution}
                         width="600px"
                         fontSize={18}
                         wrapEnabled={true}
                         name="solution"
                         onChange={this.handleChangeForSolution}/>
                    </label>
                    <br/>
                    <br/>
                    <Button
                        type="submit">Submit
                    </Button>
                </form>
            </div>
        );

        const notAuthorized = (
          <div>
            Access denied.
          </div>
        );

        return this.rolesMatched() ? problemCreatorContent : notAuthorized;
    }
}

export default ProblemCreator;
