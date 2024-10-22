<script setup>

</script>

<template>
  <main>
    <article id="menu_area">
      <a href="#">
        <div class="menu_area_item">
          <p class="button_title">팀모집</p>
          <img src="https://img.icons8.com/?size=100&id=24945&format=png&color=000000" alt="팀모집아이콘">
          <p class="button_subtitle">새로운 팀을 모집하거나 <br> 지원해보세요!</p><br>
        </div>
      </a>
      <a href="#">
        <div class="menu_area_item">
          <p class="button_title">프로젝트</p>
          <img src="https://img.icons8.com/?size=100&id=VuNO5eSHIj35&format=png&color=000000" alt="프로젝트아이콘">
          <p class="button_subtitle">자신의 프로젝트를 <br> 자랑해보세요!</p><br>
        </div>
      </a>
      <a href="#">
        <div class="menu_area_item">
          <p class="button_title">굿즈</p>
          <img src="https://img.icons8.com/?size=100&id=x9gD2am0uGsn&format=png&color=000000" alt="팀모집아이콘">
          <p class="button_subtitle">다양한 굿즈를 <br> 구매해보세요!</p><br>
        </div>
      </a>
      <a href="#">
        <div class="menu_area_item">
          <p class="button_title">채용공고</p>
          <img src="https://img.icons8.com/?size=100&id=14356&format=png&color=000000" alt="팀모집아이콘">
          <p class="button_subtitle">다양한 채용공고를 <br> 확인하세요!</p><br>
        </div>
      </a>
      <a href="#">
        <div class="menu_area_item">
          <p class="button_title">커뮤니티</p>
          <img src="https://img.icons8.com/?size=100&id=94518&format=png&color=000000" alt="팀모집아이콘">
          <p class="button_subtitle">다양한 사람들과  <br> 정보를 공유하세요!</p><br>
        </div>
        </a>
      <a href="#">
        <div class="menu_area_item">
          <p class="button_title">북마크</p>
          <img src="https://img.icons8.com/?size=100&id=82461&format=png&color=000000" alt="팀모집아이콘">
          <p class="button_subtitle">다시 보고 싶은 컨텐츠를 <br> 저장하세요!</p><br>
        </div>
      </a>

    </article>
    <article id="content_area">
      <div>
        <div class="content_type">
          <img src="https://img.icons8.com/?size=100&id=14356&format=png&color=000000" alt="팀모집아이콘">
          <p>최신 채용공고</p>
        </div>
        <hr>
      </div>
      <div>
        <div class="content_type">
          <img src="https://img.icons8.com/?size=100&id=24945&format=png&color=000000" alt="팀모집아이콘">
          <p>최신 팀모집</p>
        </div>
        <hr>
      </div>
      <div>
        <div class="content_type">
          <img src="https://img.icons8.com/?size=100&id=VuNO5eSHIj35&format=png&color=000000" alt="팀모집아이콘">
          <p>최신 프로젝트자랑</p>
        </div>
        <hr>
      </div>
      <div>
        <div class="content_type">
          <img src="https://img.icons8.com/?size=100&id=94518&format=png&color=000000" alt="팀모집아이콘">
          <p>최신 커뮤니티</p>
        </div>
        <hr>
      </div>
    </article>
  </main>
</template>

<style scoped>
  #menu_area{
    display: grid;
    height: 520px;
    background-color: #F5F5F5;
    grid-template-columns: repeat(3, 250px);
    grid-template-rows: repeat(2, 250px);
    column-gap: 50px;
    justify-content: center;
    }
  #content_area{
    display: grid;
    height: 600px;
    grid-template-columns: 500px 500px;
    grid-template-rows: 300px 300px;
    column-gap: 50px;
    justify-content: center;
  }
  .menu_area_item {
    background-color: white;
    color : black;
    padding : 10px;
    margin : 20px;
    border: 1px solid #B0ADAD;
    border-radius: 20px;
    text-align: center;
  }
  .button_title{
    font-size: 25px;
    color: #014995;
    width: 100%;
  }
  .button_subtitle{
    color: #7E7E7E;
  }
  .menu_area_item > img {
    width: 50px;
    height: 50px;
  }
  .content_type{
    display: flex;
    align-items: center;
    font-size: 20px;
  }
  .content_type > img {
    width: 25px;
    height: 25px;
    margin-right: 10px;
  }
  hr{
    border: 1px solid;
    margin: 0;
  }
  a {
    text-decoration-line: none;
  }
</style>