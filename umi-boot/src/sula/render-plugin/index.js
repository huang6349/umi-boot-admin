import { registerRenderPlugin } from 'sula';

import TextTime from '@/components/TextTime';

import OperationGroup from './OperationGroup';

registerRenderPlugin('texttime')(TextTime);
registerRenderPlugin('operationgroup')(OperationGroup, true);
