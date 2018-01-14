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
        this.state = {code : this.props.signature, compilationStatus : "", runStatus: "", testsTotal :"",
          testsPositive: "", timeTaken: "", resultIcon: "", errorCode: ""};
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
                compilationStatus : constants.getCompilationStatus(response["data"].compilationCode),
                runStatus : constants.getRunStatus(response["data"].runCode),
                runCode : response["data"].runCode,
                testsTotal : response["data"].testsTotal,
                testsPositive : response["data"].testsPositive,
                timeTaken : response["data"].timeTaken,
                resultIcon : constants.getResultIcon(response["data"].runCode,
                  response["data"].testsPositive, response["data"].testsTotal),
                errorCode : response["data"].errorCode
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
                    {
                      this.state.errorCode != null && this.state.errorCode != 'unsigned' && this.state.errorCode != "" ?
                        <div> Error message: <b><pre>{this.state.errorCode}</pre></b></div> : ''
                    }
                    <div> Compilation: <b>{this.state.compilationStatus} </b></div>
                    <div> Execution: <b>{this.state.runStatus} </b></div>
                    <div> Tests passed: <b>{this.state.testsPositive}/{this.state.testsTotal} </b></div>
                    <div> Runtime [s]: <b>{this.state.timeTaken} </b></div>
                    <div> Result: {this.state.resultIcon} </div>
                </div>
            </div>
        );
    }
}

export default CodeFormComponent;
