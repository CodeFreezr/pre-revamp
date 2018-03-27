@Grapes(@Grab(group='org.yaml', module='snakeyaml', version='1.19'))
import org.yaml.snakeyaml.Yaml
import groovy.io.FileType
import groovy.io.FileVisitResult
import groovy.json.*
import groovy.text.*

def fs = File.separator //To be platform independent

//initial Atomic Settings
def env = System.getenv()
def vShort = env['nxt_vShort']
def vFull = env['nxt_vFull']
def piFruit = env['nxt_piFruit']
def kmRoot = env['nxt_kmRoot']
def scopeRoot = env['nxt_scopeRoot']

//combined values
def scopePath = scopeRoot + fs + piFruit + fs + "Release-" + vFull
def outputDir = scopePath + fs + "YamlCheck"
def scope = scopePath + fs + "release-check" + vShort + ".json"
def cssjsDir = kmRoot + fs + ".KRMA\\templates\\cssjs"

//Consuming json 
def restResponse = new File(scope) //later via Rest-Call
def js = """${restResponse.text}"""
def releaseData = new JsonSlurper().parseText(js)

def kmType = this.args[0]
assert kmType == "Dependencies" | kmType == "ReleaseNotes" | kmType == "Configurations" 

def kmReleaseVersion = vFull
def yaml = new Yaml()

def kmTitle = "YAML Error Report for ${kmType} in Release ${kmReleaseVersion}"
println kmTitle

//HTML Ausgabevorbereiten
def html = """
<html>
    <head>
        <title>${kmTitle}</title>
        <link rel='stylesheet' href='styles.css'>
        <script src='sorttable.js'></script>
    </head>
    <body>
        <div class='headnavi'>
            <h2>${kmTitle}</h2>
            <a href='CheckReleaseNotes-${kmReleaseVersion}.html' class='Maintab'>ReleaseNotes</a> - 
            <a href='CheckDependencies-${kmReleaseVersion}.html' class='Maintab'>Dependencies</a> - 
            <a href='CheckConfigurations-${kmReleaseVersion}.html' class='Maintab'>Configurations</a>
        </div>
        <table class='sortable'><thead><tr><th>No</th><th>Meld</th><th>Status</th><th>Component</th><th>Size</th></tr></thead>

"""

//Pfadanalyse und tr Preparing

def htmlClose = "</table></bod></html>"
def issues = ""

def i = 1 //Count all
def n = 1 //Count Problems
new File(kmRoot).eachDir(
) { dir ->  
    if (!dir.name.startsWith(".")) {
        def filePath = kmRoot + fs + dir.name + fs + kmType + fs + kmType + "-" + kmReleaseVersion + ".yml"
        def file = new File(filePath)
        def chkState = "n/a"
        def chkSize = -1
        def singleLine = ""

        if (file.exists() & file.canRead()) {
            chkState = "ok"
            chkSize = file.length()
            try {
                def map = (Map) yaml.load(file.text) //check for correct YAML-Syntax
            } catch (all) {
                chkState = "syntax"
            }
        }
        def myArtefakt = releaseData.find { it.Artefakt == dir.name }
        def meldung = ""
        if (myArtefakt != null) {
            meldung = myArtefakt.Meldung
        } else {
            meldung = "!!!"
        }
        html += "<tr><td>${i}</td><td>${meldung}</td><td>${chkState}</td><td>${dir.name}</td><td>${chkSize}</td></tr>"
        singleLine = "\t" + meldung + "\t" + chkState + "\t" + chkSize + "\t" + dir.name 
        println i++ + singleLine
        //Collect Issues:
        if (meldung == "U" & chkState == "n/a") {
            issues = issues + n++ + singleLine + "\n"
        }
    }
}

//generate Report
println "______________________________________________________________________________"

new File(outputDir).mkdirs()
def outputName = outputDir + fs + "Check${kmType}-${kmReleaseVersion}.html"
html += htmlClose
def output = new File(outputName)
output.write html

//copy cssjs  <copy file="myfile.txt" todir="../some/other/dir"/>
def ant = new AntBuilder()
//ant.copy(file: mycss, todir: outputDir)
ant.copy(todir: outputDir) {fileset(dir: cssjsDir) }

println "Report written into: "
println outputName
println "______________________________________________________________________________"
println "Announced, but no Informations:"
println issues
println "______________________________________________________________________________"
println "Done \n\n"
