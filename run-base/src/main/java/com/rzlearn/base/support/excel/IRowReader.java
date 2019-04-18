package com.rzlearn.base.support.excel;

import java.util.List;

/**
 * <p>ClassName:IRowReader</p>
 * <p>Description:</p>
 * @author JiPeigong
 * @date 2019-01-29 09:11:27
 **/
public interface IRowReader {
	/**
	 * 业务逻辑实现方法
	 * 
	 * @param sheetIndex
	 * @param curRow
	 * @param rowlist
	 */
	void getRows(int sheetIndex, int curRow, List<String> rowlist);
}
