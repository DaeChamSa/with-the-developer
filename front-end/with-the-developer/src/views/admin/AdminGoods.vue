<script setup>
import {onMounted, ref} from "vue";
import AdminGoodsRegister from "@/views/admin/Admin-GoodsRegister.vue";
import axios from "axios";
import Modal from "@/components/Modal.vue"; // 모달창
import {useRouter} from "vue-router"; // 페이징
import { usePagination } from "@/components/Pagination.js";
import AdminGoodsDetail from "@/views/admin/AdminGoodsDetail.vue";
import AdminSideBar from "@/components/AdminSideBar.vue";

const router = useRouter();

// 굿즈 모달창
const showModal = ref(false);
const openModal = () => showModal.value = true;
const closeModal = () => showModal.value = false;
const showDetail = ref(false);
const selectGoodsCode = ref(null);


const products = ref([]);
const itemsPerPage = 10; // 페이지당 아이템 수
const { currentPage, totalPage, paginatedItems, setPage } = usePagination(products, itemsPerPage); // usePagination 사용

// 체크된 굿즈 코드 배열
const checkedGoods = ref([]);

// 로그인 함수
const loginUser = async () => {
  try {
    const adminRole = localStorage.getItem('userRole');
    if(adminRole == 'ROLE_ADMIN'){
      router.push('/goods');
    }else {
      router('/login')
    }
    console.log("admin 로그인 성공");
    await fetchGoods(); // 굿즈 목록 불러오기
  } catch (error) {
    console.error('로그인 실패:', error.response ? error.response.data : error.message);
  }
};

// 굿즈 상세 보기
const openGoodsDetail = (goodsCode) => {
  selectGoodsCode.value = goodsCode;
  showDetail.value = true;
}

// 굿즈 삭제
const deleteGoods = async () => {
  if (checkedGoods.value.length === 0) {
    alert("삭제할 상품을 선택하세요");
    return;
  }
  const deleteCheck = confirm("선택한 상품을 삭제하시겠습니까?");
  if (deleteCheck) {
    try {
      await Promise.all(checkedGoods.value.map(goodsCode => {
        return axios.delete(`http://localhost:8080/goods/${parseInt(goodsCode)}`, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });
      }));

      alert("상품이 삭제되었습니다.");
      await fetchGoods(); //삭제 후 목록 갱신
      checkedGoods.value = []; //삭제 배열 초기화
    } catch (error) {
      console.log("상품 삭제 중 오류 발생", error);
      alert("상품 삭제 실패")
    }
  }
};

  // 굿즈 목록 조회, 갱신
  const fetchGoods = async (page = 1) => {
    try {
      const response = await axios.get(`http://localhost:8080/public/goods?page=${currentPage.value}`, {
        headers: {
        },
      });
      console.log("FetchData: ", response.data);
      products.value = response.data;
    } catch (error) {
      console.log("굿즈 목록 불러오기 실패", error);
      products.splice(0, products.length);
    };
  };

// 체크박스 상태 업데이트
  const toggleChecked = (goodsCode) => {
    const index = checkedGoods.value.indexOf(goodsCode);
    if (index === -1) {
      checkedGoods.value.push(goodsCode); // 추가
    } else {
      checkedGoods.value.splice(index, 1); // 제거
    }
  }

// 페이지 로드 시 굿즈 목록 가져오기
  onMounted(() => {
    console.log("새로고침확인")
    loginUser(); // 로그인 후 굿즈 목록 가져오기
  });

// 굿즈 등록 완료 시 호출되는 함수
  const handleGoodsRegistered = () => {
    closeModal();
    fetchGoods(); // 등록 후 목록 갱신
  };

</script>

<template>
  <section class="admin-goods">
    <AdminSideBar/>
    <div class="admin-goods-content">
      <div class="header">
        <span>총 {{ products.length }}건</span>
        <button class="register-button" @click="openModal">등록하기</button>
      </div>

      <table>
        <thead>
        <tr>
          <th><input type="checkbox"/></th>
          <th>
            <button class="delete-button" @click="deleteGoods">삭제</button>
          </th>
          <th>이름</th>
          <th>가격</th>
          <th>상태</th>
          <th>수정</th>
        </tr>
        </thead>
        <tbody>
        <tr v-if="products.length === 0">
          <td colspan="6">데이터가 없습니다.</td>
        </tr>
        <tr v-for="product in paginatedItems" :key="product.goodsCode">
          <td><input type="checkbox" @change="toggleChecked(product.goodsCode)"/></td>
          <td><img :src="product.images[0] || 'default-image-path.png'" alt="product image"/></td>
          <td>{{ product.goodsName }}</td>
          <td>{{ product.goodsPrice.toLocaleString() }}원</td>
          <td>{{ product.delStatus }}</td>
          <td>
            <button class="edit-button" @click="openGoodsDetail(product.goodsCode)">보기</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </section>

  <!-- 등록페이지 열기 위한 모달창 -->
  <Modal :show="showModal" @close="closeModal">
    <AdminGoodsRegister @cancel="closeModal"/>
  </Modal>

<!--  굿즈 상세 보기 모달 -->
  <Modal :show="showDetail" @close="showDetail=false">
    <AdminGoodsDetail :goodsCode="selectGoodsCode" @cancel="showDetail=false"/>
  </Modal>

  <!-- 페이지네이션 -->
  <div class="pagination">
    <span v-for="page in totalPage" :key="page" @click="setPage(page)" :class="{ active: currentPage === page }">{{ page }}</span>
  </div>
</template>

<style scoped>

.admin-goods {
  display: flex;
  border: 2px solid #c0c0c0;
  border-radius: 10px;
  padding: 30px;
  width: 90%;
  margin: auto;
  height: 820px;
  position: relative;
}

.admin-goods-content {
  flex: 1;
  font-size: 20px;
}

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

.delete-button {
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

th,
td {
  padding: 10px;
  text-align: center;
  border-bottom: 1px solid #e0e0e0;
}

img {
  width: 50px;
  height: 50px;
}

.name-click {
  background: none;
  border: none;
  cursor: pointer;
}

.edit-button {
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 18px;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 15px;
  font-size: 20px;
}

.pagination span {
  margin: 0 5px;
  cursor: pointer;
}

.pagination span.active {
  font-weight: bold;
  color: #1b5ac2;
}

</style>
