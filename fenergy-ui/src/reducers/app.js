import initialState from '../store/initialState';
import * as types from '../actions/actionTypes';

export function app(state = initialState.app, action) {

  switch (action.type) {

    case types.APP_ACTIONS.LOADING: {
      let newState = Object.assign({}, state)
      newState.loading = true;
      return newState;
    }
    case types.APP_ACTIONS.LOADED: {
      let newState = Object.assign({}, state)
      newState.loading = false;
      return newState;
    }
    case types.APP_ACTIONS.ERROR: {
      let newState = Object.assign({}, state)
      newState.error = action.error
      return newState;
    }

    default:
      return state;
  }
}
