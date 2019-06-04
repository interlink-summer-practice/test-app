import React, { Component } from 'react';
import './App.css';
import StartPage from './components/start-page/StartPage';
import TestPassing from './components/test-passing/TestPassing';
import { stat } from 'fs';

export default class App extends Component {
  state = {
    startTest: false,
  }
  startTest = ()=> {
    this.setState((state) =>{
      state.startTest = true; 
      return state;
    })
  }

  render(){


    if(this.state.startTest === true){
      return (<TestPassing/>);  
    
    }
    else {
      return (<StartPage startTest={this.startTest}/>);
    }
  }
  // return (
  //   <div className="App">
      
  //      <TestPassing/> 
  //   </div>
  // );
}

