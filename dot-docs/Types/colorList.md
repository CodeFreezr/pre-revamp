---
Title: colorList
Based-On: List of Strings
Values-Or-Examples: 
Description: A colon-separated list of weighted color values.
LastEdit: 2018-03-28
LastEditor: generator
---

WC(:WC)* where each WC has the form C(;F)? with C a color value and the optional F a floating-point number, 0 ? F ? 1. The sum of the floating-point numbers in a colorList must sum to at most 1.
