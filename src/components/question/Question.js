import React, { Component } from 'react';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import FormLabel from '@material-ui/core/FormLabel';
import Button from '@material-ui/core/Button';

import './Question.css'

export default class Question extends Component {
    state = {
        value: ''
    }



    handleChange = (event) => {
        this.setState({ value: event.target.value });
    }

    nextQuestion = () => {

        if (this.state.value !== '') {
            this.setState((state) => {               
                this.props.selectedOption(this.state.value);
                state.value = '';
            }, () => this.props.nextQuestion());


        }
    }

    // setChosenOption = () => {
    //     if (this.state.value !== ' ') {
    //         questions[this.props.i].chosenOption = this.state.value;
    //     }
    //     this.setState({ value: ' ' });
    // }
    // isOptionChosen = () => {
    //     if (this.state.value !== ' ') {
    //         return true;
    //     }
    //     else {
    //         return false;
    //     }
    // }

    render() {

        const { id, subject, question, answerOptions, righrAnswer } = this.props.question;
        return (
            
            <div className="test">
                <FormControl component="fieldset" >
                    <FormLabel component="legend">{question}</FormLabel>
                    <RadioGroup
                        value={this.state.value}
                        onChange={this.handleChange}
                    >
                        <FormControlLabel value={`${answerOptions[0]}`} control={<Radio />} label={answerOptions[0]} />
                        <FormControlLabel value={`${answerOptions[1]}`} control={<Radio />} label={answerOptions[1]} />
                        <FormControlLabel value={`${answerOptions[2]}`} control={<Radio />} label={answerOptions[2]} />
                        <FormControlLabel value={`${answerOptions[3]}`} control={<Radio />} label={answerOptions[3]} />
                    </RadioGroup>
                </FormControl>
                <Button variant="contained" color="primary" onClick={this.nextQuestion}>
                    Відповісти
                </Button>
                </div>
        );

    }
}