<template>
  <div class="container" v-loading="loading">
    <h1 class="title">
      <i class="el-icon-loading"></i>TimeLine
    </h1>
    <div v-if="timeline">
      <article class="blog-card">
        <el-timeline>
          <el-timeline-item
            v-for="(activity, index) in timeline"
            :key="index"
            :timestamp="activity.createTime"
            placement="top"
          >
            <h3
              v-for="(temp,i) in stringToArr(activity.blogNames)"
              :key="i"
              v-html="getContent(temp)"
              class="blog-name"
            ></h3>
          </el-timeline-item>
        </el-timeline>
      </article>
    </div>
    <div v-else>555</div>
  </div>
</template>

<script>
import { getBlogTimeLine } from "@/api/front/index";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      //博客列表
      timeline: [],
    };
  },
  created() {
    this.showTimeLine();
  },
  mounted() {
    // 切换页面时滚动条自动滚动到顶部
    window.scrollTo(0, 0);
  },

  methods: {
    showTimeLine() {
      getBlogTimeLine().then((response) => {
        this.timeline = response.data;
        this.loading = false;
      });
    },
    stringToArr(obj) {
      return (obj || "").split(",");
    },
    getContent(obj) {
      let temp = (obj || "").split("--+--");
      let html =
        "<a href='/front/article?blogId=" + temp[1] + "'>" + temp[0] + "</a>";
      return html;
    },
  },
};
</script>
<style lang="scss" scoped>
.container {
  width: calc(100% - 550px);
  max-width: 1200px;
  min-width: 345px;
  margin: 0 auto;
  text-align: center;
}
.title {
  border-bottom: 1px dashed #a9a9b354;
  margin-top: 20px;
  margin-bottom: 20px;
  line-height: 50px;
  text-align: left;
  color: rgb(169, 169, 179);
  font-size: 1.5rem;
  letter-spacing: 4px;
}
.blog-card {
  padding: 20px 10px;
  width: 100%;
  margin: 0 auto;
  margin-bottom: 40px;
  border-radius: 5px;
  height: auto;
  box-shadow: 0 1px 20px -6px rgba(0, 0, 0, 0.5);
}
.blog-name {
  line-height: 18px;
  color: rgb(24, 144, 255);
  &:hover {
     transition: all 0.6s ease;
    transform: translateX(15px);
    -webkit-transform: translateX(15px); /*兼容-webkit-引擎浏览器*/
    -moz-transform: translateX(15x);

  }
}
.el-timeline {
  text-align: left;
}
</style>
