import React from 'react';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import {Typography} from "@material-ui/core";
import axios from 'axios';
import TestTopicsAutocomplete from "../test-topics-autocomplete/TestTopicsAutocomplete";
import {withRouter, Route} from 'react-router-dom';
import Input from '@material-ui/core/Input';
import OutlinedInput from '@material-ui/core/OutlinedInput';
import FilledInput from '@material-ui/core/FilledInput';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';


class StartTestsDialog extends React.Component {

    state = {
        topics: [],
        selectedTopics: [],
        difficulty: 'All',
    };

    selectedTopics = (value) => {
        this.setState((state) => {
            state.selectedTopics = value.map((element) => {
                return {
                    id: element.id
                }
            });


            return state;
        }, () => {
            console.log(this.state.selectedTopics)
        })
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
        this.setState({difficulty: event.target.value})
    }

    render() {
        return (
            <div>
                <Dialog open={this.props.open} className="startTestsDialog">
                    <DialogTitle id="form-dialog-title">Start tests</DialogTitle>
                    <DialogContent>
                        <Typography variant="h6">
                            Choose what skills you want to prove
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
                            <MenuItem value="All">
                                <em>All</em>
                            </MenuItem>
                            <MenuItem value={"Просте"}>Просте</MenuItem>
                            <MenuItem value={"Середнє"}>Середнє</MenuItem>
                            <MenuItem value={"Складне"}>Складне</MenuItem>
                        </Select>
                    </DialogContent>
                    <DialogActions>
                        <Button color="primary" onClick={this.props.startTestsDialogHandler}>
                            Cancel
                        </Button>

                        <Route render={({history}) => (
                            <Button color="primary" onClick={() => {
                                this.props.startTestsDialogHandler();
                                history.push('/quiz', {
                                    topics: this.state.selectedTopics,
                                    difficulty: this.state.difficulty,
                                })
                            }}>
                                Start Tests
                            </Button>
                        )}/>

                    </DialogActions>
                </Dialog>
            </div>
        )
    }
}

export default StartTestsDialog;