<script setup>
import {ref} from "vue";
import {usePagination} from "@/components/Pagination.js";
import {useRouter} from "vue-router";
import AdminSideBar from "@/components/AdminSideBar.vue";

const router = useRouter();

const showModal = ref(false);
const openModal = () => (showModal.value = true);
const closeModal = () => (showModal.value = false);

const users = ref([]);

const searchMiniBar = ref("");

// 테스트 데이터 (가상의 사용자 목록)
const testUsers = [
  {id: 1, nickName: "user1", orderCount: 5, payment: "완료", deliveryStatus: "배송중"},
  {id: 2, nickName: "user2", orderCount: 3, payment: "대기", deliveryStatus: "준비중"},
  {id: 3, nickName: "user3", orderCount: 8, payment: "완료", deliveryStatus: "배송완료"},
  {id: 4, nickName: "user4", orderCount: 1, payment: "취소", deliveryStatus: "취소됨"},
  {id: 5, nickName: "user5", orderCount: 0, payment: "완료", deliveryStatus: "배송중"},
  // 더 많은 사용자 데이터 추가 가능
];

const {currentPage, totalPage, paginatedItems, setPage} = usePagination(users, 10);

// 사용자 목록 조회, 갱신
const fetchUsers = () => {
  // 임시 테스트 데이터 사용
  users.value = testUsers; // 테스트 데이터 설정
};

// 페이지가 로드될 때 사용자 목록을 가져옵니다.
fetchUsers();

</script>

<template>
  <section class="admin-user">
    <AdminSideBar/>
    <div class="admin-users-content">
      <div class="header">
        <span>총 {{ users.length }}건</span>
        <div class="userStatusFilter">
          <select id="userStatus" class="filterClick">
            <option value="전체">전체</option>
            <option value="배송중">배송중</option>
            <option value="배송완료">배송완료</option>
            <option value="회수중">회수중</option>
            <option value="회수중">회수완료</option>
            <option value="결제취소">결제취소</option>
          </select>
          <input
              type="text"
              v-model="searchMiniBar"
              placeholder="id 검색"
              class="search-input"
          />
        </div>
      </div>

      <div class="admin-userList">
        <table>
          <thead>
          <tr>
            <th><input type="checkbox"/></th>
            <th>회원번호</th>
            <th>닉네임</th>
            <th>주문수량</th>
            <th>결제상태</th>
            <th>배송상태</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="user in paginatedItems" :key="user.id">
            <td><input type="checkbox"/></td>
            <td>{{ user.id }}</td>
            <td>{{ user.nickName }}</td>
            <td>{{ user.orderCount }}회</td>
            <td>{{ user.payment }}</td>
            <td>{{ user.deliveryStatus }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </section>
</template>

<style scoped>
.admin-user{
  display: flex;
}
.admin-users-content {
  border: 2px;
  border-radius: 10px;
  padding: 20px;
  width: 550px;
  margin-top: 20px;
  margin: auto;
  height: 820px;
  position: relative;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.userStatusFilter {
  flex: 1;
  text-align: right;
}

.filterClick {
  margin-right: 20px;
}

.admin-userList {
  flex-grow: 1;
}

table {
  width: 100%;
  border-collapse: collapse;
  text-align: left;
}

th,
td {
  padding: 12px;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f3f3f3;
  font-weight: bold;
}

td {
  font-size: 14px;
}

.pagination span {
  display: inline-block;
  margin: 0 5px;
  padding: 5px 10px;
  cursor: pointer;
}

.pagination .active {
  font-weight: bold;
  color: #617cc2;
}
</style>
