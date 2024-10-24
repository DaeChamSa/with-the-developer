<script setup>
import NavigationBar from "@/components/NavigationBar.vue";
import axios from "axios";
import {ref, reactive, onMounted, watch, computed} from "vue";

const cartGoods = reactive([]);

// 전체 선택에 대한 상태 관리 (false: 전체 선택 안됨, true: 전체 선택 됨)
const selectAll = ref(true);
let totalPrice = 0;

// 로그인
const loginUser = async () => {
  try {
    const token = `eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMDJAbmF2ZXIuY29tIiwidXNlckNvZGUiOjIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE3Mjk2NjY4NjJ9.nEH9Y9erl4F7z40oIYxsRIH-5oX7POx4AbtFQnGhBzWIdeh9Bsk_9uMRrIKZUOUYfLgAT-kVg7qeOugs6nrnxw`;
    localStorage.setItem('jwtToken', token);

    // Axios 기본 헤더에 JWT 토큰 추가
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    console.log(axios.defaults.headers.common['Authorization']);
    console.log('로그인 성공:', response.data);
  } catch (error) {
    console.error('로그인 실패:', error.response ? error.response.data : error.message);
  }
};

// 상품 개수에 따라 ul height 조절
const ulBoxHeight = computed(() => {
  return cartGoods.length === 0 ? '336px' : `${cartGoods.length * 130 + 71}px`
})

// cartGoods에 장바구니에 담긴 굿즈들 넣어주기
const fetchCartGoods = async () => {
  try {
    // 장바구니 데이터 가져오기
    const response = await axios.get('http://localhost:8080/cart-goods', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
      }
    });
    const goodsList = response.data;
    await Promise.all(goodsList.map(async (goods) => {
      const goodsInfo = await axios.get(`http://localhost:8080/public/goods/${goods.goodsGoodsCode}`);
      if (goodsInfo) {
        cartGoods.push({
          goodsCode: goods.goodsGoodsCode,
          amount: goods.goodsAmount,
          name: goodsInfo.data.goodsName,
          price: goodsInfo.data.goodsPrice,
          isSelected: true,
        });
      }
    }));
    saveCheckboxState();
  } catch (error) {
    console.log('장바구니 정보를 불러오던 도중 오류 발생');
  }
}

// 장바구니에 담긴 굿즈 삭제
const removeCartGoods = async(index) => {
  const goodsCode = cartGoods[index].goodsCode;

  try {
    await axios.delete(`http://localhost:8080/cart-goods/${goodsCode}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
      }
    });
    cartGoods.splice(index, 1); // 로컬 상태에서 제거
  } catch(error) {
    console.log("장바구니에 담긴 상품 삭제 중 오류 발생");
  }
}

const deleteSelectedCartGoods = async() => {
  const selectedCartGoodsList = cartGoods
      .filter(goods => goods.isSelected)
      .map(goods => goods.goodsCode);
  try {
    for (const goodsCode of selectedCartGoodsList) {
      await axios.delete(`http://localhost:8080/cart-goods/${goodsCode}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
        }
      });

      // 굿즈코드로 해당 인덱스 찾기
      const index = cartGoods.findIndex(goods => goods.goodsCode === goodsCode);
      if (index !== -1) {
        cartGoods.splice(index, 1);
      }
    }
  } catch(error) {
    console.log("장바구니에서 선택 상품 삭제 중 오류 발생");
  }
}

// 굿즈 전체 선택/해제 (전체 선택 체크박스가 각 굿즈 체크박스에 적용되는 경우에 대한 메소드)
const selectGoodsAll = () => {
  cartGoods.forEach(goods => {
    goods.isSelected = selectAll.value;
  })
}

// 선택된 굿즈 개수 카운트
const countSelectedGoods = () => {
  return cartGoods.filter(goods => goods.isSelected).length;
}

// '굿즈 선택 개수 = 장바구니에 담긴 전체 굿즈 개수' 일 경우 전체 선택 체크박스에 체크
const updateSelectAllCheckbox = () => {
  selectAll.value = (countSelectedGoods() === cartGoods.length);
}

