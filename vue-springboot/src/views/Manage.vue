<template>
  <div style="height: 100%">
    <el-container style="height: 100%; border: 1px solid #eee">

      <Aside/>

      <el-container>

        <el-header style="text-align: right; font-size: 12px" >
          <Header/>
        </el-header>

        <el-main>
<!--          表示当前页面使用子路由的地方，也就是说，来到子路由的界面，会执行整个该文件，不在子路由的界面（在父路由路径下），会执行除该子路由外其他整个该文件-->
          <router-view></router-view>

        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>

<script>

import request from "@/utils/request";
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'Manage',
  //组件
  components:{
    Aside,
    Header,
  },
}
</script>
