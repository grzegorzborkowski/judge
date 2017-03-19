import React from 'react';
import {render} from 'react-dom';
import CodeFormComponent from './CodeFormComponent.jsx';
import ProblemDescription from './ProblemDescription.jsx';

class Problem extends React.Component {

    render() {
        return (
           <div className="container">
               <ProblemDescription />
               <CodeFormComponent />
           </div>
        );
    }
}

export default Problem;