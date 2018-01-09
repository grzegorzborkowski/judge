import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import { Table } from 'react-bootstrap';
import { Button } from 'react-bootstrap';
import { RoleAwareComponent } from 'react-router-role-authorization';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class AddTeacher extends RoleAwareComponent {
  constructor(props) {
      super(props);
      this.state = {
        username: "",
        firstName: "",
        lastName: "",
        password: ""
      };

      this.userRoles = cookies.get("judge.role");
      this.allowedRoles = ["teacher", "admin"];

      this.handleInputChange = this.handleInputChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
      this.validateInput = this.validateInput.bind(this);
      this.submitTeacher = this.submitTeacher.bind(this);
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
            this.submitTeacher();
          }
      }

      validateInput(){
        if(this.state.username.length<1 || this.state.firstName.length<1 ||
          this.state.lastName.length<1 || this.state.password.length<1) {
          alert("Fill in the missing gaps")
          return false
        }
        return true
      }

      submitTeacher(){
        axios.post(constants.BACKEND_ADDRESS + constants.ADD_TEACHER_ENDPOINT, {
            username: this.state.username,
            password: this.state.password,
            firstName: this.state.firstName,
            lastName: this.state.lastName
        }, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
          console.log(response.data);
          alert(response.data);
          window.location.reload();
        }).catch(function (error) {
          console.log(error.response);
          alert(error.response.data);
          window.location.reload();
        })
      }

      componentWillMount() {
          var self = this;
      }

      render() {
        return (
          <div>
              <h2>Add new teacher</h2>
              <form onSubmit={this.handleSubmit}>
                  <label>
                      Username:
                      <br/>
                      <input
                          type="text"
                          name="username"
                          value={this.state.username}
                          onChange={this.handleInputChange} />
                  </label>
                  <br />
                  <label>
                      Default password:
                      <br/>
                      <input
                          type="password"
                          name="password"
                          value={this.state.password}
                          onChange={this.handleInputChange} />
                  </label>
                  <br />
                  <label>
                      First name:
                      <br/>
                      <input
                          type="text"
                          name="firstName"
                          value={this.state.firstName}
                          onChange={this.handleInputChange} />
                  </label>
                  <br />
                  <label>
                      Last name:
                      <br/>
                      <input
                          type="text"
                          name="lastName"
                          value={this.state.lastName}
                          onChange={this.handleInputChange} />
                  </label>
                  <br />
                  <Button type="submit">Add</Button>
              </form>
           </div>
        );
      }
}

export default AddTeacher;
