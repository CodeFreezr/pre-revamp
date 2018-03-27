/*
Input1: Artefakt / Komponenten Meldungen (json)
Input2: Jira JQL als RSS-Feed (xml)
Output: Artefakt/ReleaseNotes/ReleaseNotes-[Version].yml

Flow: 
Start-Releasecyle -> Generiert GenStufe1
UE edit GenStufe1
ST merge Jira + GenStufe1 = GenFinal

*/

import groovy.json.*

def restResponse = new File("data/Coconut-64a.json") //later via Rest-Call .parse(URI)
def js = """${restResponse.text}"""
def data = new JsonSlurper().parseText(js)


def xml = new XmlSlurper().parse('data/Coconut-63_64-DoneApproved.xml')

def items = xml.depthFirst().findAll { it.name() == 'item' }

/*
Map Mappings )1.0
def signed = "Anmeldung"
def cName = "Component"
def pName = "Package"
def trunk = "Trunk"
def ver = "Version"
def atype = "Type"
def tech = "Tech."
*/

//Map Mappings 2.0
def signed = "Meldung"
def cName = "cmpName"
def pName = "Artefakt"
def trunk = "Trunk"
def ver = "cmpVer"
def atype = "cmpType"
def tech = "Technologie"

def ue ="UE"; def jPrefix = "M13SYS"


//processing Data
def counter = 1
def countStoriesWithLabels = 1

data.each {
 if(it[signed] == "U" || it[signed] == "C") { //only signed packages
    println counter++ + ": " + it[pName]

    def ArtefactStories = xml.depthFirst().findAll { a -> a.name() == 'item' && a.labels.text().contains(it[pName])} 
    def myStories = "stories:\n"

    ArtefactStories.each { b -> 
            myStories = myStories + " - id: " + b.key.text() + "\n"   
            myStories = myStories + "   summary: " + b.summary.text() + "\n" 
            myStories = myStories + "   link: " + b.link.text() + "\n" 
            myStories = myStories + "   priority: " + b.priority.text() + "\n" 
            countStoriesWithLabels++
    }
    print myStories
    
    }
}
println ""
println "-------------------------------------------------------------------------"

//def itemsWithLabels = xml.depthFirst().findAll { it.name() == 'item' && it.labels.text().contains('KonBel-Commons')} 

// projects.each {println "project: \t" + it.@key}
//itemsWithLabels.each {println "ItemsWithTags: " + it.key.text()}

// println "-------------------------------------------------------------------------"
println "Stories Gesamt: \t" + items.size()
println "Stories mit Artefakt: \t" + countStoriesWithLabels
//println "itemsWithLabels: \t" + itemsWithLabels.size()