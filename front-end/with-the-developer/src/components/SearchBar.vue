<script setup>
import { ref } from "vue";
import { useRouter } from 'vue-router';

const searchQuery = ref(''); // 검색어 저장
const router = useRouter();

const search = () => {
  if(searchQuery.value.trim() === '') {
    alert('검색어를 입력해주세요.');
    return;
  } else if (searchQuery.value.trim().length < 2) {
    alert('검색어를 2자 이상 입력해주세요.');
    return;
  }
  router.push({ path: '/search', query: { query: searchQuery.value}});
}
</script>

<template>
  <div class="search_bar flex">
    <input v-model="searchQuery" type="text" placeholder="검색어를 입력해주세요." required @keydown.enter="search">
    <button @click="search" type="submit" class="search_btn">
      <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="30" height="30" viewBox="0,0,256,256"><g fill="#617cc2" fill-rule="nonzero" stroke="none" stroke-width="1" stroke-linecap="butt" stroke-linejoin="miter" stroke-miterlimit="10" stroke-dasharray="" stroke-dashoffset="0" font-family="none" font-weight="none" font-size="none" text-anchor="none" style="mix-blend-mode: normal"><g transform="scale(10.66667,10.66667)"><path d="M9,2c-3.85415,0 -7,3.14585 -7,7c0,3.85415 3.14585,7 7,7c1.748,0 3.34501,-0.65198 4.57422,-1.71875l0.42578,0.42578v1.29297l5.58594,5.58594c0.552,0.552 1.448,0.552 2,0c0.552,-0.552 0.552,-1.448 0,-2l-5.58594,-5.58594h-1.29297l-0.42578,-0.42578c1.06677,-1.22921 1.71875,-2.82622 1.71875,-4.57422c0,-3.85415 -3.14585,-7 -7,-7zM9,4c2.77327,0 5,2.22673 5,5c0,2.77327 -2.22673,5 -5,5c-2.77327,0 -5,-2.22673 -5,-5c0,-2.77327 2.22673,-5 5,-5z"></path></g></g></svg>
    </button>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
  font-family: "Neo둥근모 Pro";
}

.flex {
  display: flex;
  align-items: center;
  justify-content: center;
}

.search_bar {
  border-radius: 180px;
  border: 1px solid black;
  width: 680px;
  height: 50px;
  padding: 0 24px 0 24px;
}

input {
  width: 600px;
  height: 30px;
  border: none;
  font-size: 16px;
  padding-left: 10px;
}

input:focus {
  outline: none;
}

input::placeholder {
  color: #A7A7A7;
}

input:focus::placeholder {
  color: transparent;
}

button:hover {
  cursor: pointer;
}

.search_btn {
  border: none;
  background-color: transparent;
}
</style>