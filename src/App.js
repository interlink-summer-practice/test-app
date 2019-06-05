import React, {Component} from 'react';
import './App.css';
import StartPage from './components/start-page/StartPage';
import {BrowserRouter as Router, Route} from "react-router-dom";


export default class App extends Component {

    render() {

        return (
            <Router>
                <div>
                    <Route path="/" exect component={StartPage}/>
                </div>
            </Router>


        )
    }

}

