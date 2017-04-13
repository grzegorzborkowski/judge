import React from 'react';
import Navbar from './Navbar';

export default React.createClass({
  render() {
      const containerStyle = {
          marginTop: "60px"
      };

    return (
      <div>
       <Navbar />
       <div class="container" style={containerStyle}>
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
