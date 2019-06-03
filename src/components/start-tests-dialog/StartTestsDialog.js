import React from 'react';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import {Typography} from "@material-ui/core";
import TestTopicsAutocomplete from "../test-topics-autocomplete/TestTopicsAutocomplete";

class StartTestsDialog extends React.Component {

  render() {
    return (
      <div>
        <Dialog open={this.props.open} className="startTestsDialog">
          <DialogTitle id="form-dialog-title">Start tests</DialogTitle>
          <DialogContent>
            <Typography variant="h6">
              Choose what skills you want to prove
            </Typography>
            <TestTopicsAutocomplete/>
          </DialogContent>
          <DialogActions>
            <Button color="primary" onClick={this.props.startTestsDialogHandler}>
              Cancel
            </Button>
            <Button color="primary">
              Start Tests
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    )
  }

}

export default StartTestsDialog;
