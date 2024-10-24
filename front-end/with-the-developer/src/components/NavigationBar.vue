<script setup>

import {computed, ref} from 'vue';
import router from "@/router/index.js";

  const searchState = ref(false);

  // accessToken 여부에 따라 로그인, 로그아웃 변경
  const accessToken = ref(localStorage.getItem('accessToken'));
  const isLoggedIn = computed(() => !!accessToken.value);

  function switchSearch() {
    searchState.value = !searchState.value;
  }

  // 로그인창으로
  const moveToLogin = () => {
    router.push('/login');
  }

  // 로그아웃 (토큰 삭제)
  const logout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    accessToken.value = null;
    console.log('로그아웃');
    alert('로그아웃 성공');
  }

</script>

<template>
  <header>
    <div id="nav-left">
      <img src="../assets/images/logo.png" alt="로고 이미지" id="logo-image">
      <ul class="nav-ul">
        <li class="nav-menu"><a>게시판</a></li>
        <li class="nav-menu"><a>채용공고</a></li>
        <li class="nav-menu"><a>굿즈</a></li>
        <li class="nav-menu"><a>마이페이지</a></li>
      </ul>
    </div>
    <div id="nav-right">
      <ul class="nav-ul">
        <li class="nav-menu">
          <span v-if="!isLoggedIn" @click="moveToLogin">로그인</span>
          <span v-else @click="logout">로그아웃</span>
        </li>
        <li class="nav-menu"><a id="login"><img src="https://img.icons8.com/?size=100&id=eMfeVHKyTnkc&format=png&color=000000" alt="alarm" class="nav-img"></a></li>
        <li class="nav-menu"><a id="login"><img src="https://img.icons8.com/?size=100&id=zhda2EVBCvHY&format=png&color=000000" alt="cart" class="nav-img"></a></li>
        <li class="nav-menu">
          <a id="login">
            <img src="https://img.icons8.com/?size=100&id=elSdeHsB03U3&format=png&color=000000" alt="search" class="nav-img" @click="switchSearch">
          </a>
        </li>
      </ul>
    </div>
  </header>
  <form id="search-input"  v-if="searchState">
    <input type="text" placeholder="검색어 입력">
    <button type="submit">검색</button>
  </form>

</template>

<style scoped>
header{
  font-family: "Neo둥근모 Pro";
  font-size: 20px;
  display: flex;
  justify-content: center;
  border-bottom: 1px solid black;
}
#nav-left{
  display: flex;
  float: left;
  width: 700px;
}
#logo-image{
  width: 170px;
  height: 50px;
  margin-top: 5px;
  margin-left: 30px;
}
.nav-ul{
  list-style: none;
  display: flex;
  margin-top: 20px;
  white-space: nowrap;
}
.nav-menu{
  margin-right: 40px;
  height: auto;
}
.nav-menu span{
  cursor: pointer;
}
.nav-menu span:hover{
  color: #1b5ac2;
}
.nav-menu > a {
  text-decoration-line: none;
  color: #7E7E7E;
}
.nav-menu > a:hover{
  color: #1b5ac2;
}
#nav-right{
  display: flex;
  float: right;
  width: 380px;
}
#login{
  text-decoration-line: none;
  color: black;
}
.nav-img{
  width: 40px;
  height: 40px;
  position: relative;
  top: -10px;
}
#search-input{
  height: 40px;
  width: 320px;
  border: 1px solid #1b5ac2;
  padding: 0;
  position: absolute;
  right: 40%;
  top: 8%;
}
input{
  font-size: 16px;
  width: 250px;
  padding: 10px;
  border: 0;
  outline: none;
  float: left;
}
button{
  width: 50px;
  height: 100%;
  border: 0;
  background-color: #1b5ac2;
  float: left;
  color: white;
}


</style>