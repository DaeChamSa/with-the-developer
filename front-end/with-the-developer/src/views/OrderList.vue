<script setup>
import axios from "axios";
import {onMounted, reactive} from "vue";
import MypageSideBar from "@/components/MypageSideBar.vue";

const orderGoodsList = reactive([]);
const orderGoodsDetail = reactive([]);

const fetchOrderList = async () => {
  try {
    const response = await axios.get('http://localhost:8080/order');
    const orderList = response.data;

    orderList.forEach(goods => {
      orderGoodsList.push({
        orderCode: goods.orderCode,
        orderGoods: goods.orderGoods
      })
    })

    orderList.forEach(goods => {
      orderGoodsDetail.push({
        orderCode: goods.orderCode,
        orderDate: goods.orderDate,
        orderStatus: goods.orderStatus,
        orderPrice: goods.responsePaymentDTO.paymentPrice,
        goodsName: goods.orderGoods[0].goodsName,
        goodsCount: goods.orderGoods.length - 1
      })
    })
    console.log(orderList);
    console.log(orderGoodsList[0].orderGoods[0].orderGoodsCode);
    console.log(orderGoodsDetail);
  } catch (error) {
    console.log("주문 목록을 불러오던 도중 오류 발생:", error);
  }
}

// 가격 10000 -> 10,000으로 formatting
const formatPrice = (price) => {
  if (price === undefined || price === null) {
    return '가격 정보가 없습니다';
  }
  return price.toLocaleString();
}

// 주문 상태에 따른 한글값 변환
const getOrderStatus = (status) => {
  switch(status) {
    case 'READY':
      return '결제실패';
    case 'OK':
      return '결제완료';
    case 'CANCEL':
      return '결제취소';
  }
}

onMounted((async () => {
  await fetchOrderList();
}))
</script>

<template>
  <div class="flex">
    <MypageSideBar/>
    <div id="order_list_box">
      <div id="order_list_title">주문내역</div>
      <div id="content_box">
        <div v-for="order in orderGoodsDetail" :key="order.orderCode" class="order_goods_box">
          <router-link :to="`/mypage/orders/${order.orderCode}`">
            <div class="order_status">{{ getOrderStatus(order.orderStatus)}}</div>
            <div class="flex order_info_box">
              <img src="../assets/images/img.png">
              <div class="order_text_info">
                <div>{{ order.orderDate.replace('T', ' ') }} 결제</div>
                <div>{{ order.goodsName }} 외 {{ order.goodsCount }}건</div>
                <div>{{ formatPrice(order.orderPrice) }}원</div>
              </div>
            </div>
          </router-link>
          <hr>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  font-family: "Neo둥근모 Pro";
  box-sizing: border-box;
}

.flex {
  display: flex;
}

#order_list_box {
  width: 700px;
  margin-left: 100px;
  margin-top: 55px;
}
#order_list_title {
  font-size: 23px;
}

#content_box {
  width: 700px;
  margin-top: 20px;
  box-shadow: 1px 1px 1px 1px lightgray;
  padding: 30px;
  border: lightgray 1px solid;
  border-radius: 10px;
}

a {
  text-decoration: none;
  color: inherit;
  cursor: pointer;
}

.order_goods_box {
  padding-top: 10px;
}

.order_status {
  font-size: 18px;
}

.order_info_box {
  padding: 10px 0 12px 0;
}

img {
  width: 120px;
  height: 120px;
  border-radius: 15px;
}

.order_text_info {
  height: 120px;
  padding: 24px 0 24px 14px;
}

.order_text_info > div {
  margin: 7px;
}

.order_text_info > div:first-child {
  font-size: 15px;
  color: #7A7A7A;
}
</style>