import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import {Table, Modal} from 'react-bootstrap';
import {Button} from 'react-bootstrap';
import {RoleAwareComponent} from 'react-router-role-authorization';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class CategoryManagement extends RoleAwareComponent {
    constructor(props) {
        super(props);
        this.state = {
            categories: [],
            category: "",
            showRemoveModal: false,
            showEditNameModal: false,
            newCategoryName: ""
        };

        this.userRoles = cookies.get("judge.role");
        this.allowedRoles = ["teacher", "admin"];

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleChangeForCategory = this.handleChangeForCategory.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.submitCategory = this.submitCategory.bind(this);
        this.removeCategory = this.removeCategory.bind(this);
        this.handleShowRemoveModal = this.handleShowRemoveModal.bind(this);
        this.handleCloseRemoveModal = this.handleCloseRemoveModal.bind(this);
        this.changeCategoryName = this.changeCategoryName.bind(this);
        this.handleCloseEditNameModal = this.handleCloseEditNameModal.bind(this);
        this.handleShowEditNameModal = this.handleShowEditNameModal.bind(this);
        this.handleNewCategoryAreaChange = this.handleNewCategoryAreaChange.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    handleChangeForCategory(event) {
        this.setState({category: event.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        this.submitCategory();
        window.location.reload();
    }

    submitCategory() {
        axios.post(constants.BACKEND_ADDRESS + constants.CATEGORY_ADD_ENDPOINT, {
            name: this.state.category
        }, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            console.log(response.data);
            alert(response.data);
        });
    }

    removeCategory(category_id) {
        axios.post(constants.BACKEND_ADDRESS + constants.CATEGORY_REMOVE_ENDPOINT
            + category_id).then(function (response) {
            console.log(response);
            alert(response['data'])
        }).catch(function (error) {
            alert("You can't delete this category!")
        });
        this.handleCloseRemoveModal();
        window.location.reload();
    }

    changeCategoryName(category_id, new_name) {
        var self = this;
        axios.post(constants.BACKEND_ADDRESS + constants.CATEGORY_CHANGE_NAME_ENDPOINT
            + category_id, {
            name: new_name
        }).then(function (response) {
            console.log("Succesfully changed name!" + response)
            window.location.reload();
        });
    }


    handleCloseRemoveModal() {
        this.setState({showRemoveModal: false});
    }

    handleShowRemoveModal() {
        this.setState({showRemoveModal: true});
    }

    handleCloseEditNameModal() {
        this.setState({showEditNameModal: false});
    }

    handleShowEditNameModal() {
        this.setState({showEditNameModal: true});
    }

    handleNewCategoryAreaChange(event) {
        this.setState({newCategoryName: event.target.value});
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
            })
    }



    render() {
        return (
            <div>
                <div className="CategoryInputForm">
                    <h2>Category creator</h2>
                    <form onSubmit={this.handleSubmit}>
                        <label>
                            New category:
                            <br/>
                            <textarea
                                name="category"
                                value={this.state.category}
                                onChange={this.handleInputChange}/>
                        </label>
                        <br/>
                        <Button
                            type="submit">Add
                        </Button>
                    </form>
                </div>

                <div>
                    <h2>{this.props.categoryName}</h2>
                    <Table bordered condensed hover>
                        <thead>
                        <tr>
                            <th>Category</th>
                            <th>Edit Name </th>
                            <th>Delete</th>
                        </tr>
                        </thead>

                        <tbody>
                        {this.state.categories.map(category =>
                            <tr key={category.id}>
                                <td>{category.name}</td>
                                <td><a onClick={() => this.handleShowEditNameModal()}> Edit Name </a></td>
                                <td><a onClick={() => this.handleShowRemoveModal()}>Delete</a></td>

                                <Modal show={this.state.showEditNameModal} onHide={this.handleCloseEditNameModal}>
                                    <Modal.Header closeButton>
                                        <Modal.Title>Changing a category name</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        <h4>Please type in a new category name for a category {category.name} </h4>
                                        <input type="text" value={this.state.newCategoryName}
                                                  onChange={this.handleNewCategoryAreaChange}>
                                        </input>
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button bsStyle="danger" onClick={() => this.changeCategoryName(category.id, this.state.newCategoryName)}
                                                style={{float: 'left'}}> Change Name  </Button>
                                        <Button bsStyle="info" onClick={this.handleCloseEditNameModal}> Close </Button>
                                    </Modal.Footer>
                                </Modal>


                                <Modal show={this.state.showRemoveModal} onHide={this.handleCloseRemoveModal}>
                                    <Modal.Header closeButton>
                                        <Modal.Title>Deleting a category</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        <h4>Are you sure you want to delete a category {category.name} and all problems
                                            and
                                            submissions related to this category? </h4>

                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button bsStyle="danger" onClick={() => this.removeCategory(category.id)}
                                                style={{float: 'left'}}> Yes (Delete) </Button>
                                        <Button bsStyle="info" onClick={this.handleCloseRemoveModal}> No (Close) </Button>
                                    </Modal.Footer>
                                </Modal>
                            </tr>
                        )}
                        </tbody>
                    </Table>
                </div>
            </div>



        );
    }
}

export default CategoryManagement;
