---
Title: height
MainCategory: Node
Description: Height of node, in inches.
Type: double
Default: 0.5
Min: 0.02
Restrictions: 
Examples: 
LastEdit: 2018-03-28
LastEditor: generator
---

This is taken as the initial, minimum height of the node. If fixedsize is true, this will be the final height of the node. Otherwise, if the node label requires more height to fit, the node's height will be increased to contain the label. Note also that, if the output format is dot, the value given to height will be the final value.
