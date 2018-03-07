import {createMuiTheme} from 'material-ui/styles';

const DEFAULT_THEME = createMuiTheme({
  palette: {
    primary: {
      light: '#63a4ff',
      main: '#1976d2',
      dark: '#004ba0',
      contrastText: '#ffffff',
    },
    secondary: {
      light: '#d1d9ff',
      main: '#9fa8da',
      dark: '#6f79a8',
      contrastText: '#000000',
    },
  },
});

const NOTIFICATION = {
  STYLE: {
    NotificationItem: {
      DefaultStyle: {
        fontFamily: 'roboto',
      }
    }
  },
  DEFAULT_OPTS: {
    title: 'Title',
    message: 'Message!',
    position: 'br',
    autoDismiss: 3
  },
  INFO_OPTS: {
    title: 'INFO',
    message: 'Everything seems to be working.'
  },
  ERROR_OPTS: {
    title: 'ERROR',
    message: 'Unexpected error occured, try again later.'
  }
}

export {
  DEFAULT_THEME, NOTIFICATION
}
