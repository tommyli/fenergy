import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Grid from "material-ui/Grid";
import Paper from "material-ui/Paper";
import 'typeface-roboto'
import Reboot from 'material-ui/Reboot';
import NavBar from "../containers/NavBarContainer";
import PropTypes from 'prop-types';
import Notifications from 'react-notification-system-redux';
import { DEFAULT_THEME, NOTIFICATION } from '../shared/constants';

// xs, extra-small: 0px or larger
// sm, small: 600px or larger
// md, medium: 960px or larger
// lg, large: 1280px or larger
// xl, xlarge: 1920px or larger

const Main = ({ notifications }) => {

  return (
    <div className="main">
      <Notifications notifications={notifications} style={NOTIFICATION.STYLE} />
      <Reboot />
      <MuiThemeProvider theme={DEFAULT_THEME}>
        <NavBar />
        <Paper>

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
};

Main.propTypes = {
  notifications: PropTypes.array,
};

export default Main;
