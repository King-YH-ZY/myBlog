import request from '@/utils/request'

// 查询博客列表
export function listBlog(query) {
  return request({
    url: '/yh/blog/list',
    method: 'get',
    params: query
  })
}

// 查询博客详细
export function getBlog(blogId) {
  return request({
    url: '/yh/blog/' + blogId,
    method: 'get'
  })
}

// 新增博客
export function addBlog(data) {
  return request({
    url: '/yh/blog',
    method: 'post',
    data: data
  })
}

// 修改博客
export function updateBlog(data) {
  return request({
    url: '/yh/blog',
    method: 'put',
    data: data
  })
}

// 删除博客
export function delBlog(blogId) {
  return request({
    url: '/yh/blog/' + blogId,
    method: 'delete'
  })
}

// 导出博客
export function exportBlog(query) {
  return request({
    url: '/yh/blog/export',
    method: 'get',
    params: query
  })
}

// 上传文章图片
export function addImage(data) {
  return request({
    url: '/common/upload',
    method: 'post',
    data: data
  })
}

// 删除文章图片
export function delImage(data) {
  return request({
    url: '/yh/blog/delImg',
    method: 'delete',
    data: data
  })
}

// 查询博客列表
export function getAllTags() {
  return request({
    url: '/yh/blog/tag/all',
    method: 'get'
  })
}
  // 查询博客列表
export function getAllTagsByBlogId(blogId) {
  return request({
    url: '/yh/blog/tag/all/'+blogId,
    method: 'get'
  })
}