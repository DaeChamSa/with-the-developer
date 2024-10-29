<script setup>
import { computed, ref } from "vue";
import { usePagination } from "@/components/Pagination.js";
import { useRouter } from "vue-router";
import AdminSideBar from "@/components/AdminSideBar.vue";

const router = useRouter();

const showModal = ref(false);
const openModal = () => (showModal.value = true);
const closeModal = () => (showModal.value = false);

const users = ref([]);

// 테스트 데이터 (가상의 사용자 목록)
const testUsers = [
  { id: 1, nickName: "user1", blockCount: 2, status: "사용중" },
  { id: 2, nickName: "user2", blockCount: 0, status: "사용중" },
  { id: 3, nickName: "user3", blockCount: 1, status: "정지" },
  { id: 4, nickName: "user4", blockCount: 3, status: "사용중" },
  { id: 5, nickName: "user5", blockCount: 0, status: "정지" },
  // 더 많은 사용자 데이터 추가 가능
];

const { currentPage, totalPage, paginatedItems, setPage } = usePagination(users, 10);

// 사용자 목록 조회, 갱신
const fetchUsers = () => {
  // 임시 테스트 데이터 사용
  users.value = testUsers; // 테스트 데이터 설정
};

// 페이지가 로드될 때 사용자 목록을 가져옵니다.
fetchUsers();

</script>

<template>
  <section id="adminDiv">
    <AdminSideBar/>
    <div class="admin-users">
      <div class="header">
        <span>총 {{ users.length }}건</span>
        <div class="userStatusFilter">
          <select id="userStatus" class="filterClick">
            <option value="전체">전체</option>
            <option value="활성화">활성화</option>
            <option value="정지">정지</option>
          </select>
          <input
              type="text"
              v-model="searcMiniBar"
              placeholder="id 검색"
              class="search-input"
          />
        </div>
      </div>

      <div class="admin-userList">
        <table>
          <thead>
          <tr>
            <th><input type="checkbox" /></th>
            <th>회원번호</th>
            <th>닉네임</th>
            <th>신고횟수</th>
            <th>상태</th>
            <th>관리</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="user in paginatedItems" :key="user.id">
            <td><input type="checkbox" /></td>
            <td>{{ user.id }}</td>
            <td>{{ user.nickName }}</td>
            <td>{{ user.blockCount }}회</td>
            <td>{{ user.status }}</td>
            <td>
              <button class="block-button" v-if="user.status === '사용중'">정지</button>
              <button class="activate-button" v-else>활성화</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </section>
</template>

<style scoped>
#adminDiv{
  display: flex;
}
.admin-users {
  border: 2px;
  border-radius: 10px;
  padding: 20px;
  width: 550px;
  margin-top: 20px;
  margin: auto;
  height: 820px;
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

.block-button,
.activate-button {
  background-color: #617cc2;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
  width: 75px;
}

.block-button:hover,
.activate-button:hover {
  background-color: #495b99;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 15px;
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
