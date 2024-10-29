<script setup>
import {onMounted, ref} from 'vue';
import MypageSideBar from "@/components/MypageSideBar.vue";
import axios from "axios";
import ModifyUserInfo from "@/views/mypage/info/ModifyUserInfo.vue";

const userInfo = ref({});
const modifyModalStatus = ref(false);

const openModifyModal = () => {
  modifyModalStatus.value = true;
}
const cancelModifyModal = () => {
  modifyModalStatus.value = false;
}
const confirmChangeResNoti = () => {
  // 삭제 확인 alert
  const confirmed = window.confirm('알림 허용 여부를 변경하시겠습니까?');

  if (confirmed) {
    changeResNoti(); // 확인 시 삭제 메서드 호출
  }
};
const changeResNoti = async () => {
  try {
    await axios.put(`user/res-noti`, {});
    alert('알림 허용 유무가 변경 되었습니다.');
  } catch (error) {
    console.error('알림 허용 유무 변경 중 오류 발생:', error);
    alert('알림 허용 유무 변경에 실패했습니다.');
  }
};
const fetchUserInfo = async () => {
  try{
    return (await axios.get('user')).data;
  }catch (error){
    alert('회원정보 조회 중 에러 발생!' + error);
  }
}
onMounted(async () => {
  userInfo.value = await fetchUserInfo();
})
</script>

<template>

  <section>
    <ModifyUserInfo v-if="modifyModalStatus" @cancelModal="cancelModifyModal" :user = "userInfo" />
    <MypageSideBar/>
    <div id="content">
      <h2>내 프로필</h2>
      <article id = "info">
        <button id="modify" @click="openModifyModal">수정하기</button>
        <div id="main_info">
          <img src="https://img.icons8.com/?size=100&id=52232&format=png&color=000000" alt="프로필이미지" id="profile">
          <ul>
            <li id="name">{{userInfo.userName}}</li>
            <li id="prefix">감각적인백엔드</li>
            <li>{{userInfo.userNick}}</li>
            <li>{{userInfo.userId}}</li>
          </ul>

        </div>
        <div id="sub_info">
          <div class="sub_info_content">
            <img class="info-icon" src="https://img.icons8.com/?size=100&id=UYmVJ09dKuF8&format=png&color=000000" alt="핸드폰"><p>{{userInfo.userPhone}}</p>
          </div>
          <hr>
          <div class="sub_info_content">
            <img class="info-icon" src="https://img.icons8.com/?size=100&id=E5UKUDD1Rzlf&format=png&color=000000" alt="생일"><p>{{userInfo.userBirth}}</p>
          </div>
          <hr>
          <div class="sub_info_content">
            <img class="info-icon" src="https://img.icons8.com/?size=100&id=54481&format=png&color=000000" alt="알림허용여부">
            <p>알림허용여부</p>
            <label id="check">
              <input role="switch" type="checkbox" :checked="userInfo.resNoti" @change="confirmChangeResNoti" />
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
  width: 600px;
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
  margin-left: 220px;
}
</style>