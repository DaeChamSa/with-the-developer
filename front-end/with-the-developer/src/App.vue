<script setup>

import Footer from "@/components/Footer.vue";
import AdminGoods from "@/views/AdminGoods.vue";
import NavigationBar from "@/components/NavigationBar.vue";
import AdminSideBar from "@/components/AdminSideBar.vue";
import MypageSideBar from "@/components/MypageSideBar.vue";
import MainPageAfter from "@/views/MainPageAfter.vue";
import MainPageBefore from "@/views/MainPageBefore.vue";
import RegisterView from "@/views/RegisterView.vue";
import SearchBar from "@/components/SearchBar.vue";

</script>

<template>
  <NavigationBar/>
  <main class="main">
    <RegisterView/>
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
