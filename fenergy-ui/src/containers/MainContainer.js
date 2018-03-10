import {connect} from 'react-redux';
import Main from "../components/Main";
import { getUser } from '../actions/user'
import { bindActionCreators } from 'redux'

const mapStateToProps = state => {
  return {
    notifications: state.notifications,
  }
};

const mapDispatchToProps = dispatch => {
  return {
    actions: bindActionCreators({
      getUser
    }, dispatch)
  }
};

const MainContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(Main)

export default MainContainer
