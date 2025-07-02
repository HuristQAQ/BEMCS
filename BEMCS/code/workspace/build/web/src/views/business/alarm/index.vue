<template>
  <div class="app-container">
    <el-card class="box-card">
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
        <el-form-item label="报警时间">
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

      <el-table v-loading="loading" :data="alarmList">
        <el-table-column label="报警ID" align="center" prop="alarmId" />
        <el-table-column label="数据类型" align="center" prop="dataType">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.env_data_type" :value="scope.row.dataType"/>
          </template>
        </el-table-column>
        <el-table-column label="数据值" align="center" prop="dataValue" />
        <el-table-column label="报警时间" align="center" prop="alarmTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.alarmTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="项目名称" align="center" prop="projectName" />
        <el-table-column label="设备名称" align="center" prop="deviceName" />
       
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-card>
  </div>
</template>

<script>
import { listAlarm } from "@/api/business/alarm";

export default {
  name: "Alarm",
  dicts: ['env_data_type'],
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      alarmList: [],
      dateRange: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dataType: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询报警记录列表 */
    getList() {
      this.loading = true;
      listAlarm(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.alarmList = response.rows;
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
    }
  }
};
</script>

<style scoped>
</style> 