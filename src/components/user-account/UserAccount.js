import React from 'react';
import './UserAccount.css';
import UserAccountHeader from "../user-account-header/UserAccountHeader";
import PassedTest from "../passed-test/PassedTest";

export default class UserAccount extends React.Component {

    testsResults = [
        {
            date: '06/06/2019',
            topics: [
                'HTML/CSS',
                'Алгоритми',
                'Бази данних',
                'OOP',
                'Data Science',
                'One more theme'
            ],
            rightAnswersPercentage: '50%'
        },
        {
            date: '06/06/2019',
            topics: [
                'HTML/CSS',
                'Алгоритми',
                'Бази данних',
                'OOP',
                'Data Science',
                'One more theme'
            ],
            rightAnswersPercentage: '50%'
        },
        {
            date: '06/06/2019',
            topics: [
                'HTML/CSS',
                'Алгоритми',
                'Бази данних',
                'OOP',
                'Data Science',
                'One more theme'
            ],
            rightAnswersPercentage: '50%'
        },
        {
            date: '06/06/2019',
            topics: [
                'HTML/CSS',
                'Алгоритми',
                'Бази данних',
                'OOP',
                'Data Science',
                'One more theme'
            ],
            rightAnswersPercentage: '50%'
        }
    ];

    render() {
        return (
            <div>
                <UserAccountHeader />
                <div className="userPassedTests">
                    {
                        this.testsResults.map(test => {
                            return (
                                <PassedTest testInformation={test}/>
                            )
                        })
                    }
                </div>
            </div>
        );
    }

}
