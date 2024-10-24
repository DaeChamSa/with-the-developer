import {createRouter, createWebHistory} from 'vue-router';
import Main from "@/views/MainPage-Before-Login.vue";


const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/cart-goods',
            component: () => import('@/views/Cart.vue')
        }
    ]
})

export default router;