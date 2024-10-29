<template>
  <div class="board-page">
    <h1 id="board-title">팀모집</h1>
    <div class="upper-menu">
      <button class="post-button" @click="goToCreatePage">
        <img
            src="https://img.icons8.com/?size=100&id=11737&format=png&color=ffffff"
            alt="글쓰기"
            class="post-icon"
        />
        <span class="post-text">글쓰기</span>
      </button>
    </div>
    <TeamBoardList :posts="posts" />
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import axios from 'axios';
import TeamBoardList from "@/components/TeamBoardList.vue";

const posts = ref([]);
const router = useRouter();

const goToCreatePage = () => {
  router.push('/team/create');
};

const fetchPosts = async () => {
  try {
    const response = await axios.get('/public/team/post');
    console.log(response.data);
    posts.value = response.data.content || response.data;
  } catch (error) {
    console.error('게시글을 불러오는 중 오류 발생:', error);
  }
};

onMounted(fetchPosts);
</script>

<style scoped>
.board-page {
  padding: 20px;
  font-size: 1.2rem;
  width: 70%;
}

.upper-menu {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 15px;
  border-bottom: 1px solid #A7A7A7;
}

#board-title {
  text-align: center;
  font-weight: bold;
  font-size: 2rem;
}

.post-button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background-color: #617CC2;
  border: none;
  border-radius: 8px;
  color: #ffffff;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

.post-button:hover {
  background-color: #4E5EA3;
  transform: scale(1.05);
}

.post-button:active {
  transform: scale(0.98);
}

.post-icon {
  width: 24px;
  height: 24px;
}

.post-text {
  display: inline-block;
  vertical-align: middle;
}
</style>
