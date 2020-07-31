import { Icon } from 'sula';

export default function loopMenu(menuData = []) {
  return menuData.map(({ icon, children, ...item }) => ({
    ...item,
    icon: icon && <Icon type={icon} />,
    children: children && loopMenu(children),
  }));
}
