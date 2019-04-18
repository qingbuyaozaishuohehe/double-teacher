package com.rzlearn.setting.common.util;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * <p>ClassName:PoiUtil</p>
 * <p>Description:</p>
 * @author WuRangshui
 * @date 2019-01-29 09:09:15
 **/
@Component
public class PoiUtil {

    /**
     * 格式化 number为整
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0");

    /**
     * 格式化分比格式，后面不足2位的用0补齐
     */
    private static final DecimalFormat DECIMAL_FORMAT_PERCENT = new DecimalFormat("##.00%");

    private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy/MM/dd");

    /**
     * 格式化科学计数器
     */
    private static final DecimalFormat DECIMAL_FORMAT_NUMBER  = new DecimalFormat("0.00E000");

    /**
     * 小数匹配
     */
    private static final Pattern POINTS_PATTERN = Pattern.compile("0.0+_*[^/s]+");

    public static String getCellFormatValue(Cell cell) {
        Object value = null;
        if(cell==null){
            return "";
        }
        switch (cell.getCellTypeEnum()) {
            case _NONE:
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                // 日期
                if(DateUtil.isCellDateFormatted(cell)){
                    value = FAST_DATE_FORMAT.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
                } else if("@".equals(cell.getCellStyle().getDataFormatString())
                        || "General".equals(cell.getCellStyle().getDataFormatString())
                        || "0_ ".equals(cell.getCellStyle().getDataFormatString())){
                    value = DECIMAL_FORMAT.format(cell.getNumericCellValue());
                    // 正则匹配小数类型
                } else if(POINTS_PATTERN.matcher(cell.getCellStyle().getDataFormatString()).matches()){
                    value = cell.getNumericCellValue();
                    // 科学计数
                } else if("0.00E+00".equals(cell.getCellStyle().getDataFormatString())){
                    value = cell.getNumericCellValue();
                    value = DECIMAL_FORMAT_NUMBER.format(value);
                    // 百分比
                } else if("0.00%".equals(cell.getCellStyle().getDataFormatString())){
                    value = cell.getNumericCellValue();
                    value = DECIMAL_FORMAT_PERCENT.format(value);
                    // 分数
                } else if("# ?/?".equals(cell.getCellStyle().getDataFormatString())){
                    value = cell.getNumericCellValue();
                } else {
                    // 货币
                    value = cell.getNumericCellValue();
                    value = DecimalFormat.getCurrencyInstance().format(value);
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                // value = ",";
                break;
            default:
                value = cell.toString();
        }
        if(value==null){
            return "";
        }
        return value.toString();
    }


    public static int getMergedRegionLastRow(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return lastRow;
                }
            }
        }

        return row;
    }

}
