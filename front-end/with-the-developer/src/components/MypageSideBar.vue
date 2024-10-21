<template>
  <div class="sidebar">
    <ul class="sidebar-menu">
      <li>
        <button @click="navigateTo('profile')">내 프로필</button>
      </li>
      <li>
        <button @click="navigateTo('bookmark')">북마크</button>
      </li>
      <li>
        <button @click="navigateTo('posts')">내 게시글</button>
      </li>
      <li>
        <button @click="navigateTo('messages')">쪽지</button>
      </li>
      <li>
        <button @click="navigateTo('orders')">주문내역</button>
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
  background-color: #f5f5f5;
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
  background-color: #e2e2e2;
  border-radius: 5px;
}
</style>
