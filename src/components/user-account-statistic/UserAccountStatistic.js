import React from 'react';
import ResultBySubjectsGraph from "../result-by-subjects/ResultBySubjectsGraph";

export default class UserAccountStatistic extends React.Component {


    render() {

        console.log(this.props.statistic);

        return (
            <div>
                <ResultBySubjectsGraph  topicsResult={ [{ topic: {name: 'OOP'}, result: 50}, { topic: {name: 'MMM'}, result: 80}]}/>
            </div>
        );
    }
}
