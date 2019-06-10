import React from 'react';
import './PassedTest.css';
import Typography from "@material-ui/core/Typography";
import Chip from "@material-ui/core/Chip";
import {CardContent} from "@material-ui/core";
import Card from "@material-ui/core/Card";
import TestsDifficulty from "../tests-difficulty/TestsDifficulty";

export default class PassedTest extends React.Component {

    formatDate = () => {
        const date = new Date(this.props.testInformation.date);
        return `${date.getDate()}/${date.getMonth()+1}/${date.getFullYear()}`;
    };

    render() {
        return (
            <div className="passedTest">
                <Card>
                    <CardContent>
                        <Typography className="date" variant="subtitle1" color="inherit">
                            {this.formatDate()}
                        </Typography>
                        <TestsDifficulty difficulty={this.props.testInformation.difficulty} />
                        <div className="topics">
                            {
                                this.props.testInformation.topics.map((topic) => {
                                    const red = Math.floor(Math.random() * 255);
                                    const green = Math.floor(Math.random() * 255);
                                    const blue = Math.floor(Math.random() * 255);
                                    const rgb = `${red},${green},${blue}`;

                                    return (
                                        <Chip className="chip"
                                              label={topic.name}
                                              style={{
                                                  backgroundColor: "rgb(" + rgb + ")",
                                                  color: 'white',
                                                  paddingRight: '5px'
                                              }}
                                              key={topic.name}
                                        />
                                    )
                                })
                            }
                        </div>
                        <div className="results">
                            <Typography className="rightAnswersPercentage" variant="subtitle1" color="inherit">
                                Right answers:
                            </Typography>
                            <Typography className="rightAnswersPercentage" variant="subtitle1" color="inherit">
                            { Math.floor(this.props.testInformation.percentOfPassingQuiz * 100) / 100  + '%' }
                        </Typography>

                        </div>
                    </CardContent>
                </Card>
            </div>
        );
    }
}
