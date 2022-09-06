<template>
  <div>
    <el-dropdown>
      <i class="el-icon-setting" style="margin-right: 15px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="personInfo">个人信息</el-dropdown-item>
<!--        点击事件写法不太一样-->
        <el-dropdown-item @click.native="goBackLogin">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
<!--    这里不用{{}},注意，要通过这种方式获得数据来显示图片(src="item.xxxx")，src前面需要加:-->
    <img :src="user.avatar"
         alt="" style="width: 30px;border-radius: 50%;position: relative;top: 10px;right: 5px">
<!--    后端有定义.后内容-->
    <span>{{ user.nickname }}</span>
<!--弹窗-->
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px">
        <el-form-item label="密码">
<!--          palceholder要使用data中数据，前面必须加:-->
          <el-input :placeholder="user.password" v-model="user.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input :placeholder="user.nickname" v-model="user.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-input :placeholder="user.avatar" v-model="user.avatar" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}
</style>

<script>
export default {
  name: "Header",
  data(){
    return{
      //如果有，解析，没有则为空
      user:localStorage.getItem("userLogin") ? JSON.parse(localStorage.getItem("userLogin")) :{},
      dialogFormVisible:false,
    }
  },
  methods:{
    goBackLogin(){
      //退出即删除用户信息
      localStorage.removeItem("userLogin")
      this.$router.push("/login")
      this.$message.success("退出成功")
    },
    personInfo(){
      this.dialogFormVisible=true
    },
    save(){
      this.request.post("http://localhost:9091/user/alter",this.user).then(res=>{
        if(res){
          //弹出提示
          this.$message.success("修改成功")
          this.user.password=res.password
          this.user.nickname=res.nickname
          this.user.avatar=res.avatar
          this.dialogFormVisible=false

        }else{
          this.$message.error("修改失败")
        }
      })
    }
  }
}
</script>

