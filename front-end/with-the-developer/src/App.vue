<script setup>

import Footer from "@/components/Footer.vue";
import NavigationBar from "@/components/NavigationBar.vue";
import DbtiTestView from "@/views/DbtiTestView.vue";
import AdminGoods from "@/views/AdminGoods.vue";
import SearchBar from "@/components/SearchBar.vue";
import AdminUser from "@/views/Admin-User.vue";
import AdminSideBar from "@/components/AdminSideBar.vue";

</script>

<template>
  <SearchBar/>
  <NavigationBar/>
  <main class="main">
    <RouterView/>
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
  footer{
    margin-top: 10vh;
  }
  .admin-layout{
    display: flex;
  }
  .main{
    flex-grow: 1;
    padding: 20px;
    background: white;
  }

</style>
