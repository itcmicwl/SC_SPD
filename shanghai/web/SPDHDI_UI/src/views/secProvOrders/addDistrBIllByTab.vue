<template>
   <div id="addDistrTab" style="height:100%;">
    <el-row :gutter="10" v-loading="pageLoading" style="height:100%;">
        <el-col :span="24" style="height:100%;">
            <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                <el-form label-width="120px" class="orderInput">
                    <el-col :span="8">
                        <el-form-item label="请购机构">
                            <span>{{distrBill.purchaseCompanyName}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="请购科室">
                            <span>{{distrBill.applyOrgName}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="收货科室">
                            <span>{{distrBill.recieveOrgName}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="配送机构">
                            <span>{{distrBill.subProvName}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="制单时间">
                            <span>{{distrBill.fillDate}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="制单人">
                            <span>{{distrBill.fillterName}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="备注">
                            <el-input type="textarea" v-model="distrBill.remark"  autosize>
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-form>
            </el-col>
            <!--条码-->
            <el-col :span="4" class="toolbar" style="padding-bottom: 0px;">
                <el-switch v-model="barcodeType" active-text="仅主码" inactive-text="主副码" @change="barcodeTypeChange">
                </el-switch>
            </el-col>
            <el-col :span="18" class="toolbar" v-show="barcodeType" style="padding-bottom: 0px;">
                <el-input type="text" class="input-barcode" ref="mbarcode" v-model="barcode" @keyup.native.enter="addBybarcode"  placeholder="请录入条形码">
                </el-input>
            </el-col>
            <el-col :span="18" class="toolbar" v-show="!barcodeType" style="padding-bottom: 0px;">
                <el-col :span="10">
                    <el-input type="text" class="input-barcode" ref="mainBarcode" v-model="mainBarcode" @keyup.native.enter="barcodeNext" autosize placeholder="请录入条形码主码">
                    </el-input>
                </el-col>
                <el-col class="line" :span="2">-</el-col>
                <el-col :span="10">
                    <el-input type="text" class="input-barcode" ref="fbarcode" v-model="fBarcode" @keyup.native.enter="barcodePre" autosize placeholder="请录入条形码副码">
                    </el-input>
                </el-col>
            </el-col>
            <el-table :data="distrBill.distrBillList" style="width: 100%;" fit border class="table_sub_content">
                <el-table-column label="操作" width="100">
                    <template slot-scope="scope">
                        <div class="icoBtn">
                            <i class="fa fa-copy fa-lg" @click="copyDetail(scope.row)" title="复制"></i>
                            <i class="fa fa-edit fa-lg" @click="editOthers(scope.row)" title="编辑其他信息"></i>
                            <!-- <i class="fa fa-barcode fa-lg" @click="editBarcode(scope.row)" title="编辑条码"></i>
                                                                                                        <i class="fa fa-thermometer fa-lg" @click="editHumiture(scope.row)" title="编辑温湿度"></i> -->
                            <i class="fa fa-trash-o fa-lg" @click="delRow(scope.row)" title="删除" style="color:red;margin-left:5px;"></i>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-width="120px" class="el-form-item-nomsg">
                          <el-row>
                            <el-col :span="4">
                                <el-form-item label="销售单号">
                                    <el-input v-model="props.row.saleBillId" placeholder="销售单号" ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="货运方式">
                                <el-select v-model="props.row.transportType" placeholder="请选择货运方式">
								    <el-option label="外运" value="外运"></el-option>
                                    <el-option label="自运" value="自运"></el-option>
                                    <el-option label="自提" value="自提"></el-option>                  
                                </el-select>
                                </el-form-item>
                            </el-col>
                         
                        </el-row>
                            <!--
                            <el-col :span="4">
                                <el-form-item label="唯一码管理">
                                     {{f_uniqueKind(null,null,props.row.isUniqueCode) }}
                                </el-form-item>
                            </el-col>
                            
                            -->
                            <!--
                            <el-col :span="12">
                                <el-form-item label="批次号">
                                    <el-input v-model="props.row.batchNum" placeholder="批次号" ></el-input>
                                </el-form-item>
                            </el-col>
                            -->
                            <el-row>
                                <el-col :span="4">
                                    <el-form-item label="批号">
                                        <el-input v-model="props.row.batchCode" placeholder="批号" ></el-input>
                                    </el-form-item>
                                </el-col>
                            
                                <el-col :span="4">
                                    <el-form-item label="阳光编码">
                                        <el-input v-model="props.row.hitCode" placeholder="阳光编码" ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row>
							<el-col :span="4">
								<el-form-item label="注册证号" width="200">
									<el-input v-model="props.row.certificateCode" placeholder="注册证号" ></el-input>
								</el-form-item>
                            </el-col>      
                            <el-col :span="4">
                                <el-form-item label="单位">
                                    <el-input v-model="props.row.sendUnit" disabled ></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4">
                                <el-form-item label="有效日期">
                                    <el-date-picker v-model="props.row.expdtEndDate" type="date" placeholder="选择日期" >
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="送货日期">
                                    <el-date-picker type="datetime" v-model="props.row.deliveryDate" placeholder="送货日期" ></el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                          <el-row>
                            <el-col :span="4">
                                <el-form-item label="生产日期">
                                    <el-date-picker v-model="props.row.productDate" type="date" placeholder="选择日期" >
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="灭菌日期">
                                    <el-date-picker v-model="props.row.sterilizationDate" placeholder="灭菌批号" ></el-date-picker>
                                </el-form-item>
                            </el-col>
                         </el-row>
                         <el-row>
                            <el-col :span="4">
                                <el-form-item label="灭菌有效日期">
                                    <el-date-picker v-model="props.row.sterilizationEndDate" placeholder="灭菌批号" ></el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="灭菌批号">
                                    <el-input v-model="props.row.sterilizationCode" placeholder="灭菌批号" ></el-input>
                                </el-form-item>
                            </el-col>
                          </el-row>
                       
                    
                          <el-row>
                            <el-col :span="4">
                                <el-form-item label="温度">
                                  <el-input v-model="props.row.sendTemprature" placeholder="温度" ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="湿度">
                                  <el-input v-model="props.row.sendHumidity" placeholder="湿度" ></el-input>
                                </el-form-item>
                            </el-col>

                        </el-row>                      
                        <el-row>
                                <el-col :span="4">
                                <el-form-item label="条码">
                                    <el-input v-model="props.row.barcode" placeholder="条码" ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="说明">
                                    <el-input v-model="props.row.memo" placeholder="说明" ></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        </el-form>

                    </template>
                </el-table-column>
                <el-table-column prop="rowNumber" label="序号" width="60">
                </el-table-column>
                <el-table-column prop="provGoodsErpName" label="商品名称" width="200">
                </el-table-column>
                <el-table-column prop="goodsGg" label="规格" width="200">
                </el-table-column>
                <el-table-column prop="canSendQty" label="未发数量">
                </el-table-column>
                <el-table-column label="配送数量" width="120">
                    <template slot-scope="scope">
                        <el-input-number placeholder="请输入数量" v-model="scope.row.sendQty" size="mini" :min="0" :max="scope.row.canSendQty" class="col-input-num" @change="qtyChange($event,scope.row)">
                        </el-input-number>
                    </template>
                </el-table-column>
                <el-table-column label="采购价" width="100">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.subSendPrice"  @blur="checkPrice($event,scope.row)">
                        </el-input>
                    </template>
                </el-table-column>
				<el-table-column label="销售价" width="100">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.sendPrice"  @blur="checkPrice($event,scope.row)">
                        </el-input>
                    </template>
                </el-table-column>        
                <el-table-column label="批号">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.batchCode" placeholder="批号" ></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="有效日期" width="100">
                    <template slot-scope="scope">
                        <el-date-picker v-model="scope.row.expdtEndDate" type="date" placeholder="选择日期" >
                        </el-date-picker>
                    </template>
                </el-table-column>
                <el-table-column label="生产日期" width="100">
                    <template slot-scope="scope">
                        <el-date-picker v-model="scope.row.productDate" type="date" placeholder="选择日期" >
                        </el-date-picker>
                    </template>
                </el-table-column>
                <el-table-column label="阳光编码">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.hitCode" placeholder="阳光编码" ></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="温度">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.sendTemprature" placeholder="说明" ></el-input>
                    </template>
                </el-table-column>
               <el-table-column label="注册证号" width="250">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.certificateCode" placeholder="注册证号" ></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="sendUnit" label="单位">
                </el-table-column>
                <el-table-column label="送货日期" width="120">
                    <template slot-scope="scope">
                        <el-date-picker type="datetime" v-model="scope.row.deliveryDate"  placeholder="选择日期" >
                        </el-date-picker>
                    </template>
                </el-table-column>

                <el-table-column label="灭菌日期" width="100">
                    <template slot-scope="scope">
                        <el-date-picker v-model="scope.row.sterilizationDate" type="date" placeholder="选择日期" >
                        </el-date-picker>
                    </template>
                </el-table-column>
                <el-table-column label="灭菌有效日期" width="100">
                    <template slot-scope="scope">
                        <el-date-picker v-model="scope.row.sterilizationEndDate" type="date" placeholder="选择日期" >
                        </el-date-picker>
                    </template>
                </el-table-column>
                <el-table-column label="灭菌批号">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.sterilizationCode" placeholder="灭菌批号" ></el-input>
                    </template>
                </el-table-column>

                <el-table-column label="湿度">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.sendHumidity" placeholder="说明" ></el-input>
                    </template>
                </el-table-column>

                <el-table-column label="销售单号">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.saleBillId" placeholder="销售单号" ></el-input>
                    </template>
                </el-table-column> 

                <el-table-column label="货运方式" width="100">
                    <template slot-scope="scope">
                        <el-select v-model="scope.row.transportType" placeholder="请选择货运方式">
						    <el-option label="外运" value="外运"></el-option>
                            <el-option label="自运" value="自运"></el-option>
                            <el-option label="自提" value="自提"></el-option>
                          
                        </el-select>
                    </template>
                 </el-table-column>             
                   
                <el-table-column label="条码类型" prop="uniqueKind" :formatter="f_uniqueKind" width='90'>
                </el-table-column>
                <el-table-column label="出库库区">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.outStorearea" placeholder="出库库区" ></el-input>
                    </template>
                </el-table-column>
                <!-- <el-table-column label="出库类型">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.outKind" placeholder="出库类型" ></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="换货标识">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.isExchange" placeholder="换货标识" ></el-input>
                    </template>
                </el-table-column> -->
                <el-table-column label="定量包装">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.packetCode" placeholder="定量包装" ></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="条码">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.barcode" placeholder="条码" ></el-input>
                    </template>
                </el-table-column>
         
                <el-table-column label="说明">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.memo" placeholder="说明" ></el-input>
                    </template>
                </el-table-column>
             
            </el-table>
            <el-col :span="24" class="toolbar" style="margin-top:10px; padding-bottom: 0px;">
                <div style="margin-left:100px;text-align: center;">
                    <el-button type="success"  @click="submitDistrBill('20')">提交</el-button>
                    <!-- <el-button type="warning"  @click="submitDistrBill('10')">保存</el-button> -->
                    <el-button type="danger"  @click="cancel">取消</el-button>
                </div>
            </el-col>
            <el-dialog title="其他信息设置" :visible.sync="otherV" :close-on-click-modal="false"  @close="distrDetailClose">
                <el-form label-width="120px" class="el-form-item-nomsg">
                    <el-col :span="12">
                        <el-form-item label="销售单号">
                            <el-input v-model="distrDetail.saleBillId" placeholder="销售单号" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="货运方式">
                            <el-select  v-model="distrDetail.transportType"  placeholder="请选择货运方式">
							<el-option label="外运" value="外运"></el-option>
                            <el-option label="自运" value="自运"></el-option>
                            <el-option label="自提" value="自提"></el-option>
                          </el-select>                       
                       </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="批次号">
                            <el-input v-model="distrDetail.batchNum" placeholder="批次号" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="唯一码管理">
                             {{f_uniqueKind(null,null,distrDetail.isUniqueCode) }}
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="批号">
                            <el-input v-model="distrDetail.batchCode" placeholder="批号" ></el-input>
                        </el-form-item>
                    </el-col>
                     <el-col :span="12" width='200'>
                        <el-form-item label="注册证号">
                            <el-input v-model="distrDetail.certificateCode" placeholder="注册证号" ></el-input>
                        </el-form-item>
                    </el-col>
                    
                    <el-col :span="12">
                        <el-form-item label="有效日期">
                            <el-date-picker v-model="distrDetail.expdtEndDate" type="date" placeholder="选择日期" >
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="生产日期">
                            <el-date-picker v-model="distrDetail.productDate" type="date" placeholder="选择日期" >
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="灭菌日期">
                            <el-date-picker v-model="distrDetail.sterilizationDate" placeholder="灭菌批号" ></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="灭菌有效日期">
                            <el-date-picker v-model="distrDetail.sterilizationEndDate" placeholder="灭菌批号" ></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="灭菌批号">
                            <el-input v-model="distrDetail.sterilizationCode" placeholder="灭菌批号" ></el-input>
                        </el-form-item>
                    </el-col>
    
                    <el-col :span="12">
                        <el-form-item label="出库库区">
                            <el-input v-model="distrDetail.outStorearea" placeholder="出库库区" ></el-input>
                        </el-form-item>
                    </el-col>
                    <!-- <el-col :span="12">
                        <el-form-item label="出库类型">
                            <el-input v-model="distrDetail.outKind" placeholder="出库类型" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="换货标识">
                            <el-input v-model="distrDetail.isExchange" placeholder="换货标识" ></el-input>
                        </el-form-item>
                    </el-col> -->
                    <el-col :span="12">
                        <el-form-item label="定量包装">
                            <el-input-number v-model="distrDetail.packetCode"  :min="0"></el-input-number>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="条码">
                            <el-input v-model="distrDetail.barcode" placeholder="条码" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="送货日期">
                            <el-date-picker type="datetime" v-model="distrDetail.deliveryDate" placeholder="送货日期" ></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="温度">
                            <el-input v-model="distrDetail.sendTemprature" placeholder="说明" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="湿度">
                            <el-input v-model="distrDetail.sendHumidity" placeholder="说明" ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="说明">
                            <el-input v-model="distrDetail.memo" placeholder="说明" ></el-input>
                        </el-form-item>
                    </el-col>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="otherV = false" >取消</el-button>
                    <el-button type="primary" @click.native="otherV = false" >确定</el-button>
                </div>
            </el-dialog>
            <el-dialog title="条码设置" :visible.sync="barcodeV" :close-on-click-modal="false"  @close="distrDetailClose">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form label-width="120px" class="el-form-item-nomsg">
                        <el-col :span="12">
                            <el-form-item label="商品名称">
                                <el-input v-model="distrUnique.goodsName" :disabled="true" placeholder="商品名称" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="批次号">
                                <el-input v-model="distrUnique.batchCode" placeholder="批次号" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="唯一码">
                                <el-input v-model="distrUnique.uniqueCode" placeholder="唯一码" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item>
                                <el-button type="primary" @click.native="addBarCode" >添加</el-button>
                            </el-form-item>
                        </el-col>
                    </el-form>
                </el-col>
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-tag type="primary" v-for="item in distrUniqueList" :key="item.id" :closable="true" :title="item.batchCode" @close="delBarCode(item)">
                        <i class="fa fa-barcode"></i>
                        {{item.uniqueCode}}
                    </el-tag>
                </el-col>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="barcodeV = false" >关闭</el-button>
                    <el-button type="primary" @click.native="setBarCode" >确定</el-button>
                </div>
            </el-dialog>
            <el-dialog title="温湿度设置" :visible.sync="humitureV" :close-on-click-modal="false"  @close="distrDetailClose">
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form label-width="120px" class="el-form-item-nomsg">
                        <el-col :span="12">
                            <el-form-item label="商品名称">
                                <el-input v-model="distrHumiture.goodsName" placeholder="商品名称" :disabled="true" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="箱号">
                                <el-input v-model="distrHumiture.boxCode" placeholder="箱号" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="产品批次">
                                <el-input v-model="distrHumiture.batchCode" placeholder="箱号" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="唯一码">
                                <el-input v-model="distrHumiture.uniqueCode" placeholder="唯一码" ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="温度">
                                <el-input-number v-model="distrHumiture.temprature" :min="1" :max="100" ></el-input-number>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="湿度">
                                <el-input-number v-model="distrHumiture.humidity" :min="1" :max="100" ></el-input-number>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="测量时间">
                                <el-date-picker v-model="distrHumiture.measureDate" type="datetime" placeholder="选择日期时间" >
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item>
                                <el-button type="primary" @click.native="addHumiture" >添加</el-button>
                            </el-form-item>
                        </el-col>
                    </el-form>
                </el-col>
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-tag type="primary" v-for="item in distrHumitureList" :key="item.id" :closable="true" :title="item.measureDate.toLocaleString()" @close="delHumiture(item)">
                        <i class="fa fa-thermometer"></i>
                        {{item.temprature}}℃ / {{item.humidity}}%
                    </el-tag>
                </el-col>
                <div slot="footer" class="dialog-footer">
                    <el-button @click.native="humitureV = false" >关闭</el-button>
                    <el-button type="primary" @click.native="setHumiture" >确定</el-button>
                </div>
            </el-dialog>
            <barcodeMathc :provId="barcodeMatch.provId" :hosId="barcodeMatch.hosId" :mfrsCode="barcodeMatch.mfrsCode" :barcodeMatchShow="barcodeMatchV" v-on:barcodeMatchSave="barcodeMatchSave" v-on:barcodeMatchClose="barcodeMatchV = false"></barcodeMathc>
        </el-col>
    </el-row>
   </div>
</template>
<script>
import moment from 'moment';
import barcodeUtil from '../../common/js/barcodeUtil';
import barcodeMathc from '../orderMgr/barcodeMathc';
var Child = {
  template:
    '<div><p v-for="item in errMsg" v-html="item"></p></div>',
  props: ["errMsg", "vGoOn"]
};
export default {
    data() {
        return {
            pageLoading: false,
            opModule: 'ADD',
            orgLoading: false,
            barcode: '',
            mainBarcode: '',
            fBarcode: '',
            barcodeType: true,
            barcodeMatchV: false,
            barcodeMatch: {
                provId: '',
                hosId: '',
                mfrsCode: ''
            },
            distrBill: {
                id: '',
                billId: '',
                spdCode: '',
                purType:10,
                purchaseCompanyId: '',
                purchaseCompanyCode: '',
                purchaseCompanyName: '',
                purchaseCompanyErpCode: '',
                applyOrgId: '',
                applyOrgCode: '',
                applyOrgName: '',
                applyOrgScmCode: '',
                applyOrgErpCode: '',
                recieveOrgId: '',
                recieveOrgCode: '',
                recieveOrgName: '',
                recieveOrgScmCode: '',
                recieveOrgErpCode: '',
                provId: '',
                provCode: '',
                provName: '',
                provErpCode: '',
                fillter: '',
                remark: '',
                sumRow: 0,
                status: '',
                fillDate: '',
                version: '',
                distrBillList: []
            },

            constDistrDetail: {
                id: '',
                pid: '',
                billId: '',
                rowNumber: '',
                purconfirmBillId: '',
                purconfirmRowNumber: '',
                purBillId: '',
                purRowNumber: '',
                purBillDate: '',
                applyBillId: '',
                saleBillId: '',
                deliveryDate: new Date(),
                provGoodsId: '',
                goodsGg: '',
                goodsMrfsId: '',
                made: '',
                hosGoodsId: '',
                hosGoodsCode: '',
                hosGoodsName: '',
                provGoodsErpCode: '',
                provGoodsErpName: '',
                batchNum: '',
                hitCode:'',
                isUniqueCode: "1",
                batchCode: '',
                sterilizationCode: '',
                sterilizationEndDate: '',
                sterilizationDate: '',
                expdtEndDate: '',
                productDate: '',
                sendUnit: '',
                sendScale: '',
                canSendQty: '',
                sendQty: '',
                sendPrice: 0,
                hosUnit: '',
                hosQty: '',
                hosPrice: '',
                outStorearea: '',
                outKind: '',
                sumRow: '',
                memo: '',
                barcode: '',
                packetCode: '',
                sourceData: 0,
                isExchange: '',
                sendTemprature: 0,
                sendHumidity: 0,
                version: '',
                transportType:"外运",
                distrBillUniqueList: [],
                distrBillHumitureList: []
            },
            otherV: false,
            barcodeV: false,
            humitureV: false,
            distrDetail: {
                distrBillUniqueList: [],
                distrBillHumitureList: []
            },
            distrUnique: {
                id: '',
                pid: '',
                distrRowNumber: 0,
                provGoodsId: '',
                goodsName: '',
                batchCode: '',
                uniqueCode: '',
                sourceData: 0,
                version: 0
            },
            distrHumiture: {
                id: '',
                pid: '',
                distrRowNumber: 0,
                boxCode: '',
                provGoodsId: '',
                goodsName: '',
                batchCode: '',
                uniqueCode: '',
                temprature: '',
                humidity: '',
                measureDate: '',
                sourceData: 0,
                version: 0
            },
            distrUniqueList: [],
            distrHumitureList: [],
            filters: {
                billId: ''
            },
            pcPage: {
                total: 0,
                page: 1,
                pageSize: 15,
            },
            orgProps: {
                value: "code",
                label: "label"
            },
            orgInfo: [],
            loading: false,
            purconfirmBill: {},              //请购确认单列表
            purconfirmVisible: false,
            tempPurconfirm: null,
            ProvsInfo: [],                  //请购机构列表
            filterProvs: [],               //过滤后的供应商列表
            uniqueKinds:[]
        }
    },
    components: { barcodeMathc },
    props: ["pcBillId", "distrBillId"],
    watch: {
        pcBillId(curVal, oldVal) {
            if (curVal) {
                this.opModule = "ADD";
                this.getPurConfirmInfo(curVal);
            }
        },
        distrBillId(cval, oval) {
            if (cval) {
                this.opModule = "UPDATE";
                this.getDistrBill(cval);
            }
        }
    },   
    methods: {
        getPurConfirmInfo(billId) {//获取确认单信息 
            if (!billId) {
                return;
            }
            this.pageLoading = true;
            this.axios.get('/spdHDIService/orderMgr/purconfirm/getByBillid/' + billId).then(res => {
                this.pageLoading = false;
                if (res && res.data.code == "0") {
                    this.convertPurcToDistr(res.data.data);
                }
            }, err => { this.pageLoading = false; });;
        },
        copyDetail(row) {
            // if (row.sendQty <= 1) {
            //     this.$message.error('发货数量不足,无法复制!');
            //     return;
            // }
            var crow = Object.assign({}, row);
            crow.sendQty = 0;
            crow.hosQty = 0;
            row.sendQty = row.sendQty - 1;
            row.hosQty = row.sendQty / row.sendScale;
            this.distrBill.distrBillList.splice(row.rowNumber, 0, crow); // 
            this.distrBill.distrBillList.forEach((item, i) => item.rowNumber = i + 1);
        },
        delRow(pd) {
            var index = this.distrBill.distrBillList.findIndex(item => { return item.rowNumber == pd.rowNumber });
            this.distrBill.distrBillList.splice(index, 1);
            this.distrBill.distrBillList.forEach((item, i) => item.rowNumber = i + 1);
        },
        convertPurcToDistr(purc) {
            if (!purc) {
                return;
            }
            this.distrBill.id = '';
            this.distrBill.billId = '';
            this.distrBill.spdCode = '';
            this.distrBill.purType =  purc.purType;
            this.distrBill.purchaseCompanyId = purc.purchaseCompanyId;
            this.distrBill.purchaseCompanyCode = purc.purchaseCompanyCode;
            this.distrBill.purchaseCompanyName = purc.purchaseCompanyName;
            this.distrBill.purchaseCompanyErpCode = purc.purchaseCompanyErpCode;
            this.distrBill.applyOrgId = purc.applyOrgId;
            this.distrBill.applyOrgCode = purc.applyOrgCode;
            this.distrBill.applyOrgName = purc.applyOrgName;
            this.distrBill.applyOrgScmCode = purc.applyOrgScmCode;
            this.distrBill.applyOrgErpCode = purc.applyOrgErpCode;
            this.distrBill.recieveOrgId = purc.recieveOrgId;
            this.distrBill.recieveOrgCode = purc.recieveOrgCode;
            this.distrBill.recieveOrgName = purc.recieveOrgName;
            this.distrBill.recieveOrgScmCode = purc.recieveOrgScmCode;
            this.distrBill.recieveOrgErpCode = purc.recieveOrgErpCode;
            this.distrBill.provId = purc.provId;
            this.distrBill.provCode = purc.provCode;
            this.distrBill.provName = purc.provName;
            this.distrBill.provErpCode = purc.provErpCode;
            this.distrBill.subProvId = purc.subProvId;
            this.distrBill.subProvName = purc.subProvName;
            this.distrBill.remark = '';
            this.distrBill.sumRow = purc.lstDetail.length;
            this.distrBill.status = 10;
            this.distrBill.isConverted = 0;
            this.distrBill.version = 0;


            this.distrBill.fillterName = this.user.ename;
            this.distrBill.fillter = this.user.userId;
            this.distrBill.provId = this.user.corpId;
            this.distrBill.provCode = this.user.corpCode;
            this.distrBill.provName = this.user.corpName;
            this.distrBill.fillDate = moment(new Date()).format("YYYY-MM-DD");

            this.distrBill.distrBillList = [];
            if (purc.lstDetail.length > 0) {
                purc.lstDetail.forEach(item => {
                    let row = Object.assign({}, this.constDistrDetail);
                    row.purconfirmBillId = item.billId;
                    row.purconfirmRowNumber = item.rowNumber;
                    row.purBillId = item.purBillId;
                    row.purRowNumber = item.purBillRownum;
                    row.purBillDate = item.purBillDate;
                    row.applyBillId = item.applyBillId;
                    //row.saleBillId = item.saleBillId;
                    row.provGoodsId = item.provGoodsId;
                    row.goodsGg = item.goodsGg;
                    row.goodsMrfsId = item.goodsMrfsId;
                    row.hosPrice = item.hosPrice | 0;
                    row.made = item.made;
                    row.hosGoodsId = item.hosGoodsId;
                    row.hosGoodsCode = item.hosGoodsCode;
                    row.hosGoodsName = item.hosGoodsName;
                    row.provGoodsErpCode = item.provGoodsErpCode;
                    row.provGoodsErpName = item.provGoodsErpName;
                    row.hitCode = item.hitCode;
                    row.hosUnit = item.hosUnit;
                    row.hosQty = item.unitQty;
                    row.canSendQty = item.qty - item.sendQty;
                    row.sendQty = 0;//item.qty - item.sendQty;//item.qty;
                    row.sendScale = item.hosScale;
                    row.sendUnit = item.unit;
                    row.uniqueKind = item.uniqueKind;
                    row.sendPrice = 0;//item.provGoodsErpPrice | 0;
                    row.subSendPrice = 0;//item.provGoodsErpPrice | 0;
                    row.transportType = '外运' ;
                    row.purPrice = item.purPrice;
                    row.certificateCode = item.certificateCode;
                    row.sendPrice = item.salePrice;
                    row.subSendPrice = item.purPrice;
                    this.distrBill.distrBillList.push(row);
                    row.rowNumber = this.distrBill.distrBillList.length;
                });
                this.purconfirmVisible = false;
            }
        },
        qtyChange(val, row) {
            row.sendQty = val;
            row.hosQty = row.sendQty / row.sendScale;
            // row.purAmount = (row.hosPrice * row.sendQty).toFixed(2);
            // row.purTaxAmount = (row.purTaxPrice * row.sendQty).toFixed(2);
        },
        cancel() {
            //this.distrBill.distrBillList = [];
            this.barcode = "";
            this.distrBill = {};
            this.$emit('cancel');
        },
        checkSubmit(distrBill){
            const h = this.$createElement;            
            var errMsg=[];
            distrBill.distrBillList.forEach(item=>{
                if(Number(item.sendPrice)<=0){
                    errMsg.push(`<font color='red'>${item.provGoodsErpName}</font>的配送价必须大于零`);
                }
                if(Number(item.sendQty)<=0){
                    errMsg.push(`<font color='red'>${item.provGoodsErpName}</font>的配送数量必须大于零`);
                }
                if(Number.isNaN(Number(item.sendTemprature)) || Number.isNaN(Number(item.sendHumidity))){
                    errMsg.push(`<font color='red'>${item.provGoodsErpName}</font>的温湿度必须为数字`);
                }
            });
            if(errMsg.length>0){
                this.$msgbox({type: "error",title: '验证出错',message: h(Child, { props: { errMsg: errMsg } })});
                return false;
            }
            return errMsg.length ==0;
        },
        
        submitDistrBill(status) {
            this.distrBill.status = status;
            this.distrBill.sumRow = this.distrBill.distrBillList.length;
			delete this.distrBill.subProvName;
            var params = this.distrBill;
            if(!this.checkSubmit(params)){
                return;
            }
			/*
            params.distrBillList.forEach(i=>{
                i.subSendPrice = i.sendPrice
            })
			*/
            this.pageLoading = true;            
            var serviceUrl = this.opModule == "ADD" ? '/spdHDIService/orderMgr/distrBill/add' : '/spdHDIService/orderMgr/distrBill/update';
            this.axios.post(serviceUrl, params).then(res => {
                this.pageLoading = false;
                if (res.data.code == "0") {
                    var billId = res.data.data.billId;
                    this.$msgbox({
                        title: "提示",
                        message: "操作成功",
                        type: "success",
                        callback: action => {
                            this.distrBill = {};
                            this.barcode = "";
                            this.$emit('creatDistrBill', billId);
                            //this.$router.push({ path: 'distrBillList', query: { billId: billId } });
                        }
                    });
                } else if (res.data.code == "-122") {
                    var msg = res.data.validateErrors.fieldErrors[0].message;
                    this.$msgbox({ title: "验证失败", message: msg, type: "error" });
                } else {
                    this.$msgbox({ title: "操作失败", message: msg, type: "error" });
                }
            }, err => {
                this.pageLoading = false;
                this.$msgbox({
                    title: "提示",
                    message: "操作失败",
                    type: "error"
                });
            });
        },
        getDistrBill(billId) {
            this.axios.get('/spdHDIService/orderMgr/distrBill/distrBillByBillId/' + billId).then(res => {
                this.distrBill = res.data.data;
            }, err => { });
        },
        editBarcode(row) {
            this.barcodeV = true;
            this.distrDetail = Object.assign({}, row);
            this.distrUniqueList = Object.assign([], this.distrDetail.distrBillUniqueList);
            this.distrUnique = {
                id: '',
                pid: '',
                distrRowNumber: row.rowNumber,
                provGoodsId: row.provGoodsId,
                goodsName: row.provGoodsErpName,
                batchCode: '',
                uniqueCode: '',
                sourceData: 0,
                version: 0
            }
        },
        addBarCode() {
            var barCode = Object.assign({}, this.distrUnique);
            this.distrUniqueList.push(barCode);
        },
        delBarCode(barcode) {
            var index = this.distrUniqueList.findIndex(item => item.batchCode == barcode.batchCode && item.uniqueCode == barcode.uniqueCode)
            this.distrUniqueList.splice(index, 1);
        },
        setBarCode() {
            var barCode = Object.assign({}, this.distrUnique);
            var row = this.distrBill.distrBillList.find(item => item.rowNumber == barCode.distrRowNumber);
            row.distrBillUniqueList = this.distrUniqueList;
            this.barcodeV = false;
        },
        editHumiture(row) {
            this.humitureV = true;
            this.distrDetail = Object.assign({}, row);
            this.distrHumitureList = Object.assign([], this.distrDetail.distrBillHumitureList);
            this.distrHumiture = {
                id: '',
                pid: '',
                distrRowNumber: row.rowNumber,
                boxCode: '',
                provGoodsId: row.provGoodsId,
                goodsName: row.provGoodsErpName,
                batchCode: '',
                uniqueCode: '',
                temprature: '',
                humidity: '',
                measureDate: '',
                sourceData: 0,
                version: 0
            }
        },
        addHumiture() {
            var humiture = Object.assign({}, this.distrHumiture);
            this.distrHumitureList.push(humiture);
        },
        delHumiture(humiture) {
            // var index = this.distrUniqueList.findIndex(item=>item.batchCode==barcode.batchCode && item.uniqueCode==barcode.uniqueCode)
            // this.distrUniqueList.splice(index,1);
        },
        setHumiture() {
            var humiture = Object.assign({}, this.distrHumiture);
            var row = this.distrBill.distrBillList.find(item => item.rowNumber == humiture.distrRowNumber);
            row.distrBillHumitureList = this.distrHumitureList;
            this.humitureV = false;
        },
        distrDetailClose() {
            this.distrDetail = {};
            this.distrUnique = {};
            this.distrUniqueList = [];
        },
        editOthers(row) {
            this.otherV = true;
            this.distrDetail = row;//Object.assign({}, row);
        },
        barcodeTypeChange(val) {
            this.$nextTick(function () {
                if (val) {
                    this.$refs.mbarcode.$el.children[0].focus();
                    this.$refs.mbarcode.$el.children[0].select();
                } else {
                    this.$refs.mainBarcode.$el.children[0].focus();
                    this.$refs.mainBarcode.$el.children[0].select();

                }
            });
        },
        barcodeNext() {
            this.fBarcode = "";
            this.$refs.fbarcode.$el.children[0].focus();
            this.$refs.fbarcode.$el.children[0].select();
        },
        barcodePre() {
            barcodeUtil.parseCombinedBarcode(this.mainBarcode, this.fBarcode, this.distrBill.purchaseCompanyId, this.distrBill.provId).then(res => {
                var goodsLst = res.data.data;
                this.setSendGoods(goodsLst);
                this.$refs.mainBarcode.$el.children[0].focus();
                this.$refs.mainBarcode.$el.children[0].select();
                this.mainBarcode = "";
                this.fBarcode = "";
            });
        },
        addBybarcode() {
            barcodeUtil.parseCombinedBarcode(this.barcode, null, this.distrBill.purchaseCompanyId, this.distrBill.provId).then(res => {
                var goodsLst = res.data.data;
                this.setSendGoods(goodsLst);
                this.$refs.mbarcode.$el.children[0].focus();
                this.$refs.mbarcode.$el.children[0].select();
            });
        },
        findGoods(barCodeInfo) {
            var res = null;
            var flag = false;//是否找到匹配的商品
            for (var i = 0; i < this.distrBill.distrBillList.length; i++) {
                var item = this.distrBill.distrBillList[i];
                if (barCodeInfo.goodsId == item.hosGoodsId) {
                    flag = true;
                    if (item.sendQty == 0) {
                        res = item;
                        break;
                    } else if (item.sendQty > 0 && item.sendQty < item.canSendQty) {
                        // if (item.batchNum && item.expdtEndDate && item.batchNum == barCodeInfo.batchNo
                        //     && item.expdtEndDate.getTime() == new Date(barCodeInfo.expiredDate).getTime()) {
                        //     res = item;
                        //     break;
                        // }
                         if (item.batchNum) {
                            if(item.batchNum == barCodeInfo.batchNo){
                                res = item;
                                break;
                            }
                        }else{
                             res = item;
                                break;
                        }
                    }

                }
            }
            if (!flag) {
                this.barcodeMatch.hosId = //this.distrBill.purchaseCompanyId;
                this.barcodeMatch.provId = this.distrBill.provId
                this.barcodeMatch.mfrsCode = barCodeInfo.goodsNo;
                this.barcodeMatchV = true;
            }
            return res;
        },
        setSendGoods(goodsLst) {
            if (!Array.isArray(goodsLst) || goodsLst.length == 0) {
                this.$message.error('此条码无法解析!');
                return;
            }
            for (var i = 0; i < goodsLst.length; i++) {
                if (goodsLst[i].mainFlag == "1") {//主码
                    var curRow = this.findGoods(goodsLst[i]);
                    if (curRow && curRow.sendQty < curRow.canSendQty) {
                        this.distrDetail = curRow;
                        this.distrDetail.sendQty++;
                        this.distrDetail.expdtEndDate = new Date(goodsLst[i].expiredDate);
                        this.distrDetail.batchCode = goodsLst[i].batchNo;
                        break;
                    }
                } else {//副码
                    this.distrDetail.sendQty++;
                }
            }
        },
        barcodeMatchSave() {
            this.barcodeMatchV = false;
            if (this.barcodeType) {
                this.addBybarcode();
            } else {
                this.barcodePre();
            }
        },
        getUniqueKinds(){
            var _this = this;
            this.axios.post('/platformService/sys/dict/getDictValueVos' , {dictId:'WYMGLCL'}).then(res => {
                _this.uniqueKinds = res.data.data.data;
            }, err => { });
        },
         f_uniqueKind: function (r,c,value) {           
            var unq =  this.uniqueKinds.find(item=>item.val == value);
            if(unq){return unq.ename;}else{return '不支持的条码类别'}
        },
        init() {
            this.getUniqueKinds();
            if (this.pcBillId) {
                this.getPurConfirmInfo(this.pcBillId);
            }
            if (this.distrBillId) {
                this.opModule = "UPDATE";
                this.getDistrBill(this.distrBillId);
            }
        },
        checkPrice(d1, row) {
            var sendPrice = row.sendPrice;
            var subSendPrice = row.subSendPrice;
            //var a = /^[0-9]*(\.[0-9]{1,4})?$/;
            var a = /^\d+(\.\d+)?$/; // 非负浮点数
            //var flag = a.test(price);
            if (!a.test(sendPrice)) {
                row.sendPrice = 0;
            }
            if(!a.test(subSendPrice)){
                row.subSendPrice = 0;
            }
          //  row.subSendPrice = row.sendPrice;
        }
    },
    mounted() {
        this.init();
    }
};

</script>

<style>
.table_sub_content{
    font-size: 12px;
    height: calc(94% - 175px);
    overflow-y: auto;
}

.el-dialog--small {
    width: 60%;
}

.el-card__body {
    padding: 0px;
}

.orderInput .el-form-item input {
    /*BORDER-TOP-STYLE: none;
    BORDER-RIGHT-STYLE: none;
    BORDER-LEFT-STYLE: none;
    BORDER-BOTTOM-STYLE: solid;*/
    width: 200px;
}

.provListInfo {
    width: 97%;
    height: 300px;
    max-height: 400px;
    padding: 3px;
    background-color: #eef1f6;
    border: 1px solid #bfcbd9;
}

.barlst {
    list-style: none;
    margin: 0px;
    padding: 0;
}

.barlst li {
    cursor: pointer;
    float: left;
}

.icoBtn [class^=fa] {
    vertical-align: baseline;
    cursor: pointer;
    margin-right: 5px;
}
#addDistrTab .col-input-num{
  width:120px;
}
</style>