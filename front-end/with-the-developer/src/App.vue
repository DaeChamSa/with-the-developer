<script setup>

import Footer from "@/components/Footer.vue";
import TosView from "@/views/TosView.vue";
import MainPageAfter from "@/components/main/MainPageAfter.vue";
import NavigationBar from "@/components/NavigationBar.vue";
</script>

<template>
<!--  <main class="main">-->
<!--    <TosView/>-->
<!--  </main>-->
  <NavigationBar/>
  <MainPageAfter/>
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
