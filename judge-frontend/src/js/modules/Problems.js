import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import Category from './Category.js';

class Problems extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            categories: []
        };
    }

    componentWillMount() {
        var self = this;
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

    render() {
        return (
            <div>
                <h2>Problems</h2>
                    {this.state.categories.map(category =>
                        <Category categoryName={category.name} />
                    )}
            </div>
        )
    }
}


export default Problems;
