import React from 'react'
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
          <Button><Link to={`/addUsers/student`}>Add one student</Link></Button>
          <br />
          <br />
          <Button><Link to={`/addUsers/students`}>Add multiple students</Link></Button>
          <br />
          <br />
          <Button><Link to={`/addUsers/teacher`}>Add teacher</Link></Button>
          <br />
          <br />
          <Button><Link to={`/addUsers/changePassword`}> Change Password</Link></Button>
        </div>
      );
    }
}

export default AddUsers;
