import React from 'react';
import {render} from 'react-dom';

class ProblemDescription extends React.Component {

    render() {
        return (
            <div>
                <div><b>Ciąg Fibonacciego</b></div>
                <div> Mając podaną liczbę naturalną n, podaj n-ty wyraz ciągu fibonacciego </div>
            </div>
        );
    }
}

export default ProblemDescription;