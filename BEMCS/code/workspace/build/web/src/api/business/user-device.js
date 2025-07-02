import request from '@/utils/request'

// 查询用户设备关联列表
export function listUserDevice(query) {
  return request({
    url: '/business/user-device/list',
    method: 'get',
    params: query
  })
}

// 查询用户设备关联详细
export function getUserDevice(id) {
  return request({
    url: '/business/user-device/' + id,
    method: 'get'
  })
}

// 新增用户设备关联
export function addUserDevice(data) {
  return request({
    url: '/business/user-device',
    method: 'post',
    data: data
  })
}

// 删除用户设备关联
export function delUserDevice(id) {
  return request({
    url: '/business/user-device/' + id,
    method: 'delete'
  })
} 