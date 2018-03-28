---
Title: layout
MainCategory: Graph
Description: Specifies the name of the layout algorithm to use, such as 'dot' or 'neato'.
Type: string
Default: n/a
Min: 
Restrictions: 
Examples: 
LastEdit: 2018-03-28
LastEditor: generator
---

Normally, graphs should be kept independent of a type of layout. In some cases, however, it can be convenient to embed the type of layout desired within the graph. For example, a graph containing position information from a layout might want to record what the associated layout algorithm was. This attribute takes precedence over the -K flag or the actual command name used.
