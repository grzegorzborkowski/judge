import React from 'react';
import CodeFormComponent from './CodeFormComponent';
import axios from 'axios';
import * as constants from './util.js'

class Problem extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            id: this.props.routeParams.problemID,
            description: "",
            title: "",
            signature: ""
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
                    signature: data['signature']
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
                <div> {this.state.signature} </div>
                <CodeFormComponent
                    signature={this.state.signature} problem_id={this.state.id}/>
            </div>
        )
    }
}

export default Problem;
