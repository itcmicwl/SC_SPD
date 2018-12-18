<template>
  <div id="certList" style="height: 100%;">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :v-mode="filters" label-width="88px" class="queryFormClass" :model="filters" ref="queryForm">
        <el-form-item label="证件类型：" prop="certKind">
          <el-select v-model="filters.certKind" style="width: 73%" placeholder="请选择" @change="changeCertKind">
            <el-option
              v-for="item in outDateCertKind"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="过期时间：" prop="outDate" style="margin-left: -3%;" label-width="80" >
          <el-select v-model="filters.outDate" style="width: 60%" placeholder="请选择" @change="changeOutDate">
            <el-option
              v-for="item in outDateDict"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item v-if="certKind=='94'" label="历史证件：" prop="isOld" style="margin-left: -6%;">
          <el-select v-model="filters.isOld" style="width: 50%" placeholder="请选择" @change="isOldChange()">
            <el-option
              v-for="item in oldDict"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="相关名称：" prop="aboutName" style="margin-left: -7%;" >
          <el-input placeholder="相关名称" v-model="filters.aboutName"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getCertList(1)" plain style="width: 80px;height:25px;" icon="el-icon-search">搜索</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <template>

      <!-- 我的经营证照 -->
      <el-table v-if="certKind=='91'" highlight-current-row class="certList-tableheight" key="myCompCertTable" ref="myCompCertTable"
                :data="myCompCertList" style="width: 100%" v-loading="loading" border :row-class-name="tableRowClassName">
        <el-table-column type="index" width="40" align="center" label="序号">
        </el-table-column>
        <!-- <el-table-column label="企业名称" show-overflow-tooltip  prop="companyName" />	 -->
        <el-table-column label="证件类型" show-overflow-tooltip prop="certKind" :formatter="formateZzlxType" />
        <el-table-column label="证照编码" show-overflow-tooltip prop="certCode" />
        <!--<el-table-column label="效期" >-->
        <!--<template slot-scope="scope">-->
        <!--<div v-if="scope.row.expdtBeginDate && scope.row.expdtEndDate">-->
        <!--{{ formatDate(scope.row.expdtBeginDate) }} 至 {{ formatDate(scope.row.expdtEndDate) }}-->
        <!--</div>-->
        <!--<div v-else>-->
        <!--</div>-->
        <!--</template>-->
        <!--</el-table-column>-->
        <el-table-column :formatter="formatterExpdtDate" prop="expdtDate" label="效期" width="150">
        </el-table-column>
        <el-table-column label="证照附件" align="center"  width="180">
          <template slot-scope="scope" >
            <template v-for="i in scope.row.basCompanyCertificateImages.length">
              <a :href="imgBasePath+scope.row.basCompanyCertificateImages[i-1].filePath"
                 style="text-decoration: none;" target="_blank">
                <el-tag type="danger" color="#3e6ff2" size="small" v-if="scope.row.certKind=='1'"
                        style="color: white;font-weight: bold" class="box_shadow_class">营业执照
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='3'"
                        style="font-weight: bold;color: white" class="box_shadow_class">税务登记证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='4'"
                        style="font-weight: bold;color: white" class="box_shadow_class">组织机构代码证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='5'"
                        style="font-weight: bold;color: white" class="box_shadow_class">经营许可证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='6'"
                        style="font-weight: bold;color: white" class="box_shadow_class">经营备案证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='7'"
                        style="font-weight: bold;color: white" class="box_shadow_class">生产许可证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='8'"
                        style="font-weight: bold;color: white" class="box_shadow_class">生产备案证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else
                        style="font-weight: bold;color: white" class="box_shadow_class">其他证件
                </el-tag>
                <!-- <i class="el-icon-picture" style="font-size: 35px;color: #4792c3;"></i>&nbsp; -->
              </a>
            </template>
          </template>
        </el-table-column>
      </el-table>

      <!-- 供应商经营证照 -->
      <el-table v-if="certKind=='92'" highlight-current-row class="certList-tableheight" key="provCertTable" ref="provCertTable"
                :data="provCertList" style="width: 100%" v-loading="loading" border :row-class-name="tableRowClassName">
        <el-table-column type="index" width="40" align="center" label="序号">
        </el-table-column>
        <el-table-column label="企业名称" show-overflow-tooltip prop="companyName" />
        <el-table-column label="证件类型" show-overflow-tooltip prop="certKind" :formatter="formateZzlxType" />
        <el-table-column label="证照编码" show-overflow-tooltip prop="certCode" />
        <!--<el-table-column label="有效期" >-->
        <!--<template slot-scope="scope">-->
        <!--<div v-if="scope.row.expdtBeginDate && scope.row.expdtEndDate">-->
        <!--{{ formatDate(scope.row.expdtBeginDate) }} 至 {{ formatDate(scope.row.expdtEndDate) }}-->
        <!--</div>-->
        <!--<div v-else>-->
        <!--</div>-->
        <!--</template>-->
        <!--</el-table-column>-->
        <el-table-column :formatter="formatterExpdtDate" prop="expdtDate" label="效期" width="150">
        </el-table-column>
        <el-table-column label="证照附件" align="center" width="180">
          <template slot-scope="scope" >
            <template v-for="i in scope.row.basCompanyCertificateImages.length">
              <a :href="imgBasePath+scope.row.basCompanyCertificateImages[i-1].filePath"
                 style="text-decoration: none;" target="_blank">
                <el-tag type="danger" color="#3e6ff2" size="small" v-if="scope.row.certKind=='1'"
                        style="color: white;font-weight: bold" class="box_shadow_class">营业执照
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='3'"
                        style="font-weight: bold;color: white" class="box_shadow_class">税务登记证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='4'"
                        style="font-weight: bold;color: white" class="box_shadow_class">组织机构代码证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='5'"
                        style="font-weight: bold;color: white" class="box_shadow_class">经营许可证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='6'"
                        style="font-weight: bold;color: white" class="box_shadow_class">经营备案证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='7'"
                        style="font-weight: bold;color: white" class="box_shadow_class">生产许可证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='8'"
                        style="font-weight: bold;color: white" class="box_shadow_class">生产备案证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else
                        style="font-weight: bold;color: white" class="box_shadow_class">其他证件
                </el-tag>
                <!-- <i class="el-icon-picture" style="font-size: 35px;color: #4792c3;"></i>&nbsp; -->
              </a>
            </template>
          </template>
        </el-table-column>
      </el-table>

      <!-- 厂商经营证照 -->
      <el-table v-if="certKind=='93'" highlight-current-row class="certList-tableheight" key="mfrsCertTable" ref="mfrsCertTable"
                :data="mfrsCertList" style="width: 100%" v-loading="loading" border :row-class-name="tableRowClassName">
        <el-table-column type="index" width="40" align="center" label="序号">
        </el-table-column>
        <el-table-column label="企业名称" show-overflow-tooltip prop="mfrsName" />
        <el-table-column label="证件类型" show-overflow-tooltip prop="certKind" :formatter="formateZzlxType" />
        <el-table-column label="证照编码" show-overflow-tooltip prop="certCode" />
        <!--<el-table-column label="有效期" >-->
        <!--<template slot-scope="scope">-->
        <!--<div v-if="scope.row.expdtBeginDate && scope.row.expdtEndDate">-->
        <!--{{ formatDate(scope.row.expdtBeginDate) }} 至 {{ formatDate(scope.row.expdtEndDate) }}-->
        <!--</div>-->
        <!--<div v-else>-->
        <!--</div>-->
        <!--</template>-->
        <!--</el-table-column>-->
        <el-table-column :formatter="formatterExpdtDate" prop="expdtDate" label="效期" width="150">
        </el-table-column>
        <el-table-column label="证照附件" align="center" width="180">
          <template slot-scope="scope" >
            <template v-for="i in scope.row.imageList.length">
              <a :href="imgBasePath+scope.row.imageList[i-1].filePath"
                 style="text-decoration: none;" target="_blank">
                <el-tag type="danger" color="#3e6ff2" size="small" v-if="scope.row.certKind=='1'"
                        style="color: white;font-weight: bold" class="box_shadow_class">营业执照
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='3'"
                        style="font-weight: bold;color: white" class="box_shadow_class">税务登记证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='4'"
                        style="font-weight: bold;color: white" class="box_shadow_class">组织机构代码证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='5'"
                        style="font-weight: bold;color: white" class="box_shadow_class">经营许可证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='6'"
                        style="font-weight: bold;color: white" class="box_shadow_class">经营备案证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='7'"
                        style="font-weight: bold;color: white" class="box_shadow_class">生产许可证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else-if="scope.row.certKind=='8'"
                        style="font-weight: bold;color: white" class="box_shadow_class">生产备案证
                </el-tag>
                <el-tag type="success" color="#3e6ff2" size="small" v-else
                        style="font-weight: bold;color: white" class="box_shadow_class">其他证件
                </el-tag>
                <!-- <i class="el-icon-picture" style="font-size: 35px;color: #4792c3;"></i>&nbsp; -->
              </a>
            </template>
          </template>
        </el-table-column>
      </el-table>

      <!-- 医疗器械注册证 -->
      <el-table v-if="certKind=='94'" highlight-current-row class="certList-tableheight" ref="regTable" key="regTable"
                :data="regList" style="width: 100%" v-loading="loading" border :row-class-name="tableRowClassName">
        <el-table-column type="index" width="40" align="center" label="序号">
        </el-table-column>
        <el-table-column label="注册证名称" show-overflow-tooltip prop="productName" />
        <el-table-column v-if="compKind != 3" label="生产厂家" show-overflow-tooltip prop="mfrsName" />
        <el-table-column label="注册证编号" show-overflow-tooltip prop="certificateCode" />
        <el-table-column label="证件类型" prop="regKind" :formatter="formateZczType" />
        <!--<el-table-column label="有效期" >-->
        <!--<template slot-scope="scope">-->
        <!--<div v-if="scope.row.expdtBeginDate && scope.row.expdtEndDate">-->
        <!--{{ formatDate(scope.row.expdtBeginDate) }} 至 {{ formatDate(scope.row.expdtEndDate) }}-->
        <!--</div>-->
        <!--<div v-else>-->
        <!--</div>-->
        <!--</template>-->
        <!--</el-table-column>-->
        <el-table-column :formatter="formatterExpdtDate" prop="expdtDate" label="效期" width="150">
        </el-table-column>
        <el-table-column label="历史证件" width="80">
          <template slot-scope="scope">
            <div v-if="scope.row.isOldInfo==1">是</div>
            <div v-else>否</div>
          </template>
        </el-table-column>
        <el-table-column label="证照附件" align="center"  width="180">
          <template slot-scope="scope" >
            <template v-for="i in scope.row.fileList.length">
              <a :href="imgBasePath+scope.row.fileList[i-1].filePath"
                 style="text-decoration: none;" target="_blank">
                <el-tag type="success" color="#3e6ff2" size="small"
                        style="font-weight: bold;color: white" class="box_shadow_class">产品注册证
                </el-tag>
                <!-- <i class="el-icon-picture" style="font-size: 35px;color: #4792c3;"></i>&nbsp; -->
              </a>
            </template>
          </template>
        </el-table-column>
      </el-table>

      <!-- 经销授权书 -->
      <el-table v-if="certKind=='95'" highlight-current-row class="certList-tableheight" ref="jxsqsTable" key="jxsqsTable"
                :data="jxsqsList" style="width: 100%" v-loading="loading" border :row-class-name="tableRowClassName">
        <el-table-column type="index" width="40" align="center" label="序号">
        </el-table-column>
        <el-table-column label="授权单位" show-overflow-tooltip prop="chiefName" />
        <el-table-column label="被授权单位" show-overflow-tooltip prop="provName" />
        <el-table-column label="授权范围" show-overflow-tooltip prop="saleArea" />
        <!--<el-table-column label="有效期" >-->
        <!--<template slot-scope="scope">-->
        <!--<div v-if="scope.row.expdtBeginDate && scope.row.expdtEndDate">-->
        <!--{{ formatDate(scope.row.expdtBeginDate) }} 至 {{ formatDate(scope.row.expdtEndDate) }}-->
        <!--</div>-->
        <!--<div v-else>-->

        <!--</div>-->
        <!--</template>-->
        <!--</el-table-column>-->
        <el-table-column :formatter="formatterExpdtDate" prop="expdtDate" label="效期" width="150">
        </el-table-column>
        <el-table-column label="证照附件" align="center" width="180">
          <template slot-scope="scope">
            <div>
              <a :href="imgBasePath + scope.row.filePath"
                 style="text-decoration: none;" target="_blank">
                <el-tag type="success" color="#3e6ff2" size="small" v-if="scope.row.filePath"
                        style="font-weight: bold;color: white" class="box_shadow_class">经销授权书
                </el-tag>
                <!-- <i class="el-icon-picture" style="font-size: 35px;color: #4792c3;"></i> -->
              </a>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 承诺书 -->
      <el-table v-if="certKind=='98'" highlight-current-row class="certList-tableheight" ref="cnsTable" key="cnsTable"
                :data="cnsList" style="width: 100%" v-loading="loading" border :row-class-name="tableRowClassName">
        <el-table-column type="index" width="40" align="center" label="序号">
        </el-table-column>
        <el-table-column v-if="compKind == 2 || compKind == 4" label="医院" show-overflow-tooltip prop="hosName" />
        <el-table-column v-if="compKind == 1" label="供应商" show-overflow-tooltip prop="provName" />
        <!--<el-table-column label="有效期">-->
        <!--<template slot-scope="scope">-->
        <!--<div v-if="scope.row.expdtBeginDate && scope.row.expdtEndDate">-->
        <!--{{ formatDate(scope.row.expdtBeginDate) }} 至 {{ formatDate(scope.row.expdtEndDate) }}-->
        <!--</div>-->
        <!--<div v-else>-->

        <!--</div>-->
        <!--</template>-->
        <!--</el-table-column>-->
        <el-table-column :formatter="formatterExpdtDate" prop="expdtDate" label="效期" width="150">
        </el-table-column>
        <el-table-column label="证照附件" align="center" width="180">
          <template slot-scope="scope">
            <div>
              <a :href="imgBasePath + scope.row.filePathCns"
                 style="text-decoration: none;" target="_blank">
                <el-tag type="success" color="#3e6ff2" size="small" v-if="scope.row.filePathCns"
                        style="font-weight: bold;color: white" class="box_shadow_class">承诺书
                </el-tag>
                <!-- <i class="el-icon-picture" style="font-size: 35px;color: #4792c3;"></i> -->
              </a>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 廉政协议书 -->
      <el-table v-if="certKind=='99'" highlight-current-row class="certList-tableheight" ref="lzxysTable" key="lzxysTable"
                :data="lzxysList" style="width: 100%" v-loading="loading" border :row-class-name="tableRowClassName">
        <el-table-column type="index" width="40" align="center" label="序号">
        </el-table-column>
        <el-table-column v-if="compKind == 2 || compKind == 4" label="医院" show-overflow-tooltip prop="hosName" />
        <el-table-column v-if="compKind == 1" label="供应商" show-overflow-tooltip prop="provName" />
        <!--<el-table-column label="有效期" >-->
        <!--<template slot-scope="scope">-->
        <!--<div v-if="scope.row.expdtBeginDate && scope.row.expdtEndDate">-->
        <!--{{ formatDate(scope.row.expdtBeginDate) }} 至 {{ formatDate(scope.row.expdtEndDate) }}-->
        <!--</div>-->
        <!--<div v-else>-->

        <!--</div>-->
        <!--</template>-->
        <!--</el-table-column>-->
        <el-table-column :formatter="formatterExpdtDate" prop="expdtDate" label="效期" width="150">
        </el-table-column>
        <el-table-column label="证照附件" align="center" width="180">
          <template slot-scope="scope">
            <div>
              <a :href="imgBasePath+scope.row.filePath" style="text-decoration: none;" target="_blank">
                <el-tag type="success" color="#3e6ff2" size="small" v-if="scope.row.filePath"
                        style="font-weight: bold;color: white" class="box_shadow_class">廉政协议书
                </el-tag>
                <!-- <i class="el-icon-picture" style="font-size: 35px;color: #4792c3;"></i> -->
              </a>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 销售代表 -->
      <el-table v-if="certKind=='97'" highlight-current-row class="certList-tableheight" ref="saleRepTable" key="saleRepTable"
                :data="saleRepList" style="width: 100%" v-loading="loading" border :row-class-name="tableRowClassName">
        <el-table-column type="index" width="40" align="center" label="序号">
        </el-table-column>
        <el-table-column v-if="compKind == 1" label="供应商" show-overflow-tooltip prop="provName" />
        <el-table-column v-if="compKind == 2 || compKind == 4" label="医院" show-overflow-tooltip prop="hosName" />
        <el-table-column label="销售代表" show-overflow-tooltip prop="salesName" />
        <el-table-column label="电话" show-overflow-tooltip prop="phoneNum" />
        <!--<el-table-column label="有效期" >-->
        <!--<template slot-scope="scope">-->
        <!--<div v-if="scope.row.expdtBeginDate && scope.row.expdtEndDate">-->
        <!--{{ formatDate(scope.row.expdtBeginDate) }} 至 {{ formatDate(scope.row.expdtEndDate) }}-->
        <!--</div>-->
        <!--<div v-else>-->

        <!--</div>-->
        <!--</template>-->
        <!--</el-table-column>-->
        <el-table-column :formatter="formatterExpdtDate" prop="expdtDate" label="效期" width="150">
        </el-table-column>
        <el-table-column label="证照附件" align="center" width="180">
          <template slot-scope="scope">
            <div>
              <a :href="imgBasePath+scope.row.filePath" style="text-decoration: none;" target="_blank">
                <el-tag type="success" color="#3e6ff2" size="small" v-if="scope.row.filePath"
                        style="font-weight: bold;color: white" class="box_shadow_class">销售代表
                </el-tag>
                <!-- <i class="el-icon-picture" style="font-size: 35px;color: #4792c3;"></i> -->
              </a>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 法人委托书 -->
      <el-table v-if="certKind=='96'" highlight-current-row class="certList-tableheight" ref="frwtsTable" key="frwtsTable"
                :data="frwtsList" style="width: 100%" v-loading="loading" border :row-class-name="tableRowClassName">
        <el-table-column type="index" width="40" align="center" label="序号">
        </el-table-column>
        <el-table-column v-if="compKind == 1" label="委托企业" show-overflow-tooltip prop="provName" />
        <el-table-column v-if="compKind == 2 || compKind == 4" label="业务对象" show-overflow-tooltip prop="hosName" />
        <el-table-column label="业务员" show-overflow-tooltip prop="wtrName" />
        <el-table-column label="电话" show-overflow-tooltip prop="wtrPhone" />
        <!--<el-table-column label="有效期" >-->
        <!--<template slot-scope="scope">-->
        <!--<div v-if="scope.row.expdtBeginDate && scope.row.expdtEndDate">-->
        <!--{{ formatDate(scope.row.expdtBeginDate) }} 至 {{ formatDate(scope.row.expdtEndDate) }}-->
        <!--</div>-->
        <!--<div v-else>-->

        <!--</div>-->
        <!--</template>-->
        <!--</el-table-column>-->
        <el-table-column :formatter="formatterExpdtDate" prop="expdtDate" label="效期" width="150">
        </el-table-column>
        <el-table-column label="证照附件" align="center" width="180">
          <template slot-scope="scope">
            <div>
              <a :href="imgBasePath+scope.row.filePathWts"
                 style="text-decoration: none;" target="_blank">
                <el-tag type="success" color="#3e6ff2" size="small" v-if="scope.row.filePathWts"
                        style="font-weight: bold;color: white" class="box_shadow_class">委托书
                </el-tag>
              </a>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <a :href="imgBasePath+scope.row.filePathWtr"
                 style="text-decoration: none;" target="_blank">
                <el-tag type="success" color="#3e6ff2" size="small" v-if="scope.row.filePathWtr"
                        style="font-weight: bold;color: white" class="box_shadow_class">委托人
                </el-tag>
              </a>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination style="float:right" small @size-change="handleSizeChange"
                       @current-change="changeCurrentChange"
                       :current-page.sync="pageNum" layout="total, sizes, prev, pager, next"
                       :page-sizes="[30, 50, 100, 200]" :page-size="pageSize" :total="total">
        </el-pagination>
      </el-col>
    </template>
  </div>
