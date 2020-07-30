import IconMap from './iconMap';

export default function loopMenu(menuData = []) {
  return menuData.map(({ icon, children, ...item }) => ({
    ...item,
    icon: icon && IconMap[icon],
    children: children && loopMenu(children),
  }));
}
