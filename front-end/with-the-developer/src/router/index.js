import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
import SearchResults from "@/views/SearchResults.vue";

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/search',
            component: SearchResults // 검색 결과 페이지
        }
    ]
});

export default router;