package controller;

import domain.Employee;
import domain.SalaryDetail;
import exception.RecordInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import service.FileUploadService;
import service.QueryService;
import service.SalaryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/7 23:21
 */

@Controller
@RequestMapping(value = "/update")
public class FileUploadControl {

    private FileUploadService fileUploadService;
    private QueryService queryService;
    private SalaryService salaryService;

    @Autowired
    public void setFileUploadService(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @Autowired
    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    @Autowired
    public void setSalaryService(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    /**
     * @Description: 根据选择的时间和部门校验数据是否已经入库
     * @Param: []
     * @return: void
     * @Author: lcx
     * @Date: 2018/10/10
     */
    @RequestMapping(value = "/update_single/validate_time_dep")
    public void updateSingle_validate_timeDep(@RequestParam("department") String department,
                                              @RequestParam("time") String time,
                                              HttpServletResponse response) {
        String result = "OK";
        if (department != null && time != null && !department.equals("") && !time.equals("")) {
            //日期转换
            Date date = dateTransform(time);
            int count = queryService.departmentAtDateExist(department, date);
            if (count > 0)
                result = "EXIST";
        }
        response.setContentType("text/html;charset=UTF-8");
        //设置跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");

        try {
            response.getWriter().println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 根据文件判断是否已经上传过该文件
     * @Param: []
     * @return: void
     * @Author: lcx
     * @Date: 2018/10/10
     */
    @RequestMapping(value = "/update_single/validate_file")
    public void updateSingle_validateFile(@RequestParam("file_name") String file_name,
                                          @RequestParam("file_type") String file_type,
                                          HttpServletResponse response) {
        String result = "OK";
        if (file_name != null && file_type != null && !file_name.equals("") && !file_type.equals("")) {
            int count = fileUploadService.validateFile(file_name, file_type);
            if (count > 0)
                result = "EXIST";
        }

        response.setContentType("text/html;charset=UTF-8");
        //设置跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");

        try {
            response.getWriter().println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * @Description: 接收前端上传的文件：接收并写数据库记录 -> 解析  ->入库 
     * @Param: [request, response, description, date, type, file] 
     * @return: void
     * @Author: lcx
     * @Date: 2018/10/11 
     */ 
    @RequestMapping(value = "/update_single/update_file", method = RequestMethod.POST)
    public void upload(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam("description") String description,
                       @RequestParam("date") String date,
                       @RequestParam("type") String type,
                       @RequestParam("file") MultipartFile file) {

        String result = null;

        if (!file.isEmpty()) {

            //按照存储基地址/年月份/类型/文件名称的方式存储上传的文件
            //前端应该做一次过滤，如果传递的数据无效，则文件存储在默认路径下
            if (date == null || date.length() == 0)
                date = "default_date";
            if (type == null || type.length() == 0)
                type = "default_type";

            String path = request.getServletContext().getRealPath(
                    File.separator + date + File.separator + type);
            String IP = getIpAddress(request);

            int count = fileUploadService.uploadAndInsertRecord(type, path, file,
                    IP, description);

            if (count < 0)
                result = "RECEIVE FAIL";
            else {
                //对数据进行解析和插入
                if (date != null && date.length() != 0) {
                    Date salary_data = dateTransform(date);
                    Map<String, Employee> employeeMap = new HashMap<>();
                    Map<String, SalaryDetail> salaryDetailMap = new HashMap<>();
                    String filename = file.getOriginalFilename();
                    String filePath = path + File.separator + filename;
                    //解析完成的数据时没有日期的，需要根据选的日期进行设定
                    int res = DataParse.ReadExcelData(filePath, employeeMap, salaryDetailMap);
                    if (res == 0) {
                        try {
                            int update_res = salaryService.insertOrUpdateDate(employeeMap, salaryDetailMap, salary_data);
                            if (update_res == 0)
                                result = "SUCCESS";
                        } catch (RecordInvalidException e) {
                            e.printStackTrace();
                            result = "INVALID DATA";    //存在无效数据
                        }
                    } else {
                        result = "FILE PARSE FAIL";    //文件解析失败
                    }
                }
            }
        } else {
            result = "EMPTY FILE";
        }

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            response.getWriter().println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @Description: 通过HttpServletRequest获取IP地址
     * @Param: [request]
     * @return: java.lang.String
     * @Author: lcx
     * @Date: 2018/10/11
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

    private static Date dateTransform(String time) {
        String[] times = time.split("-");
        int year = Integer.parseInt(times[0]);
        int month = Integer.parseInt(times[1]);
        Date date = new Date(year - 1900, month - 1, 1);
        return date;
    }
}