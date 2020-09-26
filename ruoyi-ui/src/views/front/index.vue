<template>
  <div class="container" v-loading="loading">
    <h1 class="title">
      <i class="el-icon-loading"></i>Discovery
    </h1>
    <article class="blog-card" v-for="(item,i) in blogs" :key="i">
      <h1 class="card-title" @click="toArticle(i)">{{item.blogName}}</h1>
      <div class="card-title-item">
        <span style="color:#1890ff;">
          <i class="el-icon-edit"></i>
          {{item.createTime}}
        </span>
        <span style="color:#888;">
          <span>&nbsp•&nbsp</span>
          <i class="el-icon-price-tag"></i>
          <span v-for="temp in splitTag(item.tag)" :key="temp">&nbsp{{temp}}&nbsp</span>
        </span>
        <span style="color:#ff3f1a;">
          <span>&nbsp•&nbsp</span>
          <i class="el-icon-view"></i>
          阅读&nbsp{{item.readNum}}&nbsp
        </span>
      </div>
      <div class="card-content" v-html="splitContent(item.contentHtml)"></div>
    </article>
    <div class="card-pagination">
      <pagination
        background
        layout="prev, pager, next"
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script>
import { getBlogList } from "@/api/front/index";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 6,
        searchInfo: null,
      },
      //博客列表
      blogs: [],
    };
  },
  created() {
    if (this.$route.params && this.$route.params.pageNum) {
      this.queryParams.pageNum = Number(this.$route.params.pageNum);
      window.history.pushState(
        "",
        "",
        "/front/page/" + this.queryParams.pageNum
      );
    }
    this.showBlogList();
  },
  methods: {
    showBlogList() {
      getBlogList(this.queryParams).then((response) => {
        this.total = response.total;
        this.blogs = response.rows;
        this.loading = false;
      });
    },
    getList() {
      window.history.pushState(
        "",
        "",
        "/front/page/" + this.queryParams.pageNum
      );
      window.scrollTo(0, 400);
      this.showBlogList();
    },
    splitTag(tag) {
      return tag.split(",");
    },
    splitContent(content) {
      let html = content.replace(/<\/?[^>]*>/g, "").substring(0, 500);
      return html;
    },
    toArticle(i) {
      let blogId = this.blogs[i].blogId;
      // this.$router.push({ path: '/front/article',query:{blogId:blogId}});
      window.location.href = "/front/article" + "?blogId=" + blogId;
    },
  },
  watch: {
    $route(to, from) {
      this.queryParams.pageNum = Number(this.$route.params.pageNum);
      this.showBlogList();
    },
  },
};
</script>
<style lang="scss" scoped>
.container {
  width: calc(100% - 450px);
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
  // border: 1px solid #a9a9b354;
  margin: 0 auto;
  margin-bottom: 40px;
  border-radius: 5px;
  height: 250px;
  box-shadow: 0 1px 20px -6px rgba(0, 0, 0, 0.5);
  &:hover {
    box-shadow: 0 1px 20px 1px rgba(0, 0, 0, 0.5);
  }
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
  cursor: pointer;
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
}
.card-pagination {
  text-align: center;
}
</style>
<style lang="scss">
.pagination-container {
  padding-bottom: 30px !important;
}
.el-pagination {
  position: unset !important;
}
.el-pagination .btn-prev,
.el-pagination .btn-next,
.el-pagination .el-pager li {
  border-radius: 50% !important;
}
</style>