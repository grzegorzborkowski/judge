import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import AceEditor from 'react-ace';
import 'brace/mode/c_cpp';
import 'brace/theme/dreamweaver';
import { Table } from 'react-bootstrap';
import { Button } from 'react-bootstrap';
import { RoleAwareComponent } from 'react-router-role-authorization';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class CategoryManagement extends RoleAwareComponent {
  constructor(props) {
      super(props);
      this.state = {
        categories: [],
        category: ""
      };

      this.userRoles = cookies.get("judge.role");
      this.allowedRoles = ["teacher", "admin"];

      this.handleInputChange = this.handleInputChange.bind(this);
      this.handleChangeForCategory = this.handleChangeForCategory.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
      this.submitCategory = this.submitCategory.bind(this);
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

      submitCategory(){
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
                          onChange={this.handleInputChange} />
                  </label>
                  <br />
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
                      <th>Delete</th>
                  </tr>
                </thead>

              <tbody>
              {this.state.categories.map(category =>
                  <tr key={category.id}>
                    <td>{category.name}</td>
                    <td><a onClick={() => {
                        axios.post(constants.BACKEND_ADDRESS + constants.CATEGORY_REMOVE_ENDPOINT
                          + category.id).then(function(response){
                            alert(response['data'])
                          }).catch(function(error){
                            alert("You can't delete this category!")
                          })
                          window.location.reload();
                     }}>Delete</a></td>
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
