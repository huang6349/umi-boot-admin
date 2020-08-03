import * as React from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { QueryTable } from 'sula';

export const remoteDataSource = { type: 'umi:pageable', url: '/api/user/pageable' };

export const queryFields = [
  { field: { type: 'input', props: { placeholder: '请输入用户账号' } }, name: 'username', label: '用户账号' },
  { field: { type: 'input', props: { placeholder: '请输入昵称' } }, name: 'nickname', label: '昵称' },
];

export const columns = [
  {
    title: '#数据编号',
    key: 'id',
    width: 120,
    ellipsis: !0,
  },
  {
    title: '用户账号',
    key: 'username',
    width: 200,
    ellipsis: !0,
  },
  {
    title: '昵称',
    key: 'nickname',
    width: 180,
    ellipsis: !0,
  },
  {
    title: '性别',
    key: 'sexText',
    width: 100,
    ellipsis: !0,
  },
  {
    title: '角色',
    key: 'authoritieNames',
    ellipsis: !0,
  },
  {
    title: '最后修改人',
    key: 'lastModifiedBy',
    width: 180,
    filterRender: 'search',
  },
  {
    title: '最后修改时间',
    key: 'lastModifiedDate',
    width: 200,
    render: { type: 'texttime', props: { children: '#{text}' } },
  },
  {
    title: '操作',
    key: 'operator',
    width: 240,
    render: {
      type: 'operationgroup',
      max: 4,
      actionsRender: [
        { type: 'icon', props: { type: 'edit', text: '编辑' } },
        {
          type: 'icon',
          confirm: '您确认要执行删除操作吗？',
          props: { type: 'delete', text: '删除' },
          action: [{ type: 'umi:delete', url: '/api/user/#{record["id"]}' }, 'refreshTable'],
        },
        { type: 'icon', props: { type: 'file', text: '详情' } },
        {
          type: 'icon',
          text: '启用',
          props: { type: 'reload' },
          action: [{ type: 'umi:put', url: '/api/user/enable/#{record["id"]}' }, 'refreshTable'],
        },
        {
          type: 'icon',
          text: '禁用',
          props: { type: 'undo' },
          action: [{ type: 'umi:put', url: '/api/user/disable/#{record["id"]}' }, 'refreshTable'],
        },
        {
          type: 'icon',
          text: '重置密码',
          props: { type: 'key' },
          action: [{ type: 'umi:put', url: '/api/user/reset/password/#{record["id"]}' }, 'refreshTable'],
        },
      ],
    },
  },
];

export const actions = [
  {
    type: 'button',
    disabled: ({ table }) => {
      const selectedRowKeys = table.getSelectedRowKeys();
      return !(selectedRowKeys && selectedRowKeys.length);
    },
    confirm: '您确认要执行删除操作吗？',
    props: {
      type: 'primary',
      icon: { type: 'delete' },
      danger: !0,
      children: '批量删除',
    },
    action: [{ type: 'umi:delete', url: '/api/user/#{table.getSelectedRowKeys()}' }, 'refreshTable'],
  },
];

const IndexPage = () => {
  return (
    <PageHeaderWrapper className="sula" title={!1}>
      <QueryTable
        fields={queryFields}
        layout="vertical"
        actionsRender={actions}
        rowSelection={{}}
        rowKey="id"
        columns={columns}
        remoteDataSource={remoteDataSource}
      />
    </PageHeaderWrapper>
  );
};

export default IndexPage;
