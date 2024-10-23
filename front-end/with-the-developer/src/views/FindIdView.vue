<script setup>

import {ref, watch} from "vue";
  import router from "@/router/index.js";
  import axios from "axios";

  const userName = ref('');
  const userPhone = ref('');
  const resultId = ref('');

  // 핸드폰 번호 포맷 변경 함수
  const formatPhoneNumber = (phone) => {
    // 숫자만 추출
    const cleaned = ('' + phone).replace(/\D/g, '');
    const match = cleaned.match(/^(\d{3})(\d{4})(\d{4})$/);
    if (match) {
      return `${match[1]}-${match[2]}-${match[3]}`;
    }
    return phone; // 포맷이 맞지 않으면 원본 반환
  };

  // userPhone의 변화를 감지하여 포맷 변경
  watch(userPhone, (newValue) => {
    userPhone.value = formatPhoneNumber(newValue);
  });

  const findId = () => {
    console.log(userName.value);
    console.log(userPhone.value);
    const findIdDTO = {
      userName: userName.value,
      userPhone: userPhone.value
    }
    axios.post('/user-service/user/find-id', findIdDTO)
        .then(res => {
          if (res.status === 200){
            alert('아이디 찾기 성공');
            resultId.value = res.data;
          } else {
            alert('아이디 찾기 실패');
          }
        })
        .catch(error => {
          alert('아이디 찾기 실패');
          console.log('아이디 찾기 오류', error);
        });
  }
  const login = () => {
    router.push('/login');
  }

</script>

<template>
  <div class="find-id_all">

    <div class="find-id_content">
      <div class="find-id_title">
        <span>아이디 찾기</span>
      </div>
      <div class="form_item form_id">
        <div class="user_icon">
          <img src="https://img.icons8.com/?size=100&id=AZazdsitsrgg&format=png&color=000000" width="30px" height="30px">
        </div>
        <input type="text" placeholder="이름을 입력해주세요" v-model="userName">
      </div>
      <div class="form_item form_phone">
        <div class="phone_icon">
          <img src="https://img.icons8.com/?size=100&id=79&format=png&color=000000" width="30px" height="30px">
        </div>
        <input type="text" placeholder="휴대폰 번호를 입력해주세요" v-model="userPhone" maxlength="11">
      </div>
      <div class="find-id_btn id_btn">
        <button @click="findId">아이디 찾기</button>
      </div>
    </div>

    <div class="result-id_content">
      <div class="result-id_title">
        <span>찾은 아이디</span>
      </div>
      <div class="result-id">
        <div class="result_box">
          <span>{{ resultId }}</span>
        </div>
      </div>
      <div class="result-id_btn id_btn">
        <button @click="login" class="login">로그인</button>
        <button class="find-pw_btn" style="width: 150px" >비밀번호 찾기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .find-id_all{
    margin-top: 50px;
    align-items: center;
    text-align: center;

  }
  div{
    margin-bottom: 10px;
  }
  span{
    font-size: 28px;
    color: #3257B5;
  }
  .form_item{
    display: flex;
    justify-content: center;
    margin-top: 15px;
  }
  .form_id{
    margin-top: 30px;
  }
  .form_item input{
    height: 45px;
    width: 300px;
    border: 2px solid #3257B5;
    border-radius: 10px;
    text-align: center;
  }
  input, button{
    font-family: "Neo둥근모 Pro";
  }
  .user_icon, .phone_icon{
    line-height: 50px;
  }
  .user_icon img, .phone_icon img{
    vertical-align: middle;
  }
  .id_btn button{
    width: 100px;
    color: black;
    background-color: #A4BCF9;
    border: solid 1px grey;
    height: 35px;
    border-radius: 5px;
    margin-left: 30px;
    font-size: 15px;
    cursor: pointer;
  }
  .id_btn .login{
    font-size: 18px;
    border: solid 1px grey;
    height: 35px;
    width: 120px;
    background-color: #617CC2;
    color: white;
    border-radius: 10px;
  }
  .result-id_title{
    margin-top: 70px;
    margin-left: 30px;
  }
  .result-id{
    display: flex;
    justify-content: center;
    margin-left: 30px;
  }
  .result_box{
    width: 400px;
    height: 50px;
    border: #3257B5 solid 2px;
    border-radius: 10px;
  }
  .result_box span{
    font-size: 20px;
    color: #1b9700;
  }
  .id_btn .find-pw_btn{
    background-color: white;
    border: 1px solid #617CC2;
    color: #617CC2;
    border-radius: 10px;
  }
</style>