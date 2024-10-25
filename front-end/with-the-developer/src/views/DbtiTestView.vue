<script setup>
import { ref, computed } from 'vue';
import axios from "axios";
import router from "@/router/index.js";

// 질문 리스트
// score => BACKEND 0, FRONTEND 1, PM 2, DESIGNER 3
const questions = ref([
  { question: "동물모양 쿠키 반죽을 만들던 도중, 모양이 이상한 반죽을 발견한다. 이때, 나의 행동은?",
    answers: ["이건 아트다! 어쩌면 현대미술에 출품할 수 있을지도?",
        "반죽을 다시 동물답게 만든다. 누가 봐도 고양이여야지!",
      "쿠키는 원래 반듯해야 한다. 모양틀 어디 갔지?",
      "오... 이상하지만 구우면 어차피 맛은 똑같으니 괜찮아!"],
    score: [3, 1, 2, 0]
  },

  { question: "친구가 최신 스마트폰을 자랑한다. 이때, 나의 반응은?",
    answers: ["우와! 홈 화면 진짜 깔끔하다. 색상도 멋지네!",
      "음... 카메라 렌즈는 좀 더 옆으로 조정하면 좋을 텐데.",
      "배터리 수명은? 아무런 정보도 없이 이걸 샀단 말이야?",
      "속도가 얼마나 빨라? 스펙 좀 보여줘봐. 내가 테스트 해볼게."],
    score: [1, 3, 2, 0]
  },

  { question: "새로운 보드게임을 시작하려고 하는데 규칙이 복잡하다. 이때, 나의 행동은?",
    answers: ["규칙이 복잡해도 실제로 해보면 간단하겠지?",
      "게임 규칙을 분석하고, 이걸 노트에 정리해두자.",
      "게임말 색깔이랑 디자인은 너무 예쁜데? 이걸로 내 방 장식할까?",
      "우선 규칙을 읽어보고, 보드 디자인이 직관적인지 확인한다."],
    score: [0, 2, 3, 1]
  },

  { question: "식당에서 음식을 기다리는데 너무 오래 걸린다. 이때, 나의 행동은?",
    answers: ["식당 인테리어를 보며 저 조명과 벽지는 나도 써보고 싶어!",
      "주방에 들어가서 왜 이렇게 시간이 걸리는지 확인한다.",
      "여기 메뉴판 디자인 좀 바꾸면 더 주문하기 쉬울 텐데.",
      "계산서를 요청하며 정확히 몇 분 기다렸는지 기록해둔다."],
    score: [3, 0, 1, 2]
  },

  { question: "집에서 영화를 보다가 화면이 갑자기 꺼졌다. 이때, 나의 행동은?",
    answers: ["TV를 다시 켜보고 전원코드를 확인한다. 뭐가 문제일까?",
      "리모컨 배터리가 문제인가? 이참에 리모컨 버튼 배치를 바꿀 수 있을까?",
      "음... 기술적 문제? 이 상황에 대비한 리모콘이 필요했나?",
      "음... 갑자기 꺼진 화면이 뭔가 감성적이지 않나? 영화의 일부분일지도!"],
    score: [0, 1, 2, 3]
  },

  { question: "길을 걷다가 스마트폰 알림이 왔는데, 화면이 안 켜진다. 이때, 나의 행동은?",
    answers: ["아... 고장인가? 가끔은 이런 불완전함이 멋있어.",
      "화면이 안 켜지네? 스크립트가 멈춘건가?",
      "이럴 줄 알고 예비용 핸드폰을 가지고 다니지!",
      "전원을 껐다 켠다. 리셋은 모든 문제를 해결한다!"],
    score: [3, 1, 2, 0]
  },

  { question: "커피 머신이 고장 났다. 아침 커피를 못 마신다. 이때, 나의 행동은?",
    answers: ["커피 머신의 터치 패널이 반응이 없는 걸까? 인식 범위가 문제 있나?",
      "고장이 나도 이 커피 머신의 디자인은 여전히 멋져. 마치 작품 같아.",
      "커피를 마시지 못하면 능률이 떨어지는데..!!",
      "커피 머신을 열어 내부를 살펴본다. 이건 내가 고칠 수 있어!"],
    score: [1, 3, 2, 0]
  },

  { question: "옷을 사러 갔는데, 옷이 마음에 들지 않는다. 이때, 나의 행동은?",
    answers: ["이 옷은 나의 라이프 스타일에 맞지 않아. 내 옷장을 다시 계획해봐야겠어.",
      "옷의 패턴과 색상이 어울리지 않네. 뭔가 디자인을 바꾸고 싶다.",
      "옷의 품질을 분석하고, 원단과 재봉 상태를 점검한다.",
      "음... 옷은 마음에 들지 않지만, 옷걸이 디자인은 훌륭해!"],
    score: [2, 1, 0, 3] },
]);

