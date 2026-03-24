/**
 * Generic MS-Excel readers for SoapUI / Groovy (Apache POI).
 * Drop poi-*.jar, poi-ooxml-*.jar, and dependencies into SoapUI <install>/bin/ext, then restart.
 *
 * Usage from a Groovy Script test step:
 *   def rows = ExcelReader.readSheetRows(new File("/absolute/path/to/book.xlsx"), 0)
 *   def first = rows[0]
 *   log.info first["subregion"]
 */
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ExcelReader {

  static List<Map<String, String>> readSheetRows(File excelFile, int sheetIndex = 0) {
    def fis = new FileInputStream(excelFile)
    def wb = new XSSFWorkbook(fis)
    try {
      Sheet sheet = wb.getSheetAt(sheetIndex)
      Row header = sheet.getRow(0)
      if (!header) {
        throw new IllegalStateException("Sheet ${sheetIndex} has no header row")
      }
      def fmt = new DataFormatter()
      def columns = []
      header.each { Cell c ->
        if (c != null) {
          columns << fmt.formatCellValue(c).trim()
        }
      }
      def rows = []
      for (int r = 1; r <= sheet.getLastRowNum(); r++) {
        Row row = sheet.getRow(r)
        if (row == null) continue
        def map = [:]
        boolean any = false
        columns.eachWithIndex { String name, int i ->
          Cell cell = row.getCell(i)
          def v = cell == null ? "" : fmt.formatCellValue(cell).trim()
          map[name] = v
          if (v) any = true
        }
        if (any) rows << map
      }
      return rows
    } finally {
      wb.close()
      fis.close()
    }
  }

  static Map<String, String> firstDataRow(File excelFile, int sheetIndex = 0) {
    def all = readSheetRows(excelFile, sheetIndex)
    if (!all) throw new IllegalStateException("No data rows in ${excelFile}")
    return all[0]
  }
}
