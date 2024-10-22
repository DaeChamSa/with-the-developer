<script setup>
import { useRoute } from 'vue-router';
const route = useRoute();
// 쿼리 파라미터에서 검색어 가져오기
const searchQuery = route.query.query; // URL 쿼리에서 'query' 파라미터를 가져옴
</script>

<template>
  <div>
    <h1>검색 결과</h1>
    <p>검색어: {{ searchQuery }}</p>
  </div>
</template>

<style scoped>

</style>