import com.spire.pdf.PdfDocument
import com.spire.pdf.PdfPageBase
import com.spire.pdf.tables.PdfTable
import com.spire.pdf.utilities.PdfTableExtractor
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import technology.tabula.ObjectExtractor
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm
import java.io.File

fun main(args: Array<String>) {
    val fileStr = "C:\\Users\\sergio.zorro\\IdeaProjects\\PS_test_gradle\\src\\main\\resources\\horarios.pdf"
    val file = File(fileStr)
    val document = PDDocument.load(file)
    val sea = SpreadsheetExtractionAlgorithm()
    val pi = ObjectExtractor(document).extract()
    while (pi.hasNext()) {
        println("###################### NEW PAGE #######################")
        val page = pi.next()
        val tables = sea.extract(page)
        println("tables found:${tables.size}")
        for (table in tables) {
            println("---------------new table ${tables.indexOf(table)}----------")
            val rows = table.getRows();
            var row=0
            // iterate over the rows of the table
            for (cells in rows) {
                var column=0
                // print all column-cells of the row plus linefeed
                for (content in cells) {
                    if (!content.getText().isNullOrBlank()){
                        val maxx = content.getMaxX()
                        val minx = content.getMinX()
                        val maxy=content.getMaxY()
                        val miny=content.getMinY()
                        val text = content.getText().replace("\r", " ");
                        println("text:$text ->row:$row col:$column Max X:$maxx Min x:$minx Max y:$maxy Min y:$miny")
                        column++
                    }
                }
            row++
            }
        }
    }


    //println(text)
}