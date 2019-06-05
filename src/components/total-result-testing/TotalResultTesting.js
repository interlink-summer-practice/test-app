import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import './TotalResultTesting.css';
export default class TotalResultTesting extends Component {

    showResultBySubjects = () => {
       this.props.showResultBySubjects();
    }

    render() {
        return (    
            <div className="totalResult">
                <div className="number">{this.props.numberOfCorrectAnswers} of {this.props.questions.length}</div>
                <div className="percent">{this.props.percentOfCorrectAnswers}%</div>
                <Button color="secondary" onClick={this.showResultBySubjects}>
                    More
                </Button>
            </div>

        );
    }
}