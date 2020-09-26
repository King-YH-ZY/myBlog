<template>
  <div class="container">
    <div class="overlay"></div>
    <div class="home-content">
      <div class="home-content-div">
        <div class="home-avatar">
          <div class="home-avatar-div">
            <img src="@/assets/logo/小鸽鸽鸭01.gif" alt="logo" />
          </div>
        </div>
        <div class="home-title">
          <H1 class="author">会长丶临死前 BLOG</H1>
          <p class="description">一切都是最好的安排</p>
          <el-row>
            <i class="el-icon-s-promotion toBlog" title="进入博客" @click="toBlog()"></i>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {};
  },
  methods: {
    toBlog(){
      this.$router.push({ path:  "/front" });
    }
  },
};
</script>

<style  lang="scss" scoped>
@import "~@/assets/styles/home.scss";
.container {
  background: url("../assets/home_images/back.jpg") no-repeat;
}
</style>