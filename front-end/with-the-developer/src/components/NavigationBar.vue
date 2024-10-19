<script setup>

</script>

<template>
  <header>
    <div id="nav-left">
      <img src="../assets/images/logo.png" alt="로고 이미지" id="logo-image">
      <ul class="nav-ul">
        <li class="nav-menu"><a href="#">게시판</a></li>
        <li class="nav-menu"><a href="#">채용공고</a></li>
        <li class="nav-menu"><a href="#">굿즈</a></li>
        <li class="nav-menu"><a href="#">마이페이지</a></li>
      </ul>
    </div>
    <div id="nav-right">
      <ul class="nav-ul">
        <li class="nav-menu"><a href="#" id="login">로그인</a></li>
        <li class="nav-menu"><a href="#" id="login"><img src="https://img.icons8.com/?size=100&id=eMfeVHKyTnkc&format=png&color=000000" alt="alarm" class="nav-img"></a></li>
        <li class="nav-menu"><a href="#" id="login"><img src="https://img.icons8.com/?size=100&id=zhda2EVBCvHY&format=png&color=000000" alt="cart" class="nav-img"></a></li>
        <li class="nav-menu"><a href="#" id="login"><img src="https://img.icons8.com/?size=100&id=elSdeHsB03U3&format=png&color=000000" alt="search" class="nav-img"></a></li>
      </ul>
    </div>
  </header>

</template>

<style scoped>
header{
  font-family: "Neo둥근모 Pro";
  font-size: 20px;
  display: flex;
  width: 1336px;
  margin: 0 auto;
  justify-content: center;
}
#nav-left{
  display: flex;
  float: left;
}
#logo-image{
  width: 170px;
  height: 50px;
  margin-top: 5px;
  margin-left: 30px;
}
.nav-ul{
  list-style: none;
  display: flex;
  margin-top: 20px;
  white-space: nowrap;
}
.nav-menu{
  margin-right: 40px;
  height: auto;
}
.nav-menu > a {
  text-decoration-line: none;
  color: #7E7E7E;
}
#nav-right{
  display: flex;
  float: right;
}
#login{
  text-decoration-line: none;
  color: black;
}
.nav-img{
  width: 40px;
  height: 40px;
  position: relative;
  top: -10px;
}

</style>