import { createStore } from 'vuex';

const store = createStore({
    state: {
        accessToken: null,
        isLoggedIn: false,
    },
    mutations: {
        setAccessToken(state, token) {
            state.accessToken = token;
            state.isLoggedIn = !!token; // 토큰이 있을 경우 로그인 상태로 변경
        },
        logout(state) {
            state.accessToken = null;
            state.isLoggedIn = false;
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            localStorage.removeItem('userRole');
            alert('로그아웃 성공');
        },
    },
    actions: {
        login({ commit }, token) {
            commit('setAccessToken', token);
        },
        logout({ commit }) {
            commit('logout');
        },
    },
    getters: {
        isLoggedIn: (state) => state.isLoggedIn,
        accessToken: (state) => state.accessToken,
    },
});

export default store;