<template>
    <el-input  placeholder="请输入内容" v-model="data.params[index-1].input" @keyup.enter.native="doSearch">
        <el-select style="width:110px;" v-model="data.params[index-1].value" slot="prepend" placeholder="请选择">
            <el-option v-for="item in data.options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
        </el-select>
        <el-button slot="append" icon="close" v-if="index != 1" @click="close"></el-button>
    </el-input>
</template>

<script>
export default {
  name: 'searchInput',
  data () {
    return {}
  },
  props: {
    data: Object,
    index: Number
  },
  methods: {
    close () {
      this.$emit('delInput', this.index)
    },
    doSearch () {
      this.$emit('doSearch')
    }
  }
}
</script>

<style scoped>
    .el-select {
        width: 110px;
    }
</style>
