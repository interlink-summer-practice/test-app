import React, { Component } from 'react';
import Question from '../question/Question';
import TotalResultTesting from '../total-result-testing/TotalResultTesting'
import resultBySubjects from '../result-by-subjects/ResultBySubjects'
import ResultBySubjects from '../result-by-subjects/ResultBySubjects';
import axios from 'axios';




export default class TestPassing extends Component {

    state = {
        i: 0,
        isDataLoaded: false,
        questions: [],
        selectedTopics: [{id:4},{id:5}],
        sessionId: 0,
    };
    nextQuestion = (questionId,answerId) => {
        axios.post('/quiz-answer',{quizSessionId: this.state.sessionId, answerId: answerId, questionId: questionId})
            .then(res => {this.setState((state) => {
                state.i = state.i + 1
                return state;
            });})
            .catch(err => console.log(err))


    }
    // selectedOption = (value) => {
    //     this.setState((state) => {
    //         state.questions[state.i].chosenOption = value;
    //         return state;
    //     });
    //     console.log(this.state.questions[this.state.i]);
    // }
    // checkIsCorrectAnswer = () => {
    //     this.state.questions.forEach(element => {
    //         if (element.chosenOption === element.rightAnswer) {
    //             // this.setState((state) => {
    //             //     state.correct = true;
    //             //     return state;
    //             // })
    //             element.correct = true;
    //         }
    //         else {
    //             // this.setState((state) => {
    //             //     state.correct = false;
    //             //     return state;
    //             // })
    //             element.correct = false;
    //         }
    //     });
    //     // console.log(this.state.questions);
    // }
    //
    // numberOfCorrectAnswers = () => {
    //     // console.log(this.state.questions);
    //     var count = 0;
    //     this.state.questions.forEach(element => {
    //         if (element.correct === true) {
    //             count += 1;
    //         }
    //     });
    //     return count;
    // }
    // percentOfCorrectAnswers = () => {
    //     return Math.floor(this.numberOfCorrectAnswers() * 100 / this.state.questions.length);
    // }

    //
    // allSubjectsWithoutRepetition = () => {
    //     let subjects = _.uniqBy(this.state.questions, "subject").map((elem,i) => {
    //         // return elem.subject;
    //         return {
    //             id: i,
    //             subject: elem.subject,
    //             numberOfCorrectAnswers: 0,
    //             numberOfQuestions: 0
    //         }
    //     });
    //     this.setState({ allSubjects: subjects });
    //     console.log(this.state.allSubjects);
    // }
    // resultBySubjects = () => {
    //     this.state.questions.forEach((element) => {
    //         this.state.allSubjects.forEach((elem, i) => {
    //             if (elem.subject === element.subject) {
    //                 elem.numberOfQuestions += 1;
    //                 if (element.correct === true) {
    //                     elem.numberOfCorrectAnswers += 1;
    //                 }
    //             }
    //         });
    //     })
    //     return this.state.allSubjects;
    // }
    // showResultBySubjects = () => {
    //     this.setState({ showResultBySubjects: true });
    //     console.log("show");
    // }
    componentDidMount() {
        axios.post('/questions',this.state.selectedTopics)
            .then(res => {
                this.setState((state) => {
                    state.sessionId = res.data.quizSession.id;
                    state.isDataLoaded = true;
                    state.questions = res.data.questions;
                    return state;

                });

            })
    }
    render() {
        console.log(this.state.i)
        if (this.state.isDataLoaded === true){
            if (this.state.questions[this.state.i] !== undefined) {
                return (<React.Fragment>
                    <Question question={this.state.questions[0 + this.state.i]}  nextQuestion={this.nextQuestion}/>
                </React.Fragment>)
            }
            else {
                return(<TotalResultTesting sessionId={this.state.sessionId}/>)

            }

        }
        else{
            return (<div>loading....</div>)
        }

    }
}
