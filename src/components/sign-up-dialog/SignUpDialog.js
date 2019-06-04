import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';

class SignUpDialog extends React.Component {

  render() {
    return (
      <div>
        <Dialog open={this.props.open}>
          <DialogTitle id="form-dialog-title">Sign Up</DialogTitle>
          <DialogContent>
            <TextField
              autoFocus
              margin="dense"
              id="firstName"
              label="First name"
              type="text"
              fullWidth
            />
            <TextField
              margin="dense"
              id="lastName"
              label="Last name"
              type="text"
              fullWidth
            />
            <TextField
              margin="dense"
              id="email"
              label="Password"
              type="email"
              fullWidth
            />
            <TextField
              margin="dense"
              id="confirmPassword"
              label="Confirm password"
              type="password"
              fullWidth
            />
          </DialogContent>
          <DialogActions>
            <Button color="primary" onClick={this.props.signUpDialogHandler}>
              Cancel
            </Button>
            <Button color="primary">
              Create Account
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    )
  }

}

export default SignUpDialog;
