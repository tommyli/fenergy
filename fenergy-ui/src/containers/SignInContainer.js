import { connect } from 'react-redux';
import { bindActionCreators } from 'redux'
import SignIn from '../components/SignIn'
import { signOut } from '../actions/user'

const mapStateToProps = state => {
  return {
    authenticated: state.app.authenticated,
    user: state.user
  }
};

const mapDispatchToProps = dispatch => {
  return {
    actions: bindActionCreators({
      signOut
    }, dispatch)
  }
};

const SignInContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(SignIn)

export default SignInContainer
