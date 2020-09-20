//Author: Tafadzwa Brian Motsi

package sample.modules;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

@SuppressWarnings("SameParameterValue")
public class ConvertImagesToPdf {

    public static void convertImageToPdf(String input, String output){

        Document document =  new Document();
        try {

            FileOutputStream fos = new FileOutputStream(output);

            PdfWriter writer = PdfWriter.getInstance(document, fos);

            writer.open();
            document.open();

            document.newPage();
            Image image = Image.getInstance(input);
            image.setAbsolutePosition(0,0);
            image.setBorderWidth(0);
            image.scaleAbsoluteHeight(PageSize.A4.getHeight());
            image.scaleAbsoluteWidth(PageSize.A4.getWidth());


            document.add(image);


            document.close();
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
