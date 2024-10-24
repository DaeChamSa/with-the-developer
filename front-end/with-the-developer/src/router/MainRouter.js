import {createRouter, createWebHistory} from 'vue-router';

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