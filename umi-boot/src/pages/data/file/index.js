import * as React from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { QueryTable } from 'sula';

export const remoteDataSource = { type: 'umi:pageable', url: '/api/file/pageable' };

export const queryFields = [
  { field: { type: 'input', props: { placeholder: '请输入名称' } }, name: 'name', label: '名称' },
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
    title: '地址',
    key: 'url',
    ellipsis: !0,
  },
  {
    title: '文件类型',
    key: 'type',
    width: 120,
  },
  {
    title: '文件大小',
    key: 'size',
    width: 120,
  },
  {
    title: '下载次数',
    key: 'num',
    width: 120,
  },
  {
    title: '创建人',
    key: 'createdBy',
    width: 180,
    ellipsis: !0,
    filterRender: 'search',
  },
  {
    title: '创建时间',
    key: 'createdDate',
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
    action: [{ type: 'umi:delete', url: '/api/file/#{table.getSelectedRowKeys()}' }, 'refreshTable'],
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
