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

class AddTeacher extends RoleAwareComponent {
  constructor(props) {
      super(props);
      this.state = {
        username: "",
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
            window.location.reload();
          }
      }

      validateInput(){
        if(this.state.username.length<1 || this.state.password.length<1) {
          alert("Fill in the missing gaps")
          return false
        }
        return true
      }

      submitTeacher(){
        axios.post(constants.BACKEND_ADDRESS + constants.ADD_TEACHER_ENDPOINT, {
            username: this.state.username,
            password: this.state.password
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
      }

      render() {
        return (
          <div>
              <h2>Add new teacher</h2>
              <form onSubmit={this.handleSubmit}>
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
                      Default password:
                      <br/>
                      <textarea
                          name="password"
                          value={this.state.password}
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

export default AddTeacher;
