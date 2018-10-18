package domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lcx :liu.changxin@qq.com
 * @date 2018/10/7 22:29
 * 功能：用于记录文件上传
 */
public class UploadRecord implements Serializable {
    private int id;
    private String file_name;
    private String file_type;
    private String storage_path;
    private Date data_date;
    private Date upload_date;
    private String upload_ip;
    private String operator;
    private String desc;

    public UploadRecord() {
    }

    public int getId() {
        return id;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getFile_type() {
        return file_type;
    }

    public Date getData_date() {
        return data_date;
    }

    public String getStorage_path() {
        return storage_path;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public String getUpload_ip() {
        return upload_ip;
    }

    public String getOperator() {
        return operator;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public void setData_date(Date data_date) {
        this.data_date = data_date;
    }

    public void setStorage_path(String storage_path) {
        this.storage_path = storage_path;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    public void setUpload_ip(String upload_ip) {
        this.upload_ip = upload_ip;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
