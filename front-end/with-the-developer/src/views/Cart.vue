<script setup>
import NavigationBar from "@/components/NavigationBar.vue";
import {ref, onMounted, computed} from "vue";

const cartGoods = ref([]);

const fetchCart = async () => {
  try {
    const response = await axios.get('/cart-goods');
    cartGoods.value = response.data.items;
  } catch(error) {
    console.log('장바구니 데이터를 불러오는 도중 오류 발생', error);
  }
};

const totalQuantity = computed(() => cartGoods.value.length);

onMounted(() => {
  fetchCart();
})
</script>

<template>
  <NavigationBar/>
  <div>
    <div id="cart_title">장바구니</div>
    <div id="cart_content_box" class="flex">
      <div id="cart_left_box" class="blue_box">
        <div id="select_all_header">
          <div class="flex">
            <input type="checkbox" class="checkbox" id="select-all">
            <label for="select-all" id="checkbox_label"></label>
            <label for="select-all">전체선택(1/{{ totalQuantity}}})</label>
          </div>
          <button id="specific_delete_btn">선택삭제</button>
        </div>
        <ul>
          <li v-for="goods in cartGoods" :key="goods.id">
            <hr>
            <input type="checkbox">
            <img :src="goods.image">
            <div>
              <div>{{ goods.name }}</div>
              <div>{{ goods.price}}원</div>
            </div>
            <div>
              <span>-</span>
              <span>1</span>
              <span>+</span>
            </div>
            <button>x</button>
          </li>
        </ul>
      </div>

      <div id="cart_right_box">
        <div class="blue_box">
          <div id="address_title_text_box">
            <img src="https://img.icons8.com/?size=100&id=19326&format=png&color=000000">
            <span>배송지</span>
          </div>
          <div id="address_text_box">
            <div id="address">서울시 동작구 보라매로 87길 3층</div>
            <button id="address_change_btn">변경</button>
          </div>
        </div>
        <div class="blue_box">
          <div>결제금액</div>
          <div class="flex">
            <div>상품금액</div>
            <div>22,400원</div>
          </div>
          <div class="flex">
            <span>배송비</span>
            <span>3,000원</span>x
          </div>
          <hr>
          <div class="flex">
            <span>결제예정금액</span>
            <span>25,400원</span>
          </div>
        </div>
        <button id="order_btn">25,400원 주문하기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
*{
  font-family: "Neo둥근모 Pro";
  margin: 0 auto;
}

.flex {
  display: flex;
}

#cart_content_box {
  width: 900px;
}

#cart_title {
  text-align: center;
  font-size: 22px;
  margin: 42px 0;
}

.blue_box {
  background-color: #EEF3FF;
  border-radius: 28px;
  padding: 22px 20px;
  margin-bottom: 14px;
}

#cart_left_box {
  width: 508px;
}

#select_all_header {
  align-content: center;
}

input[type="checkbox"] {
  display: none;
}

/* off */
input[type="checkbox"]+label{
  display: inline-block;
  width: 18px;
  height: 18px;
  background-repeat: no-repeat; /* 반복 방지 */
  background-image: url('../assets/images/checkbox_off.png'); /*off 이미지*/
  background-size: 18px 18px;
}

/* on */
input[type="checkbox"]:checked+label {
  background-repeat: no-repeat; /* 반복 방지 */
  background-image: url('../assets/images/checkbox_on.png'); /*on 이미지*/
}

#checkbox_label {
  margin-right: 10px;
}

#select_all_header {
  margin-bottom: 10px;
}

#select_all_header > div {
  float: left;
  text-align: center;
  margin-top: 6px;
}

#select_all_header > button {
  float: right;
}

#specific_delete_btn {
  background-color: white;
  border: #6C94D0 1px solid;
  border-radius: 30px;
  padding: 8px 10px;
}

#cart_right_box {
  width: 328px;
}


#order_btn {

}

#cart_right_box img {
  width: 20px;
  height: 20px;
}

#address_title_text_box {
  display: inline-flex;
  align-content: center;
  margin-bottom: 16px;
}

#address_title_text_box > span {
  margin-left: 8px;
  font-size: 17px;
}

#cart_right_box > button {
  width: 328px;
  height: 58px;
  border: none;
  color: white;
  background-color: #6780E2;
  border-radius: 24px;
  font-size: 16px;
}

#address_text_box {
  display: flex;
}

#address_change_btn {
  width: 64px;
  background-color: white;
  border: #6C94D0 1px solid;
  border-radius: 30px;
  font-size: 16px;
  padding: 7px 0;
}
</style>