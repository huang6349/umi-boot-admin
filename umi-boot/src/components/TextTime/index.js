import { Typography } from 'antd';
import dayjs from 'dayjs';

const { Text: AText } = Typography;

export default function TextTime({ children, ...props }) {
  return <AText {...props} children={dayjs(children).format('YYYY-MM-DD HH:mm:ss')} />;
}
