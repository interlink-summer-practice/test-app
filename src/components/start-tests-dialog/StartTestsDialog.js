import React from 'react';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import {Typography} from "@material-ui/core";
import axios from 'axios';
import TestTopicsAutocomplete from "../test-topics-autocomplete/TestTopicsAutocomplete";


class StartTestsDialog extends React.Component {

  state = {
    topics: [],
    selectedTopics: []
  }

  startTest = () => {
    let questions = [];
    axios.post('/questions',this.state.selectedTopics)

        .then(res => {questions = res})

        .catch()
    return questions;
  }


  selectedTopics = (value) => {
    this.setState((state) => {
      state.selectedTopics = value.map((element)=>{return {
        id: element.id
      }})


      return state;
    },()=>{console.log(this.state.selectedTopics)})
  }

  componentDidMount = () => {
    axios.get('/topics')
        .then(res => {
          this.setState((state) => {
            state.topics = res.data;
          })

        })
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
            <TestTopicsAutocomplete suggestions={this.state.topics}selectedTopics={this.selectedTopics} />
          </DialogContent>
          <DialogActions>
            <Button color="primary" onClick={this.props.startTestsDialogHandler}>
              Cancel
            </Button>
            <Button color="primary" onClick={this.startTest}>
              Start Tests
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    )
  }

}

export default StartTestsDialog;