<script setup>

import Footer from "@/components/Footer.vue";
</script>

<template>
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

</style>
