<template>
  <div class="app-container">
    <!-- 项目选择下拉框 -->
    <el-row :gutter="20" class="mb8">
      <el-col :span="24">
        <el-select v-model="selectedProject" placeholder="请选择项目" style="width: 240px; margin-bottom: 16px;">
          <el-option
            v-for="item in projectList"
            :key="item.projectId"
            :label="item.projectName"
            :value="item.projectId"
          />
        </el-select>
      </el-col>
    </el-row>
    <!-- 当前值卡片 -->
    <el-row :gutter="20" class="mb8">
      <el-col :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>实时环境数据</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="refreshCurrentData">刷新</el-button>
          </div>
          <div class="realtime-cards-flex">
            <div v-for="(item, index) in currentData" :key="index" class="realtime-card-flex" :class="['card-bg-' + item.dataType, {'warning-card': item.isAlarm}]">
              <div class="card-icon">
                <svg v-if="item.dataTypeName==='PM2.5'" width="24" height="24" viewBox="0 0 24 24"><circle cx="12" cy="12" r="10" fill="#4f8cff"/></svg>
                <svg v-else-if="item.dataTypeName==='CO2'" width="24" height="24" viewBox="0 0 24 24"><rect x="4" y="4" width="16" height="16" rx="8" fill="#43d19e"/></svg>
                <svg v-else-if="item.dataTypeName==='温度'" width="24" height="24" viewBox="0 0 24 24"><rect x="9" y="2" width="6" height="20" rx="3" fill="#ff9800"/></svg>
                <svg v-else-if="item.dataTypeName==='湿度'" width="24" height="24" viewBox="0 0 24 24"><ellipse cx="12" cy="12" rx="10" ry="6" fill="#00bcd4"/></svg>
                <svg v-else-if="item.dataTypeName==='光照'" width="24" height="24" viewBox="0 0 24 24"><circle cx="12" cy="12" r="8" fill="#ffd600"/></svg>
                <svg v-else-if="item.dataTypeName==='烟雾'" width="24" height="24" viewBox="0 0 24 24"><ellipse cx="12" cy="16" rx="8" ry="4" fill="#bdbdbd"/></svg>
                <svg v-else-if="item.dataTypeName==='含氧量'" width="24" height="24" viewBox="0 0 24 24"><circle cx="12" cy="12" r="10" fill="#8bc34a"/></svg>
              </div>
              <div class="data-item">
                <div class="data-type">{{ item.dataTypeName }}</div>
                <div class="data-value">{{ item.dataValue }} <span class="data-unit">{{ item.unit }}</span></div>
                <div class="data-time">{{ parseTime(item.collectTime) }}</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!-- 折线图 -->
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <el-select v-model="chartDataType" placeholder="请选择数据类型" style="width: 200px;">
              <el-option
                v-for="dict in dict.type.env_data_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
            <el-button style="float: right; padding: 3px 0" type="text" @click="refreshChart">刷新折线图</el-button>
          </div>
          <div ref="chart" style="height: 400px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getCurrentEnvironment, listHistoryEnvironment } from "@/api/system/environment";
import { listProject } from "@/api/business/project";
import * as echarts from 'echarts';

