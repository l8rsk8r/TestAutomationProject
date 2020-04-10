package tests.orphans;

import common.BaseTest;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utils.CollectionsHelper;
import utils.fileHelpers.ExcelReader;

import java.io.IOException;
import java.util.ArrayList;

public class InnCodesComparisonQa extends BaseTest {

	String userDir;
	ExcelReader excelReader; 
	CollectionsHelper collectionsHelper = new CollectionsHelper();
	
	private InnCodesComparisonQa() throws Exception {
		userDir  = System.getProperty("user.dir");
		excelReader = new ExcelReader(userDir+"/src/test/resources/innCodesComparisonQa/incodeComparison.xlsx");
	}
	
	public void script() throws EncryptedDocumentException, InvalidFormatException, IOException {
		excelReader.setExcelSheet("Sheet1");
		ArrayList<String> mstrValues = excelReader.getColumnAsArray(0);
		System.out.println(mstrValues);
		ArrayList<String> tablueaValues = excelReader.getColumnAsArray(1);
		System.out.println(tablueaValues);
		ArrayList<String> databaseValues = excelReader.getColumnAsArray(2);
		System.out.println(databaseValues);
		
		ArrayList<String> tableauValues_mstrValues = collectionsHelper.removeAllFromArrayList(tablueaValues, mstrValues);
		System.out.println("the missing values from the mstr row if compared against tableau are: ");
		System.out.println(tableauValues_mstrValues);
		
		ArrayList<String> mstrValues_tableauValues = collectionsHelper.removeAllFromArrayList(mstrValues, tablueaValues);
		System.out.println("the missing values from the tableau column if compared against mstr are: ");
		System.out.println(mstrValues_tableauValues);
		
		ArrayList<String> databaseValues_mstrValues = collectionsHelper.removeAllFromArrayList(databaseValues, mstrValues);
		System.out.println("the missing values from the mstr row if compared against database are: ");
		System.out.println(databaseValues_mstrValues);
		
		ArrayList<String> mstrValues_databaseValues = collectionsHelper.removeAllFromArrayList(mstrValues, databaseValues);
		System.out.println("the missing values from the database column if compared against mstr are: ");
		System.out.println(mstrValues_databaseValues);
		
		ArrayList<String> databaseValues_tableauValues = collectionsHelper.removeAllFromArrayList(databaseValues, tablueaValues);
		System.out.println("the missing values from the tableau row if compared against database are: ");
		System.out.println(databaseValues_tableauValues);
		
		ArrayList<String> tableauValues_databaseValues = collectionsHelper.removeAllFromArrayList(tablueaValues, databaseValues);
		System.out.println("the missing values from the database column if compared against tableau are: ");
		System.out.println(tableauValues_databaseValues);
		
	}
	
	public static void main(String[] args) throws Exception {
		InnCodesComparisonQa icq = new InnCodesComparisonQa();
		icq.script();
	}
	
}
