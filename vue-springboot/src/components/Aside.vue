<template>
  <el-aside width="200px;" style="background-color: rgb(238, 241, 246); height: 100%">
    <!--        最后一个是高亮文字颜色,第二排关动画-->
    <el-menu :default-openeds="['1', '3']" background-color="rgb(48, 65, 86)" text-color="#fff" active-text-color="#ffd04b" router>
      <div style="height: 60px;line-height: 60px;text-align: center">
        <b style="color: white">后台管理系统</b>
      </div>
<!--      index指点击就跳到相应路由,当然，前提要在el-menu上加router-->
      <el-menu-item index="/">主页</el-menu-item>
      <el-submenu index="2">
        <template slot="title"><i class="el-icon-menu"></i>系统管理</template>
        <el-menu-item index="/user">用户管理</el-menu-item>
        <el-menu-item index="/files">文件管理</el-menu-item>
      </el-submenu>
    </el-menu>
    <div style="width: 200px;background-color: rgb(48, 65, 86);height: 700px;">

    </div>
  </el-aside>
</template>

<script>
export default {
  name: "Aside"
}
</script>

<style scoped>
.el-aside {
  color: #333;
}
</style>