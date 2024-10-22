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
  alarm: true
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
  height: 120px;
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
</style>