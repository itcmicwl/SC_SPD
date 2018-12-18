<template>
  <div style="height:100%">
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" label-width="80px">
            <el-form-item label="文件名">
                <el-input v-model="fileName" placeholder="文件名" ></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="getAttachList(1)" type="primary" icon="search" >查询</el-button>
                <el-button >重置</el-button>
                <el-button-group>
                <el-button @click="addAttach" type="primary" icon="upload" >新增</el-button>
                <el-button @click="deleteAttach" type="danger" icon="delete" >批量删除</el-button>
                </el-button-group>
            </el-form-item>
        </el-form>
    </el-col>
    <el-table @selection-change="handleSelectionChange" :data="attachList" highlight-current-row v-loading="loading" style="width: 100%;" border class="table_content">
        <el-table-column type="selection" width="50"></el-table-column>
        <el-table-column width="170" header-align="center" label="医院" prop="hosName"></el-table-column>
        <el-table-column header-align="center" label="供应商" prop="provName"></el-table-column>
        <el-table-column header-align="center" :show-overflow-tooltip="true" label="文件名" prop="title"></el-table-column>
        <el-table-column header-align="center" :show-overflow-tooltip="true" label="路径" prop="filePath"></el-table-column>
        <el-table-column width="80" header-align="center" label="文件类型" prop="attachType"></el-table-column>
        <el-table-column header-align="center" label="录入人" prop="userName"></el-table-column>
        <el-table-column label="操作" width="120" header-align="center">
            <template slot-scope="scope">
                <el-button-group>
                    <el-button @click="updateAttach(scope.row)" size="mini" icon="edit" type="primary" >编辑
                    </el-button>
                    <el-button @click="downloadAttach(scope.row)" size="mini" icon="document" type="primary" >下载
                    </el-button>
                </el-button-group>
            </template>
        </el-table-column>
    </el-table>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[10, 20, 40, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
        </el-pagination>
    </el-col>
    <el-dialog :title="title" :visible.sync="showDialog" :close-on-press-escape='false' :close-on-click-modal="false">
        <el-form :rules="rules" v-loading="formLoad" :model="attach" label-width="100px" ref="formRef">
            <el-row>
                <el-col :span="12">
                    <el-form-item prop="hosName" label="医院">
                        <el-select v-model="attach.hosName" filterable :remote="remote" placeholder="请输入关键词"
                               :remote-method="querySearchHosAsync" :loading="hosSearchLoading"
                               clearable @input="handleFreezeRemote" @change="handleHosSelect">
                            <el-option v-for="item in hosSearchData" :key="item.id" :label="item.hosName" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="provName" label="供应商">
                        <el-select v-model="attach.provName" filterable :remote="remoteProv" placeholder="请输入关键词"
                               :remote-method="querySearchProvAsync" :loading="provSearchLoading"
                               clearable @input="handleFreezeRemoteProv" @change="handleProvSelect">
                            <el-option v-for="item in provSearchData" :key="item.id" :label="item.hosName" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item prop="title" label="文件名">
                        <el-upload style="text-align:center;" class="pic-uploader" accept=".pdf" ref="upload" :headers="userInfo" :action="uploadURL" :auto-upload="true" :show-file-list="false" :multiple="false" :before-upload="handleUploadBefore" :on-success="handleUploadSuccess" :on-error="handleUploadErr">
                            <el-input :readonly="true" size="mini" v-model="attach.title" type="text" placeholder="文件名">
                                <el-button slot="append" icon="upload"></el-button>
                            </el-input>
                        </el-upload>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="attachType" label="文件类型">
                        <el-input readonly size="mini" v-model="attach.attachType" type="text" placeholder="文件类型"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item prop="fileSize" label="文件大小(byte)">
                        <el-input readonly size="mini" v-model="attach.fileSize" type="text" placeholder="文件大小"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="fillUser" label="录入人">
                        <el-input readonly size="mini" v-model="attach.userName" type="text" placeholder="录入人"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item prop="content" label="说明">
                        <el-input size="mini" v-model="attach.content" type="textarea" placeholder="说明"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="cancelDia">取消</el-button>
            <el-button type="primary" @click="saveOrUpdate">提交</el-button>
        </div>
    </el-dialog>
  </div>
</template>
<script>
import debounce from 'throttle-debounce/debounce'
export default {
  data(){
      return {
          baseURL: '/spdHDIService/provSalesRep',
          uploadURL: '/platformService/sys/upload/file/provAttach/',
          attachList:[],
          loading:false,
          showDialog:false,
          fileName:'',
          hosSearchData:[],
          provSearchData:[],
          multipleSelection: [],
          rules:{
              title:[
                  { required: true, message: '请上传附件', trigger: 'blur' }
              ],
              hosName:[
                  { required: true, message: '请录入医院', trigger: 'blur' }
              ]
          },
          remote: true,
          hosSearchLoading: false,
          remoteProv: true,
          provSearchLoading: false,
          attach:{
              attachType:'',
              fileExt:'',
              filePath:'',
              title:'',
              fileSize:''
          },
          formLoad:false,
          title:'',
          page:1,
          total:0,
          pageSize:20
      }
  },
  methods:{
        getAttachList(pIndex){
            this.page = pIndex;
            let param = { orderBy: "", queryObject: {title:this.fileName,type:'2'}, page: this.page, rows: this.pageSize };
            this.loading = true;
            this.axios.post('/spdHDIService/provAttach/getAttachList', param).then(res => {
                this.loading = false;
                if (res.data.code == "0") {
                    this.attachList = res.data.data.data;
                    this.total = res.data.data.total;
                }else{
                    this.$message.error( res.data.msg);
                }
            }, err => {
                this.loading = false;
            });
        },
        cancelDia(){
            this.showDialog = false;
        },
        saveOrUpdate(){
            let url = this.attach.id==null?'/spdHDIService/provAttach/insertAttach':'/spdHDIService/provAttach/updateAttach'
            this.$refs.formRef.validate((valid)=>{
                if(valid){
                    this.attach.type = '2';
                    if(this.attach.id)
                        this.multipleSelection.push(this.attach);
                    this.formLoad = true;
                    this.axios.post(url,this.attach.id==null?this.attach:this.multipleSelection).then(res=>{
                        this.formLoad = false;
                        this.showDialog = false;
                        if(res.data.code=='0'){
                            this.$message.success('执行成功');
                            this.getAttachList(1);
                        }else{
                            this.$message.error(res.data.msg);
                        }
                    });
                }
            });
        },
        updateAttach(row){
            this.title = '上传附件';
            this.showDialog = true;
            this.attach = row;
        },
        addAttach(){
            if(this.$refs.formRef)
                this.$refs.formRef.resetFields();
            this.attach = {};
            this.title = '上传附件';
            this.showDialog = true;
        },
        handleUploadSuccess (response, file, fileList, i) {
            this.formLoad = false;
            if (response.code < 0) {
                this.$message({
                    type: 'error',
                    showClose: 'true',
                    duration: 0,
                    message: file.name + '上传失败，' + response.msg
                })
            } else {
                this.$message.success(file.name + '上传成功！');
                let { ext, id, name, path, fileSize } = response.data[0];
                this.attach.filePath = path + '/' + id + '.' + ext;
                this.attach.attachType = ext;
                this.attach.fileExt = ext;
                this.attach.title = name;
                this.attach.fileSize = fileSize;
                this.attach.fillUser = this.user.userId;
            }
        },
        handleUploadBefore(file){
            this.formLoad = true;
        },
        handleUploadErr (_, file, fileList) {
            this.formLoad = false;
            this.$message.error('上传失败，请联系管理员！')
        },
        handleCurrentChange(val) {
            this.page = val;
            this.getAttachList(this.page);
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.getAttachList(1);
        },
        querySearchHosAsync (queryString) {
            if (queryString !== '') {
                this.hosSearchLoading = true
                this.debounceQuerySearchHosAsync(queryString)
            }
        },
        handleFreezeRemote () {
            this.$set(this, 'remote', false)
            this.$nextTick(function () {
                this.$set(this, 'remote', true)
            })
        },
        handleHosSelect (item) {
        if (item) {
            const hos = this.hosSearchData.find(o => o.id === item)
            if (hos) {
            this.attach.hosId = hos.id;
            //   this.attach.hosName = hos.hosName;
            }
        }
        },
        debounceQuerySearchHosAsync: debounce(500, function (queryString) {
        this.axios
            .get(this.baseURL + '/getHosList', {
            params: {
                hosName: queryString
            }
            })
            .then(res => {
            if (res.data.code >= 0) {
                this.hosSearchData = res.data.data
                this.hosSearchLoading = false
            } else {
                this.$message.error(
                '获取数据失败。' + res.data.msg.substring(0, 50) + '...'
                )
                this.hosSearchLoading = false
            }
            })
            .catch(_ => {
            this.$message.error('数据异常，请联系管理员。')
            })
        }),
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        deleteAttach(){
            this.$confirm('确认删除选中的记录?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(_=>{
                this.multipleSelection.forEach(item=>item.flag='0');
                this.loading = true;
                this.axios.post('/spdHDIService/provAttach/updateAttach',this.multipleSelection).then(res=>{
                    this.loading = true;
                    if(res.data.code=='0'){
                        this.$message.success('执行成功');
                        this.getAttachList(1);
                    }else{
                        this.$message.error(res.data.msg);
                    }
                });
            });
        },
        downloadAttach(row){
            window.location.href="/platformService/sys/upload/downFile?url="+row.filePath+"&fileName="+row.title;
        },
        querySearchProvAsync (queryString) {
            if (queryString !== '') {
                this.provSearchLoading = true
                this.debounceQuerySearchProvAsync(queryString)
            }
        },
        handleFreezeRemoteProv () {
            this.$set(this, 'remoteProv', false)
            this.$nextTick(function () {
                this.$set(this, 'remoteProv', true)
            })
        },
        handleProvSelect (item) {
        if (item) {
            const prov = this.provSearchData.find(o => o.id === item)
            if (prov) {
            this.attach.provId = prov.id;
            //   this.attach.hosName = hos.hosName;
            }
        }
        },
        debounceQuerySearchProvAsync: debounce(500, function (queryString) {
        this.axios
            .get('/spdHDIService/provAttach/getProvList', {
            params: {
                provName: queryString
            }
            })
            .then(res => {
            if (res.data.code >= 0) {
                this.provSearchData = res.data.data
                this.provSearchLoading = false
            } else {
                this.$message.error(
                '获取数据失败。' + res.data.msg.substring(0, 50) + '...'
                )
                this.provSearchLoading = false
            }
            })
            .catch(_ => {
            this.$message.error('数据异常，请联系管理员。')
            })
        })
  },
  mounted(){
      this.getAttachList(1);
  },
  computed:{
    userInfo () {
      return {
        'X-US': this.user.xus
      }
    }
  }
}
</script>



<style scoped>
.table_content{
    font-size: 12px;
    height: calc(100% - 90px);
    overflow-y: auto;
}
</style>
