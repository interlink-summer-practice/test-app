import React, {Component} from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Link} from "react-router-dom";
import StartPage from "./components/start-page/StartPage";

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



