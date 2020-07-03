package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report2);

        // final Button btnCreateReport = (Button) findViewById(R.id.btnCreateReport);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        createPDFFile(Common.getAppPath(UserInfo.instance.getContext()) + "test.pdf");
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
        
        finish();

    }

    private void createPDFFile(String path) {
        if (new File(path).exists()) {
            new File(path).delete();
        }


        try {

            /*PdfDocument myPdfDocument = new PdfDocument();
            Paint myPaint = new Paint();
            Paint titlePaint = new Paint();

            PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(595,842, 1).create();
            PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
            Canvas canvas = myPage1.getCanvas();

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            titlePaint.setTextSize(70);
            canvas.drawText(vendorName.toString(), 270, 270, titlePaint);*/

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));

            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addCreator("Thanh Nguyen");
            document.addAuthor("FCFS");

            BaseFont fontName = BaseFont.createFont("assets/fonts/times.ttf", "UTF-8", BaseFont.EMBEDDED);
            float titleSize = 26.0f;
            float headerSize = 20.0f;
            float subHeaderSize = 16.0f;
            float dataSize = 12.0f;

            Font titleFont = new Font(fontName, titleSize, Font.NORMAL, BaseColor.BLACK);
            Font headerFont = new Font(fontName, headerSize, Font.NORMAL, BaseColor.BLACK);

            Font dataFont = new Font(fontName, dataSize, Font.NORMAL, BaseColor.BLACK);

            addNewItem(document, "Order Details", Element.ALIGN_CENTER, titleFont);

            PdfPTable table = new PdfPTable(4);
            table.addCell("Food Name");
            table.addCell("Quantity");
            table.addCell("Price");
            table.addCell("Revenue");

            ArrayList<FoodReport> foodReports = UserInfo.instance.getFood();

            for (int i = 0; i < foodReports.size(); i++) {
                table.addCell(foodReports.get(i).getName());
                table.addCell(String.valueOf(foodReports.get(i).getQuantity()));
                table.addCell(foodReports.get(i).getPrice());
                table.addCell(String.valueOf(foodReports.get(i).getQuantity() * Integer.parseInt(foodReports.get(i).getPrice())));
            }

            document.add(table);

            //addNewItem(document, "Food Info", Element.ALIGN_LEFT, headerFont);
            //addLineSeparator(document);

            /*for (String text : foodInfoText) {
                addNewItem(document, text, Element.ALIGN_LEFT, dataFont);
            }*/

            document.close();

            //addLineSeparator(document);

            //addNewItem(document, "Account Name", Element.ALIGN_LEFT, orderNumberFont);
            //addNewItem(document, "Thanh Nguyen", Element.ALIGN_LEFT, orderNumberValueFont);

            /*myPdfDocument.finishPage(myPage1);
            myPdfDocument.writeTo(new FileOutputStream(path));
            myPdfDocument.close();*/

            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

            //printPDF();

            openPDF(UserInfo.instance.getContext());

        } catch (Exception e) {
            Toast.makeText(CreateReport.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewItem(Document document, String text, int align, Font font) throws DocumentException {
        Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        document.add(paragraph);
    }

    private void openPDF(Context context) {
        File file = new File(Common.getAppPath(UserInfo.instance.getContext())+"test.pdf");
        Intent target = new Intent(Intent.ACTION_VIEW);
        Uri data = FileProvider.getUriForFile(context, "com.example.demo.fileprovider", file);
        target.setDataAndType(data, "application/pdf");
        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(target);
    }
}