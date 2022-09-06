<template>
<div>

  <div>
    <!--            加图标，默认字-->
    <el-input style="width: 200px;padding-bottom: 10px" placeholder="请输入名称" v-model="username" suffix-icon="el-icon-search"></el-input>
    <!--            <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-search"></el-input>-->
    <!--            <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-search"></el-input>-->
    <el-button type="primary" @click="load">搜索</el-button>
  </div>
  <div>
    <el-button type="primary" @click="handleAdd">新增</el-button>
    <el-button type="danger" @click="handleMultiplyDel">批量删除</el-button>
    <el-button type="info">导入</el-button>
    <el-button type="info">导出</el-button>
  </div>
  <el-table :data="tableData" @selection-change="handleSelectionChange">
    <el-table-column
        type="selection"
        width="55">
    </el-table-column>
    <!--            label是最上面显示的，prop对应data中的数据名-->
    <el-table-column prop="id" label="id" width="140">
    </el-table-column>
    <el-table-column prop="username" label="用户名" width="140">
    </el-table-column>
    <el-table-column prop="nickname" label="昵称" width="120">
    </el-table-column>
    <el-table-column prop="email" label="邮箱">
    </el-table-column>
    <el-table-column prop="phone" label="电话">
    </el-table-column>
    <el-table-column prop="address" label="地址">
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <!--                数据传入-->
        <el-button type="success" size="small" @click="handleEdit(scope.row)">编辑</el-button>
        <!--                popconfirm-->
        <el-popconfirm
            style="margin-left: 5px"
            confirm-button-text='确定'
            cancel-button-text='取消'
            icon="el-icon-info"
            icon-color="red"
            title="您确定删除吗"
            @confirm="handleDel(scope.row.id)"
        >
          <el-button type="danger" size="small" slot="reference">删除</el-button>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <div>
    <!--       改变页大小方法，改变目前页方法，目前页，页的大小分类，默认叶大小     -->
    <!--       total中的"total"是data中的total     -->
    <!--       pageNum和pageSize是前端点击传给后端，total是后端传给前端-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2, 5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>


  <!--          弹窗-->
  <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="80px">
      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "User",
  data() {
    return {
      //默认为空
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:2,
      username:"",
      dialogFormVisible:false,
      form:{},
      //数组
      multipleSelection:[]
    }
  },

//  created可在页面更新前访问data数据
  created(){
    this.load()
  },


  methods:{
    //请求数据
    load(){
      //axios写法
      //从后端网址拿到数据res，执行相应操作,get内部写法也可与原来一致
      request.get("http://localhost:9091/user/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username
        }
      }).then(res=>{
        console.log(res)
        //这里data中的tableData数据是res传过来数据中的data,total同理（见后端）
        this.tableData = res.data
        this.total = res.num

      })
      // //  请求分页查询数据,注意后端需要配置跨域访问(过时写法)
      // //  第一步，拿到数据，第二步，把字符串转为json，第三步，执行
      // fetch("http://localhost:9091/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username).then(res => res.json()).then(res => {
      //   console.log(res)
      //   //这里data中的tableData数据是res传过来数据中的data,total同理（见后端）
      //   this.tableData = res.data
      //   this.total = res.num

      // })
    },
    //新增
    handleAdd(){
      this.dialogFormVisible=true
      //表单置空
      this.form={}
    },
    //编辑
    handleEdit(row){
      //由上可见，row是表格数据
      this.form=row
      this.dialogFormVisible=true
    },
    //删除,上方传入的是scope.row.id
    handleDel(id){
      request.delete("http://localhost:9091/user/"+id).then(res =>{
        if(res){
          //弹出提示
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })

    },
    //批量选择
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val

    },
    //批量删除
    handleMultiplyDel(){
      let ids=this.multipleSelection.map(value => value.id) //把对象数组变为对象的一个元素的数组
      request.post("http://localhost:9091/user/delBatch",ids).then(res=>{
        this.load()
        this.$message.success("批量删除成功")
      })
    },
    //提交表单
    save(){
      //注意，提交表格与参数写法不一样
      request.post("http://localhost:9091/user/",this.form).then(res =>{
        if(res){
          //弹出提示
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error("报错失败")
        }

      })
    },
    //elemengui板子里可以看到写法
    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum=pageNum
      this.load()
    }
  }
}
</script>

