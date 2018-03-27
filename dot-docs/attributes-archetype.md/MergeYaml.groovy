
// a) Liest bereits eingepflegt Jira-Tickets aus, wenn 0, dann
//      a) Bereitet Datei vor:
//          Remove Configuration & Storie-Part
// b) Ermittelt alle relevanten Jira-Tickets
// c) Ermittel die noch nicht eingepflegte Tickets b) minus a)
// d) Ergänzt die Releasenotes um die in c) ermittelten Tickets

// TODO: Das selbe auch für nicht gespiegelte TMT-Tickets 
// TODO: Release-Attribute aus Umgebungsvariablen lesen

@Grapes(@Grab(group='org.yaml', module='snakeyaml', version='1.19'))
import org.yaml.snakeyaml.Yaml
import groovy.io.FileType
import groovy.io.FileVisitResult
import groovy.json.*
import groovy.text.*


def fs = File.separator

//Basic Values (TODO: Read from env)
def vShort = "72"
def vFull = "0.7.2"
def piFruit = "Dragenfruit"

def kmType = "ReleaseNotes"
def finalFile = kmType + "-" + vFull + ".yml"

//BasicFolder
def kmRoot = "D:\\KM\\"
def outputDirRoot = "D:\\temp\\KM-merged\\"

//ScopeFolder
def scopeFolder = piFruit + fs + "Release-" + vFull + fs
def scopeRoot = kmRoot + ".KRMA\\scope\\" 
def scope = scopeRoot + scopeFolder


//def scope = scopePath + "Coconut-66b.json"
//KM Input der Jira-Tickets
//def xml = new XmlSlurper().parse(scopePath + 'check_jira_0-7-2.xml')
//def items = xml.depthFirst().findAll { it.name() == 'item' }


//Hier wird dann traversiert
def filePath = kmRoot + "ESB-TrassenProduktPruefenUndErgaenzen" + fs + kmType + fs + finalFile

def yaml = new Yaml()
def file = new File(filePath)
def map = (Map) yaml.load(file.text)

println "${map.keySet()}"

def myStories = map['stories']

for (e in myStories) {
    println e['id']
}


//myStories.each {k,v -> println "${k} => ${v}"}





//parser.load(filePath).each {
//    print it.stories
//}

//List example = parser.load((filePath as File).text)
//example.each{println it.subject}



/* Consuming json (Scope)
def restResponse = new File(scope) //later via Rest-Call
def js = """${restResponse.text}"""
def releaseData = new JsonSlurper().parseText(js)


assert this.args.size() == 2
def kmType = this.args[0]
assert kmType == "Dependencies" | kmType == "ReleaseNotes" | kmType == "Configurations" 
def kmReleaseVersion = this.args[1]
def outputDir = outputDirRoot + piFruit + "-" + kmReleaseVersion

def yaml = new Yaml()
def i = 1

//HTML Ausgabevorbereiten
def kmTitle = "YAML Error Report for ${kmType} in Release ${kmReleaseVersion}"
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


new File(kmRoot).eachDir(
) { dir ->  
    if (!dir.name.startsWith(".")) {
        def filePath = kmRoot + dir.name + fs + kmType + fs + kmType + "-" + kmReleaseVersion + ".yml"
        def file = new File(filePath)
        def chkState = "n/a"
//
        def chkSize = -1


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
        def meldung = myArtefakt.Meldung
        html += "<tr><td>${i}</td><td>${meldung}</td><td>${chkState}</td><td>${dir.name}</td><td>${chkSize}</td></tr>"
        println i++ + "\t" + meldung + "\t" + chkState + "\t" + chkSize + "\t" + dir.name 
    }
}

//generate Report
println "______________________________________________________________________________"
def outputName = outputDir + "\\Check${kmType}-${kmReleaseVersion}.html"
html += htmlClose
def output = new File(outputName)
output.write html
println "Report written into: " + outputName

*/