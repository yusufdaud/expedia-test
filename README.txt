Problem Description
===================

A hotel information store allows brands to upload images for their hotels; each brand has a different way to identify the images, so a hotel can have an image under different names depending on the brand. 

Devise a data structure that stores each image efficiently and so it is not stored more than once. You can use a byte array in lieu of an actual image. 

The sample project contains a class with the methods' signature for the image store. 

You are required to:
1) Complete the implementation of the image store class.
2) Provide a way to demonstrate how the implementation works.

Additional notes:
- Please provide comments where you think they improve readability, but keep them compact.
- You can use any number of helper classes that you need to complete the solution.
- Please explain any assumptions you make


The following example shows the expected functionality:

1) Given the following sequence of operations:
  
   Insert <"Sunshine hotels", imageA>
   Insert <"Stormy hotels", imageA>
   Insert <"Beachfront Hotels", imageB>
 
The store should have size 2 and contain imageA and imageB.

2) Given the following sequence of operations:
   
   Insert <"Sunshine hotels", imageA>
   Insert <"Stormy hotels", imageA>
   Insert <"Sunshine hotels", imageB>
   Insert <"Stormy hotels", imageB>
   
The store should have size 1 and only contain imageB.

