<template>
  <el-row>
    <el-table
      ref="multipleTable"
      :data="tableData"
      style="width: 100%" tooltip-effect="dark">
      <el-table-column
        prop="time"
        label="测量日期"
        width="180">
      </el-table-column>
      <el-table-column
        prop="treeCode"
        label="树木编码"
        width="180">
        <template slot-scope="scope">
          <router-link :to="{name:'tree', params:{ treeCode: scope.row.treeCode }}">
            <el-button type="text" size="small">{{scope.row.treeCode}}</el-button>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column
        prop="diameterCalculate"
        label="测量结果(cm)">
        <template slot-scope="scope">
          <div>{{scope.row.diameterCorrect ? scope.row.diameterCorrect : scope.row.diameterCalculate}}</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="type"
        label="测量方式">
      </el-table-column>
      <el-table-column
        prop="userCode"
        label="测量人员编号">
      </el-table-column>
      <el-table-column
        label="查看详情">
        <template slot-scope="scope">
          <el-button @click="showPic(scope.row)" type="text" size="small">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="照片查看"
      :visible.sync="dialogVisible"
      width="700px">
      <div class="dialog-content">
        <div class="image-wrapper">
          <img :src="conf.ROOT_DEV+detail.pic+'.png'"/>
          <div class="line line1" v-show="!editMode" :style="line1_style"></div>
          <div class="line line2" v-show="!editMode" :style="line2_style"></div>
          <div class="edit-wrapper" v-show="editMode" @click="editClick">
            <div class="edit-dot" v-for="(dot,index) in editDots" :key="index"
                 :style="{left:dot.x+'px',top:dot.y+'px'}"></div>
          </div>
        </div>
        <div class="info-wrapper" v-show="!editMode">
          <p>测量日期：{{detail.time}}</p>
          <p>树木编码：{{detail.treeCode}}</p>
          <p>测量结果(cm)：{{diameter}}</p>
          <p>测量方式：{{detail.type}}</p>
          <el-button plain @click="editMode = true">人工标注</el-button>
        </div>
        <div class="info-wrapper btn-wrapper" v-show="editMode">
          <div class="btn-row">
            <el-button plain @click="editDots=[]">撤销所有</el-button>
          </div>
          <div class="btn-row">
            <el-button plain @click="saveEdit">保存更改</el-button>
            <el-button plain @click="editMode = false;this.editDots = [];">放弃退出</el-button>
          </div>
        </div>
      </div>
      <!-- <img style="width:100%;" :src="picPath"/> -->
      <!-- <span slot="footer" class="dialog-footer"> -->
      <!-- <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false">确 定</el-button> -->
      <!-- </span> -->
    </el-dialog>
  </el-row>
</template>