</template>
<script>
  import { fileServer, PROV_CERT_TYPE } from "../../common/js/constance";
  import moment from "moment";
  export default {
    data() {
      return {
        filters: {
          isValiding: "",
          certKind: "91",
          outDate: "999999",
          isOld: "0",
          aboutName: ""
        },
        outDateDict: [
          {
            value: "0",
            label: "已过期"
          },
          {
            value: "-7",
            label: "一周内过期"
          },
          {
            value: "-30",
            label: "一月内过期"
          },
          {
            value: "-90",
            label: "三个月内过期"
          },
          {
            value: "-180",
            label: "半年内过期"
          },
          {
            value: "999999",
            label: "全部证照"
          }
        ],

        outDateCertKind: [],

        oldDict: [
          {
            value: "0",
            label: "否"
          },
          {
            value: "1",
            label: "是"
          },
          {
            value: "",
            label: "全部"
          }
        ],

        certKind: "91",

        start: "",
        end: "",
        queryParam: {},

        pageNum: 1,
        pageSize: 30,
        total: 0,
        loading: false,

        cnsList: [],
        lzxysList: [],
        saleRepList: [],
        frwtsList: [],
        regList: [],
        jxsqsList: [],
        provCertList: [],
        mfrsCertList: [],
        myCompCertList: [],

        // 注册证类型
        regCertKinds: [],

        compCertKinds: [],
        imgBasePath: "",
        baseURL: "/spdHDIService/certAlert",

        unitKinds: []
      };
    },
    mounted() {
      //this.getZZLX();
      this.imgBasePath = fileServer.imgBasePath;
      this.compKind = this.user.corpKind;
      this.setInitOutDateCertKind();
      this.getCertList(1);
    },
    methods: {
      formatterExpdtDate(row, col, cellValue) {
        let begin = moment(row.expdtBeginDate).format("YYYY-MM-DD");
        let end = moment(row.expdtEndDate).format("YYYY-MM-DD");
        return begin + "至" + end;
      },
      tableRowClassName({row, rowIndex}) {
        const expdtEndDate = moment(row.expdtEndDate);
        const now = moment();
        if (expdtEndDate.isBefore(now)) {
          return "warning-red";
        } else if (expdtEndDate.isBefore(now.add(1, "month"))) {
          return "warning-row";
        }
        return "";
      },
      setInitOutDateCertKind() {
        if (this.compKind == 1) {
          this.outDateCertKind = [
            {
              value: "91",
              label: "我的经营证照"
            },
            {
              value: "92",
              label: "供应商经营证照"
            },
            {
              value: "94",
              label: "医疗器械注册证"
            },
            {
              value: "95",
              label: "经销授权书"
            },
            {
              value: "96",
              label: "法人委托书"
            },
            {
              value: "97",
              label: "销售代表"
            },
            {
              value: "98",
              label: "承诺书"
            },
            {
              value: "99",
              label: "廉政协议书"
            }
          ];
        } else if (this.compKind == 2 || this.compKind == 4) {
          this.outDateCertKind = [
            {
              value: "91",
              label: "我的经营证照"
            },
            {
              value: "92",
              label: "供应商经营证照"
            },
            {
              value: "93",
              label: "厂商经营证照"
            },
            {
              value: "94",
              label: "医疗器械注册证"
            },
            {
              value: "95",
              label: "经销授权书"
            },
            {
              value: "96",
              label: "法人委托书"
            },
            {
              value: "97",
              label: "销售代表"
            },
            {
              value: "98",
              label: "承诺书"
            },
            {
              value: "99",
              label: "廉政协议书"
            }
          ];
        } else if (this.compKind == 3) {
          this.outDateCertKind = [
            {
              value: "91",
              label: "我的经营证照"
            },
            {
              value: "94",
              label: "医疗器械注册证"
            },
            {
              value: "95",
              label: "经销授权书"
            }
          ];
        }
      },
      //注册证类型下拉
      changeCertKind() {
        this.certKind = this.filters.certKind;
        this.getCertList(this.pageNum);
      },
      // 过期时间下拉
      changeOutDate() {
        this.getCertList(this.pageNum);
      },
      // 历史证件下拉
      isOldChange() {
        this.getCertList(this.pageNum);
      },

      changeCurrentChange(val) {
        this.pageNum = val;
        this.getCertList(this.pageNum);
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.getCertList(this.pageNum);
      },
      formatDate(item) {
        return new Date(item).toLocaleDateString();
      },
      // 企业证照类型格式化
      formateZzlxType(row, column, cellValue) {
        if (this.compCertKinds.length === 0) {
          return "";
        }

        let certKind = this.compCertKinds.find(item => item.val == cellValue);
        if (certKind) {
          return certKind.ename;
        } else {
          //return cellValue;
          return "其他";
        }
      },
      // 注册证类型格式化
      formateZczType(row, column, cellValue) {
        if (this.regCertKinds.length === 0) {
          return "";
        }

        let certKind = this.regCertKinds.find(item => item.val == cellValue);
        if (certKind) {
          return certKind.ename;
        } else {
          //return cellValue;
          return "其他";
        }
      },
      getStartEndDate() {
        let endTime = new Date();
        if (this.filters.outDate && this.filters.outDate == "999999") {
          this.start = null;
          this.end = null;
        } else {
          this.end = moment(
            endTime.getTime() - this.filters.outDate * 3600 * 1000 * 24
          ).format("YYYY-MM-DD");
          this.start = moment(new Date()).format("YYYY-MM-DD");
        }
      },
      setAllQueryParam() {
        this.getStartEndDate();

        let params = {
          compKind: this.compKind,
          start: this.start,
          end: this.end,
          aboutName: this.filters.aboutName,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };

        return params;
      },
      resetData() {
        (this.myCompCertList = []),
          (this.provCertList = []),
          (this.mfrsCertList = []),
          (this.regList = []);
        this.cnsList = [];
        this.frwtsList = [];
        this.lzxysList = [];
        this.saleRepList = [];
        this.jxsqsList = [];
      },

      // 我的经营证照
      getMyCompCertList(pIndex) {
        let params = {
          compKind: this.compKind,
          start: this.start,
          end: this.end,
          aboutName: this.filters.aboutName,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };
        this.loading = true;
        this.axios
          .get(this.baseURL + "/listCompCertById", { params })
          .then(res => {
            this.loading = false;
            if (res.data.code == 0) {
              this.myCompCertList = res.data.data.data;
              this.total = res.data.data.total;
            } else {
              this.$message.error("获取数据失败。" + res.data.msg);
            }
          })
          .catch(err => {
            this.loading = false;
          });
      },

      // 供应商经营证照
      getProvList(pIndex) {
        let params = {
          compKind: this.compKind,
          start: this.start,
          end: this.end,
          aboutName: this.filters.aboutName,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };
        this.loading = true;
        this.axios
          .get(this.baseURL + "/listProvCertByLoginComp", { params })
          .then(res => {
            this.loading = false;
            if (res.data.code == 0) {
              this.provCertList = res.data.data.data;
              this.total = res.data.data.total;
            } else {
              this.$message.error("获取数据失败。" + res.data.msg);
            }
          })
          .catch(err => {
            this.loading = false;
          });
      },

      // 厂商经营证照
      getMfrsList(pIndex) {
        let params = {
          compKind: this.compKind,
          start: this.start,
          end: this.end,
          aboutName: this.filters.aboutName,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };
        this.loading = true;
        this.axios
          .get(this.baseURL + "/listMfrsCertByProv", { params })
          .then(res => {
            this.loading = false;
            if (res.data.code == 0) {
              this.mfrsCertList = res.data.data.data;
              this.total = res.data.data.total;
            } else {
              this.$message.error("获取数据失败。" + res.data.msg);
            }
          })
          .catch(err => {
            this.loading = false;
          });
      },

      // 医疗器械注册证
      getRegList(pIndex) {
        let params = {
          compKind: this.compKind,
          start: this.start,
          end: this.end,
          isOld: this.filters.isOld,
          aboutName: this.filters.aboutName,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };
        this.loading = true;
        this.axios
          .get(this.baseURL + "/listRegCertByLoginComp", { params })
          .then(res => {
            this.loading = false;
            if (res.data.code == 0) {
              this.regList = res.data.data.data;
              this.total = res.data.data.total;
            } else {
              this.$message.error("获取数据失败。" + res.data.msg);
            }
          })
          .catch(err => {
            this.loading = false;
          });
      },

      // 经销授权书
      getJxsqsList(pIndex) {
        let params = {
          compKind: this.compKind,
          start: this.start,
          end: this.end,
          aboutName: this.filters.aboutName,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };
        this.loading = true;
        this.axios
          .get(this.baseURL + "/listSqsByLoginComp", { params })
          .then(res => {
            this.loading = false;
            if (res.data.code == 0) {
              this.jxsqsList = res.data.data.data;
              this.total = res.data.data.total;
            } else {
              this.$message.error("获取数据失败。" + res.data.msg);
            }
          })
          .catch(err => {
            this.loading = false;
          });
      },

      // 获取承诺书
      getCnsList(pIndex) {
        let params = {
          compKind: this.compKind,
          start: this.start,
          end: this.end,
          aboutName: this.filters.aboutName,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };
        this.loading = true;
        this.axios
          .get(this.baseURL + "/getCnsListByLoginComp", { params })
          .then(res => {
            this.loading = false;
            if (res.data.code == 0) {
              this.cnsList = res.data.data.data;
              this.total = res.data.data.total;
            } else {
              this.$message.error("获取数据失败。" + res.data.msg);
            }
          })
          .catch(err => {
            this.loading = false;
          });
      },

      // 获取廉政协议书
      getLzxysList(pIndex) {
        let params = {
          compKind: this.compKind,
          start: this.start,
          end: this.end,
          aboutName: this.filters.aboutName,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };

        this.loading = true;
        this.axios
          .get(this.baseURL + "/getIncorruptAgrmtListByLoginComp", { params })
          .then(res => {
            this.loading = false;
            if (res.data.code == 0) {
              this.lzxysList = res.data.data.data;
              this.total = res.data.data.total;
            } else {
              this.$message.error("获取数据失败。" + res.data.msg);
            }
          })
          .catch(err => {
            this.loading = false;
          });
      },

      // 获取法人委托书
      getFrwtsList(pIndex) {
        let params = {
          compKind: this.compKind,
          start: this.start,
          end: this.end,
          aboutName: this.filters.aboutName,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };
        this.loading = true;
        this.axios
          .get(this.baseURL + "/getFrwtsListByLoginComp", { params })
          .then(res => {
            this.loading = false;
            if (res.data.code == 0) {
              this.frwtsList = res.data.data.data;
              this.total = res.data.data.total;
            } else {
              this.$message.error("获取数据失败。" + res.data.msg);
            }
          })
          .catch(err => {
            this.loading = false;
          });
      },

      // 获取销售代表
      getSaleRepList(pIndex) {
        let params = {
          compKind: this.compKind,
          start: this.start,
          end: this.end,
          aboutName: this.filters.aboutName,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };
        this.loading = true;
        this.axios
          .get(this.baseURL + "/getSalesRepListByLoginComp", { params })
          .then(res => {
            this.loading = false;
            if (res.data.code == 0) {
              this.saleRepList = res.data.data.data;
              this.total = res.data.data.total;
            } else {
              this.$message.error("获取数据失败。" + res.data.msg);
            }
          })
          .catch(err => {
            this.loading = false;
          });
      },

      // 获取证照
      getCertList(pIndex) {
        this.pageNum = pIndex;

        this.getStartEndDate();

        this.resetData();

        if (this.certKind == "91") {
          this.getZZLX();
          this.getMyCompCertList(this.pageNum);
        } else if (this.certKind == "92") {
          this.getZZLX();
          this.getProvList(this.pageNum);
        } else if (this.certKind == "93") {
          this.getZZLX();
          this.getMfrsList(this.pageNum);
        } else if (this.certKind == "94") {
          this.getZCZLX();
          this.getRegList(this.pageNum);
        } else if (this.certKind == "95") {
          this.getJxsqsList(this.pageNum);
        } else if (this.certKind == "96") {
          this.getFrwtsList(this.pageNum);
        } else if (this.certKind == "97") {
          this.getSaleRepList(this.pageNum);
        } else if (this.certKind == "98") {
          this.getCnsList(this.pageNum);
        } else if (this.certKind == "99") {
          this.getLzxysList(this.pageNum);
        }
      },

      //字典获取企业经营证照类型
      getUnitKinds: function() {
        this.loading = true;
        this.axios
          .post("/platformService/sys/dict/getDictValueByDictEname", {
            dictName: "JGLX",
            val: 2
          })
          .then(
            res => {
              this.loading = false;
              if (res.data.code == "0") {
                this.unitKinds = res.data.data;
              } else {
                this.$message.error(res.data.msg);
              }
            },
            err => {
              this.loading = false;
            }
          );
      },
      getZCZLX: function() {
        //注册证类型 字典
        this.loading = true;
        this.axios
          .post("/platformService/sys/dict/getDictValueByDictEname", {
            dictName: "ZCZLX"
          })
          .then(
            res => {
              this.loading = false;
              if (res.data.code == "0") {
                this.regCertKinds = res.data.data;
              } else {
                this.$message.error(res.data.msg);
              }
            },
            err => {
              this.loading = false;
            }
          );
      },
      getZZLX: function() {
        //证照类型 字典
        this.loading = true;
        this.axios
          .post("/platformService/sys/dict/getDictValueByDictEname", {
            dictName: "ZZLX"
          })
          .then(
            res => {
              this.loading = false;
              if (res.data.code == "0") {
                this.compCertKinds = res.data.data;
              } else {
                this.$message.error(res.data.msg);
              }
            },
            err => {
              this.loading = false;
            }
          );
      }
    }
  };
