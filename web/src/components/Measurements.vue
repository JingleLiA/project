<template>
  <div class="hello">
    <div class="search-box">
      <el-row>
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          align="right"
          unlink-panels
          value-format="yyyy-MM-dd"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
        <el-button type="primary" @click="search">搜索</el-button>
      </el-row>
      <z-table :tableData="tableData.slice((currentPage-1)*pagesize,currentPage*pagesize)">

      </z-table>
      <div style="text-align: center;margin-top: 30px;">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          @current-change="current_change">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
  import Table from './self/Table.vue';
  import conf from '../conf.js'

  export default {
    // name: 'HelloWorld',
    components: {
      'z-table': Table,
    },
    data() {
      return {
        picPath: '',
        msg: 'Welcome to Your Vue.js App',
        dateRange: '',
        detail: {},
        tableData: [{
          time: '2016-05-02',
          treeCode: 'tree111',
          diameterCalculate: 11.1,
          pic: '../assets/tree.jpg',
          thumbnail: require('../assets/thumbnail.png'),
          way: '自动',
          dots: {
            leftX: '1796',
            leftY: '1698',
            upX: '1646',
            upY: '177',
            rightX: '1888',
            rightY: '177',
            downX: '1789',
            downY: '2248',
          }
        }, {
          time: '2016-05-04',
          treeCode: 'tree1111',
          diameterCalculate: 12.2,
          pic: '../assets/tree.jpg',
          thumbnail: '../assets/thumbnail.png',
          way: '自动',
          dots: {
            leftX: '1796',
            leftY: '1698',
            upX: '1646',
            upY: '177',
            rightX: '1888',
            rightY: '177',
            downX: '1789',
            downY: '2248',
          }
        }, {
          time: '2016-05-01',
          treeCode: 'tree111',
          diameterCalculate: 12.5,
          pic: 'pic path',
          thumbnail: '/assets/thumbnail.png',
          way: '自动',
          dots: {
            leftX: '1796',
            leftY: '1698',
            upX: '1646',
            upY: '177',
            rightX: '1888',
            rightY: '177',
            downX: '1789',
            downY: '2248',
          }
        }, {
          time: '2016-05-03',
          treeCode: 'tree111',
          diameterCalculate: 13.1,
          pic: 'pic path',
          thumbnail: '/assets/thumbnail.png',
          way: '自动',
          dots: {
            leftX: '1796',
            leftY: '1698',
            upX: '1646',
            upY: '177',
            rightX: '1888',
            rightY: '177',
            downX: '1789',
            downY: '2248',
          }
        }], multipleSelection: [],
        total: 0,
        pagesize: 10,
        currentPage: 1
      }
    },
    mounted() {
      let self = this;
      document.getElementsByClassName('navs')[0].style.display = "block";
      fetch(conf.ROOT + '/api/measurements')
        .then(res => res.json())
        .then(res => {
          console.log(res.data);
          self.tableData = res.data;
          self.total = res.total;
        })
    },
    methods: {
      current_change: function (currentPage) {
        this.currentPage = currentPage;
      },
      search() {
        let dateRange = this.dateRange;
        let self = this;
        fetch(`${conf.ROOT}/api/measurements?beginDate=${dateRange[0]}&endDate=${dateRange[1]}`)
          .then(res => res.json())
          .then(res => {
            self.tableData = res.data;
            self.total = res.total;
          })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .search-box {
    /* padding:20px; */
  }

  .container {
    width: 100%;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap; /* 为了cell宽度的百分比变化时，折行的效果 */
  }

  .cell {
    width: 25%;
    height: 150px; /* 无所谓 */
    display: flex;
    flex-direction: row; /* 这两行是为了cell也可以再包含cell */
    box-sizing: border-box; /* 这一行是为了改变浏览器计算block大小的方式，不加的话，加上border宽度，一个cell的宽度会大于container的25% */
    border: 1px solid black;
  }

  @media (max-width: 720px) {
    .cell {
      width: 50%;
    }
  }

  @media (max-width: 360px) {
    .cell {
      width: 100%;
    }
  }
</style>
