import com.spire.pdf.PdfDocument
import com.spire.pdf.PdfPageBase
import com.spire.pdf.tables.PdfTable
import com.spire.pdf.utilities.PdfTableExtractor
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
    val fileStr = "C:\\Users\\sergio.zorro\\IdeaProjects\\PS_test_gradle\\src\\main\\resources\\horarios.pdf"
//    val file = File(fileStr)
//    val document = PDDocument.load(file)
//    val stripper = PDFTextStripper()
//    stripper.sortByPosition = true
//    val text = stripper.getText(document)

    val doc = PdfDocument()
    doc.loadFromFile(fileStr)
    val extractor = PdfTableExtractor(doc)

    for (pageIndex in 0 until doc.pages.count) {
        val tableLists = extractor.extractTable(pageIndex)
        if (tableLists != null && tableLists.isNotEmpty()) {
            for (table in tableLists) {
                for (i in 0 until table.rowCount) {
                    for (j in 0 until table.columnCount) {
                        val text = table.getText(i, j)
                        println("i:$i,j:$j")
                        println(text)

                    }

                }
            }
        }
    }

    //println(text)
}