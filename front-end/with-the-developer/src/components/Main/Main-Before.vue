<script setup>

</script>

<template>
  <main>

    <article class="articles" id="first">
      <div class="articles-text">
        <div class="small-content"><p>다양한 개발자들과 함께하세요!</p></div>
        <div class="big-content">
          <p>개발자랑에 어서오세요!</p>
          <button id="register">가입하기</button>
        </div>
      </div>
      <div class="articles-image">
        <img src="https://cdn.pixabay.com/photo/2020/08/16/03/30/remote-5491798_1280.png" alt="임시 이미지 1">
      </div>
    </article>

    <article class="articles" id="second">
      <div class="articles-image">
        <img src="https://cdn.pixabay.com/photo/2019/07/29/12/13/group-4370510_1280.png" alt="임시 이미지 2">
      </div>
      <div class="articles-text">
        <div class="small-content"><p>마음 맞는 개발자들과 함께하세요!</p></div>
        <div class="big-content">
          <p>다양한 팀 모집</p>
          <button id="team">팀 모집</button>
        </div>
      </div>
    </article>

    <article class="articles" id="third">
      <div class="articles-text">
        <div class="small-content"><p>나만의 프로젝트를 공유하세요!</p></div>
        <div class="big-content">
          <p>멋진 프로젝트를 자랑!</p>
          <button id="project">프로젝트 자랑</button>
        </div>
      </div>
      <div class="articles-image">
        <img src="https://cdn.pixabay.com/photo/2016/04/01/09/54/computer-1299659_1280.png" alt="임시 이미지 3">
      </div>
    </article>

    <article class="articles" id="forth">
      <div class="articles-image">
        <img src="https://cdn.pixabay.com/photo/2018/08/25/09/27/shop-3629607_1280.png" alt="임시 이미지 4">
      </div>
      <div class="articles-text">
        <div class="small-content"><p>귀여운 굿즈들을 만나보세요!</p></div>
        <div class="big-content">
          <p>다양한 굿즈!</p>
          <button id="goods">굿즈 구매</button>
        </div>
      </div>
    </article>

  </main>
</template>

<style scoped>
  main{
    font-family: "Neo둥근모 Pro";
  }
  .articles{
    height: 400px;
    display: flex;
    overflow: hidden;
    white-space: nowrap;
  }
  #first{
    background-color: #323232;
    color: white;
  }
  #third{
    background-color: #E5EAFB;
  }
  .articles-text {
    width: 50%;
    justify-content: center;
  }
  .articles-image {
    width: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .articles-image > img {
    width: 350px;
    height: 350px;

  }
  .small-content {
    font-size: 15px;
    height: 30%;
    text-align: center;
    margin: 0;
    align-content: end;
  }
  .big-content {
    font-size: 40px;
    height: 70%;
    text-align: center;
    margin: 0;
  }
  button {
    width: 120px;
    height: 60px;
    font-family: "Neo둥근모 Pro";
    border: none;
    border-radius: 10px;
    transition: none;
    font-size: 17px;
    color: white;
  }
  #register{
    background-color: #9C9C9C;
  }
  #team{
    background-color: #1A63FF;
  }
  #project{
    background-color: #4D62B3;
  }
  #goods{
    background-color: #1A63FF;
  }

</style>