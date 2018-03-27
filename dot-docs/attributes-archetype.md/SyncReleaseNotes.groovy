/*
Fügt Jira-Informationen in Artefakt-Yaml zusammen.
Input1: Release.json (based on ReleaseChecks.xls)
Input2: Jira-XML aller geflaggten Tickets (Filtervorbereitung, siehe KM\.KRMA\shortcuts\JiraProjectsFilter.xlsx)

*/

import groovy.json.*
import groovy.text.*

/*
def env = System.getenv()
println "\n\n--------------------------------------------------------------------------------"
println "Umgebungsvariablen:"
println "nxt_piFruit:\t" + env['nxt_piFruit']
println "nxt_vFull:\t" + env['nxt_vFull']
println "nxt_vShort:\t" + env['nxt_vShort']
println "nxt_kmRoot:\t" + env['nxt_kmRoot']
println "nxt_scopeRoot:\t" + env['nxt_scopeRoot']
println "--------------------------------------------------------------------------------"
*/
//RM Version:
//richtig: def ReleaseVersion = env['nxt_vFull']
def ReleaseVersion = "0.7.2"

//KM Scope
def scopePath = "D:\\KM\\.KRMA\\scope\\Dragonfruit\\Release-0.7.2\\"

//KM Output:
def targetPath = "D:\\temp\\KM72\\"

//KM Input der Meldungen im Release-Check
def scope = scopePath + "release-check72c.json"

//KM Input der Jira-Tickets
def xml = new XmlSlurper().parse(scopePath + 'check_jira_0-7-2.xml')
def items = xml.depthFirst().findAll { it.name() == 'item' }

//Consuming json 
def restResponse = new File(scope) //later via Rest-Call
def js = """${restResponse.text}"""
def data = new JsonSlurper().parseText(js) 

//Map Mappings
def signed = "Meldung"
def cName = "cmpName"
def pName = "Artefakt"
def trunk = "SVN"
def ver = "cmpVer"
def atype = "cmpType"
def tech = "Technologie"
def ue ="UE"; def jPrefix = "M13UE"

//Counting  Stuff
def totalCount = data.size
def newCount = data.count{it[signed] == "C"}
def updateCount = data.count{it[signed] == "U"}
def deleteCount = data.count{it[signed] == "D"}
def announcedCount = newCount + updateCount + deleteCount
def storiesCount = items.size()

//processing Jira-Data
def counter = 1
def countStoriesWithLabels = 1
def countArtefactsWithStories = 1
def noStories = "";
def noStoriesCount = 0;

println "\n\n--------------------------------------------------------------------------------"
println "Writing Yaml-Files for " + ReleaseVersion + ":"

data.each { 
 if(it[signed] == "U" || it[signed] == "C" || it[signed] == "D") { //only signed packages
    //retrieving Stories from jira-xml
    def ArtefactStories = xml.depthFirst().findAll { a -> a.name() == 'item' && a.labels.text().contains(it[pName])} 
    def storiesForThisArtefact = ArtefactStories.size
    def myStories = ""
    if ( storiesForThisArtefact > 0) {
        myStories = myStories + "stories:\n"

        def outDir = targetPath + it[pName] + "\\ReleaseNotes"
        new File(outDir).mkdirs()
        def outputFile = outDir + "\\ReleaseNotes-" + ReleaseVersion + ".yml"
        def output = new File(outputFile)

        println counter++ + ". Write " + storiesForThisArtefact + " Stories into \t" + outputFile
        ArtefactStories.each { b -> 
                myStories = myStories + " - id: " + b.key.text() + "\n"   
                myStories = myStories + "   summary: \'" + b.summary.text() + "\' \n" 
                myStories = myStories + "   link: " + b.link.text() + "\n" 
                myStories = myStories + "   priority: " + b.priority.text() + "\n" 
                countStoriesWithLabels++
        }

        def keyValue = [
            'cmpName' : it[cName],
            'version' : it[ver],
            'type' : it[atype],
            'technology' : it[tech],
            'jiraProject' : jPrefix + it[ue].toUpperCase(),
            'pkgName' : it[pName],
            'trunkPath' : it[trunk],
            'stories' : myStories,
            'configs' : "configurations: [Will be inserted here from jira after PO Approval]",
            'defects' : "tbd",
        ]

        def templateFile = new File("ReleaseNotesSync.template")
        def engine = new SimpleTemplateEngine()
        def template = engine.createTemplate(templateFile)
        def writable = template.make(keyValue)

        countArtefactsWithStories++
    } else {
        noStories = noStories  + noStoriesCount + ". " + it[pName] + "\n"
        noStoriesCount++
    }
 }
}
println "--------------------------------------------------------------------------------"
println "Can't find stories for the following artefacts in " + ReleaseVersion + ":"
println noStories
//Resuming Scope:
println "--------------------------------------------------------------------------------"
println "Artefact Numbers in " + ReleaseVersion + ":"
println totalCount + " Artefact in total"
println announcedCount + " Announced"
println newCount + " New"
println updateCount + " Update"
println deleteCount + " Remove"
println "${totalCount - announcedCount} signed out" 
println "--------------------------------------------------------------------------------"
println "Stories Numbers in " + ReleaseVersion + ":"
println storiesCount + " Stories (Done/Approved)"
println countStoriesWithLabels-1 + " Stories of them are assigned to " + countArtefactsWithStories-1 + " artefacts"