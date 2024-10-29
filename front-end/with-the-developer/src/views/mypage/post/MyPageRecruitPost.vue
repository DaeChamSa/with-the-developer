<script setup>
import MypageSideBar from "@/components/MypageSideBar.vue";
import {onMounted, ref} from "vue";
import router from "@/router/index.js";
import MypagePostMenu from "@/components/MypagePostMenu.vue";
import axios from "axios";

const postList = ref([]);
const fetchPostList = async () => {
  try{
    return (await axios.get('/public/recruit/mypage',{
      headers: {
        Authorization: `${localStorage.getItem('accessToken')}`
      }
    })).data;
  }catch (error){
    alert('회원정보 조회 중 에러 발생!' + error);
  }
}

onMounted(async () => {
  postList.value = await fetchPostList();
});

const moveTo = (url) => {
  router.push(`/recruit/${url}`);
}
</script>

<template>
  <section>
    <MypageSideBar/>
    <div id="content">
      <MypagePostMenu/>
      <article id = "info">
        <div class = "post" v-for="post in postList"  @click="moveTo(post.comuCode)">
          <div class="post_left">
            <p class="post_title">{{post.comuSubject}}</p>
            <p class="post_content">{{post.comuContent}}</p>
          </div>
          <div class="post_right">
            <p class="user_nick">{{post.userNick}}</p>
            <p class="create_date">{{post.createdDate}}</p>
          </div>
        </div>
      </article>
    </div>
  </section>
</template>

<style scoped>
section{
  display: flex;
  height: 850px;
}
#content{
  margin: 35px 0 0 100px;
}
#info{
  width: 600px;
  height: 800px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 1px 1px 1px 1px lightgray;
  background-color: white;
}
.post{
  height: 80px;
  border-bottom: 1px solid lightgray;
  display: flex;
  cursor: pointer;
}
.post_left{
  width: 300px;
}
.post_right{
  width: 150px;
}
.user_nick{
  color: #617CC2;
}
.post_title{
  color: #617CC2;
  font-size: 20px;
}
.bmk_icon{
  width: 20px;
  height: 20px;
  margin: 12px;
}
*,
*::before,
*::after {
  box-sizing: border-box;
}
</style>