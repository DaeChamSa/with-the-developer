<script setup>

import NavigationBar from "@/components/NavigationBar.vue";
import {computed, ref, watch} from "vue";
import axios from "axios";

// 아이디
const id = ref('');
const idValid = ref(true);
// 이메일
const email = ref('');
const emailValid = ref(true);
// 선택된 이메일 값
const selectedEmail = ref('직접입력');

// 사용자 전체 아이디 (아이디 + 이메일)
let userId;
const fixedUserId = () => {
  userId = id + email;
}

// 인증코드
const code = ref('');

// 이름
const name = ref('');
const nameValid = ref(true);

// 닉네임
const nick = ref('');
const nickValid = ref(true);

// 비밀번호
const password = ref('');
const pwValid = ref(true);
// 비밀번호 재확인
const passwordCheck = ref('');
const pwCheckValid = ref(true);

// 휴대폰 번호
const phone1 = ref('');
const phone2 = ref('');
const phone3 = ref('');

const phone = ref('');
const phoneValid = ref(true);

// 생년월일
const birthDay = ref('');
const birthValid = ref(false);

// 선택 성별 저장
const selectedGender = ref('');
const genderValid = ref(false);
const selectGender = (gender) => {
  selectedGender.value = gender;
  console.log(selectedGender.value);
}

// 유효성 검사 틀 및 메세지
const lastTopErrorField = ref('');
const lastMiddleErrorField = ref('');
const lastBottomErrorField = ref('');
const errorTopMessage = ref('');
const errorMiddleMessage = ref('');
const errorBottomMessage = ref('');

// 아이디 유효성 검사
const validateId = () => {
  if (!id.value) {
    lastTopErrorField.value = 'id';
    errorTopMessage.value = '* 아이디를 입력하세요.';
    idValid.value = false;
  } else {
    lastTopErrorField.value = '';
    errorTopMessage.value = '';
    idValid.value = true;
  }
};

// 이메일 유효성 검사
const validateEmail = () => {
  if (!email.value) {
    lastTopErrorField.value = 'email';
    errorTopMessage.value = '* 이메일을 입력하세요.';
    emailValid.value = false;
  } else {
    lastTopErrorField.value = '';
    errorTopMessage.value = '';
    emailValid.value = true;
  }
};

// 인증코드 유효성 검사
const validateCode = () => {
  if (!code.value) {
    lastTopErrorField.value = 'code';
    errorTopMessage.value = '* 인증코드를 입력하세요.';
  } else {
    lastTopErrorField.value = '';
    errorTopMessage.value = '';
  }
};

// 이름 유효성 검사
const validateName = () => {
  if (!name.value) {
    lastTopErrorField.value = 'name';
    errorTopMessage.value = '* 이름을 입력하세요.';
    nameValid.value = false;
  } else {
    lastTopErrorField.value = '';
    errorTopMessage.value = '';
    nameValid.value = true;
  }
};


// 닉네임 유효성 검사
const validateNick = () => {
  if (!nick.value) {
    lastMiddleErrorField.value = 'nick';
    errorMiddleMessage.value = '* 닉네임을 입력하세요.';
    nickValid.value = false;
  } else {
    lastMiddleErrorField.value = '';
    errorMiddleMessage.value = '';
    nickValid.value = true;
  }
};

  // 비밀번호 유효성 검사
  const validatePassword = () => {
    if (!password.value) {
      lastMiddleErrorField.value = 'password';
      errorMiddleMessage.value = '* 비밀번호를 입력하세요.';
      pwValid.value = false;
    } else {
      lastMiddleErrorField.value = '';
      errorMiddleMessage.value = '';
      pwValid.value = true;
    }
  };

    // 비밀번호 재확인 유효성 검사
    const validatePasswordCheck = () => {
      if (password.value !== passwordCheck.value) {
        lastMiddleErrorField.value = 'passwordCheck';
        errorMiddleMessage.value = '* 비밀번호가 일치하지 않습니다.';
        pwCheckValid.value = false;
      } else {
        lastMiddleErrorField.value = '';
        errorMiddleMessage.value = '';
        pwCheckValid.value = true;
      }
  };

