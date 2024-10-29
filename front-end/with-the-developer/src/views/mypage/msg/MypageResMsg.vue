<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import MypageSideBar from "@/components/MypageSideBar.vue";
import MypageMsgMenu from "@/components/MypageMsgMenu.vue";
import router from "@/router";

    const msgList = ref([]);
    const selectedList = ref([]);

    const fetchResMsgList = async () => {
      return (await axios.get('msg/res/unread')).data;
    }

    onMounted(async () => {
      msgList.value = await fetchResMsgList();
    });

    // 전체 선택/해제 기능
    const toggleSelectAll = (event) => {
      if (event.target.checked) {
        // 모든 메시지의 msgCode를 selectedList에 추가
        selectedList.value = msgList.value.map(msg => msg.msgCode);
      } else {
        // 선택 해제 시 selectedList를 빈 배열로 초기화
        selectedList.value = [];
      }
    };

    // 읽음 확인 후 선택된 메시지를 삭제하는 메서드
    const confirmReadSelectedMessages = () => {
      // 읽음 확인 alert
      const confirmed = window.confirm('선택한 메시지를 읽음 처리 하시겠습니까?');

      if (confirmed) {
        readSelectedMessages(); // 확인 시 삭제 메서드 호출
      }
    };
    // 선택된 메시지를 읽음 처리 하는 메서드
    const readSelectedMessages = async () => {
      try {
        const readPromises = selectedList.value.map(msgCode => {
          return axios.put(`msg/${msgCode}`, [],{
            headers: {
              Authorization: `${localStorage.getItem('accessToken')}`,
            }
          });
        });

        await Promise.all(readPromises);

        // 읽음 후 msgList와 selectedList 업데이트
        msgList.value = await fetchResMsgList();
        selectedList.value = [];
        alert('선택된 메시지가 읽음 처리 되었습니다.');
      } catch (error) {
        console.error('메시지 읽음 처리 중 오류 발생:', error);
        alert('메시지 읽음 처리에 실패했습니다.');
      }
    };

    // 삭제 확인 후 선택된 메시지를 삭제하는 메서드
    const confirmDeleteSelectedMessages = () => {
      // 삭제 확인 alert
      const confirmed = window.confirm('선택한 메시지를 삭제하시겠습니까?');

      if (confirmed) {
        deleteSelectedMessages(); // 확인 시 삭제 메서드 호출
      }
    };
    // 선택된 메시지를 삭제하는 메서드
    const deleteSelectedMessages = async () => {
      try {
        const deletePromises = selectedList.value.map(msgCode => {
          return axios.delete(`msg/receiver/${msgCode}`, {
            headers: {
              Authorization: `${localStorage.getItem('accessToken')}`,
            }
          });
        });

        await Promise.all(deletePromises);

        // 삭제 후 msgList와 selectedList 업데이트
        msgList.value = await fetchResMsgList();
        selectedList.value = [];
        alert('선택된 메시지가 삭제되었습니다.');
      } catch (error) {
        console.error('메시지 삭제 중 오류 발생:', error);
        alert('메시지 삭제에 실패했습니다.');
      }
    };
    const moveToDetail = (msgDetailCode) => {
      router.push({ name : 'resMsgDetail' , params: { msgCode : msgDetailCode } })
    };
    const modalState = ref(false);
    const targetUserId = ref({});
    const targetUserCode = ref({});
    const msgText = ref("");

    const openModal = (userId, userCode) => {
      modalState.value = true;
      targetUserId.value = userId;
      targetUserCode.value = userCode;
    }
    const closeModal = () => {
      modalState.value = false;
      msgText.value = "";
    }
    const confirmSendMsg = () =>{
      const confirmed = window.confirm('메세지를 전송하시겠습니까?');

      if (confirmed) {
        requestMsg(); // 확인 시 삭제 메서드 호출
      }
    }
    const requestMsg = async () => {
      try{
        await axios.post('msg',{
          recipientUserCode : targetUserCode.value,
          msgContent : msgText.value
        },{
          headers: {
            Authorization: `${localStorage.getItem('accessToken')}`,
          }
        });
        closeModal();
        alert("메세지 전송 완료!");
        await fetchResMsgList();
      } catch (error){
        console.error('메시지 전송 중 오류 발생:', error);
        alert("메세지 전송에 실패했습니다.");
      }
    }

