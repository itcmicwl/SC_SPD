<template>
    <div id="provJxsqs" style="height:100%;">
        <el-row style="margin-bottom: 5px;">
            <div class="inline" style="padding-top:1.5px;">
                <el-date-picker type="date" placeholder="开始日期" v-model="searchExpdtBeginDate"  style="width:130px"></el-date-picker>
                <span>-</span>
                <el-date-picker type="date" placeholder="结束日期" v-model="searchExpdtEndDate"  style="width:130px"></el-date-picker>
            </div>
            <div class="inline" style="padding-top:1.5px;">
                <el-input placeholder="请输入授权单位名称" v-model="searchMfrsName"
                          @keyup.enter.native="searchBtnClick">
                    <el-button slot="append" icon="el-icon-search" @click="searchBtnClick">查询</el-button>
                </el-input>
            </div>
            <div class="inline right">
                <el-button  type="primary" @click="addPrepare">
                    添加授权书
                </el-button>
            </div>
        </el-row>
        <el-table highlight-current-row  class="provJxsqs-tableheight" ref="jxsqsTable" border
                  :data="jxsqsList" style="width: 100%" v-loading="jLoading" :default-sort="{'prop':'mfrsName'}" height="outer">
            <el-table-column prop="mfrsName" label="生产厂家" show-overflow-tooltip sortable header-align="center">
            </el-table-column>
            <el-table-column label="关联注册证数" show-overflow-tooltip sortable header-align="center"
                             align="center">
                <template slot-scope="scope">
                    {{scope.row.relatedCerts.length}}本
                </template>
            </el-table-column>
            <el-table-column label="证件有效期" show-overflow-tooltip sortable header-align="center" align="center">
                <template slot-scope="scope">
                    <div v-if="scope.row.expdtBeginDate && scope.row.isValiding === '1'">
                        {{formatDate(scope.row.expdtBeginDate)}}至长期有效
                    </div>
                    <div v-else-if="scope.row.expdtBeginDate && scope.row.expdtEndDate"
                         :class="expdtDateClass(scope.row.expdtEndDate)">
                        {{formatDate(scope.row.expdtBeginDate)}}至{{formatDate(scope.row.expdtEndDate)}}
                    </div>
                    <div v-else>
                        Error
                    </div>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="140" header-align="center" align="center">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button size="mini" icon="edit" type="primary"
                                   @click.native.prevent="handleEdit(scope.row)">编辑
                        </el-button>
                        <el-button size="mini" icon="delete" type="danger"
                                   @click.native.prevent="handleDelete(scope.row)">删除
                        </el-button>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination v-if="jTotal > 0" style="float:right" small @size-change="handlejSizeChange"
                       @current-change="handlejPageChange"
                       :current-page.sync="jPageNum" layout="total, sizes, prev, pager, next"
                       :page-sizes="[10, 15, 20, 30]" :page-size="jPageSize" :total="jTotal">
        </el-pagination>

        <el-dialog :title="dlgAddMode?'添加授权书':'授权书信息'" :visible.sync="jxsqsDlgVisible" >
            <el-form :model="currentJxsqs" ref="jxsqsForm" label-width="110px" :rules="validateRules"
                     class="el-form-item-nomsg">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="生产厂家" required prop="mfrsId" :error="'testError'">
                            <el-select v-model="currentJxsqs.mfrsId" filterable :remote="remote" placeholder="请输入关键词"
                                       :remote-method="querySearchMfrsAsync" :loading="mfrsSearchLoading"
                                       clearable @input="handleFreezeRemote" @change="handleDlgMfrsSelect">
                                <el-option v-for="item in mfrsSearchData" :key="item.id" :label="item.mfrsName"
                                           :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item :style="currentJxsqs.isMfrs == 0 ? 'margin-bottom:0;' : ''">
                            <el-checkbox v-model="currentJxsqs.isMfrs" true-label="1" false-label="0">
                                <span class="font">我是厂家/厂家直接授权</span>
                            </el-checkbox>
                        </el-form-item>
                        <el-form-item label="直接上级单位" prop="chiefId" v-if="currentJxsqs.isMfrs == 0">
                            <el-select v-model="currentJxsqs.chiefId" filterable :remote="remote" placeholder="请输入关键词"
                                       :remote-method="querySearchSuperiorCompanyAsync"
                                       :loading="superiorCompanySearchLoading"
                                       clearable @input="handleFreezeRemote" @change="handleDlgSuperiorCompanySelect">
                                <el-option v-for="item in superiorCompanySearchData" :key="item.id"
                                           :label="item.mfrsName" :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="直接上级授权书" prop="filePath" required>
                            <el-input v-model="currentJxsqs.filePath" style="display:none" readonly disabled></el-input>
                            <span style="font-size:12px;color:grey;">注：只允许上传15M以下文件。</span>
                            <el-upload class="pic-uploader" :class="{'upload-background':!imageUrl}" ref="upload"
                                       accept=".bmp,.jpg,.png,.pdf" :show-file-list="true" :multiple="false"
                                       :headers="userInfo" :action="uploadURL" :auto-upload="true"
                                       :on-error="handlePicUploadErr" :on-success="handlePicSuccess"
                                       :on-change="handlePicLocalAdd" :before-upload="beforePicUpload">
                                <label :class="{'item-status-label':uploadSuccess}">
                                    <i :class="{'upload-success el-icon-check':uploadSuccess}"></i>
                                </label>
                                <el-tooltip placement="right" v-if="imageUrl" effect="light" :open-delay="300">
                                    <div slot="content"><img class="tooltip-pic" :src="largeImageUrl"></div>
                                    <img :src="imageUrl" class="pic" @mouseenter="picMouseEnter">
                                </el-tooltip>
                                <i v-else class="el-icon-plus pic-uploader-icon"></i>
                            </el-upload>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" style="padding-left:5px;">
                        <div v-if="currentJxsqs.isMfrs == '0'" style="text-align:center;margin-top:33px;">
                            <div>
                                <el-button size="mini" type="info" @click="zjzjAddPrepare">上传逐级证件</el-button>
                            </div>
                            <el-table highlight-current-row ref="zjzjTable" border
                                      :data="currentJxsqs.zjzjList" style="margin-top:4px;">
                                <el-table-column prop="pname" label="上级授权单位" show-overflow-tooltip
                                                 header-align="center">
                                </el-table-column>
                                <el-table-column label="查看授权书" show-overflow-tooltip header-align="center"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-button v-if="scope.row.filePath" type="text"
                                                   @click="showZjzjPic(scope.row, $event)">查看
                                        </el-button>
                                        <span v-else>无</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="操作" header-align="center" align="center">
                                    <template slot-scope="scope">
                                        <el-button size="mini" icon="delete" type="danger"
                                                   @click.native.prevent="handleZjzjDelete(scope.row)">删除
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </el-col>
                </el-row>
                <el-form-item label="授权书有效期" required>
                    <el-col :span="6">
                        <el-form-item prop="expdtBeginDate">
                            <el-date-picker type="date" placeholder="开始日期" v-model="currentJxsqs.expdtBeginDate"
                                            style="width: 100%;" ></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col style="text-align:center;" :span="1">-</el-col>
                    <el-col :span="6">
                        <el-form-item prop="expdtEndDate">
                            <el-date-picker type="date" placeholder="结束日期" v-model="currentJxsqs.expdtEndDate"
                                            style="width: 100%;"
                                            :disabled="currentJxsqs.isValiding === '1'"></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="2" :offset="1">
                        <el-form-item prop="isVailding">
                            <el-checkbox v-model="currentJxsqs.isValiding" true-label="1" false-label="0"><span
                                    class="font">是否长期有效</span></el-checkbox>
                        </el-form-item>
                    </el-col>
                </el-form-item>
                <el-form-item label="授权区域" prop="saleArea" required>
                    <el-input v-model="currentJxsqs.saleArea"  type="textarea" autosize
                              class="font"></el-input>
                </el-form-item>
                <el-form-item label="关联注册证" prop="relatedCerts" style="margin-top:10px;">
                    <el-input style="display:none;" readonly disabled
                              v-model="currentJxsqs.relatedCerts.length"></el-input>
                    <el-input size="mini" icon="search" placeholder="请输入注册证名称" v-model="certSearchStr"
                              :on-icon-click="handleCertSearch" @keyup.enter.native="handleCertSearch"></el-input>
                    <el-table ref="certsTable" :data="provMfrsCertsList" v-loading="cLoading" row-key="id"
                              @select="handleCertSelectionChange" max-height="300"
                              @select-all="handleCertsSelectAll">
                        <el-table-column type="selection" :reserve-selection="true" width="40"></el-table-column>
                        <el-table-column prop="certificateCode" label="证件编号" width="200"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column prop="productName" label="注册证产品名称" show-overflow-tooltip></el-table-column>
                        <el-table-column prop="mfrsName" label="生产厂家" show-overflow-tooltip></el-table-column>
                    </el-table>
                    <el-pagination v-if="cTotal > 0" style="float:right" small @current-change="handleCPageChange"
                                   :current-page.sync="cPageNum" layout="total, prev, pager, next"
                                   :page-size="cPageSize" :total="cTotal">
                    </el-pagination>
                </el-form-item>
                <el-form-item label="适用医院" prop="forHospitals" style="margin-top:10px;">
                    <el-input style="display:none;" readonly disabled
                              v-model="currentJxsqs.forHospitals.length"></el-input>
                    <el-input size="mini" icon="search" placeholder="请输入医院名称" v-model="hosSearchStr"
                              :on-icon-click="handleHosSearch" @keyup.enter.native="handleHosSearch"></el-input>
                    <el-table ref="hosTable" :data="basHosList" row-key="id" @select-all="handleHosSelectAll"
                              @select="handleHosSelectionChange" max-height="300">
                        <el-table-column type="selection" :reserve-selection="true" width="40"></el-table-column>
                        <el-table-column prop="hosName" label="医院名称" show-overflow-tooltip></el-table-column>
                    </el-table>
                    <el-pagination v-if="hTotal > 0" style="float:right" small @current-change="handleHPageChange"
                                   :current-page.sync="hPageNum" layout="total, prev, pager, next"
                                   :page-size="hPageSize" :total="hTotal">
                    </el-pagination>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dlgCancle" >取消</el-button>
                <el-button type="primary" @click="dlgConfirm"
                           :loading="dlgConfirmBtnLoading">保存</el-button>
            </span>
        </el-dialog>

        <el-dialog title="逐级证件上传" :visible.sync="zjzjDlgVisible" size="mini">
            <el-form :model="currentZjzj" ref="zjzjForm" label-width="100px" :rules="validateRulesZjzj"
                     class="el-form-item-nomsg">
                <el-form-item label="我的上级单位" prop="pname" style="margin-top:0;">
                    <el-select v-model="currentZjzj.pname" filterable :remote="remote" placeholder="请输入关键词"
                               :remote-method="querySearchZjzjSuperiorCompanyAsync"
                               :loading="zjzjSuperiorCompanySearchLoading"
                               clearable @input="handleFreezeRemote"><!--@change="handleDlgZjzjSuperiorCompanySelect"-->
                        <el-option v-for="name in zjzjSuperiorCompanySearchData" :key="name" :label="name"
                                   :value="name">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="授权书上传" prop="filePath" required>
                    <el-input v-model="currentZjzj.filePath" style="display:none" readonly disabled></el-input>
                    <span style="font-size:12px;color:grey;">注：只允许上传15M以下文件。</span>
                    <el-upload class="pic-uploader" :class="{'upload-background':!zjzjImageUrl}" ref="zjzjUpload"
                               accept=".bmp,.jpg,.png,.pdf"
                               :headers="userInfo" :action="uploadURL" :auto-upload="true"
                               :show-file-list="true" :multiple="false" :on-error="handlePicUploadErr"
                               :on-success="handleZjzjPicSuccess" :on-change="handleZjzjPicLocalAdd"
                               :before-upload="beforePicUpload">
                        <label :class="{'item-status-label':zjzjUploadSuccess}">
                            <i :class="{'upload-success el-icon-check':zjzjUploadSuccess}"></i>
                        </label>
                        <img v-if="zjzjImageUrl" :src="zjzjImageUrl" class="pic">
                        <i v-else class="el-icon-plus pic-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="zjzjDlgCancle" >取消</el-button>
                <el-button type="primary" @click="zjzjDlgConfirm"
                           :loading="zjzjDlgConfirmBtnLoading">保存</el-button>
            </span>
        </el-dialog>

        <el-dialog :title="zjzjShowImageTitle" size="mini" :visible.sync="zjzjShowImageDlgVisible">
            <img :src="zjzjShowImageUrl">
        </el-dialog>
    </div>
