package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                        try {
                            createPDFFile(Common.getAppPath(UserInfo.instance.getContext()) + "test.pdf");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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

    private void createPDFFile(final String path) throws IOException {
        if (new File(path).exists()) {
            new File(path).delete();
        }

        InputStream inputStream = getAssets().open("movies.html");
        // int size = inputStream.available();

        //byte[] buffer = new byte[size];
        //inputStream.read(buffer);
        //inputStream.close();

        //String str = new String(buffer);


        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(inputStream, new FileOutputStream(path), converterProperties);

        openPDF(UserInfo.instance.getContext());




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

            /* Log.d("DATE", UserInfo.instance.getDate());

            readData(new MyCallback() {
                @Override
                public void onCallBack1(ArrayList<DateObject> value) {
                    return;
                }

                @Override
                public void onCallBack2(List<FoodInfo> value) throws Exception {
                    UserInfo.instance.setFood(value);
                    FoodReport foodReport = new FoodReport(UserInfo.instance.getDate(), UserInfo.instance.getFood());

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

                    addNewCeilItem(table, "Food Name", Element.ALIGN_CENTER, headerFont);
                    addNewCeilItem(table, "Quantity", Element.ALIGN_MIDDLE, headerFont);
                    addNewCeilItem(table, "Price", Element.ALIGN_MIDDLE, headerFont);
                    addNewCeilItem(table, "Revenue", Element.ALIGN_MIDDLE, headerFont);

                    for (int i = 0; i < foodReport.getFoods().size(); i++) {
                        if (foodReport.getFoods().get(i).getQuantity() != 0) {
                            table.addCell(foodReport.getFoods().get(i).getName());
                            table.addCell(String.valueOf(foodReport.getFoods().get(i).getQuantity()));
                            table.addCell(foodReport.getFoods().get(i).getPrice());
                            table.addCell(String.valueOf(foodReport.getFoods().get(i).getQuantity() * Integer.parseInt(foodReport.getFoods().get(i).getPrice())));
                        }
                    }

                    document.add(table);

                    document.close();

                    openPDF(UserInfo.instance.getContext());
                }
            });*/


            //addNewItem(document, "Food Info", Element.ALIGN_LEFT, headerFont);
            //addLineSeparator(document);

            /*for (String text : foodInfoText) {
                addNewItem(document, text, Element.ALIGN_LEFT, dataFont);
            }*/



            //addLineSeparator(document);

            //addNewItem(document, "Account Name", Element.ALIGN_LEFT, orderNumberFont);
            //addNewItem(document, "Thanh Nguyen", Element.ALIGN_LEFT, orderNumberValueFont);

            /*myPdfDocument.finishPage(myPage1);
            myPdfDocument.writeTo(new FileOutputStream(path));
            myPdfDocument.close();*/

            //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

            //printPDF();
    }


    private void readData(final MyCallback myCallback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Vendors").child(UserInfo.instance.getUserName()).child("completed_orders");
        ref.orderByChild("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FoodInfo> foodInfos = UserInfo.instance.getFood();
                if (snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Order order = dataSnapshot.getValue(Order.class);
                        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = sdf1.parse(order.getDate());
                            String strDate = sdf2.format(date);

                            if (strDate.equals(UserInfo.instance.getDate())) {
                                for (OrderFood orderFood: order.getFoods()) {
                                    String fName = orderFood.getName();
                                    for (FoodInfo fi : foodInfos) {
                                        if (fi.getName().equals(fName)) {
                                            fi.setQuantity(fi.getQuantity() + Integer.parseInt(orderFood.getQuantity()));
                                            break;
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.d("ERROR", e.getMessage());
                        }
                    }
                    try {
                        myCallback.onCallBack2(foodInfos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*private void addNewItem(Document document, String text, int align, Font font) throws DocumentException {
        Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        document.add(paragraph);
    }


    private void addNewCeilItem(PdfPTable table, String text, int align, Font font) {
        Paragraph paragraph = new Paragraph(text, font);
        PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(align);
        cell.addElement(paragraph);
        table.addCell(cell);
    }*/

    private void openPDF(Context context) {
        File file = new File(Common.getAppPath(UserInfo.instance.getContext())+"test.pdf");
        Intent target = new Intent(Intent.ACTION_VIEW);
        Uri data = FileProvider.getUriForFile(context, "com.example.demo.fileprovider", file);
        target.setDataAndType(data, "application/pdf");
        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(target);
    }
}