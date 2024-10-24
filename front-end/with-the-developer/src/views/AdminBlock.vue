<template>
  <div id="app">
    <div class="content">
      <h3 class="header">신고 게시글</h3>
      <div class="blockPost form-group">
        <label>신고 게시글</label>
        <input type="text" v-model="blockPostSub" disabled class="input-disabled"/>
      </div>

      <div class="form-group">
        <label>신고 대상자</label>
        <input type="text" v-model="blockTargetUser" disabled class="input-disabled"/>
      </div>

      <div class="form-group">
        <label>신고자</label>
        <input type="text" v-model="blockSendUser" disabled class="input-disabled"/>
      </div>

      <div class="form-group">
        <label>신고 사유</label>
        <select v-model="reportReason" class="input-select">
          <option value="">선택</option>
          <option value="비난, 날조">비난, 날조</option>
          <option value="욕설">욕설</option>
          <option value="스팸">스팸</option>
        </select>
      </div>

      <div class="form-group small-input-group">
        <label>신고 횟수</label>
        <input type="number" v-model="reportCount" min="1" class="small-input"/>
      </div>

      <div class="form-group">
        <label>신고 상세 사유</label>
        <textarea v-model="reportDetails" rows="4" class="input-textarea"></textarea>
      </div>

      <div class="form-group inline-group">
        <div class="buttons">
          <button @click="submitReport" class="btn-submit">정지</button>
          <button @click="resetForm" class="btn-reset">취소</button>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
//임시데이터
export default {
  data() {
    return {
      blockPostSub: "게시글 신고다",
      blockTargetUser: "쿠로미",
      blockSendUser: "마이멜로디",
      reportReason: "비난, 날조",
      reportCount: 1,
      reportDetails: "",
      banDuration: 1,
    };
  },
  methods: {
    submitReport() {
      // 신고 제출 로직 처리
      console.log({
        blockPostSub: this.blockPostSub,
        blockTargetUser: this.blockTargetUser,
        blockSendUser: this.blockSendUser,
        reportReason: this.reportReason,
        reportCount: this.reportCount,
        reportDetails: this.reportDetails,
        banDuration: this.banDuration,
      });
    },
    resetForm() {
      this.reportReason = "비난, 날조";
      this.reportCount = 1;
      this.reportDetails = "";
      this.banDuration = 1;
    },
  },
};
</script>

<style scoped>
/* 메인 레이아웃 */
.content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 500px;
  margin: 50px auto;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

/* 제목 스타일 */
.header {
  background-color: #486db5;
  color: white;
  padding: 10px;
  border-radius: 4px 4px 0 0;
  text-align: center;
  margin-bottom: 20px;
}

/* 입력 폼 스타일 */
.form-group {
  margin-bottom: 20px; /* 하단에 여백 추가 */
}

.input-disabled {
  background-color: #e9ecef;
  border: 1px solid #ced4da;
  border-radius: 4px;
  padding: 10px;
  width: 95%;
  display: flex;
}

.small-input {
  width: 50%;
  padding: 8px;
  margin-top: 5px;
  border: 1px solid #ced4da;
  border-radius: 4px;
}

.input-textarea {
  width: 400px;
  padding: 8px;
  margin-top: 5px;
  border: 1px solid #ced4da;
  border-radius: 4px;
}

.small-input {
  width: 30px;
  text-align: right;
}

.inline-group{
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 버튼 스타일 */
.buttons {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.btn-submit {
  background-color: red;
  color: white;
  padding: 10px 20px;
  margin-right: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-reset {
  background-color: #6c757d;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

* {
  font-family: "Neo둥근모 Pro";
}
</style>
