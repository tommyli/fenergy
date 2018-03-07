import NavBar from '../components/NavBar'
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux'
import {success} from 'react-notification-system-redux';
import {NOTIFICATION} from '../shared/constants';

const notifySuccess = success.bind(this, NOTIFICATION.DEFAULT_OPTS)

const mapStateToProps = state => {
  return {
    progressHidden: !state.app.loading
  }
};

const mapDispatchToProps = dispatch => {
  return {
    actions: bindActionCreators({
      healthCheck: notifySuccess
    }, dispatch)
  }
};

const NavBarContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(NavBar)

export default NavBarContainer