// 체크박스 로컬스토리지에 저장(새로고침 시에도 체크된 항목들이 유지되게)
const saveCheckboxState = () => {
  const selectedGoodsCodes = cartGoods
      .filter(goods => goods.isSelected)  // isSelected가 true, 즉 선택된 굿즈들만 담긴 배열 반환
      .map(goods => goods.goodsCode);
  localStorage.setItem('selectedGoods', JSON.stringify(selectedGoodsCodes));
}

// 로컬스토리지에서 체크박스 상태 가져오기
const getCheckboxState = () => {
  const selectedGoodsCodes = JSON.parse(localStorage.getItem('selectedGoods')) || []; // 로컬스토리지에 해당 데이터 없을 경우 빈 배열로 초기화
  cartGoods.forEach(goods => {
    goods.isSelected = selectedGoodsCodes.includes(goods.goodsCode); // 로컬스토리지에서 체크되어 있다고 저장된 굿즈의 체크박스 체크
  });
  // 장바구니에 굿즈가 존재하는데 모든 굿즈가 선택되어 있으면 전체체크박스도 체크
  selectAll.value = cartGoods.length > 0 && cartGoods.every(goods => goods.isSelected);
}

// 선택된 굿즈의 가격 계산
const getSelectedCartGoodsPrice = () => {
  totalPrice = cartGoods.filter(goods => goods.isSelected).reduce((total, goods) => total + goods.price * goods.amount, 0);
  return totalPrice;
}

// 가격 10000 -> 10,000으로 formatting
const formatPrice = (price) => {
  if (price === undefined || price === null) {
    return '가격 정보가 없습니다';
  }
  return price.toLocaleString();
}

// 굿즈 수량 조절
const updateQuantity = async(index, amount) => {
  const goodsCode = cartGoods[index].goodsCode;
  const updatedAmount = cartGoods[index].amount + amount;
  if (updatedAmount < 1) {
    alert('1개 이상부터 구매할 수 있습니다.');
    return;
  }
  try {
    // DB 굿즈 개수 업데이트
    await axios.put(`http://localhost:8080/cart-goods/${goodsCode}`, null,{
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
      },
      params: {
        amount: updatedAmount
      }
    })

    cartGoods[index].amount = updatedAmount;

  } catch(error) {
    console.log("상품 개수 업데이트 중 오류 발생")
  }
}

onMounted(async() => {
  await fetchCartGoods();
  getCheckboxState(); // 체크박스 상태 가져오기
  // cartGoods의 변화를 감지. 변화가 있을 경우 saveCheckboxState 함수 호출해서 체크박스 상태 저장
  watch(cartGoods, saveCheckboxState, {deep: true});  // deep: true -> 배열의 속성 값의 변화까지도 감시
  watch(cartGoods, updateSelectAllCheckbox, {deep: true});
});

</script>

