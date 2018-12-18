<template>
	<div  style="height: 100%;">
			<!--工具条-->

				<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
					<el-form :inline="true" label-width="80px">
						<el-form-item label="商品名称">
							<el-input v-model="filters.goodsName" placeholder="输入商品名称或规格" ></el-input>
						</el-form-item>
                        <el-form-item label="类别">
                            <el-select v-model="filters.kindCode" filterable  clearable placeholder="请选择"  >
                                <el-option
                                        v-for="item in hosKindsCodes"
                                        :key="item.id"
                                        :label="item.kindName"
                                        :value="item.kindName">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="是否激活">
                              <el-select v-model="filters.flag" filterable clearable placeholder="请选择">
                                  <el-option
                                          v-for="item in flags"
                                          :key="item.value"
                                          :label="item.label"
                                          :value="item.value">
                                  </el-option>
                              </el-select>
                        </el-form-item>
						<el-button type="primary" v-on:click="getGoodsList(1)" >查询</el-button>
                     <!--   <el-button type="primary" @click="goodsImport()" >从平台批量导入</el-button>-->
                      <el-button type="primary" @click="goodsImportByph()" >从供货目录导入</el-button>
						<!-- <el-button type="primary" @click="goodEdit(null)" >新增</el-button> -->
                        <el-button type="primary" @click="multDel()" >批量删除</el-button>
                        <el-button type="primary" @click="exportExcel()" >导出Excel</el-button>
                      <!--   <el-button type="primary" @click="importExcel()" >从Excel导入</el-button>-->
					</el-form>
				</el-col>
				<!--列表-->
				<template>
					<el-table :data="goods"  ref="multipleTable"
                               highlight-current-row v-loading="loading" style="width: 100%;"
                               row-key='id'  @selection-change="handleSelectionChange"
                               border fit  class="hosgoods-tableheight" height="outer">
					   <!--	<el-table-column type="index" label="序号" align="center" width="62"></el-table-column>     -->
                        <el-table-column  type="selection" align="center" :reserve-selection="true" width="50"> </el-table-column>
						<el-table-column prop="code" label="院内商品编号" width="120" header-align="center"></el-table-column>
						<el-table-column prop="goodsName" label="院内商品名称" width="200" header-align="center"></el-table-column>
						<el-table-column prop="goodsGg" label="规格型号" width="180" header-align="center" ></el-table-column>
						<el-table-column prop="made" label="产地" width="80" header-align="center" ></el-table-column>
                        <el-table-column prop="mfrsName" label="生产厂商"  width="180" show-overflow-tooltip header-align="center" ></el-table-column>
						<!--<el-table-column prop="mfrsId" label="生产厂商ID" header-align="center" width="90" ></el-table-column>-->

                        <el-table-column prop="unit" width="60" label="单位" align="center" > </el-table-column>
                        <!--<el-table-column prop="packeage" label="包装" header-align="center" width="65"></el-table-column>-->
                        <el-table-column prop="spdGoodsCode" width="80" label="平台编码" align="center" ></el-table-column>
                        <!--<el-table-column prop="certificateCode" label="注册证号" sortable></el-table-column>-->
                         <!--<el-table-column prop="brand" label="品牌" width="100" header-align="center" ></el-table-column>-->
                        <el-table-column label="操作" width="160" align="center" >
							<template slot-scope="scope">
                                <el-button-group>
                                	<el-button type="primary" size="mini" @click="actGood(scope.row)" icon="plus" v-if="scope.row.flag==0" >激活</el-button>
								    <el-button type="primary" size="mini" @click="goodEdit(scope.row)" icon="edit"  v-if="scope.row.flag==1">编辑</el-button>
								    <el-button type="danger" size="mini" @click="delGood(scope.row)" icon="delete" v-if="scope.row.flag==1">删除</el-button>
								    <el-button type="info" size="mini" @click="getRegInfos(scope.row)" icon="el-icon-picture" v-if="scope.row.flag==1">注册证</el-button>
                                </el-button-group>
							</template>
						</el-table-column>
					</el-table>
					<!--工具条-->
					<el-col :span="24" class="toolbar">
						<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page" :page-sizes="[20, 100, 200, 400]" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" style="float:right;" small>
						</el-pagination>
					</el-col>
				</template>
				<!--编辑界面-->
				<el-dialog :title="modelTitle" close="onClose" :visible.sync="editFormVisible" :close-on-click-modal="false" >

                    <el-form :model="good"  label-width="100px" :rules="editFormRules" ref="editForm"  class="el-form-item-nomsg">
                            <el-row>
                                <el-col>
                                    <el-form-item label="医院商品名称" prop="goodsName" :error="errors.goodsNameErr" >
                                        <el-input v-model="good.goodsName"  placeholder="请输入商品名称"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
							 <el-row>
                                <el-col :span="12">
                                    <el-form-item label="商品俗称" prop="generalName"  >
                                        <el-input v-model="good.generalName"  placeholder="请输入商品俗称"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="商品描述" prop="goodsDesc"  >
                                        <el-input v-model="good.goodsDesc"  placeholder="请输入商品描述"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="8">
                                    <el-form-item label="医院商品编号" prop="code" :error="errors.codeErr">
                                                <el-input v-model="good.code" placeholder="请输入商品编号" ></el-input>
                                    </el-form-item>
                                </el-col>

                                  <el-col :span="16">
                                        <el-form-item label="生产厂商" prop="mfrsId" :error="errors.mfrsIdErr">
                                            <!--	<el-input v-model="good.mfrsId"  placeholder="请输入生产厂商"></el-input>  -->
                                                   <!-- <el-select v-model="selectedId" filterable placeholder="请选择" >
                                                                <el-option
                                                                    v-for="item in mfrs"
                                                                    :key="item.id"
                                                                    :label="item.mfrsName"
                                                                    :value="item.id">
                                                                </el-option>
                                                </el-select>
                                                -->
                                                 <el-autocomplete style="width:100%"
                                                    class="inline-input"
                                                    v-model="selectedName"
                                                    :fetch-suggestions="querySearch"
                                                    placeholder="请输入内容"

                                                    @select="handleSelect"
                                                      :props ="myPorps"
                                                      size='small'
                                                    >
                                                </el-autocomplete>
                                            </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="8">
                                    <el-form-item label="产地" prop="made" :error="errors.madeErr">
                                                <el-input v-model="good.made"  placeholder="请输入产地"></el-input>
                                    </el-form-item>
                                </el-col>
                                 <el-col :span="16">
                                    <el-form-item label="规格型号" prop="goodsGg" :error="errors.goodsGgErr">
                                        <el-input v-model="good.goodsGg"  placeholder="请输入规格型号"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="出厂编码">
                                                    <el-input placeholder="请输入厂家出厂编码" v-model="good.mfrsCode" ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                        <el-form-item label="单位" prop="unit" :error="errors.unitErr">
                                                    <el-input placeholder="请输入单位" v-model="good.unit" ></el-input>
                                        </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="品牌" prop="brand" :error="errors.brandErr">
                                                <el-input placeholder="请输入品牌" v-model="good.brand" ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="包装" prop="packeage" :error="errors.packeageErr">
                                                <el-input placeholder="请输入包装" v-model.number="good.packeage" ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="12">
                                    <el-form-item label="统一码">
                                                <el-input placeholder="请输入统一码" v-model="good.masterCode" ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">

                                      <el-form-item label="药监分类" prop="kind68code" >
                                                     <el-select v-model="kind68code" filterable placeholder="请选择"
                                                       style='width:100%' >
                                                            <el-option
                                                                v-for="item in dict68s"
                                                                :key="item.val"
                                                                :label="item.ename"
                                                                :value="item.val">
                                                             <span style="float: left">{{ item.ename }}</span>
                                                                 <span style="float: right; color: #8492a6; font-size: 13px">{{ item.val }}</span>
                                                            </el-option>
                                                      </el-select>
                                    </el-form-item>
                                </el-col>

                            </el-row>

                            <el-row>
                                <!--<el-col :span="12">
                                        <el-form-item label="类别属性">
                                            <el-input placeholder="请输入类别属性" v-model="good.lbsx" ></el-input>
                                        </el-form-item>
                                </el-col>-->

                            </el-row>
                             <el-row>
                                            <el-col :span="12">
                                                    <el-form-item label="价格"  prop="price" :error="errors.priceErr">
                                                        <el-input placeholder="请输入价格" v-model.number="good.price" ></el-input>
                                                    </el-form-item>
                                               </el-col>
											   <!--
												   <el-col :span="12">
															<el-form-item label=" 医院厂商名称">
																<el-input placeholder="请输入备注信息" v-model="good.hosMfrsName" ></el-input>
															</el-form-item>
													 </el-col>
                                                -->
                                                <el-col :span="12">
                                                 <el-form-item label="类别" prop="kindCode" >
                                                     <el-select v-model="kindCode" filterable placeholder="请选择"  style="width:100%">
                                                         <el-option
                                                                 v-for="item in hosKindsCodes"
                                                                 :key="item.id"
                                                                 :label="item.kindName"
                                                                 :value="item.kindName">
                                                         </el-option>
                                                     </el-select>
                                                 </el-form-item>

                                               </el-col>
                                        </el-row>

                                         <el-row>
                                            <el-col :span="12">
                                                    <el-form-item label="阳光采购编码">
                                                        <el-input placeholder="请输入阳光采购编码" v-model="good.hitCode" ></el-input>
                                                    </el-form-item>
                                               </el-col>
                                           <el-col :span="12">
                                                    <el-form-item label=" 阳光采购价" prop="hitPrice" :error="errors.hitPriceErr">
                                                        <el-input placeholder="请输入阳光采购价" v-model.number="good.hitPrice" ></el-input>
                                                    </el-form-item>
                                             </el-col>
                                        </el-row>

                                          <el-row>
                                            <el-col :span="12">
                                                    <el-form-item label="erp编码">
                                                        <el-input placeholder="请输入erp编码" v-model="good.erpCode" ></el-input>
                                                    </el-form-item>
                                              </el-col>
                                           <el-col :span="12">
                                                        <el-form-item label="平台编码">
                                                        <el-input placeholder="请输入平台编码" v-model="good.spdGoodsCode" ></el-input>
                                                    </el-form-item>
                                            </el-col>
                                        </el-row>
                                   <el-row>
                                            <el-col :span="12">
                                                    <el-form-item label="HIS销售价"  prop="hisPrice" :error="errors.hisPriceErr">
                                                        <el-input placeholder="请输入HIS销售价" v-model.number="good.hisPrice" ></el-input>
                                                    </el-form-item>
                                               </el-col>
                                                 <el-col :span="12">
                                                   <el-form-item label="税率" prop="taxRate" >
                                                     <el-select v-model="taxRate" filterable placeholder="请选择"  style="width:100%">
                                                            <el-option
                                                                v-for="item in SL"
                                                                :key="item.ename"
                                                                :label="item.val"
                                                                :value="item.ename">
                                                            </el-option>
                                                    </el-select>
                                                </el-form-item>

                                            </el-col>

                                        </el-row>

                                         <el-row>
                                             <el-col :span="12">
                                                 <el-form-item label="唯一码管理策略" prop="uniqueCodeStrategy" >
                                                         <el-select v-model="uniqueCodeStrategy" filterable placeholder="请选择"   style="width:100%">
                                                                <el-option
                                                                    v-for="item in dictUCodeStrategys"
                                                                    :key="item.ename"
                                                                    :label="item.ename"
                                                                    :value="item.val">
                                                                </el-option>
                                                        </el-select>
                                                    </el-form-item>
                                             </el-col>

                                        <el-col :span="8">
                                            <el-form-item label="采购模式" prop="purMode" :error="errors.purModeErr">
                                                <el-select v-model="good.purMode" filterable placeholder="请选择" :clearable="true" >
                                                    <el-option
                                                            v-for="item in purModes"
                                                            :key="item.val"
                                                            :label="item.ename"
                                                            :value="item.val">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        </el-row>
                                        <el-row style="padding-left: 10px;">
                                            <el-col :span="8">
                                                <el-form-item label="是否计量" prop="isMetering">
                                                    <el-radio-group v-model="good.isMetering" readonly >
                                                        <el-radio-button label="1">是</el-radio-button>
                                                        <el-radio-button label="0">否</el-radio-button>
                                                    </el-radio-group>
                                                </el-form-item>
                                            </el-col>
                                        </el-row>
                                    <el-row>


                                        <el-col >
                                            <el-form-item label=" 备注信息">
                                                <el-input placeholder="请输入备注信息" v-model="good.remark" ></el-input>
                                            </el-form-item>
                                        </el-col>

									<el-row>
										<el-form-item label="是否收费">
											<el-checkbox v-model="good.charging" true-label="1" false-label="0"><span style="font-size:12px">{{good.charging === '1'?"收费":"不收费"}}</span></el-checkbox>
										</el-form-item>
                                     </el-row>

                                </el-row>
                            </el-form>

					<div slot="footer" class="dialog-footer">
						<el-button @click.native="editFormVisible = false">取消</el-button>
						<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
					</div>
				</el-dialog>
        <el-dialog :title="selectExportColumnsTitle" close="onClose" :visible.sync="selectExportColumnsVisible" width="70%" :close-on-click-modal="false">
					    <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
              <div style="margin: 15px 0;"></div>
                <el-checkbox-group v-model="checkedColumns" @change="handleCheckedColumnsChange">
                  <el-col :span="8" v-for="column in columns" :label="column" :key="column">
                      <el-checkbox  :label="column" >{{column}}</el-checkbox>
                 </el-col>
              </el-checkbox-group>
              <div  v-show="downLoadVisible">
                   <el-col>
                       <a :href="fileSrc">导出完毕，请点击此处进行下载！</a>
                   </el-col>
              </div >
              <div slot="footer" class="dialog-footer">
                <el-col  style="padding-bottom: 5px;">
                  <el-button @click.native="selectExportColumnsVisible = false">取消</el-button>
                  <el-button type="primary" @click.native="selectExportColumnsSubmit" :loading="editLoading">提交</el-button>
                </el-col>
              </div>
        </el-dialog>
                <el-dialog title="从平台导入数据" close="onClose" :visible.sync="goodImportVisible" :close-on-click-modal="false" size="large">

                    <el-form :inline="true" label-width="80px">
                        <el-form-item label="商品名称">
                            <el-input v-model="basGoodsFilters.goodsName" placeholder="输入商品名称或规格" ></el-input>
                        </el-form-item>
                        <el-button type="primary" v-on:click="getBasGoodsList(1)" >查询</el-button>

                    </el-form>
                        <template>
                            <el-table
                                ref="multipleTable" :data="basGoods"  border tooltip-effect="dark" row-key='id'
                                style="width: 100%" @selection-change="handleSelectionChange">
                                <el-table-column  type="selection" :reserve-selection="true" width="55"> </el-table-column>

                                <el-table-column  prop="goodsName" label="商品名称" width="200"></el-table-column>
                                <el-table-column prop="code" label="商品编号" show-overflow-tooltip> </el-table-column>
                                    <el-table-column prop="mfrsName" label="生产厂家" show-overflow-tooltip> </el-table-column>
                                    <el-table-column prop="goodsGg" label="规格" show-overflow-tooltip> </el-table-column>
                            </el-table>
                                <el-pagination @size-change="handleBasGoodsSizeChange" @current-change="handleBasGoodsCurrentChange" :current-page="basGoodsPage" :page-sizes="[10, 100, 200, 400]" :page-size="basGoodsPageSize" layout="total, sizes, prev, pager, next"
                                :total="basGoodsTotal" style="float:right;" small>
                                </el-pagination>
                            <div style="margin-top: 20px">

                                <el-button @click="toggleSelection()">取消选择</el-button>
                                <el-button @click="goodImprotSubmit()">确定</el-button>
                            </div>
                        </template>
                </el-dialog>
                <el-dialog title="从供货目录导入数据" close="onClose"  :visible.sync="goodImportByphVisible" :close-on-click-modal="false" width="85%">
                        <el-form :inline="true" label-width="80px">
                            <el-form-item label="商品名称">
                                <el-input v-model="basGoodsFilters.goodsName" placeholder="输入商品名称或规格" ></el-input>
                            </el-form-item>
                             <el-form-item label="供应商">
                                <el-select v-model="basGoodsFilters.provId" filterable clearable placeholder="请选择" >
                                                <el-option
                                                    v-for="item in provList"
                                                    :key="item.provId"
                                                    :label="item.provName"
                                                    :value="item.provId">
                                                </el-option>
                                    </el-select>
                            </el-form-item>

                            <el-switch :width="80"
								v-model="basGoodsFilters.isAll"
								 active-value="1" inactive-value="0"
								 active-color="#13ce66" active-text="全部" inactive-text="未导入" inactive-color="#ff4949">
                            </el-switch>
                            <el-button type="primary" v-on:click="getPHGoodsList(1)"  >查询</el-button>
                        </el-form>
                         <template>
                                <el-table
                                    ref="multipleTable" :data="phGoods"  border tooltip-effect="dark" row-key='id'
                                    style="width: 100%" @selection-change="handleSelectionChange" :loading="qLoading">
                                    <el-table-column  type="selection" :reserve-selection="true" width="50" align="center" > </el-table-column>

                                    <el-table-column  prop="provGoodsName" label="商品名称" width="200"></el-table-column>
                                    <el-table-column prop="provGoodsCode" label="商品编号" show-overflow-tooltip> </el-table-column>
                                    <el-table-column prop="provName" label="经销商名" show-overflow-tooltip> </el-table-column>
                                     <el-table-column prop="provGoodsMfrsName" label="生产厂家" show-overflow-tooltip> </el-table-column>
                                    <el-table-column prop="provGoodsMade" label="产地" show-overflow-tooltip> </el-table-column>
                                     <el-table-column prop="provGoodsGg" label="规格" show-overflow-tooltip> </el-table-column>
                                      <el-table-column prop="provGoodsUnit" label="单位" show-overflow-tooltip> </el-table-column>

                                </el-table>
                                  <el-pagination @size-change="handlePHGoodsSizeChange" @current-change="handlePHGoodsCurrentChange" :current-page="phGoodsPage" :page-sizes="[10, 100, 200, 400]" :page-size="phGoodsPageSize" layout="total, sizes, prev, pager, next"
                                   :total="phGoodsTotal" style="float:right;" small>
			                      </el-pagination>
                                <div style="margin-top: 20px">

                                    <el-button @click="toggleSelection()">取消选择</el-button>
                                    <el-button @click="goodImprotByphSubmit()">确定</el-button>
                                </div>
                         </template>
                </el-dialog>
                <el-dialog title="从Excel中导入商品" close="onClose" :visible.sync="importExcelVisible" :show-close='false' :close-on-press-escape='false' :close-on-click-modal="false">
                            下载模板:<br> <a :href="HosGoodsTempUrl" >HosGoodsTemp.xls</a>
                        <template>
                                        <div>
                                            <el-upload
                                            class="upload-demo"
                                            :disabled="uploadState"
                                            :action= "uploadUrl"
                                            :on-preview="handlePreview"
                                            :on-remove="handleRemove"
                                            :on-success="handleFileSuccess"
                                            :before-upload="handleBeforeUpload"
                                            :file-list="fileList"
                                            list-type="text">
                                            <el-button  type="primary">点击上传</el-button>
                                            <div slot="tip" class="el-upload__tip">只能上传一个Excel文件</div>
                                            </el-upload>
                                        </div>
                        </template>

                        <div slot="footer" class="dialog-footer">
                            <el-button @click.native="importExcelVisible = false;uploadState=false">取消</el-button>
                            <el-button type="primary" @click.native="importExcelSubmit" :loading="editLoading">提交</el-button>
                        </div>
                </el-dialog>
                <el-dialog title="导入失败数据" close="onClose" :visible.sync="goodImportErrorsVisible" :close-on-click-modal="false" :show-close='false'  :close-on-press-escape='false'  size="large">
                        <template>
                            <el-table
                                ref="multipleTable" :data="goodImportErrors"  border tooltip-effect="dark"
                                style="width: 100%">
                                <el-table-column  prop="goodsName" label="商品名称" width="200"></el-table-column>
                                <el-table-column prop="code" label="商品编号" show-overflow-tooltip> </el-table-column>
                                    <el-table-column prop="mfrsId" label="生产厂家" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="goodsGg" label="规格" show-overflow-tooltip> </el-table-column>
                                    <el-table-column prop="remark" label="失败原因" show-overflow-tooltip> </el-table-column>
                            </el-table>

                            <div style="margin-top: 20px">
                                <el-button @click="goodImprotErrorClose()">关闭</el-button>
                            </div>
                        </template>
                </el-dialog>

                <el-dialog title="产品注册证" close="onClose" :visible.sync="regsListVisible" :close-on-click-modal="true" :show-close='true'  :close-on-press-escape='true' width="85%">
                    <template>
                      <el-table :data="regsList" highlight-current-row style="width: 100%;" border fit class="certsTableHeight" height="200">
                        <!--<el-table-column type="index" label="序号" align="center" width="50"></el-table-column>-->
                        <el-table-column prop="productName" width="200" header-align="center" label="注册证产品名称"></el-table-column>
                        <el-table-column prop="certificateCode" width="250" header-align="center" label="注册证号"></el-table-column>
                        <el-table-column prop="regKind" width="135" align="center" label="注册证类别" :formatter="formatregistCertKind"></el-table-column>
                        <el-table-column prop="cname" width="250" header-align="center" label="生产厂家" ></el-table-column>
                        <el-table-column prop="expdtBeginDate" width="90" align="center" label="开始时间" :formatter="formatterBeginDate"></el-table-column>
                        <el-table-column prop="expdtEndDate" width="90" align="center" label="截止时间" :formatter="formatterEndDate"></el-table-column>
                        <el-table-column label="查看附件"  align="center">
                          <template slot-scope="scope">
                            <template v-for="i in scope.row.provRegImageVos.length">
                              <a :href='imgBasePath+scope.row.provRegImageVos[i-1].filePath'
                                 style="text-decoration: none;" target="_blank">
                                附件{{i}}&nbsp;</a>
                            </template>
                          </template>
                        </el-table-column>
                      </el-table>
                    </template>
                </el-dialog>
		</div>

	<!--选择导出字段界面-->



