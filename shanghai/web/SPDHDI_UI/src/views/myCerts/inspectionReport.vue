<template>
  <div id="inspection-report" class="full-height overflow">
    <transition name="el-fade-in-linear">
      <div v-show="!bAddMode" style="height: 100%;">
        <el-row class="tool-bar">
          <el-input v-model="filter.goodsName" class="input-box" placeholder="请输入名称"></el-input>
          <el-input v-model="filter.goodsSpec" class="input-box" placeholder="请输入规格"></el-input>
          <el-input v-model="filter.lot" class="input-box" placeholder="请输入批号"></el-input>
          <el-input v-model="filter.mfrsName" class="input-box" placeholder="请输入生产厂家"></el-input>
          <el-button @click="listReport(true)" icon="el-icon-search">搜索</el-button>
          <el-button v-if="!bIsHos" @click="initForm" type="primary" icon="el-icon-plus">新增检验单</el-button>
        </el-row>
        <el-row class="table-class">
          <el-table :data="reportsTableData" style="height: 100%;" v-loading="tableLoading" height="css" border fit>
            <el-table-column type="index" width="50" label="序号">
            </el-table-column>
            <el-table-column prop="goodsName" label="产品名称" width="180">
            </el-table-column>
            <el-table-column prop="goodsSpec" label="产品规格" width="180">
            </el-table-column>
            <el-table-column prop="lot" label="批号">
            </el-table-column>
            <el-table-column v-if="bIsHos" prop="provName" label="供应商">
            </el-table-column>
            <el-table-column prop="mfrsName" label="厂家">
            </el-table-column>
            <el-table-column label="操作" width="150" fixed="right" align="center">
              <template slot-scope="scope">
                <el-button-group>
                  <el-button :disabled="!scope.row.imgNum" @click="viewReport(scope.row)" type="success" size="mini" icon="el-icon-picture-outline">附件{{ scope.row.imgNum ? ('('+scope.row.imgNum+')'):'' }}</el-button>
                  <el-button v-if="!bIsHos" @click="deleteReport(scope.row)" type="danger" size="mini" icon="el-icon-delete">删除</el-button>
                </el-button-group>
              </template>
            </el-table-column>
          </el-table>
        </el-row>
        <el-row>
          <el-pagination :current-page="pageInfo.page" :page-size="pageInfo.size" :page-sizes="[30, 50, 100]" :total="pageInfo.total" @current-change="handlePageChange" @size-change="handlePageSizeChange" layout="total, sizes, prev, pager, next" style="float:right;" small>
          </el-pagination>
        </el-row>
      </div>
    </transition>

    <transition name="el-fade-in-linear">
      <div v-show="bAddMode">
        <el-form ref="rptForm" :model="rptFormData" class="el-form-item-nomsg" label-width="100px" label-suffix="：">
          <el-row>
            <el-form-item
              label="生产厂家"
              prop="mfrsName"
              :rules="[
                { required: true, message: '请输入生产厂家名称', trigger: 'blur' },
                { min: 2, max: 512, message: '长度在 2 到 512 个字符', trigger: ['blur', 'change'] }
              ]"
            >
              <el-input v-model="rptFormData.mfrsName" placeholder="请输入生产厂家名称"></el-input>
            </el-form-item>
          </el-row>
          <el-row v-for="(item, index) in rptFormData.reportSubVoList"
                  :key="item.id" type="flex" justify="space-between">
            <el-col :span="7">
              <el-form-item :label="'商品'+(index+1)+'名称'"
                            :prop="'reportSubVoList.' + index + '.goodsName'"
                            :rules="[
                              { required: true, message: '请输入商品名称', trigger: 'blur' },
                              { min: 2, max: 512, message: '长度在 2 到 512 个字符', trigger: ['blur', 'change'] }
                            ]"
              >
                <el-input v-model="item.goodsName" placeholder="请输入商品名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item :label="'商品'+(index+1)+'规格'"
                            :prop="'reportSubVoList.' + index + '.goodsSpec'"
                            :rules="[
                              { required: true, message: '请输入商品规格', trigger: 'blur' },
                              { min: 2, max: 512, message: '长度在 2 到 512 个字符', trigger: ['blur', 'change'] }
                            ]"
              >
                <el-input v-model="item.goodsSpec" placeholder="请输入商品规格"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item :label="'商品'+(index+1)+'批号'"
                            :prop="'reportSubVoList.' + index + '.lot'"
                            :rules="[
                              { required: true, message: '请输入商品规格', trigger: 'blur' },
                              { min: 2, max: 512, message: '长度在 2 到 128 个字符', trigger: ['blur', 'change'] }
                            ]"
              >
                <el-input v-model="item.lot" placeholder="请输入商品规格"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="1">
              <div style="float:right">
                <el-button @click.prevent="removeGoods(item)" type="danger" icon="el-icon-delete" circle></el-button>
              </div>
            </el-col>
          </el-row>
          <el-form-item>
            <el-button type="primary" @click="addGoods()" icon="el-icon-plus">新增商品</el-button>
          </el-form-item>
          <el-form-item label="附件上传">
            <el-upload ref="rptUpload" :action="UPLOAD_URL"
                       list-type="picture-card"
                       :before-upload="beforePicUpload" :headers="userInfo"
                       :multiple="false" :on-success="handlePicUploadSuccess"
                       :on-error="handlePicUploadError" :on-preview="handlePicPreview"
                       :on-remove="handlePicRemove" class="upload"
                       accept=".bmp,.jpg,.png,.pdf">
              <i class="el-icon-plus"></i>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png/bmp/pdf文件，且不超过10Mb。点击图片文字预览证照</div>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="submitForm()" :loading="btnLoading">提交</el-button>
            <el-button type="info" @click="bAddMode = false">取消</el-button>
          </el-form-item>
        </el-form>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
      </div>
    </transition>
  </div>
