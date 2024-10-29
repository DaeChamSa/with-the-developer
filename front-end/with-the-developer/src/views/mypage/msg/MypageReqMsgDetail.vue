<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import MypageSideBar from "@/components/MypageSideBar.vue";
import router from "@/router"
import {useRoute} from "vue-router";
const msg = ref({});
const userRoute = useRoute();
const fetchMsgDetail = async (msgCode) => {
  return (await axios.get(`msg/req/${msgCode}`)).data;
}

const confirmBlockUser = (blockUserCode) => {

  const confirmed = window.confirm('유저를 차단하시겠습니까?');

  if (confirmed) {
    console.log(blockUserCode);
    blockUser(blockUserCode); // 확인 시 삭제 메서드 호출
  }
};
const blockUser = async (blockedCode) => {
  try{
    await axios.post(`blocks/block/${blockedCode}`,{});
  }catch (error){
    console.error('차단 중 오류 발생:', error);
    alert('차단에 실패했습니다.');
  }
};
onMounted(async () => {
      msg.value = await fetchMsgDetail(userRoute.params.msgCode);
    }
)
</script>

<template>
  <section>
    <MypageSideBar/>
    <div id="content">
      <h1>보낸 쪽지</h1>
      <article id = "info">
        <div id = "msg_header">
          <div id="detail">
            <p>받은사람</p><p class="nick">{{ msg.userNick }}</p>
            <p class="email">{{ msg.userId }}</p>
            <button id="block_button" @click="confirmBlockUser(msg.userCode)">차단</button>
            <p>{{ msg.createdDate }}</p>
          </div>
        </div>
        <hr>
        <p id="msg_content">{{ msg.msgContent }}</p>
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
  text-align: center;
}
#detail{
  display: grid;
  grid-template-columns: 160px 160px 160px;
  grid-template-rows: 50px 50px;
}
#block_button{
  width: 60px;
  height: 30px;
  margin-top: 10px;
  margin-left: 40px;
  border: 1px solid lightgray;
  border-radius: 15px;
  color: white;
  background-color: #FF6363;
}
.nick{
  color: blue;
}
.email{
  color: #6c757d;
}
*,
*::before,
*::after {
  box-sizing: border-box;
}
</style>