</template>

<script>
    import debounce from 'throttle-debounce/debounce';
    import {mapGetters} from 'vuex';

    export default {
        props: [],
        data() {
            let validateBeginDate = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('开始日期不能为空'))
                } else {
                    if (this.currentJxsqs.expdtEndDate && !(this.currentJxsqs.isValiding === '1')) {
                        this.$refs.jxsqsForm.validateField('expdtEndDate');
                    }
                    callback();
                }
            };
            let validateEndDate = (rule, value, callback) => {
                if (!value && !(this.currentJxsqs.isValiding === '1')) {
                    return callback(new Error('结束日期不能为空'))
                } else if (value && !(this.currentJxsqs.isValiding === '1')) {
                    if (Date.parse(this.currentJxsqs.expdtBeginDate) > Date.parse(this.currentJxsqs.expdtEndDate)) {
                        return callback(new Error('结束日期必须大于开始日期'))
                    }
                    callback();
                } else {
                    callback();
                }
            };
            let validateCerts = (rule, value, callback) => {
                if (value < 1) {
                    return callback(new Error("请关联注册证"))
                }
                callback();
            };
            let validateHos = (rule, value, callback) => {
                if (value < 1) {
                    return callback(new Error("请选择适用医院"));
                }
                callback();
            };
            let validateChief = (rule, value, callback) => {
                if (this.currentJxsqs.isMfrs == 0 && this.currentJxsqs.chiefId == '') {
                    return callback(new Error("请选择上级单位或'我是厂家/厂家直接授权'"));
                }
                callback();
            };
            return {
                validateRules: {
                    mfrsId: [
                        {required: true, message: '请选择生产厂商', trigger: 'change'}
                    ],
                    chiefId: [
                        {validator: validateChief, trigger: 'change'}
                    ],
                    expdtBeginDate: [
                        {validator: validateBeginDate, trigger: 'change'}
                    ],
                    expdtEndDate: [
                        {validator: validateEndDate, trigger: 'change'}
                    ],
                    saleArea: [
                        {required: true, message: '请填写授权区域', trigger: 'change'}
                    ],
                    filePath: [
                        {required: true, message: '请上传授权书照片', trigger: 'change'}
                    ],
                    relatedCerts: [
                        {required: true, validator: validateCerts, trigger: 'change'}
                    ],
                    forHospitals: [
                        {required: true, validator: validateHos, trigger: 'change'}
                    ]
                },
                validateRulesZjzj: {
                    pname: [
                        {required: true, message: '请选择上级单位', trigger: 'change'}
                    ],
                    filePath: [
                        {required: true, message: '请上传上级单位授权书', trigger: 'change'}
                    ]
                },
                baseURL: '/spdHDIService/provJxsqs',
                uploadURL: '/platformService/sys/upload/file/certs/',
                picURLPrefix: '/upload/',
                jxsqsList: [],
                jLoading: false,
                searchExpdtBeginDate: '',
                searchExpdtEndDate: '',
                searchMfrsName: '',
//                uploadBtnLoading: false,
                jPageNum: 1,
                jPageSize: 30,
                jTotal: 0,
                jxsqsDlgVisible: false,
                currentJxsqs: {},
                origJxsqs: {},
                imageUrl: '',

                cTotal: 0,
                cPageNum: 1,
                cPageSize: 5,
                cLoading: false,
                provMfrsCertsList: [],

                hTotal: 0,
                hPageNum: 1,
                hPageSize: 5,
                basHosList: [],

                dlgAddMode: false,
                dlgConfirmBtnLoading: false,
                uploadSuccess: false,
                showBigPic: false,

                mfrsSearchLoading: false,
                superiorCompanySearchLoading: false,
                remote: true,
                mfrsSearchData: [],
                superiorCompanySearchData: [],

                allowedFileTypes: ['image/bmp', 'image/jpeg', 'image/png', 'application/pdf'],

                certSearchStr: '',
                hosSearchStr: '',

//                zjzjList: [],
                zjzjDlgVisible: false,
                currentZjzj: {},
                zjzjImageUrl: '',
                zjzjUploadSuccess: false,
                zjzjDlgConfirmBtnLoading: false,
                zjzjSuperiorCompanySearchData: [],
                zjzjShowImageUrl: '',
                zjzjShowImageTitle: '',
                zjzjShowImageDlgVisible: false,
                zjzjSuperiorCompanySearchLoading: false,
            }
        },
        computed: {
            largeImageUrl() {
                if (this.currentJxsqs.filePath && this.showBigPic && this.uploadSuccess)
                    return this.picURLPrefix + this.currentJxsqs.filePath;
                else
                    return this.imageUrl;
            },
            userInfo() {
                return {
                    'X-US': this.getLoginUser.xus
                }
            },
            handleData() {
                let jxsqsData = JSON.parse(JSON.stringify(this.currentJxsqs));
                jxsqsData.expdtBeginDate = Date.parse(jxsqsData.expdtBeginDate);
                jxsqsData.expdtEndDate = Date.parse(jxsqsData.expdtEndDate);
                jxsqsData.provId = this.getLoginUser.corpId;
                delete jxsqsData.mfrsName;
                delete jxsqsData.superiorMfrsName;

                if (this.dlgAddMode) {
//                    jxsqsData.zjzjList = JSON.parse(JSON.stringify(this.zjzjList));
                    for (let item of jxsqsData.relatedCerts) {
                        delete item.mfrsName;
                    }
                    delete jxsqsData.addCerts;
                    delete jxsqsData.delCerts;
                    delete jxsqsData.addHos;
                    delete jxsqsData.delHos;
                } else {
                    if (jxsqsData.addCerts)
                        for (let item of jxsqsData.addCerts) {
                            delete item.mfrsName;
                        }
                    if (jxsqsData.delCerts)
                        for (let item of jxsqsData.delCerts) {
                            delete item.mfrsName;
                        }
                    delete jxsqsData.relatedCerts;
                    delete jxsqsData.forHospitals;
                }
                return jxsqsData;
            },
            ...mapGetters(["getLoginUser"])
        },
        filters: {},
        methods: {
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
            addPrepare() {
                this.cPageNum = 1;
                this.hPageNum = 1;
                this.showBigPic = false;
                this.uploadSuccess = false;
                this.imageUrl = '';
                this.mfrsSearchData = [];
                this.superiorCompanySearchData = [];
                this.currentJxsqs = this.newJxsqs();
                this.provMfrsCertsList.splice(0);
                this.getBasHos();
                this.dlgAddMode = true;
                this.jxsqsDlgVisible = true;
                if (this.$refs['jxsqsForm']) {
                    this.$refs['jxsqsForm'].resetFields();
                }
                if (this.$refs['hosTable']) {
                    this.$refs['hosTable'].clearSelection();
                }
                if (this.$refs['certsTable']) {
                    this.$refs['certsTable'].clearSelection();
                }
//                this.zjzjList.splice(0);
            },
            zjzjAddPrepare() {
                this.zjzjImageUrl = '';
                this.zjzjSuperiorCompanySearchData = [];
                this.zjzjUploadSuccess = false;
                this.currentZjzj = {
                    id: '',
                    pid: '',
                    pname: '',
                    filePath: '',
                    version: 0
                };
                this.zjzjDlgVisible = true;
                this.$refs['zjzjForm'].resetFields();
            },
            searchBtnClick() {
                this.getJxsqsInfo()
            },
            handleEdit(item) {
                this.showBigPic = false;
                this.cPageNum = 1;
                this.hPageNum = 1;
                if (item.filePath) {
                    this.uploadSuccess = true;
                    let slashIndex = item.filePath.lastIndexOf("/");
                    if (slashIndex !== -1) {
                        this.imageUrl = this.picURLPrefix + item.filePath.substring(0, slashIndex) + '/thumb-' + item.filePath.substring(slashIndex + 1);
                    } else {
                        this.imageUrl = '';
                    }
                } else {
                    this.uploadSuccess = false;
                    this.imageUrl = '';
                }
                this.currentJxsqs = JSON.parse(JSON.stringify(item));

                this.mfrsSearchData = [{id: this.currentJxsqs.mfrsId, mfrsName: this.currentJxsqs.mfrsName}];
                this.superiorCompanySearchData = [{
                    id: this.currentJxsqs.chiefId,
                    mfrsName: this.currentJxsqs.superiorMfrsName
                }];
                this.getProvRegInfo(this.currentJxsqs.id);
                this.getBasHos(this.currentJxsqs.id);
                this.dlgAddMode = false;
                this.handleFreezeRemote();
//                this.zjzjList = this.currentJxsqs.zjzjList;
                this.jxsqsDlgVisible = true;
                this.$nextTick(() => {
                    this.$refs['hosTable'].clearSelection();
                    this.$refs['certsTable'].clearSelection();
                    this.$refs['hosTable'].store.states.selection.push(...this.currentJxsqs.forHospitals);
                    this.$refs['certsTable'].store.states.selection.push(...this.currentJxsqs.relatedCerts);
                });
            },
            handleDelete(item) {
                this.$confirm('此操作将删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(_ => {
                    this.axios.delete(this.baseURL + '/delJxsqs', {
                        params: {
                            'id': item.id,
                            'version': item.version
                        }
                    }).then(res => {
                        if (res.data.code >= 0) {
                            this.$message({
                                type: 'success',
                                message: '删除授权书成功。'
                            });
                            const removeIndex = this.jxsqsList.findIndex(o => o.id === item.id);
                            this.jxsqsList.splice(removeIndex, 1);
                            this.jTotal--;
                        } else {
                            this.$message({
                                type: 'error',
                                showClose: 'true',
                                duration: 0,
                                message: res.data.msg.substring(0, 50) + '...'
                            })
                        }
                    }).catch(err => {
                        this.$message.error('数据异常，请联系管理员！');
                    })
                }).catch(_ => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    })
                })
            },
            handleZjzjDelete(item) {
                let that = this;
                this.$confirm('此操作将删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(_ => {
                    if (!this.dlgAddMode) {
                        this.axios.delete(this.baseURL + '/delProvJxsqsZjzj', {
                            params: {id: item.id, version: item.version}
                        }).then(res => {
                            if (res.data.code >= 0) {
                                this.$message({
                                    type: 'success',
                                    message: '删除逐级证件成功。'
                                });
                                const currentRow = this.jxsqsList.find(o => o.id === that.currentJxsqs.id);
                                const removeIndex1 = currentRow.zjzjList.findIndex(o => o.id === item.id);
                                if (removeIndex1 !== -1) {
                                    currentRow.zjzjList.splice(removeIndex1, 1);
                                }
                                const removeIndex2 = that.currentJxsqs.zjzjList.findIndex(o => o.id === item.id);
                                if (removeIndex2 !== -1) {
                                    that.currentJxsqs.zjzjList.splice(removeIndex2, 1);
                                }
                            } else {
                                this.$message({
                                    type: 'error',
                                    showClose: 'true',
                                    duration: 0,
                                    message: res.data.msg.substring(0, 50) + '...'
                                })
                            }
                        }).catch(err => {
                            this.$message.error('数据异常，请联系管理员！');
                        })
                    } else {
                        const removeIndex = this.currentJxsqs.zjzjList.findIndex(o => o.pId === item.pId);
                        if (removeIndex !== -1) {
                            this.currentJxsqs.zjzjList.splice(removeIndex, 1);
                        }
                    }
                }).catch(_ => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    })
                })
            },
            showZjzjPic(item, event) {
                this.zjzjShowImageUrl = this.picURLPrefix + item.filePath;
                this.zjzjShowImageTitle = item.pname;
                this.zjzjShowImageDlgVisible = true;
            },
            handlejSizeChange(val) {
                this.jPageSize = val;
                this.getJxsqsInfo();
            },
            handlejPageChange(val) {
                this.jPageNum = val;
                this.getJxsqsInfo();
            },
            handleCPageChange(val) {
                this.cPageNum = val;
                this.getProvRegInfo(this.currentJxsqs.id);
            },
            handleHPageChange(val) {
                this.hPageNum = val;
                this.getBasHos(this.currentJxsqs.id);
            },
            resetUnusedData(currentJxsqs) {
                currentJxsqs.addCerts.splice(0);
                currentJxsqs.delCerts.splice(0);
                currentJxsqs.addHos.splice(0);
                currentJxsqs.delHos.splice(0);
            },
            changeTableData(tableData, item) {
                const row = tableData.find(o => o.id === item.id);
                let {provId, mfrsId, mfrsName, isMfrs, chiefId, superiorMfrsName, saleArea, expdtBeginDate, expdtEndDate, isValiding, filePath, version, relatedCerts, forHospitals} = item;
                row.provId = provId;
                row.mfrsId = mfrsId;
                row.mfrsName = mfrsName;
                row.isMfrs = isMfrs;
                row.chiefId = chiefId;
                row.superiorMfrsName = superiorMfrsName;
                row.saleArea = saleArea;
                row.expdtBeginDate = expdtBeginDate;
                row.expdtEndDate = expdtEndDate;
                row.isValiding = isValiding;
                row.filePath = filePath;
                row.version = version;
                row.forHospitals = [...forHospitals];
                row.relatedCerts = [...relatedCerts];
            },
            dlgConfirm() {
                this.$refs['jxsqsForm'].validate((valid) => {
                    if (valid) {
                        this.dlgConfirmBtnLoading = true;
                        if (this.dlgAddMode) { // new
                            this.axios.post(this.baseURL + '/addJxsqs', this.handleData).then(res => {
                                if (res.data.code >= 0) {
                                    this.$message.success('新增授权书成功。');
                                    this.currentJxsqs.id = res.data.data;
                                    this.resetUnusedData(this.currentJxsqs);
                                    this.jxsqsList.push(this.currentJxsqs);
                                    this.jTotal++;
                                    this.currentJxsqs = this.newJxsqs();
                                    this.jxsqsDlgVisible = false;
                                } else {
                                    this.$message.error('新增授权书失败，' + res.data.msg.substring(0, 50) + '...');
                                }
                            }).catch(err => {
                                this.$message.error('数据异常，请联系管理员！');
                            })
                        } else { // modify
                            this.axios.put(this.baseURL + '/modJxsqs', this.handleData).then(res => {
                                if (res.data.code >= 0) {
                                    this.$message.success('修改授权书成功。');
                                    this.currentJxsqs.version = res.data.data;
                                    this.changeTableData(this.jxsqsList, this.currentJxsqs);
                                    this.jxsqsDlgVisible = false;
                                } else {
                                    this.$message.error('修改授权书信息失败，' + res.data.msg.substring(0, 50) + '...');
                                }
                            }).catch(err => {
                                this.$message.error('数据异常，请联系管理员！');
                            })
                        }
                        this.dlgConfirmBtnLoading = false;
                    } else {
                        this.$message.error('数据验证失败，请更正！');
                    }
                });
                this.$refs['upload'].fileList.splice(0);
            },
            zjzjDlgConfirm() {
                if (this.currentJxsqs.isMfrs === 0 && this.currentJxsqs.pId === '') {
                    this.$message.error('请先完成直接上级单位资料！');
                    return;
                }
                this.$refs['zjzjForm'].validate((valid) => {
                    let that = this;
                    if (valid) {
                        if (that.currentZjzj.pname === that.currentJxsqs.superiorMfrsName
                            || that.currentJxsqs.zjzjList.findIndex(o => o.pname === that.currentZjzj.pname) !== -1) {
                            that.$message.error('所选上级单位重复，请重新选择。');
                            return;
                        }
                        if (!that.dlgAddMode) {
                            that.currentZjzj.pid = that.currentJxsqs.id;
                            that.axios.post(that.baseURL + '/addProvJxsqsZjzj', that.currentZjzj)
                                .then(res => {
                                    if (res.data.code >= 0) {
                                        that.currentZjzj.id = res.data.data;
                                        const currentRow = that.jxsqsList.find(o => o.id === that.currentJxsqs.id);
                                        currentRow.zjzjList.push(JSON.parse(JSON.stringify(that.currentZjzj)));
                                        that.currentJxsqs.zjzjList.push(JSON.parse(JSON.stringify(that.currentZjzj)));
                                        that.$refs['zjzjUpload'].fileList.splice(0);
                                        that.zjzjDlgVisible = false;
                                    } else {
                                        that.$message.error('新增信息失败，' + res.data.msg.substring(0, 50) + '...');
                                    }
                                })
                                .catch(err => {
                                    that.$message.error('数据异常，请联系管理员！');
                                })
                        }
                    } else {
                        that.$message.error('数据验证失败，请更正！');
                    }
                });
            },
            dlgCancle() {
                this.jxsqsDlgVisible = false;
                this.$refs['upload'].fileList.splice(0);
            },
            zjzjDlgCancle() {
                this.zjzjDlgVisible = false;
                this.$refs['zjzjUpload'].fileList.splice(0);
            },
            handlePicUploadErr(err, file, fileList) {
                this.$message.error("上传图片失败，请联系管理员！");
//                this.uploadBtnLoading = false;
            },
            handlePicSuccess(response, file, fileList) {
                if (response.code < 0) {
                    this.$message({
                        type: 'error',
                        showClose: 'true',
                        duration: 0,
                        message: file.name + '上传失败，' + response.msg.substring(0, 50) + '...'
                    })
                } else {
                    this.$message.success(file.name + '上传完成，请点击保存！');
                    let {ext, id, path} = response.data[0];
                    this.currentJxsqs.filePath = path + "/" + id + "." + ext;

                    this.$nextTick(() => {
                        this.uploadSuccess = true
                    });
                }
//                this.uploadBtnLoading = false;
            },
            handleZjzjPicSuccess(response, file, fileList) {
                if (response.code < 0) {
                    this.$message({
                        type: 'error',
                        showClose: 'true',
                        duration: 0,
                        message: file.name + '上传失败，' + response.msg.substring(0, 50) + '...'
                    })
                } else {
                    this.$message.success(file.name + '上传完成，请点击保存！');
                    let {ext, id, path} = response.data[0];
                    this.currentZjzj.filePath = path + "/" + id + "." + ext;

                    this.$nextTick(() => {
                        this.zjzjUploadSuccess = true;
                    });
                }
            },
            beforePicUpload(file) {
                if (!this.allowedFileTypes.includes(file.type)) {
                    this.$message.error('文件格式不符合要求，请按要求上传' + this.allowedFileTypes.join(',') + '文件！');
                    return false;
                }
                if (file.size / 1024 / 1024 > 15) {
                    this.$message.error("上传图片大小不能超过15M！");
                    return false;
                }
            },
            /*            handlePicUpload() {
                            if (this.$refs.upload.uploadFiles.length > 0) {
                                if (this.$refs.upload.uploadFiles.some(o => o.status === 'ready')) {
            //                        this.uploadBtnLoading = true;
                                    this.$refs.upload.submit();
                                } else {
                                    this.$message.info("照片已上传成功");
                                }
                            } else {
                                this.$message.info("请选择照片后，再点击上传");
                            }
                        },*/
            handlePicLocalAdd(file, fileList) {
                this.uploadSuccess = false;
                fileList.splice(0);
                fileList.push(file);
                this.imageUrl = file.url;
            },
            handleZjzjPicLocalAdd(file, fileList) {
                this.zjzjUploadSuccess = false;
                fileList.splice(0);
                fileList.push(file);
                this.zjzjImageUrl = file.url;
            },
            addItem(item, type) {
                if (type === 'cert') {
                    if (this.currentJxsqs.relatedCerts.findIndex(o => o.id === item.id) !== -1)
                        return;
                    this.currentJxsqs.relatedCerts.push(item);
                    const removeIndex = this.currentJxsqs.delCerts.findIndex(o => o.id === item.id);
                    if (removeIndex !== -1)
                        this.currentJxsqs.delCerts.splice(removeIndex, 1);
                    else
                        this.currentJxsqs.addCerts.push(item);
                } else if (type === 'hos') {
                    if (this.currentJxsqs.forHospitals.findIndex(o => o.id === item.id) !== -1)
                        return;
                    this.currentJxsqs.forHospitals.push(item);
                    const removeIndex = this.currentJxsqs.delHos.findIndex(o => o.id === item.id);
                    if (removeIndex !== -1)
                        this.currentJxsqs.delHos.splice(removeIndex, 1);
                    else
                        this.currentJxsqs.addHos.push(item);
                }
            },
            removeItem(item, type) {
                if (type === 'cert') {
                    const removeIndex1 = this.currentJxsqs.relatedCerts.findIndex(o => o.id === item.id);
                    if (removeIndex1 !== -1)
                        this.currentJxsqs.relatedCerts.splice(removeIndex1, 1);
                    const removeIndex2 = this.currentJxsqs.addCerts.findIndex(o => o.id === item.id);
                    if (removeIndex2 !== -1)
                        this.currentJxsqs.addCerts.splice(removeIndex2, 1);
                    else
                        this.currentJxsqs.delCerts.push(item);
                } else if (type === 'hos') {
                    const removeIndex1 = this.currentJxsqs.forHospitals.findIndex(o => o.id === item.id);
                    if (removeIndex1 !== -1)
                        this.currentJxsqs.forHospitals.splice(removeIndex1, 1);
                    const removeIndex2 = this.currentJxsqs.addHos.findIndex(o => o.id === item.id);
                    if (removeIndex2 !== -1)
                        this.currentJxsqs.addHos.splice(removeIndex2, 1);
                    else
                        this.currentJxsqs.delHos.push(item);
                }
            },
            handleCertSelectionChange(selection, row) {
                if (selection.includes(row)) {
                    this.addItem(row, 'cert');
                }
                else {
                    this.removeItem(row, 'cert');
                }
            },
            handleCertsSelectAll(selection) {
                if (selection.includes(this.provMfrsCertsList[0])) {
                    for (let item of selection) {
                        this.addItem(item, 'cert');
                    }
                } else {
                    for (let item of this.provMfrsCertsList) {
                        this.removeItem(item, 'cert');
                    }
                }
            },
            handleHosSelectionChange(selection, row) {
                if (selection.includes(row)) {
                    this.addItem(row, 'hos');
                }
                else {
                    this.removeItem(row, 'hos');
                }
            },
            handleHosSelectAll(selection) {
                if (selection.includes(this.basHosList[0])) {
                    for (let item of selection) {
                        this.addItem(item, 'hos');
                    }
                } else {
                    for (let item of this.basHosList) {
                        this.removeItem(item, 'hos');
                    }
                }
            },
            debounceQuerySearchMfrsAsync: debounce(500, function (queryString) {
                this.axios.get(this.baseURL + '/getBasMfrs', {
                    params: {
                        'mfrsName': queryString
                    }
                }).then(res => {
                    this.mfrsSearchData = res.data.data;
                    this.mfrsSearchLoading = false;
                }, err => {
                    this.$message.error('数据异常，请联系管理员。');
                    this.mfrsSearchLoading = false;
                })
            }),
            debounceQuerySearchCompanyAsync: debounce(500, function (queryString) {
                this.axios.get(this.baseURL + '/getBasCompany', {
                    params: {
                        'name': queryString
                    }
                }).then(res => {
                    this.superiorCompanySearchData = res.data.data;
                    this.superiorCompanySearchLoading = false;
                }, err => {
                    this.$message.error('数据异常，请联系管理员。');
                    this.superiorCompanySearchLoading = false;
                })
            }),
            debounceQuerySearchZjzjCompanyAsync: debounce(500, function (queryString) {
                this.axios.get(this.baseURL + '/getZjzjCompany', {
                    params: {
                        'name': queryString
                    }
                }).then(res => {
                    this.zjzjSuperiorCompanySearchData = res.data.data;
                    this.zjzjSuperiorCompanySearchLoading = false;
                }, err => {
                    this.$message.error('数据异常，请联系管理员。');
                    this.zjzjSuperiorCompanySearchLoading = false;
                })
            }),
            querySearchMfrsAsync(queryString) {
                if (queryString !== '') {
                    this.mfrsSearchLoading = true;
                    this.debounceQuerySearchMfrsAsync(queryString);
                }
            },
            querySearchSuperiorCompanyAsync(queryString) {
                if (queryString !== '') {
                    this.superiorCompanySearchLoading = true;
                    this.debounceQuerySearchCompanyAsync(queryString);
                }
            },
            querySearchZjzjSuperiorCompanyAsync(queryString) {
                if (queryString !== '') {
                    this.zjzjSuperiorCompanySearchLoading = true;
                    this.debounceQuerySearchZjzjCompanyAsync(queryString);
                }
            },
            handleFreezeRemote() {
                this.$set(this, 'remote', false);
                this.$nextTick(function () {
                    this.$set(this, 'remote', true);
                });
            },
            handleDlgMfrsSelect(item) {
                if (item) {
                    const mfrs = this.mfrsSearchData.find(o => o.id === item);
                    if (mfrs) {
                        this.currentJxsqs.mfrsId = mfrs.id;
                        this.currentJxsqs.mfrsName = mfrs.mfrsName;
                        this.getProvRegInfo()
                    }
                }
            },
            handleDlgSuperiorCompanySelect(item) {
                if (item) {
                    const mfrs = this.superiorCompanySearchData.find(o => o.id === item);
                    if (mfrs) {
                        this.currentJxsqs.chiefId = mfrs.id;
                        this.currentJxsqs.superiorMfrsName = mfrs.mfrsName;
                    }
                }
            },
