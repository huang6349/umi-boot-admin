import * as React from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { QueryTable } from 'sula';

export const remoteDataSource = { type: 'umi:pageable', url: '/api/permission/pageable' };

export const queryFields = [
  { field: { type: 'input', props: { placeholder: '请输入名称' } }, name: 'name', label: '名称' },
  { field: { type: 'input', props: { placeholder: '请输入路径' } }, name: 'path', label: '路径' },
];

export const columns = [
  {
    title: '#数据编号',
    key: 'id',
    width: 120,
    ellipsis: !0,
  },
  {
    title: '名称',
    key: 'name',
    width: 200,
    ellipsis: !0,
  },
  {
    title: '图标',
    key: 'icon',
    width: 200,
    ellipsis: !0,
  },
  {
    title: '排序',
    key: 'seq',
    width: 120,
    ellipsis: !0,
  },
  {
    title: '路径',
    key: 'path',
    width: 200,
    ellipsis: !0,
  },
  {
    title: '描述',
    key: 'desc',
    filterRender: 'search',
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
    action: [{ type: 'umi:delete', url: '/api/permission/#{table.getSelectedRowKeys()}' }, 'refreshTable'],
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
