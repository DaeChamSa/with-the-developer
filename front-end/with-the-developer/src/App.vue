<script setup>


import Footer from "@/components/Footer.vue";
</script>

<template>
  <main class="main">

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
