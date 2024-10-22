<script setup>

import NavigationBar from "@/components/NavigationBar.vue";
import MypageSideBar from "@/components/MypageSideBar.vue";
</script>

<template>
<NavigationBar/>
  <section>
    <MypageSideBar/>
    <div id="content">
      <h2>내 프로필</h2>
      <article id = "info">
        <button id="modify">수정하기</button>
        <div id="main_info">

        </div>
        <div id="sub_info">

        </div>
      </article>
    </div>
  </section>

</template>

<style scoped>
section{
  display: flex;
  height: 700px;
}
#content{
  margin: 35px 0 0 100px;
}
#info{
  width: 500px;
  height: 500px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 1px 1px 1px 1px lightgray;
  background-color: #F2F6FF;
}
#main_info{
  background-color: white;
  height: 150px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 1px 1px 1px 1px lightgray;
}
#modify {
  width: 100px;
  height: 35px;
  font-family: "Neo둥근모 Pro";
  border: none;
  border-radius: 10px;
  transition: none;
  font-size: 17px;
  color: white;
  background-color: #B2C7F4;
  margin-bottom: 10px;
  box-shadow: 1px 1px 1px 1px lightgray;
}
</style>