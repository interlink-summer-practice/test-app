import React, { Component } from 'react';
import './App.css';
import StartPage from './components/start-page/StartPage';
import TestPassing from './components/test-passing/TestPassing';
import { stat } from 'fs';

export default class App extends Component {


  render(){
    return (
        <div className="App">
          <TestPassing/>
        </div>
    );
  }
}

