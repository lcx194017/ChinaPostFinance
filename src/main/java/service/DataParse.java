package service;

import domain.Employee;
import domain.SalaryDetail;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/9/30 18:02
 */
public class DataParse {
    private static final int EXCEL_NOT_EXIST = -1;
    private static final int INVALID_SHEET = -2;

    public static int ReadExcelData(String filePath, Map<String, Employee> employees,
                                    Map<String, SalaryDetail> salaryDetails) {
        employees.clear();
        salaryDetails.clear();

        Workbook workbook = readExcel(filePath);
        if (workbook == null) {
            return EXCEL_NOT_EXIST;
        }
        int sheet_counts = workbook.getNumberOfSheets();
        for (int i = 0; i < sheet_counts; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            int row_numbers = sheet.getPhysicalNumberOfRows();
            if (row_numbers == 0) continue;
            Row row = sheet.getRow(0);
            int col_numbers = row.getPhysicalNumberOfCells();
            String[] col_names = new String[col_numbers];
            int id_index = Integer.MAX_VALUE;             //记录身份证号所在列
            for (int j = 0; j < col_numbers; j++) {
                col_names[j] = (String) getCellFormatValue(row.getCell(j));
                if (col_names[j].equals("身份证号码"))
                    id_index = j;
            }

            if (id_index == Integer.MAX_VALUE)
                return INVALID_SHEET;

            for (int k = 1; k < row_numbers; k++) {
                row = sheet.getRow(k);
                if (row != null) {
                    String id = (String) getCellFormatValue(row.getCell(id_index));

                    /**排除一个表头占多个数据行的情况*/
                    if (id == "") continue;

                    Employee employee;
                    SalaryDetail salaryDetail;

                    /**if the map has already contain the record of the employee, get it
                     * if not create a new object, then add to the map after reading
                     * */
                    if (employees.containsKey(id))
                        employee = employees.get(id);
                    else
                        employee = new Employee();
                    if (salaryDetails.containsKey(id))
                        salaryDetail = salaryDetails.get(id);
                    else
                        salaryDetail = new SalaryDetail();

                    for (int m = 0; m < col_numbers; m++) {
                        String cellData = (String) getCellFormatValue(row.getCell(m));
                        if (Employee.contents.containsKey(col_names[m])) {
                            int index = Employee.contents.get(col_names[m]);
                            employee.setContents_value(index, cellData);
                        }
                        if (SalaryDetail.contents.containsKey(col_names[m])) {
                            int index = SalaryDetail.contents.get(col_names[m]);
                            salaryDetail.setContents_value(index, cellData);
                        }
                    }

                    /**读取sheet表中的一行后，更新两个map集合*/
                    employee.contents_valueToObject();
                    employees.put(id, employee);
                    salaryDetail.contents_valueToObject();
                    salaryDetails.put(id, salaryDetail);
                }
            }
        }
        return 0;
    }

    private static Workbook readExcel(String filepath) {
        Workbook wb = null;
        if (filepath == null)
            return null;
        String extString = filepath.substring(filepath.lastIndexOf("."));
        try {
            InputStream is = new FileInputStream(filepath);
            if (".xls".equals(extString))
                wb = new HSSFWorkbook(is);
            else if (".xlsx".equals(extString))
                wb = new XSSFWorkbook(is);
            else
                return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    private static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA: {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }
}
