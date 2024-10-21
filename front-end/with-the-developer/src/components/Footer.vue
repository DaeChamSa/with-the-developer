<script setup>

</script>

<template>
  <footer class="footer">
    <div class="footer-content">
      <span>TEAM<br>대참사</span>
      <span>TEAM MEMBER<br>권형미 김영기 안세령 임채륜 정의진</span>
      <span>ADDRESS<br>서울시 동작구 보라매로 87 3층</span>
      <span>CONTACT<br>ehs2523@naver.com</span>
      <span>GITHUB<br><a href="https://github.com/DaeChamSa/with-the-developer">https://github.com/DaeChamSa/with-the-developer</a></span>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  background-color: #EEF3FF;
  color: #A7A7A7;
  padding: 20px;
  text-align: center;
}

.footer-content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  font-family: "Neo둥근모 Pro", serif;
}

span {
  margin-bottom: 15px;
}
</style>