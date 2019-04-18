package com.rzlearn.base.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.base.support.MessageCode;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>ClassName:EasyExcelUtil</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2019 -03-14 10:36
 */
public class EasyExcelUtil {

    /**
     * Read excel list.
     *
     * @param inputStream the input stream
     * @param sheetNo     the sheet no
     * @param headLineMun the head line mun
     * @return the list
     * @throws IOException the io exception
     */
    public static List<Object> readExcel(InputStream inputStream, int sheetNo, int headLineMun) throws IOException {
        List<Object> dataList = EasyExcelFactory.read(inputStream, new Sheet(sheetNo, headLineMun));
        inputStream.close();
        return dataList;
    }

    /**
     * Read excel list.
     *
     * @param <T>         the type parameter
     * @param inputStream the input stream
     * @param sheetNo     the sheet no
     * @param headLineMun the head line mun
     * @param clazz       the clazz
     * @return the list
     * @throws IOException the io exception
     */
    public static <T extends BaseRowModel> List<T> readExcel(InputStream inputStream, int sheetNo, int headLineMun, Class<T> clazz) {
        List<Object> dataList = EasyExcelFactory.read(inputStream, new Sheet(sheetNo, headLineMun, clazz));
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new BusinessException(MessageCode.OPERATE_FAILED,e.getMessage());
        }
        List<T> resultList = new ArrayList<>(dataList.size());
        for (Object o : dataList) {
            resultList.add((T) o);
        }
        return resultList;
    }

    /**
     * Read larger excel.
     *
     * @param inputStream   the input stream
     * @param sheetNo       the sheet no
     * @param headLineMun   the head line mun
     * @param excelListener the excel listener
     * @throws IOException the io exception
     */
    public void readLargerExcel(InputStream inputStream, int sheetNo, int headLineMun, AnalysisEventListener excelListener) throws IOException {
        EasyExcelFactory.readBySax(inputStream, new Sheet(sheetNo, headLineMun), excelListener);
        inputStream.close();

    }

}
