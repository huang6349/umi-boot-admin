import { defineConfig } from 'umi';

export default defineConfig({
  nodeModulesTransform: {
    type: 'all',
  },
  outputPath: '../umi-boot-service/src/main/resources/static',
});