// 핸드폰번호 유효성 검사
const validatePhone = () => {
  phone.value = phone1.value + '-' + phone2.value + '-' + phone3.value;
  console.log(phone.value);
  console.log(phone.value);
  if (!phone.value || phone.value.length < 13) {
    lastBottomErrorField.value = 'phone';
    errorBottomMessage.value = '* 핸드폰 번호를 입력하세요.';
    phoneValid.value = false;
  } else {
    lastBottomErrorField.value = '';
    errorBottomMessage.value = '';
    phoneValid.value = true;
  }
};

// 생년월일 유효성 검사
const validateBirth = () => {
  if (!birthDay.value || birthDay.value.length < 8 ) {
    lastBottomErrorField.value = 'birthDay';
    errorBottomMessage.value = '* 생년월일 0자리를 입력하세요.';
    birthValid.value = false;
  } else {
    lastBottomErrorField.value = '';
    errorBottomMessage.value = '';
    birthValid.value = true;
  }
};

// 성별 유효성 검사
const validateGender = () => {
  if (!selectedGender.value) {
    lastBottomErrorField.value = 'selectedGender';
    errorBottomMessage.value = '* 성별을 선택해 주세요';
    genderValid.value = false;
  } else {
    lastBottomErrorField.value = '';
    errorBottomMessage.value = '';
    genderValid.value = true;
  }
};

// 아이디 중복 검증 변수
const idCheck = ref(false);
// 인증코드 검증 변수
const codeCheck = ref(false);
// 닉네임 중복 검증 변수
const nickCheck = ref(false);
// 휴대폰 번호 중복 검증 변수
const phoneCheck = ref(false);