//            handleDlgZjzjSuperiorCompanySelect(item) {
//                if (item) {
//                    const company = this.zjzjSuperiorCompanySearchData.find(o => o.id === item);
//                    if (company) {
//                        this.currentZjzj.chiefId = company.id;
//                        this.currentZjzj.name = company.mfrsName;
//                    }
//                }
//            },
            getProvRegInfo(sqsId) {
                this.cLoading = true;
                let params = {
                    'mfrsId': this.currentJxsqs.mfrsId,
                    'certName': this.certSearchStr,
                    'pageNum': this.cPageNum
                };
                if (sqsId) {
                    params.sqsId = sqsId;
                }
                this.axios.get(this.baseURL + '/getProvRegInfo', {
                    params
                }).then(res => {
                    if (res.data.code >= 0) {
                        this.provMfrsCertsList = res.data.data.data;
                        this.provMfrsCertsList.forEach(item => item.mfrsName = this.currentJxsqs.mfrsName);
                        this.cTotal = res.data.data.total;
//                        this.$nextTick(() => {
//                            if (this.currentJxsqs.relatedCerts) {
//                                this.currentJxsqs.relatedCerts.forEach(row => {
//                                    let index = this.provMfrsCertsList.findIndex(item => item.id === row.id);
//                                    if (index !== -1) {
//                                        this.$refs.certsTable.toggleRowSelection(this.provMfrsCertsList[index]);
//                                    }
//                                })
//                            }
//                        });
                        this.cLoading = false;
                    } else {
                        this.$message.error('获取数据失败。' + res.data.msg.substring(0, 50) + '...');
                        this.cLoading = false;
                    }
                }).catch(err => {
                    this.$message.error('数据异常，请联系管理员。')
                    this.cLoading = false;
                })
            },
            getBasHos(sqsId) {
                let params = {
                    'hosName': this.hosSearchStr,
                    'pageNum': this.hPageNum
                };
                if (sqsId) {
                    params.sqsId = sqsId;
                }
                this.axios.get(this.baseURL + '/getBasHos', {
                    params
                }).then(res => {
                    if (res.data.code >= 0) {
                        this.basHosList = res.data.data.data;
                        this.hTotal = res.data.data.total;
                    } else {
                        this.$message.error('获取数据失败。' + res.data.msg.substring(0, 50) + '...');
                    }
                }).catch(err => {
                    this.$message.error('数据异常，请联系管理员。')
                })
            },
            getJxsqsInfo() {
                let params = {
                    'mfrsName': this.searchMfrsName,
                    'pageNum': this.jPageNum,
                    'pageSize': this.jPageSize
                };
                if (this.searchExpdtBeginDate !== '')
                    params.start = Date.parse(this.searchExpdtBeginDate);
                if (this.searchExpdtEndDate !== '')
                    params.end = Date.parse(this.searchExpdtEndDate);
                this.jLoading = true;
                this.axios.get(this.baseURL + '/getJxsqs', {
                    params
                }).then(res => {
                    if (res.data.code >= 0) {
                        this.jxsqsList = res.data.data.data;
                        this.jTotal = res.data.data.total;
                        this.jLoading = false;
                    } else {
                        this.$message.error('获取数据失败。' + res.data.msg.substring(0, 50) + '...');
                        this.jLoading = false;
                    }
                }).catch(err => {
                    this.$message.error('数据异常，请联系管理员。');
                    this.jLoading = false;
                });
            },
            picMouseEnter(event) {
                this.showBigPic = true;
            },
            handleCertSearch(event) {
                this.getProvRegInfo();
            },
            handleHosSearch(event) {
                this.getBasHos();
            },
            newJxsqs() {
                return {
                    id: '',
                    provId: '',
                    mfrsId: '',
                    mfrsName: '',
                    isMfrs: 0,
                    chiefId: '',
                    superiorMfrsName: '',
                    saleArea: '',
                    expdtBeginDate: '',
                    expdtEndDate: '',
                    isValiding: '0',
                    filePath: '',
                    version: 0,
                    relatedCerts: [],
                    forHospitals: [],
                    addCerts: [],
                    delCerts: [],
                    addHos: [],
                    delHos: [],
                    zjzjList: [],
                }
            }
        },
        mounted() {
            this.getJxsqsInfo();
        },
        created() {
            this.currentJxsqs = this.newJxsqs();
        }
    }
