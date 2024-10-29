<template>
  <div class="sidebar">
    <ul class="sidebar-menu">
      <li>
        <button @click="navigateTo('/admin/team')">팀모집</button>
      </li>
      <li>
        <button @click="navigateTo('/admin/project')">프로젝트</button>
      </li>
      <li>
        <button @click="navigateTo('/admin/recruit')">채용공고</button>
      </li>
      <li>
        <button @click="navigateTo('/admin/community')">커뮤니티</button>
      </li>
      <li>
        <button @click="navigateTo('/goods')">굿즈</button>
      </li>
      <li>
        <button @click="navigateTo('/admin/order')">주문목록</button>
      </li>
      <li>
        <button @click="navigateTo('/admin/user/status')">회원관리</button>
      </li>
      <li>
        <button @click="navigateTo('/admin/jobTag')">직무태그</button>
      </li>
    </ul>
  </div>
</template>

<script setup>

import router from "@/router/index.js";

const navigateTo = (type) => {
  router.push(type);
}
</script>

<style scoped>
.sidebar {
  width: 200px;
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 1px 1px 1px 1px gray;
  height: 100vh;
  position: sticky;
  margin-left: 30px;
  margin-right: 30px;

}

.sidebar-menu {
  list-style: none;
  padding: 0;
}

.sidebar-menu li {
  margin-bottom: 20px;
  border-bottom: 1px solid black;
}

button {
  width: 100%;
  background-color: transparent;
  border: none;
  font-size: 18px;
  text-align: center;
  cursor: pointer;
  padding: 10px;
  color: #000;
}

button:hover {
  font-weight: bold;
  color: #1b5ac2;
}

*{
  font-family: "Neo둥근모 Pro";
  margin: 0 auto;
}

</style>
