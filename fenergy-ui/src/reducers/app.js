import initialState from '../store/initialState';
import { APP_ACTIONS, USER_ACTIONS } from '../actions/actionTypes';

export function app(state = initialState.app, action) {

  switch (action.type) {

    case APP_ACTIONS.LOADING: {
      let newState = Object.assign({}, state)
      newState.loading = true;
      return newState;
    }
    case APP_ACTIONS.LOADED: {
      let newState = Object.assign({}, state)
      newState.loading = false;
      return newState;
    }
    case APP_ACTIONS.ERROR: {
      let newState = Object.assign({}, state)
      newState.error = action.error
      return newState;
    }
    case USER_ACTIONS.GET_USER: {
      let newState = Object.assign({}, state)
      newState.authenticated = true
      return newState;
    }
    case USER_ACTIONS.SIGN_OUT: {
      let newState = Object.assign({}, state)
      newState.authenticated = false
      return newState;
    }

    default:
      return state;
  }
}
