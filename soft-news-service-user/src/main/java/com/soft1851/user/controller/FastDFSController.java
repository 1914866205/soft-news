package com.soft1851.user.controller;

import com.soft1851.common.utils.FastDFSFile;
import com.soft1851.common.utils.FastDFSClient;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName FastDFSController.java
 * @Description TODO
 * @createTime 2020年11月18日 11:09:00
 */
@Controller
@RequestMapping("/fastdfs")
public class FastDFSController {

    private static Logger logger = LoggerFactory.getLogger(FastDFSController.class);

    /**
     * 访问上传页面
     *
     * @return
     */
    @GetMapping("/toUp")
    public String toUp() {
        return "fastdfs/toUp";
    }

    /**
     * 上传文件
     *
     * @param file
     * @param map
     * @return
     */
    @PostMapping("/singleFileUpload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   ModelMap map) {
        if (file.isEmpty()) {
            map.addAttribute("message", "Please select a file to upload");
            return "/fastdfs/uploadStatus";
        }
        try {
            String path = saveFile(file);
            System.out.println(path);
            System.out.println(file.getOriginalFilename());
            map.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            map.addAttribute("path", "file path url '" + path + "'");
        } catch (Exception e) {
            logger.error("upload file failed", e);
        }
        return "/fastdfs/uploadStatus";
    }

    /**
     * 获取文件信息
     *
     * @param groupName:      group1
     * @param remoteFileName: M00/00/00/wKgBoF2kDXWAHdAKAAJsTdA9W_o579.png
     * @return
     */
    @PostMapping("/getFileInfo")
    public String getFileInfo(String groupName, String remoteFileName) {
        FileInfo fileInfo = FastDFSClient.getFile(groupName, remoteFileName);
        System.out.println("CRC32签名：" + fileInfo.getCrc32());
        System.out.println("文件大小：" + fileInfo.getFileSize());
        System.out.println("服务器地址：" + fileInfo.getSourceIpAddr());
        System.out.println("创建时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fileInfo.getCreateTimestamp()));
        return "/fastdfs/uploadStatus";
    }

    /**
     * 获取元数据信息
     *
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    @PostMapping("/getMetaData")
    public String getMetaData(String groupName, String remoteFileName) throws Exception {
        NameValuePair[] get_metadata = FastDFSClient.getStorageClient().get_metadata(groupName,
                remoteFileName);
        for (NameValuePair nameValuePair : get_metadata) {
            System.out.println("name: " + nameValuePair.getName() + "  value: " + nameValuePair.getValue());
        }
        return "/fastdfs/uploadStatus";
    }

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     */
    private String saveFile(MultipartFile multipartFile) throws Exception {
        String[] fileAbsolutePath = {};
        String filename = multipartFile.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream = multipartFile.getInputStream();
        if (inputStream != null) {
            int lenl = inputStream.available();
            file_buff = new byte[lenl];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(filename, file_buff, ext);
        try {
            fileAbsolutePath = FastDFSClient.upload(file);
        } catch (Exception e) {
            logger.error("upload file failed,please upload again!");
        }
        String path = FastDFSClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
        return path;
    }
}
