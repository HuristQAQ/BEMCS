import request from '@/utils/request'

// 查询设备列表
export function listDevice(query) {
  return request({
    url: '/business/device/list',
    method: 'get',
    params: query
  })
}

// 查询设备详细
export function getDevice(deviceId) {
  return request({
    url: '/business/device/' + deviceId,
    method: 'get'
  })
}

// 新增设备
export function addDevice(data) {
  return request({
    url: '/business/device',
    method: 'post',
    data: data
  })
}

// 修改设备
export function updateDevice(data) {
  return request({
    url: '/business/device',
    method: 'put',
    data: data
  })
}

// 删除设备
export function delDevice(deviceId) {
  return request({
    url: '/business/device/' + deviceId,
    method: 'delete'
  })
} 