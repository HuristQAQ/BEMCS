<template>
  <div class="app-container">
    <!-- 实时数据展示 -->
    <el-row :gutter="20" class="mb8">
      <el-col :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>实时环境数据</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="refreshCurrentData">刷新</el-button>
          </div>
          <el-row :gutter="20">
            <el-col :span="4" v-for="(item, index) in currentData" :key="index">
              <el-card shadow="hover" :class="{'warning-card': item.isAlarm}">
                <div class="data-item">
                  <div class="data-type">{{ item.dataTypeName }}</div>
                  <div class="data-value">{{ item.dataValue }} {{ item.unit }}</div>
                  <div class="data-time">{{ parseTime(item.collectTime) }}</div>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>

    <!-- 历史数据查询 -->
    <el-row :gutter="20" class="mb8">
      <el-col :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>历史数据查询</span>
          </div>
          <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
            <el-form-item label="数据类型" prop="dataType">
              <el-select v-model="queryParams.dataType" placeholder="请选择数据类型" clearable>
                <el-option
                  v-for="dict in dict.type.env_data_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="采集时间">
              <el-date-picker
                v-model="dateRange"
                size="small"
                style="width: 240px"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetimerange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              ></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="loading" :data="historyList">
            <el-table-column label="数据类型" align="center" prop="dataType">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.env_data_type" :value="scope.row.dataType"/>
              </template>
            </el-table-column>
            <el-table-column label="数据值" align="center" prop="dataValue" />
            <el-table-column label="单位" align="center" prop="unit" />
            <el-table-column label="采集时间" align="center" prop="collectTime" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.collectTime) }}</span>
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表展示 -->
    <el-row :gutter="20" class="mb8">
      <el-col :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>数据趋势图</span>
            <el-select v-model="chartDataType" placeholder="请选择数据类型" style="width: 200px; margin-left: 20px;">
              <el-option
                v-for="dict in dict.type.env_data_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
            <el-select v-model="chartTimeRange" placeholder="请选择时间范围" style="width: 200px; margin-left: 20px;">
              <el-option label="最近1小时" value="hour" />
              <el-option label="最近24小时" value="day" />
            </el-select>
            <el-button style="float: right; padding: 3px 0" type="text" @click="refreshChart">刷新</el-button>
          </div>
          <div ref="chart" style="height: 400px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getCurrentEnvironment, listHistoryEnvironment, getChartData } from "@/api/system/environment";
import * as echarts from 'echarts';

export default {
  name: "Environment",
  dicts: ['env_data_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 实时数据列表
      currentData: [],
      // 历史数据列表
      historyList: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dataType: undefined
      },
      // 图表数据类型
      chartDataType: 1,
      // 图表时间范围
      chartTimeRange: 'hour',
      // 图表实例
      chart: null
    };
  },
  created() {
    this.getCurrentData();
    this.getList();
    this.initChart();
  },
  methods: {
    /** 查询实时环境数据 */
    getCurrentData() {
      getCurrentEnvironment().then(response => {
        this.currentData = response.data;
        // 添加数据类型名称
        this.currentData.forEach(item => {
          const dict = this.dict.type.env_data_type.find(d => d.value == item.dataType);
          if (dict) {
            item.dataTypeName = dict.label;
          }
        });
      });
    },
    /** 查询历史环境数据列表 */
    getList() {
      this.loading = true;
      listHistoryEnvironment(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.historyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 刷新实时数据 */
    refreshCurrentData() {
      this.getCurrentData();
    },
    /** 初始化图表 */
    initChart() {
      this.chart = echarts.init(this.$refs.chart);
      this.refreshChart();
    },
    /** 刷新图表数据 */
    refreshChart() {
      const params = {
        dataType: this.chartDataType,
        timeRange: this.chartTimeRange
      };
      getChartData(params).then(response => {
        const data = response.data;
        const xAxis = data.map(item => this.parseTime(item.collectTime));
        const yAxis = data.map(item => item.dataValue);
        const unit = data.length > 0 ? data[0].unit : '';
        const dataTypeName = this.dict.type.env_data_type.find(d => d.value == this.chartDataType)?.label || '';

        const option = {
          title: {
            text: dataTypeName + '趋势图'
          },
          tooltip: {
            trigger: 'axis',
            formatter: '{b}<br/>{a}: {c} ' + unit
          },
          xAxis: {
            type: 'category',
            data: xAxis
          },
          yAxis: {
            type: 'value',
            name: unit
          },
          series: [{
            name: dataTypeName,
            type: 'line',
            data: yAxis,
            smooth: true
          }]
        };

        this.chart.setOption(option);
      });
    }
  }
};
</script>

<style scoped>
.data-item {
  text-align: center;
  padding: 10px;
}
.data-type {
  font-size: 14px;
  color: #666;
  margin-bottom: 10px;
}
.data-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}
.data-time {
  font-size: 12px;
  color: #999;
}
.warning-card {
  border-color: #f56c6c;
}
</style> 