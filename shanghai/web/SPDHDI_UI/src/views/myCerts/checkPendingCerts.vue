<template>
      <div id="checkPendingCerts" style="height: 100%;">
          <el-col :span="4" style="height: 100%;">
            <div style="margin-bottom: 5px">
              <el-input placeholder="供应商名称" suffix-icon="el-icon-search"  v-model="inputCertsSearch"
                        @keyup.enter.native="handleCertSearch" :on-icon-click="handleCertSearch">
              </el-input>
            </div>
            <el-table :show-header="false" border ref="singleTable" class="checkPendingCertstableheight" :data="regCertsData" style="width: 100%;" highlight-current-row
                      @row-click="getPushedGoodsList" @current-change="handleCurrentChange" v-loading="regCertLoading">
              <el-table-column prop="cname" label="供应商列表" show-overflow-tooltip>
              </el-table-column>
            </el-table>
            <el-pagination small  @current-change="handleRCurrentChange" :current-page.sync="rPageNum" style="width:100%"
                           :page-sizes="[10, 15, 20, 30]" :page-size="rPageSize" layout="sizes, prev, next" :total="rTotal">
            </el-pagination>
          </el-col>
          <el-col :span="20" id="card" style="padding-left: 5px; height: 95.7%;">
              <el-card class="box-card" style="width: 100%;margin-bottom: 0.4%;" v-for="d in pushedGoodsData" :key="d.id" v-loading="regCertLoading2"><!-- background-color: rgba(218, 191, 127, 0.11); -->
                <div slot="header" class="clearfix" >
                  <el-col :span="20">
                    <el-col :span="12">
                      <span class="regCertStyle" style="margin-left: -5px;">注册证号：{{d.certificateCode}}</span>
                      <span class="">【{{d.fillDate}}】</span>
                    </el-col>
                    <el-col :span="12">
                      <span class="regCertStyle" style="">生产厂家：{{d.mfrsName}}</span>
                    </el-col>
                  </el-col>
                  <el-col :span="4">
                    <el-button-group style="align: right">
                      <el-button style="line-height: 14px" @click="retrospect(d)" type="primary" >溯源</el-button>
                      <el-button style="line-height: 14px" @click="receiveCerts(d)" type="success" >接受</el-button>
                      <el-button style="line-height: 14px" @click="refuseCerts(d)" type="warning" >打回</el-button>
                    </el-button-group>
                  </el-col>
                </div>
                <el-table :data="d.goodsPushQRs" border >
                  <el-table-column prop="goodsName" header-align="center" align="center" label="产品名称" show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column prop="goodsGg" header-align="center" align="center" label="产品规格" show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column prop="unit" header-align="center" align="center" label="单位" show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column prop="hitCode" header-align="center" align="center" label="阳光采购平台编码" show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column prop="hitPrice" header-align="center" align="center" label="中标价格" show-overflow-tooltip>
                  </el-table-column>
                </el-table>
              </el-card>
              <el-pagination small  style="padding-right: 20px;text-align: right;"
                             :current-page.sync="pPageNum"
                             layout="total, sizes, prev, pager, next" :page-sizes="[10, 15, 20, 30]" :page-size="pPageSize" :total="pTotal">
              </el-pagination>
            </el-col>
          <el-dialog :title="retrospectDailogTitle"  close="onClose"  :visible.sync="retrospectDailog" :close-on-click-modal="false" width="80%">
            <el-table :data="retrospectObject" style="">
              <el-table-column prop="productName" min-width="100" header-align="center" align="center" label="注册证产品名称" show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="certificateCode" min-width="100" header-align="center" align="center" label="注册证号" show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="expdtBeginDate" header-align="center" align="center" label="生效日期" :formatter="formatterBeginDate" show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="expdtEndDate" header-align="center" align="center" label="截止日期" :formatter="formatterEndDate" show-overflow-tooltip>
              </el-table-column>
              <el-table-column label="操作" width="72">
                <template slot-scope="scope">
                  <el-button size="mini" @click="checkRegistCertImg" type="text">看图</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="checkCertOutBox">
            <el-row>
              <el-col :span="1">
                <div>
                  <div style="text-align: center;">
                    <i class="fa fa-industry checkCertColor" style="font-size: 26px;" aria-hidden="true"></i>
                  </div>
                  <div>
                    <hr class="hr4 checkCertColor">
                  </div>
                </div>
              </el-col>
              <el-col :span="21">
                  <div><span class="checkCertsNode checkCertColor">生产厂商</span></div>
                  <div class="checkCertsBox">
                    <div style="width: 100%;">
                      <div class="checkCertsBaseInfo">
                        <el-col :span="4" style="line-height: 32px;margin-left: 1%"><span>基本信息</span></el-col>
                        <el-col :span="20"></el-col>
                      </div>
                      <div style="width: 100%;height: 110px;">
                        <el-col :span="12" style="margin-left: 10px;">
                          <el-row style="line-height: 36px;">厂家名称：<span>{{frwtObj.mfrsName}}</span></el-row>
                          <el-row style="line-height: 36px;">注册号：<span>{{myyzzObj.certCode}}</span></el-row>
                          <el-row style="line-height: 36px;">生效日期：<span>{{myyzzObj.expdtBeginDate}}</span></el-row>
                        </el-col>
                        <el-col :span="6">
                          <el-row style="line-height: 36px;">厂家类型：<span>{{frwtObj.mfrsKind}}</span></el-row>
                          <el-row style="line-height: 36px;">
                            <el-button type="text" @click="checkMfrs">查看资质</el-button>
                            <el-button type="text" @click="checkJxsqs">经销授权书</el-button>
                          </el-row>
                          <el-row style="line-height: 36px;">截止日期：<span>{{myyzzObj.expdtEndDate}}</span></el-row>
                        </el-col>
                      </div>
                    </div>
                  </div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="1">
                <div>
                  <div style="text-align: center;">
                    <i class="fa fa-address-card-o checkCertColor" style="font-size: 26px;" aria-hidden="true"></i>
                  </div>
                  <div>
                    <hr class="hr4">
                  </div>
                  <div style="text-align: center;">
                    <i class="fa fa-hospital-o checkCertColor" style="font-size: 30px;" aria-hidden="true"></i>
                  </div>
                </div>
              </el-col>
              <el-col :span="21">
                <div><span class="checkCertsNode checkCertColor">供应商</span></div>
                <div class="checkCertsBox">
                  <div style="width: 100%;">
                    <div class="checkCertsBaseInfo">
                      <el-col :span="4" style="line-height: 32px;margin-left: 1%"><span>基本信息</span></el-col>
                      <el-col :span="20"></el-col>
                    </div>
                    <div style="width: 100%;height: 110px;">
                      <el-col :span="12" style="margin-left: 10px;">
                        <el-row style="line-height: 36px;">供应商名称：<span>{{provInfo.cname}}</span></el-row>
                        <el-row style="line-height: 36px;">注册号：<span>{{yyzzObj.certCode}}</span></el-row>
                        <el-row style="line-height: 36px;">生效日期：<span>{{yyzzObj.expdtBeginDate}}</span></el-row>
                      </el-col>
                      <el-col :span="6">
                        <el-row style="line-height: 36px;">联系人：<span>{{provInfo.linkman}}</span></el-row>
                        <el-row style="line-height: 36px;">电话：<span>{{provInfo.contactWay}}</span>
                        </el-row>
                        <el-row style="line-height: 36px;">截止日期：<span>{{yyzzObj.expdtEndDate}}</span></el-row>
                      </el-col>
                      <el-col :span="4">
                        <el-row style="line-height: 36px;">
                          <el-button @click="checkCompanyCerts" type="text">查看资质</el-button>
                        </el-row>
                        <el-row style="line-height: 36px;">
                          <el-button @click="checkFrwtCerts" type="text">法人委托书</el-button>
                        </el-row>
                        <el-row style="line-height: 36px;">
                          <el-button @click="checkCnsCerts" type="text">承诺书</el-button>
                        </el-row>
                      </el-col>
                    </div>
                  </div>
                </div>
                <div style="margin-top: 6px;"><span class="checkCertsNode checkCertColor">{{hosName}}</span></div>
              </el-col>
            </el-row>
            </div>
          </el-dialog>
          <!-- 经销授权书 -->
          <el-dialog :title="jxsqsImgDailogTitle"  close="onClose"  :visible.sync="jxsqsImgDailog" :close-on-click-modal="false" >
            <template v-if="dialogJxsqsImageUrl">
              <img width="100%" :src="dialogJxsqsImageUrl" alt="">
            </template>
            <template v-else>
              <img src="../../assets/images/noImg.jpg"  class="image">
            </template>
          </el-dialog>
          <!-- 法人委托书 -->
          <el-dialog :title="frwtImgDailogTitle"  close="onClose"  :visible.sync="frwtImgDailog" :close-on-click-modal="false" >
            <template v-if="dialogFrwtImageUrl">
              <img width="100%" :src="dialogFrwtImageUrl" alt="">
            </template>
            <template v-else>
              <img src="../../assets/images/noImg.jpg"  class="image">
            </template>
          </el-dialog>
          <!-- 承诺书 -->
          <el-dialog :title="cnsImgDailogTitle"  close="onClose"  :visible.sync="cnsImgDailog" :close-on-click-modal="false" >
            <template v-if="dialogCnsImageUrl">
              <img width="100%" :src="dialogCnsImageUrl" alt="">
            </template>
            <template v-else>
              <img src="../../assets/images/noImg.jpg"  class="image">
            </template>
          </el-dialog>
          <el-dialog :title="mfrsDailogTitle"  close="onClose"  :visible.sync="mfrsDailog" :close-on-click-modal="false" >
            <div style="margin-left: 5%;height: 100%;margin-bottom: 44%;margin-top: 3%;">
              <el-col :span="6" v-for="(d) in myyzzImgArray" v-bind:key="d.id" class="certsCard">
                <div style="margin-top: 3%;">
                  <div class="ImageCard">
                    <img v-if="d.filePath" :src="imgBasePath+d.filePath" class="certList">
                    <i v-else class="el-icon-plus"></i>
                  </div>
                </div>
                <div>
                  <div class="certListStyle">
                    <span class="certWordsStyle">营业执照</span>
                    <span><el-button type="text" class="button certButtonStyle" @click="checkMfrsImg(d)">查看</el-button></span>
                  </div>
                  <div class="certCodes">
                    <el-input placeholder="注册号" v-model="myyzzObj.certCode" readonly ></el-input>
                  </div>
                  <div class="block" style="margin-left: 3%;">
                    <span class="demonstration"></span>
                    <el-date-picker
                            style="width: 96%;margin-bottom: 2px; margin-top: 1%;"
                            size = "mini"  readonly
                            v-model="myyzzObj.expdtBeginDate"
                            type="date"
                            placeholder="生效日期">
                    </el-date-picker>
                  </div>
                  <div class="block" style="margin-left: 3%;">
                              <span class="">
                                  <el-checkbox v-model="myyzzChecked" disabled >
                                      <span style="font-size: 10px;">是否长期有效</span>
                                  </el-checkbox>
                              </span>
                    <el-date-picker
                            style="width: 96%;margin-bottom: 5%;"
                            size = "mini"  readonly
                            v-model="myyzzObj.expdtEndDate"
                            type="date"
                            placeholder="截止日期">
                    </el-date-picker>
                  </div>
                </div>
              </el-col>
              <el-col :span="6" v-for="(d) in mjyxkImgArray" v-bind:key="d.id" class="certsCard">
                <div style="margin-top: 3%;">
                  <div class="ImageCard">
                    <img v-if="d.filePath" :src="imgBasePath+d.filePath" class="certList">
                    <i v-else class="el-icon-plus"></i>
                  </div>
                </div>
                <div>
                  <div class="certListStyle">
                    <span class="certWordsStyle">经营许可证</span>
                    <span><el-button type="text" class="button certButtonStyle" @click="checkMfrsImg(d)">查看</el-button></span>
                  </div>
                  <div class="certCodes">
                    <el-input placeholder="登记证号" v-model="mjyxkObj.certCode" readonly ></el-input>
                  </div>
                  <div class="block" style="margin-left: 3%;">
                    <span class="demonstration"></span>
                    <el-date-picker
                            style="width: 96%;margin-bottom: 2px; margin-top: 1%;"
                            size = "mini"  readonly
                            v-model="mjyxkObj.expdtBeginDate"
                            type="date"
                            placeholder="生效日期">
                    </el-date-picker>
                  </div>
                  <div class="block" style="margin-left: 3%;">
                              <span class="">
                                  <el-checkbox v-model="mjyxkChecked" disabled >
                                      <span style="font-size: 10px;">是否长期有效</span>
                                  </el-checkbox>
                              </span>
                    <el-date-picker
                            style="width: 96%;margin-bottom: 5%;"
                            size = "mini"  readonly
                            v-model="mjyxkObj.expdtEndDate"
                            type="date"
                            placeholder="截止日期">
                    </el-date-picker>
                  </div>
                </div>
              </el-col>
              <el-col :span="6" v-for="(d) in mswdjImgArray" v-bind:key="d.id" class="certsCard">
                <div style="margin-top: 3%;">
                  <div class="ImageCard">
                    <img v-if="d.filePath" :src="imgBasePath+d.filePath" class="certList">
                    <i v-else class="el-icon-plus" style="background-image: image(../../assets/images/noImg.jpg)"></i>
                  </div>
                </div>
                <div>
                  <div class="certListStyle">
                    <span class="certWordsStyle">税务登记证</span>
                    <span><el-button type="text" class="button certButtonStyle" @click="checkMfrsImg(d)">查看</el-button></span>
                  </div>
                  <div class="certCodes">
                    <el-input placeholder="登记证号" v-model="mswdjObj.certCode" readonly ></el-input>
                  </div>
                  <div class="block" style="margin-left: 3%;">
                    <span class="demonstration"></span>
                    <el-date-picker
                            style="width: 96%;margin-bottom: 2px; margin-top: 1%;"
                            size = "mini"  readonly
                            v-model="mswdjObj.expdtBeginDate"
                            type="date"
                            placeholder="生效日期">
                    </el-date-picker>
                  </div>
                  <div class="block" style="margin-left: 3%;">
                              <span class="">
                                  <el-checkbox v-model="mswdjChecked" disabled >
                                      <span style="font-size: 10px;">是否长期有效</span>
                                  </el-checkbox>
                              </span>
                    <el-date-picker
                            style="width: 96%;margin-bottom: 5%;"
                            size = "mini"  readonly
                            v-model="mswdjObj.expdtEndDate"
                            type="date"
                            placeholder="截止日期">
                    </el-date-picker>
                  </div>
                </div>
              </el-col>
            </div>
          </el-dialog>
          <el-dialog :title="mfrsImgDailogTitle"  close="onClose"  :visible.sync="mfrsImgDailog" :close-on-click-modal="false" >
            <img width="100%" :src="dialogMfrsImageUrl" alt="">
          </el-dialog>
          <el-dialog :title="companyDailogTitle"  close="onClose"  :visible.sync="companyDailog" :close-on-click-modal="false" >
            <div style="margin-left: 5%;height: 100%;margin-bottom: 44%;margin-top: 3%;">
              <el-col :span="6" v-for="(d) in yyzzImgArray" v-bind:key="d.id" class="certsCard">
                <div style="margin-top: 3%;">
                  <div class="ImageCard">
                    <img v-if="d.filePath" :src="imgBasePath+d.filePath" class="certList">
                    <i v-else class="el-icon-plus"></i>
                  </div>
                </div>
                <div>
                  <div class="certListStyle">
                    <span class="certWordsStyle">营业执照</span>
                    <span><el-button type="text" class="button certButtonStyle" @click="checkCompanyImg(d)">查看</el-button></span>
                  </div>
                  <div class="certCodes">
                    <el-input placeholder="注册号" v-model="yyzzObj.certCode" readonly ></el-input>
                  </div>
                  <div class="block" style="margin-left: 3%;">
                    <span class="demonstration"></span>
                    <el-date-picker
                            style="width: 96%;margin-bottom: 2px; margin-top: 1%;"
                            size = "mini"  readonly
                            v-model="yyzzObj.expdtBeginDate"
                            type="date"
                            placeholder="生效日期">
                    </el-date-picker>
                  </div>
                  <div class="block" style="margin-left: 3%;">
                          <span class="">
                              <el-checkbox v-model="yyzzChecked" disabled >
                                  <span style="font-size: 10px;">是否长期有效</span>
                              </el-checkbox>
                          </span>
                    <el-date-picker
                            style="width: 96%;margin-bottom: 5%;"
                            size = "mini"  readonly
                            v-model="yyzzObj.expdtEndDate"
                            type="date"
                            placeholder="截止日期">
                    </el-date-picker>
                  </div>
                </div>
              </el-col>
              <el-col :span="6" v-for="(d) in jyxkImgArray" v-bind:key="d.id" class="certsCard">
                <div style="margin-top: 3%;">
                  <div class="ImageCard">
                    <img v-if="d.filePath" :src="imgBasePath+d.filePath" class="certList">
                    <i v-else class="el-icon-plus"></i>
                  </div>
                </div>
                <div>
                  <div class="certListStyle">
                    <span class="certWordsStyle">经营许可证</span>
                    <span><el-button type="text" class="button certButtonStyle" @click="checkCompanyImg(d)">查看</el-button></span>
                  </div>
                  <div class="certCodes">
                    <el-input placeholder="登记证号" v-model="jyxkObj.certCode" readonly ></el-input>
                  </div>
                  <div class="block" style="margin-left: 3%;">
                    <span class="demonstration"></span>
                    <el-date-picker
                            style="width: 96%;margin-bottom: 2px; margin-top: 1%;"
                            size = "mini"  readonly
                            v-model="jyxkObj.expdtBeginDate"
                            type="date"
                            placeholder="生效日期">
                    </el-date-picker>
                  </div>
                  <div class="block" style="margin-left: 3%;">
                          <span class="">
                              <el-checkbox v-model="jyxkChecked" disabled >
                                  <span style="font-size: 10px;">是否长期有效</span>
                              </el-checkbox>
                          </span>
                    <el-date-picker
                            style="width: 96%;margin-bottom: 5%;"
                            size = "mini"  readonly
                            v-model="jyxkObj.expdtEndDate"
                            type="date"
                            placeholder="截止日期">
                    </el-date-picker>
                  </div>
                </div>
              </el-col>
              <el-col :span="6" v-for="(d) in swdjImgArray" v-bind:key="d.id" class="certsCard">
                      <div style="margin-top: 3%;">
                        <div class="ImageCard">
                          <img v-if="d.filePath" :src="imgBasePath+d.filePath" class="certList">
                          <i v-else class="el-icon-plus"></i>
                        </div>
                      </div>
                      <div>
                        <div class="certListStyle">
                          <span class="certWordsStyle">税务登记证</span>
                          <span><el-button type="text" class="button certButtonStyle" @click="checkCompanyImg(d)">查看</el-button></span>
                        </div>
                        <div class="certCodes">
                          <el-input placeholder="登记证号" v-model="swdjObj.certCode" readonly ></el-input>
                        </div>
                        <div class="block" style="margin-left: 3%;">
                          <span class="demonstration"></span>
                          <el-date-picker
                                  style="width: 96%;margin-bottom: 2px; margin-top: 1%;"
                                  size = "mini"  readonly
                                  v-model="swdjObj.expdtBeginDate"
                                  type="date"
                                  placeholder="生效日期">
                          </el-date-picker>
                        </div>
                        <div class="block" style="margin-left: 3%;">
                              <span class="">
                                  <el-checkbox v-model="swdjChecked" disabled >
                                      <span style="font-size: 10px;">是否长期有效</span>
                                  </el-checkbox>
                              </span>
                          <el-date-picker
                                  style="width: 96%;margin-bottom: 5%;"
                                  size = "mini"  readonly
                                  v-model="swdjObj.expdtEndDate"
                                  type="date"
                                  placeholder="截止日期">
                          </el-date-picker>
                        </div>
                      </div>
                  </el-col>
            </div>
          </el-dialog>
          <el-dialog :title="companyImgDailogTitle"  close="onClose" :visible.sync="companyImgDailog" :close-on-click-modal="false" >
            <img width="100%" :src="dialogcompanyImageUrl" alt="">
          </el-dialog>
          <el-dialog :title="registCertDailogTitle"  close="onClose"  :visible.sync="registCertDailog" :close-on-click-modal="false" >
            <template v-if="registCertImgArray.length<1">
              <img src="../../assets/images/noImg.jpg"  class="image">
            </template>
            <template v-else>
              <div style="margin-bottom: 10%;margin-left: 8%;margin-top: 2%;height: 100%;">
                <el-row>
                  <el-col :span="6" v-for="(d,index) in registCertImgArray" v-bind:key="d.id" style="margin-bottom: 1%;margin-right: 1%;">
                    <el-card :body-style="{ padding: '10px' }">
                      <img :src='registImage[index]' style="width: 100%;height: 130px;">
                      <div style="padding: 14px;background-color: rgb(238, 241, 246);">
                        <el-row>
                          <span style="font-weight: bold;">注册证</span>
                          <el-button type="text" class="button" @click="checkRegistCert(d)">查看</el-button>
                        </el-row>
                      </div>
                    </el-card>
                  </el-col>
                </el-row>
              </div>
            </template>
          </el-dialog>
          <el-dialog :title="registCertImgDailogTitle"  close="onClose"  :visible.sync="registCertImgDailog" :close-on-click-modal="false" >
            <img width="100%" :src="registCertImageUrl" alt="">
          </el-dialog>
      </div>
