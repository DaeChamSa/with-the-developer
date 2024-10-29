<template>
  <div class="goods-detail">
    <div class="updateName">
    <h3>상품이름: {{ product.goodsName }}</h3>
    <input v-model="product.goodsName" placeholder="상품 이름을 입력하세요" />
    </div>
    <br>
    <img :src="product.images && product.images[0] ? product.images[0] : 'default-image-path.png'" alt="product image" />
    <div class="updateContent">
    <p>상품 상세설명: {{ product.goodsContent }}</p>
    <textarea v-model="product.goodsContent" placeholder="상품 상세설명을 입력하세요"></textarea>
    </div>
    <div class="updatePrice">
    <p>가격: {{ product.goodsPrice }}원</p>
    <input type="number" v-model.number="product.goodsPrice" placeholder="가격을 입력하세요" />
    </div>
    <div class="updateStatus">
    <p>상태: {{ product.goodsStatus }}</p>
    <select v-model="product.goodsStatus">
      <option value="판매중">판매중</option>
      <option value="품절">품절</option>
    </select>
    </div>
  </div>

  <div class="button-group">
    <button @click="updateProduct">수정하기</button>
    <button @click="$emit('cancel')">닫기</button>
  </div>
</template>

<script setup>
import {onMounted, ref, watch} from 'vue';
import axios from 'axios';

const props = defineProps({
  goodsCode: Number
});

// 상품 데이터 상태
const product = ref({});
const emit = defineEmits(['cancel']);

const fetchProductDetail = async () => {

  if(props.goodsCode){
    try {
      const response = await axios.get(`http://localhost:8080/public/goods/${props.goodsCode}`,{
      });
      product.value = response.data;
      console.log(`${props.goodsCode}`);
    } catch (error) {
      console.log("상품 정보 불러오기 실패", error);
    }
  }
};

// 상품 업데이트
const updateProduct = async () => {
  localStorage.setItem('userRole', 'ROLE_ADMIN'); //** admin 테스트 후 삭제 예정
  if(localStorage.getItem('userRole') !== 'ROLE_ADMIN') {
    alert("관리자 권한이 없습니다.")
    return;
  }
    const formData = new FormData();

    const goodsUpdateDTO = {
      goodsCode: product.value.goodsCode,
      goodsName: product.value.goodsName,
      goodsContent: product.value.goodsContent,
      goodsStock: product.value.goodsStock,
      goodsStatus: product.value.goodsStatus,
      goodsPrice: product.value.goodsPrice,
    };

    formData.append('goodsUpdateDTO', new Blob([JSON.stringify(goodsUpdateDTO)], { type: "application/json" }));

    // 이미지 파일이 있을 경우 추가
    if (product.value.images && product.value.images.length > 0) {
      product.value.images.forEach((image) => {
        formData.append('images', image);
      });
    }

  try{
    await axios.put(`http://localhost:8080/goods/${props.goodsCode}`,formData,{
      headers: {
        'Content-Type' : 'multipart/form-data',
      }
    });
    alert("상품 정보 수정 완료");
    emit('cancel');
  }catch (error){
    console.log("상품 수정 실패: ", error);
    alert("상품 수정 실패")
  }
}

// goodsCode 변경될때마다 가져오기
watch(()=>props.goodsCode, ()=>{
  fetchProductDetail()
});

// 컴포넌트 마운트 될 때 상품 정보 가져오기
onMounted(fetchProductDetail);
</script>

<style scoped>
.goods-detail {
  width: 400px; /* 너비 조정 */
  padding: 20px; /* 여백 추가 */
  border: 1px solid #ccc; /* 테두리 추가 */
  border-radius: 10px; /* 모서리 둥글게 */
  background-color: #f9f9f9; /* 배경색 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
}

.updateName, .updateContent, .updatePrice, .updateStatus {
  margin-bottom: 15px; /* 각 항목 간격 */
}

input[type="text"], input[type="number"], textarea, select {
  width: 100%; /* 너비 100% */
  padding: 10px; /* 여백 */
  border: 1px solid #ddd; /* 테두리 */
  border-radius: 5px; /* 모서리 둥글게 */
  font-size: 14px; /* 글자 크기 */
  transition: border-color 0.3s; /* 테두리 색상 전환 효과 */
}

input[type="text"]:focus, input[type="number"]:focus, textarea:focus, select:focus {
  border-color: #007bff; /* 포커스 시 테두리 색상 변경 */
  outline: none; /* 아웃라인 제거 */
}

.button-group {
  display: flex; /* 버튼 그룹을 flexbox로 구성 */
  justify-content: space-between; /* 버튼 사이 공간 분배 */
}

button {
  padding: 10px 15px; /* 버튼 여백 */
  border: none; /* 테두리 제거 */
  border-radius: 5px; /* 모서리 둥글게 */
  background-color: #007bff; /* 배경색 */
  color: white; /* 글자 색상 */
  cursor: pointer; /* 마우스 포인터 변경 */
  transition: background-color 0.3s; /* 배경색 전환 효과 */
}

button:hover {
  background-color: #0056b3; /* 호버 시 배경색 변경 */
}

*{
  font-family: "Neo둥근모 Pro";
  font-size: 25px;
  margin: 0 auto;
}
</style>
