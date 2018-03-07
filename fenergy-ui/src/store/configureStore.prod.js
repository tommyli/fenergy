import {applyMiddleware, createStore} from 'redux';
import {composeWithDevTools} from 'redux-devtools-extension/logOnlyInProduction';
import rootReducer from '../reducers/root';
import thunk from 'redux-thunk';

let store;

export default initialState => {
  if (store) {
    return store;
  }
  const createdStore = createStore(
    rootReducer,
    initialState,
    composeWithDevTools(
      applyMiddleware(thunk)
    )
  );

  store = createdStore;

  return store;
}
