import React from 'react';
import './TestsDifficulty.css';

export default class TestsDifficulty extends React.Component {

    difficulty;

    constructor(props) {
        super(props);
        this.difficulty = this.props.difficulty;
    }

    render() {
        return (
            <div>
                {this.difficulty}
            </div>
        );
    }
}
