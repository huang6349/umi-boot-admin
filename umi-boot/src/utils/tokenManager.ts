import localforage from 'localforage';

interface TokenManagerType {
  tokenName: string;
  init: (token: string) => Promise<void>;
  getToken: () => Promise<string>;
  destroy: () => Promise<void>;
}

export default <TokenManagerType>{
  tokenName: 'appToken',
  async init(token) {
    await localforage.setItem(this.tokenName, token);
  },
  async getToken() {
    return await localforage.getItem(this.tokenName);
  },
  async destroy() {
    await localforage.removeItem(this.tokenName);
  },
};
