import { TokenManager, Nprogress } from '@/utils';

export default {
  requestInterceptors: [
    function interceptorNprogress(url, options) {
      Nprogress['start']();
      return { url, options };
    },
    async function interceptorToken(url, options) {
      options['headers'] = options['headers'] || {};
      const token = await TokenManager['getToken']();
      if (token) options['headers']['Authorization'] = token;
      return { url, options };
    },
  ],
  responseInterceptors: [
    function interceptorNprogress(response) {
      Nprogress['done']();
      return response;
    },
    async function interceptorToken(response) {
      if (response['status'] === 401) {
        await TokenManager['destroy']();
      }
      if (response['headers'].get('Authorization')) {
        await TokenManager['init'](response['headers'].get('Authorization'));
      }
      return response;
    },
  ],
};
