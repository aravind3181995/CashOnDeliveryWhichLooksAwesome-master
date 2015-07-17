package com.example.aravindcm.cashondelivery;

import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    TextView txt;
    String result;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
        }
        txt=(TextView)findViewById(R.id.view1);
        // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.e("cm", rawResult.getText()); // Prints scan results
        Toast.makeText(getApplicationContext(), rawResult.getText(),
                Toast.LENGTH_LONG).show();
        String result;
        result=rawResult.getText();
        try {
            parse(result);
        }
        catch(Exception e)
        {   Intent i=new Intent(this,display.class);
            i.putExtra("result",result);
            Log.e("ignore", "ignore");

            startActivity(i);
        }
        Log.e("cm", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
    }
    public void parse(String result) throws Exception
    {
        //String xmlRecords = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
          //      "<PrintLetterBarcodeData uid=\"687968530128\" name=\"Nishant R B\" gender=\"M\" yob=\"1995\" co=\"S/O R S Bharath Kumar\" house=\"#95\" street=\"2nd Main,3rd Cross, Kempe Gowda Layout, Kattriguppe\" loc=\"BSK 3rd Stage\" vtc=\"Bangalore South\" dist=\"Bangalore\" state=\"Karnataka\" pc=\"560085\"/>";

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(result));

        Document doc = db.parse(is);
        NodeList nodes = doc.getElementsByTagName("PrintLetterBarcodeData");
        String name="";
        String yob="";
        String gender="";
        String co="";
        String street="";
        String loc="";
        String vtc="";
        String state="";
        String dist="";
        String pc="";
        String house="";

        Element element = (Element) nodes.item(0);

           /* NodeList name = element.getElementsByTagName("name");
            Element line = (Element) name.item(0);
            System.out.println("Name: " + getCharacterDataFromElement(line));
            Log.e("Name:", getCharacterDataFromElement(line));*/
        //NodeList title = element.getElementsByTagName("title");
        Element line = (Element) nodes.item(0);
        System.out.println("Title: " + getCharacterDataFromElement(line));
        Log.e("Title:", getCharacterDataFromElement(line));
        Log.e("testing", nodes.item(0).getAttributes().getNamedItem("name").getNodeValue());
        name= nodes.item(0).getAttributes().getNamedItem("name").getNodeValue();
        Log.e("testing", nodes.item(0).getAttributes().getNamedItem("gender").getNodeValue());
        gender= nodes.item(0).getAttributes().getNamedItem("gender").getNodeValue();
        Log.e("testing", nodes.item(0).getAttributes().getNamedItem("yob").getNodeValue());
        yob= nodes.item(0).getAttributes().getNamedItem("yob").getNodeValue();
        Log.e("testing", nodes.item(0).getAttributes().getNamedItem("co").getNodeValue());
        co=nodes.item(0).getAttributes().getNamedItem("co").getNodeValue();
        Log.e("testing", nodes.item(0).getAttributes().getNamedItem("street").getNodeValue());
        street= nodes.item(0).getAttributes().getNamedItem("street").getNodeValue();
        Log.e("testing", nodes.item(0).getAttributes().getNamedItem("loc").getNodeValue());
        loc= nodes.item(0).getAttributes().getNamedItem("loc").getNodeValue();
        Log.e("testing", nodes.item(0).getAttributes().getNamedItem("vtc").getNodeValue());
        vtc= nodes.item(0).getAttributes().getNamedItem("vtc").getNodeValue();
        Log.e("testing", nodes.item(0).getAttributes().getNamedItem("dist").getNodeValue());
        dist= nodes.item(0).getAttributes().getNamedItem("dist").getNodeValue();
        Log.e("testing", nodes.item(0).getAttributes().getNamedItem("pc").getNodeValue());
        pc= nodes.item(0).getAttributes().getNamedItem("pc").getNodeValue();
        Log.e("testing", nodes.item(0).getAttributes().getNamedItem("state").getNodeValue());
        state= nodes.item(0).getAttributes().getNamedItem("state").getNodeValue();
        Log.e("testing", nodes.item(0).getAttributes().getNamedItem("house").getNodeValue());
        house= nodes.item(0).getAttributes().getNamedItem("house").getNodeValue();
        result=name+"\n"+gender+"\n"+yob+"\n"+co+"\n"+house+" "+street+" "+loc+"\n"+" "+vtc+" "+"\n"+dist+"\n"+state+"\n"+pc;
        String address=house+" "+street+" "+loc+"\n"+" "+vtc+" "+"\n"+dist+"\n"+state+"\n"+pc;
        Intent i=new Intent(this,display.class);
        i.putExtra("name",name);
        i.putExtra("gender",gender);
        i.putExtra("yob",yob);
        i.putExtra("co",co);
        i.putExtra("address",address);
        i.putExtra("result",result);
        Log.e("next_act",result);
        startActivity(i);
    }
    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}