package dao;

import domain.UploadRecord;

import java.util.List;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/7 22:32
 */
public interface UploadRecordDao {

    /**将上传的文件信息记录入库*/
    int insertUploadRecord(UploadRecord uploadRecord);

    /**获取所有的文件上传记录*/
    List<UploadRecord> getAllUploadRecords();

    /**查看某个文件是否已经存在*/
    int queryFileExist(String file_name, String file_type);
}
