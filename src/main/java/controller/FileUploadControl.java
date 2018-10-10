package controller;

import dao.UploadRecordDao;
import domain.UploadRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/7 23:21
 */

@Controller
@RequestMapping(value = "/update")
public class FileUploadControl {

    @Autowired
    UploadRecordDao uploadRecordDao;

    
    /**
     * @Description: 根据选择的时间和部门校验数据是否已经入库 
     * @Param: [] 
     * @return: void 
     * @Author: lcx
     * @Date: 2018/10/10 
     */ 
    @RequestMapping(value = "/update_single/validate_time_dep")
    public void updateSingle_validate_timeDep() {

    }

    /**
     * @Description: 根据文件判断是否已经上传过该文件
     * @Param: [] 
     * @return: void 
     * @Author: lcx
     * @Date: 2018/10/10 
     */ 
    @RequestMapping(value = "/update_single/validate_file")
    public void updateSingle_validateFile() {

    }
    
    @RequestMapping(value = "/update_single/update_file", method = RequestMethod.GET)
    public void upload(HttpServletRequest request, HttpServletResponse response) {

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("success");

        } catch (IOException e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            try {
                response.getWriter().println("error");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

//    @RequestMapping(value = "/update_single", method = RequestMethod.POST)
//    public void upload(HttpServletRequest request, HttpServletResponse response,
//                         @RequestParam("description") String description,
//                         @RequestParam("date") String date,
//                         @RequestParam("type") String type,
//                         @RequestParam("file") MultipartFile file) {
//
//        if (!file.isEmpty()) {
//
//            /**按照存储基地址/年月份/类型/文件名称的方式存储上传的文件*/
//            /**前端应该做一次过滤，如果传递的数据无效，则文件存储在默认路径下*/
//            if (date == null || date.length() == 0)
//                date = "default";
//            if (type == null || type.length() == 0)
//                type = "default";
//
//            String path = request.getServletContext().getRealPath(
//                    File.separator + date + File.separator + type);
//
//            String filename = file.getOriginalFilename();
//            File filepath = new File(path, filename);
//
//            /**判断路径是否存在，如果不存在就创建一个*/
//            if (!filepath.getParentFile().exists()) {
//                filepath.getParentFile().mkdirs();
//            }
//            /** 将上传文件保存到一个目标文件当中，同时在数据库插入一条记录*/
//            try {
//                file.transferTo(new File(path + File.separator + filename));
//
//                UploadRecord uploadRecord = new UploadRecord();
//                uploadRecord.setFile_name(filename);
//                uploadRecord.setFile_type(type);
//                uploadRecord.setStorage_path(path + filename);
//                uploadRecord.setUpload_date(new Date());
//                uploadRecord.setUpload_ip(getIpAddress(request));
//                uploadRecord.setDesc(description);
//
//                int result = uploadRecordDao.insertUploadRecord(uploadRecord);
//
//                if (result == 1) {
//                    response.setContentType("text/html;charset=UTF-8");
//                    response.getWriter().println("success");
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                response.setContentType("text/html;charset=UTF-8");
//                try {
//                    response.getWriter().println("error");
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//    }

    /**
     * 通过HttpServletRequest获取IP地址
     */
    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
