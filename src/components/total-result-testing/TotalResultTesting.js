import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import './TotalResultTesting.css';
import axios from 'axios'
import { withRouter, Route } from 'react-router-dom';

export default class TotalResultTesting extends Component {
    state = {
        isDataLoaded: false,
        totalPercent: 0,
        totalCountOfQuestions: 0,
        totalCountOfCorrectAnswers: 0
    }


    componentDidMount() {
        console.log(this.props.sessionId);
        axios.post('/result', {id: this.props.sessionId})
            .then(res => {
                this.setState({
                    totalPercent: res.data.percentOfPassingQuiz,
                    totalCountOfQuestions: res.data.countOfQuestion,
                    totalCountOfCorrectAnswers: res.data.countOfCorrectAnswers
                });

            })
    }

    render() {
        return (
            <div className="totalResult">
                <div
                    className="number">{this.state.totalCountOfCorrectAnswers} of {this.state.totalCountOfQuestions}</div>
                <div className="percent">{(this.state.totalPercent).toFixed(2)}%</div>
                <div className="more">
                    <Route render={({history}) => (
                        <Button color="secondary" onClick={() => {
                            history.push('/detailed-result',
                                this.props.sessionId
                            )
                        }}>
                            More
                        </Button>
                    )}/>
                </div>
            </div>

        );
    }
}