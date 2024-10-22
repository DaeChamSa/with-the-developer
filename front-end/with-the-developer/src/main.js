import { createApp } from 'vue'

import App from './App.vue'
import router from './router';

const app = createApp(App); // Vue 애플리케이션 생성
app.use(router);  // 애플리케이션에 라우터 추가
app.mount('#app');

