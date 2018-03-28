import groovy.json.*
import groovy.text.*

def restResponse = new File("types.json") 
def js = """${restResponse.text}"""
def data = new JsonSlurper().parseText(js) 

def totalCount = data.size
def count = 0

def frontdelim = "---\n"
println "Start"
data.each {
    def shortDesc = it["Description"].tokenize(".")[0] ?: ''
    
    def content = ""
    if (shortDesc.size() > 0) {
        shortDesc += "."
        content = it["Description"].minus(shortDesc)
    }
    //def shortDesc = it["Description"].tokenize(".")[0] + "."
    //def content = it["Description"].minus(shortDesc) + ""
 
    def md = frontdelim
    md += "Title: " + it["Name"] + "\n"
    md += "Based-On: " + it["Based-On"] + "\n"
    md += "Values-Or-Examples: " + it["EnumValues-Or-Examples"] + "\n"
    md += "Description: " + shortDesc + "\n"
    md += "LastEdit: " + new Date().format( 'yyyy-MM-dd' )  + "\n"
    md += "LastEditor: generator" + "\n"
    md += frontdelim + "\n"
    md += content.trim() + "\n"
    //md += "\n-------------------------------------------<eof>\n\n"
    
    def outputFile = "..\\dot-docs\\Types\\" + it["Name"] + ".md"
    def output = new File(outputFile)
    println count++ + " " + outputFile
    //println md
    output.write md


}

println "-------------------------------------------"
println "total:\t" + totalCount
println "-------------------------------------------"


File file