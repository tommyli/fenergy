import React, { Component } from 'react';
import { Provider } from 'react-redux';
import configureStore from './store/configureStore';
import initialReduxState from './store/initialState';

import Main from './containers/MainContainer';

const store = configureStore(initialReduxState);

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Main />
      </Provider>
    );
  }
}

export default App;
