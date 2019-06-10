import React, {Comonent, Component} from 'react';
import './ResultBySubjects.css';
import axios from 'axios';
import Button from '@material-ui/core/Button';

export default class ResultBySubjects extends Component {

    state = {
        topicsResult: [],
    }

    componentDidMount() {
        console.log(this.props.sessionId);
        axios.post('/result', {id: this.props.sessionId})
            .then((res) => {
                this.setState((state) => {
                    state.topicsResult = res.data.topicResults
                    return state;
                });
            });
    }

    render() {
        console.log(this.state.topicsResult)
        return (
            <div className="resultByTopics">
                <div className="result">
                    {
                        this.state.topicsResult.map((element, i) => {
                            console.log(element, i)
                            return <div className="topic">
                                <ul>
                                    <li key={i}>{element.topic.name}</li>
                                    <li key={i}>{element.numberOfCorrectAnswers} of {element.numberOfQuestions}</li>
                                    <li>{(element.result).toFixed(2)}%</li>
                                </ul>
                            </div>
                        })
                    }

                </div>
                <Button className="startPageButton" variant="contained" href="/">
                    Start page
                </Button>
            </div>
        );
    }

}