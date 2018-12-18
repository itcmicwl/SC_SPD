<template>
    <div id="provGoodsHoslist" class="fullheight">
        <el-row  class="fullheight">
            <el-col :span="5" class="fullheight " >
                <div style="margin-bottom: 10px">
                    <el-input v-model="hosFilters.hosName" placeholder="请输入医院名称"   icon="search" @keyup.enter.native="handleHosSearch" :on-icon-click="handleHosSearch"   ></el-input>
                </div>
                <el-table :show-header="false" ref="hosTable" border class="tableheight" :data="hosList" @current-change="selectHosClick" style="width: 100%;margin-top: 5px;" highlight-current-row >
                    <el-table-column prop="hosName" label="医院" show-overflow-tooltip>
                    </el-table-column>
                </el-table>
                <el-pagination @size-change="handleHosSizeChange" @current-change="handleHosCurrentChange" :current-page="hosPage" :page-sizes="[20, 100, 200, 400]" :page-size="hosPageSize" layout="sizes,prev,next" :total="hosTotal"  small>
			          </el-pagination>
            </el-col>
            <el-col :span="19" class="fullheight" style="padding-left: 5px;">

              <div style="margin-bottom:-9px;">
                <el-form :inline="true" label-width="80px">
                  <el-form-item label="">
                    <el-input v-model="filters.ename" placeholder="请输入姓名" ></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" v-on:click="getUser(1)" >查询</el-button>
                    <el-button type="success" v-on:click="selectSubit()" >提交</el-button>
                  </el-form-item>
                </el-form>
              </div>
              <el-table :data="users" ref="userHosTable" highlight-current-row v-loading="loading" style="width: 100%;" 
              @selection-change="selectChange" border fit  class="userHos-tableheight">
							<el-table-column type="selection" width="55" align="center">
              </el-table-column>
              <el-table-column prop="ename" label="姓名">
							</el-table-column>
              <el-table-column prop="userId" label="姓名">
							</el-table-column>
              <!-- <el-table-column label="是否建立关系" align="center" width="160">
								<template slot-scope="scope">
									<el-switch v-model="isRelated"  width=60 active-color="#13ce66" inactive-color="#ff4949" active-text="是" inactive-text="否" active-value="1" inactive-value="0"  @change="userChange(scope.row)">
									</el-switch>
								</template>
							</el-table-column> -->
              <el-table-column prop="orgName" label="所属机构">
							</el-table-column>
							<el-table-column prop="tel" label="电话">
							</el-table-column>
							<el-table-column prop="job" label="职务">
							</el-table-column>
						</el-table>
						<el-col :span="24" class="toolbar">
							<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="userPage" :page-sizes="[20, 100, 200, 400]" :page-size="userPageSize" layout="total, sizes, prev, pager, next" :total="userTotal" style="float:right;" small>
							</el-pagination>
						</el-col>
            </el-col>
        </el-row>
    </div>
</template>

<script>

