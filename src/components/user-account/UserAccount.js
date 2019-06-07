import React from 'react';
import './UserAccount.css';
import UserAccountHeader from "../user-account-header/UserAccountHeader";
import PassedTest from "../passed-test/PassedTest";
import axios from 'axios';
import {Redirect} from "react-router-dom";

export default class UserAccount extends React.Component {

    state = {
        userTests: []
    };

    componentDidMount() {

        if (sessionStorage.getItem('auth-token') !== null) {
            axios.get('/account')
                .then(res => {
                    this.setState({
                        userTests: res.data
                    })
                })
        }

    }

    render() {

        if (sessionStorage.getItem('auth-token') === null) {
            return <Redirect to='/'/>
        }

        if (this.state.userTests.length !== 0) {
            return (
                <div>
                    <UserAccountHeader firstName={this.state.userTests[0].firstName}
                                       lastName={this.state.userTests[0].lastName}/>
                    <div className="userPassedTests">
                        {
                            this.state.userTests.map((passedTest, index) => {
                                return (
                                    <PassedTest key={index} testInformation={
                                        {
                                            date: passedTest.date,
                                            topics: passedTest.topics,
                                            percentOfPassingQuiz: passedTest.percentOfPassingQuiz
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
