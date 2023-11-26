
import React from 'react';
import { Navigate } from 'react-router-dom';

const AuthGuard = ({ isAuthenticated, children }) => {
  if (isAuthenticated) {
    return children;
  } else {
    return <Navigate to="/login" />;
  }
};

export default AuthGuard;
