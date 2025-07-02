<template>
  <div class="app-container">
    <el-row :gutter="20" class="mb8">
      <el-col :span="24">
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
  </div>
</template>

<script>
import { listHistoryEnvironment } from "@/api/system/environment";

export default {
  name: "EnvironmentHistory",
  dicts: ['env_data_type'],
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      historyList: [],
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
    }
  }
};
</script>

<style scoped>
</style> 