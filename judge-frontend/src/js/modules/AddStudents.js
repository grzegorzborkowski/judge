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

class AddStudents extends RoleAwareComponent {
  constructor(props) {
      super(props);
      this.state = {
        usernames: "",
        password: "",
        course: ""
      };

      this.userRoles = cookies.get("judge.role");
      this.allowedRoles = ["teacher", "admin"];

      this.handleInputChange = this.handleInputChange.bind(this);
      this.handleChangeForUsernames = this.handleChangeForUsernames.bind(this);
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

      handleChangeForUsernames(event) {
          this.setState({usernames: event.value});
      }
      handleChangeForPassword(event) {
          this.setState({password: event.value});
      }
      handleChangeForCourse(event) {
          this.setState({course: event.value});
      }

      handleSubmit(event) {
          event.preventDefault();
          if(this.validateInput()){
            this.submitUsers();
            window.location.reload();
          }
      }

      validateInput(){
        if(this.state.usernames.length<1 || this.state.password.length<1 || this.state.course.length<1) {
          alert("Fill in the missing gaps")
          return false
        }
        return true
      }

      submitUsers(){
        axios.post(constants.BACKEND_ADDRESS + constants.ADD_STUDENTS_ENDPOINT, {
            usernames: this.state.usernames,
            password: this.state.password,
            course: this.state.course
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
              <h2>Add new students</h2>
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
                      Default password:
                      <br/>
                      <textarea
                          name="password"
                          value={this.state.password}
                          onChange={this.handleInputChange} />
                  </label>
                  <br />
                  <label>
                      Comma separated usernames:
                      <br/>
                      <textarea
                          name="usernames"
                          value={this.state.usernames}
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

export default AddStudents;
