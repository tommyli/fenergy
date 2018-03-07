import {connect} from 'react-redux';
import Main from "../components/Main";

const mapStateToProps = state => {
  return {
    notifications: state.notifications,
  }
};

const MainContainer = connect(
  mapStateToProps,
)(Main)

export default MainContainer
