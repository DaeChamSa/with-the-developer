<script setup>


import Footer from "@/components/Footer.vue";
import AdminGoods from "@/views/AdminGoods.vue";
import NavigationBar from "@/components/NavigationBar.vue";
import AdminSideBar from "@/components/AdminSideBar.vue";
import MypageSideBar from "@/components/MypageSideBar.vue";
</script>

<template>
  <main class="main">
    <TosView/>
    <SearchBar/>
    <router-view/> <!--테스트-->

  </main>

  <footer>
    <Footer/>
  </footer>
</template>

<style scoped>
  *{
    font-family: "Neo둥근모 Pro";
    margin: 0 auto;
  }

</style>