//     return {
//       msgList,
//       selectedList,
//       modalState,
//       targetUserId,
//       targetUserCode,
//       msgText,
//       toggleSelectAll,
//       moveToDetail,
//       confirmDeleteSelectedMessages,
//       confirmReadSelectedMessages,
//       openModal,
//       closeModal,
//       requestMsg,
//       confirmSendMsg
//     };
//   }
// }

</script>

<template>
  <div id="requestMsgModal" v-if="modalState">
    <div id="frame">
      <div id="modal_header">
        <p>쪽지 작성</p>
      </div>
      <div id="modal_info">
        <p>받는사람</p><p>{{ targetUserId }}</p>
      </div>
      <textarea id="modal_content" placeholder="내용을 입력해 주세요." v-model="msgText"/>
      <div id="buttons">
        <button id="submit" class="modal_button" @click="confirmSendMsg">전송</button>
        <button class="modal_button" @click="closeModal">닫기</button>
      </div>
    </div>
  </div>
  <section>
    <MypageSideBar/>
    <div id="content">
      <MypageMsgMenu/>
      <article id = "info">
        <div id="tool_bar">
          <input type="checkbox" id="selectAll" @change="toggleSelectAll($event)">
          <p id="read" @click="confirmReadSelectedMessages">읽음</p>
          <p id="delete" @click="confirmDeleteSelectedMessages">삭제</p>
        </div>
        <hr>
        <div class="resMsg" v-for="msg in msgList" :key="msg.msgCode">
          <input type="checkbox" v-model="selectedList" :value="msg.msgCode">
          <p class="msg_content" @click="moveToDetail(msg.msgCode)">{{msg.msgContent}}</p>
          <p class="msg_date">{{ msg.createdDate }}</p>
          <p class="msg_id">{{ msg.userCode }}</p>
          <button class="send_button" @click="openModal(msg.userId, msg.userCode)">답장</button>
        </div>

      </article>
    </div>
  </section>

</template>

<style scoped>
section{
  display: flex;
  height: 700px;
}
#content{
  margin: 35px 0 0 100px;
}
#info{
  width: 600px;
  height: 500px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 1px 1px 1px 1px lightgray;
}

#tool_bar{
  display: flex;
  padding-left: 10px;
  margin-bottom: 0;
}
hr{
  margin: 0;
}
#read{
  color: #7A7A7A;
  margin-right: 10px;
  margin-left: 20px;
}
#read, #delete:hover{
  cursor: pointer;
}
#delete{
  color: #FF3838;
  margin-right: 10px;
  margin-left: 10px;
}
.resMsg{
  display: flex;
  padding-left: 10px;
  font-size: 14px;
  border-bottom: 1px solid lightgray;
}
.resMsg > p{
  margin-left : 20px;
  margin-right: 10px;
}
[type="checkbox"]{
  width: 17px;
}
.send_button{
  font-family: "Neo둥근모 Pro";
  width: 50px;
  height: 20px;
  margin-top: 10px;
  margin-left: 10px;
  border: 1px solid lightgray;
  border-radius: 15px;
}
.send_button:hover{
  border-color: #617CC2;
  color: #617CC2;
}
.msg_content{
  width: 200px;
  cursor: pointer;
}
#requestMsgModal{
  width: auto;
  height: auto;
  position: absolute;
  left: 40%;
}
#frame{
  width: 500px;
  height: 300px;
  border: 1px solid black;
  background-color: white;
}
#modal_header{
  height: 50px;
  font-size: 20px;
  color: white;
  background-color: #617CC2;
  border-bottom: 1px solid lightgray;
  margin: 0;
}
#modal_header > p {
  padding: 15px;
  margin: 0;
  align-content: center;
}
#modal_info{
  display: flex;
  height: 40px;
  border-bottom: 1px solid black;
}
#modal_info > p{
  margin: 10px;
  padding: 0;
}
#modal_content{
  margin: 10px;
  height: 150px;
  width: 470px;
  border: 1px solid lightgray;
  resize: none;

}
#buttons{
  display: flex;
  float: right;
}
.modal_button{
  width: 60px;
  height: 25px;
  border: 1px solid lightgray;
  border-radius: 15px;
}
#submit{
  background-color: #1b5ac2;
  color: white;
}
*,
*::before,
*::after {
  box-sizing: border-box;
}
</style>