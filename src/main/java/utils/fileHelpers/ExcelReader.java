package utils.fileHelpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * @author r0ukcb
 *	ExcelReader class is a layer of abstraction over the Apache POI WorkbookFactory
 *	Helps interact with an excel file
 */
public class ExcelReader {
	
	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	protected String filePath;
	protected Workbook workbook;
	protected Sheet sheet;
	DataFormatter dataFormatter = new DataFormatter();
	
	/**
	 * Constructor to set the workbook to interact with any Excel File in order to read
	 * @param filePath - complete file path
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ExcelReader(String filePath) {
		try {
			workbook = WorkbookFactory.create(new File(filePath));
			LOGGER.fine("ExcelReader Constructor initiated: set the wookbook");
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			LOGGER.warning("EncryptedDocumentException|InvalidFormatException|IOException: could not create workbook");
			e.printStackTrace();
		}
	}
	
	/**
	 * getter for excel sheet
	 * @return sheet
	 */
	public Sheet getExcelSheet() {
		LOGGER.fine("returning sheet");
		return sheet;
	}
	
	/**
	 * sets the excel sheet
	 * @param sheetNumber - sheet number starting from 0
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void setExcelSheet(int sheetNumber) {
		sheet = workbook.getSheetAt(sheetNumber);
		LOGGER.fine("sheet set to "+sheetNumber+" using sheetNumber");
	}
	
	/**
	 * sets the excel sheet
	 * @param sheetName - sheet name
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void setExcelSheet(String sheetName) {
		sheet = workbook.getSheet(sheetName);
		LOGGER.fine("sheet set to "+sheetName+" using sheetName");
	}
	
	/**
	 * returns an arraylist with all the values from a particular column
	 * @param colNumber - column number starting with 0
	 * @return arraylist from a column in excel
	 */
	public ArrayList<String> getColumnAsArray(int colNumber) {
        ArrayList<String> columnValues = new ArrayList<String>();
        LOGGER.fine("created an arraylist to save the values to");
        LOGGER.fine("iterating over rows for column number: "+colNumber);
		for (Row row: sheet) {
        	Cell cell = row.getCell(colNumber);
            String cellValue = dataFormatter.formatCellValue(cell);
            LOGGER.fine("cell value read "+cellValue);
            columnValues.add(cellValue);
            LOGGER.fine("added cell value to the arrayList to be returned");
        }
		LOGGER.fine("returning columnValues as an array of strings");
		LOGGER.fine("columnValues: \n"+columnValues);
		return columnValues;
	}
	
	/**
	 * returns total number of not hidden columns
	 * @return int total unhidden columns
	 */
	public int getTotalNumberOfNonHiddenColumns(int rowNum) {
		Row row = sheet.getRow(rowNum);
		int cellNumber = 0;
		LOGGER.fine("iterating over the cells for row number "+rowNum);
		for (Cell cell: row) {
			if (!sheet.isColumnHidden(cell.getColumnIndex()) && dataFormatter.formatCellValue(cell) != "") {
				cellNumber++;
				LOGGER.fine("found non hidden cell number, total cells  "+cellNumber);
			}		
		}
		LOGGER.fine("returning total number of non hidden columns, "+cellNumber+" for row: "+rowNum);
		return cellNumber;
	}
	
	/**
	 * returns index of each column that is not hidden
	 * @param totalNonHiddenColumns
	 * @return
	 */
	public int[] getNonHiddenColumnsIndexes(int totalNonHiddenColumns, int rowNum) {
		int[] nonHiddenColumns = new int[totalNonHiddenColumns];
		LOGGER.fine("created an array of ints for all the non hidden column numbers");
		Row row = sheet.getRow(rowNum);
		int nonHiddenColumnsIndex = 0;
		LOGGER.fine("iterating over the cells for row number "+rowNum);
		for (Cell cell: row) {
			if (!sheet.isColumnHidden(cell.getColumnIndex()) && dataFormatter.formatCellValue(cell) != "") {
				nonHiddenColumns[nonHiddenColumnsIndex] = cell.getColumnIndex();
				LOGGER.fine("found non hidden column: "+cell.getColumnIndex());
				nonHiddenColumnsIndex++;
			}
		}
		LOGGER.fine("returning non hidden column indexes");
		LOGGER.fine("non hidden columns : \n"+nonHiddenColumns);
		return nonHiddenColumns;
	}
	
	/**
	 * returns the value of a cell provided the row and column index
	 * @param rowIndex - row index
	 * @param colIndex - column index
	 * @return - column value formated
	 */
	public String getCellValue(int rowIndex, int colIndex) {
		Row row = sheet.getRow(rowIndex);
		LOGGER.fine("set the row to: "+rowIndex);
		Cell cell = row.getCell(colIndex);
		LOGGER.fine("set the column to: "+colIndex+" for the row");
		String cellValue = dataFormatter.formatCellValue(cell);
		LOGGER.fine("returning cell value: "+cellValue+" for row: "+rowIndex+" and column: "+colIndex);
		return cellValue;
	}
	
	public String getCellValueAsFloat(int rowIndex, int colIndex) {
		Row row = sheet.getRow(rowIndex);
		Cell cell = row.getCell(colIndex);
		System.out.println(cell.getColumnIndex()+"\t"+cell.getRowIndex());
		float cellValue = (float) cell.getNumericCellValue();
		System.out.println(cellValue);
		String formattedCellValue = String.format("%.01f", cellValue);
		System.out.println(formattedCellValue);
		return formattedCellValue;
	}
}
