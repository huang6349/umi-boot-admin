import account from '@/services/account';

export default async function() {
  return { isLogin: !0, ...(await account()) };
}
