import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import AceEditor from 'react-ace';
import 'brace/mode/c_cpp';
import 'brace/theme/dreamweaver';
import { RoleAwareComponent } from 'react-router-role-authorization';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class ProblemEditor extends RoleAwareComponent {
    constructor(props) {
        super(props);
        this.state = {
            id: this.props.routeParams.problemID,
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
        alert('A problem has been edited: ' + this.state.title);
        event.preventDefault();
        this.submitProblem();
    }

    submitProblem(){
        axios.post(constants.BACKEND_ADDRESS + constants.PROBLEM_EDITOR_ENDPOINT, {
            title: this.state.title,
            description: this.state.description,
            structures: this.state.structures,
            solution: this.state.solution,
            id: this.state.id,
        }, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    componentDidMount() {
        var self = this;
        axios.get(constants.BACKEND_ADDRESS + constants.PROBLEM_ID_ENDPOINT + self.state.id)
            .then(function (response) {
                let data = response['data'];
                self.setState({
                    description: data['description'],
                    title: data['title'],
                    solution: data['solution'],
                    structures: data['structures']
                });
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    //TODO: change structures's textarea to CodeForm
    render() {
          const problemEditorContent = (
            <div>
                <h2>My problem editor</h2>
                <form onSubmit={this.handleSubmit}>
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
                    <input type="submit" value="Submit" />
                </form>
            </div>
        );

        const notAuthorized = (
          <div>
            Access denied.
          </div>
        );

        return this.rolesMatched() ? problemEditorContent : notAuthorized;
    }
}

export default ProblemEditor;
