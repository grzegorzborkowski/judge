import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import AceEditor from 'react-ace';
import 'brace/mode/c_cpp';
import 'brace/theme/dreamweaver';
import { RoleAwareComponent } from 'react-router-role-authorization';
import Cookies from 'universal-cookie';
import { Button } from 'react-bootstrap';
import Autocomplete from 'react-autocomplete';


const cookies = new Cookies();

class ProblemEditor extends RoleAwareComponent {
    constructor(props) {
        super(props);
        this.state = {
            id: this.props.routeParams.problemID,
            category: "",
            description: "",
            title: "",
            structures: "",
            solution: "",
            categories:[],
            errorCode: ""
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
        var self = this;
        axios.post(constants.BACKEND_ADDRESS + constants.PROBLEM_EDITOR_ENDPOINT, {
            category: this.state.category,
            title: this.state.title,
            description: this.state.description,
            structures: this.state.structures,
            solution: this.state.solution,
            id: this.state.id,
        }, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
          self.setState({
              errorCode: response.data.errorCode});
          alert(response.data.message);
        });
    }

    componentDidMount() {
        var self = this;
        axios.get(constants.BACKEND_ADDRESS + constants.PROBLEM_ID_ENDPOINT + self.state.id)
            .then(function (response) {
                let data = response['data'];
                self.setState({
                    category: data['category']['name'],
                    description: data['description'],
                    title: data['title'],
                    solution: data['solution'],
                    structures: data['structures']
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
          const problemEditorContent = (
            <div className="ProblemInputForm">
                <h2>My problem editor</h2>
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
                            name="title"
                            rows="2"
                            maxlength="255"
                            value={this.state.title}
                            onChange={this.handleInputChange} />
                    </label>
                    <br />
                    <label>
                        Description:
                        <br/>
                        <textarea
                            name="description"
                            rows="5"
                            maxlength="255"
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
                    {
                      this.state.errorCode != null && this.state.errorCode != 'unsigned' && this.state.errorCode != "" ?
                        <div> Error message: <b><pre>{this.state.errorCode}</pre></b></div> : ''
                    }
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

        return this.rolesMatched() ? problemEditorContent : notAuthorized;
    }
}

export default ProblemEditor;
