import { createStore } from 'vuex';
import axios from "axios";
const store = createStore({
    state: {
        accessToken: localStorage.getItem('accessToken') || null,
        isLoggedIn: !!localStorage.getItem('accessToken'),
        bookmarkList : [],
        userRole: localStorage.getItem('userRole') || null,
        logoutTimer: null,  // 로그아웃 타이머 30분
    },
    mutations: {
        setAccessToken(state, token) {
            state.accessToken = token;
            state.isLoggedIn = !!token; // 토큰이 있을 경우 로그인 상태로 변경
            if (token) {
                localStorage.setItem('accessToken', token); // 토큰을 localStorage에 저장
            }
        },
        logout(state) {
            state.accessToken = null;
            state.isLoggedIn = false;
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            localStorage.removeItem('userRole');
            localStorage.removeItem('userCode');
            localStorage.removeItem('userId');
            alert('로그아웃 되었습니다.');
        },
        setLogoutTimer(state, timer) {
            state.logoutTimer = timer;
        },
        clearLogoutTimer(state) {
            if (state.logoutTimer) {
                clearTimeout(state.logoutTimer); // 기존 타이머가 있을 경우 제거
                state.logoutTimer = null;
            }
        },
        setMyBookmark(state, data) {
            state.bookmarkList.value = data;
        },
        setUserRole(state, role){
            state.userRole = role;
        }
    },
    actions: {
        login({ commit }, token) {
            commit('setAccessToken', token);
        },
        logout({ commit }) {
            commit('logout');
        },
        startLogoutTimer({ commit, dispatch }) {
            // 30분 후 로그아웃 실행
            const timer = setTimeout(() => {
                alert('세션이 만료되었습니다.');
                dispatch('logout');
            }, 1000 * 60 * 30);

            commit('setLogoutTimer', timer); // 타이머 저장
        },
        async fetchItems({ commit }) {
            try {
                const response = await axios.get('http://localhost:8080/bookmark',{
                    headers: {
                        Authorization: `${localStorage.getItem('accessToken')}`
                    }
                }); // Spring Boot API 엔드포인트 호출
                commit('setMyBookmark', response.data); // 데이터 저장
            } catch (error) {
                console.error('Error fetching items:', error);
            }
        },
        setRole({ commit }, role) {
            commit('setUserRole', role);
        }
    },
    getters: {
        isLoggedIn: (state) => state.isLoggedIn,
        accessToken: (state) => state.accessToken,
        bookmarkList: (state) => state.bookmarkList,
        userRole: (state) => state.userRole
    }
});

export default store;