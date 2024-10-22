<template>
  <div class="jobtag-box">
    <h2>직무 태그 관리</h2>
    <div class="tags">
      <span v-for="tag in jobTagList" :key="tag.id" class="tag">
        {{ tag.jobName }} <button @click="removeTag(tag.id)">×</button>
      </span>
    </div>

    <div class="input-container">
      <input
          v-model="newTag"
          @keyup.enter="addTag"
          placeholder="직무 태그를 입력하세요"
      />
      <button @click="addTag">추가</button>
    </div>
  </div>


  <div class="blocktag-box">
    <h2>신고 사유 카테고리 관리</h2>
    <div class="tags">
      <span v-for="tag in blockTagList" :key="tag.id" class="tag">
        {{ tag.blockContent }} <button @click="removeTag(tag.id)">×</button>
      </span>
    </div>

    <div class="input-container">
      <input
          v-model="newTag"
          @keyup.enter="addTag"
          placeholder="신고 사유를 입력하세요"
      />
      <button @click="addTag">추가</button>
    </div>
  </div>



</template>

<script setup>
import { ref } from 'vue';

// 직무 태그 리스트
const jobTagList = ref([
  { id: 1, jobName: "기획자" },
  { id: 2, jobName: "데이터엔지니어" },
  { id: 3, jobName: "디자이너" },
  { id: 4, jobName: "백엔드" },
  { id: 5, jobName: "퍼블리셔" },
  { id: 6, jobName: "프론트엔드" },
  { id: 7, jobName: "AI개발자" },
  { id: 8, jobName: "PM" }
]);

// 신고 사유 카테고리 태그 리스트
const blockTagList = ref([
  {id: 1, blockContent: "부적절한 컨텐츠입니다."},
  {id: 2, blockContent: "서비스 규칙 위반입니다."},
  {id: 3, blockContent: "스팸 및 광고입니다."},
  {id:4, blockContent: "기타"}
])




// 새로운 태그 입력
const newTag = ref('');

// 태그 추가 함수
const addTag = () => {
  const trimmedTag = newTag.value.trim();
  if (trimmedTag && !jobTagList.value.some(tag => tag.jobName === trimmedTag)) {
    jobTagList.value.push({
      id: jobTagList.value.length + 1,
      jobName: trimmedTag
    });
  }
  newTag.value = '';
};

// 태그 삭제 함수
const removeTag = (id) => {
  jobTagList.value = jobTagList.value.filter(tag => tag.id !== id);
};
</script>

<style scoped>
/* 직무 태그 관리 박스 */
.jobtag-box {
  width: 400px;
  padding: 20px;
  background-color: #edf3ff;
  border-radius: 15px;
  text-align: center;
  margin-bottom: 20px;
}

/* 신고 사유 카테고리 관리 박스*/
.blocktag-box{
  width: 400px;
  padding: 20px;
  background-color: #edf3ff;
  border-radius: 15px;
  text-align: center;
  margin-bottom: 20px;
}




h2 {
  margin-bottom: 15px;
  font-size: 18px;
  color: #333;
}

.tags {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-bottom: 10px;
}

.tag {
  background-color: white;
  border: 1px solid #b0c4de;
  border-radius: 18px;
  padding: 1px 10px;
  margin: 2px;
  font-size: 14px;
  color: #333;
  display: flex;
  align-items: center;
}

.tag button {
  background: none;
  border: none;
  color: #948a8a;
  margin-left: 3px;
  cursor: pointer;
  font-size: 13px;
}

.input-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
/* 태그 추가 입력 칸*/
input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 10px;
  width: 70%;
  margin-right: 10px;
}

/* 추가 버튼 */
button {
  padding: 8px 15px;
  background-color: #b3c8f5;
  border: none;
  border-radius: 10px;
  color: black;
  cursor: pointer;
}

/* 추가 버튼 마우스 이동 시 효과 */
button:hover {
  color: white;
  background-color: #617CC2;
}

*{
  font-family: "Neo둥근모 Pro";
  margin: 0 auto;
}
</style>
