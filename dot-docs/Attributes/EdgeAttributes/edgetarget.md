---
Title: edgetarget
MainCategory: Edge
Description: If the edge has a URL or edgeURL attribute, this attribute determines which window of the browser is used for the URL attached to the non-label part of the edge.
Type: escString
Default: n/a
Min: 
Restrictions: svg, map only
Examples: 
LastEdit: 2018-03-28
LastEditor: generator
---

Setting it to '_graphviz' will open a new window if it doesn't already exist, or reuse it if it does. If undefined, the value of the target is used.
