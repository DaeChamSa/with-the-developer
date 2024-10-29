<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";

  const props= defineProps({
    user : Object
  });

  // 닉네임
  const nick = ref('');
  const nickValid = ref(true);

  // 휴대폰 번호
  const phone1 = ref('');
  const phone2 = ref('');
  const phone3 = ref('');

  const phone = ref('');
  const phoneValid = ref(true);

  // 생년월일
  const birthDay = ref('');
  const birthValid = ref(true);

  // 유효성 검사 틀 및 메세지
  const lastMiddleErrorField = ref('');
  const lastBottomErrorField = ref('');
  const errorMiddleMessage = ref('');
  const errorBottomMessage = ref('');

  const pattern = /^[가-힣a-zA-Z0-9]+$/;
  const phonePattern = /^[0-9]+$/;

  // 닉네임 유효성 검사
  const validateNick = () => {
    if (!nick.value) {
      lastMiddleErrorField.value = 'nick';
      errorMiddleMessage.value = '* 닉네임을 입력하세요.';
      nickValid.value = false;
    } else if (!pattern.test(nick.value)){
      lastMiddleErrorField.value = 'nick';
      errorMiddleMessage.value = '* 한글, 영문, 숫자만 사용할 수 있습니다.';
      nickValid.value = false;
    } else {
      lastMiddleErrorField.value = '';
      errorMiddleMessage.value = '';
      nickValid.value = true;
    }
  };

  // 핸드폰번호 유효성 검사
  const validatePhone = () => {
    phone.value = phone1.value + phone2.value + phone3.value;
    if (!phone.value || phone.value.length < 11) {
      lastBottomErrorField.value = 'phone';
      errorBottomMessage.value = '* 핸드폰 번호를 입력하세요.';
      phoneValid.value = false;
    } else if (!phonePattern.test(phone.value)){
      lastBottomErrorField.value = 'phone';
      errorBottomMessage.value = '* 핸드폰 번호는 숫자만 입력할 수 있습니다.';
      phoneValid.value = false;
    } else {
      phone.value = phone1.value + '-' + phone2.value + '-' + phone3.value;
      lastBottomErrorField.value = '';
      errorBottomMessage.value = '';
      phoneValid.value = true;
    }
  };
  // 생년월일 유효성 검사 및 형식 변환
  const validateBirth = () => {
    // 생년월일 입력값
    const birthValue = birthDay.value;
    // 입력값이 비어있거나 8자리가 아닐 경우
    if (!birthValue || birthValue.length !== 8) {
      lastBottomErrorField.value = 'birthDay';
      errorBottomMessage.value = '* 생년월일은 8자리로 입력해야 합니다.';
      birthValid.value = false;
      return;
    }

    // 정규식으로 날짜 형식 확인 (YYYYMMDD)
    const datePattern = /^(19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])$/;
    if (!datePattern.test(birthValue)) {
      lastBottomErrorField.value = 'birthDay';
      errorBottomMessage.value = '* 유효한 날짜를 입력하세요. <br><br>(예: 19900101)';
      birthValid.value = false;
      return;
    }

    // YYYYMMDD를 YYYY-MM-DD로 변환
    const year = birthValue.slice(0, 4);
    const month = birthValue.slice(4, 6);
    const day = birthValue.slice(6, 8);
    const formattedBirthdate = `${year}-${month}-${day}`;

    // 변환된 날짜로 변수 업데이트
    birthDay.value = formattedBirthdate;

    // 오류 메시지 및 상태 초기화
    lastBottomErrorField.value = '';
    errorBottomMessage.value = '';
    birthValid.value = true;
  };

  // 닉네임 중복 확인
  const checkingNick = () => {
    axios.get(`/user/check-nick/${nick.value}`)
        .then(res => {
          if(res.data){
            nickCheck.value = true;
            alert('사용 가능한 닉네임입니다.');
          } else{
            alert('이미 존재하는 닉네임입니다.');
          }
        })
        .catch(error => {
          console.error("닉네임 중복 확인 오류", error);
          alert('이미 존재하는 닉네임입니다.');
        })
  }
  // 휴대폰 번호 중복확인
  const checkingPhone = () => {
    axios.get(`/user/check-phone/${phone.value}`)
        .then(res => {
          if(res.data){
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

  // 닉네임 중복 검증 변수
  const nickCheck = ref(false);
  // 휴대폰 번호 중복 검증 변수
  const phoneCheck = ref(false);

  const modifyUser = async () => {
    try {
      const userDTO = {
        userPw: "password@@123", // 임시데이터
        userName: props.user.userName,
        userNick: nick.value,
        userBirth: birthDay.value,
        userPhone: phone.value
      }
      await axios.put('user', userDTO);

      alert("수정 성공!");
      cancelModifyModal();
    } catch (error){
      alert("수정실패")
    }

  }
const emit = defineEmits();
const cancelModifyModal = () => {
  emit('cancelModal',false);
};
</script>

<template>
  <div id="modify_user">
    <div id="modify_area_top">
      <!-- 닉네임 -->
      <div id="form_item_nick" :class="{ 'error-border': lastMiddleErrorField === 'nick' || nickValid.value === false }">
        <div class="user_icon">
          <img src="https://img.icons8.com/?size=100&id=AZazdsitsrgg&format=png&color=000000" width="30px" height="30px">
        </div>
        <div class="nick_input codeAndNick">
          <input type="text" id="nick" class="input_nick" :placeholder=props.user.userNick v-model="nick" @blur="validateNick" :disabled="nickCheck">
        </div>
        <div>
          <button class="dupDiv_nick_btn" @click="checkingNick">중복확인</button>
        </div>
      </div>
    </div>
    <div id="modify_area_bottom">
      <!-- 핸드폰 번호 -->
      <div id="form_item_phone" :class="{'error-border' : lastBottomErrorField === 'phone' || phoneValid.value === false}">
        <div class="phone_icon">
          <img src="https://img.icons8.com/?size=100&id=79&format=png&color=000000" width="30px" height="30px">
        </div>
        <div class="input_divs">
          <input type="text" class="input_phone" placeholder="휴대폰" maxlength="3" v-model="phone1" @blur="validatePhone" :disabled="phoneCheck.value">
          <span>-</span>
          <input type="text" class="input_phone" placeholder="번호" maxlength="4" v-model="phone2" @blur="validatePhone" :disabled="phoneCheck.value">
          <span>-</span>
          <input type="text" class="input_phone" placeholder="입력" maxlength="4" v-model="phone3" @blur="validatePhone" :disabled="phoneCheck.value">
        </div>
        <div>
          <button class="dupDiv_phone_btn" @click="checkingPhone">중복확인</button>
        </div>
      </div>
      <!-- 생년월일 성별 -->
      <div class="form_item_birthday"
           :class="{ 'error-border' :
                     lastBottomErrorField === 'selectedGender'
                     || lastBottomErrorField === 'birthDay'
                     || !genderValid
                     || !birthValid }">
        <div class="birth_icon">
          <img src="https://img.icons8.com/?size=100&id=60611&format=png&color=000000" width="30px" height="30px">
        </div>
        <div class="birth_input_box">
          <input type="text" class="birthday_input" :placeholder=props.user.userBirth maxlength="8" v-model="birthDay" @blur="validateBirth">
        </div>
      </div>
      <div class="sub_info_content">
        <img class="info-icon" src="https://img.icons8.com/?size=100&id=54481&format=png&color=000000" alt="알림허용여부">
        <p class="alarm_items">알림허용여부</p>
        <input role="switch" type="checkbox" :checked="true" class="alarm_items" />
      </div>
    </div>
    <button id="modify_user_button" @click="modifyUser">수정</button>
    <button id="modify_cancel_button" @click="cancelModifyModal">취소</button>
  </div>
</template>

<style scoped>
#modify_user{
  position: absolute;
  border: 1px solid lightgray;
  border-radius: 20px;
  width: 500px;
  height: 500px;
  left: 30%;
  top: 30%;
  background-color: #F2F6FF;
  justify-content: center;
}
#modify_area_top{
  height: 100px;
  width: 400px;
  margin: 20px;
  padding: 25px;
  background-color: white;
  border-radius: 15px;
}
#modify_area_bottom{
  height: 200px;
  width: 400px;
  margin: 20px;
  padding: 25px;
  background-color: white;
  border-radius: 15px;
}
#form_item_nick{
  display: flex;
  margin-top: 20px;
  margin-bottom: 20px;
}
#form_item_phone{
  display: flex;
  margin-top: 20px;
  margin-bottom: 20px;
}
.input_nick{
  height: 25px;
  width: 150px;
  margin-left: 30px;
  margin-right: 30px;
  border: 1px solid lightgray;
  border-radius: 10px;
}
.dupDiv_nick_btn{
  height: 25px;
  border: 1px solid lightgray;
  border-radius: 10px;
  font-family: "Neo둥근모 Pro";
}
.dupDiv_phone_btn{
  height: 25px;
  border: 1px solid lightgray;
  border-radius: 10px;
  font-family: "Neo둥근모 Pro";
  margin-left: 10px;
}
.input_phone{
  height: 25px;
  width: 50px;
  border: 1px solid lightgray;
  border-radius: 10px;
  margin: 0 10px 0 10px;
  font-family: "Neo둥근모 Pro";
}
.birthday_input{
  height: 25px;
  width: 150px;
  border: 1px solid lightgray;
  border-radius: 10px;
  margin: 0 10px 0 10px;
  font-family: "Neo둥근모 Pro";
}
.form_item_birthday{
  display: flex;
}
.sub_info_content{
  display: flex;
  margin-top: 20px;
  margin-bottom: 20px;
  font-size: 15px;
}

.info-icon{
  width: 35px;
  height: 35px;
}
.alarm_items{
  margin-left: 10px;
}
#modify_user_button{
  width: 60px;
  height: 30px;
  margin-left: 20px;
  border: 1px solid lightgray;
  border-radius: 10px;
  background-color: #014995;
  color: white;
}
#modify_cancel_button{
  width: 60px;
  height: 30px;
  margin-left: 20px;
  border: 1px solid lightgray;
  border-radius: 10px;
}
</style>