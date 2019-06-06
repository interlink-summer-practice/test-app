import React from 'react';
import './UserAccountHeader.css';
import Typography from "@material-ui/core/Typography";

export default class UserAccountHeader extends React.Component {

    fullName = 'Yaroslav Kirilenko';

    firstLetters = () => {
        const firstAndLastNames = this.fullName.split(' ');
        return firstAndLastNames[0][0] + firstAndLastNames[1][0];
    };

    render() {
        return (
            <div className="userAccountHeader">
                <Typography className="userFullName" variant="subtitle1" color="inherit">
                    {this.fullName}
                </Typography>
                <Typography className="userLogo" variant="subtitle1" color="inherit">
                    {this.firstLetters()}
                </Typography>
            </div>
        );
    }

}
