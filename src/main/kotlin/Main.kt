
import org.apache.pdfbox.pdmodel.PDDocument
import technology.tabula.ObjectExtractor
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm
import java.io.File

fun main() {
    val fileStr = "src/main/resources/horarios.pdf"
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
            val rows = table.getRows()
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
                        val text = content.getText().replace("\r", " ")
                        println("text:$text ->row:$row col:$column Max X:$maxx Min x:$minx Max y:$maxy Min y:$miny")
                        column++
                    }
                }
            row++
            }
        }
    }
}