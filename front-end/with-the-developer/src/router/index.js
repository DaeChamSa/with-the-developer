import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
import SearchResults from "@/views/SearchResults.vue";
import RegisterView from "@/views/RegisterView.vue";
import TosView from "@/views/TosView.vue";
import LoginView from "@/views/LoginView.vue";
import FindIdView from "@/views/FindIdView.vue";
import MainPageBefore from "@/views/MainPageBefore.vue";
import Cart from "@/views/Cart.vue";
import DbtiTestView from "@/views/DbtiTestView.vue";
import AdminUser from "@/views/Admin-User.vue";
import AdminGoods from "@/views/AdminGoods.vue";
import AdminTag from "@/views/Admin-Tag.vue";
import PayFail from "@/views/PayFail.vue";
import PayComplete from "@/views/PayComplete.vue";
import DbtiResultView from "@/views/DbtiResultView.vue";


const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            component: MainPageBefore
        },
        {
            path: '/search',
            component: SearchResults // 검색 결과 페이지
        },
        {
            path: '/register/tos',
            component: TosView  // 약관동의 페이지
        },
        {
            path: '/register',
            component: RegisterView // 회원가입 페이지
        },
        {
            path: '/login',
            component: LoginView    // 로그인 페이지
        },
        {
            path: '/find-id',
            component: FindIdView   // 아이디 찾기
        },
        {
            path: '/dbti-test',
            component: DbtiTestView // 성향 테스트
        },
        {
            path: '/dbti-result/:result',
            name: 'ResultPage',
            component: DbtiResultView,   // 성향테스트 결과
            props: true
        },
        {
            path: '/cart-goods',
            component: Cart  // 장바구니
        },
        {
            path: '/payment/fail',
            component: PayFail
        },
        {
            path: '/payment/complete',
            component: PayComplete
        },
        {
            path: '/test',
            component: DbtiTestView // 성향 테스트
        },
        // *** 관리자 페이지
        {
            path: '/admin/user/status',
            component: AdminUser,
        },
        {
            path: '/goods',
            component: AdminGoods,
        },
        {
            path: '/jop-tag',
            component: AdminTag,
        }
        // *** 관리자
    ]
});

export default router;