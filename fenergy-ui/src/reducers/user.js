import initialState from '../store/initialState';
import { USER_ACTIONS } from '../actions/actionTypes';

export function user(state = initialState.user, action) {

  switch (action.type) {

    case USER_ACTIONS.GET_USER: {
      let newState = Object.assign({}, state)
      newState = action.user
      return newState;
    }
    case USER_ACTIONS.SIGN_OUT: {
      let newState = Object.assign({}, state)
      newState = initialState.user;
      return newState;
    }

    default:
      return state;
  }
}
