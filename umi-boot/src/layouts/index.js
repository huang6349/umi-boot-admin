import * as React from 'react';
import ProLayout from '@ant-design/pro-layout';
import { Link, useModel } from 'umi';
import isEmpty from 'lodash/isEmpty';

import { MenuHeader, RightContent } from './components';
import { loopMenu, pathsToRegexp } from './utils';
import styles from './index.less';

const BasicLayout = ({ location, children }) => {
  const { initialState: { username, menuData = [] } = {} } = useModel('@@initialState');

  const [collapsed, setCollapsed] = React.useState(!1);

  const isPure = pathsToRegexp(['/', '/404'], location['pathname']);

  function handlePoweroff() {
    alert('退出登录');
  }

  return (
    <ProLayout
      className={styles['layout']}
      location={location}
      pure={isPure}
      menuHeaderRender={() => {
        return <MenuHeader title="umi-boot-admin" />;
      }}
      layout="topmenu"
      navTheme="dark"
      collapsed={!0}
      onCollapse={(collapsed) => setCollapsed(collapsed)}
      rightContentRender={() => {
        return <RightContent theme="dark" username={username} onPoweroff={handlePoweroff} />;
      }}
      pageTitleRender={!1}
      disableContentMargin={!0}
    >
      <ProLayout
        location={location}
        pure={isPure}
        contentStyle={{ position: 'absolute', top: 0, right: 0, bottom: 0, left: 0, margin: 0 }}
        layout="sidemenu"
        navTheme="light"
        menuHeaderRender={!1}
        collapsed={collapsed}
        onCollapse={(collapsed) => setCollapsed(collapsed)}
        headerRender={!1}
        collapsedButtonRender={!1}
        pageTitleRender={!1}
        menuItemRender={(itemProps, defaultDom) => {
          if (!isEmpty(itemProps['children']) || !itemProps['path']) {
            return defaultDom;
          }
          return <Link to={{ pathname: itemProps['path'], query: { k: new Date().getTime() } }}>{defaultDom}</Link>;
        }}
        menuDataRender={() => loopMenu(menuData)}
        itemRender={(route, params, routes) => {
          const isLastItem = routes.indexOf(route) === routes['length'] - 1;
          if (!isLastItem) {
            return <Link to={route['path']}>{route['breadcrumbName']}</Link>;
          }
          return <span>{route['breadcrumbName']}</span>;
        }}
      >
        <React.Fragment>{children}</React.Fragment>
      </ProLayout>
    </ProLayout>
  );
};

export default BasicLayout;
