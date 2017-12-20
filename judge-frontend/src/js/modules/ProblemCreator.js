import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import AceEditor from 'react-ace';
import Autocomplete from 'react-autocomplete';
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
            solution: "",
            categories:[],
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
        axios.get(constants.BACKEND_ADDRESS + constants.CATEGORY_ENDPOINT)
            .then(function (response) {
                let categories = response['data'];
                self.setState({
                    categories
                });
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    //TODO: change structures's textarea to CodeForm
    render() {
          const problemCreatorContent = (
            <div className="ProblemInputForm">
                <h2>My problem adder</h2>
                <form onSubmit={this.handleSubmit}>
                    <label>
                        Category:
                        <br/>
                        <Autocomplete
                            items={this.state.categories}
                            shouldItemRender={(item, value) => item.name.toLowerCase().indexOf(value.toLowerCase()) > -1}
                            getItemValue={item => item.name}
                            renderItem={(item, highlighted) =>
                                <div
                                    key={item.id}
                                    style={{ backgroundColor: highlighted ? '#eee' : 'transparent'}}
                                >
                                    {item.name}
                                </div>
                            }
                            value={this.state.category}
                            onChange={e => this.setState({ category: e.target.value })}
                            onSelect={value => this.setState({ category:value })}
                        />
                    </label>
                    <br />
                    <label>
                        Title:
                        <br/>
                        <textarea
                            rows="2"
                            name="title"
                            value={this.state.title}
                            onChange={this.handleInputChange} />
                    </label>
                    <br />
                    <label>
                        Description:
                        <br/>
                        <textarea
                            rows="5"
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
                                   height="400px"
                                   fontSize={constants.ACE_EDITOR_FONT_SIZE}
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
                         height="800px"
                         fontSize={constants.ACE_EDITOR_FONT_SIZE}
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
