<template>
    <div id="bas-reg-info" class="fullheight">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="filters">
          <el-form-item label="产品名称">
            <el-input v-model="filters.productName" placeholder="请输入产品名称" clearable></el-input>
          </el-form-item>
          <el-form-item label="注册证编号">
            <el-input v-model="filters.certificateCode" placeholder="请输入注册证编号" clearable></el-input>
          </el-form-item>
          <el-form-item label="厂家名称">
            <el-input v-model="filters.mfrsName" placeholder="请输入厂家名称" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="getCerts(1)" >查询</el-button>
            <el-button type="success"  @click="handleAdd">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
        <template>
          <el-table highlight-current-row  class="basRegCert-tableheight" ref="certsTable" :data="certsData"
                    style="width: 100%" v-loading="certsLoading" border height="outer">
              <el-table-column prop="productName" header-align="center"  label="注册证产品名称">
              </el-table-column>
              <el-table-column prop="certificateCode" label="注册证编号" header-align="center"
                               show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="regKind" width="120" label="证照类型" align="center"
                               :formatter="formatRegKind">
              </el-table-column>
              <el-table-column prop="mfrsName" width="250" label="生产厂商" header-align="center" show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="expdtBeginDate" header-align="center" width="150"
                               label="有效期" show-overflow-tooltip sortable>
                  <template slot-scope="scope">
                      <div v-if="scope.row.expdtBeginDate && scope.row.expdtEndDate"
                           :class="expdtDateClass(scope.row.expdtEndDate)">
                          {{formatDate(scope.row.expdtBeginDate)}} - {{formatDate(scope.row.expdtEndDate)}}
                      </div>
                      <div v-else>
                          Error
                      </div>
                  </template>
              </el-table-column>

              <el-table-column label="操作" width="90" align="center" fixed="right">
                  <template slot-scope="scope">
                      <el-button-group>
                          <el-button size="mini" @click="handleEdit(scope.row)" type="primary" icon="edit">编辑
                          </el-button>
                          <el-button size="mini" @click="handleDelete(scope.row)" type="danger" icon="delete">删除
                          </el-button>
                      </el-button-group>
                  </template>
              </el-table-column>
          </el-table>
        </template>
        <el-pagination v-if="cTotal > 0" small @size-change="handleCSizeChange"
                       @current-change="handleCCurrentChange" class="el-pagination-right"
                       :current-page.sync="cPageNum" layout="total, sizes, prev, pager, next"
                       :page-sizes="[10, 15, 20, 30]" :page-size="cPageSize" :total="cTotal">
        </el-pagination>

        <el-dialog :title="title" :visible.sync="dialogVisible" size="tiny">
            <el-form :model="currentCert" :rules="validateRules" ref="certForm" label-width="120px"
                     class="el-form-item-nomsg">
                <el-form-item label="单位名称">
                    <el-input v-model="currentCert.mfrsName" :disabled="true" ></el-input>
                </el-form-item>
                <el-form-item label="注册证产品名称" prop="productName">
                    <el-input v-model="currentCert.productName" placeholder="请输入注册证名称" ></el-input>
                </el-form-item>
                <el-form-item label="注册证类型" prop="regKind">
                    <el-select v-model="currentCert.regKind" placeholder="请选择注册证类型" >
                        <el-option v-for="(value,key,index) in dicts" :key="value" :label="value"
                                   :value="key"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="注册证编号" prop="certificateCode">
                    <el-input v-model="currentCert.certificateCode" placeholder="请输入注册证编号"
                              :disabled="!dialogVisible"></el-input>
                </el-form-item>
                <el-form-item label="证照有效期" required>
                    <el-col :span="11">
                        <el-form-item prop="expdtBeginDate">
                            <el-date-picker  type="date" placeholder="开始日期"
                                            v-model="currentCert.expdtBeginDate"
                                            style="width: 100%;"></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col class="line" :span="2">-</el-col>
                    <el-col :span="11">
                        <el-form-item prop="expdtEndDate">
                            <el-date-picker  type="date" placeholder="结束日期"
                                            v-model="currentCert.expdtEndDate" style="width: 100%;"></el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dlgCancle" >取消</el-button>
                <el-button @click="dlgConfirm"  :loading="btnLoading"
                           :type="dlgAddMode ? 'success':'primary'">提交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            let validateBeginDate = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('开始日期不能为空'))
                } else {
                    if (this.currentCert.expdtEndDate) {
                        this.$refs.certForm.validateField('expdtEndDate');
                    }
                    callback();
                }
            };
            let validateEndDate = (rule, value, callback) => {
                if (this.currentCert.isValiding !== '1') {
                    if (!value) {
                        return callback(new Error('结束日期不能为空'))
                    } else {
                        if (Date.parse(this.currentCert.expdtBeginDate) > Date.parse(this.currentCert.expdtEndDate)) {
                            return callback(new Error('结束日期必须大于开始日期'))
                        }
                        callback();
                    }
                } else {
                    callback();
                }
            };
            return {
                filters: {
                  productName:'',
                  certificateCode:'',
                  mfrsName: ''
                },
              title:'',
                dicts: {},
                validateRules: {
                    regKind: [
                        {required: true, message: '请选择证照类型', trigger: 'change'}
                    ],
                    expdtBeginDate: [
                        {required: true, validator: validateBeginDate, trigger: 'change'}
                    ],
                    expdtEndDate: [
                        {required: true, validator: validateEndDate, rigger: 'change'}
                    ],
                    certificateCode: [
                        {required: true, message: '请输入证照编号', trigger: 'blur'},
                        {min: 5, max: 128, message: '长度在 5 到 128 个字符', trigger: 'blur'}
                    ],
                    productName: [
                        {required: true, message: '请输入注册证名称', trigger: 'blur'}
                    ]
                },
                certsData: [],
                cPageNum: 1,
                cPageSize: 30,
                cTotal: 0,
                baseURL: '/spdHDIService/basRegInfo',
                certsLoading: false,
                btnLoading: false,
                currentMfrs: {},
                currentCert: {},
                dialogVisible: false,
                dlgAddMode: true
            }
        },
        methods: {
            formatRegKind(row, column) {
              return this.dicts[row.regKind];
            },
            formatDate(item) {
                return new Date(item).toLocaleDateString();
            },
            expdtDateClass(date) {
                let endDate = Date.parse(date);
                let today = Date.parse(new Date());
                let days = 30 * 24 * 60 * 60 * 1000;
                let expiring = false;
                let expired = false;
                if (endDate - today < 0) {
                    expired = true;
                } else if (endDate - today < days) {
                    expiring = true;
                }
                return {
                    'expiring': expiring,
                    'expired': expired
                }
            },
            getCerts2() {
                this.certsLoading = true;
                this.axios.get(this.baseURL + '/getCerts', {
                    params: {
                        'mfrsId': mfrsId,
                        'pageNum': this.cPageNum,
                        'pageSize': this.cPageSize
                    }
                }).then(res => {
                    this.certsData = res.data.data.data;
                    this.cTotal = res.data.data.total;
                    this.certsLoading = false
                })
            },

            //获取供应商列表
            getCerts: function (pIndex) {
              this.certsLoading = true;
              this.cPageNum = pIndex;
              var param = {
                orderBy: "",
                queryObject: {
                  productName: this.filters.productName,
                  certificateCode: this.filters.certificateCode,
                  mfrsName: this.filters.mfrsName
                },
                page: this.cPageNum,
                rows: this.cPageSize
              };
              this.loading = true;
              this.axios.post(this.baseURL + '/getRegCerts',param).then(res => {
                this.certsLoading = false
                this.certsData = res.data.data.data;
                this.cTotal = res.data.data.total;
                //   this.page = res.data.data.pageNum;
              }, err => {
                this.certsLoading = false;
              });
            },
            handleAdd() {
            	this.title = "添加证照信息";
                this.currentCert = this.newBasRegInfo();
                this.currentCert.mfrsId = this.currentMfrs.id;
                this.dlgAddMode = true;
                this.dialogVisible = true;
                this.$refs.certForm.resetFields();
            },
            handleEdit(item) {
            	this.title = "修改证照信息";
                this.dlgAddMode = false;
                this.currentCert = JSON.parse(JSON.stringify(item));
                this.dialogVisible = true
            },
            handleDelete(item) {
                this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(_ => {
                    this.axios.delete(this.baseURL + '/delCert', {
                        params: {
                            'id': item.id,
                            'version': item.version
                        }
                    }).then(res => {
                        if (res.data.code >= 0) {
                            this.$message({
                                type: 'success',
                                message: '删除证照信息成功。'
                            });
                            this.certsData.splice(this.certsData.indexOf(item), 1)
                        } else {
                            this.$message.error(res.data.msg.substring(0, 50) + '...')
                        }
                    })
                }).catch(_ => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    })
                })
            },
            dlgCancle() {
                this.dialogVisible = false
            },
            dlgConfirm() {
                this.$refs.certForm.validate((valid) => {
                    if (valid) {
                        this.btnLoading = true;
                        if (this.dlgAddMode) {
                            this.axios.post(this.baseURL + '/addCert', this.certData).then(res => {
                                if (res.data.code >= 0) {
                                    this.$message({
                                        type: 'success',
                                        message: '新增证照信息成功。'
                                    });
                                    this.currentCert.id = res.data.data;
                                    this.currentCert.version = 0;
                                    this.certsData.push(this.currentCert);
                                    this.btnLoading = false;
                                    this.dialogVisible = false
                                } else {
                                    this.$message.error(res.data.msg.substring(0, 50) + '...');
                                    this.btnLoading = false;
                                    return false
                                }
                            })
                        } else {
                          delete  this.certData.mfrsName;
                            this.axios.put(this.baseURL + '/modCert', this.certData).then(res => {
                                if (res.data.code >= 0) {
                                    this.$message({
                                        type: 'success',
                                        message: '更新证照信息成功。'
                                    });
                                    this.currentCert.version = res.data.data;
                                    this.modTableData(this.currentCert);
                                    this.btnLoading = false;
                                    this.dialogVisible = false
                                } else {
                                    this.currentCert.expdtBeginDate = new Date(this.currentCert.expdtBeginDate);
                                    this.currentCert.expdtEndDate = new Date(this.currentCert.expdtEndDate);
                                    this.$message.error(res.data.msg.substring(0, 50) + '...');
                                    this.btnLoading = false;
                                    return false
                                }
                            })
                        }
                    } else {
                        this.$message.error('未按要求填写表单，请按提示更正后重新提交。');
                        this.btnLoading = false;
                    }
                })
            },
            handleCCurrentChange(val) {
                this.cPageNum = val
                this.getCerts(this.cPageNum)
            },
            handleCSizeChange(val) {
                this.cPageSize = val
                this.getCerts(this.cPageNum)
            },
            validateIsValiding() {
                this.$refs.certForm.validateField('expdtEndDate');
            },
            modTableData(item) {
                let cert = this.certsData.find(o => o.id === item.id);
                let {regKind, productName, certificateCode, mfrsId, expdtBeginDate, expdtEndDate, version} = item;
                cert.regKind = regKind;
                cert.productName = productName;
                cert.certificateCode = certificateCode;
                cert.mfrsId = mfrsId;
                cert.expdtBeginDate = expdtBeginDate;
                cert.expdtEndDate = expdtEndDate;
                cert.version = version;
            },
            newBasRegInfo() {
                return {
                    'id': '',
                    'regKind': '',
                    'productName': '',
                    'certificateCode': '',
                    'mfrsId': '',
                    'expdtBeginDate': '',
                    'expdtEndDate': '',
                    'version': '',
                }
            },
        },
        computed: {
            certData() {
                let result = JSON.parse(JSON.stringify(this.currentCert));
                result.expdtBeginDate = Date.parse(result.expdtBeginDate);
                result.expdtEndDate = Date.parse(result.expdtEndDate);
                return result;
            }
        },
        filters: {},
        mounted() {
            this.getCerts();
            this.axios.get(this.baseURL + '/getDicts').then(res => {
                if (res.data.code >= 0) {
                    for (let dict of res.data.data) {
                        this.dicts[dict.value] = dict.name;
                    }
                } else {
                    this.$message.error('获取数据失败，请联系管理员。')
                }
            }).catch(err => {
                this.$message.error('数据异常，请联系管理员。')
            })
        }
    }
</script>

<style>
    #bas-reg-info .line {
        text-align: center;
    }

    #bas-reg-info .el-pagination-right {
        float: right;
        margin-top: 5px;
    }

    .fullheight{
      height: 100%;
    }
    .basRegCert-tableheight{
      height: calc(100% - 100px);
    }

    #bas-reg-info .expiring {
        color: #ff00ff;
    }

    #bas-reg-info .expired {
        color: red;
    }

    #bas-reg-info .is-error {
        margin-bottom: 22px;
    }

    #bas-reg-info .el-form-item__error {
        display: block;
    }
</style>
