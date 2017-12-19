import React from 'react';
import axios from 'axios';
import * as constants from './util.js'
import { Button } from 'react-bootstrap';
import AceEditor from 'react-ace';
import 'brace/mode/c_cpp';
import 'brace/theme/dreamweaver';
import Cookies from 'universal-cookie';

/** This plugin is a workaround to CROS problem.
 * Another (non-hacky) solution is necessary.
 * https://chrome.google.com/webstore/detail/allow-control-allow-origi/nlfbmbojpeacfghkpbjhddihlkkiljbi
 */
class CodeFormComponent extends React.Component {

    constructor(props) {
        super(props);
        console.log(props);
        this.state = {code : this.props.signature, compilationCode : "", runCode: "", testsTotal :"", testsPositive: "", timeTaken: ""};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({code: event});
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.signature !== nextProps.signature) {
            this.handleChange(nextProps.signature);
        }
    }

    handleSubmit(event) {
        var cookies = new Cookies();
        var token = cookies.get("token");
        var self = this;
        axios.post(constants.BACKEND_ADDRESS + constants.JUDGE_ENDPOINT, {
            problemID: this.props.problem_id,
            code: this.state.code}, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            self.setState
            ({
                compilationCode : constants.getCompilationStatus(response["data"].compilationCode),
                runCode : constants.getRunStatus(response["data"].runCode),
                testsTotal : response["data"].testsTotal,
                testsPositive : response["data"].testsPositive,
                timeTaken : response["data"].timeTaken
            });
        }).catch(function (error) {
            console.log(error);
        });
        event.preventDefault();
    }

    /**
     * https://github.com/securingsincity/react-ace
     */
    render() {
        return (
            <div className="row">
                <div className="col-xs-12">
                    <form onSubmit={this.handleSubmit}>
                        <AceEditor mode="c_cpp"
                                   theme="dreamweaver"
                                   value={this.state.code}
                                   width="1000px"
                                   fontSize={constants.ACE_EDITOR_FONT_SIZE}
                                   wrapEnabled={true}
                                   onChange={this.handleChange}/>
                        <br/>
                        <Button
                            type="submit"
                            bsStyle="primary"
                            className="codeFormButton"> Submit
                        </Button>
                    </form>
                    <br/>
                    <div> Compilation result: <b>{this.state.compilationCode} </b></div>
                    <div> Execution result: <b>{this.state.runCode} </b></div>
                    <div> Number of passed tests: <b>{this.state.testsPositive} </b></div>
                    <div> Number of executed tests: <b>{this.state.testsTotal} </b></div>
                    <div> Time taken [s]: <b>{this.state.timeTaken} </b></div>
                </div>
            </div>
        );
    }
}

export default CodeFormComponent;
