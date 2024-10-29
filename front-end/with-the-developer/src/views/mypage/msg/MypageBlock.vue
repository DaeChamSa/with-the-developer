<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import MypageSideBar from "@/components/MypageSideBar.vue";
import router from "@/router/index.js";
import MypageMsgMenu from "@/components/MypageMsgMenu.vue";


const blockList = ref([]);
const blockCode = ref();
const fetchBlockList = async () => {
  return (await axios.get('block')).data;
}

const confirmUnblockUSer = (userCode) => {
  const confirmed = window.confirm('차단 해제 하시겠습니까?');

  if (confirmed) {
    unblockUser(userCode);
  }
};

const unblockUser = async (blockedCode) => {
  console.log(blockedCode);
  try {
    await axios.post(`blocks/unblock/${blockedCode}`,{});
    blockList.value = await fetchBlockList();
    alert('차단 해제 되었습니다.');
  } catch (error) {
    console.error('차단 해제 중 오류 발생:', error);
    alert('차단 해제 처리에 실패했습니다.');
  }
};
onMounted(async () => {
      blockList.value = await fetchBlockList();
    }
)

</script>

<template>
  <section>
    <MypageSideBar/>
    <div id="content">
      <MypageMsgMenu/>
      <article id = "info">
        <hr>
        <div class="block" v-for="user in blockList">
          <p class="user_id">{{user.userId}}</p>
          <p class="user_nick">{{user.userNick}}</p>
          <button class="send_button" @click="confirmUnblockUSer(user.blockedCode)">차단해제</button>
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
}

.block{
  display: flex;
  padding-left: 10px;
  font-size: 14px;
  border-bottom: 1px solid lightgray;
}
.block > p{
  margin-left : 40px;
  margin-right: 10px;
}
.send_button{
  font-family: "Neo둥근모 Pro";
  width: 100px;
  height: 30px;
  margin-top: 5px;
  margin-left: 10px;
  border: 1px solid lightgray;
  border-radius: 15px;
}
.send_button:hover{
  border-color: #617CC2;
  color: #617CC2;
}
.user_id{
  width: 150px;
}
.user_nick{
  width: 150px;
}

*,
*::before,
*::after {
  box-sizing: border-box;
}
</style>