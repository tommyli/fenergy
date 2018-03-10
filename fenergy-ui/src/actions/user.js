import { USER_ACTIONS } from './actionTypes';
import * as SERVER_API from '../shared/http';
import { loadingApp, loadedApp } from './app.js';

export function getUser() {
  return dispatch => {
    dispatch(loadingApp())
    return SERVER_API.getUser()
      .then(data => {
        dispatch(onGetUser(data.data));
      })
      .catch(error => {
        let status = error.response.status
        if (status === 401) {
          console.log('Not authenticated')
        }
      })
      .finally(() => {
        dispatch(loadedApp())
      });
  }
}

export function onGetUser(user) {
  return {
    type: USER_ACTIONS.GET_USER,
    user
  };
}

export function signOut() {
  return dispatch => {
    dispatch(loadingApp())
    return SERVER_API.signOut()
      .then(data => {
        dispatch(onSignOut());
      })
      .finally(() => {
        dispatch(loadedApp())
      });
  }
}

export function onSignOut() {
  return {
    type: USER_ACTIONS.SIGN_OUT
  };
}
