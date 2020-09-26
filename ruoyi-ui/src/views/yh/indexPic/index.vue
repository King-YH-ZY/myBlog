<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="图片名称" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入图片名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['yh:indexPic:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['yh:indexPic:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['yh:indexPic:remove']"
        >删除</el-button>
      </el-col>
      <div class="top-right-btn">
        <el-tooltip class="item" effect="dark" content="刷新" placement="top">
          <el-button size="mini" circle icon="el-icon-refresh" @click="handleQuery" />
        </el-tooltip>
        <el-tooltip
          class="item"
          effect="dark"
          :content="showSearch ? '隐藏搜索' : '显示搜索'"
          placement="top"
        >
          <el-button size="mini" circle icon="el-icon-search" @click="showSearch=!showSearch" />
        </el-tooltip>
      </div>
    </el-row>

    <el-table v-loading="loading" :data="picList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50" align="center">
        <template slot-scope="scope">
          <span>{{(queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column label="图片名称" align="center" prop="fileName" />
      <el-table-column label="图片" align="center" prop="fileUrl" width="400">
        <template slot-scope="scope">
          <img :src="getImgUrl(scope.row.fileUrl)" width="400" height="100" />
        </template>
      </el-table-column>
      <el-table-column label="图片状态" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="1"
            inactive-value="0"
            @change="handleSwitch(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['yh:indexPic:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['yh:indexPic:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="750px"
      append-to-body
      @opened="modalOpened"
      :before-close="cancel"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="图片名称" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入图片名称" />
        </el-form-item>
        <el-form-item label="启用">
          <el-switch v-model="form.status" active-value="1" inactive-value="0"></el-switch>
        </el-form-item>
      </el-form>
      <el-row>
        <el-col :xs="24" :md="24" :style="{height: '200px'}">
          <vue-cropper
            ref="cropper"
            :img="options.img"
            :info="true"
            :autoCrop="options.autoCrop"
            :autoCropWidth="options.autoCropWidth"
            :autoCropHeight="options.autoCropHeight"
            :fixedBox="options.fixedBox"
            :full="options.full"
            @realTime="realTime"
            v-if="visible"
          />
        </el-col>
        <el-col :xs="24" :md="24" :style="{height: '150px'}">
          <div class="upload-preview">
            <img :src="previews.url" :style="previews.img" />
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col :lg="2" :md="2">
          <el-upload
            action="#"
            :http-request="requestUpload"
            :show-file-list="false"
            :before-upload="beforeUpload"
          >
            <el-button size="medium">
              上传
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-plus" size="medium" @click="changeScale(1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-minus" size="medium" @click="changeScale(-1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-left" size="medium" @click="rotateLeft()"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-right" size="medium" @click="rotateRight()"></el-button>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listPic,
  getPic,
  delPic,
  addPic,
  updatePic,
  uploadPic,
  updatePicStatus,
} from "@/api/yh/indexPic";
import { VueCropper } from "vue-cropper";

export default {
  name: "IndexPic",
  components: { VueCropper },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 标签表格数据
      picList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fileName: null,
      },
      // 是否显示cropper
      visible: false,
      previews: {},
      form: {},
      switchStatus: true,
      options: {
        img: "", //裁剪图片的地址
        autoCrop: true, // 是否默认生成截图框
        autoCropWidth: 400, // 默认生成截图框宽度
        autoCropHeight: 100, // 默认生成截图框高度
        fixedBox: false, // 固定截图框大小 不允许改变
        full:true, //输出原图比例截图
      },
      // 表单校验
      rules: {
        fileName: [
          { required: true, message: "图片名称不能为空", trigger: "blur" },
          {
            min: 1,
            max: 30,
            message: "长度在 1 到 30 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询博客列表 */
    getList() {
      this.loading = true;
      listPic(this.queryParams).then((response) => {
        this.picList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加图片";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const id = row.id || this.ids[0];
      this.reset();
      getPic(id).then((response) => {
        this.title = "修改图片";
        this.form = response.data.indexPic;
        this.options.img =
          process.env.VUE_APP_BASE_API + response.data.indexPic.fileUrl;
        this.open = true;
      });
    },
    handleSwitch(row) {
      var that = this;
      let temp = {
        id: row.id,
        status: row.status,
      };
      if (row.status == "0") {
        updatePicStatus(temp).then((response) => {
          that.msgSuccess("轮播图关闭成功");
        });
      } else if (row.status == "1") {
        updatePicStatus(temp).then((response) => {
          that.msgSuccess("轮播图启用成功");
        });
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.uploadImg();
        }
      });
    },
    // 取消按钮
    cancel() {
      this.visible = false;
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        fileName: "",
        status: "0",
      };
      this.options.img = "";
      this.resetForm("form");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      var that = this;
      const ids = row.id || this.ids;
      that
        .$confirm("是否确认删除选中的图片?", "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
        .then(function () {
          return delPic(ids);
        })
        .then(() => {
          that.getList();
          that.msgSuccess("删除成功");
        })
        .catch(function (res) {
          console.log(res);
        });
    },
    // 打开弹出层结束时的回调
    modalOpened() {
      this.visible = true;
    },
    // 覆盖默认的上传行为
    requestUpload() {},
    // 向左旋转
    rotateLeft() {
      this.$refs.cropper.rotateLeft();
    },
    // 向右旋转
    rotateRight() {
      this.$refs.cropper.rotateRight();
    },
    // 图片缩放
    changeScale(num) {
      num = num || 1;
      this.$refs.cropper.changeScale(num);
    },
    // 上传预处理
    beforeUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.options.img = reader.result;
        };
      }
    },
    // 上传图片
    uploadImg() {
      this.$refs.cropper.getCropBlob((data) => {
        let formData = new FormData();
        formData.append("file", data);
        uploadPic(formData).then((response) => {
          if (response.code === 200) {
            this.form.fileUrl = response.fileUrl;
            if (this.form.id != null && this.form.id != "") {
              updatePic(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addPic(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                }
              });
            }
          }
          this.visible = false;
        });
      });
    },
    // 实时预览
    realTime(data) {
      this.previews = data;
    },
    getImgUrl(fileUrl) {
      var url = process.env.VUE_APP_BASE_API + fileUrl;
      return url;
    },
  },
};
</script>

<style lang="scss" scoped>
.upload-preview {
  -webkit-transform: translate(0%, 10%);
  transform: translate(0%, 10%);
  width: 400px;
  height: 100px;
  -webkit-box-shadow: 0 0 4px #ccc;
  box-shadow: 0 0 4px #ccc;
  overflow: hidden;
}
</style>
