---
Title: defaultdist
MainCategory: Graph
Description: This specifies the distance between nodes in separate connected components.
Type: double
Default: 1+(avg. len)*sqrt(|V|)
Min: epsilon
Restrictions: neato only
Examples: 
LastEdit: 2018-03-28
LastEditor: generator
---

If set too small, connected components may overlap. Only applicable if pack=false.
