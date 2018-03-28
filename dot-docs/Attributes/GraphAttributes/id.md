---
Title: id
MainCategory: Graph
Description: Allows the graph author to provide an id for graph objects which is to be included in the output.
Type: escString
Default: n/a
Min: 
Restrictions: svg, postscript, map only
Examples: 
LastEdit: 2018-03-28
LastEditor: generator
---

Normal '\N', '\E', '\G' substitutions are applied. If provided, it is the responsibility of the provider to keep its values sufficiently unique for its intended downstream use. Note, in particular, that '\E' does not provide a unique id for multi-edges. If no id attribute is provided, then a unique internal id is used. However, this value is unpredictable by the graph writer. An externally provided id is not used internally.