</template>
<script>
    import { fileServer } from '../../common/js/constance';
    export default {
        data() {
            return {
                swdjChecked:false,
                jyxkChecked:false,
                yyzzChecked:false,
                editDisable:false,
                cyyzzCertCode:'',
                mswdjChecked:false,
                mjyxkChecked:false,
                myyzzChecked:false,
                registCertDailogTitle:'',
                registCertDailog:false,
                registCertImageUrl:'',
                registCertImgDailogTitle:'',
                registCertImgDailog:false,
                registCertImgArray:[],
                mfrsDailogTitle:'',
                jxsqsImgDailogTitle:'',
                frwtImgDailogTitle:'',
                cnsImgDailogTitle:'',
                companyDailogTitle:'',
                companyImgDailogTitle:'',
                companyDailog:false,
                companyImgDailog:false,
                mfrsDailog:false,
                jxsqsImgDailog:false,
                frwtImgDailog:false,
                cnsImgDailog:false,
                mfrsImgDailogTitle:'',
                mfrsImgDailog:false,
                dialogcompanyImageUrl:'',
                dialogMfrsImageUrl:'',
                dialogJxsqsImageUrl:'',
                dialogFrwtImageUrl:'',
                dialogCnsImageUrl:'',
                registImage:[],
                myyzzImgArray:[],
                mjyxkImgArray:[],
                mswdjImgArray:[],
                myyzzObj:'',
                mjyxkObj:'',
                mswdjObj:'',
                yyzzImgArray:[],
                jyxkImgArray:[],
                swdjImgArray:[],
                yyzzObj:'',
                jyxkObj:'',
                swdjObj:'',
                frwtObj:{},
                provInfo:{},
                jxsqs:{},
                frwt:{},
                cns:{},
                registCertObj:{},
                hosName:'',// 溯源时显示的医院名称
                retrospectObject:[],// 注册证溯源对象
                retrospectDailog:false,// 注册证溯源Dailog
                retrospectDailogTitle:'',// 注册证溯源DailogTitle
                currentRow:{},// 选中左侧供应商列表当前行对象
                pushStatus:'',// 注册证推送状态（对供应商推送的注册证作'接受'和'打回'操作时用到）
                rTotal: 0,
                pTotal: 0,
                rPageNum:1,
                rPageSize: 20,
                pPageNum: 1,
                pPageSize: 20,
                selectMfrsId: '',
                inputCertsSearch: "",// 搜索左侧供应商列表的文本框
                currentMfrs: {},
                regCertsData: [],// 左侧供应商列表数组数据
                pushedGoodsData: [],// 右侧供应商推送注册证列表数组数据
                regCertLoading: false,// 搜索左侧供应商列表时Loading
                regCertLoading2: false,// 搜索右侧供应商推送注册证列表时Loading
                baseURL: '/spdHDIService/myInfo'
            }
        },
        methods: {
            /**
             * 获取待审证件供应商列表
             */
            getRegCertsList:function (pageNum) {
                var param = {
                    orderBy: "",
                    queryObject: {pushStatus:10},
                    page: pageNum,
                    rows: this.rPageSize
                };
                this.regCertLoading = true;
                this.axios.post(this.baseURL + '/provRegistInfoPush/pushedRegCertsProvListByPage', param).then(res => {
                    this.regCertLoading = false;

                    this.regCertsData = res.data.data.data;
                    this.rTotal = res.data.data.total;
                    // this.rPageNum = res.data.data.pageNum;
                    this.$nextTick(function () {
                        this.setCurrent(this.regCertsData[0]);
                    });
                }, err =>{
                    this.regCertLoading = false;
                })
            },
            setCurrent(row) {

                this.currentRow = row;
                this.$refs.singleTable.setCurrentRow(row);
            },
            handleCurrentChange(){
                this.getPushedGoodsList(this.currentRow);
            },

            /**
             * 注册证搜索框
             */
            handleCertSearch () {

                var param = {
                    orderBy: "",
                    queryObject: {cname : this.inputCertsSearch,pushStatus:'10'},
                    page: this.rPageNum,
                    rows: this.rPageSize
                };
                this.regCertLoading = true;
                this.axios.post(this.baseURL + '/provRegistInfoPush/pushedRegCertsProvListByPage', param).then(res => {
                    this.regCertLoading = false;

                    this.regCertsData = res.data.data.data;
                    this.rTotal = res.data.data.total;
                    // this.rPageNum = res.data.data.pageNum;
                }, err =>{
                    this.regCertLoading = false;
                })
            },
            /**
             * 获取推送注册证产品列表
             */
            getPushedGoodsList(row){

                this.currentRow = row;
                var param = {
                    orderBy: "",
                    queryObject: {
                        provId : row.provId,
                        hosId : row.hosId,
                        cname : this.inputCertsSearch
                    },
                    page: this.pPageNum,
                    rows: this.pPageSize
                };
                this.regCertLoading2 = true;
                this.axios.post(this.baseURL + '/provRegistInfoPush/pushedGoodsProvListByPage', param).then(res => {
                    this.regCertLoading2 = false;

                    this.pushedGoodsData = res.data.data.data;
                    this.pTotal = res.data.data.total;
                    // this.pPageNum = res.data.data.pageNum;
                }, err =>{
                    this.regCertLoading2 = false;
                })
            },
            /**
             * 接受推送注册证
             */
            receiveCerts(d){
                d.pushStatus = 30;
                this.updatePushStatus(d);
            },
            /**
             * 驳回推送注册证
             */
            refuseCerts(d){
                d.pushStatus = 20;
                this.updatePushStatus(d);
            },
            /**
             * 更改注册证推送状态
             */
            updatePushStatus(d){

                this.loading = true;
                this.axios.post(this.baseURL + '/provRegistInfoPush/update', d).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                        this.loading = false;

                        this.$message({message: '操作成功！', type: 'success'});
                        this.getPushedGoodsList(this.currentRow);
                    } else {
                        this.$message.error(res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            },
            /**
             * 溯源
             */
            retrospect(d){
                this.retrospectDailog = true;
                this.retrospectDailogTitle = '注册证溯源'+'【'+this.currentRow.cname+'】';
                this.retrospectObject[0] = d;
                this.getRetrospectMap(d);
            },
            /**
             * 溯源集合对象查询
             */
            getRetrospectMap(d){
                this.registCertObj = d;

                var param = {
                    queryObject: {
                        provId : d.provId,
                        hosId : d.hosId,
                        provRegistId : d.provRegistId,
                        mfrsId:d.mfrsId
                    },
                };
                this.axios.post(this.baseURL + '/provRegistInfoPush/getRetrospectMap', param).then(res => {

                    this.provInfo = res.data.data.provInfo;
                    this.registCertImgArray = res.data.data.registCertImg;
                    for(var i=0;i<this.registCertImgArray.length;i++){
                        this.registImage.push(this.imgBasePath + this.registCertImgArray[i].filePath);
                    }
                    this.jxsqs = res.data.data.jxsqs;
                    this.cns = res.data.data.cns;
                    this.frwt = res.data.data.frwt;
                    this.getProvCompanyInfo(this.provInfo);
                    this.getMfrsCertsInfo(res.data.data.mfrs[0]);
                }, err =>{
                })
            },
            /**
             * 查看厂家三证Dialog
             */
            checkMfrs (){

                this.mfrsDailogTitle = '厂家三证 查看' + '【'+ this.frwtObj.mfrsName +'】';
                this.mfrsDailog = true;
            },
            /**
             * 查看经销授权书Dialog
             */
            checkJxsqs (){

                this.dialogJxsqsImageUrl = this.imgBasePath + this.jxsqs.filePath;
                this.jxsqsImgDailogTitle = '经销授权书 查看' + '【'+ this.frwtObj.mfrsName +'】';
                this.jxsqsImgDailog = true;
            },
            /**
             * 查看法人委托书Dialog
             */
            checkFrwtCerts (){

                this.dialogFrwtImageUrl = this.imgBasePath + this.frwt.filePathWts;
                this.frwtImgDailogTitle = '法人委托书 查看' + '【'+ this.currentRow.cname +'】';
                this.frwtImgDailog = true;
            },
            /**
             * 查看承诺书Dialog
             */
            checkCnsCerts (){

                this.dialogCnsImageUrl = this.imgBasePath + this.cns.filePathWts;
                this.cnsImgDailogTitle = '承诺书 查看' + '【'+ this.currentRow.cname +'】';
                this.cnsImgDailog = true;
            },
            /**
             * 获取厂家三证图片对象信息
             */
            getMfrsCertsInfo (mfrs){

                if(mfrs){
                    this.frwtObj = mfrs;
                    this.frwtObj.mfrsKind = this.frwtObj.mfrsKind === '1'?'国产':'进口';
                    if(mfrs.basMfrsCertificateVos.length>0) {

                        // 营业执照对象
                        this.myyzzChecked = this.myyzzObj.isValiding == '1' ? true : false;
                        this.myyzzObj = mfrs.basMfrsCertificateVos.find(item => item.certKind == 1);
                        this.myyzzObj.expdtBeginDate = this.myyzzObj.expdtBeginDate == null? '' : this.myyzzObj.expdtBeginDate.substr(0,10);
                        this.myyzzObj.expdtEndDate = this.myyzzObj.expdtEndDate == null? '' : this.myyzzObj.expdtEndDate.substr(0,10);
                        this.myyzzObj.expdtEndDate = this.myyzzObj.isValiding == '1' ? '长期有效': this.myyzzObj.expdtEndDate;
                        // 营业执照图片数组
                        if(this.myyzzObj){
                            this.myyzzImgArray = this.myyzzObj.provMfrsCertificateImages;
                        }
                        // 经营许可证对象
                        this.mjyxkChecked = this.mjyxkObj.isValiding == '1' ? true : false;
                        this.mjyxkObj = mfrs.basMfrsCertificateVos.find(item => item.certKind == 2);
                        this.mjyxkObj.expdtBeginDate = this.mjyxkObj.expdtBeginDate == null? '' : this.mjyxkObj.expdtBeginDate.substr(0,10);
                        if(this.mjyxkObj){
                            // 经营许可证图片数组
                            this.mjyxkImgArray = this.mjyxkObj.provMfrsCertificateImages;
                        }
                        // 税务登记证对象
                        this.mswdjChecked = this.mswdjObj.isValiding == '1' ? true : false;
                        this.mswdjObj = mfrs.basMfrsCertificateVos.find(item => item.certKind == 3);
                        this.mswdjObj.expdtBeginDate = this.mswdjObj.expdtBeginDate == null? '' : this.mswdjObj.expdtBeginDate.substr(0,10);
                        if(this.mswdjObj){
                            // 税务登记证图片数组
                            this.mswdjImgArray = this.mswdjObj.provMfrsCertificateImages;
                        }
                    }
                }else{
                    this.frwtObj = {
                        mfrsName:'',
                        mfrsKind:'',
                    };
                }
            },
            /**
             * 查看厂家三证图片
             */
            checkMfrsImg (d){
                this.mfrsImgDailogTitle = '企业三证图片 查看' + '【'+ this.currentRow.cname +'】';
                this.mfrsImgDailog = true;
                this.dialogMfrsImageUrl = this.imgBasePath + d.filePath;
            },
            /**
             * 查看供应商三证Dialog
             */
            checkCompanyCerts (){

                this.companyDailogTitle = '供应商三证 查看' + '【'+ this.currentRow.cname +'】';
                this.companyDailog = true;
            },
            /**
             * 查看企业三证
             */
            checkCompanyImg (d){
                this.companyImgDailogTitle = '供应商三证图片 查看' + '【'+ this.currentRow.cname +'】';
                this.companyImgDailog = true;
                this.dialogcompanyImageUrl = this.imgBasePath + d.filePath;
            },
            /**
             * 获取供应商信息
             */
            getProvCompanyInfo (company){

                if(company.companyCertificateVos.length>0) {

                    // 营业执照对象
                    this.yyzzObj = company.companyCertificateVos.find(item => item.certKind == 1);
                    if(this.yyzzObj){
                        this.yyzzChecked = this.yyzzObj.isValiding == '1' ? true : false;
                        this.yyzzObj.expdtBeginDate = this.yyzzObj.expdtBeginDate == null? '' : this.yyzzObj.expdtBeginDate.substr(0,10);
                        this.yyzzObj.expdtEndDate = this.yyzzObj.expdtEndDate == null? '' : this.yyzzObj.expdtEndDate.substr(0,10);
                        // 营业执照图片数组
                        this.yyzzImgArray = this.yyzzObj.basCompanyCertificateImages;
                    }
                    // 经营许可证对象
                    this.jyxkObj = company.companyCertificateVos.find(item => item.certKind == 2);
                    if(this.jyxkObj){
                        this.jyxkChecked = this.jyxkObj.isValiding == '1' ? true : false;
                        this.jyxkObj.expdtBeginDate = this.jyxkObj.expdtBeginDate == null? '' : this.jyxkObj.expdtBeginDate.substr(0,10);
                        this.jyxkObj.expdtEndDate = this.jyxkObj.expdtEndDate == null? '' : this.jyxkObj.expdtEndDate.substr(0,10);
                        // 经营许可证图片数组
                        this.jyxkImgArray = this.jyxkObj.basCompanyCertificateImages;
                    }
                    // 税务登记证对象
                    this.swdjObj = company.companyCertificateVos.find(item => item.certKind == 3);
                    if(this.swdjObj){
                        this.swdjChecked = this.swdjObj.isValiding == '1' ? true : false;
                        this.swdjObj.expdtBeginDate = this.swdjObj.expdtBeginDate == null? '' : this.swdjObj.expdtBeginDate.substr(0,10);
                        this.swdjObj.expdtEndDate = this.swdjObj.expdtEndDate == null? '' : this.swdjObj.expdtEndDate.substr(0,10);
                        // 税务登记证图片数组
                        this.swdjImgArray = this.swdjObj.basCompanyCertificateImages;
                    }
                }
            },
            /**
             * 查看注册证
             */
            checkRegistCertImg (){

                this.registCertDailogTitle = '注册证 查看' + '【'+ this.currentRow.cname +'】';
                this.registCertDailog = true;
            },
            /**
             * 查看注册证图片
             */
            checkRegistCert (d){

                this.registCertImageUrl = this.imgBasePath + d.filePath;
                this.registCertImgDailogTitle = '注册证图片 查看' + '【'+ this.currentRow.cname +'】';
                this.registCertImgDailog = true;
            },
            handleRCurrentChange (val) {
                this.rPageSize = val
                this.getPushedGoodsList(this.currentMfrs.id)
            },
            handleRSizeChange (val) {
                this.rPageSize = val
                this.getRegCertsList(this.currentMfrs.id)
            },
            /**
             * 格式化生效日期
             */
            formatterBeginDate (row,colum) {
                var result = '';
                if(row.expdtBeginDate != null && row.expdtBeginDate != ''){
                    result = row.expdtBeginDate.substr(0,10);
                }
                return result;
            },
            /**
             * 格式化截止日期
             */
            formatterEndDate (row,colum) {
                var result = '';
                if(row.expdtEndDate != null && row.expdtEndDate != ''){
                    result = row.expdtEndDate.substr(0,10);
                }
                return result;
            }
        },
        mounted() {
            this.imgBasePath = fileServer.imgBasePath;
            this.hosName = this.user.corpName;
            this.getRegCertsList(1);
        }
    };

</script>
<style scoped>
  .regCertStyle{  line-height: 30px;  font-size: 13px;  font-weight: bold;  }
  .text {  font-size: 14px;  }
  .item {  padding: 18px 0;  }
  .clearfix:before,
  .clearfix:after {  display: table;  content: "";  }
  .clearfix:after {  clear: both  }
  .box-card {  width: 480px;  }
  .time {   font-size: 12px;  color: #999;  }
  .bottom {   margin-top: 13px;  line-height: 14px;   }
  .button {   padding: 0;  float: right;   }
  .image {    width: 100%;  display: block;   }
  .clearfix:before,
  .clearfix:after {   display: table;  content: "";   }
  .clearfix:after {   clear: both    }
  .avatar {   width: 100%;;display: block;   }

  /************************************** 三证查看CSS样式 *****************************/
  .ImageCard{
    border:none;
    background-color: #fbfdff;
    border-radius: 6px;
    box-sizing: border-box;
    width: 93%;
    cursor: pointer;
    line-height: 146px;
    vertical-align: top;
    margin-left: 3%;
  }
  .certList {   width: 100%;;display: block; height: 130px;   }
  .certsCard{
    margin-right: 0.3%;
    background-color: rgb(238, 241, 246);
    border: 1px solid darkgrey;
    border: 1px solid #d1dbe5;
    border-radius: 4px;
    overflow: hidden;
    box-shadow: 0 2px 4px 0 rgba(0,0,0,.12), 0 0 6px 0 rgba(0,0,0,.04);
  }
  .certWordsStyle{
    font-size: 12px;
    line-height: 27px;
    font-weight: bold;
    margin-left: 4%;
  }
  .certListStyle{
    margin-left: 3%;
    width: 93%;
    background-color: white;
    height: 26px;
    margin-bottom: 2%;
  }
  .certButtonStyle{
    line-height: 27px;
    margin-right: 3%;
  }
  .certCodes{
    margin-left: 3%;
    margin-right: 4%;
  }
  .checkPendingCertstableheight{
     height:  calc(100% - 100px);
  }
  /*************************************** 三证查看CSS样式 ******************************/

  .checkCertsBox{
    height: 140px;
    border: 1px solid rgb(236, 230, 223);
    border-radius: 6px;
  }
  .checkCertOutBox{
    padding-top: 2%;
    padding-left: 2%;
    padding-bottom: 2%;
    border: 1px solid rgb(236, 230, 223);
  }
  .checkCertsNode{
    line-height: 36px;font-weight: bolder;
  }
  .hr4{
    height: 137px;
    width: 0px;
  }
  #card .el-card__header{  background-color: rgba(191, 221, 239, 0.16);  padding: 3px 20px;  }
  #checkPendingCerts .el-dialog--retrospectDialogSize{
    width: 60%;
    min-width: 800px;
  }
</style>
