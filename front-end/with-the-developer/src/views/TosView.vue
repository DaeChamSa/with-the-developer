<script setup>
import { ref } from 'vue';
import NavigationBar from "@/components/NavigationBar.vue";

const isChecked = ref(false);
</script>

<template>
  <div class="tos-all">
      <NavigationBar/>

    <div class="tos-content">
      <div class="tos-title">
        <span>약관동의</span>
      </div>
      <div class="tos-info">
        <span>약관동의 내용</span>
      </div>
      <div class="agree-box">
        <div class="check-box">
          <label>
            <span class="point-text">*(필수) &nbsp;</span>
            <input type="checkbox" class="agree-check" v-model="isChecked">
            <span class="agree-text">동의</span>
          </label>
        </div>
        <button class="next" :disabled="!isChecked">다음</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .tos-all {
    width: 1366px;
    height: 768px;

  }
  .tos-content{
    width: 750px;
    margin: 100px auto;
    text-align: center;
  }

  .tos-title span{
    font-size: 30px;
    color: #3257B5;
  }
  .tos-info{
    margin-top: 30px;
    height: 400px;
    border: 2px solid #617CC2;
    border-radius: 10px;
    text-align: left;
    overflow: scroll;
    padding-top: 15px;
  }
  .tos-info span{
    margin-left: 10px;
  }
  .check-box{
    text-align: right;
  }
  .check-box .point-text{
    color: #FF5353;
  }
  .check-box .agree-text{
    margin-right: 20px;
  }
  .next{
    font-size: 23px;
    color: white;
    background-color: #617CC2;
    border: none;
    width: 130px;
    height: 50px;
    border-radius: 10px;
    cursor: pointer;
  }
  /* 비활성화 상태 스타일 */
  .next:disabled {
    background-color: #D9D9D9;
    color: white;
    cursor: not-allowed;
  }


</style>