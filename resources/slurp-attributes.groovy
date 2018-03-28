import groovy.json.*
import groovy.text.*

def restResponse = new File("attributes.json") //later via Rest-Call .parse(URI)
def js = """${restResponse.text}"""
def data = new JsonSlurper().parseText(js) 

def totalCount = data.size

def graphCount = data.count{it["Cat"] == "G"}
def nodeCount = data.count{it["Cat"] == "N"}
def edgeCount = data.count{it["Cat"] == "E"}
def clusterCount = data.count{it["Cat"] == "C"}
def count = 0

def frontdelim = "---\n"
//def md = ""

def catMap = [G:'Graph', N:'Node', E:'Edge', C:'Cluster']
    
new File("Attributes/Graph").mkdirs() 
new File("Attributes/Node").mkdirs() 
new File("Attributes/Edge").mkdirs() 
new File("Attributes/Cluster").mkdirs() 

data.each {
    def shortDesc = it["Description"].tokenize(".")[0] + "."
    def content = it["Description"].minus(shortDesc)
    //if (content.size() < 2) {content = "See description."}
    def categ = catMap[it["Cat"]]
 


    
    def md = frontdelim
    md += "Title: " + it["Name"] + "\n"
    md += "MainCategory: " + categ + "\n"
    md += "Description: " + shortDesc + "\n"
    md += "Type: " + it["Type"] + "\n"
    md += "Default: " + it["Default"] + "\n"
    md += "Min: " + it["Min"] + "\n"
    md += "Restrictions: " + it["Restrictions"] + "\n"
    md += "Examples: " + it["Examples"] + "\n"
    md += "LastEdit: " + new Date().format( 'yyyy-MM-dd' )  + "\n"
    md += "LastEditor: generator" + "\n"
    md += frontdelim + "\n"
    md += content.trim() + "\n"
    //md += "\n-------------------------------------------<eof>\n\n"
    
    def outputFile = "Attributes\\" + categ + "\\" + it["Name"] + ".md"
    def output = new File(outputFile)
    output.write md

    println count++ + " " + outputFile
}

println "-------------------------------------------"
println "total:\t" + totalCount
println "graph:\t" + graphCount
println "node:\t" + nodeCount
println "edge:\t" + edgeCount
println "clstr:\t" + clusterCount
println "-------------------------------------------"


File file
//print md