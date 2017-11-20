import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import AceEditor from 'react-ace';
import 'brace/mode/c_cpp';
import 'brace/theme/dreamweaver';
import { Button } from 'react-bootstrap';
import {Link} from 'react-router';
import { RoleAwareComponent } from 'react-router-role-authorization';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class AddUsers extends RoleAwareComponent {

  constructor(props) {
    super(props);

    this.userRoles = cookies.get("judge.role");
    this.allowedRoles = ["teacher", "admin"];
  }

  render() {
    return (
        <div>
          <br />
          <Button><Link to={`/addUsers/students`}>Add students</Link></Button>
          <br />
          <br />
          <Button><Link to={`/addUsers/teacher`}>Add teacher</Link></Button>
        </div>
      );
    }
}

export default AddUsers;
