import {applyMiddleware, createStore} from 'redux';
import {composeWithDevTools} from 'redux-devtools-extension';
import rootReducer from '../reducers/root';
import thunk from 'redux-thunk';

let store;

export default initialState => {
  console.log("Configuring dev store")

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
};
