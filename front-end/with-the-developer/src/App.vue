<script setup>

import Footer from "@/components/Footer.vue";
import TosView from "@/views/TosView.vue";
import SearchBar from "@/components/SearchBar.vue";
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