</template>
<script>
import { fileServer } from '../../common/js/constance';
    export default {
        data() {
            return {
                registCertKindDict:[],
                imgBasePath:'',
                regsList:[],
                regsListVisible:false,
                selectedId:'' ,    //选中的厂家Id
                selectedName:'' ,    //选中的厂家name
                myPorps:{
                   value:'mfrsId',
                   label:'mfrsName'
                },
                flags: [{
                    value: '1',
                    label: '是'
                }, {
                    value: '0',
                    label: '否'
                }],
                HosGoodsTempUrl:'',
                uploadUrl:'',
                filters: {
                  goodsName: '',
                    kindCode:''
                },
				        qLoading:false,
                basGoodsFilters:{ goodsName: '',provId:'',isAll:'1'},
                total: 0,
                page: 1,
                pageSize: 20,
                basGoodsTotal: 0,
                basGoodsPage: 1,
                basGoodsPageSize: 10,
                basGoods:[],
                phGoodsTotal: 0,
                phGoodsPage: 1,
                phGoodsPageSize: 10,
                phGoods:[],
                multipleSelection:[],
                loading: false,
                goods: [],
                dict68s:[],
                kind68code:'',
                uniqueCodeStrategy:'',
                dictUCodeStrategys:[],
                hosKindsCodes:[],
                kindCode:'',
                selectdeProvId:'',
                good: {hisPrice:null,mfrsId:'',mfrsName:""},
                provList:[],
                isMeteringYes:false,
                isMetering:null,
                purMode:'',
                purModes: [],

                modelTitle: "",
                editFormVisible: false,
                editLoading: false,
                goodImportVisible:false,
                goodImportByphVisible:false,
                downLoadVisible:false,
                goodImportErrorsVisible:false,
                goodImportErrors:[],
                importExcelVisible:false,
                fileSrc:'',
                fileMap:{id:'',ext:'',name:'',path:''},
                fileList:[],
                uploadState:false,
                editFormRules: {
                  goodsName: [
                        { required: true, message: '请输入商品名称', trigger: 'blur' }
                    ],
                    code: [
                        { required: true, message: '请输入商品编号', trigger: 'blur' }
                    ],
                    goodsGg: [
                        { required: true, message: '请输入商品规格', trigger: 'blur' }
                    ],
                    unit: [
                        { required: true, message: '请输入商品单位', trigger: 'blur' }
                    ],
                    mfrsId: [
                        { required: true, message: '请选择生产厂商', trigger: 'blur' }
                    ],
                     made: [
                        { required: true, message: '请输入产地', trigger: 'blur' }
                    ],
                     packeage: [
                        {type:"number", required: false, message: '请输入包装，必须为数字', trigger: 'blur' }  //
                    ],
                     hitPrice: [
                        {type:"number", required: false, message: '请输入阳光采购价，必须为数字', trigger: 'blur' }  //hitPrice
                    ],
                     price: [
                        {type:"number", required: false, message: '请输入价格，必须为数字', trigger: 'blur' }  //
                    ],
                    brand: [
                          {max:128,message:'品牌必须小于128个字符', trigger: 'blur'}
                    ],
                    purMode: [
                        { required: true, message: '请选择采购模式', trigger: 'blur' }
                    ],
                     hisPrice: [
                        {type:"number", required: false, message: '请输入HIS销售价，必须为数字', trigger: 'blur' }  //
                    ]
                },
            errors: {
				goodsNameErr: '',
				codeErr: '',
                goodsGgErr:'',
                unitErr:'',
                mfrsIdErr:'',
                madeErr:'',
                brandErr:'',
                packeageErr:'',
                hitPriceErr:'',
                hisPriceErr:'',
                purModeErr:'',
                priceErr:''
			},
            selectExportColumnsTitle:"选择需要导出的列",
            selectExportColumnsVisible:false,
            checkedColumns:[],
            checkAll: true,
            isIndeterminate: true,
            columns:[],
            mfrs:[] ,    //生产厂家
            mfr:{id:'',mfrsName:''},

             multipleSelection: [],
            SL:[],
            taxRate:''

            }
        },
        methods: {
          getZCZLX: function () {   //注册证类型 字典
            this.axios.post('/platformService/sys/dict/getDictValueByDictEname', { dictName: 'ZCZLX' }).then(res => {
              if (res.data.code == "0") {
                this.registCertKindDict = res.data.data;
              }else {
                this.$message.error(res.data.msg);
              }
            }, err => {
            });
          },
          /**
           * 格式化注册证类型
           */
          formatregistCertKind (row){
            return this.registCertKindDict.find(item => item.val == row.regKind).ename;
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
          },

            /* 输入框自动补全**/
            querySearch(queryString, cb) {
                let restaurants = this.mfrs;
                let results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
                // 调用 callback 返回建议列表的数据
                cb(results);
            },
            createFilter(queryString) {
                return (restaurant) => {
                return (restaurant.mfrsName.indexOf(queryString) >= 0);
                };
            },
            handleSelect(item) {
                    this.selectedId= item.id;
                    this.selectedName=item.mfrsName;
            },
          /* 输入框自动补全**/
            //获取列表
            getGoodsList: function (pIndex) {
                this.page = pIndex;
                let param = { orderBy: "", queryObject: { goodsName: this.filters.goodsName,kindCode:this.filters.kindCode,flag:this.filters.flag}, page: this.page, rows: this.pageSize };
                this.loading = true;
                this.axios.post('/spdHDIService/myGoods/hosGoods/getGoodsInfos', param).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                        this.goods = res.data.data.data;
                        this.total = res.data.data.total;

                    }
                    else{
                        this.$message.error( res.data.msg);
					}
                }, err => {
                    this.loading = false;
                });
            },

            handleCurrentChange(val) {
                this.page = val;
                this.getGoodsList(this.page);
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getGoodsList(this.page);
            },
             handleBasGoodsCurrentChange(val) {
                 this.basGoodsPage = val;
                  this.getBasGoodsList( this.basGoodsPage);
            },
            handleBasGoodsSizeChange(val) {
                this.getBasGoodsList( this.basGoodsPage);
            },
            handlePHGoodsCurrentChange(val) {
                 this.phGoodsPage = val;
                  this.getPHGoodsList( this.phGoodsPage);
            },
            handlePHGoodsSizeChange(val) {
                this.getPHBasGoodsList( this.phGoodsPage);
            },
            goodEdit(item) {
                 this.errors.goodsNameErr = '';

				 this.errors.codeErr= '';
                 this.errors.goodsGgErr= '';
                 this.errors.unitErr= '';
                 this.errors.mfrsIdErr= '';
                 this.errors.madeErr= '';
                 this.errors.packeageErr= '';
                 this.errors.hitPriceErr= '';
                 this.errors.hisPriceErr= '';
                 this.errors.priceErr= '';
                 this.errors.brandErr= '';
                 this.errors.purModeErr = '';

                if (item) {
                    let good = Object.assign({}, item);

                    this.good = good;
                    this.taxRate= this.good.taxRate ? this.good.taxRate:0.17;
                    this.kindCode=this.good.kindCode;
                    this.kind68code=this.good.kind68code;
                    this.modelTitle = "修改商品";
                    this.selectedId =item.mfrsId;
                    this.selectedName=item.mfrsName;
					this.uniqueCodeStrategy=this.good.uniqueCodeStrategy ? this.good.uniqueCodeStrategy:'3';


                } else {
                    this.good={};
                    this.kind68code='';
                    this.uniqueCodeStrategy=3; //默认值 非唯一码管理
                    this.kindCode='';
                    this.taxRate='0.17';
                    this.modelTitle = "添加商品";
                    this.selectedId='';
                    this.selectedName='';
                }
                this.editFormVisible = true;
              //  this.$refs.editForm.resetFields();

            },
            delGood(good) {
                this.$confirm('确认删除这条记录吗?', '提示', {
                }).then(() => {
                    this.loading = true;
                    this.axios.post("/spdHDIService/myGoods/hosGoods/delGoodsInfo", good).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({ message: '操作成功！', type: 'success' });
                        } else {
                            this.$message.error( res.data.msg);

                        }
						this.getGoodsList(this.page);
                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {

                });
            },
            actGood(good) {
                this.$confirm('确认激活这条记录吗?', '提示', {
                }).then(() => {
                    this.loading = true;
                    good.flag = '1';
                    this.axios.post("/spdHDIService/myGoods/hosGoods/update", good).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({ message: '操作成功！', type: 'success' });
                        } else {
                            this.$message.error( res.data.msg);

                        }
						this.getGoodsList(this.page);
                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {

                });
            },

          getRegInfos:function(row){
            this.axios.get("/spdHDIService/provRegInfo/getProvRegsBySpdGoodsCode",{
              params: {
                'spdGoodsCode': 'good-'+row.spdGoodsCode
              }
            }).then(res => {
              if (res.code < 0) {
                this.$message.error('系统错误，请联系管理员。')
                return
              }
              this.regsList = res.data.data;
              this.regsListVisible = true;
            }).catch(_ => {
              this.$message.error('数据异常，请联系管理员。')
            })
          },


            editSubmit: function () {

                this.good.mfrsName=this.selectedName;
                this.good.mfrsId=this.selectedId;
                this.good.taxRate = this.taxRate;
                this.good.kindCode = this.kindCode;
                this.good.kind68code = this.kind68code;
                this.good.uniqueCodeStrategy= this.uniqueCodeStrategy;
                this.good.price = this.good.price ? this.good.price:0;
                let _this = this;
                let form =  this.$refs.editForm;
                const h = this.$createElement;
                let arr=new Array();
                form.validate((valid) => {
                    if (valid) {
                        this.editLoading = true;
                        let serviceUrl = this.good.id ? "/spdHDIService/myGoods/hosGoods/update" : "/spdHDIService/myGoods/hosGoods/insert";
                        this.axios.post(serviceUrl, this.good).then(res => {
                            this.editLoading = false;
                            if (res.data.code == "0") {
                                this.$message({ message: '操作成功！', type: 'success' });
                                this.editFormVisible = false;
                                this.getGoodsList(this.page);
                            } else if (res.data.code == "-122") {
                                    let err = res.data.validateErrors.fieldErrors[0];
                                        if (err.field == "code") {
                                        this.errors.codeErr += err.message;
                                           arr.push(h('span',null,err.message));
                                        }
                                         if (err.field == "mfrsId") {
                                        this.errors.mfrsIdErr += err.message;
                                           arr.push(h('span',null,err.message));
                                        }
                                          _this.$msgbox({title:"错误",type:"error",message:h('p',null,arr)});

                                 }
                            else {
                                this.$message.error('操作失败！');
                            }
                        }, err => {
                            this.editLoading = false;
                        });
                     }else{
                        let fields = form.fields;
                        let msg ="";
                      fields.forEach(f=>{
                          if(f.validateState =="error"){
                              msg +="</br>"+f.validateMessage;
                              if (arr.length>0) arr.push(h('br',null,null));
                              arr.push(h('span',null,f.validateMessage));
                          }
                      });
                      _this.$msgbox({title:"错误",type:"error",message:h('p',null,arr)});
                    }
                });
            },
            exportExcel:function(){
                this.selectExportColumnsVisible=true;
                //列出可选字段
                    this.axios.post('/spdHDIService/myGoods/hosGoods/getAllColumns', null).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                      this.columns =res.data.data;
                    }else{
                        this.$message.error( res.data.msg);
					          }
                }, err => {
                    this.loading = false;
                });
            },
             goodsImport : function(){
                this.goodImportVisible =true;
                this.getBasGoodsList(1);
            },
            goodsImportByph : function(){
			          this.$refs.multipleTable.clearSelection();
                this.goodImportByphVisible =true;
                this.getUnitListsByHos();
                this.getPHGoodsList(1);
            },

         goodImprotByphSubmit:function(){


              if(this.multipleSelection.length<=0){

                   this.$message.error( "至少选择一条数据！");
                  return;
              }

                 this.axios.post('/spdHDIService/myGoods/hosGoods/goodImprotFromPHGoods', this.multipleSelection).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                     this.goodImportByphVisible =false;
                       this.getGoodsList(1);

                    }
                    else{
                        this.$message.error( res.data.msg);
					}
                     this.$refs.multipleTable.clearSelection();

                }, err => {
                    this.loading = false;
                });

            },

            goodImprotSubmit:function(){
              if(this.multipleSelection.length<=0){
                   this.$message.error( "至少选择一条数据！");
                  return;
              }
                 this.axios.post('/spdHDIService/myGoods/hosGoods/goodImprotFromBasGoods', this.multipleSelection).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                     this.goodImportVisible =false;
                       this.getGoodsList(1);

                    }
                    else{
                        this.$message.error( res.data.msg);
					}
                }, err => {
                    this.loading = false;
                });

            },

            importExcel:function(){
                 this.fileList=[];
                 this.importExcelVisible=true;
            },
           importExcelSubmit:function(){
                this.uploadState=false;
                this.axios.post('/spdHDIService/myGoods/hosGoods/importExcel', this.fileMap).then(res => {
                                    this.loading = true;
                                    if (res.data.code == "0") {

                                       this.loading = false;
                                        this.goodImportErrorsVisible =true;
                                        this.goodImportErrors=res.data.data;
                                    }
                                    else{
                                        this.$message.error( res.data.msg);
                                    }
                                }, err => {
                                    this.loading = false;
                                    this.importExcelVisible=false;
                                });

            },

               handleRemove(file, fileList) {
                    this.uploadState=false;
                },
                handlePreview(file) {
                },
                handleFileSuccess(response, file, fileList){
                    let ob  = response.data[0];

                    this.fileMap=ob;
                    this.uploadState=true;
                },
                handleBeforeUpload(file){
                },
              handleCheckAllChange(event) {
                    this.checkedColumns = event.target.checked ? this.columns : [];
                    this.isIndeterminate = false;
                },
                handleCheckedColumnsChange(value) {
                    let checkedCount = value.length;
                    this.checkAll = checkedCount === this.columns.length;
                    this.isIndeterminate = checkedCount > 0 && checkedCount < this.columns.length;
               },
               selectExportColumnsSubmit:function(){

                   if(this.checkedColumns.length<=0){
                         this.$message.error( "至少选择一条数据！");
                          return;
                   }

                let param = this.checkedColumns;
                this.loading = true;
                this.axios.post('/spdHDIService/myGoods/hosGoods/exportGoodsInfos', param).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {

                    }
                    else if(res.data.code == "998"){
                          this.downLoadVisible =true;
                          this.fileSrc=res.data.msg;
                    }

                    else{
                        this.$message.error( res.data.msg);
					}
                }, err => {
                    this.loading = false;
                });


               },
             goodImprotErrorClose:function(){
                     this.goodImportErrorsVisible =false;
                      this.importExcelVisible=false;
            },
                         //获取产家 供选择使用
            getMfrs: function () {


                this.loading = true;
                this.axios.post('/spdHDIService/bas/mfrsInfo/basMfrsInfoList', {}).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
						this.mfrs = res.data.data.data;
					    	if(this.mfrs)
                             this.mfr=this.mfrs[0];
                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            },

            toggleSelection(rows) {
                    if (rows) {
                    rows.forEach(row => {
                        this.$refs.multipleTable.toggleRowSelection(row);
                    });
                    } else {
                    this.$refs.multipleTable.clearSelection();
                    }
                },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
              multDel(){
                 if(this.multipleSelection.length<=0){

                   this.$message.error( "至少选择一条数据！");
                  return;
                  }

                   this.$confirm('确认删除选中记录吗?', '提示', {
                }).then(() => {
                    this.loading = true;
                    this.axios.post("/spdHDIService/myGoods/hosGoods/multDel", this.multipleSelection).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.loading = false;
                            this.$message({ message: '操作成功！', type: 'success' });

                        } else {
                            this.$message.error( res.data.msg);

                        }
						this.getGoodsList(this.page);
						this.$refs.multipleTable.clearSelection();

                    }, err => {
                        this.loading = false;
                    });
                }).catch(() => {

                });
            },
              getPHGoodsList: function (pIndex) { //获取供货列表供批量导入
                this.phGoodsPage = pIndex;
                let param = { orderBy: "", queryObject: { provGoodsName: this.basGoodsFilters.goodsName,provId:this.basGoodsFilters.provId,isAll:this.basGoodsFilters.isAll}, page: this.phGoodsPage, rows: this.phGoodsPageSize };
                //this.loading = true;
				this.qLoading = true;
                this.axios.post('/spdHDIService/myGoods/provGoodsHos/getPHGoodsList', param).then(res => {
                    //this.loading = false;
					this.qLoading = false;
                    if (res.data.code == "0") {
                        this.phGoods = res.data.data.data;
                        this.phGoodsTotal = res.data.data.total;
                   //     this.basGoodsPage = res.data.data.pageNum;
                    }
                    else{
                        this.$message.error( res.data.msg);
					}


                }, err => {
                    //this.loading = false;
					this.qLoading = false;
                });
            },
             //获取平台商品列表供批量导入
            getBasGoodsList: function (pIndex) {
                this.basGoodsPage = pIndex;
                let param = { orderBy: "", queryObject: { goodsName: this.basGoodsFilters.goodsName}, page: this.basGoodsPage, rows: this.basGoodsPageSize };
                this.loading = true;
                this.axios.post('/spdHDIService/bas/goods/getGoodsInfos', param).then(res => {
                    this.loading = false;
                    if (res.data.code == "0") {
                        this.basGoods = res.data.data.data;
                        this.basGoodsTotal = res.data.data.total;
                   //     this.basGoodsPage = res.data.data.pageNum;
                    }
                    else{
                        this.$message.error( res.data.msg);
					}
                }, err => {
                    this.loading = false;
                });
            },
            getUnitListsByHos:function(){     //获取经销商
                this.loading = true;

                let param = { orderBy: "", queryObject: {}, page:0, rows: 0 };
                    this.axios.post('/spdHDIService/provManager/hosProvInfo/hosProvInfoVoList',param).then(res => {
                        this.loading = false;
                        if (res.data.code == "0") {
                            this.provList = res.data.data.data;
                        }
                        else{
                            this.$message.error( res.data.msg);
                        }
                    }, err => {
                        this.loading = false;
                    });
            },
                //获取产家 供选择使用
            getSL: function () {


                this.loading = true;
                  this.axios.post('/platformService/sys/dict/getDictValueByDictEname',{dictName:'SL'}).then(res => {                    this.loading = false;
                    if (res.data.code == "0") {
						this.SL = res.data.data;
                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            },
             getDict68s: function () {
                this.loading = true;
                  this.axios.post('/platformService/sys/dict/getDictValueByDictEname',{dictName:'68FL'}).then(res => {                    this.loading = false;
                    if (res.data.code == "0") {
						this.dict68s = res.data.data;



                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                }, err => {
                    this.loading = false;
                });
            },
            getDictUCodeStrategys:function(){
                 this.loading = true;
                  this.axios.post('/platformService/sys/dict/getDictValueByDictEname',{dictName:'WYMGLCL'}).then(res => {

                    if (res.data.code == "0") {
						this.dictUCodeStrategys = res.data.data;

                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                      this.loading = false;
                }, err => {
                    this.loading = false;
                });
            },
           getCGMS: function () {
                this.axios.post('/platformService/sys/dict/getDictValueByDictEname', {dictName: 'CGMS'}).then(res => {
                    if (res.data.code == '0') {
                    this.purModes = res.data.data
                    } else {
                    this.$message.error(res.data.msg)
                    }
                }, err => {})
            },
            getHosKindCodes:function () {
                this.loading =true;
                this.axios.post('/spdHDIService/baseData/hosKindcode/list',{hosId:this.user.corpId}).then(res => {

                    if (res.data.code == "0") {
                        this.hosKindsCodes = res.data.data;
                    }
                    else{
                        this.$message.error( res.data.msg);
                    }
                    this.loading = false;
                }, err => {
                    this.loading = false;
                });

            }




        },

        mounted() {
            this.imgBasePath = fileServer.imgBasePath;
            this.getZCZLX();
            this.getGoodsList(1);
            this.getMfrs();
            this.getSL();
            this.getDict68s();
            this.getDictUCodeStrategys();
            this.getHosKindCodes();
            this.getCGMS()
         //   this.HosGoodsTempUrl=fileServer.download+'myGoods/HosGoodsTemp.xls';
            this.HosGoodsTempUrl='/upload/myGoods/HosGoodsTemp.xls';
            this.uploadUrl =fileServer.upload +"myGoods";

        }
    };

</script>
<style scoped>

.hosgoods-tableheight {
        height:  calc(100% - 95px);
}
</style>

