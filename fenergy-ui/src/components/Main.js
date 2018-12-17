import React, {Component} from 'react';
import {MuiThemeProvider} from '@material-ui/core/styles';
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import 'typeface-roboto'
import CssBaseline from '@material-ui/core/CssBaseline';
import NavBar from "../containers/NavBarContainer";
import PropTypes from 'prop-types';
import Notifications from 'react-notification-system-redux';
import {DEFAULT_THEME, NOTIFICATION} from '../shared/constants';

// xs, extra-small: 0px or larger
// sm, small: 600px or larger
// md, medium: 960px or larger
// lg, large: 1280px or larger
// xl, xlarge: 1920px or larger

class Main extends Component {

  static propTypes = {
    notifications: PropTypes.array,
    actions: PropTypes.shape({
      getUser: PropTypes.func
    })
  };

  componentDidMount() {
    // console.log(JSON.stringify(this.props.actions.getUser()));
    // this.props.actions.getUser()
    // console.log('componentDidMount');
  }

  render() {
    return (
      <div className="main">
        <Notifications notifications={this.props.notifications} style={NOTIFICATION.STYLE}/>
        <CssBaseline/>
        <MuiThemeProvider theme={DEFAULT_THEME}>
          <NavBar/>
          <Paper elevation={0}>

            <Grid id="grid-mainBody" container justify={"center"} alignContent={"center"} alignItems={"center"} direction={"row"} spacing={8}>

              <Grid id="grid-search" item xs={12} sm={11} md={10} lg={9} xl={8}>
                <div>top</div>
              </Grid>

              <Grid id="grid-result" item xs={12} sm={11} md={10} lg={9} xl={8}>
                <div>bottom</div>
              </Grid>

            </Grid>

          </Paper>
        </MuiThemeProvider>
      </div>
    );
  }
}

export default Main;
