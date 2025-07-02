import request from '@/utils/request'

// 查询实时环境数据
export function getCurrentEnvironment() {
  return request({
    url: '/business/environment/current',
    method: 'get'
  })
}

// 查询历史环境数据列表
export function listHistoryEnvironment(query) {
  return request({
    url: '/business/environment/history',
    method: 'get',
    params: query
  })
}

// 获取图表数据
export function getChartData(query) {
  return request({
    url: '/business/environment/chart',
    method: 'get',
    params: query
  })
}

// 查询环境数据列表
export function listEnvironment(query) {
  return request({
    url: '/business/environment/list',
    method: 'get',
    params: query
  })
}

// 查询环境数据详细
export function getEnvironment(dataId) {
  return request({
    url: '/business/environment/' + dataId,
    method: 'get'
  })
}

// 新增环境数据
export function addEnvironment(data) {
  return request({
    url: '/business/environment',
    method: 'post',
    data: data
  })
}

// 修改环境数据
export function updateEnvironment(data) {
  return request({
    url: '/business/environment',
    method: 'put',
    data: data
  })
}

// 删除环境数据
export function delEnvironment(dataId) {
  return request({
    url: '/business/environment/' + dataId,
    method: 'delete'
  })
}

// 导出环境数据
export function exportEnvironment(query) {
  return request({
    url: '/business/environment/export',
    method: 'post',
    params: query
  })
} 