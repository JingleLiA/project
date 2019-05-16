import Vue from 'vue'
import Router from 'vue-router'
import Measurements from '@/components/Measurements'
import Tree from '@/components/Tree'
import Trees from '@/components/Trees'
import LoginComponent from "@/components/LoginComponent";


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: Measurements
    },{
      path: '/login',
      name: 'login',
      component: LoginComponent
    },{
      path: '/tree',
      name: 'trees',
      component: Trees
    },{
      path: '/tree/:treeCode',
      name: 'tree',
      component: Tree
    }
  ]
})