</template>
<script>
import cloneDeep from "lodash/cloneDeep";
import { companyKind } from "../../common/js/constance";
export default {
  data() {
    return {
      validateRules: {
        mfrsId: [{ required: true, message: "请选择厂家", trigger: "change" }],
        regKind: [
          { required: true, message: "请选择证件类型", trigger: "change" }
        ],
        productName: [
          { required: true, message: "请输入注册证产品名称", trigger: "blur" }
        ],
        certificateCode: [
          { required: true, message: "请输入注册证编号", trigger: "blur" }
        ]
      },
      BASE_URL: "/spdHDIService/inspectionReport",
      UPLOAD_URL: "/platformService/sys/upload/file/report/",
      ATTACHMENT_URL: "/upload/",
      bAddMode: false,
      bIsHos: false,
      tableLoading: false,
      btnLoading: false,
      filter: {
        goodsname: "",
        goodsSpec: "",
        mfrsName: "",
        lot: ""
      },
      pageInfo: {
        page: 1,
        size: 30,
        total: 0
      },
      reportsTableData: [],
      rptFormData: {},
      allowedFileTypes: [
        "image/bmp",
        "image/jpeg",
        "image/png",
        "application/pdf"
      ],
      dialogVisible: false,
      dialogImageUrl: ""
    };
  },
  computed: {
    userInfo() {
      return { "X-US": this.user.xus };
    }
  },
  mounted() {
    this.rptFormData = this.newRpt();
    this.bIsHos = this.user.corpKind === companyKind.HOS;
    this.listReport();
  },
  methods: {
    handlePageSizeChange(val) {
      this.pageInfo.size = val;
      this.listReport();
    },
    handlePageChange(val) {
      this.pageInfo.page = val;
      this.listReport();
    },
    listReport(newSearch = false) {
      if (newSearch) {
        this.pageInfo.page = 1;
      }
      let params = {
        goodsName: this.filter.goodsName,
        goodsSpec: this.filter.goodsSpec,
        lot: this.filter.lot,
        mfrsName: this.filter.mfrsName,
        page: this.pageInfo.page,
        size: this.pageInfo.size
      };
      this.tableLoading = true;
      this.axios
        .get(this.BASE_URL + "/listReport", { params })
        .then(res => {
          if (res.data.code === 0) {
            this.reportsTableData.splice(0);
            const data = res.data.data.data;
            for (let item of data) {
              item.reportSubVoList.forEach(element => {
                element.mfrsName = item.mfrsName;
                element.provName = item.provName;
                element.pid = item.id;
                element.version = item.version;
                element.imgNum = item.imgNum;
              });
              this.reportsTableData = this.reportsTableData.concat(
                item.reportSubVoList
              );
            }
            this.pageInfo.total = res.data.data.total;
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(err => {
          this.$message.error(err);
        })
        .finally(_ => {
          this.tableLoading = false;
        });
    },
    deleteReport(row) {
      this.$confirm("是否确认删除此检验单?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.axios
            .delete(`${this.BASE_URL}/delReport/${row.pid}/${row.version}`)
            .then(res => {
              if (res.data.code === 0 && res.data.data === 1) {
                this.$message.success("删除检验单成功。");
                this.listReport(true);
              } else {
                this.$message.error("删除检验单失败");
              }
            })
            .catch(err => {
              this.$message.error(err);
            });
        })
        .catch(() => {});
    },
    viewReport(row) {
      this.axios
        .get(`${this.BASE_URL}/getReportAttachment/${row.pid}/${row.version}`)
        .then(res => {
          if (res.data.code === 0) {
            const data = res.data.data;
            data.forEach(item => {
              window.open(this.ATTACHMENT_URL + item, "_blank");
            });
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(err => {
          this.$message.error(err);
        });
    },
    initForm() {
      this.bAddMode = true;
    },
    newRpt() {
      return {
        mfrsName: "",
        reportSubVoList: [
          {
            id: Date.now(),
            goodsName: "",
            goodsSpec: "",
            lot: ""
          }
        ],
        reportImageVoList: []
      };
    },
    removeGoods(item) {
      var index = this.rptFormData.reportSubVoList.indexOf(item);
      if (index !== -1) {
        this.rptFormData.reportSubVoList.splice(index, 1);
      }
    },
    addGoods() {
      this.rptFormData.reportSubVoList.push({
        id: Date.now(),
        goodsName: "",
        goodsSpec: "",
        lot: ""
      });
    },
    beforePicUpload(file) {
      if (!this.allowedFileTypes.includes(file.type)) {
        this.$message.error(
          "文件格式不符合要求，请按要求上传" +
            this.allowedFileTypes.join(",") +
            "文件！"
        );
        return false;
      }
      if (file.size / 1024 / 1024 > 10) {
        this.$message.error("上传图片大小不能超过10M！");
        return false;
      }
    },
    handlePicUploadSuccess(res, file, fileList) {
      file.filePath =
        res.data[0].path + "/" + res.data[0].id + "." + res.data[0].ext;
    },
    handlePicUploadError(err, file, fileList) {},
    handlePicPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handlePicRemove(file, fileList) {},
    submitForm() {
      this.$refs.rptForm.validate(valid => {
        if (valid) {
          this.btnLoading = true;
          this.axios
            .post(this.BASE_URL + "/addReport", this.transDataToPost())
            .then(res => {
              if (res.data.code === 0) {
                if (res.data.data) {
                  this.$message.success("更新数据成功");
                  this.bAddMode = false;
                  this.rptFormData = this.newRpt();
                  this.$refs.rptUpload.clearFiles();
                  this.listReport();
                } else {
                  this.$message.error("更新数据失败，新重新刷新页面提交数据");
                }
              } else {
                this.$message.error(res.data.msg);
              }
            })
            .catch(err => {
              this.$message.error(err);
            })
            .finally(() => {
              this.btnLoading = false;
            });
        } else {
          return false;
        }
      });
    },
    transDataToPost() {
      const fileList = cloneDeep(
        this.$refs.rptUpload.uploadFiles.filter(item => item.filePath)
      );
      const rptData = cloneDeep(this.rptFormData);
      rptData.reportImageVoList = fileList.map(item => {
        return {
          path: item.filePath
        };
      });
      return rptData;
    }
  }
};
</script>
<style scoped lang="scss">
a {
  color: blue;
}
.tool-bar {
  padding-top: 3px;
  height: 30px;
  box-shadow: 0px 0px 3px 0px #a9a1a1 inset;
  border-radius: 4px;
  background-color: #f9f9f9;
  margin-bottom: 5px;
}
.full-height {
  height: calc(100% - 30px);
}
.table-class {
  width: 100%;
  height: calc(100% - 61px);
}
.overflow {
  overflow-y: auto;
}
.input-box {
  padding: 0 5px;
  width: 200px;
}
</style>
<style lang="scss">
#inspection-report {
  .el-form-item-nomsg {
    .el-form-item {
      margin-bottom: 10px;
    }
    .is-error {
      margin-bottom: 20px;
    }
  }
}
</style>
