<template>
  <div class="app-wrapper">
    <el-backtop target=".app-wrapper" :visibility-height="100" :bottom="60"></el-backtop>
    <div class="main-container">
      <el-container>
        <front-header />
        <el-main>
          <el-carousel :interval="4000" indicator-position="none" :height="bannerHeight + 'px'">
            <el-carousel-item v-for="item in img_list" :key="item" class="carousel-item">
              <img class="carousel-img" :src="item" alt />
            </el-carousel-item>
          </el-carousel>
          <app-main />
        </el-main>
        <el-footer>
          <p>Copyright © 2019-2020 会长丶临死前 All Rights Reserved</p>
          <el-link :underline="false" type="info">
            <a href="/login">后台管理</a>
          </el-link>
        </el-footer>
      </el-container>
    </div>
  </div>
</template>


<script>
import { AppMain, FrontHeader } from "./components";
import image1 from "@/assets/image/login-background.jpg";
import { getIndexPic } from "@/api/front/index";

export default {
  name: "Front",
  components: { AppMain, FrontHeader },
  data() {
    return {
      img_list: [image1],
      // 图片父容器高度
      bannerHeight: 500,
      // 浏览器宽度
      screenWidth: 0,
    };
  },
  created() {
    this.getImages();
  },
  mounted() {
    // 首次加载时,需要调用一次
    this.screenWidth = window.innerWidth;
    this.setSize(); // 窗口大小发生改变时,调用一次
    window.onresize = () => {
      this.screenWidth = window.innerWidth;
      this.setSize();
    };
  },
  computed: {},
  methods: {
    setSize: function () {
      // 通过浏览器宽度(图片宽度)计算高度
      this.bannerHeight = (650 / 2560) * this.screenWidth;
      if (this.bannerHeight > 650) this.bannerHeight = 650;
      if (this.bannerHeight < 250) this.bannerHeight = 250;
    },
    getImages() {
      getIndexPic().then((response) => {
        if (response.code == 200) {
          let temp = [];
          let data = response.data;
          if (response.data.length > 0) {
            for (let i = 0, len = data.length; i < len; i++) {
              console.log(data[i]);
              temp.push(process.env.VUE_APP_BASE_API + data[i].fileUrl);
            }
            this.img_list = temp;
          }
        }
      });
    },
  },
};
</script>


<style lang="scss" scoped>
.app-wrapper {
  position: relative;
  width: 100%;
  height: 100vh; // 不必是100vh，只需要是该容器显示的最大高度即可
  overflow-x: hidden;
}
.main-container {
  margin: 0 0 !important;
}

.el-footer {
  background-color: #fff;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.el-footer {
  background: #232323;
  padding: 10px;
  text-align: center;
  color: #888;
  font-size: 14px;
  line-height: 28px;
  height: auto !important;
}
.el-footer p {
  margin: 0;
}
.el-main {
  // background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 160px;
  padding: 0;
  margin-top: 60px;
  margin-bottom: 40px;
}

.carousel-item {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
}
.carousel-img {
  width: 100%;
  height: auto;
  min-height: 250px;
}
</style>
