<script setup>

  import {ref} from "vue";
  import axios from "axios";
  import router from "@/router/index.js";

  // pw패턴
  const pwPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+=-]).{8,16}$/;

  // email패턴
  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  // 사용자아이디, 인증코드
  const userId = ref('');
  const code = ref('');
  const emailValid = ref(true);

  // 비밀번호, 비밀번호 재확인
  const userPw = ref('');
  const userPwCheck = ref('');
  const pwValid = ref(true);
  const pwCheckValid = ref(true);

  // 검증 코드가 인증 되었는지
  const isChecked = ref(false);

  // 두 비밀번호가 일치하는지
  const isMatched = ref(false);

  const lastErrorField = ref('');
  const errorMessage = ref('');
  const lastSecondErrorField = ref('');
  const errorSecondMessage = ref('');

  // 이메일 유효성 검사
  const validateEmail = () => {
    if (!userId.value) {
      lastErrorField.value = 'email';
      errorMessage.value = '* 이메일을 입력하세요.';
      emailValid.value = false;
    } else if (!emailPattern.test(userId.value)){
      lastErrorField.value = 'email';
      errorMessage.value = '* 유효하지 않은 이메일입니다.';
      emailValid.value = false;
    } else {
      lastErrorField.value = '';
      errorMessage.value = '';
      emailValid.value = true;
    }
  };

  // 비밀번호 유효성 검사
  const validatePassword = () => {
    if (!userPw.value) {
      lastSecondErrorField.value = 'password';
      errorSecondMessage.value = '* 비밀번호를 입력하세요.';
      pwValid.value = false;
    } else if (!pwPattern.test(userPw.value)){
      lastSecondErrorField.value = 'password';
      errorSecondMessage.value = '* 영문자, 숫자, 특수문자가 하나씩 포함되어야 합니다. 최소 8자리 ~ 최대 16자리';
      pwValid.value = false;
    } else {
      lastSecondErrorField.value = '';
      errorSecondMessage.value = '';
      pwValid.value = true;
    }
  };

  // 비밀번호 재확인 유효성 검사
  const validatePasswordCheck = () => {
    if (userPw.value !== userPwCheck.value) {
      lastSecondErrorField.value = 'passwordCheck';
      errorSecondMessage.value = '* 비밀번호가 일치하지 않습니다.';
      pwCheckValid.value = false;
    } else {
      lastSecondErrorField.value = '';
      errorSecondMessage.value = '';
      pwCheckValid.value = true;
      isMatched.value = true;
    }
  };

  // 인증코드 보내기
  const sendCode = () => {
    if (userId.value){
      const sendEmailDTO = {
        userId: userId.value
      }
      axios.post('/user/send-code', sendEmailDTO)
          .then(res => {
            if (res.status === 200){
              alert('인증 코드가 발송되었습니다.');
            } else {
              alert('인증 코드 발송에 실패했습니다.')
            }
          })
          .catch(error => {
            console.error('인증코드 발송 오류', error);
          })
    }
  }

  // 인증코드 검증
  const checkCode = () => {
    const checkCodeDTO = {
      userId: userId.value,
      code: code.value
    };
    axios.post('/user/check-code', checkCodeDTO)
        .then(res => {
          if (res.status === 200){
            isChecked.value = true;
            alert('인증 코드가 검증되었습니다.');
          } else {
            alert('인증 코드 검증 실패');
          }
        })
        .catch(error => {
          console.error('인증 코드 검증 오류', error);
          alert('인증 코드 검증 실패');
        })
  }

  // 비밀번호 재설정
  const resettingPw = () => {
    if (pwValid && pwCheckValid){
      const pwResettingDTO = {
        userId : userId.value,
        code : code.value,
        userPw : userPw.value
      };
      axios.post('/user/reset-pw', pwResettingDTO)
          .then(res => {
            if (res.status === 200){
              alert('비밀번호 재설정 완료');
              moveToLogin();
            } else {
              alert('비밀번호 재설정 실패');
            }
          })
          .catch(error => {
            alert('비밀번호 재설정 실패');
            console.error('비밀번호 재설정 오류', error);
          });
    }
  }

  // 아이디 찾기
  const moveToFindId = () => {
    router.push('/find-id')
  }

  // 로그인
  const moveToLogin = () => {
    router.push('/login')
  }

</script>

