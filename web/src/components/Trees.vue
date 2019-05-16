<template>
  <div>
    <el-row>
      选择类别:
      <el-select v-model="value" filterable placeholder="请选择" @change="changed">
        <el-option
          v-for="item in categoryInfo"
          :key="item.key"
          :label="item.value"
          :value="item.key">
        </el-option>
      </el-select>
      <el-table
        :data="tableData"
        style="width: 100%">
        <el-table-column
          prop="treeCode"
          label="树木编码">
          <template slot-scope="scope">
            <router-link :to="{name:'tree', params:{ treeCode: scope.row.treeCode }}">
              <el-button type="text" size="small">{{scope.row.treeCode}}</el-button>
            </router-link>
          </template>
        </el-table-column>
        <el-table-column
          prop="treeType"
          label="树木种类">
        </el-table-column>
        <el-table-column
          prop="measureTimes"
          label="测量次数">
        </el-table-column>
        <el-table-column
          prop="grow"
          label="生长速度(cm/年)">
        </el-table-column>
      </el-table>
    </el-row>
  </div>
</template>

<script>
  import conf from '../conf.js';

  export default {
    data() {
      return {
        tableData: [{
          treeCode: 'tree1111',
          treeType: '未知',
          measureTimes: 10,
          grow: 12.1,
        }, {
          treeCode: 'tree1111',
          treeType: '未知',
          measureTimes: 10,
          grow: 12.1,
        }],
        categoryInfo: [
          {
            key: 1,
            value: '杨树22'
          }, {
            key: 2,
            value: '爆炸叔'
          }
        ],
        value:"",
      }
    },
    inject: ['reload'],
    mounted() {
      let self = this;
      fetch(`${conf.ROOT}/api/trees`)
        .then(res => res.json())
        .then(res => {
          self.tableData = res.data;
        });
      fetch(`${conf.ROOT}/api/categoryInfo`)
        .then(res => res.json())
        .then(res => {
          self.categoryInfo = res.data;
        });
    },
    methods: {
      changed() {
        let me = this;
        console.log(me.value);
        fetch(`${conf.ROOT}/api/trees/category/${me.value}`)
          .then(res => res.json())
          .then(res => {
            // console.log(res.data);
            self.tableData = res.data;
            var salesAmountList=[];
            res.data.forEach(function (value, index) {
              //省略 从res.data.dataFromServer中取值对item赋值 操作
              salesAmountList[index] = value;
            });
            this.tableData=salesAmountList
          });
      }
    }
  }
</script>

<style>

</style>
