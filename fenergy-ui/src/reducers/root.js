import { combineReducers } from 'redux';
import { app } from "./app";
import { user } from "./user";
import { reducer as notifications } from 'react-notification-system-redux';

const rootReducer = combineReducers({
  app,
  notifications,
  user
});

export default rootReducer;
