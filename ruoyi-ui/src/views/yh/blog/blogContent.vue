<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="文章标题" prop="blogName">
        <el-input v-model="form.blogName"></el-input>
      </el-form-item>
      <el-form-item label="文章状态">
        <el-radio-group v-model="form.status">
          <el-radio label="0">暂存</el-radio>
          <el-radio label="1">仅个人可见</el-radio>
          <el-radio label="2">公开</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="标签" prop="tag">
        <el-tag
          :key="index"
          v-for="(tag,index) in dynamicTags"
          :effect="tag.active?'dark':'plain'"
          :disable-transitions="false"
          @click.native="handleClickTag(index)"
          style="cursor: pointer;"
        >{{tag.name}}</el-tag>
        <el-input
          class="input-new-tag"
          v-if="inputVisible"
          v-model="inputValue"
          ref="saveTagInput"
          size="small"
          @keyup.enter.native="handleInputConfirm"
          @blur="handleInputConfirm"
        ></el-input>
        <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
      </el-form-item>
      <div id="main">
        <mavon-editor
          v-model="blogContent.content"
          ref="md"
          :codeStyle="code_style"
          @imgAdd="$imgAdd"
          @change="change"
          @imgDel="$imgDel"
          @save="saveBlogContent"
          style="height: 800px"
        ></mavon-editor>
      </div>
      <el-form-item style="text-align: center;margin-left:-100px;margin-top:20px;">
        <el-button type="primary" @click="saveBlog()">保存</el-button>
        <el-button @click="close()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import {
  addBlog,
  updateBlog,
  addImage,
  delImage,
  getBlog,
  getAllTags,
  getAllTagsByBlogId,
} from "@/api/yh/blog";
import { listTag } from "@/api/yh/blogTag";
import { mavonEditor } from "mavon-editor";
import "mavon-editor/dist/css/index.css";

export default {
  // name: "BlogContent",
  components: {
    mavonEditor,
  },
  data() {
    var that = this;
    var validateTag = (rule, value, callback) => {
      if (that.chooseTag.length > 0) {
        callback();
      } else {
        callback(new Error("请选择最少一个标签"));
      }
    };
    return {
      code_style: "monokai-sublime",
      //遮罩层
      loading: true,
      // 表单参数
      form: {
        blogId: null,
        blogName: "",
        status: "0",
      },
      blogContent: {
        //文章内容
        content: "",
        //文章转HTML内容
        contentHtml: "",
      },
      // 表单校验
      rules: {
        blogName: [
          { required: true, message: "文章标题不能为空", trigger: "blur" },
          {
            min: 1,
            max: 50,
            message: "长度在 1 到 50 个字符",
            trigger: "blur",
          },
        ],
        tag: [{ validator: validateTag, trigger: ["blur", "change"] }],
      },

      //标签
      dynamicTags: [],
      inputVisible: false,
      inputValue: "",
      //以选中的标签
      chooseTag: [],
    };
  },
  created() {
    const blogId = this.$route.params && this.$route.params.blogId;
    if (blogId) {
      this.form.blogId = blogId;
      getBlog(blogId)
        .then((res) => {
          if (res.code == 200) {
            this.form = res.data.blog;
            this.blogContent = res.data.blogContent;
          } else {
            this.$message.error("数据初始化失败！");
          }
        })
        .catch((err) => {
          console.log(err);
        });
      getAllTagsByBlogId(blogId)
        .then((res) => {
          if (res.code == 200) {
            let data = res.data;
            this.dynamicTags = data;
            data.forEach(element => {
              if(element.active){
                this.chooseTag.push(element.name);
              }
            });
          } else {
            this.$message.error("标签数据初始化失败！");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    } else {
      getAllTags()
        .then((res) => {
          if (res.code == 200) {
            this.dynamicTags = res.data;
          } else {
            this.$message.error("标签数据初始化失败！");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  },
  methods: {
    //点击保存
    saveBlog() {
      this.saveForm();
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/yh/blog", query: { t: Date.now() } });
    },
    //markdown保存
    saveBlogContent(value, render) {
      this.blogContent.contentHtml = render;
      this.saveForm();
    },
    saveForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          var tag = this.chooseTag.join(",");
          this.form.tag = tag;
          let blog = { yhBlog: this.form, yhBlogContent: this.blogContent };
          if (this.form.blogId != null) {
            updateBlog(blog)
              .then((res) => {
                if (res.code == 200) {
                  this.form = res.data.yhBlog;
                  this.yhBlogContent = res.data.yhBlogContent;
                  this.$message.success("保存成功！");
                } else {
                  this.$message.error("操作失败！");
                }
              })
              .catch((err) => {
                console.log(err);
              });
          } else {
            addBlog(blog)
              .then((res) => {
                if (res.code == 200) {
                  this.form = res.data.yhBlog;
                  this.yhBlogContent = res.data.yhBlogContent;
                  this.$message.success("保存成功！");
                } else {
                  this.$message.error("操作失败！");
                }
              })
              .catch((err) => {
                console.log(err);
              });
          }
        }
      });
    },
    /** 关闭按钮 */
    close() {
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/yh/blog", query: { t: Date.now() } });
    },
    // 将图片上传到服务器，返回地址替换到md中
    $imgAdd(pos, $file) {
      let formdata = new FormData();
      formdata.append("file", $file);
      addImage(formdata)
        .then((res) => {
          this.$refs.md.$img2Url(pos, res.url);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    $imgDel(pos) {
      let formdata = new FormData();
      formdata.append("filePath", pos);
      delImage(formdata)
        .then((res) => {
          if (res) {
            this.$message.success("图片删除成功！");
          } else {
            this.$message.error("图片删除失败！");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 所有操作都会被解析重新渲染
    change(value, render) {
      // render 为 markdown 解析后的结果[html]
      this.blogContent.contentHtml = render;
    },

    /**标签处理 */

    showInput() {
      this.inputVisible = true;
      this.$nextTick((_) => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        inputValue = inputValue.trim();
        if (this.dynamicTags.indexOf(inputValue) != -1) {
          this.$message.error("标签已存在！");
        } else {
          this.dynamicTags.push({ name: inputValue, active: false });
          this.inputVisible = false;
          this.inputValue = "";
        }
      }
    },
    handleClickTag(index) {
      let tag = this.dynamicTags[index];
      let active = !tag.active;
      if (this.chooseTag.length == 3 && tag.active == false) {
        this.$message.error("最多选择三个标签");
      } else {
        this.dynamicTags[index].active = active;
        if (active) {
          this.chooseTag.push(tag.name);
        } else {
          this.chooseTag.some((item, i) => {
            if (item == tag.name) {
              this.chooseTag.splice(i, 1); //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
              return true;
            }
          });
        }
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
