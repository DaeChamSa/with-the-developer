<script setup>

import MypageSideBar from "@/components/MypageSideBar.vue";


</script>

<template>
  <div class="page-container">
    <MypageSideBar />
    <div class="content">
      <!-- Page-specific content goes here -->
    </div>
  </div>
</template>

<style scoped>
.page-container {
  display: flex;
}

.content {
  flex-grow: 1;
  padding: 20px;
}

</style>
