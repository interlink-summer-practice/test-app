import React from 'react';
import './PassedTest.css';
import Typography from "@material-ui/core/Typography";
import Chip from "@material-ui/core/Chip";
import {CardContent} from "@material-ui/core";
import Card from "@material-ui/core/Card";

export default class PassedTest extends React.Component {

    render() {
        return (
            <div className="passedTest">
                <Card>
                    <CardContent>
                        <Typography className="date" variant="subtitle1" color="inherit">
                            {this.props.testInformation.date}
                        </Typography>
                        <div className="topics">
                            {
                                this.props.testInformation.topics.map((topic) => {
                                    const red = Math.floor(Math.random() * 255);
                                    const green = Math.floor(Math.random() * 255);
                                    const blue = Math.floor(Math.random() * 255);
                                    const rgb = `${red},${green},${blue}`;

                                    return (
                                        <Chip className="chip"
                                              label={topic}
                                              style={{
                                                  backgroundColor: "rgb(" + rgb + ")",
                                                  color: 'white',
                                                  paddingRight: '5px'
                                              }}
                                              key={topic}
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
                                25 of 50
                            </Typography>
                            <Typography className="rightAnswersPercentage" variant="subtitle1" color="inherit">
                            {this.props.testInformation.rightAnswersPercentage}
                        </Typography>

                        </div>
                    </CardContent>
                </Card>
            </div>
        );
    }
}
