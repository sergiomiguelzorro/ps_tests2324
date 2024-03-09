import org.apache.pdfbox.pdmodel.PDDocument
import technology.tabula.ObjectExtractor
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm
import java.io.File

data class Discipline(val name: String, val coordinates: Coordinates)
data class Day(val name: String, val coordinates: Coordinates)
data class Hour(val name: String, val coordinates: Coordinates)

data class Coordinates(val minX: Double, val maxX: Double, val minY: Double, val maxY: Double)

fun main() {
    val fileStr = "src/main/resources/horarios.pdf"
    val file = File(fileStr)
    val document = PDDocument.load(file)
    val sea = SpreadsheetExtractionAlgorithm()
    val pi = ObjectExtractor(document).extract()
    while (pi.hasNext()) { // Check if there's at least one page
        println("###################### NEW PAGE #######################")
        val page = pi.next()
        val tables = sea.extract(page)
        //println("tables found:${tables.size}")
        val disciplines = mutableListOf<Discipline>()
        val days = mutableListOf<Day>()
        val hours = mutableListOf<Hour>()

        // Guardar as coordenadas das disciplinas, dias da semana e horas
        for (table in tables) {
            //println("---------------new table ${tables.indexOf(table)}----------")
            val rows = table.getRows()
            var disciplineName: String?
            var dayName: String?
            var hourName: String?

            // iterate over the rows of the table
            for (cells in rows) {
                // iterate over the cells of the row
                for (content in cells) {
                    if (!content.getText().isNullOrBlank()) {
                        val text = content.getText().replace("\r", " ")
                        val coordinates = Coordinates(content.getMinX(), content.getMaxX(), content.getMinY(), content.getMaxY())

                        when {
                            text.contains(",") -> {
                                disciplineName = text
                                disciplines.add(Discipline(disciplineName, coordinates))
                            }
                            text.contains(Regex("(Segunda|Terça|Quarta|Quinta|Sexta|Sábado|Domingo)")) -> {
                                dayName = text
                                days.add(Day(dayName, coordinates))
                            }
                            text.matches(Regex("\\d{2}:\\d{2} - \\d{2}:\\d{2}")) -> {
                                hourName = text
                                hours.add(Hour(hourName, coordinates))
                            }
                        }
                    }
                }
            }
        }

        // Cruzar as coordenadas das disciplinas com as dos dias da semana e horários
        var initialTime: String? = null
        var endTime: String? = null
        for (discipline in disciplines) {
            for (day in days) {
                if (discipline.coordinates.maxX == day.coordinates.maxX && discipline.coordinates.minX == day.coordinates.minX ) {
                    for (hour in hours) {
                        if (discipline.coordinates.minY >= hour.coordinates.minY) {
                            initialTime=hour.name.substring(0,5)
                        }
                        if (discipline.coordinates.maxY >= hour.coordinates.maxY) {
                            endTime=hour.name.substring(8,13)
                        }
                    }
                    println("Disciplina: ${discipline.name} - Dia: ${day.name} - Horário: $initialTime - $endTime" )
                }
            }
        }
    }
}

