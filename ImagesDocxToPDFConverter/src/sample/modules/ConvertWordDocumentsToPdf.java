//Author: Tafadzwa Brian Motsi

package sample.modules;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

import java.io.*;

@SuppressWarnings("SameParameterValue")
public
class ConvertWordDocumentsToPdf {
    public static void convertDocxToPdf(String docxFileInputPath, String pdfFileOutputPath){
        File inputWord = new File(docxFileInputPath);
        File outputFile = new File(pdfFileOutputPath);

        try  {

            InputStream docxInputStream = new FileInputStream(inputWord);
            OutputStream outputStream = new FileOutputStream(outputFile);

            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();

            converter.shutDown();
            docxInputStream.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