// 아이디 중복확인 후 인증코드 보내기로 변경용
const isIdValid = ref(false);
const checkIdAndSendCode = () => {
  if (isIdValid.value) {
    sendCode(); // 인증 코드 보내기
  } else {
    checkingId(); // 아이디 중복 확인
  }
}
// 아이디 중복확인
const checkingId = () => {
  axios.get(`http://localhost:8000/user-service/user/${userId}`)
      .then(res => {
        if(res.data.isUnique){
          isIdValid.value = true;
          idCheck.value = true;
          alert('사용 가능한 아이디입니다.');
        } else{
          alert('이미 존재하는 아이디입니다.');
        }
      })
      .catch(error => {
        console.error("아이디 중복 확인 오류", error);
      })
}
// 인증코드 보내기
const sendCode = () => {
  axios.post('http://localhost:8000/user-service/user/send-code', userId)
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
// 코드 인증하기
const checkCode = () => {
  const codeCheckDTO = {
    userId: userId,
    code: code
  };
  axios.post('http://localhost:8000/user-service/user/check-code', codeCheckDTO)
      .then(res => {
        if (res.status === 200){
          codeCheck.value = true;
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
// 닉네임 중복 확인
const checkingNick = () => {
  axios.get(`http://localhost:8000/user-service/user/${userId}`)
      .then(res => {
        if(res.data.isUnique){
          nickCheck.value = true;
          alert('사용 가능한 닉네임입니다.');
        } else{
          alert('이미 존재하는 닉네임입니다.');
        }
      })
      .catch(error => {
        console.error("닉네임 중복 확인 오류", error);
      })
}
// 휴대폰 번호 중복확인
const checkingPhone = () => {
  axios.get(`http://localhost:8000/user-service/user/${phone}`)
      .then(res => {
        if(res.data.isUnique){
          phoneCheck.value = true;
          alert('사용 가능한 휴대폰 번호입니다.');
        } else{
          alert('이미 존재하는 휴대폰 번호입니다.');
        }
      })
      .catch(error => {
        console.error("휴대폰 중복 확인 오류", error);
      })
}


const isFormValid = computed(() => {
  return idCheck.value &&
      nickCheck.value &&
      phoneCheck.value &&
      codeCheck.value &&
      isIdValid.value &&
      pwValid.value &&
      pwCheckValid.value &&
      birthValid.value &&
      genderValid.value;
});


// 회원가입 버튼
const register = () => {
  if (idCheck.value && codeCheck.value && nickCheck.value && phoneCheck.value
      && isIdValid.value && pwValid.value && pwCheckValid.value && birthValid.value && genderValid.value){

    const userDTO = {
      userId: userId,
      userPw: password.value,
      userName: name.value,
      userNick: nick.value,
      userBirth: birthDay.value,
      userPhone: phone.value
    }
    axios.post('http://localhost:8000/user-service/user/register', userDTO)
        .then(res => {
            if (res.status === 200){
              alert('회원가입 성공!');
            } else {
              alert('회원가입 실패');
            }
        })
        .catch(error => {
          console.error('회원가입 오류', error);
          alert('회원가입 실패');
        })
  }
}
</script>

<template>
  <div class="register_all">
    <NavigationBar/>

    <div class="register_content">

      <div class="form_section">
        <div class="register_title">
          <span>회원 정보 입력</span>
        </div>

        <!-- 폼 내부중 상단 (아이디, 인증코드, 이름) -->
        <div class="form_top">

          <!-- 아이디 div -->
          <div id="divId">
            <div class="form_item id"
                 :class="{ 'error-border': lastTopErrorField === 'id'
                 || lastTopErrorField === 'email'
                 || idValid.value === false
                 || emailValid.value === false }"
            >
              <div class="email_icon">
                <img src="https://img.icons8.com/?size=100&id=53388&format=png&color=000000" width="30px" height="30px">
              </div>
              <div class="id_inputs">
                <input type="text" id="id" v-model="id" @blur="validateId" placeholder="아이디 입력" :disabled="idCheck.value">
                <input type="email"
                       id="email"
                       v-model="email"
                       :placeholder="selectedEmail === '직접입력' ? '이메일 직접 입력' : selectedEmail"
                       :disabled="selectedEmail!=='직접입력' || idCheck.value"
                       @blur="validateEmail"
                >
                <select id="select_email" v-model="selectedEmail">
                  <option value="직접입력" selected>직접입력</option>
                  <option value="@gmail.com">gmail.com</option>
                  <option value="@naver.com">naver.com</option>
                  <option value="@nate.com">nate.com</option>
                  <option value="@hanmail.net">hanmail.net</option>
                </select>
              </div>
              <button class="send_code" @click="checkIdAndSendCode">{{ isIdValid ? '인증코드 보내기' : '아이디 중복 확인' }}</button>
            </div>
          </div>

            <!-- 인증 코드-->
            <div class="form_item code">
              <div class="code_icon">
                <img src="https://img.icons8.com/?size=100&id=124383&format=png&color=000000" width="30px" height="30px">
              </div>
              <div class="code_check codeAndNick">
                <input type="text" id="code" class="code_input" placeholder="인증코드 입력" v-model="code">
              </div>
              <div class="code_check_btn code-nick_btn">
                <button @click="checkCode">인증하기</button>
              </div>
            </div>

            <!-- 이름 -->
            <div class="form_item name" :class="{ 'error-border': nameValid.value === false || lastTopErrorField === 'name' }">
              <div class="user_icon">
                <img src="https://img.icons8.com/?size=100&id=AZazdsitsrgg&format=png&color=000000" width="30px" height="30px">
              </div>
              <div class="name_box name_pw">
                <input type="text" id="name" placeholder="이름" v-model="name" @blur="validateName">
              </div>
            </div>
            <div class="error_box" v-if="errorTopMessage">
              <span>{{ errorTopMessage }}</span>
            </div>
          </div>

          <!-- 폼 내부 중 중단 (닉네임, 비밀번호, 비밀번호 재확인) -->
          <div class="form-middle">

            <!-- 닉네임 -->
            <div class="form_item nick" :class="{ 'error-border': lastMiddleErrorField === 'nick' || nickValid.value === false }">
              <div class="user_icon">
                <img src="https://img.icons8.com/?size=100&id=AZazdsitsrgg&format=png&color=000000" width="30px" height="30px">
              </div>
              <div class="nick_input codeAndNick">
                <input type="text" id="nick" class="" placeholder="닉네임" v-model="nick" @blur="validateNick" :disabled="nickCheck.value">
              </div>
              <div class="dupDiv nick_dup code-nick_btn">
                <button @click="checkingNick">중복확인</button>
              </div>
            </div>

            <!-- 비밀번호 -->
            <div class="form_item password" :class="{ 'error-border' : lastMiddleErrorField === 'password' || pwValid.value === false}">
              <div class="pw_icon_box pw_icon">
                <img src="https://img.icons8.com/?size=100&id=94&format=png&color=000000" width="30px" height="30px">
              </div>
              <div class="name_pw">
                <input type="password" class="password" placeholder="비밀번호" v-model="password" @blur="validatePassword">
              </div>
            </div>

            <!-- 비밀번호 재확인 -->
            <div class="form_item password_check" :class=" { 'error-border' : lastMiddleErrorField === 'passwordCheck' || pwCheckValid.value === false }">
              <div class="pw_icon pw_check_icon_box">
                <img src="https://img.icons8.com/?size=100&id=94&format=png&color=000000" width="30px" height="30px">
              </div>
              <div class="name_pw">
                <input type="password" class="password_check" placeholder="비밀번호 재확인" v-model="passwordCheck" @blur="validatePasswordCheck">
              </div>
            </div>
            <div class="error_box" v-if="errorMiddleMessage">
              <span>{{ errorMiddleMessage }}</span>
            </div>
          </div>

        <!-- 폼 내부 중 하단 (휴대폰 번호, 생년월일, 성별) -->
        <div class="form-bottom">

          <!-- 핸드폰 번호 -->
          <div class="form_item phone" :class="{'error-border' : lastBottomErrorField === 'phone' || phoneValid.value === false}">
            <div class="phone_icon">
              <img src="https://img.icons8.com/?size=100&id=79&format=png&color=000000" width="30px" height="30px">
            </div>
            <div class="input_divs">
              <input type="text" class="first-phone input_phone" placeholder="휴대폰" maxlength="3" v-model="phone1" @blur="validatePhone" :disabled="phoneCheck.value">
              <span>-</span>
              <input type="text" class="second-phone input_phone" placeholder="번호" maxlength="4" v-model="phone2" @blur="validatePhone" :disabled="phoneCheck.value">
              <span>-</span>
              <input type="text" class="third-phone input_phone" placeholder="입력" maxlength="4" v-model="phone3" @blur="validatePhone" :disabled="phoneCheck.value">
            </div>
            <div class="dupDiv phone_dup">
              <button @click="checkingPhone">중복확인</button>
            </div>
          </div>

          <!-- 생년월일 성별 -->
          <div class="form_item birthday"
               :class="{ 'error-border' :
                     lastBottomErrorField === 'selectedGender' ||
                     lastBottomErrorField === 'birthDay' ||
                     genderValid.value === false ||
                     birthValid.value === false }">
            <div class="birth_icon">
              <img src="https://img.icons8.com/?size=100&id=60611&format=png&color=000000" width="30px" height="30px">
            </div>
            <div class="birth_input_box">
              <input type="text" class="birthday_input" placeholder="생년월일 8자리" maxlength="8" @blur="validateBirth">
            </div>

            <div class="gender_div">
              <div class="gender_title">
                <span>성별</span>
              </div>
              <div class="radio_item" :class="{ selected: selectedGender === 'M' }" @click="selectGender('M')">
                <input type="radio" id="gender1" name="identityGender" value="M" v-model="selectedGender" @blur="validateGender">
                <label for="gender1">남자</label>
              </div>
              <div class="radio_item" :class="{ selected: selectedGender === 'F' }" @click="selectGender('F')">
                <input type="radio" id="gender2" name="identityGender" value="F" v-model="selectedGender" @blur="validateGender">
                <label for="gender2">여자</label>
              </div>
            </div>
          </div>

          <div class="error_box" v-if="errorBottomMessage">
            <span>{{ errorBottomMessage }}</span>
          </div>
        </div>

          <div class="register_btn">
            <button :disabled="!isFormValid">가입하기</button>
          </div>

      </div>

    </div>
  </div>
</template>

<style scoped>
  .register_all{
    width: 100%;

  }
  .register_content{
    display: flex;
    justify-content: center;
  }
  .form_section{
    margin-top: 45px;
    width: 650px;
    text-align: center;
  }
  button{
    cursor: pointer;
  }
  .form_section .register_title span{
    font-size: 25px;
  }
  #divId{
    margin-top: 50px;
  }
  .form_item{
    display: flex;
    width: 650px;
    height: 60px;
    border: 1.5px solid #617CC2;
    align-items: center;
    border-radius: 10px;
    margin-top: 5px;
  }
  .email_icon{
    text-align: left;
    width: 20px;
  }
  .id_inputs{
    width: 400px;
  }
  #id{
    width: 130px;
    height: 35px;
    border: none;
    border-radius: 5px;
  }
  #email{
    width: 130px;
    height: 35px;
    border: none;
    border-radius: 5px;
  }
  #select_email{
    width: 120px;
    height: 35px;
    border-color: #617CC2;
    border-radius: 10px;
    margin-left: 5px;
  }
  #divId .id{
    justify-content: space-around;
  }
  #divId .send_code{
    color: black;
    background-color: #A4BCF9;
    border: solid 1px grey;
    height: 35px;
    border-radius: 5px;
  }
  .code, .nick{
    justify-content: space-around;

  }

  .codeAndNick{
    width: 400px;
    text-align: left;
  }
  .codeAndNick input{
    height: 35px;
    width: 400px;
    border: none;
    border-radius: 5px;
  }
  .code-nick_btn{
    width: 110px;
  }
  .code-nick_btn button, .phone_dup button{
    color: black;
    background-color: #A4BCF9;
    border: solid 1px grey;
    height: 35px;
    border-radius: 5px;
  }


  .name{

  }
  .name .user_icon, .pw_icon, .birth_icon{
    margin-left: 17px;
  }
  .name_box, .name_pw, .birth_input_box{
    width: 400px;
  }
  .name_box input, .name_pw input, .birth_input_box input{
    width: 320px;
    height: 35px;
    border: none;
    border-radius: 5px;
  }

  .nick .user_icon{
    margin-right: 6px;
  }
  .nick_input{
    margin-right: 8px;
  }

  .phone{
    justify-content: space-around;
  }
  .input_divs{
    width: 400px;
  }
  .input_phone{
    width: 100px;
    height: 35px;
    border: none;
    border-radius: 5px;
    text-align: center;
  }
  .phone_dup{
    width: 110px;
  }

  .birth_input_box{
    width: 350px;
  }
  .birth_input_box input{
    width: 270px;
  }
  .gender_div{
    align-items: center;
    display: flex;
    width: 230px;
    justify-content: space-around;
  }
  .gender_title{
    margin-right: 3px;
  }
  .radio_item input[type="radio"]{
    display: none;

  }
  .radio_item{
    margin-left: 5px;
    border: 2px solid #617CC2;
    width: 80px;
    height: 35px;
    line-height: 35px;
    cursor: pointer;
    border-radius: 10px;
  }
  .radio_item label{
    cursor: pointer;
  }
  .radio_item.selected {
    border-color: #3257B5; /* 선택된 경우 테두리 색상 */
    background-color: #A4BCF9; /* 선택된 경우 배경 색상 */
  }
  input[type="radio"]:checked + label::before {
    background-color: #A4BCF9; /* 선택 시 배경 색상 */
  }
  input, button{
    font-family: "Neo둥근모 Pro";
  }
  .register_btn{
    margin-top: 30px;
  }
  .register_btn button{
    font-size: 18px;
    width: 200px;
    height: 50px;
    background-color: #A4BCF9;
    border: none;
    border-radius: 10px;
  }

  .error-border {
    border: 2px solid red; /* 에러 시 테두리 색상 */
  }
  .error_box{
    text-align: left;
    color: red;
  }
  .register_btn button:disabled{
    background-color: #A7A7A7;
    cursor: default;
  }






</style>