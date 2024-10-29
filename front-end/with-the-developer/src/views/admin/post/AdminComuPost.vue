<script setup>
import {onMounted, ref} from "vue";
import router from "@/router/index.js";
import axios from "axios";
import AdminSideBar from "@/components/AdminSideBar.vue";
import AdminPostMenu from "@/components/AdminPostMenu.vue";

const postList = ref([]);
const fetchPostList = async () => {
  try{
    return (await axios.get('/public/comu/post')).data;
  }catch (error){
    alert('회원정보 조회 중 에러 발생!' + error);
  }
}
const confirmDelete = (comuCode) => {
  // 읽음 확인 alert
  const confirmed = window.confirm('게시글을 삭제 하시겠습니까?');

  if (confirmed) {
    deletePost(comuCode); // 확인 시 삭제 메서드 호출
  }
}
// 게시글 삭제
const deletePost = async (comuCode) => {
  try {
    await axios.delete(`comu/post/${comuCode}`);

    const filtering = await fetchPostList();
    postList.value = filtering.filter(post => post.reportCount >= 3);

    alert('삭제되었습니다.');

  } catch (error) {
    console.error('삭제 도중 오류 발생:', error);
    alert('삭제에 실패했습니다.');
  }
};
onMounted(async () => {
  const filtering = await fetchPostList();
  postList.value = filtering.filter(post => post.reportCount >= 3);
});

const moveTo = (url) => {
  router.push(`/community/${url}`);
}
</script>

<template>
  <section>
    <AdminSideBar/>
    <div id="content">
      <AdminPostMenu/>
      <article id = "info">
        <div class = "post" v-for="post in postList">
          <div class="post_left" @click="moveTo(post.comuCode)">
            <p class="post_title">{{post.comuSubject}}</p>
            <p class="post_content">{{post.comuContent}}</p>
          </div>
          <div class="post_right">
            <p class="user_nick">{{post.userNick}}</p>
            <p class="create_date">{{post.createdDate}}</p>
          </div>
          <img class="bmk_icon" src="https://img.icons8.com/?size=100&id=TUqJsJ4ey9V0&format=png&color=000000" alt="북마크이미지">
          <p>{{post.reportCount}}</p>
          <button @click="confirmDelete(post.comuCode)">삭제</button>
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