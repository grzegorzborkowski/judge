import React from 'react';
import Navbar from './Navbar';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

export default React.createClass({

    logout: function() {
        console.log("Logging out.");
        cookies.remove("judgeToken");
        this.setState({isLoggedIn: false});
        window.location = '/';
    },

  render() {
      const containerStyle = {
          marginTop: "60px"
      };

    return (
      <div>
       <Navbar />
       <div class="container" style={containerStyle}>
       <button onClick={this.logout}>Log out</button>
         <div class="row">
           <div class="col-lg-12">
           {this.props.children}
           </div>
         </div>
        </div>
      </div>
    )
  }
})
