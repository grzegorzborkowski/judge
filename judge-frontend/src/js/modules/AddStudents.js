import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import { Table } from 'react-bootstrap';
import { Button } from 'react-bootstrap';
import { RoleAwareComponent } from 'react-router-role-authorization';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class AddStudents extends RoleAwareComponent {
  constructor(props) {
      super(props);
      this.state = {
        formData: new FormData()
      };

      this.userRoles = cookies.get("judge.role");
      this.allowedRoles = ["teacher", "admin"];

      this.handleInputChange = this.handleInputChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
      this.submitUsers = this.submitUsers.bind(this);
    }

      handleInputChange(event) {
         event.preventDefault();
         this.state.formData = new FormData();
         this.state.formData.append("file", document.getElementById("file").files[0]);
      }

      handleSubmit(event) {
          if(this.state.formData.get("file") != null && this.state.formData.get("file") != 'undefined') {
            this.submitUsers();
            window.location.reload();
          } else {
            alert("You must provide a csv file");
          }
      }

      submitUsers(){
        axios.post(constants.BACKEND_ADDRESS + constants.ADD_STUDENTS_ENDPOINT,
            this.state.formData
        , {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).then(function (response) {
          console.log(response.data);
          alert(response.data);
        }).catch(function (error) {
          alert(error.response.data);
          console.log(error.response);
        });
      }

      componentWillMount() {
          var self = this;
      }

      render() {

          var divStyle = {
              paddingTop: '50px'
          };


          return (
          <div>
              <h2>Add new students</h2>
              <form onSubmit={this.handleSubmit}>
                <h3>Select CSV file</h3>
                <br />
                <input id="file" type="file" value={this.state.file} onChange={this.handleInputChange} />
                <br />
                <button type="submit">Upload</button>
              </form>

              <div>
                <div>
                    <h5 style={divStyle}> Format of the file: </h5> </div>
                <div>    username1,firstname1,lastname1,password1 </div>
              <div>username2,firstname2,lastname2,password2 </div>
              <div> ... </div>
              <div> username_n, firstname_n, lastname_n, password_n</div>
                </div>
           </div>
        );
      }
}

export default AddStudents;
