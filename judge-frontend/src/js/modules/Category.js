import React from 'react'
import axios from 'axios';
import * as constants from './util.js'
import {Link} from 'react-router';
import { Table } from 'react-bootstrap';
import Cookies from 'universal-cookie';

const cookies = new Cookies();
class Category extends React.Component {
  constructor(props) {
      super(props);
      this.state = {
          problems: []
      };
  }

  componentWillMount() {
      var self = this;
      axios.get(constants.BACKEND_ADDRESS + constants.PROBLEMS_BY_CATEGORY_ENDPOINT
        + this.props.categoryName)
          .then(function (response) {
              let problems = response['data'];
              self.setState({
                  problems
              });
          })
          .catch(function (error) {
              console.log(error);
          })
  }

  render() {
    return (
      <div>
          <h2>{this.props.categoryName}</h2>
          <Table bordered condensed hover>
            <thead>
              <tr>
                  <th>Problem ID</th>
                  <th>Problem title</th>
                  <th>My submissions</th>

                    {
                    cookies.get('judge.role')=='admin' ?
                      <th>Editor</th> : ''
                    }


                    {
                    cookies.get('judge.role')=='admin' ?
                      <th>Delete</th> : ''
                    }

              </tr>
            </thead>
            <tbody>

            {this.state.problems.map(problem =>
                <tr key={problem.id}>
                  <td><Link to={`/problem/${problem.id}`}>{problem.id}</Link></td>
                  <td><Link to={`/problem/${problem.id}`}>{problem.title}</Link></td>
                  <td><Link to={`/solutions/${problem.id}`}>View</Link></td>

                  {
                  cookies.get('judge.role')=='admin' ?
                    <td><Link to={`/problemEditor/${problem.id}`}>Edit</Link></td> : ''
                  }

                  {
                  cookies.get('judge.role')=='admin' ?
                      <td><a onClick={() => {
                          axios.post(constants.BACKEND_ADDRESS + constants.PROBLEM_REMOVE_ENDPOINT
                            + problem.id).then(function(response){
                              alert(response['data'])
                            }).catch(function(error){
                              alert("You can't delete this problem!")
                            })
                            window.location.reload();
                       }}>Delete</a></td> : ''
                  }

                </tr>
            )}
          </tbody>
        </Table>
      </div>
    );
  }
}

export default Category;
