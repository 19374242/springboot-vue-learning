<template>
<div class="wrapper">
<!--  外边距上下200px，左右自适应（居中）,padding指内部元素和边框空20px,圆角-->
  <div style="margin: 200px auto;background-color: white;width: 350px;height: 300px;padding: 20px;border-radius: 10px">
    <div style="margin: 20px 0px;text-align: center;font-size: 24px">登录</div>
<!--    model和rules是表单验证,ref是用来验证符不符合前述条件，不符合则不向后端发送请求，在elementui上有写-->
    <el-form :model="user" :rules="rules">
      <el-form-item prop="username">
        <!--   prefix-icon指加图标,prop与下方数据对应 -->
        <el-input placeholder="请输入用户名" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.password" show-password></el-input>
      </el-form-item>
      <div style="margin: 10px 0;text-align: right">
        <el-button type="primary" size="small" @click="login">登录</el-button>
<!--        此处不用this.router.push而是router.push，双引号里面用单引号-->
        <el-button type="warning" size="small" @click="$router.push('/register')">注册</el-button>
      </div>
    </el-form>
  </div>
</div>
</template>

<script>
export default {
  name: "login",
  data(){
    return {
      //绑定内的内容可以直接写，此处没必要写出其属性，只要名字加{}即可(可写可不写)
      user: {
        username:'',
        password:''
      },
      //输入时验证（elementui上cv）
      rules: {
        username: [
          //  blur指鼠标失去焦点时,username与prop对应
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'}
        ],
        password: [
          //  blur指鼠标失去焦点时,username与prop对应
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    login(){
      //不引入request则要这么写
      this.request.post("http://localhost:9091/user/login",this.user).then(res=>{
        if(res.code==0){
          //.code和.message都是后端传
          this.$message.error(res.message)
        }
        else{
          //把res.data存到前端浏览器里面,任何界面可通过getItem访问,需要JSON.parse解析
          localStorage.setItem("userLogin",JSON.stringify(res.data))
          this.$message.success("登入成功")
          //来到相应路由处
          this.$router.push("/")
        }
      })
    }
  }
}
</script>

<style scoped>
  .wrapper{
    /*100%高度*/
    height: 100vh;
    /*  渐变背景色,从上到下，从左到右*/
    background-image: linear-gradient(to bottom right,#FC4668,#3F5EFB);
    /*超出部分隐藏*/
    overflow: hidden;
  }
</style>