</script>
<style>
  #certList .certList-tableheight {
    height: calc(100% - 80px);
  }
  #certList .el-radio-button__orig-radio:checked + .el-radio-button__inner {
    color: #ffffff;
    background: #535c63;
    border-color: #676082;
  }
  #certList .el-radio-button--mini .el-radio-button__inner {
    height: 28px;
    width: 38px;
    padding: 7px 7px 6px;
    font-size: 12px;
    font-size: 12px;
    border-radius: 0px;
  }
  #certList .el-radio-button:first-child .el-radio-button__inner {
    border-left: 1px solid #dcdfe6;
    border-radius: 4px 0 0 4px;
    -webkit-box-shadow: none !important;
    box-shadow: none !important;
  }
  #certList .el-radio-button:last-child .el-radio-button__inner {
    border-radius: 0px 4px 4px 0px;
    -webkit-box-shadow: none !important;
    box-shadow: none !important;
  }
  #certList .el-input--mini .el-input__inner {
  }
  .queryFormClass {
    padding-top: 3px;
    height: 30px;
    box-shadow: 0px 0px 3px 0px #a9a1a1 inset;
    border-radius: 4px;
    background-color: #f9f9f9;
  }
  #certList .el-form--inline .el-form-item {
    margin-right: 0px;
  }
  #certList .toolbar {
    margin-bottom: 1px;
    margin-top: -3px;
  }
  .box_shadow_class {
    box-shadow: inset 0 0 5px 0 #ffffff;
  }
  #certList .el-table .warning-red {
    color: #F56C6C;
  }
  #certList .el-table .warning-row {
    color: #E6A23C;
  }
</style>
