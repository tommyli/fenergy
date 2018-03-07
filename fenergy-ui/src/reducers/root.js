import {combineReducers} from 'redux';
import {app} from "./app";
import {reducer as notifications} from 'react-notification-system-redux';

const rootReducer = combineReducers({
  app,
  notifications
});

export default rootReducer;
