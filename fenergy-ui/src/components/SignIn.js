import React from 'react';
import PropTypes from 'prop-types';
import IconButton from 'material-ui/IconButton';
import Avatar from 'material-ui/Avatar';
import faGoogle from '@fortawesome/fontawesome-free-brands/faGoogle'
import faSignOutAlt from '@fortawesome/fontawesome-free-solid/faSignOutAlt'
import FontAwesomeIcon from '@fortawesome/react-fontawesome'

const SignIn = ({ authenticated = false, user, actions }) => {

  const googleSignIn = () => { window.location.href = "/auth/signin/google" }

  return (
    <span>{
      authenticated ?
        (
          <span>
            <IconButton color="inherit">
              <Avatar alt={user.name} src={user.picture} />
            </IconButton>
            <IconButton color="inherit">
              <FontAwesomeIcon icon={faSignOutAlt} onClick={actions.signOut} />
            </IconButton>
          </span>
        ) :
        (
          <span>
            <IconButton color="inherit">
              <FontAwesomeIcon icon={faGoogle} onClick={googleSignIn} />
            </IconButton>
          </span>
        )
    }
    </span>
  )
}

SignIn.propTypes = {
  authenticated: PropTypes.bool,
  user: PropTypes.shape({
    name: PropTypes.string,
    given_name: PropTypes.string,
    locale: PropTypes.string,
    family_name: PropTypes.string,
    email: PropTypes.string,
    picture: PropTypes.string
  }),
  actions: PropTypes.shape({
    signOut: PropTypes.func,
  })
};

export default SignIn;
