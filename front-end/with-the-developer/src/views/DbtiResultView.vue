<script setup>
import { ref, computed } from 'vue';
import axios from 'axios';

const props = defineProps(['result']); // result prop 수신

// 통신을 통해 결과 배열을 가져옴
const fetchedResults = ref([]);
const currentPage = ref(1);
const itemsPerPage = 8; // 한 페이지에 표시할 아이템 수
const selectedItem = ref([]); // 선택한 항목

// 데이터 가져오기 (onMounted 사용 가능)
axios.get(`/dbti/result/${props.result}`)
    .then(res => {
      console.log(res.data);
      fetchedResults.value = res.data; // 통신을 통해 데이터 배열을 받음
    })
    .catch(error => {
      console.error('결과 데이터를 가져오는 중 오류 발생:', error);
    });

// 현재 페이지의 데이터를 계산
const paginatedResults = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return fetchedResults.value.slice(start, end);
});

// 전체 페이지 수 계산
const totalPages = computed(() => {
  return Math.ceil(fetchedResults.value.length / itemsPerPage);
});

// 이전 페이지로 이동
const goToPrevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

// 다음 페이지로 이동
const goToNextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

// 항목 선택 시 호출
const selectItem = (item) => {
  selectedItem.value = item;
  console.log(selectedItem.value)
};
</script>

<template>
  <div class="result-container">
    <h2>성향 테스트 결과</h2>
    <p>당신의 성향은 <strong>{{ result }}</strong>와 가깝습니다.</p>

    <div class="buttons-container">
      <div v-for="(item, index) in paginatedResults" :key="index">
        <button
            :class="{ selected: selectedItem === item }"
            @click="selectItem(item)">
          {{ item.dbtiValue }}
        </button>
      </div>
    </div>

    <!-- 페이지네이션 버튼 -->
    <div class="pagination">
      <button @click="goToPrevPage" :disabled="currentPage === 1">이전</button>
      <button @click="goToNextPage" :disabled="currentPage === totalPages">다음</button>
    </div>

    <!-- 선택시 완료 버튼 표시 -->
    <div v-if="selectedItem">
      <button class="complete-button">완료</button>
    </div>
  </div>
</template>

<style scoped>
.result-container {
  text-align: center;
}

.buttons-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 10px;
}

button {
  padding: 10px 20px;
  border: 1px solid #000;
  background-color: white;
  cursor: pointer;
}

button.selected {
  background-color: #007BFF;
  color: white;
}

.pagination {
  margin-top: 20px;
}

.complete-button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #28a745;
  color: white;
  border: none;
  cursor: pointer;
}
</style>
