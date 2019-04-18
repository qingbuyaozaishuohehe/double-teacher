package com.rzlearn.base.support.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * <p>ClassName:Excel2003Reader</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018 -07-19 13:38:33
 */
@Slf4j
public class Excel2003Reader implements HSSFListener {
	private int minColumns = -1;
	private POIFSFileSystem fs;
	private int lastRowNumber;
	private int lastColumnNumber;

	/** Should we output the formula, or the value it has? */
	private boolean outputFormulaValues = true;

	/** For parsing Formulas */
	private SheetRecordCollectingListener workbookBuildingListener;
	/**
	 * excel2003工作薄
	 */
	private HSSFWorkbook stubWorkbook;

    /**
     * Records we pick up as we process
     */
	private SSTRecord sstRecord;
	private FormatTrackingHSSFListener formatListener;

	/**
	 * 表索引
	 */
	private int sheetIndex = -1;
	private BoundSheetRecord[] orderedBSRs;
	private List<BoundSheetRecord> boundSheetRecords = new ArrayList<BoundSheetRecord>();

	/**
	 * For handling formulas with string results
	 */
	private int nextRow;
	private int nextColumn;
	private boolean outputNextStringRecord;
	/**
	 * 当前行
	 */
	private int curRow = 0;
	/**
	 * 存储行记录的容器
	 */
	private List<String> rowlist = new ArrayList<String>();
	@SuppressWarnings("unused")
	private String sheetName;

	private IRowReader rowReader;

	/**
	 * Sets row reader.
	 *
	 * @param rowReader the row reader
	 */
	public void setRowReader(IRowReader rowReader) {
		this.rowReader = rowReader;
	}

	/**
	 * Instantiates a new Excel 2003 reader.
	 */
	public Excel2003Reader() {
	}

	/**
	 * Instantiates a new Excel 2003 reader.
	 *
	 * @param rowReader the row reader
	 */
	public Excel2003Reader(IRowReader rowReader) {
		this.rowReader = rowReader;
	}

	/**
	 * 遍历excel下所有的sheet
	 *
	 * @param fileName the file name
	 * @throws IOException
	 */
	public void process(String fileName) {
		try {
			process(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Process.
	 *
	 * @param stream the stream
	 */
	public void process(InputStream stream) {
		try {
			this.fs = new POIFSFileSystem(stream);
			MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
			formatListener = new FormatTrackingHSSFListener(listener);
			HSSFEventFactory factory = new HSSFEventFactory();
			HSSFRequest request = new HSSFRequest();
			if (outputFormulaValues) {
				request.addListenerForAllRecords(formatListener);
			} else {
				workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
				request.addListenerForAllRecords(workbookBuildingListener);
			}
			factory.processWorkbookEvents(request, fs);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * HSSFListener 监听方法，处理 Record
	 */
	@Override
	public void processRecord(Record record) {
		int thisRow = -1;
		int thisColumn = -1;
		String thisStr = null;
		String value;
		switch (record.getSid()) {
			case BoundSheetRecord.sid:
				boundSheetRecords.add((BoundSheetRecord)record);
				break;
			case BOFRecord.sid:
				BOFRecord br = (BOFRecord)record;
				if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
					// 如果有需要，则建立子工作薄
					if (workbookBuildingListener != null && stubWorkbook == null) {
						stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
					}

					sheetIndex++;
					if (orderedBSRs == null) {
						orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
					}
					sheetName = orderedBSRs[sheetIndex].getSheetname();
				}
				break;

			case SSTRecord.sid:
				sstRecord = (SSTRecord)record;
				break;

			case BlankRecord.sid:
				BlankRecord brec = (BlankRecord)record;
				thisRow = brec.getRow();
				thisColumn = brec.getColumn();
				thisStr = "";
				rowlist.add(thisColumn, thisStr);
				break;
			case BoolErrRecord.sid: // 单元格为布尔类型
				BoolErrRecord berec = (BoolErrRecord)record;
				thisRow = berec.getRow();
				thisColumn = berec.getColumn();
				thisStr = berec.getBooleanValue() + "";
				rowlist.add(thisColumn, thisStr);
				break;

			case FormulaRecord.sid: // 单元格为公式类型
				FormulaRecord frec = (FormulaRecord)record;
				thisRow = frec.getRow();
				thisColumn = frec.getColumn();
				if (outputFormulaValues) {
					if (Double.isNaN(frec.getValue())) {
						// Formula result is a string
						// This is stored in the next record
						outputNextStringRecord = true;
						nextRow = frec.getRow();
						nextColumn = frec.getColumn();
					} else {
						thisStr = formatListener.formatNumberDateCell(frec);
					}
				} else {
					thisStr = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) + '"';
				}
				rowlist.add(thisColumn, thisStr);
				break;
            // 单元格中公式的字符串
			case StringRecord.sid:
				if (outputNextStringRecord) {
					// String for formula
					thisRow = nextRow;
					thisColumn = nextColumn;
					outputNextStringRecord = false;
				}
				break;
			case LabelRecord.sid:
				LabelRecord lrec = (LabelRecord)record;
				curRow = thisRow = lrec.getRow();
				thisColumn = lrec.getColumn();
				value = lrec.getValue().trim();
				value = "".equals(value) ? " " : value;
				this.rowlist.add(thisColumn, value);
				break;
            // 单元格为字符串类型
			case LabelSSTRecord.sid:
				LabelSSTRecord lsrec = (LabelSSTRecord)record;
				curRow = thisRow = lsrec.getRow();
				thisColumn = lsrec.getColumn();
				if (sstRecord == null) {
					rowlist.add(thisColumn, " ");
				} else {
					value = sstRecord.getString(lsrec.getSSTIndex()).toString().trim();
					value = "".equals(value) ? " " : value;
					rowlist.add(thisColumn, value);
				}
				break;
            // 单元格为数字类型
			case NumberRecord.sid:
				NumberRecord numrec = (NumberRecord)record;
				curRow = thisRow = numrec.getRow();
				thisColumn = numrec.getColumn();
				value = formatListener.formatNumberDateCell(numrec).trim();
				value = "".equals(value) ? " " : value;
				// 向容器加入列值
				rowlist.add(thisColumn, value);
				break;
			default:
				break;
		}

		// 遇到新行的操作
		if (thisRow != -1 && thisRow != lastRowNumber) {
			lastColumnNumber = -1;
		}

		// 空值的操作
		if (record instanceof MissingCellDummyRecord) {
			MissingCellDummyRecord mc = (MissingCellDummyRecord)record;
			curRow = thisRow = mc.getRow();
			thisColumn = mc.getColumn();
			rowlist.add(thisColumn, " ");
		}

		// 更新行和列的值
		if (thisRow > -1) {
			lastRowNumber = thisRow;
		}
		if (thisColumn > -1) {
			lastColumnNumber = thisColumn;
		}

		// 行结束时的操作
		if (record instanceof LastCellOfRowDummyRecord) {
			if (minColumns > 0) {
				// 列值重新置空
				if (lastColumnNumber == -1) {
					lastColumnNumber = 0;
				}
			}
			lastColumnNumber = -1;
			if (rowReader != null && !rowlist.isEmpty()) { // 每行结束时， 调用getRows() 方法
				try {
					rowReader.getRows(sheetIndex, curRow, rowlist);
				} catch (Exception e) {
					log.error("", "", e);
				}
			}
			// 清空容器
			rowlist.clear();
		}
	}
}