</script>

<style>
    #provJxsqs .font {
        font-size: 12px;
    }

    #provJxsqs .el-date-editor.el-input {
        width: 120px;
    }

    .provJxsqs-tableheight {
        height: calc(100% - 100px);
      /*  min-height: 400px;*/
    }

    #provJxsqs .expiring {
        color: #ff00ff;
    }

    #provJxsqs .expired {
        color: red;
    }

    #provJxsqs .pic-uploader .el-upload {
        border: 1px dashed #9b9b9b;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    #provJxsqs .upload-background .el-upload::after {
        content: '';
        display: block;
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        background-size: 100%;
        background-image: url("../../assets/images/logo_200x200.png");
        background-repeat: no-repeat;
        opacity: 0.20;
    }

    #provJxsqs .pic-uploader .el-upload:hover {
        border-color: #20a0ff;
    }

    #provJxsqs .pic-uploader .item-status-label {
        position: absolute;
        right: -15px;
        top: -6px;
        width: 40px;
        height: 24px;
        background: #13ce66;
        text-align: center;
        transform: rotate(45deg);
        box-shadow: 0 0 1pc 1px rgba(0, 0, 0, .2);
    }

    #provJxsqs .pic-uploader .item-status-label .upload-success {
        font-size: 12px;
        margin-top: 11px;
        transform: rotate(-45deg) scale(.8);
        color: #fff;
    }

    #provJxsqs .pic-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }

    #provJxsqs .pic {
        min-width: 50px;
        min-height: 50px;
        max-width: 178px;
        /*height: 178px;*/
        max-height: 178px;
        display: block;
    }

    img.tooltip-pic {
        max-width: 400px;
    }

    #provJxsqs .el-form-item__error {
        display: none;
    }

    #provJxsqs .el-upload-list__item, .el-upload-list__item:first-child {
        margin-top: 0;
        margin-bottom: 5px;
    }

    #provJxsqs .is-error {
        margin-bottom: 22px;
    }

    #provJxsqs .el-form-item__error {
        display: block;
    }

    #provJxsqs .inline {
        display: inline-block;
    }

    #provJxsqs .right {
        float: right;
    }
</style>
