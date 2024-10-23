<script setup>
import { reactive } from 'vue';

import NavigationBar from "@/components/NavigationBar.vue";
import MypageSideBar from "@/components/MypageSideBar.vue";

const user = reactive({
  name: "테스트",
  prefix: "계획적인 백엔드",
  nickname: "대참사",
  email: "test@naver.com",
  phone: "010-1111-1111",
  birth: "2000.11.11",
  alarm: false
})
</script>

<template>
<NavigationBar/>
  <section>
    <MypageSideBar/>
    <div id="content">
      <h2>내 프로필</h2>
      <article id = "info">
        <button id="modify">수정하기</button>
        <div id="main_info">
          <img src="https://img.icons8.com/?size=100&id=52232&format=png&color=000000" alt="프로필이미지" id="profile">
          <ul>
            <li id="name">{{user.name}}</li>
            <li id="prefix">{{user.prefix}}</li>
            <li>{{user.nickname}}</li>
            <li>{{user.email}}</li>
          </ul>

        </div>
        <div id="sub_info">
          <div class="sub_info_content">
            <img class="info-icon" src="https://img.icons8.com/?size=100&id=UYmVJ09dKuF8&format=png&color=000000" alt="핸드폰"><p>{{user.phone}}</p>
          </div>
          <hr>
          <div class="sub_info_content">
            <img class="info-icon" src="https://img.icons8.com/?size=100&id=E5UKUDD1Rzlf&format=png&color=000000" alt="생일"><p>{{user.birth}}</p>
          </div>
          <hr>
          <div class="sub_info_content">
            <img class="info-icon" src="https://img.icons8.com/?size=100&id=54481&format=png&color=000000" alt="알림허용여부">
            <p>알림허용여부</p>
            <label id="check">
              <input role="switch" type="checkbox" :checked="user.alarm" />
            </label>
          </div>
        </div>
      </article>
    </div>
  </section>

</template>

<style scoped>
section{
  display: flex;
  height: 700px;
}
#content{
  margin: 35px 0 0 100px;
}
#info{
  width: 500px;
  height: 500px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 1px 1px 1px 1px lightgray;
  background-color: #F2F6FF;
}
#main_info{
  background-color: white;
  height: 150px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 1px 1px 1px 1px lightgray;
  margin-bottom: 20px;
  display: flex;
}
#main_info > ul{
  list-style: none;
}
#main_info > ul > li{
  margin-bottom: 8px;
}
#name{
  font-size: 20px;
}
#prefix{
  color: #014995;
}
#sub_info{
  background-color: white;
  height: 220px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 1px 1px 1px 1px lightgray;
  font-size: 17px;
}
#modify {
  width: 100px;
  height: 35px;
  font-family: "Neo둥근모 Pro";
  border: none;
  border-radius: 10px;
  transition: none;
  font-size: 17px;
  color: white;
  background-color: #B2C7F4;
  margin-bottom: 10px;
  box-shadow: 1px 1px 1px 1px lightgray;
}
#modify:hover{
  background-color: #014995;
}
#profile{
  width: 80px;
  height: 80px;
  border: 3px solid;
  border-radius: 50px;
  margin-top: 15px;
  margin-left: 15px;
}
.info-icon{
  width: 45px;
  height: 45px;
  margin-left: 15px;
}
.sub_info_content{
  display: flex;
}
.sub_info_content > p{
  margin-left: 50px;
}
label {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  cursor: pointer;
}

[type="checkbox"] {
  appearance: none;
  position: relative;
  border: max(2px, 0.1em) solid gray;
  border-radius: 1.25em;
  width: 2.25em;
  height: 1.25em;
}

[type="checkbox"]::before {
  content: "";
  position: absolute;
  left: 0;
  width: 1em;
  height: 1em;
  border-radius: 50%;
  transform: scale(0.8);
  background-color: gray;
  transition: left 250ms linear;
}

[type="checkbox"]:checked {
  background-color: tomato;
  border-color: tomato;
}

[type="checkbox"]:checked::before {
  background-color: white;
  left: 1em;
}

[type="checkbox"]:disabled {
  border-color: lightgray;
  opacity: 0.7;
  cursor: not-allowed;
}

[type="checkbox"]:disabled:before {
  background-color: lightgray;
}

[type="checkbox"]:disabled + span {
  opacity: 0.7;
  cursor: not-allowed;
}

[type="checkbox"]:focus-visible {
  outline-offset: max(2px, 0.1em);
  outline: max(2px, 0.1em) solid tomato;
}

[type="checkbox"]:enabled:hover {
  box-shadow: 0 0 0 max(4px, 0.2em) lightgray;
}

/* Global CSS */
body {
  display: grid;
  justify-content: center;
  align-items: center;
  height: 100vh;
}


*,
*::before,
*::after {
  box-sizing: border-box;
}
#check{
  margin-left: 120px;
}
</style>