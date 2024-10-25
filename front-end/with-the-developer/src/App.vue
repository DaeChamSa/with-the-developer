<script setup>

import Footer from "@/components/Footer.vue";
import NavigationBar from "@/components/NavigationBar.vue";

</script>

<template>
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

</style>
