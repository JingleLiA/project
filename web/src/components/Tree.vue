<template>
  <div>
    <el-row>
      <el-input v-model="treeCode" placeholder="请输入树木编码" style="width:250px;"></el-input>
      <el-button type="primary" @click="search">搜索</el-button>
      <el-button type="primary" :size='tableData.size' @click="exportExcel()">导出</el-button>

      <!-- <el-button v-show="tableData.length>0">下载csv统计文件<i class="el-icon-download el-icon--right"></i></el-button>          -->
    </el-row>
    <el-row class="info">
      <span>树种：{{treeInfo.treeType}}</span>
      <span>测量次数：{{treeInfo.measureTimes}}</span>
      <span>生长速度：{{treeInfo.grow}} cm/年</span>
    </el-row>
    <div id="chart"></div>
    <z-table :tableData="tableData"></z-table>
    <h-table :tableData="tableData" hidden></h-table>


  </div>
</template>

<script>
  import conf from '../conf.js';
  import Table from './self/Table.vue';
  import TableHide from './self/TableHide.vue';
  import FileSaver from 'file-saver'
  import XLSX from 'xlsx'

  export default {
    components: {
      'z-table': Table,
      'h-table': TableHide,
    },
    data() {
      return {
        myChart: null,
        treeCode: '',
        tableData: [],

        treeInfo: {
          measureTimes: 0,
          treeType: "杨树",
          grow: 0
        }
        // }]
      }
    },
    mounted() {
      let self = this;
      let treeCode = this.$route.params.treeCode || '';
      self.treeCode = treeCode;
      self.initChart();
      if (treeCode && treeCode != "") {
        self.fetchData(treeCode);
      }
    },
    methods: {
      exportExcel() {
        /* generate workbook object from table */
        console.log(document.querySelector('#out-table'));
        var wb = XLSX.utils.table_to_book(document.querySelector('#out-table'));
        /* get binary string as output */
        var wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: true, type: 'array'});
        try {
          FileSaver.saveAs(new Blob([wbout], {type: 'application/octet-stream'}), '测量记录.xlsx')
        } catch (e) {
          if (typeof console !== 'undefined') console.log(e, wbout)
        }
        return wbout
      },
      showPic(dataIndex, diameter, date) {
        console.log('showPic');
      },
      search() {
        this.fetchData(this.treeCode);
      },
      fetchData(treeCode) {
        let self = this;
        fetch(`${conf.ROOT}/api/measurements?treeCode=${treeCode}`)
          .then(res => res.json())
          .then(res => {
            self.tableData = res.data;
            let dates = [];
            let diameters = [];
            res.data.forEach(element => {
              dates.push(element.time.split(' ')[0]);
              diameters.push(element.diameterCalculate);
            });
            dates = dates.reverse();
            diameters = diameters.reverse();
            for (let i = 1; i < dates.length; i++) {
              if (dates[i - 1] == dates[i]) {
                dates.splice(i - 1, 1);
                diameters.splice(i - 1, 1);
                i--;
              }
            }
            self.treeInfo.measureTimes = dates.length;
            self.tableData = res.data;
            self.updateChart(dates, diameters);
          });
        fetch(`${conf.ROOT}/api/trees/${treeCode}`)
          .then(res => res.json())
          .then(res => {
            console.log(res.data);
            self.treeInfo.grow = res.data.grow
          });
      },
      updateChart(dates, diameters) {
        this.myChart.setOption({
          grid: {
            left: '0%',
            right: '4%',
            containLabel: true
          },
          tooltip: {
            trigger: 'axis'
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            axisLine: {onZero: false},
            data: dates
          },
          yAxis: {},
          series: [{
            name: '直径',
            type: 'line',
            data: diameters,
            label: {
              normal: {
                show: true,
                position: 'top'
              }
            },
          }]
        });
      },
      initChart() {
        let self = this;
        let myChart = this.$echarts.init(document.getElementById('chart'), 'macarons')
        myChart.on('click', function (param) {
          self.showPic(param.dataIndex, param.data, param.name);
        });
        self.myChart = myChart;
      }
    }
  }
</script>

<style>
  .info {
    margin-top: 30px;
    margin-bottom: -40px;
  }

  .info span {
    margin-right: 20px;
  }

  #chart {
    margin-top: 20px;
    width: 100%;
    height: 400px;
  }
</style>
