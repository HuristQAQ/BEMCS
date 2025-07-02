import request from '@/utils/request'

// 查询报警记录列表
export function listAlarm(query) {
  return request({
    url: '/business/alarm/list',
    method: 'get',
    params: query
  })
} 