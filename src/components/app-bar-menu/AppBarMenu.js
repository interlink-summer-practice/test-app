import React from 'react';
import './AppBarMenu.css';
import Button from '@material-ui/core/Button';
import {Route} from "react-router-dom";

export default class AppBarMenu extends React.Component {

    render() {
        return (
            <div className="menu">
                <Button>My Account</Button>
                <Button>Start New Test</Button>

                <Route render={({history}) => (
                    <Button onClick={() => {
                        this.props.logoutHandler(history);
                    }}>
                        Log Out
                    </Button>
                )}/>
            </div>
        );
    }

}
