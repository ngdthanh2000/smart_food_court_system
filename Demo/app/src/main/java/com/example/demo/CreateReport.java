package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateReport extends AppCompatActivity {

    WebView webView;
    StringBuffer monthBuf = new StringBuffer();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_report_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.shareButton:
                printPDF(webView);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        try {
            createPDFFile(Common.getAppPath(UserInfo.instance.getContext()) + "test.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void createPDFFile(final String path) throws IOException {

        final NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

            readData(new MyCallback() {
                @Override
                public void onCallBack1(ArrayList<DateObject> value) {
                    return;
                }

                @Override
                public void onCallBack2(List<FoodInfo> value) throws Exception {

                    InputStream inputStream = getAssets().open("pdfdata.html");
                    String str = "";
                    StringBuffer buf = new StringBuffer();
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        if (inputStream != null) {
                            while ((str = reader.readLine()) != null) {
                                buf.append(str + "\n");
                            }
                        }
                    } finally {
                        try {
                            inputStream.close();
                        } catch (Throwable ignore) {}

                    }

                    buf.append("function drawQuantityChart() {\n");

                    buf.append("var data = new google.visualization.DataTable();\n" +
                            "        data.addColumn('string', 'Food');\n" +
                            "        data.addColumn('number', 'Quantity');\n" +
                            "        data.addRows([\n");
                    for (int i = 0; i < value.size(); i++) {
                            buf.append("['" + value.get(i).getName() + "', " + value.get(i).getQuantity() + "]");
                            if (i != value.size() - 1) {
                                buf.append(",\n");
                            }
                            else {
                                buf.append("\n");
                            }
                    }

                    buf.append("]);\n");

                    buf.append("var options = {'title':'Consumed food by quantity',\n" +
                            "                       'width':350,\n" +
                            "                       'height':300};\n" +
                            "\n" +
                            "        // Instantiate and draw our chart, passing in some options.\n" +
                            "        var chart = new google.visualization.PieChart(document.getElementById('quantity_chart_div'));\n" +
                            "        chart.draw(data, options);\n" +
                            "      }\n");


                    buf.append("function drawRevenueChart() {\n");

                    buf.append("var data = new google.visualization.DataTable();\n" +
                            "        data.addColumn('string', 'Food');\n" +
                            "        data.addColumn('number', 'Revenue');\n" +
                            "        data.addRows([\n");
                    for (int i = 0; i < value.size(); i++) {
                        buf.append("['" + value.get(i).getName() + "', " + Integer.parseInt(value.get(i).getPrice())*value.get(i).getQuantity() + "]");
                        if (i != value.size() - 1) {
                            buf.append(",\n");
                        }
                        else {
                            buf.append("\n");
                        }
                    }

                    buf.append("]);\n");

                    buf.append("var options = {'title':'Consumed food by revenue',\n" +
                            "                       'width':350,\n" +
                            "                       'height':300\n" +
                            "};\n" +
                            "\n" +
                            "        // Instantiate and draw our chart, passing in some options.\n" +
                            "        var chart = new google.visualization.PieChart(document.getElementById('revenue_chart_div'));\n" +
                            "        chart.draw(data, options);\n" +
                            "      }\n");



                    buf.append("    </script>\n" +
                            "  </head>\n");

                    buf.append("<body>\n" +
                            "<h1 class=\"title\">Countries and Movies</h1>\n" +
                            "<p>This is an overview of all the countries in our movie database.</p>\n" +
                            "<p>\n" +
                            "<h1 class=\"country\">Argentina</h1>\n" +
                            "<p>This is a table containing all the movies that were entirely or partially produced in Argentina:</p>\n" +
                            "</p>\n" +
                            "<table>\n" +
                            "<tr class=\"movierow\"><th class=\"column1\">No.</th><th class=\"column2\">Name</th><th class=\"column3\">Price</th><th class=\"column4\">Quantity</th><th class=\"column5\">Revenue</th></tr>\n");



                    int no = 1;
                    int totalQuantity = 0;
                    long totalPrice = 0;
                    for (int i = 0; i < value.size(); i++) {
                        if (value.get(i).getQuantity() != 0) {
                            buf.append("<tr class=\"movierow\"><td class=\"column1\">");
                            buf.append(no);
                            buf.append("</td><td class=\"column2\">");
                            buf.append(value.get(i).getName());
                            buf.append("</td><td class=\"column3\">");
                            buf.append(numberFormat.format(Long.parseLong(value.get(i).getPrice())));
                            buf.append("</td><td class=\"column4\">");
                            buf.append(value.get(i).getQuantity());
                            totalQuantity += value.get(i).getQuantity();
                            buf.append("</td><td class=\"column5\">");
                            buf.append(numberFormat.format(value.get(i).getQuantity() * Integer.parseInt(value.get(i).getPrice())));
                            totalPrice += value.get(i).getQuantity() * Integer.parseInt(value.get(i).getPrice());
                            buf.append("</td></tr>" + "\n");
                            no++;
                        }
                    }

                    buf.append("<tr class=\"movierow\"><th colspan=\"3\">TOTAL</th><th class=\"column4\">");
                    buf.append(totalQuantity);
                    buf.append("</th><th class=\"column5\">");
                    buf.append(numberFormat.format(totalPrice));
                    buf.append("</th></tr>" + "\n");

                    buf.append("</table>\n");

                    buf.append("    <div id=\"quantity_chart_div\"></div>\n" +
                                    "    <div id=\"revenue_chart_div\"></div>\n" +
                            "  </body>\n" +
                            "</html>");

                    Log.d("HTML", buf.toString());

                    webView = (WebView) findViewById(R.id.webView);
                    webView.setInitialScale(1);
                    webView.getSettings().setUseWideViewPort(true);
                    webView.getSettings().setLoadWithOverviewMode(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadDataWithBaseURL("", buf.toString(), "text/html; charset=utf-8", "UTF-8", "");
                }
            });
    }

    private void printPDF(WebView webView) {
        PrintManager printManager = (PrintManager)getSystemService(Context.PRINT_SERVICE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        PrintDocumentAdapter printDocumentAdapter = webView.createPrintDocumentAdapter(UserInfo.instance.getUserName() + "-" + simpleDateFormat.format(date) + "-Report");

        String jobName = getString(R.string.app_name) + " Document";
        PrintAttributes printAttributes = new PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .build();

        printManager.print(jobName, printDocumentAdapter, printAttributes);
    }


    private void readData(final MyCallback myCallback) {

        final int month = Integer.parseInt(UserInfo.instance.getMonth());

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Vendors").child(UserInfo.instance.getUserName()).child("completed_orders");
        ref.orderByChild("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FoodInfo> foodInfos = new ArrayList<>();
                List<FoodInfo> sample = UserInfo.instance.getFood();
                for (int i = 0; i < sample.size(); i++) {
                    foodInfos.add(new FoodInfo(sample.get(i)));
                }
                if (snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Order order = dataSnapshot.getValue(Order.class);
                        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = sdf1.parse(order.getDate());
                            String strDate = sdf2.format(date);

                            if (month == Integer.parseInt(strDate.substring(5,7))) {
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
}