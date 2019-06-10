import React from 'react';
import './UserAccount.css';
import PassedTest from "../passed-test/PassedTest";
import axios from 'axios';
import {Redirect} from "react-router-dom";

export default class UserAccount extends React.Component {

    state = {
        account: null
    };

    componentDidMount() {

        if (sessionStorage.getItem('auth-token') !== null) {
            axios.get('/account')
                .then(res => {
                    this.setState({
                        account: res.data
                    });
                });
        }

    }

    render() {

        if (sessionStorage.getItem('auth-token') === null) {
            return <Redirect to='/'/>
        }

        if (this.state.account !== null) {
            return (
                <div>
                    <div className="userPassedTests">
                        {
                            this.state.account.results.map((passedTest, index) => {
                                return (
                                    <PassedTest key={index} testInformation={
                                        {
                                            date: passedTest.date,
                                            topics: passedTest.topics,
                                            percentOfPassingQuiz: passedTest.percentOfPassingQuiz,
                                            difficulty: passedTest.difficulty
                                        }

                                    }/>
                                )
                            })
                        }
                    </div>
                </div>
            );
        }

        return (
            <div>
                <p>Loading...</p>
            </div>
        )

    }

}
