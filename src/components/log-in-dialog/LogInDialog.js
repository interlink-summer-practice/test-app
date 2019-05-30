import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';

class LogInDialog extends React.Component {

  render() {
    return (
      <div>
        <Dialog open={this.props.open}>
          <DialogTitle id="form-dialog-title">Welcome!</DialogTitle>
          <DialogContent>
            <TextField
              autoFocus
              margin="dense"
              id="login"
              label="Username or email"
              type="text"
              fullWidth
            />
            <TextField
              margin="dense"
              id="password"
              label="Password"
              type="password"
              fullWidth
            />
          </DialogContent>
          <DialogActions>
            <Button color="primary" onClick={this.props.loginDialogHandler}>
              Cancel
            </Button>
            <Button color="primary">
              Log In
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    )
  }

}

export default LogInDialog;
