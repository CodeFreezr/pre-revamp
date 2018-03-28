---
Title: fontpath
MainCategory: Graph
Description: Directory list used by libgd to search for bitmap fonts if Graphviz was not built with the fontconfig library.
Type: string
Default: n/a
Min: 
Restrictions: system-dependent
Examples: 
LastEdit: 2018-03-28
LastEditor: generator
---

If fontpath is not set, the environment variable DOTFONTPATH is checked. If that is not set, GDFONTPATH is checked. If not set, libgd uses its compiled-in font path. Note that fontpath is an attribute of the root graph.
