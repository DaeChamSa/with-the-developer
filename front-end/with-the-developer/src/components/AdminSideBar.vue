<template>
  <div class="sidebar">
    <ul class="sidebar-menu">
      <li>
        <button @click="navigateTo('team')">팀모집</button>
      </li>
      <li>
        <button @click="navigateTo('project')">프로젝트</button>
      </li>
      <li>
        <button @click="navigateTo('recruit')">채용공고</button>
      </li>
      <li>
        <button @click="navigateTo('community')">커뮤니티</button>
      </li>
      <li>
        <button @click="navigateTo('goods')">굿즈</button>
      </li>
      <li>
        <button @click="navigateTo('orders')">주문목록</button>
      </li>
      <li>
        <button @click="navigateTo('member')">회원관리</button>
      </li>
      <li>
        <button @click="navigateTo('jopTag')">직무태그</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  methods: {
    navigateTo(page) {
      this.$router.push({ name: page });
    }
  }
};
</script>

<style scoped>
.sidebar {
  width: 200px;
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 1px 1px 1px 1px gray;  /* inner white */


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
