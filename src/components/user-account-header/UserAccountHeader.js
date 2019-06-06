import React from 'react';
import './UserAccountHeader.css';
import Typography from "@material-ui/core/Typography";

export default class UserAccountHeader extends React.Component {

    render() {
        return (
            <div className="userAccountHeader">
                <Typography className="userFullName" variant="subtitle1" color="inherit">
                    {this.props.firstName + ' ' + this.props.lastName}
                </Typography>
                <Typography className="userLogo" variant="subtitle1" color="inherit">
                    {this.props.firstName[0] + this.props.lastName[0]}
                </Typography>
            </div>
        );
    }

}
