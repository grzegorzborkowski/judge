import React from 'react';
import { Table } from 'react-bootstrap';
import { Button } from 'react-bootstrap';
import axios from 'axios';
import * as constants from './util.js'
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class AccountManagement extends React.Component {

  constructor(props) {
      super(props);
      this.state = {
        password1: "",
        password2: ""
      };

      this.handleInputChange = this.handleInputChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
      this.validateInput = this.validateInput.bind(this);
      this.submitChanges = this.submitChanges.bind(this);
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
            this.submitChanges();
            window.location.reload();
          }
      }

      validateInput(){
        if(this.state.password1 != this.state.password2) {
          alert("Your password and confirmation password do not match")
          return false
        }
        return true
      }

      submitChanges(){
        axios.post(constants.BACKEND_ADDRESS + constants.PASSWORD_CHANGE_ENDPOINT, {
            password: this.state.password1,
        }, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(function (response) {
          cookies.set("judge.token",response['data']['token']);
          console.log("Password has been successfully changed");
          alert("Password has been successfully changed");
        })
        .catch(function (error) {
            console.log("Operation failed");
        })
      }

      componentWillMount() {
          var self = this;
      }

  render() {
    return (
        <div>
          <br />
          You are logged in as <b>{cookies.get("judge.username")}</b>.
          <br />
          <br />

          <form onSubmit={this.handleSubmit}>
              <label>
                  New password:
                  <br/>
                  <input type="password"
                      name="password1"
                      value={this.state.password1}
                      onChange={this.handleInputChange} />
              </label>
              <br />
              <label>
                  Repeat password:
                  <br/>
                  <input type="password"
                      name="password2"
                      value={this.state.password2}
                      onChange={this.handleInputChange} />
              </label>
              <br />
              <Button type="submit">Confirm</Button>
          </form>
        </div>
    )
  }
}

export default AccountManagement;
