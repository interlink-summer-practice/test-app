import React, {Component} from 'react';
import './App.css';
import StartPage from './components/start-page/StartPage';
import {BrowserRouter as Router, Route} from "react-router-dom";
import TestPassing from "./components/test-passing/TestPassing";
import UserAccount from "./components/user-account/UserAccount";


export default class App extends Component {

    render() {

        return (
            <Router>
                <div>
                    <Route path="/" exact component={StartPage}/>
                    <Route path="/quiz" render={(props) => (<TestPassing topics={props.location.state} />)} />
                    <Route path="/account" component={UserAccount}/>
                </div>
            </Router>
        )
    }

}

