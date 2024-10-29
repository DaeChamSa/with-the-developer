<script setup>

import router from "@/router/index.js";
import {ref} from "vue";
import {useStore} from "vuex";
import axios from "axios";

// 회원가입 창
const moveToRegister = () => {
  router.push('/register/tos');
}

// 메인화면
const moveToMain = () => {
  router.push('/main')
}

// 아이디 찾기 창
const moveTo = (type) => {
  router.push(`${type}`);
}

// 성향테스트 창
function moveToDbtiTest(){
  router.push('/prefix/test');
}

const userId = ref('');
const userPw = ref('');
const saveId = ref(false);

// 상태 관리
const store = useStore();

const login = () => {

  const userDTO = {
    userId : userId.value,
    userPw : userPw.value
  }
  axios.post('/user/login', userDTO)
      .then(res => {
        if (res.status === 200){
          const accessToken = res.headers['authorization']; // 대소문자 구분
          const refreshToken = res.headers['refresh-token']; // 대소문자 구분
          store.dispatch('login', accessToken); // Vuex 스토어에 로그인 처리
          store.dispatch('startLogoutTimer');  // 30분 타이머 (토큰 유효시간)
          // 북마크 목록 vuex에 초기화
          store.dispatch('fetchItems');
          // 로컬스토리지에 토큰값 저장
          localStorage.setItem('refreshToken', refreshToken);

          // 로컬스토리지에 유저 권한 저장
          const userRole = parseJwt(accessToken).auth; // accessToken에서 auth 추출
          const userCode = parseJwt(accessToken).userCode;
          const userId = parseJwt(accessToken).sub;

          localStorage.setItem('userRole', userRole);
          localStorage.setItem('userCode', userCode);
          localStorage.setItem('userId', userId);
          store.dispatch('setRole', userRole);

          alert('로그인 성공');
          if(userRole==='ROLE_ADMIN'){
            moveTo('/admin/user/status');
          }else if (!res.data){
            moveToDbtiTest();
          } else{
            moveToMain();
          }
        } else if (res.status === 401){
          alert('정지된 회원입니다.');
        }
        else {
          alert('로그인 실패');
        }
      })
      .catch(error => {
        alert('로그인 실패');
        console.log('로그인 실패', error);
      });
}

// JWT 파싱 함수
const parseJwt = (token) => {
  try {
    const base64Url = token.split('.')[1]; // 페이로드 부분
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/'); // Base64Url을 Base64로 변환
    return JSON.parse(atob(base64)); // Base64 디코딩 후 JSON 파싱
  } catch (error) {
    console.error('JWT 파싱 오류:', error);
    return null;
  }
}

// 카카오 로그인
const moveToKakao = () => {
  window.location.href =
      'https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=34910f6e018bcd7dc06e7b7e09db20f5&redirect_uri=http://localhost:8080/oauth/kakao';
}
</script>

<template>
  <div class="login-container">
    <h2>로그인</h2>
    <form>
      <div class="input-group">
        <input type="text" placeholder="아이디를 입력해주세요." v-model="userId" @keyup.enter="login"/>
      </div>
      <div class="input-group">
        <input type="password" placeholder="비밀번호를 입력해주세요." v-model="userPw" @keyup.enter="login"/>
      </div>

      <div class="options">
        <label>
          <input type="checkbox" v-model="saveId" />
          아이디 저장
        </label>
        <span>
          <span @click="moveTo('/find-id')">아이디 찾기</span> / <span @click="moveTo('/reset-pw')">비밀번호 찾기</span>
        </span>
      </div>

      <div class="buttons">
        <button type="button" class="login-btn" @click="login">로그인</button>
        <button type="button" class="signup-btn" @click="moveToRegister">회원가입</button>
      </div>

      <div class="social-login">
        <p>이런 로그인 방법도 있어요!</p>
        <h3>간편 로그인</h3>
        <div class="social-login_div">
          <button type="button" class="kakao-login" @click="moveToKakao">
            <img src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png" alt="카카오 로그인" />
            카카오 로그인
          </button>
        </div>
      </div>
    </form>
  </div>
</template>

<style scoped>
button, input, label{
  font-family: "Neo둥근모 Pro";
}
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 30px;
}

h2 {
  font-size: 24px;
  color: #3257B5;
  margin-bottom: 20px;
}

.input-group {
  margin-bottom: 10px;
}

.input-group input {
  width: 400px;
  height: 35px;
  padding: 10px;
  border: 2px solid #617CC2;
  border-radius: 10px;
  font-size: 14px;
}

.options {
  display: flex;
  justify-content: space-around;
  width: 420px;
  margin-bottom: 20px;
}

.options label {
  font-size: 14px;
}

.options span {
  color: #003399;
  text-decoration: none;
  font-size: 14px;
  cursor: pointer;
}

.buttons {
  display: flex;
  justify-content: space-around;
  width: 420px;
  margin-bottom: 20px;
}

.login-btn, .signup-btn {
  width: 140px;
  padding: 10px;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

.login-btn {
  background-color: #617CC2;
  color: white;
  border-radius: 10px;
}

.signup-btn {
  background-color: white;
  border: 1px solid #617CC2;
  color: #617CC2;
  border-radius: 10px;
}

.social-login {
  text-align: center;
  margin-top: 20px;

}

.social-login p {
  font-size: 14px;
  color: #666;
}

.social-login h3 {
  font-size: 18px;
  color: #617CC2;
  margin-bottom: 10px;
}
.social-login_div{
  width: 420px;
  display: flex;
  justify-content: center;
}
.kakao-login {
  background-color: #ffe812;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  display: flex;
  align-items: center;
}

.kakao-login img {
  width: 20px;
  margin-right: 10px;
}
</style>
