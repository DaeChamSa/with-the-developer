<script setup>
import {computed, ref} from "vue";
import Modal from "@/components/Modal.vue";
import AdminGoodsRegister from "@/views/Admin-GoodsRegister.vue";

// 굿즈 등록 모달창
const showModal = ref(false);
const openModal = () => showModal.value = true;
const closeModal = () => showModal.value = false;

// 프론트 구현 위한 임시 굿즈 데이터
const products = [
  {id: 1, name: "나는 개발자 스티커", price: 1480, status: "판매중", img: "https://via.placeholder.com/50"},
  {id: 2, name: "나는 개발자 스티커", price: 1480, status: "판매중", img: "https://via.placeholder.com/50"},
  {id: 3, name: "나는 개발자 스티커", price: 1480, status: "판매중", img: "https://via.placeholder.com/50"},
  {id: 4, name: "나는 개발자 스티커", price: 1480, status: "판매중", img: "https://via.placeholder.com/50"},
  {id: 5, name: "나는 개발자 스티커", price: 1480, status: "판매중", img: "https://via.placeholder.com/50"},
  {id: 6, name: "나는 개발자 스티커", price: 1480, status: "판매중", img: "https://via.placeholder.com/50"},
  {id: 7, name: "나는 개발자 스티커", price: 1480, status: "판매중", img: "https://via.placeholder.com/50"},
  {id: 8, name: "나는 개발자 스티커", price: 1480, status: "판매중", img: "https://via.placeholder.com/50"},
  {id: 9, name: "나는 개발자 스티커", price: 1480, status: "판매중", img: "https://via.placeholder.com/50"},
  {id: 10, name: "나는 개발자 스티커", price: 1480, status: "판매중", img: "https://via.placeholder.com/50"},
  {id: 10, name: "나는 개발자 스티커", price: 1480, status: "판매중", img: "https://via.placeholder.com/50"}
];

// 페이지 내 상품 10개 제한
const itemPage = 10;
const currentPage = ref(1);
const totalPage = Math.ceil(products.length / itemPage);

// 현재 페이지에 표시할 상품 계산
const paginatedProducts = computed(()=>{
  const start = (currentPage.value-1) * itemPage;
  const end = start + itemPage;
  return products.slice(start, end);
})

const setPage = (page) => {
  if( page < 1 || page > totalPage) return;
  currentPage.value = page;
};

</script>

<template>
  <div class="admin-goods">
    <div class="header">
      <span>총 {{ products.length }}건</span>
      <button class="register-button" @click="openModal">등록하기</button>
    </div>

    <div class="admin-goods-content">
      <table>
        <thead>
        <tr>
          <th><input type="checkbox" /></th>
          <th><button class="delete-button">삭제</button></th>
          <th>이름</th>
          <th>가격</th>
          <th>상태</th>
          <th>수정</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="product in paginatedProducts" :key="product.id">
          <td><input type="checkbox" /></td>
          <td><img :src="product.img" alt="product image" /></td>
          <td>
<!--            상품 이름 클릭 스 상세 조회로 화면 이동 예정 -->
            <a @click.prevent="viewProductDetail(product.id)" class="name-click">{{product.name}}</a>
          </td>
          <td>{{ product.price.toLocaleString() }}원</td>
          <td>{{ product.status }}</td>
          <td>
            <button class="edit-button">수정</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>

<!--  등록페이지 열기 위한 모달창 -->
  <Modal :show="showModal" @close="closeModal">
    <AdminGoodsRegister @cancel="closeModal"/>
  </Modal>

<!--  페이지 -->
  <div class="pagination">
    <span v-for="page in totalPage" :key="page" @click="setPage(page)" :class="{ active: currentPage === page }">{{ page }}</span>
  </div>

</template>

<style scoped>
.admin-goods {
  border: 2px solid #c0c0c0;
  border-radius: 10px;
  padding: 30px;
  width: 50%;
  margin: auto;
  height: 820px;
  position: relative;
}

.admin-goods-content{
  font-size:20px
}

/* 총 수량건, 등록하기 버튼을 header에 추가*/
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1px;
  margin-top: -25px;
  background: white;
  padding: 6px;
  border-radius: 10px 10px 0 0;
  font-size: 20px;
}

.register-button {
  background-color: #4a90e2;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 18px;
}

.delete-button{
  background-color: #f56c6c;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 18px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 10px;
  text-align: center;
  border-bottom: 1px solid #e0e0e0;
}

img {
  width: 50px;
  height: 50px;
}

.name-click{
  background: none;
  border: none;
  cursor: pointer;
}


.edit-button{ /* 수정 버튼 */
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 18px;
}


.pagination{ /* 페이지 넘버링 */
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 15px;
  font-size: 20px;
}

.pagination span{ /* 페이지 숫자 간격, 마우스 커서 변경 */
  margin: 0 5px;
  cursor: pointer;
}

.pagination span.active{
  font-weight: bold;
  color: #1b5ac2;
}
</style>
