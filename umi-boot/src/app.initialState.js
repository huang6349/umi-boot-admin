import account from '@/services/account';
import { TokenManager } from '@/utils';

export default async function() {
  const hasToken = !!(await TokenManager['getToken']());

  let initialState = { isLogin: hasToken };

  if (hasToken) {
    initialState = { ...initialState, ...(await account()) };
  }

  return initialState;
}
