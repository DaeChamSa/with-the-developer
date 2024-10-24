<template>
  <div class="container">
    <!-- 굿즈 등록 폼 -->
    <div class="form-container">
      <div class="input-group">
        <label for="name">굿즈 이름</label>
        <input v-model="goodsName" id="name" placeholder="굿즈 이름" />
      </div>

      <div class="input-group">
        <label for="price">굿즈 가격</label>
        <input v-model="goodsPrice" id="price" placeholder="굿즈 가격" />
      </div>

      <div class="input-group">
        <label for="status">판매상태 선택</label>
        <select v-model="goodsStatus" id="status">
          <option value="판매중">판매중</option>
          <option value="품절">품절</option>
        </select>
      </div>

      <div class="input-group">
        <label for="images">이미지 목록</label>
        <div class="image-list">
          <!-- 이미지 목록을 렌더링 -->
          <img v-for="(image, index) in imageList" :src="image" :key="index" class="thumbnail" />
        </div>
        <input type="file" @change="handelFileUpload" multiple/>
        <button @click="submitGoods">이미지 업로드</button>
      </div>

      <div class="input-group">
        <label for="content">본문</label>
        <textarea v-model="goodsContent" id="content" placeholder="본문"></textarea>
      </div>

      <div class="button-group">
        <button @click="submitGoods">등록하기</button>
        <button @click="$emit('cancel')">취소하기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

// 폼 데이터
const goodsName = ref('');
const goodsPrice = ref('');
const goodsStatus = ref('판매중');
const goodsContent = ref('');

// 이미지 목록, 파일데이터
const imageList = ref([]);
const fileList = ref([]);

// 이미지 업로드
const handelFileUpload = (event) => {
  const files = event.target.files;
  for(let i=0; i<files.length; i++){
    const file = files[i];
    fileList.value.push(file);
  }
  console.log('이미지 업로드');

  const reader = new FileReader();
  reader.onload = (e) => {
    imageList.value.push(e.target.result);
  }
  reader.readAsDataURL(file);
};

// 굿즈 등록하기
const submitGoods = () => {
  const formData = new FormData();

  fileList.value.forEach((file, index)=>{
    formData.append(`{file_${index}`,file);
  });
}
</script>

<style scoped>
.container {
  display: flex;
  width: 300px;
  flex-direction: row;
  margin-bottom: 20px;
  margin-top: 20px;
}

.form-container {
  flex-grow: 1;
  background-color: #fff;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.input-group {
  margin-bottom: 15px;
}

.input-group label {
  display: block;
  margin-bottom: 5px;
}

.input-group input,
.input-group select,
.input-group textarea {
  width: 100%;
  padding: 8px;
  border-radius: 4px;
  border: 1px solid #ddd;
}

.image-list {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

.thumbnail {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin-right: 10px;
  border-radius: 4px;
}

.button-group {
  display: flex;
  gap: 10px;
}

button {
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #617CC2;
  color: white;
}

*{
  font-family: "Neo둥근모 Pro";
  margin: 0 auto;
}
</style>
