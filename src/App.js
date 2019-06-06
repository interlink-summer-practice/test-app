import React, {Component} from 'react';
import './App.css';
import StartPage from './components/start-page/StartPage';
import {BrowserRouter as Router, Route} from "react-router-dom";
import TestPassing from "./components/test-passing/TestPassing";
import ResultBySubjects from "./components/result-by-subjects/ResultBySubjects";



export default class App extends Component {

    render() {

        return (
            <Router>
                <div className="router">
                    <Route path="/" exact component={StartPage}/>
                    <Route path="/quiz" render={(props) => (<TestPassing topics={props.location.state} />)} />
                    <Route path="/detailed-result" render={(props) => (<ResultBySubjects sessionId={props.location.state} />)} />
                </div>
            </Router>
        )
    }

}

