<template>
  <div class="container" v-loading="loading">
    <h1 class="title">
      <i class="el-icon-loading"></i>Discovery
    </h1>
    <div v-if="blogs">
      <article class="blog-card">
        <h1 class="card-title">{{blogs.blogName}}</h1>
        <div class="card-title-item">
          <span style="color:#1890ff;">
            <i class="el-icon-edit"></i>
            {{blogs.createTime}}
          </span>
          <span style="color:#888;">
            <span>&nbsp•&nbsp</span>
            <i class="el-icon-price-tag"></i>
            <span v-for="temp in splitTag(blogs.tag)" :key="temp">&nbsp{{temp}}&nbsp</span>
          </span>
          <span style="color:#ff3f1a;">
            <span>&nbsp•&nbsp</span>
            <i class="el-icon-view"></i>
            阅读&nbsp{{blogs.readNum}}&nbsp
          </span>
        </div>
        <div class="card-content">
          <mavon-editor
            v-model="blogs.content"
            codeStyle="monokai-sublime"
            :subfield="false"
            :boxShadow="false"
            defaultOpen="preview"
            :toolbarsFlag="false"
          />
        </div>
      </article>
    </div>
    <div v-else>
      <h1>Not Found</h1>
      <h2>查询不到文章的信息</h2>
    </div>
  </div>
</template>

<script>
import { getBlog } from "@/api/front/index";
import { mavonEditor } from "mavon-editor";
import "mavon-editor/dist/css/index.css";

export default {
  components: {
    mavonEditor,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      //博客列表
      blogs: null,
    };
  },
  created() {
    const blogId = this.$route.params && this.$route.query.blogId;
    if (blogId) {
      this.showBlog(blogId);
    }else{
      this.loading = false;
    }
  },
  mounted() {
    // 切换页面时滚动条自动滚动到顶部
    // console.log(document.documentElement.scrollTop )
    // document.documentElement.scrollTop = 0
    window.scrollTo(0, 0);
  },

  methods: {
    showBlog(blogId) {
      getBlog(blogId).then((response) => {
        this.blogs = response.data;
        this.loading = false;
      });
    },
    splitTag(tag) {
      return (tag || "").split(",");
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
  width: 100%;
  margin: 0 auto;
  margin-bottom: 40px;
  border-radius: 5px;
  height: auto;
  box-shadow: 0 1px 20px -6px rgba(0, 0, 0, 0.5);
}
.card-title {
  margin: 0;
  line-height: 50px;
  font-size: 24px;
  padding-top: 15px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
.card-title-item {
  margin: 0;
  line-height: 24px;
  font-size: 14px;
  margin-bottom: 15px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
.card-content {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  text-indent: 2em;
  line-height: 30px;
  padding: 0 30px;
  text-align: left;
  padding-top: 10px;
  height: 100%;
  padding-bottom: 30px;
}
</style>
