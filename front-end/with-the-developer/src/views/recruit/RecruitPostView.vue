<template>
  <div class="post-detail">
    <div class="post-header">
      <span class="board-category">채용 공고</span>
      <div class="action-buttons">
        <button @click="goToList">목록</button>
        <template v-if="isAdmin">
          <button @click="confirmDeletePost">삭제</button>
        </template>
      </div>
    </div>

    <hr/>

    <div class="post-title">
      <h1>{{ post.recruitTitle }}</h1>
    </div>

    <hr/>

    <div class="post-content">
      <p>{{ post.recruitContent }}</p>

      <div v-if="post.recruitUrl" class="url-button">
        <button @click="openProjectUrl">채용 공고 주소</button>
      </div>

      <div v-if="post.images && post.images.length" class="image-slider">
        <swiper
            :slides-per-view="1"
            :navigation="true"
            :pagination="{ clickable: true }"
        >
          <swiper-slide v-for="(image, index) in post.images" :key="index">
            <img :src="getImageUrl(image.fileName)" alt="게시글 이미지"/>
          </swiper-slide>
        </swiper>
      </div>
    </div>

    <hr/>

    <div class="post-footer">
      <div class="proj-tags">
        <span v-for="(tag, index) in post.jobTagNames" :key="index" class="tag">
          #{{ tag }}
        </span>
      </div>
      <span>[모집기간] {{ formatDate(post.recruitStart) }} ~ {{ formatDate(post.recruitEnd) }}</span>
      <div class="post-actions">
        <template v-if="!isAuthor && isLogin">
          <button class="bookmark-button" @click="toggleBookmark(post)">북마크</button>
          <span>{{ post.bookmarkCount }}</span>
        </template>
      </div>
    </div>

    <hr/>
  </div>
</template>

<script setup>
import axios from 'axios';
import {ref, onMounted, computed} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import {BASE_IMAGE_URL} from '@/config/image-base-url.js';
import {Swiper, SwiperSlide} from 'swiper/vue';
import 'swiper/swiper-bundle.css'; // Swiper 스타일

const route = useRoute();
const router = useRouter();
const recruitCode = route.params.id; // URL에서 게시글 ID 추출

const post = ref({});

const currentUserCode = Number(localStorage.getItem('userCode'));
const isAuthor = computed(() => post.value.userCode === currentUserCode);
const isAdmin = computed(() => localStorage.getItem('userRole') === 'ROLE_ADMIN');
const isLogin = computed(() => localStorage.getItem('accessToken') !== null);

// 이미지 URL 생성 함수
const getImageUrl = (fileName) => `${BASE_IMAGE_URL}/${fileName}`;

// 게시글 상세 조회
const fetchPost = async () => {
  try {
    const response = await axios.get(`/public/recruit/${recruitCode}`);
    post.value = response.data;
  } catch (error) {
    console.error('게시글 조회 실패:', error);
  }
};

const openProjectUrl = () => {
  if (post.value.recruitUrl) {
    window.open(post.value.recruitUrl, '_blank');
  }
};

// 게시글 삭제 확인
const confirmDeletePost = () => {
  const confirmed = confirm('정말로 이 게시글을 삭제하시겠습니까?');
  if (confirmed) {
    deletePost();
  }
};

// 게시글 삭제
const deletePost = async () => {
  try {
    await axios.delete(`/recruit/delete/${recruitCode}`);
    await router.push('/recruit'); // 삭제 후 목록 페이지로 이동
  } catch (error) {
    console.error('게시글 삭제 실패:', error);
  }
};

// 목록 페이지로 이동
const goToList = () => router.push('/recruit');

// 날짜 포맷팅 함수
const formatDate = (dateString) => {
  const options = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
  };
  return new Date(dateString).toLocaleString(undefined, options);
};

const toggleBookmark = async (post) => {
  const userCode = localStorage.getItem('userCode'); // 사용자 코드 가져오기

  const bookmarkData = {
    bmkUrl: `/recruit/${post.recruitCode}`,
    bmkTitle: post.recruitTitle,
    postType: 'recruit',
    postCode: post.recruitCode,
    userCode: userCode,
  };

  try {
    const response = await axios.post('/bookmark', bookmarkData);
    post.bookmarked = response.data.bookmarked; // 북마크 상태 업데이트
    post.bookmarkCount = response.data.bookmarkCount; // 북마크 개수 업데이트
  } catch (error) {
    console.error('북마크 처리 중 오류 발생:', error);
  }
};

// 컴포넌트가 마운트될 때 게시글과 댓글 조회
onMounted(() => {
  fetchPost();
});
</script>

<style scoped>
.post-detail {
  padding: 20px;
  width: 70%;
}

.post-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.action-buttons img {
  width: 30px;
  cursor: pointer;
  margin-left: 10px;
}

.post-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #617CC2;
}

.post-content {
  margin: 20px 0;
  color: #969696;
}

.url-button {
  margin-top: 10px;
}

.url-button button {
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.url-button button:hover {
  background-color: #45a049;
}

.image-slider img {
  height: 300px;
  width: auto;
  object-fit: cover;
}

.post-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.proj-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 5px;
}

.tag {
  background-color: #f1f1f1;
  padding: 5px 10px;
  border-radius: 15px;
  font-size: 14px;
  color: #333;
}

button {
  background-color: #617CC2;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
  cursor: pointer;
  margin-top: 10px;
}

button:hover {
  background-color: #506a9b;
}
</style>