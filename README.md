# Images-Docx-To-PDF-Converter

## Images Docx Files to PDF Converter

ImagesDocxToPDFConverter is a JavaFX desktop application that allows users to convert either images (.jpg or .png) files or .docx files to PDF files by dragging and dropping the files for conversion.

---
## Input files

The program takes the following as input files:

 * __Image files__
 * __Word document files__]
 
 ---
 ## Processing
 The application uses __itextpdf__ and __documents4j__ packages to convert images and docx files to Acrobat PDF format respectively. 
 
 ### Processing images
 Given an image, the program does the following:
  * Creates a new document instance
  * Creates a new file output stream instance
  * Instantiates a new pdf writer object
  * Opens the pdf writer
  * Opens the document
  * Create a new page where the image will be rendered
  * 
 


## User Interface

The application has three options (shown in the screen shot below) for the user:

![](ImagesDocxToPDFConverter/screen_shots/welcome.png)

---

## Converting Images to PDF

### Drag and Drop Images

Clicking on the first option, the program launches a new scene prompting the user to drag the image files to the window area:

![](ImagesDocxToPDFConverter/screen_shots/drop_images.png)

### Converting

On dropping the images, the preview window is launched and the user can browse through the images to converted by scrolling on the window (see the screen shot below):

![](ImagesDocxToPDFConverter/screen_shots/convert_images_3.png)

### Merging
On clicking the convert button, the user choose to merge the files or convert as single files, and then they can navigate their system to choose location of storage.

---
## Results
  * [Output files](https://github.com/tafadzwabmotsi/Images-Docx-To-PDF-Converter/tree/master/output_files "Single and merged files")
