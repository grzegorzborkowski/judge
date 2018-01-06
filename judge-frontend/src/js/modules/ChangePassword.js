import Cookies from 'universal-cookie';
import React from 'react'
import { RoleAwareComponent } from 'react-router-role-authorization';
import { Button } from 'react-bootstrap';
import * as constants from './util.js'
import axios from 'axios';


const cookies = new Cookies();

class ChangePassword extends RoleAwareComponent {

    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            repeatedPassword: ""
        };
        this.userRoles = cookies.get("judge.role");
        this.allowedRoles = ["teacher", "admin"];

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.validateInput = this.validateInput.bind(this);
        this.submitTeacher = this.submitTeacher.bind(this);
    }

    handleInputChange(event) {
      event.preventDefault();
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
        if(this.state.username.length<1 || this.state.password.length<1 ||
            this.state.repeatedPassword.length<1 ){
            alert("Fill in the missing gaps")
            return false
        }
        if (this.state.password !== this.state.repeatedPassword) {
            alert("Password don't match")
            return false
        }
        return true
    }

    submitTeacher(){
        axios.post(constants.BACKEND_ADDRESS + constants.CHANGE_OTHER_USER_PASSWORD_ENDPOINT, {
            username: this.state.username,
            password: this.state.password
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
                <h2>Change users password </h2>
                <form onSubmit={this.handleSubmit}>
                    <label>
                        Username:
                        <br/>
                        <input type="text"
                            name="username"
                            value={this.state.username}
                            onChange={this.handleInputChange} />
                    </label>
                    <br />
                    <label>
                        New password:
                        <br/>
                        <input type="password"
                            name="password"
                            value={this.state.password}
                            onChange={this.handleInputChange} />
                    </label>
                    <br />
                    <label>
                        Repeat password:
                        <br/>
                        <input type="password"
                            name="repeatedPassword"
                            value={this.state.repeatedPassword}
                            onChange={this.handleInputChange} />
                    </label>
                    <br />
                    <br />
                    <Button type="submit">Change</Button>
                </form>
            </div>
        )
    }

}

export default ChangePassword;
