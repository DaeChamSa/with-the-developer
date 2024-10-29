<script setup>
import {computed, defineEmits, defineProps, onMounted, ref} from 'vue';
import axios from 'axios';
import router from "@/router/index.js";
import {useStore} from "vuex";

// props로 모달 상태 받기
const props = defineProps({
  showModal: Boolean
});

// 부모 컴포넌트로 이벤트 전달
const emit = defineEmits(['close']);

// 모달 닫기 함수
function toggleModal() {
  emit('close');
}

// 받은 알림들
const noties = ref([]);

// 무한 스크롤을 위한 상태 관리
const isLoading = ref(false);
const hasMore = ref(true);

// 알림들 불러오기 함수
function loadNotifications() {
  if (isLoading.value || !hasMore.value) return;

  isLoading.value = true;
  axios.get(`/noti/received`)
      .then((res) => {
        if (res.status === 200) {
          const newNoties = res.data;
          if (newNoties.length > 0) {
            noties.value = newNoties;
          } else {
            hasMore.value = false; // 더 이상 데이터가 없으면 로딩 중지
          }
        } else {
          console.log('알림 불러오기 실패');
        }
      })
      .catch((error) => {
        console.log('알림 불러오기 오류', error);
      })
      .finally(() => {
        isLoading.value = false;
      });
}

// 상태관리
const store = useStore();

// 로그인 유무
const isLogin = computed(() => store.getters.isLoggedIn);

// 페이지가 마운트될 때 첫 번째 알림 페이지 로드
onMounted(() => {
  if (isLogin.value)
    loadNotifications();
});

// 10분마다 알람 불러오기 함수 호출
setInterval(() => {
  if (isLogin.value)
    loadNotifications();
}, 1000 * 60 * 10);

// 스크롤 이벤트를 감지하여 무한 스크롤 처리
function handleScroll() {
  const bottomOfWindow =
      window.innerHeight + window.scrollY >= document.documentElement.scrollHeight;
  if (bottomOfWindow) {
    loadNotifications();
  }
}

window.addEventListener('scroll', handleScroll);

// 알림 읽음 처리
function markAsRead(notiCode) {
  axios.put(`/noti/${notiCode}`)
      .then((res) => {
        const noti = noties.value.find(n => n.notiCode === notiCode);
        if (noti) {
          noti.notiRead = true; // 읽음 처리
        }
      })
      .catch((error) => {
        console.log('읽음 처리 오류', error);
      });
}

// 알림 삭제 처리
function deleteNotification(notiCode) {
  axios.delete(`/noti/${notiCode}`)
      .then((res) => {
        noties.value = noties.value.filter(n => n.notiCode !== notiCode); // 삭제 후 목록에서 제거
      })
      .catch((error) => {
        console.log('삭제 처리 오류', error);
      });
}

const notiAllDTO = ref([]);

// 알림 전체 읽음 처리
function readAllNoti(){
  noties.value.filter(n => !n.notiRead)
      .forEach(n => {
        notiAllDTO.value.push(n.notiCode);
      });

  axios.post('/noti/all', notiAllDTO.value)
      .then(res => {
        if (res.status){
          noties.value.forEach(noti => {
            if (!noti.notiRead){
              noti.notiRead = true;
            }
          });
          alert('알림 전체 읽음');
        }else {
          alert('전체 읽음 처리 실패');
        }
      })
      .catch(error => {
        console.log('전체 읽음 처리 오류', error);
      })

  notiAllDTO.value = [];
}

// 알림 전체 삭제 처리
function deleteAllNoti(){
  noties.value.forEach(n => {
        notiAllDTO.value.push(n.notiCode);
      });

  axios.delete('/noti/all', {data: notiAllDTO.value})
      .then(res => {
        if (res.status === 200){
          noties.value = [];
          alert('알림 전체 삭제');
        } else {
          alert('알림 전체 삭제 실패');
        }
      })
      .catch(error => {
        console.log("알림 전체 삭제 오류", error);
      })

  notiAllDTO.value = [];
}

// 해당 페이지로 이동
function move(notiUrl, notiCode) {
  markAsRead(notiCode);
  router.push(`${notiUrl}`);
}

// 날짜 형식 변환 함수
function formatDate(dateString) {
  const date = new Date(dateString);

  const year = date.getFullYear(); // 년도
  const month = String(date.getMonth() + 1).padStart(2, '0'); // 월 (0부터 시작하므로 +1)
  const day = String(date.getDate()).padStart(2, '0'); // 일
  const hours = String(date.getHours()).padStart(2, '0'); // 시
  const minutes = String(date.getMinutes()).padStart(2, '0'); // 분

  return ` ${month}월 ${day}일 ${hours}:${minutes}`; // 원하는 형식으로 반환
}
</script>

<template>
  <!-- 모달 -->
  <div v-if="props.showModal" class="modal">
    <div class="modal-content">
      <div class="modal-header">
        <button @click="readAllNoti">전체 읽음</button>
        <button @click="deleteAllNoti">전체 삭제</button>
        <button @click="toggleModal">닫기</button>
      </div>
      <div class="modal-body">
        <!-- 알림 리스트 -->
        <div
            v-for="(noti, index) in noties"
            :key="noti.notiCode"
            class="notification"
            :class="{ unread: !noti.notiRead }"
        >
          <p><a @click="move(noti.notiUrl, noti.notiCde)">{{ noti.notiTitle }}</a></p>
          <div>
            <div class="margin-right">
              <button @click="markAsRead(noti.notiCode)" :disabled="noti.notiRead">
                {{ noti.notiRead ? '읽음' : '읽기' }}
              </button>
              <button @click="deleteNotification(noti.notiCode)">삭제</button>
            </div>
            <div class="center">
              <span>{{ formatDate(noti.notiCreateDate) }}</span>
            </div>
          </div>
        </div>
        <div v-if="isLoading" class="loading">로딩 중...</div>
        <div v-if="!hasMore" class="end">알림이 없습니다.</div>
      </div>
      <div class="modal-footer">
        <button @click="toggleModal">닫기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
*{
  font-family: "Neo둥근모 Pro";
  font-size: 13px;
}
.modal {
  position: absolute;
  top: 60px;
  right: 20px;
  width: 420px;
  background-color: #F2F6FF;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  z-index: 1000;
}
.center{
  text-align: center;
}
p{
  align-content: center;
  margin-left: 10px;
}
.modal-content {
  padding: 20px;
  overflow: scroll;
  max-height: 400px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}
.modal-header span{
  background-color: #4a90e2;
}
.modal-body {
  display: flex;
  flex-direction: column;

}
.modal-body button{
  margin: 10px 3px 5px;
}

.notification {
  display: flex;
  justify-content: space-between;
  padding: 10px 10px;
  border-bottom: 1px solid #ddd;
  background-color: #FFFFFF;
  border-radius: 15px;
  margin-bottom: 10px;
  box-shadow: 0 1px 3px;
}
.unread {
  background-color: rgba(132, 255, 83, 0.56); /* 읽지 않은 알림 배경색 */
}
.unread a {
  text-underline-position : under;
  text-decoration: underline;
}
a:hover{
  cursor: pointer;
}
.modal-footer {
  display: flex;
  justify-content: center;
  padding-top: 10px;
}

button {
  padding: 5px 10px;
  background-color: #668ef7;
  color: white;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.loading {
  text-align: center;
  margin-top: 10px;
  color: #666;
}

.end {
  text-align: center;
  margin-top: 10px;
  color: #999;
}
</style>
