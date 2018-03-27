import groovy.json.*
import groovy.text.*

//RM Version:
def ReleaseVersion = "Initial-Coconut-66"

//KM Scope
def scopePath = "D:\\KM\\.KRMA\\scope\\"

//KM Output:
def targetPath = "D:\\KM\\"

//Consuming json 
def restResponse = new File("${scopePath}Coconut-66.json") //later via Rest-Call .parse(URI)
def js = """${restResponse.text}"""
def data = new JsonSlurper().parseText(js) 

//Map Mappings
def signed = "Meldung"
def cName = "cmpName"
def pName = "Artefakt"
def trunk = "Trunk"
def ver = "cmpVer"
def atype = "cmpType"
def tech = "Technologie"
def ue ="UE"; def jPrefix = "M13UE"

//Counting Scope Stuff
def totalCount = data.size
def newCount = data.count{it[signed] == "C"}
def updateCount = data.count{it[signed] == "U"}
def deleteCount = data.count{it[signed] == "D"}
def announcedCount = newCount + updateCount + deleteCount

//processing Data
def counter = 1
data.each { 
 if(it[signed] == "U" || it[signed] == "C" || it[signed] == "D") { //only signed packages

    def outDir = targetPath + it[pName] + "\\ReleaseNotes"
    new File(outDir).mkdirs()
    def outputFile = outDir + "\\ReleaseNotes-" + ReleaseVersion + ".yml"
    def output = new File(outputFile)
    println counter++ + ". Write Yaml to " + outputFile

    def keyValue = [
        'cmpName' : it[cName],
        'version' : it[ver],
        'type' : it[atype],
        'technology' : it[tech],
        'jiraProject' : jPrefix + it[ue],
        'pkgName' : it[pName],
        'trunkPath' : it[trunk],
        'stories' : "stories: [Will be inserted here from jira after PO Approval]",
        'configs' : "configurations: [Will be inserted here from jira after PO Approval]",
        'defects' : "tbd",
    ]

    def templateFile = new File("ReleaseNotes.template")
    def engine = new SimpleTemplateEngine()
    def template = engine.createTemplate(templateFile)
    def writable = template.make(keyValue)

    //println writable
    output.write writable.toString()

 }
}

//Resuming Scope:
println "--------------------------------------------------------------------------------"
println "Scope Numbers:"
println totalCount + " Packages"
println announcedCount + " Announced"
println newCount + " New"
println updateCount + " Update"
println deleteCount + " Remove"
println "${totalCount - announcedCount} signed out" 