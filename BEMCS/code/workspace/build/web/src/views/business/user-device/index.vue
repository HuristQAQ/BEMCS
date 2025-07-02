<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
          <el-form-item label="用户名称" prop="userName">
            <el-input v-model="queryParams.userName" placeholder="请输入用户名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="项目名称" prop="projectName">
            <el-input v-model="queryParams.projectName" placeholder="请输入项目名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="设备名称" prop="deviceName">
            <el-input v-model="queryParams.deviceName" placeholder="请输入设备名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">删除</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="userDeviceList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="用户名称" align="center" prop="userName" :show-overflow-tooltip="true" />
          <el-table-column label="项目名称" align="center" prop="projectName" :show-overflow-tooltip="true" />
          <el-table-column label="设备名称" align="center" prop="deviceName" :show-overflow-tooltip="true" />
          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
      </el-col>
    </el-row>

    <!-- 添加或修改用户设备关联对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户" prop="userId">
              <el-select v-model="form.userId" placeholder="请选择用户">
                <el-option v-for="user in userOptions" :key="user.userId" :label="user.nickName" :value="user.userId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目" prop="projectId">
              <el-select v-model="form.projectId" placeholder="请选择项目">
                <el-option v-for="project in projectOptions" :key="project.projectId" :label="project.projectName" :value="project.projectId" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备" prop="deviceId">
              <el-select v-model="form.deviceId" placeholder="请选择设备">
                <el-option v-for="device in deviceOptions" :key="device.deviceId" :label="device.deviceName" :value="device.deviceId" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUserDevice, addUserDevice, delUserDevice } from "@/api/business/user-device";
import { listUser } from "@/api/system/user";
import { listProject } from "@/api/business/project";
import { listDevice } from "@/api/business/device";
import { parseTime } from "@/utils/ruoyi";

export default {
  name: "UserDevice",
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 用户设备关联表格数据
      userDeviceList: null,
      // 用户选项
      userOptions: [],
      // 项目选项
      projectOptions: [],
      // 设备选项
      deviceOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        projectName: undefined,
        deviceName: undefined
      },
      // 表单参数
      form: {
        userId: undefined,
        projectId: undefined,
        deviceId: undefined
      },
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户不能为空", trigger: "change" }
        ],
        projectId: [
          { required: true, message: "项目不能为空", trigger: "change" }
        ],
        deviceId: [
          { required: true, message: "设备不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getUserOptions();
    this.getProjectOptions();
  },
  methods: {
    /** 查询用户设备关联列表 */
    getList() {
      this.loading = true;
      listUserDevice(this.queryParams).then(response => {
          this.userDeviceList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询用户选项 */
    getUserOptions() {
      listUser().then(response => {
        this.userOptions = response.rows;
      });
    },
    /** 查询项目选项 */
    getProjectOptions() {
      listProject().then(response => {
        this.projectOptions = response.rows;
      });
    },
    /** 查询设备选项 */
    getDeviceOptions() {
      if (this.form.projectId) {
        listDevice({ projectId: this.form.projectId }).then(response => {
          this.deviceOptions = response.rows;
        });
      } else {
        this.deviceOptions = [];
        this.form.deviceId = undefined;
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        userId: undefined,
        projectId: undefined,
        deviceId: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户设备关联";
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addUserDevice(this.form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除用户设备关联编号为"' + ids + '"的数据项？').then(function() {
        return delUserDevice(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  },
  watch: {
    'form.projectId': {
      handler(newVal) {
        this.getDeviceOptions();
      }
    }
  }
};
</script> 