<script>
  import conf from '../../conf.js'

  export default {
    data() {
      return {
        conf: conf,
        editMode: false,
        editDots: [],
        dialogVisible: false,
        picPath: '',
        detail: {},
        multipleSelection: [],
        total: 0,
        pagesize: 10,
        currentPage: 1
      }
    },
    computed: {
      detail_time: function () {
        return this.detail.time ? this.detail.time.slice(0, 10) : '';
      },
      detail_way: function () {
        // return this.detail.way?this.detail.way:'自动';
        return '自动';
      },
      diameter: function () {
        if (this.detail.diameterCorrect) {
          return this.detail.diameterCorrect
        } else {
          return this.detail.diameterCalculate
        }
      },
      line1_style: function () {
        let style = {
          left: '0',
          top: '0'
        }
        if (this.detail && this.detail.dotsCalculate && this.detail.dotsCalculate.leftx) {
          let dots = this.detail.dotsCorrect.id ? this.detail.dotsCorrect : this.detail.dotsCalculate;
          console.log(dots);
          style = {
            left: ((dots.leftx * 1.0 / 3000) * 400 - 2).toString() + 'px',
            top: ((dots.lefty * 1.0 / 4000) * 533 - 50).toString() + 'px',
            transform: `rotate(${dots.anglel}deg)`
          }
        }
        if (style.top.charAt(0) == '-') {
          style.top = '213.2px'
        }
        return style;
      },
      line2_style: function () {
        let style = {
          left: '0',
          top: '0'
        }
        if (this.detail && this.detail.dotsCalculate && this.detail.dotsCalculate.rightx) {
          let dots = this.detail.dotsCorrect.id ? this.detail.dotsCorrect : this.detail.dotsCalculate;
          style = {
            left: ((dots.rightx * 1.0 / 3000) * 400).toString() + 'px',
            top: ((dots.righty * 1.0 / 4000) * 533 - 50).toString() + 'px',
            transform: `rotate(${dots.angler}deg)`
          }
        }
        if (style.top.charAt(0) == '-') {
          style.top = '213.2px'
        }
        return style;
      }
    },
    props: {
      tableData: Array
    },
    methods: {
      current_change: function (currentPage) {
        this.currentPage = currentPage;
      },
      showPic(data) {
        this.picPath = "";
        this.picPath = conf.ROOT + data.path;
        this.detail = data;
        this.dialogVisible = true;
        console.log(data)
      },
      getDotsStyle(index) {
        let style = {
          left: this.editDots[index].left + 'px',
          top: this.editDots[index].left + 'px'
        }
      },
      editClick(e) {
        if (this.editDots.length < 4) {
          this.editDots.push({
            x: e.offsetX,
            y: e.offsetY
          })
        } else {
          this.$message({
            message: '请用四个点标注树干位置，标注错误请撤销',
            type: 'warning'
          });
        }
      },
      saveEdit() {
        if (this.editDots.length == 4) {
          let resultDots = this.editDots.map((e) => {
            return {
              x: Math.round(e.x * 3000.0 / 400),
              y: Math.round(e.y * 4000.0 / 533.33)
            }
          })
          resultDots.sort(function (pre, post) {
            return pre.x - post.x;
          })
          if (resultDots[0].y > resultDots[1].y) {
            let dot = resultDots[0];
            resultDots[0] = resultDots[1];
            resultDots[1] = dot;
          }
          if (resultDots[2].y > resultDots[3].y) {
            let dot = resultDots[2];
            resultDots[2] = resultDots[3];
            resultDots[3] = dot;
          }
          let self = this;
          let dots = [];
          dots.push(resultDots[0].x);
          dots.push(resultDots[0].y);
          dots.push(resultDots[1].x);
          dots.push(resultDots[1].y);
          dots.push(resultDots[2].x);
          dots.push(resultDots[2].y);
          dots.push(resultDots[3].x);
          dots.push(resultDots[3].y);
          dots.push(this.detail.dotsCalculate.upx)
          dots.push(this.detail.dotsCalculate.upy)
          dots.push(this.detail.dotsCalculate.downx)
          dots.push(this.detail.dotsCalculate.downy)
          let data = {
            "id": self.detail.id,
            "pic": self.detail.pic,
            "left_up_x": resultDots[0].x,
            "left_up_y": resultDots[0].y,
            "left_down_x": resultDots[1].x,
            "left_down_y": resultDots[1].y,
            "right_up_x": resultDots[2].x,
            "right_up_y": resultDots[2].y,
            "right_down_x": resultDots[3].x,
            "right_down_y": resultDots[3].y,
            dots
          };
          console.log(data);
          let queryString = Object.keys(data).map((key) => {
            return key + '=' + data[key]
          }).join("&")
          fetch(conf.ROOT + '/api/measurements?' + queryString, {
            method: "PATCH",
          })
            .then(res => res.json())
            .then(res => {
              if (res.data.success) {
                self.detail.diameterCorrect = res.data.diameter_correct;
                self.editMode = false;
                let dotsCorrect = {}
                Object.assign(dotsCorrect, self.detail.dotsCalculate);
                console.log(dotsCorrect);
                Object.assign(dotsCorrect, res.data.dots);
                console.log(dotsCorrect);
                self.detail.dotsCorrect = dotsCorrect;
                this.editDots = [];
                self.$message({
                  message: '标注成功',
                  type: 'success'
                });
              }
            })
        } else {
          this.$message({
            message: '请用四个点标注树干位置',
            type: 'warning'
          });
        }
      }
    }
  }
</script>

<style>
  .dialog-content {
    margin-top: -20px;
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    /* justify-content: space-between; */
  }

  .btn-wrapper {
    display: flex;
    flex-direction: column;
  }

  .btn-row {
    margin-bottom: 20px;
  }

  .edit-wrapper {
    position: absolute;
    top: 0;
    left: 0;
    width: 400px;
    height: 533.33px;
  }

  .edit-dot {
    position: absolute;
    width: 2px;
    height: 2px;
    background: white;
    box-shadow: 0 0 2px 2px blue;
    border-radius: 1px;
  }

  .info-wrapper {
    width: 250px;
    padding-left: 20px;
  }

  .info-wrapper p {
    margin-top: 0;
    width: 100%;
  }

  .image-wrapper {
    width: 400px;
    position: relative;
  }

  .image-wrapper img {
    width: 400px;
  }

  .line {
    position: absolute;
    left: 0;
    top: 0;
    width: 2px;
    height: 100px;
    /* background: rgba(143,243,249,1); */
    background: white;
    /* box-shadow: 0 0 1px 1px rgba(143,243,249,1); */
    box-shadow: 0 0 2px 1px red;
    border-radius: 1px;
    z-index: 99;
  }

  .line1 {
    top: 40%;
    left: 54.2%;
  }

  .line2 {
    top: 40%;
    left: 63%;
  }
</style>
