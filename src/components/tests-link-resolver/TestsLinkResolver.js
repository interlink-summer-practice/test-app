import React from 'react';
import {Redirect} from "react-router-dom";
import axios from "axios";

export default class TestsLinkResolver extends React.Component {

    getQuestions = () => {
        axios.get('/questions/' + this.props.match.params.id)
            .then(res => console.log(res));
    };

    componentDidMount() {
        if (sessionStorage.getItem('auth-token') !== null) {
            this.getQuestions();
        }
    }

    render() {
        console.log(this.props.match.params.id);

        if (sessionStorage.getItem('auth-token') === null) {
            return (
                <Redirect to="/"/>
            );
        }

        return (
            <div>
                Loading....
            </div>
        )
    }
}
