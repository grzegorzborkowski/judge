import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import { Table } from 'react-bootstrap';
import { Button } from 'react-bootstrap';
import { RoleAwareComponent } from 'react-router-role-authorization';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class AddStudent extends RoleAwareComponent {
  constructor(props) {
      super(props);
      this.state = {
        username: "",
        firstName: "",
        lastName: "",
        password: "",
        course: ""
      };

      this.userRoles = cookies.get("judge.role");
      this.allowedRoles = ["teacher", "admin"];

      this.handleInputChange = this.handleInputChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
      this.validateInput = this.validateInput.bind(this);
      this.submitUsers = this.submitUsers.bind(this);
    }

      handleInputChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
      }

      handleSubmit(event) {
          event.preventDefault();
          if(this.validateInput()){
            this.submitUsers();
            window.location.reload();
          }
      }

      validateInput(){
        if(this.state.username.length<1 || this.state.firstName.length<1 ||
          this.state.lastName.length<1 || this.state.password.length<1 ||
          this.state.course.length<1) {
          alert("Fill in the missing gaps")
          return false
        }
        return true
      }

      submitUsers(){
        axios.post(constants.BACKEND_ADDRESS + constants.ADD_STUDENT_ENDPOINT, {
            username: this.state.username,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            password: this.state.password,
            course: this.state.course
        }, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
          console.log(response.data);
          alert(response.data);
        }).catch(function (error) {
          console.log(error.response);
          alert(error.response.data);
        })
      }

      componentWillMount() {
          var self = this;
      }

      render() {
        return (
          <div>
              <h2>Add new student</h2>
              <form onSubmit={this.handleSubmit}>
                  <label>
                      Course name:
                      <br/>
                      <textarea
                          name="course"
                          value={this.state.course}
                          onChange={this.handleInputChange} />
                  </label>
                  <br />
                  <label>
                      Initial password:
                      <br/>
                      <textarea
                          name="password"
                          value={this.state.password}
                          onChange={this.handleInputChange} />
                  </label>
                  <br />
                  <label>
                      Username:
                      <br/>
                      <textarea
                          name="username"
                          value={this.state.username}
                          onChange={this.handleInputChange} />
                  </label>
                  <br />
                  <label>
                      First name:
                      <br/>
                      <textarea
                          name="firstName"
                          value={this.state.firstName}
                          onChange={this.handleInputChange} />
                  </label>
                  <br />
                  <label>
                      Last name:
                      <br/>
                      <textarea
                          name="lastName"
                          value={this.state.lastName}
                          onChange={this.handleInputChange} />
                  </label>
                  <br />
                  <br />
                  <Button type="submit">Add</Button>
              </form>
           </div>
        );
      }
}

export default AddStudent;
