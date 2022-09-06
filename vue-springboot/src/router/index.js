import Vue from 'vue'
import VueRouter from 'vue-router'

import Manage from '@/views/Manage.vue'
import User from "@/views/User.vue";
import Home from "@/views/Home.vue";
import Files from "@/views/Files.vue";
Vue.use(VueRouter)

const routes = [
  {
      path: '/',
      name: 'manage',
      component: Manage,
      //‘/’会重定向到‘/home’
      redirect: "/home",
      //子路由
      children: [
          {
              path: 'user',
              name: 'user',
              component: User
          },
          {
              path: 'home',
              name: 'home',
              component: Home
          },
          {
              path: 'files',
              name: 'files',
              component: Files
          },
      ]
  },
  {
      path: '/login',
      name: 'login',
      component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue'),
  },
    {
        path: '/register',
        name: 'register',
        component: () => import(/* webpackChunkName: "about" */ '../views/Register.vue'),
    }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
