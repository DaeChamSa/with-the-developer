<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goToCart = () => {
  router.push('/cart-goods');
}
</script>

<template>
  <div id="pay_fail_box">
    <div id="pay_fail_msg">결제에 실패했습니다.</div>
    <button @click="goToCart">장바구니로 돌아가기</button>
  </div>
</template>

<style scoped>
* {
  font-family: "Neo둥근모 Pro";
  margin: 0 auto;
  box-sizing: border-box;
}

#pay_fail_box {
  width: 320px;
  height: 320px;
}

#pay_fail_msg {
  font-size: 20px;
  text-align: center;
  margin-top: 120px;
  margin-bottom: 40px;
}

button {
  width: 320px;
  height: 64px;
  border-radius: 24px;
  background-color: #94B1F5;
  border: transparent;
  color: white;
  font-size: 18px;
  cursor: pointer;
}
</style>