export default {
  name: "EnvironmentCurrent",
  dicts: ['env_data_type'],
  data() {
    return {
      currentData: [],
      chartDataType: null, // 初始为null，等待字典加载后赋值
      chart: null,
      projectList: [],
      selectedProject: null,
      refreshTimer: null
    };
  },
  created() {
    this.getProjectList();
    this.getCurrentData();
    this.$nextTick(() => {
      // 等待字典加载后再赋值
      if (this.dict.type.env_data_type && this.dict.type.env_data_type.length > 0) {
        this.chartDataType = this.dict.type.env_data_type[0].value;
      }
      this.initChart();
    });
    // 自动刷新定时器
    this.refreshTimer = setInterval(() => {
      this.getCurrentData();
      this.refreshChart();
    }, 5000);
  },
  beforeDestroy() {
    if (this.refreshTimer) {
      clearInterval(this.refreshTimer);
      this.refreshTimer = null;
    }
  },
  watch: {
    chartDataType() {
      this.refreshChart();
    },
    'dict.type.env_data_type'(val) {
      if (val && val.length > 0 && this.chartDataType == null) {
        this.chartDataType = val[0].value;
      }
    }
  },
  methods: {
    /** 查询实时环境数据卡片 */
    getCurrentData() {
      getCurrentEnvironment().then(response => {
        this.currentData = response.data;
        this.currentData.forEach(item => {
          const dict = this.dict.type.env_data_type.find(d => d.value == item.dataType);
          if (dict) {
            item.dataTypeName = dict.label;
          }
        });
      });
    },
    /** 刷新卡片 */
    refreshCurrentData() {
      this.getCurrentData();
    },
    /** 初始化折线图 */
    initChart() {
      this.chart = echarts.init(this.$refs.chart);
      this.refreshChart();
    },
    /** 刷新折线图数据 */
    refreshChart() {
      // 查询最新10条历史+当前数据，按时间升序
      const params = {
        dataType: this.chartDataType,
        pageNum: 1,
        pageSize: 10
      };
      listHistoryEnvironment(params).then(response => {
        let data = response.rows || [];
        // 按采集时间升序排列
        data = data.slice().reverse();
        if (data.length === 0) {
          this.chart.clear();
          this.chart.showLoading({ text: '暂无数据' });
          return;
        }
        this.chart.hideLoading();
        const xAxis = data.map(item => this.parseTime(item.collectTime));
        const yAxis = data.map(item => item.dataValue);
        const unit = data.length > 0 ? data[0].unit : '';
        const dataTypeName = this.dict.type.env_data_type.find(d => d.value == this.chartDataType)?.label || '';
        // 模拟阈值线（后续可对接接口）
        let threshold = null;
        if (this.chartDataType == 1) threshold = 100; // PM2.5
        if (this.chartDataType == 2) threshold = 40;  // CO2
        if (this.chartDataType == 3) threshold = 30;  // 温度
        if (this.chartDataType == 4) threshold = 80;  // 湿度
        if (this.chartDataType == 5) threshold = 600; // 光照
        if (this.chartDataType == 6) threshold = 10;  // 烟雾
        if (this.chartDataType == 7) threshold = 80;  // 含氧量
        const option = {
          title: { text: dataTypeName + '最近10次数据趋势', left: 'center', textStyle: { fontSize: 16, color: '#333' } },
          tooltip: {
            trigger: 'axis',
            backgroundColor: 'rgba(50,50,50,0.9)',
            borderRadius: 8,
            textStyle: { color: '#fff' },
            formatter: function(params) {
              const p = params[0];
              return `${p.axisValue}<br/>${p.seriesName}: <b style='font-size:18px'>${p.data}</b> ${unit}`;
            }
          },
          grid: { left: 40, right: 30, top: 60, bottom: 40 },
          xAxis: {
            type: 'category',
            data: xAxis,
            boundaryGap: false,
            axisLine: { lineStyle: { color: '#bbb' } },
            axisLabel: { color: '#666', fontSize: 12 }
          },
          yAxis: {
            type: 'value',
            name: unit,
            axisLine: { lineStyle: { color: '#bbb' } },
            axisLabel: { color: '#666', fontSize: 12 },
            splitLine: { lineStyle: { color: '#eee', type: 'dashed' } }
          },
          series: [{
            name: dataTypeName,
            type: 'line',
            data: yAxis,
            smooth: false,
            symbol: 'circle',
            symbolSize: 10,
            itemStyle: {
              color: {
                type: 'linear',
                x: 0, y: 0, x2: 1, y2: 0,
                colorStops: [
                  { offset: 0, color: '#4f8cff' },
                  { offset: 1, color: '#43d19e' }
                ]
              },
              borderColor: '#fff',
              borderWidth: 2
            },
            lineStyle: {
              width: 4,
              color: {
                type: 'linear',
                x: 0, y: 0, x2: 1, y2: 0,
                colorStops: [
                  { offset: 0, color: '#4f8cff' },
                  { offset: 1, color: '#43d19e' }
                ]
              }
            },
            areaStyle: {
              color: {
                type: 'linear',
                x: 0, y: 0, x2: 0, y2: 1,
                colorStops: [
                  { offset: 0, color: 'rgba(79,140,255,0.18)' },
                  { offset: 1, color: 'rgba(67,209,158,0.05)' }
                ]
              }
            },
            emphasis: {
              itemStyle: {
                color: '#ff9800',
                borderColor: '#fff',
                borderWidth: 3,
                shadowBlur: 10,
                shadowColor: 'rgba(255,152,0,0.3)'
              }
            },
            markPoint: {
              data: [
                { type: 'max', name: '最大值', symbolSize: 50, itemStyle: { color: '#f56c6c' } },
                { type: 'min', name: '最小值', symbolSize: 50, itemStyle: { color: '#67c23a' } }
              ],
              label: { fontSize: 12, fontWeight: 'bold' }
            },
            animationDuration: 1200,
            animationEasing: 'cubicOut'
          }],
          // 阈值线
          markLine: threshold ? {
            symbol: 'none',
            data: [{ yAxis: threshold }],
            lineStyle: { color: '#f56c6c', type: 'dashed', width: 2 },
            label: { show: true, formatter: '阈值', color: '#f56c6c', fontWeight: 'bold' }
          } : undefined
        };
        this.chart.setOption(option, true);
      });
    },
    getProjectList() {
      listProject({}).then(response => {
        this.projectList = response.rows || [];
        if (this.projectList.length > 0) {
          this.selectedProject = this.projectList[0].projectId;
        }
      });
    }
  }
};
</script>

