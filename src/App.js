import React, {Component} from 'react';
import './App.css';
import StartPage from './components/start-page/StartPage';
import {BrowserRouter as Router, Route} from "react-router-dom";
import TestPassing from "./components/test-passing/TestPassing";
import UserAccount from "./components/user-account/UserAccount";
import axios from 'axios';
import ResultBySubjects from "./components/result-by-subjects/ResultBySubjects";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import AuthAppBarControls from "./components/auth-app-bar-controls/AuthAppBarControls";
import Drawer from "@material-ui/core/Drawer";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import SvgIcon from "@material-ui/core/SvgIcon";
import AccountCircle from '@material-ui/icons/AccountCircle';
import Menu from '@material-ui/icons/Menu';
import LogInDialog from "./components/log-in-dialog/LogInDialog";
import SignUpDialog from "./components/sign-up-dialog/SignUpDialog";
import StartTestsDialog from "./components/start-tests-dialog/StartTestsDialog";
import AppBarMenu from "./components/app-bar-menu/AppBarMenu";

(function () {
    const token = sessionStorage.getItem('auth-token');
    if (token) {
        axios.defaults.headers.common['auth-token'] = token;
    } else {
        axios.defaults.headers.common['auth-token'] = '';
    }
})();

export default class App extends Component {

    state = {
        isDrawerOpen: false,
        isLoginDialogOpen: false,
        isSignUpDialogOpen: false,
        isStartTestsDialogOpen: false,
        isAuthenticated: sessionStorage.getItem('auth-token')
    };

    handleAuthentication = () => {
        this.setState((state) => {
            state.isAuthenticated = !state.isAuthenticated;
            return state;
        })
    };

    logout = (history) => {
        this.setState({
            isDrawerOpen: false,
            isAuthenticated: false
        });
        axios.defaults.headers.common['auth-token'] = '';
        sessionStorage.removeItem('auth-token');
        sessionStorage.removeItem('userFirstName');
        sessionStorage.removeItem('userLastName');
        history.push('/');
    };

    openUserAccount = (history) => {
        this.setState({
            isDrawerOpen: false
        });
        history.push('/account');
    };

    toggleDrawer(event) {
        if (event && event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
        }

        this.setState({isDrawerOpen: !this.state.isDrawerOpen});
    }

    loginDialogHandler = () => {
        this.setState({
            isDrawerOpen: false,
            isLoginDialogOpen: !this.state.isLoginDialogOpen
        });
    };

    signUpDialogHandler = () => {
        this.setState({
            isDrawerOpen: false,
            isSignUpDialogOpen: !this.state.isSignUpDialogOpen
        });
    };

    startTestsDialogHandler = () => {
        this.setState({
            isDrawerOpen: false,
            isStartTestsDialogOpen: !this.state.isStartTestsDialogOpen
        })
    };


