import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
//import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class XlsxToCSV {
    static void xlsx(File inputFile, File outputFile){
        // For storing data into CSV files
        StringBuffer data = new StringBuffer();

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            // Get the workbook object for XLSX file
            FileInputStream ExcelFileToRead=new FileInputStream(inputFile);
            Workbook wBook = new XSSFWorkbook(ExcelFileToRead);
            
            // Get first sheet from the workbook 
            Sheet sheet = wBook.getSheetAt(0);
            //XSSFCell cell;
            // Iterate through each rows from first sheet
            int last=sheet.getLastRowNum();
            int first=sheet.getFirstRowNum();
            for (int i=first;i<=last;i++) {
                Row row=sheet.getRow(i);
                // For each row, iterate through each columns
                short lastCell=row.getLastCellNum();
                short firstCell=row.getFirstCellNum();
                for (int j=firstCell;j<=lastCell;j++) {
                    Cell cell= row.getCell(j);
                    
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            data.append(cell.getBooleanCellValue() + ",");

                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            data.append(cell.getNumericCellValue() + ",");

                            break;
                        case Cell.CELL_TYPE_STRING:
                            data.append(cell.getStringCellValue() + ",");
                            break;

                        case Cell.CELL_TYPE_BLANK:
                            data.append("" + ",");
                            break;
                        default:
                            data.append(cell + ",");

                    }
                }
            }
            wBook.close();
            fos.write(data.toString().getBytes());
            fos.close();

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }
}
