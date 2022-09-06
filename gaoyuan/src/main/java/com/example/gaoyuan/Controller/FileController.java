package com.example.gaoyuan.Controller;

import ch.qos.logback.core.util.FileUtil;
import com.example.gaoyuan.entity.Files;
import com.example.gaoyuan.entity.User;
import com.example.gaoyuan.mapper.FileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/file")  //给接口加前缀，要在/file处访问才行
public class FileController {

//引入存储路径
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Autowired
    private FileMapper fileMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file){
//        获取名字不是getName啊啊啊啊
        String fileName=file.getOriginalFilename();
//        得到文件后缀名
        String type=fileName.substring(fileName.lastIndexOf(".")+1);
        File dirFile=new File(fileUploadPath);
//        不存在则创建目录
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
//        随机生成唯一文件名
        String newFileName= UUID.randomUUID().toString();
        File uploadFile=new File(fileUploadPath+'/'+newFileName+"."+type);
//        将文件转存到相应位置
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        插入数据库
//        绝对路径
        String url1=fileUploadPath+'/'+newFileName+"."+type;
//        前端public下
        String url2="/files/"+newFileName+"."+type;
        Files myFiles=new Files();
        myFiles.setIsexists(1);
        myFiles.setUrl(url2);
        myFiles.setName(newFileName);
        myFiles.setType(type);
        fileMapper.insertFile(myFiles);
        return url2;
    }
    @GetMapping("/search")
    public List<Files> searchALL(){
        return fileMapper.findAll();
    }

//    预览某一图片,注意传参数用的是get
    @GetMapping("/preview")
    public HashMap<String, Object> preview(@RequestParam Integer id){
        String url=fileMapper.getUrl(id);
        HashMap<String,Object> res=new HashMap<>();
        res.put("url",url);
//        值得注意的是，这里不能直接返回String,因为前端必须要是JSON类型，即“键”：“值”，因此，用哈希表刚好符合这种输出格式,前端用res.url即可获得数据
        return res;
    }
}
