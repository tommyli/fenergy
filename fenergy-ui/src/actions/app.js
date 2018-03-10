import { APP_ACTIONS } from './actionTypes';
import { NOTIFICATION } from "../shared/constants";
import { error as notifyError } from 'react-notification-system-redux';

export function loadingApp() {
  return {
    type: APP_ACTIONS.LOADING
  };
}

export function loadedApp() {
  return {
    type: APP_ACTIONS.LOADED
  };
}

export function createError(error) {
  return {
    type: APP_ACTIONS.ERROR,
    error
  };
}

export function createNotifyError(error) {
  let opts = Object.assign({}, NOTIFICATION.DEFAULT_OPTS, NOTIFICATION.ERROR_OPTS)

  return notifyError(opts)
}
