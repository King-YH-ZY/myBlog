import request from '@/utils/request'
// 查询博客列表
export function getBlogList(query) {
    return request({
      url: '/yh/front/getBlogList',
      method: 'get',
      params: query
    })
  }

// 查询具体博客
export function getBlog(blogId) {
  return request({
    url: '/yh/front/getBlog/'+blogId,
    method: 'get'
  })
}
 

  // 查询博客归档
export function getBlogTimeLine() {
  return request({
    url: '/yh/front/getBlogTimeLine',
    method: 'get'
  })
}

  // 查询轮播图
  export function getIndexPic() {
    return request({
      url: '/yh/front/getIndexPic',
      method: 'get'
    })
  }