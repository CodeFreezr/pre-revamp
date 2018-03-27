//Print the nxt environment variables via JVM to so the real values

def env = System.getenv()

//env.each{
//    println it
//} 

String myjava = env['JAVA_HOME']

println "JAVA_HOME: \t" + myjava
println "nxt_piFruit:\t" + env['nxt_piFruit']
println "nxt_vFull:\t" + env['nxt_vFull']
println "nxt_vShort:\t" + env['nxt_vShort']
println "nxt_kmRoot:\t" + env['nxt_kmRoot']
println "nxt_scopeRoot:\t" + env['nxt_scopeRoot']