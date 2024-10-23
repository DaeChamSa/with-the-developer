import { createApp } from 'vue'
import axios from "axios";
import App from './App.vue'
import router from './router';

axios.defaults.baseURL = 'http://localhost:8000';
const app = createApp(App); // Vue 애플리케이션 생성
app.config.globalProperties.axios = axios;
app.use(router);  // 애플리케이션에 라우터 추가
app.mount('#app');

