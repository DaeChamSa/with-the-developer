<template>
  <div class="content">
    <h3 class="header">신고 작성</h3>
    <table class="form-table">
      <tr>
        <td><label>신고 게시글</label></td>
        <td><input type="text" v-model="blockPostSub" class="input-blocktext"/></td>
        </tr>
      <tr>
        <td><label>신고 사유</label></td>
        <td>
          <select v-model="blockReason" class="input-select">
            <option value="선택">선택</option>
            <option value="비난, 날조">비난, 날조</option>
            <option value="욕설">욕설</option>
            <option value="스팸">스팸</option>
          </select>
        </td>
      </tr>
    </table>

    <div class="form-group">
      <label>신고 상세 사유</label>
      <textarea v-model="blockDetails" rows="4" class="input-textarea"></textarea>
    </div>

    <div class="form-group buttons">
      <button @click="submitBlock" class="btn-submit">보내기</button>
      <button @click="resetForm" class="btn-cancel">취소</button>
    </div>
  </div>
</template>

<script>
import {ref} from "vue";

export default {
  setup() {
    const blockPostSub = ref(''); //신고 게시글
    const blockReason = ref(''); //신고사유
    const blockDetails = ref(''); //신고상세사유

    const submitBlock = () => {
      console.log({
        blockPostSub: blockPostSub.value,
        blockReason: blockReason.value,
        blockDetails: blockDetails.value,
      });
    };

    const resetForm = () => {
      blockPostSub.value = "";
      blockReason.value = "선택";
      blockDetails.value = "";
    };

    return {
      blockPostSub,
      blockReason,
      blockDetails,
      submitBlock,
      resetForm,
    };
  },
};
</script>

<style scoped>
.content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 600px;
  margin: 50px auto;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.header {
  background-color: #486db5;
  color: white;
  padding: 10px;
  text-align: center;
  font-size: 18px;
}

.form-table {
  width: 100%;
  margin-bottom: 20px;
  border-collapse: collapse;
}

.form-table td {
  padding: 10px;
  vertical-align: top;
}

.input-blocktext, .input-select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ced4da;
  border-radius: 4px;
}

.input-textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #ced4da;
  border-radius: 4px;
}

.buttons {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.btn-submit {
  background-color: #486db5;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-cancel {
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
