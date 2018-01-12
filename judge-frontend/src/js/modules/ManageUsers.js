import React from 'react'
import 'brace/mode/c_cpp';
import 'brace/theme/dreamweaver';
import { Button } from 'react-bootstrap';
import {Link} from 'react-router';
import { RoleAwareComponent } from 'react-router-role-authorization';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class ManageUsers extends RoleAwareComponent {

  constructor(props) {
    super(props);

    this.userRoles = cookies.get("judge.role");
    this.allowedRoles = ["teacher", "admin"];
  }

  render() {
    return (
        <div>
          <br />
          <Link to={`/manageUsers/student`}>Add one student</Link>
          <br />
          <br />
          <Link to={`/manageUsers/students`}>Add multiple students</Link>
          <br />
          <br />
          <Link to={`/manageUsers/teacher`}>Add teacher</Link>
          <br />
          <br />
          <Link to={`/manageUsers/changePassword`}>Change Password</Link>
        </div>
      );
    }
}

export default ManageUsers;