<template>
  <div class="resetting-pw_all">

    <div class="resetting-pw_content">

      <div class="top_container">
        <div class="resetting-pw_title">
          <span>아이디 인증</span>
        </div>

        <div class="resetting_id_box form_item"
             :class="{ 'error-border': lastErrorField === 'email' || !emailValid }">
          <div class="email_icon">
            <img src="https://img.icons8.com/?size=100&id=53388&format=png&color=000000" width="30px" height="30px">
          </div>
          <input type="text" class="email_input" placeholder="이메일을 입력해주세요." v-model="userId" :disabled="isChecked" @blur="validateEmail">
          <button class="send_code_btn btn" :disabled="isChecked || !emailValid" @click="sendCode">인증코드 보내기</button>
        </div>

        <div class="check_code_box form_item">
          <div class="code_icon">
            <img src="https://img.icons8.com/?size=100&id=124383&format=png&color=000000" width="30px" height="30px">
          </div>
          <input type="text" class="code_input" placeholder="인증 코드를 입력해주세요." v-model="code" :disabled="isChecked">
          <button class="check_code_btn btn" @click="checkCode" :disabled="isChecked || !emailValid">코드 인증</button>
        </div>
        <div class="error_box" v-if="errorMessage">
          <span>{{ errorMessage }}</span>
        </div>
      </div>

      <div class="bottom_container">
        <div class="resetting-pw_title">
          <span>비밀번호 재설정</span>
        </div>

        <div class="pw_input_box form_item"
             :class="{ 'error-border': lastSecondErrorField === 'password' || !pwValid }">
          <div>
            <img src="https://img.icons8.com/?size=100&id=364&format=png&color=000000" width="30px" height="30px">
          </div>
          <input type="password" placeholder="비밀번호를 입력해주세요." v-model="userPw" :disabled="!isChecked" @blur="validatePassword">
        </div>

        <div class="pw_check_box form_item"
             :class="{ 'error-border': lastSecondErrorField === 'passwordCheck' || !pwCheckValid }">
          <div>
            <img src="https://img.icons8.com/?size=100&id=364&format=png&color=000000" width="30px" height="30px">
          </div>
          <input type="password" class="pw_check" placeholder="비밀번호 재확인" v-model="userPwCheck" :disabled="!isChecked" @blur="validatePasswordCheck">
        </div>
        <div class="error_box" v-if="errorSecondMessage">
          <span>{{ errorSecondMessage }}</span>
        </div>

        <div class="resetting_btn">
          <button class="resetting" :disabled="!isMatched" @click="resettingPw">재설정</button>
          <button class="find-id" @click="moveToFindId">아이디 찾기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .resetting-pw_content{
    margin-top: 50px;
    align-items: center;
    text-align: center;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  span{
    font-size: 28px;
    color: #3257B5;
  }
  .top_container{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 800px;
  }
  .form_item{
    display: flex;
    margin-top: 15px;
    align-items: center;
    width: 450px;
    justify-content: space-around;
    border: 2px solid #3257B5;
    border-radius: 10px;
  }
  .form_item .email_input{
    width: 255px;
  }
  .form_item input{
    height: 45px;
    width: 300px;
    border: none;
    border-radius: 10px;
    text-align: left;
  }
  .pw_input_box input, .pw_check_box input{
    width: 370px;
    margin-right: 17px;
  }

  .form_item button{
    color: black;
    background-color: #A4BCF9;
    border: solid 1px grey;
    height: 35px;
    border-radius: 5px;
    font-size: 15px;
    cursor: pointer;
  }

  .bottom_container{
    margin-top: 50px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .resetting_btn{
    width: 300px;
    margin-top: 25px;
    display: flex;
    justify-content: space-around;
  }
  .resetting_btn button{
    cursor: pointer;
  }
  .resetting_btn .resetting{
    font-size: 18px;
    border: solid 1px grey;
    height: 35px;
    width: 120px;
    background-color: #617CC2;
    color: white;
    border-radius: 10px;
  }
  button, input{
    font-family: "Neo둥근모 Pro";
  }
  .find-id{
    font-size: 18px;
    height: 35px;
    width: 120px;
    background-color: white;
    border: 1px solid #617CC2;
    color: #617CC2;
    border-radius: 10px;
  }
  .resetting:disabled{
    background-color: #A7A7A7;
    cursor: not-allowed;
  }
  .error-border {
    border: 2px solid red; /* 에러 시 테두리 색상 */
  }

  .error_box span{
    text-align: left;
    font-size: 15px;
    color: red;
  }
  button:disabled{
    background-color: #A7A7A7;
    cursor: not-allowed;
  }
</style>