    render() {

        return (
            <Router>
                <div className="router">
                    {/* App bar */}
                    <AppBar position="static" color="default">
                        <Toolbar>
                            <Menu onClick={(e) => this.toggleDrawer(e)} className="menuIcon"/>
                            <Typography className="appBarTitle" variant="h6" color="inherit">
                                Tests App
                            </Typography>

                            {
                                !this.state.isAuthenticated
                                    ? (
                                        <AuthAppBarControls
                                            firstButtonClickHandler={this.signUpDialogHandler}
                                            firstButtonLabel={'Sign Up'}
                                            secondButtonClickHandler={this.loginDialogHandler}
                                            secondButtonLabel={'Log In'}
                                        />
                                    )
                                    : <AppBarMenu logoutHandler={this.logout}/>
                            }

                        </Toolbar>
                    </AppBar>

                    {/* Drawer */}
                    <Drawer open={this.state.isDrawerOpen} onClose={(e) => this.toggleDrawer(e)}>
                        <List>

                            {
                                sessionStorage.getItem('auth-token') === null
                                    ? (
                                        <ListItem button onClick={this.signUpDialogHandler}>
                                            <ListItemIcon><AccountCircle/></ListItemIcon>
                                            <ListItemText>Create account</ListItemText>
                                        </ListItem>
                                    )
                                    : (
                                        <Route render={({history}) => (
                                            <ListItem button onClick={() => {
                                                this.openUserAccount(history)
                                            }}>
                                                <Typography className="userLogo" variant="subtitle1" color="inherit">
                                                    {sessionStorage.getItem('userFirstName')[0] + sessionStorage.getItem('userLastName')[0]}
                                                </Typography>
                                                <ListItemText className="userName">
                                                    {sessionStorage.getItem('userFirstName') + ' ' + sessionStorage.getItem('userLastName')}
                                                </ListItemText>
                                            </ListItem>
                                        )}/>
                                    )
                            }

                            {
                                sessionStorage.getItem('auth-token') !== null
                                    ? (
                                        <ListItem button onClick={this.startTestsDialogHandler}>
                                            <ListItemIcon>
                                                <SvgIcon>
                                                    <path fill="#000000"
                                                          d="M14,14H7V16H14M19,19H5V8H19M19,3H18V1H16V3H8V1H6V3H5C3.89,3 3,3.9 3,5V19A2,2 0 0,0 5,21H19A2,2 0 0,0 21,19V5A2,2 0 0,0 19,3M17,10H7V12H17V10Z"/>
                                                </SvgIcon>
                                            </ListItemIcon>
                                            <ListItemText>Start new test</ListItemText>
                                        </ListItem>
                                    )
                                    : null
                            }

                            {
                                sessionStorage.getItem('auth-token') === null
                                    ? (
                                        <ListItem button onClick={this.loginDialogHandler}>
                                            <ListItemIcon>
                                                <SvgIcon>
                                                    <path fill="#000000"
                                                          d="M10,17V14H3V10H10V7L15,12L10,17M10,2H19A2,2 0 0,1 21,4V20A2,2 0 0,1 19,22H10A2,2 0 0,1 8,20V18H10V20H19V4H10V6H8V4A2,2 0 0,1 10,2Z"/>
                                                </SvgIcon>
                                            </ListItemIcon>
                                            <ListItemText>Log In</ListItemText>
                                        </ListItem>
                                    )
                                    : (
                                        <Route render={({history}) => (
                                            <ListItem button onClick={() => this.logout(history)}>
                                                <ListItemIcon className="logoutIcon">
                                                    <SvgIcon>
                                                        <path fill="#000000"
                                                              d="M16,17V14H9V10H16V7L21,12L16,17M14,2A2,2 0 0,1 16,4V6H14V4H5V20H14V18H16V20A2,2 0 0,1 14,22H5A2,2 0 0,1 3,20V4A2,2 0 0,1 5,2H14Z"/>
                                                    </SvgIcon>
                                                </ListItemIcon>
                                                <ListItemText>Log Out</ListItemText>
                                            </ListItem>
                                        )}/>
                                    )
                            }

                        </List>
                    </Drawer>

                    <LogInDialog
                        open={this.state.isLoginDialogOpen}
                        loginDialogHandler={this.loginDialogHandler}
                        handleAuthentication={this.handleAuthentication}
                    />
                    <SignUpDialog open={this.state.isSignUpDialogOpen} signUpDialogHandler={this.signUpDialogHandler}/>
                    <StartTestsDialog open={this.state.isStartTestsDialogOpen}
                                      startTestsDialogHandler={this.startTestsDialogHandler}/>

                    <Route path="/" exact
                           render={() => (<StartPage startTestsDialogHandler={this.startTestsDialogHandler}/>)}/>
                    <Route path="/quiz" render={(props) => (<TestPassing topics={props.location.state}/>)}/>
                    <Route path="/account" component={UserAccount}/>
                    <Route path="/detailed-result"
                           render={(props) => (<ResultBySubjects sessionId={props.location.state}/>)}/>
                </div>
            </Router>
        )
    }

}