// 현재 질문 인덱스와 사용자 답변 저장소
const currentQuestionIndex = ref(0);
const userAnswers = ref(Array(8).fill(null));
const userAnswersDbti = ref(Array(8).fill(null));

// 가장 많은 DBTI 답변
const mostDbti = ref('');

// 마지막 질문 여부
const isLastQuestion = computed(() => currentQuestionIndex.value === questions.value.length - 1);

// 모든 질문이 답변되었는지 확인
const allQuestionsAnswered = computed(() => userAnswers.value.every(answer => answer !== null));

// 다음 질문으로 이동
const nextQuestion = () => {

  if (currentQuestionIndex.value < questions.value.length - 1) {
    currentQuestionIndex.value++;
  }
};

// 이전 질문으로 이동
const prevQuestion = () => {
  if (currentQuestionIndex.value > 0) {
    currentQuestionIndex.value--;
  }
};

// 테스트 완료
const completeQuiz = () => {
  if (allQuestionsAnswered.value) {
    getRandomMostFrequentNumber(userAnswersDbti.value);
    const dbtiResult = mostDbti.value;
    router.push({ name: 'ResultPage', params: { result: dbtiResult } }); // 결과 페이지로 이동

  } else {
    alert("모든 질문에 답변을 선택해야 합니다.");
  }
};

// 사용자 답변 및 추가 데이터 업데이트
const updateAnswer = (index, answer, selected) => {
  userAnswers.value[index] = answer; // 첫 번째 값 업데이트 (사용자 답변)
  // 현재 인덱스의 질문이 존재하는지 확인
  const currentQuestion = questions.value[index];
  if (currentQuestion) {
    userAnswersDbti.value[index] = currentQuestion.score[selected]; // 질문이 존재하면 추가 데이터 업데이트
  } else {
    console.error(`인덱스 ${index}의 질문이 정의되지 않았습니다.`);
  }
  // 선택한 답변 확인
  console.log(userAnswers.value);
  console.log(userAnswersDbti.value)
};

// 숫자 가장 많이 나온거에 따른 DBTI
function getRandomMostFrequentNumber(arr) {
  // 숫자의 출현 횟수를 저장할 객체
  const countMap = {};

  // 배열 내 각 숫자에 대한 카운트를 세기
  arr.forEach(num => {
    countMap[num] = (countMap[num] || 0) + 1;
  });

  let maxCount = 0;
  const mostFrequentNumbers = [];

  // 카운트 맵을 순회하여 가장 많이 나타난 숫자 찾기
  for (const num in countMap) {
    if (countMap[num] > maxCount) {
      maxCount = countMap[num];
      mostFrequentNumbers.length = 0; // 가장 많이 나타난 숫자를 초기화
      mostFrequentNumbers.push(Number(num)); // 숫자로 변환하여 추가
    } else if (countMap[num] === maxCount) {
      mostFrequentNumbers.push(Number(num)); // 같은 최대 갯수 추가
    }
  }

  // 가장 많이 나타나는 숫자가 2개 이상일 경우 랜덤으로 선택
  if (mostFrequentNumbers.length > 1) {
    const randomIndex = Math.floor(Math.random() * mostFrequentNumbers.length);
    return mostFrequentNumbers[randomIndex];
  }

  console.log(mostFrequentNumbers[0]);
  switch (mostFrequentNumbers[0]){
    case 0: mostDbti.value = 'BACKEND'
          break;
    case 1: mostDbti.value = 'FRONTEND'
          break;
    case 2: mostDbti.value = 'PM'
          break;
    case 3: mostDbti.value = 'DESIGNER'
  }
  console.log(mostDbti.value);

}

