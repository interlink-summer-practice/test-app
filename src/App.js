import React, { Component } from 'react';
import './App.css';
import TestPassing from './components/test-passing/TestPassing';

export default class App extends Component {

  render(){
    return(
      <div className={App}>
        <TestPassing/>
      </div>

    );
  }

}
