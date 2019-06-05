import React, { Comonent, Component } from 'react';
import './ResultBySubjects.css'

export default class ResultBySubjects extends Component {

    state = {
        subjects: this.props.allSubjects,
    }
    percentOfCorrectAnswerrdBySubject = () => {
        this.state.subjects.forEach((element) => {
            element.percentOfcorrect = element.numberOfCorrectAnswers * 100 / element.numberOfQuestions;
        });
    }
    render() {
        this.percentOfCorrectAnswerrdBySubject();
        return(
            <div className="result">
            {
                this.state.subjects.map((element)=>{
                    return <ul>
                        <li>{element.subject}</li>
                        <li>{element.numberOfCorrectAnswers} of {element.numberOfQuestions}</li>
                        <li>{element.percentOfcorrect}%</li>
                    </ul>
                })
            }
            </div>
        );
    }

}