</script>

<template>
  <div class="quiz-container">
    <div class="question-box">
      <h2>성향 테스트</h2>
      <div class="box">
        <div class="question">
          <p>{{ currentQuestionIndex + 1 }}. {{ questions[currentQuestionIndex].question }}</p>
        </div>

        <!-- 답변 선택 -->
        <div class="answer-box" v-for="(answer, index) in questions[currentQuestionIndex].answers" :key="index">
          <label :class="{ selected: userAnswers[currentQuestionIndex] === answer }" class="custom-radio">
            <input
                type="radio"
                :name="'question' + currentQuestionIndex"
                :checked="userAnswers[currentQuestionIndex] === answer"
                @input="updateAnswer(currentQuestionIndex, answer, index)"
            />
            <span class="radio-text">{{ answer }}</span> <!-- 여기에 답변 내용 출력 -->
          </label>
        </div>
        <!-- 버튼들 -->
        <div class="button-container">
          <div>
            <button @click="prevQuestion" :disabled="currentQuestionIndex === 0">이전</button>
          </div>
          <div>
            <button @click="nextQuestion" :disabled="!userAnswers[currentQuestionIndex] || currentQuestionIndex === 7">다음</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 완료 버튼 -->
    <button class="complete_btn" @click="completeQuiz" v-if="isLastQuestion && allQuestionsAnswered">완료</button>
  </div>
</template>

<style scoped>
.quiz-container {
  text-align: center;
  max-width: 620px;
  margin: auto;
}
.question-box h2{
  color: #3257B5;
}
.box{
  display: flex;
  flex-direction: column;
  background-color: #E6ECFD;
  height: auto;
  border-radius: 10px;
  padding-top: 15px;
  align-items: center;
}
.answer-box {
  margin: 15px 0;
  text-align: left;
  width: 530px;
}
.question p{
  font-size: 20px;
  width: 450px;
}

.button-container {
  margin-top: 20px;
}
.custom-radio {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 50px;
  margin-bottom: 10px;
  border: 2px solid #ccc;
  border-radius: 10px;
  background-color: white;
  cursor: pointer;
  text-align: center;
  transition: background-color 0.3s, border-color 0.3s;
}

input[type="radio"] {
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  margin-right: 10px;
}

input[type="radio"]:checked .radio-text {
  background-color: #3257B5;
  color: white;
}

.radio-text {
  font-size: 15px;
  display: inline-block;
  width: 100%;
  height: 100%;
  line-height: 50px;
  margin-right: 12px;
}

.custom-radio.selected {
  background-color: #719bfa;
  color: white;
  border-color: #617CC2;
  box-shadow: inset 0px 0px 3px #666
}
.button-container{
  display: flex;
  margin-bottom: 20px;
  width: 300px;
  justify-content: space-around;
}
.button-container button, .complete_btn{
  width: 100px;
  height: 35px;
  border: 1px solid #617CC2;
  border-radius: 5px;
  box-shadow: 0px 0px 5px #444;
  cursor: pointer;
  background-color: white;
}
button:disabled{
  cursor: not-allowed;
  box-shadow: none;
  border: none;
}
.complete_btn{
  margin-top: 3vh;
}
</style>
