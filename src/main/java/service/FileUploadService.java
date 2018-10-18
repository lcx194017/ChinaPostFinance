package service;

import dao.UploadRecordDao;
import domain.UploadRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author lcx :liu.changxin@qq.com
 * @date 2018/10/10 22:00
 */
@Service
public class FileUploadService {

    private UploadRecordDao uploadRecordDao;

    @Autowired
    public void setUploadRecordDao(UploadRecordDao uploadRecordDao) {
        this.uploadRecordDao = uploadRecordDao;
    }

    @Transactional(readOnly = true)
    public int validateFile(String file_type, Date date) {
        return uploadRecordDao.queryDepartmentMonthExist(file_type, date);
    }

    @Transactional
    public int uploadAndInsertRecord(String type, String path, Date date, MultipartFile file, String IP, String description) {

        String filename = file.getOriginalFilename();
        File filepath = new File(path, filename);

        //判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }

        // 将上传文件保存到一个目标文件当中，同时在数据库插入一条记录
        try {
            file.transferTo(new File(path + File.separator + filename));
            UploadRecord uploadRecord = new UploadRecord();
            uploadRecord.setFile_name(filename);
            uploadRecord.setFile_type(type);
            uploadRecord.setData_date(date);
            uploadRecord.setStorage_path(path + filename);
            uploadRecord.setUpload_date(new Date());
            uploadRecord.setUpload_ip(IP);
            uploadRecord.setDesc(description);
            return uploadRecordDao.insertUploadRecord(uploadRecord);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
