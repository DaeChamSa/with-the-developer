import {createRouter, createWebHistory} from "vue-router";
import SearchResults from "@/views/SearchResults.vue";
import Cart from "@/views/Cart.vue";
import AdminUser from "@/views/admin/Admin-User.vue";
import AdminGoods from "@/views/admin/AdminGoods.vue";
import AdminTag from "@/views/admin/Admin-Tag.vue";
import PayFail from "@/views/PayFail.vue";
import PayComplete from "@/views/PayComplete.vue";
import AdminGoodsDetail from "@/views/admin/AdminGoodsDetail.vue";
import PrefixRouter from "@/router/PrefixRouter.js";
import UserRouter from "@/router/UserRouter.js";
import GoodsList from "@/views/GoodsList.vue";
import OrderList from "@/views/OrderList.vue";
import OrderDetail from "@/views/OrderDetail.vue";
import GoodsDetail from "@/views/GoodsDetail.vue";
import NotFound from "@/views/error/NotFound.vue";
import MainRouter from "@/router/MainRouter.js";
import MyPageRouter from "@/router/MyPageRouter.js";
import CommunityRouter from "@/router/CommunityRouter.js";
import ProjectRouter from "@/router/ProjectRouter.js";
import TeamRouter from "@/router/TeamRouter.js";
import AdminTeamPost from "@/views/admin/post/AdminTeamPost.vue";
import AdminComuPost from "@/views/admin/post/AdminComuPost.vue";
import AdminProjectPost from "@/views/admin/post/AdminProjectPost.vue";
import AdminRecruitPost from "@/views/admin/post/AdminRecruitPost.vue";
import RecruitRouter from "@/router/RecruitRouter.js";
import AdminOrder from "@/views/admin/AdminOrder.vue";

const routes = [
        {
            path: '/search',
            component: SearchResults // 검색 결과 페이지
        },
        {
            path: '/cart-goods',
            component: Cart  // 장바구니
        },
        {
            path: '/mypage/orders',
            component: OrderList
        },
        {
            path: '/mypage/orders/:orderCode',
            component: OrderDetail,
            props: true
        },
        {
            path: '/payment/fail',
            component: PayFail
        },
        {
            path: '/payment/complete/:orderUid',
            component: PayComplete
        },
        {
            path: '/goods/:goodsCode',
            component: GoodsDetail
        },
        {
            path: '/goods',
            component: GoodsList
        },
        // *** 관리자 페이지
        {
            path: '/admin/user/status',
            component: AdminUser
        },
        {
            path: '/admin/goods',
            component: AdminGoods,
        },
        {
            path: '/admin/goods/:goodsCode',
            component: AdminGoodsDetail,
            props: true
        },
        {
            path: '/admin/jobTag',
            component: AdminTag
        },
        {
            path: '/admin/team',
            component: AdminTeamPost
        },
        {
            path: '/admin/community',
            component: AdminComuPost
        },
        {
            path: '/admin/project',
            component: AdminProjectPost
        },
        {
            path: '/admin/recruit',
            component: AdminRecruitPost
        },
        {
            path: '/admin/order',
            component: AdminOrder
        },
        // *** 관리자
        // 에러 페이지
        {
            // 404 NotFound
            path: '/notFound',
            name: "notFound",
            component: NotFound
        },
        // 성향 라우터
        ...PrefixRouter,

        // 유저 라우터
        ...UserRouter,

        // 메인 페이지 라우터
        ...MainRouter,

        // 마이 페이지 라우터
        ...MyPageRouter,

        // 커뮤니티 게시판 라우터
        ...CommunityRouter,

        // 프로젝트 게시판 라우터
        ...ProjectRouter,

        // 팀모집 게시판 라우터
        ...TeamRouter,

        // 채용공고 게시판 라우터
        ...RecruitRouter,

        {
            path: "/:pathMatch(.*)*",
            redirect: "/notFound"
        },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
    // 라우팅 시 화면 최 상단으로 이동됨.
    scrollBehavior(to, from, savedPosition) {
        // savedPosition이 있는 경우(예: 뒤로 가기), 해당 위치로 이동
        if (savedPosition) {
            return savedPosition;
        } else {
            // 새로운 페이지 이동 시 맨 위로 스크롤
            return { top: 0 };
        }
    }
});

// 페이지 이동 전에 실행되는 가드
router.beforeEach((to, from, next) => {
    const allowedPages = ['/prefix/result', '/prefix/job-tag']; // `dbti`와 `jobTag`가 필요한 페이지 목록
    if (!allowedPages.includes(to.path)) {
        // 페이지가 허용되지 않은 경우 localStorage에서 항목 삭제
        localStorage.removeItem('dbti');
        localStorage.removeItem('jobTag');
    }
    next(); // 페이지 이동 허용
});

export default router;