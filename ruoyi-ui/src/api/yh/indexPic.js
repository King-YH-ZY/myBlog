import request from '@/utils/request'

// 查询轮播图列表
export function listPic(query) {
  return request({
    url: '/yh/blog/indexPic/list',
    method: 'get',
    params: query
  })
}

// 查询轮播图详细
export function getPic(id) {
  return request({
    url: '/yh/blog/indexPic/' + id,
    method: 'get'
  })
}

// 新增轮播图
export function addPic(data) {
  return request({
    url: '/yh/blog/indexPic',
    method: 'post',
    data: data
  })
}

// 修改轮播图
export function updatePic(data) {
  return request({
    url: '/yh/blog/indexPic',
    method: 'put',
    data: data
  })
}

// 删除轮播图
export function delPic(ids) {
  return request({
    url: '/yh/blog/indexPic/'+ids,
    method: 'delete',
  })
}


// 上传轮播图
export function uploadPic(data) {
  return request({
    url: '/yh/blog/indexPic/upload',
    method: 'post',
    data: data
  })
}

// 修改轮播图
export function updatePicStatus(data) {
  return request({
    url: '/yh/blog/indexPic/status',
    method: 'put',
    data: data
  })
}

