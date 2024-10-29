<script setup>
import {useRoute} from 'vue-router';
import axios from "axios";
import {onMounted, ref} from "vue";

const route = useRoute();
const searchResults = ref([]);
// 쿼리 파라미터에서 검색어 가져오기
const searchKeyword = route.query.query; // URL 쿼리에서 'query' 파라미터를 가져옴

const fetchSearchResults = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/public/search`, {
      params: {keyword: searchKeyword}
    });
    searchResults.value = response.data;
    console.log("검색 결과: ", searchResults.value)
  } catch (error) {
    console.log("검색 결과를 불러오던 중 오류 발생", error);
  }
}

// 주문 상태에 따른 한글값 변환
const getSearchSource = (source) => {
  switch(source) {
    case '채용공고':
      return 'recruit';
    case '커뮤니티':
      return 'community';
    case '프로젝트 자랑 게시판':
      return 'project';
    case '팀모집 게시판':
      return 'team'
  }
}

onMounted(() => {
  fetchSearchResults();
})
</script>

<template>
  <div>
    <h1>"{{ searchKeyword }}"에 대한 검색 결과</h1>
    <div class="board-list">
      <div v-if="searchResults.length === 0" class="no-posts-message">검색결과가 없습니다.</div>
      <div v-else class="post-list">
        <div v-for="(result, index) in searchResults" :key="index" class="post-item">
          <router-link :to="`/${ getSearchSource(result.source) }/${ result.code }`">
            <div class="post-header">
              <h3 class="post-title">{{ result.title }}</h3>
              <div class="post_header_right">
                <div>{{ result.createdDate.replace('T', ' ') }}</div>
                <div>{{ result.source }}</div>
              </div>
            </div>
            <div class="post-user-info">
              <div class="nickname">{{ result.content }}</div>
            </div>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
li {
  list-style-type: none;
}

.board-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.no-posts-message {
  text-align: center;
  font-size: 1.5rem;
  color: #666;
}

.post-item {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 15px;
  background-color: #ffffff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.post-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.post_header_right {
  display: flex;
  color: #7E7E7E;
}

.post_header_right > div:first-child {
  margin-right: 10px;
}

.post-title {
  font-size: 1.75rem;
  font-weight: bold;
  color: #617CC2;
  margin: 0;
}

.post-user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  color: black;
}

.nickname {
  font-weight: bold;
}

.bookmark-button:hover .bookmark-image {
  transform: scale(1.2);
}

a {
  text-decoration: none;
  color: inherit;
  cursor: pointer;
}
</style>