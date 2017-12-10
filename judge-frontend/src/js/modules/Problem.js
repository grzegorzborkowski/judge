import React from 'react';
import CodeFormComponent from './CodeFormComponent';
import axios from 'axios';
import * as constants from './util.js'
import AceEditor from 'react-ace';
import 'brace/mode/c_cpp';
import 'brace/theme/dreamweaver';

class Problem extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            id: this.props.routeParams.problemID,
            description: "",
            title: "",
            signature: "",
            structures:""
        };
    }

    componentDidMount() {
        var self = this;
        axios.get(constants.BACKEND_ADDRESS + constants.PROBLEM_ID_ENDPOINT + self.state.id)
            .then(function (response) {
                let data = response['data'];
                self.setState({
                    description: data['description'],
                    title: data['title'],
                    signature: data['signature'],
                    structures: data['structures']
                });
                console.log(self.state.signature);
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    render() {
        return (
            <div>
                <div><b>{this.state.title}</b></div>
                <div> {this.state.description} </div>
                <br/>
                <div><AceEditor mode="c_cpp"
                           theme="dreamweaver"
                           value={this.state.structures}
                           width="1000px"
                           name="Structure editor"
                           fontSize={18}
                           wrapEnabled={true}
                           readOnly={true}
                           /></div>
                <br/>
                <br/>
                <CodeFormComponent
                    signature={this.state.signature} problem_id={this.state.id}/>
            </div>
        )
    }
}

export default Problem;
