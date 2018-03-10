import axios from 'axios';

// // Interceptor on all requests
// axios.interceptors.request.use(function (config) {
//   return config;
// }, function (error) {
//   getStore().dispatch(createError(error))
//   getStore().dispatch(createNotifyError(error))
//   return Promise.reject(error);
// });

// // Interceptor on all responses
// axios.interceptors.response.use(function (response) {
//   return response;
// }, function (error) {
//   getStore().dispatch(createError(error))
//   getStore().dispatch(createNotifyError(error))
//   return Promise.reject(error);
// });

function requestConfig(method, params = null, body = null) {
  const upperMethod = method.toUpperCase()
  const result = {
    method: upperMethod,
    headers: {
      'Content-Type': 'application/json'
    },
    params: params
  }

  if (['POST', 'PUT'].includes(upperMethod)) {
    result.body = JSON.stringify(body);
  }

  return result
}

export function getUser() {
  return axios.get(`/auth/currentlogin`, requestConfig('GET'))
}

export function signOut() {
  return axios.post(`/auth/logout`, requestConfig('POST'))
}
