//前台页面路由
import Layout from '@/layout/front/index'

export default {
  path: '/front',
  component: Layout,
  hidden:true,
  children: [{
    path: '/front',
    component: (resolve) => require(['@/views/front/index'], resolve),
    name: 'Blog首页',
    meta: {
      title: 'Blog首页'
    }
  }, {
    path: 'page/:pageNum(\\d+)',
    component: (resolve) => require(['@/views/front/index'], resolve),
    name: 'Blog文章',
    meta: {
      title: 'Blog文章'
    }
  },{
    path: 'article',
    component: (resolve) => require(['@/views/front/article'], resolve),
    name: 'article',
    meta: {
      title: 'article'
    }
  },{
    path: 'timeline',
    component: (resolve) => require(['@/views/front/timeline'], resolve),
    name: 'timeline',
    meta: {
      title: 'timeline'
    }
  }]
}
