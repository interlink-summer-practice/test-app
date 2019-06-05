import React, { Component } from 'react';
import Question from '../question/Question';
import TotalResultTesting from '../total-result-testing/TotalResultTesting'
import resultBySubjects from '../result-by-subjects/ResultBySubjects'
import _ from 'lodash';
import ResultBySubjects from '../result-by-subjects/ResultBySubjects';
import { resetWarningCache } from 'prop-types';
import { exportAllDeclaration } from '@babel/types';

let questions = require('../../questions.json');



export default class TestPassing extends Component {

    state = {
        i: 0,
        questions: questions,
        allSubjects: [],
        showResultBySubjects: false,
    };

    nextQuestion = () => {
        this.setState((state) => {
            state.i = state.i + 1;
            return state;
        });
    };

    selectedOption = (value) => {
        this.setState((state) => {
            state.questions[state.i].chosenOption = value;
            return state;
        });
        console.log(this.state.questions[this.state.i]);
    };

    checkIsCorrectAnswer = () => {
        this.state.questions.forEach(element => {
            if (element.chosenOption === element.rightAnswer) {
                // this.setState((state) => {
                //     state.correct = true;
                //     return state;
                // })
                element.correct = true;
            }
            else {
                // this.setState((state) => {
                //     state.correct = false;
                //     return state; 
                // })
                element.correct = false;
            }
        });
        // console.log(this.state.questions);
    };

    numberOfCorrectAnswers = () => {
        // console.log(this.state.questions);
        var count = 0;
        this.state.questions.forEach(element => {
            if (element.correct === true) {
                count += 1;
            }
        });
        return count;
    };

    percentOfCorrectAnswers = () => {
        return Math.floor(this.numberOfCorrectAnswers() * 100 / this.state.questions.length);
    };


    allSubjectsWithoutRepetition = () => {
        let subjects = _.uniqBy(this.state.questions, "subject").map((elem,i) => {
            // return elem.subject;
            return {
                id: i,
                subject: elem.subject,
                numberOfCorrectAnswers: 0,
                numberOfQuestions: 0
            }
        });
        this.setState({ allSubjects: subjects });
        console.log(this.state.allSubjects);
    };

    resultBySubjects = () => {
        this.state.questions.forEach((element) => {
            this.state.allSubjects.forEach((elem, i) => {
                if (elem.subject === element.subject) {
                    elem.numberOfQuestions += 1;
                    if (element.correct === true) {
                        elem.numberOfCorrectAnswers += 1;
                    }
                }
            });
        });

        return this.state.allSubjects;
    };

    showResultBySubjects = () => {
        this.setState({ showResultBySubjects: true });
        console.log("show");
    };

    componentDidMount() {
        this.allSubjectsWithoutRepetition();
        console.log(this.props.topics);
    }
    render() {
        if (questions[this.state.i] !== undefined) {
            return (
                <React.Fragment>
                    <Question question={this.state.questions[0 + this.state.i]} selectedOption={this.selectedOption} nextQuestion={this.nextQuestion} />
                </React.Fragment>
            );
        }
        else {
            this.checkIsCorrectAnswer();
            
            console.log(this.state.allSubjects);
            if (this.state.showResultBySubjects === true) {
                return (<ResultBySubjects allSubjects={this.state.allSubjects}/>);
            }
            else {
                this.resultBySubjects();
                return (
                    <TotalResultTesting showResultBySubjects={this.showResultBySubjects} questions={this.state.questions} numberOfCorrectAnswers={this.numberOfCorrectAnswers()} percentOfCorrectAnswers={this.percentOfCorrectAnswers()} />
                );
            }
        }
    }
}
