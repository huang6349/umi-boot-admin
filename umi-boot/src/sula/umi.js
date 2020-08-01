import { registerActionPlugin } from 'sula';
import { request } from 'umi';

registerActionPlugin('umi:pageable', (ctx, { url, params: { current, pageSize, ...params } }) => {
  const page = current - 1;
  const size = pageSize;
  return request(url, { method: 'GET', params: { page, size, ...params } }).then(({ data }) => data);
});

registerActionPlugin('umi:delete', (ctx, { url }) => {
  return request(url, { method: 'DELETE' });
});
