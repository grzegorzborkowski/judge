import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import AceEditor from 'react-ace';
import 'brace/mode/c_cpp';
import 'brace/theme/dreamweaver';
import {Link} from 'react-router';
import { Table } from 'react-bootstrap';
import $ from 'jquery';


class ProblemCreator extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            description: "",
            title: "",
            structures: "",
            solution: ""
        };

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
        alert('A problem has been submitted: ' + this.state.title);
        event.preventDefault();
        this.submitProblem();
    }

    submitProblem(){
        axios.post(constants.BACKEND_ADDRESS + constants.PROBLEM_CREATOR_ENDPOINT, {
            title: this.state.title,
            description: this.state.description,
            structures: this.state.structures,
            solution: this.state.solution
        }, {
            headers: {
                'Content-Type': 'application/json'
            }
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
        return (
            <div>
                <h2>My problem adder</h2>
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
        )
    }

}

export default ProblemCreator;
