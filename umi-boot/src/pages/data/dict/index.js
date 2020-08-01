import * as React from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { QueryTable } from 'sula';

export const remoteDataSource = { type: 'umi:pageable', url: '/api/dict/pageable' };

export const queryFields = [
  { field: { type: 'input', props: { placeholder: '请输入名称' } }, name: 'name', label: '名称' },
  { field: { type: 'input', props: { placeholder: '请输入唯一标识码' } }, name: 'code', label: '唯一标识码' },
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
    title: '唯一标识码',
    key: 'code',
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
    width: 240,
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
    action: [{ type: 'umi:delete', url: '/api/dict/#{table.getSelectedRowKeys()}' }, 'refreshTable'],
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
