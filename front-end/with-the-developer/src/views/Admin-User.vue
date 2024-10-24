<script setup>
import {computed, ref} from "vue";
import AdminBlock from "@/views/AdminBlock.vue";
import Modal from '@/components/Modal.vue';
import {usePagination} from "@/components/Pagination.js";


const showModal = ref(false);
const openModal = () => showModal.value = true;
const closeModal = () => showModal.value = false;




// 프론트 구현 위한 임시 굿즈 데이터
const users = [
  {id: "user01", nickName: "발랄한개발자", "blockCount": 0, status: "사용중"},
  {id: "user02", nickName: "발랄한개발자", "blockCount": 0, status: "정지(30일)"},
  {id: "user03", nickName: "발랄한개발자", "blockCount": 0, status: "사용중"},
  {id: "user04", nickName: "발랄한개발자", "blockCount": 0, status: "정지(5일)"},
  {id: "user05", nickName: "발랄한개발자", "blockCount": 0, status: "사용중"},
  {id: "user06", nickName: "발랄한개발자", "blockCount": 0, status: "사용중"},
  {id: "user07", nickName: "발랄한개발자", "blockCount": 0, status: "사용중"},
  {id: "user08", nickName: "발랄한개발자", "blockCount": 0, status: "사용중"},
  {id: "user09", nickName: "발랄한개발자", "blockCount": 0, status: "사용중"},
  {id: "user10", nickName: "발랄한개발자", "blockCount": 0, status: "사용중"},
  {id: "user11", nickName: "발랄한개발자", "blockCount": 0, status: "사용중"}
];

const { currentPage, totalPage, paginatedItems, setPage } = usePagination(users, 10);

</script>

<template>
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
          v-model="searchBar"
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
          <th>신고횟수</th>
          <th>상태</th>
          <th>관리</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in paginatedItems" :key="user.id">
          <td><input type="checkbox"/></td>
          <td>{{ user.id }}</td>
          <td  @click="openModal">{{ user.nickName }}</td>
          <td>{{ user.blockCount }}회</td>
          <td>{{ user.status }}</td>
          <td>
            <button class="block-button" v-if="user.status==='사용중'">정지</button>
            <button class="activate-button" v-else>활성화</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>

  <!--  페이지 -->
  <div class="pagination">
    <span v-for="page in totalPage" :key="page" @click="setPage(page)" :class="{ active: currentPage.value === page }">
      {{ page }}
    </span>
  </div>

<!--  모달페이지 -->
  <Modal :show="showModal" @close="closeModal">
    <AdminBlock @cancel="closeModal"/>
  </Modal>

</template>

<style scoped>
.admin-users {
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

.userStatusFilter{
  flex: 1;
  text-align: right;
}

.filterClick{
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

th, td {
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

.block-button, .activate-button {
  background-color: #617CC2;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
  width: 75px;
}

.block-button:hover, .activate-button:hover {
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
  color: #617CC2;
}
</style>