package com.RelaxiTaxi.pipelines;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class ImageExtract {

    public String getImgText(String imageLocation) {
        ITesseract instance = new Tesseract();
        instance.setDatapath("D:\\tessdata-4.1.0\\");

        try
        {
            String imgText = instance.doOCR(new File(imageLocation));
            return imgText;
        }
        catch (TesseractException e)
        {
            e.getMessage();
            return "Error while reading image";
        }
    }
    public static void main ( String[] args)
    {
        ImageExtract app = new ImageExtract();

        String dlExtracted = app.getImgText("C:\\Users\\DELL PC\\Downloads\\sayan_dl.jpeg");
        System.out.println("dlExtracted : " + dlExtracted);
        if(dlExtracted.contains("Sayan") && dlExtracted.contains("KAO05 987654321")){
            System.out.println("Valid dl  ");

        }


    }
}
