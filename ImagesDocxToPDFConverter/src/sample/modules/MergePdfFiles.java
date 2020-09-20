//Author: Tafadzwa Brian Motsi

package sample.modules;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MergePdfFiles {

    public static void mergePdfFiles(List<String> inputPdfFilePathList, String outputPdfFilePath) throws Exception{
        List<File> pdfFileList = new ArrayList<>();

        for(String filePath: inputPdfFilePathList){
            pdfFileList.add(new File(filePath));
        }

        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();

        pdfMergerUtility.setDestinationFileName(outputPdfFilePath);

        for(File pdfFile: pdfFileList){
            pdfMergerUtility.addSource(pdfFile);
        }

        pdfMergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
    }
}
