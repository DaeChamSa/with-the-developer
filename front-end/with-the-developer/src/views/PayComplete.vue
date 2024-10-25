<script setup>
import NavigationBar from "@/components/NavigationBar.vue";
import {onMounted, ref} from 'vue';

const orderNum = ref('');
//  주문번호 API 호출 함수
const fetchOrderNum = async() => {
  try {
    const response = await axios.get(`https://localhost:8080/order`);
    orderNum.value = orderCreateDate.replace(/\D/g, '');
  } catch (error) {
    console.error('주문 번호를 불러오는 중 에러가 발생했습니다.', error);
  }
};

onMounted(() => {
  fetchOrderNum();
})
</script>

<template>
  <div id="pay_complete_msg_box">
    <div>
      <div id="pay_complete_msg">결제가 완료되었습니다.</div>
      <hr>
      <div id="order_num_box">
        <span id="order_num_title">주문번호</span>
        <span id="order_num">{{ orderNum }}</span>
      </div>
    </div>
    <button>결제 상세 내역</button>
  </div>
</template>

<style scoped>
* {
  font-family: "Neo둥근모 Pro";
}

#pay_complete_msg_box {
  width: 340px;
  margin: 120px auto;
}

#pay_complete_msg {
  text-align: center;
  font-size: 20px;
  padding-bottom: 20px;
}

#order_num_box {
  padding: 16px 48px 52px 48px;
  font-size: 16px;
}

#order_num_title {
  float: left;
}

#order_num {
  float: right;
}

hr {
  width: 340px;
  border: 1px solid #D4D4D4;
}

button {
  width: 320px;
  height: 64px;
  border-radius: 24px;
  background-color: #94B1F5;
  border: transparent;
  color: white;
  font-size: 18px;
  margin: 0 10px;
}

button:hover {
  cursor: pointer;
}
</style>