export default {
  data () {
    return {

      isRelated:0,
      userHosLists:[],

      users:[],
      filters: {
					ename: '',
					orgCode: '',
					orgId: '',
					orgName:''
			},

      hosFilters: {
        hosName: ''
      },

      hosTotal: 0,
      hosPage: 1,
      hosPageSize: 10,

      userPage:1,
      userTotal:0,
      userPageSize:10,

      hosList: [],
      loading: false,

      currentHos: {}, // 当前选择的医院
      currentHosId: '', // 当前选择的医院ID

      multipleSelection: [],
      userHos:[],

    }
  },

  methods: {

    // 获取(当前用户对应供应商的)所有合作医院信息
    getHosListsByUnit: function (pIndex) {
      this.loading = true
      this.hosPage = pIndex
      var param = {
        orderBy: '', 
        queryObject: {hosName: this.hosFilters.hosName, status: 2}, 
        page: this.hosPage, 
        rows: this.hosPageSize 
      }
      this.axios.post('/spdHDIService/myInfo/provHosInfo/provHosInfoVoList', param).then(res => {
        this.loading = false
        if (res.data.code == '0') {
          this.hosList = res.data.data.data;
          this.hosTotal = res.data.data.total;
          //  this.page = res.data.data.pageNum;
          if (this.hosList.length>0) {
            this.$nextTick(function () {
              this.setCurrent(this.hosList[0]);
            });
          }
        } else {
          this.$message.error(res.data.msg);
        }
      }, _ => {
        this.loading = false
      })
    },

    setCurrent(row) {
        this.currentHosId = row.hosId;
        this.currentHos = row;
        this.$refs.hosTable.setCurrentRow(row);
    },

    selectSubit:function(){
        this.loading = true;
        var tempObj = {hosId:this.currentHosId,userId:''};
        if(this.multipleSelection.length<1){
          this.userHos.push(tempObj);
        }else{
          this.multipleSelection.forEach(item=>{
            // tempObj.userId = item.userId;
            this.userHos.push({hosId:this.currentHosId,userId:item.userId});
          });
        }
        this.multipleSelection = [];
				this.axios.post("/spdHDIService/myInfo/userHos/addUserHos", this.userHos).then(res => {
					this.loading = false;
					if (res.data.code == "0") {
						this.loading = false;
            this.$message({ message: '操作成功！', type: 'success' });
            // this.getUserHosLists(1);
					} else {
						this.$message.error('操作失败！');
						user.status = user.status == "1" ? "0" : "1";
					}
				}, err => {
					this.loading = false;
				});
    },

    selectChange:function(d1){
        this.multipleSelection = d1;
        this.userHos = [];
        
    },

    //获取用户列表
		getUser:function (pIndex) {
				this.userPage = pIndex;
        var param = {
          orderBy: "", 
          queryObject: {
            ename: this.filters.ename
          }, 
          page: this.userPage, 
          rows: this.userPageSize 
        };
				this.loading = true;
				this.axios.post(`/platformService/sys/user/userlistByUnit`, param).then(res => {
					this.loading = false;
					this.users = res.data.data.data;
					this.userTotal = res.data.data.total;
				}, err => {
					this.loading = false;
				});
    },

    selectHosClick: function (row) {
      if (this.currentHos.id === row.hosId) {
        return;
      }
      // alert(2);
      this.currentHos = row;
      this.currentHosId = row.hosId;
      this.getUserHosLists(row.hosId)
    },

    //获取用户列表
		getUserHosLists: function (hosId) {
      // alert(3);
        let _this = this;
        var param = {queryObject: {hosId: hosId}};
				this.axios.post(`/spdHDIService/myInfo/userHos/getUserHosLists`, param).then(res => {
          _this.userHosLists = res.data.data;
          var result = res.data.data;
          var flag;
          // alert(4);
          var userHosObj;
          _this.users.forEach(f => {
              userHosObj = res.data.data.find(item => item.userId == f.userId);
              if(userHosObj){
                  flag = true;
              }else{
                  flag = false;
              }
              _this.$refs.userHosTable.toggleRowSelection(f,flag);
          });
				}, err => {
					this.loading = false;
				});
    },

    
    handleHosSearch () {
      this.getHosListsByUnit(this.page);
    },

    handleHosCurrentChange (val) {
      this.hosPage = val;
      this.getHosListsByUnit(this.hosPage);
    },

    handleHosSizeChange (val) {
      this.hosPageSize = val;
      this.getHosListsByUnit(this.hosPageSize);
    },

    handleCurrentChange(val) {
				this.userPage = val;
				this.getUser(this.userPage);
			},
			handleSizeChange(val) {
					this.userPageSize = val;
					this.getUser(this.userPage);
			}

  },



  mounted () {
    this.getUser(1);
    this.getHosListsByUnit(1);
  }
}
</script>

<style scoped>

    .fullheight {
        height: calc(100% - 0px);
    }

    .tableheight {
        height: calc(100% - 90px);
    }

    .userHos-tableheight{
	  height:  calc(100% - 90px);
}
</style>
