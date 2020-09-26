<template>
  <el-header>
    <a href="/" title="主页">
      <el-image style="width: 60px; height: 60px" :src="logoImg" fit="contain"></el-image>
    </a>
    <el-menu
      :default-active="$route.path"
      :router="true"
      class="el-menu-demo"
      mode="horizontal"
      active-text-color="#1890ff"
    >
      <el-menu-item index="/front">
        <i class="el-icon-s-home"></i>
        <span>首页</span>
      </el-menu-item>
      <el-menu-item index="/front/timeline">
        <i class="el-icon-s-help"></i>
        <span>归档</span>
      </el-menu-item>
      <el-menu-item index="">
        <i class="el-icon-star-off"></i>
        <span>标签</span>
      </el-menu-item>
      <el-menu-item index="">
        <i class="el-icon-s-promotion"></i>
        <span>关于</span>
      </el-menu-item>
    </el-menu>
    <el-input
      placeholder="请输入内容"
      v-model="searchInfo"
      @keyup.enter.native="searchHandler"
      class="searchInput"
    >
      <i slot="prefix" class="el-input__icon el-icon-search"></i>
    </el-input>

    <span class="sideBtn" @click="siderMenu">
      <i class="el-icon-menu"></i>
    </span>
    <el-drawer
      :visible.sync="drawer"
      direction="ltr"
      :show-close="showClose"
      size="40%"
      title
      class="sideMenu"
    >
      <p>
        <a href="/front">首页</a>
      </p>
      <p>
        <a href="/front/timeline">归档</a>
      </p>
      <p>
        <a href="/front">标签</a>
      </p>
      <p>
        <a href="/front">关于</a>
      </p>
    </el-drawer>
  </el-header>
</template>


<script>
import logoImg from "@/assets/logo/小鸽鸽鸭02.gif";

export default {
  name: "FrontHeader",
  data() {
    return {
      activeIndex: "1",
      logoImg: logoImg,
      searchInfo: "",
      drawer: false,
      showClose: false,
    };
  },
  computed: {},
  methods: {
    searchHandler() {
      // console.log(this.searchInfo)
    },
    siderMenu() {
      let show = this.drawer;
      this.drawer = !show;
    },
  },
  mounted() {
  },
};
</script>

<style lang="scss" scoped>
.el-header {
  background-color: #fff;
  color: #333;
  text-align: center;
  line-height: 60px;
  text-align: unset;
  box-shadow: 0 4px 8px 0 rgba(7, 17, 27, 0.1);
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 9999;
}

.el-menu-demo {
  width: 410px;
  display: inline-block;
  margin-left: 10px;
  font-size: 2rem;
}
.searchInput {
  display: inline-block;
  width: 250px;
  float: right;
  &::after {
    clear: both;
    content: "";
  }
}
.sideBtn {
  cursor: pointer;
  display: none;
  font-size: 2rem;
  position: absolute;
  right: 20px;
  color: #1890ff;
}
.sideMenu {
  text-align: center;
  & p {
    margin: 0px;
  }
}

@media screen and (max-width: 768px) {
  .el-menu-demo {
    display: none;
  }
  .searchInput {
    display: none;
  }
  .sideBtn {
    display: inline-block;
  }
}
</style>