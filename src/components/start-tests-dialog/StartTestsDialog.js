import React from 'react';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import {Typography} from "@material-ui/core";
import axios from 'axios';
import TestTopicsAutocomplete from "../test-topics-autocomplete/TestTopicsAutocomplete";
import {Route} from 'react-router-dom';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import TextField from "@material-ui/core/TextField";

class StartTestsDialog extends React.Component {

    state = {
        topics: [],
        selectedTopics: [],
        difficulty: '',
        groupName: ''
    };

    selectedTopics = (value) => {
        if (value !== null) {

            this.setState((state) => {
                state.topicDisableButton = false;
                state.selectedTopics = value.map((element) => {
                    return {
                        id: element.id
                    }
                });
                return state;
            }, () => {
                console.log(this.state.selectedTopics)
            })
        }

    };

    componentDidMount = () => {
        axios.get('/topics')
            .then(res => {
                this.setState((state) => {
                    state.topics = res.data;
                })

            })
    };
    handleChange = (event) => {
        this.setState((state) => {
            state.difficulty = event.target.value;
            state.difficultyDisableButton = !state.difficultyDisableButton;
            return state;
        })
        // this.setState({
        //     difficulty: event.target.value,
        //     difficultyDisableButton: false
        // })
    };

    handleGroupNameChange = (event) => {
        this.setState({
            groupName: event.target.value
        })
    };

    /**
     * Admin only
     */
    createTests = () => {
        const baseUrl = 'http://localhost:3000/questions/';

        axios.post('/group', {
            name: this.state.groupName
        }).then(res => {
            const group = {
                groupId: res.data.id,
                topics: this.state.selectedTopics,
                difficulties: [this.state.difficulty]
            };

            let groupStr = JSON.stringify(group);
            let groupBase64 = Buffer.from(groupStr).toString("base64");

            this.props.testsLinkDialogHandler(baseUrl + groupBase64);
            this.props.startTestsDialogHandler();
        });

    };

    render() {
        return (
            <div>
                <Dialog open={this.props.open} className="startTestsDialog">
                    <DialogTitle id="form-dialog-title">Start tests</DialogTitle>
                    <DialogContent>
                        {
                            this.props.isAdmin
                                ? (
                                    <TextField
                                        onChange={(e) => {
                                            this.handleGroupNameChange(e)
                                        }}
                                        autoFocus
                                        margin="dense"
                                        id="groupName"
                                        label="Group name"
                                        type="text"
                                        fullWidth
                                    />
                                )
                                : null
                        }
                        <Typography variant="h6">
                            Choose what skills you want to check
                        </Typography>
                        <TestTopicsAutocomplete suggestions={this.state.topics} selectedTopics={this.selectedTopics}/>
                        <InputLabel htmlFor="age-difficulty">Difficulty</InputLabel>
                        <Select

                            fullWidth
                            value={this.state.difficulty}
                            onChange={this.handleChange}
                            inputProps={{
                                name: 'age',
                                id: 'difficulty',
                            }}
                        >

                            <MenuItem value={"Просте"}>Просте</MenuItem>
                            <MenuItem value={"Середнє"}>Середнє</MenuItem>
                            <MenuItem value={"Складне"}>Складне</MenuItem>
                        </Select>
                    </DialogContent>
                    <DialogActions>
                        <Button color="primary" onClick={this.props.startTestsDialogHandler}>
                            Cancel
                        </Button>

                        {
                            this.props.isAdmin
                                ? (
                                    <Button color="primary" onClick={this.createTests}>
                                        Create tests
                                    </Button>
                                )
                                : (
                                    <Route render={({history}) => (
                                        <Button
                                            color="primary" onClick={() => {
                                            this.props.startTestsDialogHandler();
                                            history.push('/quiz', {
                                                topics: this.state.selectedTopics,
                                                difficulty: this.state.difficulty,
                                            });
                                            this.setState((state) => {
                                                state.selectedTopics = [];
                                                state.difficulty = '';
                                                return state;
                                            });
                                        }}>
                                            Start Tests
                                        </Button>
                                    )}/>
                                )
                        }

                    </DialogActions>
                </Dialog>
            </div>
        )
    }
}

export default StartTestsDialog;