<template>
  <NavigationBar/>
  <div>
    <div id="cart_title">장바구니</div>
    <div id="cart_content_box" class="flex">
      <div id="cart_left_box" class="blue_box" :style="{ height: ulBoxHeight}">
        <div id="select_all_header">
          <div class="flex">
            <input
                type="checkbox"
                class="checkbox"
                id="select_all"
                v-model="selectAll"
                @change="selectGoodsAll"
                :disabled="cartGoods.length===0"
            >
            <label for="select_all" class="checkbox_label pointer"></label>
            <label for="select_all">전체선택({{ countSelectedGoods() }}/{{ cartGoods.length }})</label>
          </div>
          <button
              id="selected_delete_btn"
              class="pointer"
              @click="deleteSelectedCartGoods()"
              :disabled="cartGoods.length===0"
          >선택삭제</button>
        </div>
        <ul>
          <hr v-if="cartGoods.length === 0">
          <li v-if="cartGoods.length === 0" id="emptyCart">장바구니에 담긴 상품이 없습니다.</li>
          <li v-for="(goods, index) in cartGoods" :key="goods.goodsGoodsCode">
            <hr>
            <div class="flex">
              <input
                  type="checkbox"
                  class="checkbox"
                  :id="'select-' + goods.goodsCode"
                  v-model="goods.isSelected"
              >
              <label :for="'select-' + goods.goodsCode" class="checkbox_label pointer"></label>
              <img src="../assets/images/goods.png">
              <div class="goods_info_text_box">
                <div>{{ goods.name }}</div>
                <div>{{ formatPrice(goods.price)}}원</div>
              </div>
              <div class="quantity_box">
                <span class="pointer" @click="updateQuantity(index, -1)">-</span>
                <span>{{ goods.amount}}</span>
                <span class="pointer" @click="updateQuantity(index, 1)">+</span>
              </div>
              <button class="pointer" @click="removeCartGoods(index)">x</button>
            </div>
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
            <button id="address_change_btn" class="pointer">변경</button>
          </div>
        </div>
        <div class="blue_box">
          <div id="price_text">결제금액</div>
          <div class="float_left_right">
            <div>상품금액</div>
            <div>{{ formatPrice(getSelectedCartGoodsPrice()) }}원</div>
          </div>
          <div class="float_left_right">
            <div>배송비</div>
            <div>3,000원</div>
          </div>
          <hr>
          <div id="total_price_box" class="flex">
            <div>결제예정금액</div>
            <div id="total_price_text">{{ formatPrice(getSelectedCartGoodsPrice() + 3000)}}원</div>
          </div>
        </div>
        <button id="order_btn" class="pointer">{{ formatPrice(getSelectedCartGoodsPrice() + 3000)}}원 주문하기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  font-family: "Neo둥근모 Pro";
  margin: 0 auto;
  box-sizing: border-box;
}

.flex {
  display: flex;
}

.pointer {
  cursor: pointer;
}

.blue_box {
  background-color: #EEF3FF;
  border-radius: 25px;
  padding: 20px 18px;
  margin-bottom: 14px;
}

#cart_content_box {
  width: 905px;
}

#cart_title {
  text-align: center;
  font-size: 22px;
  margin: 42px 0;
}

#cart_left_box {
  width: 540px;
}

#select_all_header {
  align-content: center;
}

input[type="checkbox"] {
  display: none;
  height: 18px;
}

/* off */
input[type="checkbox"] + label {
  display: inline-block;
  width: 18px;
  height: 18px;
  background-repeat: no-repeat;
  background-image: url('../assets/images/checkbox_off.png');
  background-size: 18px 18px;
}

/* on */
input[type="checkbox"]:checked + label {
  background-repeat: no-repeat;
  background-image: url('../assets/images/checkbox_on.png');
}

.checkbox_label {
  margin-right: 10px;
  margin-left: 0;
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

#selected_delete_btn {
  background-color: white;
  border: #6C94D0 1px solid;
  border-radius: 30px;
  padding: 8px 10px;
}

ul {
  padding: 0;
}

#emptyCart {
  font-size: 20px;
  text-align: center;
  margin: 120px 0;
  color: #7E7E7E;
}

li {
  list-style-type: none;
}

li > hr {
  width: 500px;
}

li > div {
  padding: 14px 0;
}

li img {
  width: 100px;
  height: 100px;
  margin: 0;
}

.goods_info_text_box {
  padding: 27px 0;
  margin-left: 18px;
}

.goods_info_text_box > div:first-child {
  margin-bottom: 12px;
}

.quantity_box {
  border: #6C94D0 1px solid;
  border-radius: 30px;
  width: 90px;
  height: 35px;
  font-size: 20px;
  margin: 30px 16px;
}

.quantity_box > span {
  display: inline-block;
  width: 29px;
  text-align: center;
  padding: 8px 0;
}

li button {
  width: 20px;
  height: 20px;
  background-color: transparent;
  border: none;
  color: #938888;
  font-size: 20px;
  margin: 0;
}

#cart_right_box {
  width: 328px;
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

#price_text {
  font-size: 17px;
  margin-bottom: 16px;
}

.float_left_right {
  width: 292px;
  height: 15px;
  margin: 15px 0;
}

.float_left_right > div:first-child {
  float: left;
}

.float_left_right > div:last-child {
  float: right;
}

#cart_right_box hr {
  width: 292px;
}

#total_price_box {
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

#total_price_box > div {
  margin: 0;
}

#total_price_text {
  font-size: 20px;
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
</style>