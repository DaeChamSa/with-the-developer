<template>
  <section class="admin-tag">
    <AdminSideBar class="sidebar"/>
    <div class="Alltag">
      <div class="jobtag-box">
        <h2>직무 태그 관리</h2>
        <div class="tags">
      <span v-for="tag in jobTagList" :key="tag.id" class="tag">
        {{ tag.jobName }} <button @click="removeJobTag(tag.id)">×</button>
      </span>
        </div>

        <div class="input-container">
          <input
              v-model="newJobTag"
              @keyup.enter="addJobTag"
              placeholder="직무 태그를 입력하세요"
          />
          <button @click="addJobTag">추가</button>
        </div>
      </div>

      <div class="blocktag-box">
        <h2>신고 사유 카테고리 관리</h2>
        <div class="tags">
      <span v-for="tag in blockTagList" :key="tag.id" class="tag">
        {{ tag.blockContent }} <button @click="removeBlockTag(tag.id)">×</button>
      </span>
        </div>

        <div class="input-container">
          <input
              v-model="newBlockTag"
              @keyup.enter="addBlockTag"
              placeholder="신고 사유를 입력하세요"
          />
          <button @click="addBlockTag">추가</button>
        </div>
      </div>

      <div class="dbtiTag-box">
        <h2>성향 태그 관리</h2>

        <!-- DBTI 직무 선택 -->
        <div class="dbtiTag-select-job">
          <select id="dbtiJob v-model=" selectedJob @change="filterTagsByJob">
            <option value="">해당 DBTI 직무</option>
            <option value="BACKEND">BACKEND</option>
            <option value="FRONTEND">FRONTEND</option>
            <option value="DESIGNER">DESIGNER</option>
            <option value="PM">PM</option>
          </select>
        </div>

        <!--    필터링 성향 태그 표시 -->
        <div class="tags">
      <span v-for="tag in dbtiTagList" :key="tag.id" class="tag">
        {{ tag.dbtiContent }} <button @click="removeDbtiTag(tag.id)">×</button>
      </span>
        </div>

        <!--    태그 추가 -->
        <div class="input-container">
          <input
              v-model="newDBTITag"
              @keyup.enter="addDBTITag"
              placeholder="성향 태그를 입력하세요"
          />
          <button @click="addDBTITag">추가</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import {ref} from 'vue';
import AdminSideBar from "@/components/AdminSideBar.vue"

// 직무 태그 리스트
const jobTagList = ref([
  {id: 1, jobName: "기획자"},
  {id: 2, jobName: "데이터엔지니어"},
  {id: 3, jobName: "디자이너"},
  {id: 4, jobName: "백엔드"},
  {id: 5, jobName: "퍼블리셔"},
  {id: 6, jobName: "프론트엔드"},
  {id: 7, jobName: "AI개발자"},
  {id: 8, jobName: "PM"}
]);

// 신고 사유 카테고리 태그 리스트
const blockTagList = ref([
  {id: 1, blockContent: "부적절한 컨텐츠입니다."},
  {id: 2, blockContent: "서비스 규칙 위반입니다."},
  {id: 3, blockContent: "스팸 및 광고입니다."},
  {id: 4, blockContent: "기타"}
])

// 성향 태그 리스트
const dbtiTagList = ref([
  {id: 1, dbtiContent: "세심한", job: "BACKEND"},
  {id: 2, dbtiContent: "계획적인", job: "BACKEND"},
  {id: 3, dbtiContent: "치밀한", job: "BACKEND"},
  {id: 4, dbtiContent: "미적인", job: "DESIGNER"},
  {id: 5, dbtiContent: "폭발적인", job: "FRONTEND"},
  {id: 6, dbtiContent: "반응적인", job: "FRONTEND"},
  {id: 7, dbtiContent: "능동적인", job: "PM"},
  {id: 8, dbtiContent: "꼼꼼한", job: "PM"}
]);

// 선택된 직무에 따라 필터링된 태그 리스트
const filteredTags = ref([]);

// 성향 선택 전 기본값
const selectedJob = ref('');

// 새로운 직무 태그 입력
const newJobTag = ref('');

// 새로운 신고 사유 태그 입력
const newBlockTag = ref('');

// 새로운 성향 태그 입력
const newDBTITag = ref('');

// 직무에 따른 태그 필터링
const filterTagsByJob = () => {
  if (selectedJob.value) {
    filteredTags.value = dbtiTagList.value.filter(tag => tag.job === selectedJob.value);
  } else {
    filteredTags.value = [];
  }
}

// 초기 태그 필터링 설정
filterTagsByJob();

// 직무 태그 추가 함수
const addJobTag = () => {
  const trimmedTag = newJobTag.value.trim();
  if (trimmedTag && !jobTagList.value.some(tag => tag.jobName === trimmedTag)) {
    jobTagList.value.push({
      id: jobTagList.value.length + 1,
      jobName: trimmedTag
    });
  }
  newJobTag.value = '';
};

// 신고 사유 태그 추가 함수
const addBlockTag = () => {
  const trimmedTag = newBlockTag.value.trim();
  if (trimmedTag && !blockTagList.value.some(tag => tag.blockContent === trimmedTag)) {
    blockTagList.value.push({
      id: blockTagList.value.length + 1,
      blockContent: trimmedTag
    });
  }
  newBlockTag.value = '';
};

// 성향 태그 추가 함수
const addDBTITag = () => {
  const trimmedTag = newDbtiTag.value.trim();
  if (trimmedTag && selectedJob.value && !dbtiTagList.value.some(tag => tag.dbtiContent === trimmedTag && tag.job === selectedJob.value)) {
    dbtiTagList.value.push({
      id: dbtiTagList.value.length + 1,
      dbtiContent: trimmedTag,
      job: selectedJob.value
    });
    filterTagsByJob();
  }
  newDBTITag.value = '';
};

// 태그 삭제 함수
const removeJobTag = (id) => {
  jobTagList.value = jobTagList.value.filter(tag => tag.id !== id);
};

const removeBlockTag = (id) => {
  blockTagList.value = blockTagList.value.filter(tag => tag.id !== id);
};

const removeDbtiTag = (id) => {
  dbtiTagList.value = dbtiTagList.value.filter(tag => tag.id !== id);
  filterTagsByJob();
};
</script>

<style scoped>
.admin-tag{
  display: flex;
}

.sidebar{
  width: 200px;
}

.Alltag {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 태그 박스*/
.jobtag-box,
.blocktag-box,
.dbtiTag-box
{
  width: 400px;
  padding: 20px;
  background-color: #edf3ff;
  border-radius: 15px;
  text-align: center;
  margin-bottom: 20px;
}

/* 해당 DBTI 선택 버튼*/
.dbtiTag-select-job {
  margin-left: 250px;
  margin-bottom: 10px;
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

* {
  font-family: "Neo둥근모 Pro";
  margin: 0 auto;
}
</style>
