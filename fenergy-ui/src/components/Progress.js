import React from 'react';
import CircularProgress from "material-ui/Progress/CircularProgress";
import PropTypes from 'prop-types';

const Progress = ({ hidden = true }) => {
  return (
    <div hidden={hidden}>
      <CircularProgress variant={"determinate"} color={"secondary"} size={25} thickness={7} />
    </div>
  )
}

Progress.propTypes = {
  hidden: PropTypes.bool
};

export default Progress;
