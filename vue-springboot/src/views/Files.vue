<template>
  <div>
    <el-button type="success" size="small" @click="upload">上传</el-button>
    <el-table :data="tableData">
      <!--            label是最上面显示的，prop对应data中的数据名-->
      <el-table-column prop="id" label="id" width="140">
      </el-table-column>
      <el-table-column prop="name" label="文件名" width="140">
      </el-table-column>
      <el-table-column prop="type" label="文件类型" width="120">
      </el-table-column>
      <el-table-column prop="isexists" label="是否可用" width="120">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <!--                数据传入-->
          <el-button type="success" size="small" @click="handleEdit(scope.row)">预览</el-button>
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

    <!--          上传弹窗-->
    <el-dialog title="文件上传" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px">
<!--        action是后端接口-->
        <el-upload
            class="upload-demo"
            drag
            action="http://localhost:9091/file/upload"
            multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="uploadFiles">确 定</el-button>
      </div>
    </el-dialog>


    <!--          预览弹窗-->
    <el-dialog title="文件预览" :visible.sync="previewPicture" width="30%">
      <el-form label-width="80px">
        <img class="img2" :src="img">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">返回</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Files",
  data() {
    return {
      //默认为空
      tableData: [],
      dialogFormVisible:false,
      previewPicture:false,
      img:"",
      selectID:1,
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
      request.get("http://localhost:9091/file/search").then(res=>{
        console.log(res)
        //这里data中的tableData数据是res传过来数据中的data,total同理（见后端）
        //如果不是被拦截的res，则赋值，在files文件中不用这么写是因为那里刚好用的是res.data，而res.data在拦截器中显示为null，因此不会报错，而这里用的是res，因此会报错，要进行判断
        if(res.code!=1234) this.tableData = res

      })
    },
    upload(){
      this.dialogFormVisible=true
    },
    uploadFiles(){
      this.load()
      this.dialogFormVisible=false
    },
    //预览
    handleEdit(row){
      //由上可见，row是表格数据
      this.selectID=row.id
      request.get("http://localhost:9091/file/preview",{
        params:{
          id:this.selectID
        }
      }).then(res=>{
        console.log(res.url)
        //在public下访问图片直接是/files/图片，若直接使用磁盘路径会报错（浏览器规定）
        this.img=res.url
        this.previewPicture=true

      })
    },

  }
}
</script>

<style scoped>
.img2{
  width: 350px;
  height: 200px;
}
</style>