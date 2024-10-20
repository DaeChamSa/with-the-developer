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
        <img src="https://cdn.pixabay.com/photo/2020/08/16/03/30/remote-5491798_1280.png" alt="임시 이미지 1">
      </div>
      <div class="articles-text">
        <div class="small-content"><p>다양한 개발자들과 함께하세요!</p></div>
        <div class="big-content">
          <p>개발자랑에 어서오세요!</p>
          <button id="register">가입하기</button>
        </div>
      </div>
    </article>
    <article class="articles" id="third">
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
    <article class="articles" id="forth">
      <div class="articles-image">
        <img src="https://cdn.pixabay.com/photo/2020/08/16/03/30/remote-5491798_1280.png" alt="임시 이미지 1">
      </div>
      <div class="articles-text">
        <div class="small-content"><p>다양한 개발자들과 함께하세요!</p></div>
        <div class="big-content">
          <p>개발자랑에 어서오세요!</p>
          <button id="register">가입하기</button>
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
    font-size: 30px;
    height: 70%;
    text-align: center;
    margin: 0;
  }
  button {
    width: 100px;
    height: 50px;
    font-family: "Neo둥근모 Pro";
    border: none;
    border-radius: 10px;
    transition: none;
  }
  #register{
    color: white;
    background-color: #9C9C9C;
  }

</style>