<style scoped>
.realtime-cards-flex {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: stretch;
  width: 100%;
}
.realtime-card-flex {
  flex: 1 1 0;
  margin-right: 16px;
  border-radius: 16px;
  box-shadow: 0 4px 24px 0 rgba(0,0,0,0.08);
  border: 2px solid transparent;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  min-width: 0;
  min-height: 140px;
  max-height: 180px;
  position: relative;
  transition: box-shadow 0.3s, border-color 0.3s, transform 0.2s;
}
.realtime-card-flex:hover {
  transform: scale(1.08);
  box-shadow: 0 8px 32px 0 rgba(0,0,0,0.16);
  z-index: 2;
}
.realtime-card-flex:last-child {
  margin-right: 0;
}
.card-icon {
  margin-left: 18px;
  margin-right: 10px;
  flex-shrink: 0;
}
.data-item {
  text-align: left;
  padding: 10px 0 10px 0;
  width: 100%;
  height: 120px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.data-type {
  font-size: 15px;
  color: #333;
  margin-bottom: 8px;
  font-weight: 500;
}
.data-value {
  font-size: 32px;
  font-weight: bold;
  color: #222;
  margin-bottom: 8px;
  line-height: 1.1;
}
.data-unit {
  font-size: 16px;
  font-weight: 400;
  color: #666;
}
.data-time {
  font-size: 12px;
  color: #999;
}
.warning-card {
  border-color: #f56c6c !important;
  animation: warning-flash 1s infinite alternate;
}
@keyframes warning-flash {
  0% { box-shadow: 0 0 8px 0 #f56c6c33; }
  100% { box-shadow: 0 0 24px 0 #f56c6c99; }
}
.card-bg-1 { background: linear-gradient(135deg, #e3f0ff 0%, #b3d8ff 100%); }
.card-bg-2 { background: linear-gradient(135deg, #e6fff6 0%, #b3ffe2 100%); }
.card-bg-3 { background: linear-gradient(135deg, #fff3e0 0%, #ffd180 100%); }
.card-bg-4 { background: linear-gradient(135deg, #e0f7fa 0%, #80deea 100%); }
.card-bg-5 { background: linear-gradient(135deg, #fffde7 0%, #fff59d 100%); }
.card-bg-6 { background: linear-gradient(135deg, #f5f5f5 0%, #bdbdbd 100%); }
.card-bg-7 { background: linear-gradient(135deg, #f1f8e9 0%, #aed581 100%); }
</style> 