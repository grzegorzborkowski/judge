import React from 'react';
import axios from 'axios';
import * as constants from './util.jsx'
import { Button } from 'react-bootstrap';
import AceEditor from 'react-ace';
import 'brace/mode/c_cpp';
import 'brace/theme/dreamweaver';


/** This plugin is a workaround to CROS problem.
 * Another (non-hacky) solution is necessary.
 * https://chrome.google.com/webstore/detail/allow-control-allow-origi/nlfbmbojpeacfghkpbjhddihlkkiljbi
 */
class CodeFormComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {code : '', compilationResult : "", executionResult: ""};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        console.log(this.state);
    }

    handleChange(event) {
        this.setState({code: event});
    }

    handleSubmit(event) {
        axios.post(constants.BACKEND_ADDRESS + constants.JUDGE_ENDPOINT, {
           code: this.state.code}, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            console.log(response);
        })
            .catch(function (error) {
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
                           fontSize={18}
                           wrapEnabled={true}
                           onChange={this.handleChange}/>
                        <Button
                                type="submit"
                                bsStyle="primary"
                                className="codeFormButton"> Submit
                        </Button>
                    </form>

                <div> Rezultat kompilacji: {this.state.compilationResult} </div>
                <div> Rezultat wykonania: {this.state.executionResult} </div>
                </div>
            </div>
        );
    }

}

export default CodeFormComponent;