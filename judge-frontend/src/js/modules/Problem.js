import React from 'react';
import ProblemDescription from './ProblemDesciption';
import CodeFormComponent from './CodeFormComponent';

export default React.createClass({
    render() {
        return (
            <div>
                <h2>{this.props.params.problemName}</h2>
                <ProblemDescription />
                <CodeFormComponent />
            </div>
        )
    }
})
