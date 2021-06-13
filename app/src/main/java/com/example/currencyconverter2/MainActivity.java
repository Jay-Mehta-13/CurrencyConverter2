package com.example.currencyconverter2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static ArrayList<String> list = new ArrayList<>();
    private static final String TAG = "MainActivity";
    public Spinner List1;
    public Spinner List2;
    public EditText Number;
    public String[] strList;
    public String Curr1;
    public String Curr2;
    public TextView Result;
    public Button Convert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        List1 = findViewById(R.id.List1);
        List2 = findViewById(R.id.List2);
        Number = findViewById(R.id.Number);
        Result = findViewById(R.id.Result);
        ExampleThread runnable = new ExampleThread();
        new Thread(runnable).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, strList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        List1.setAdapter(adapter);
        int spinnerPosition1 = adapter.getPosition("USD");
        List1.setSelection(spinnerPosition1);
        List1.setOnItemSelectedListener(this);
        List2.setAdapter(adapter);
        int spinnerPosition2 = adapter.getPosition("INR");
        List2.setSelection(spinnerPosition2);
        List2.setOnItemSelectedListener(this);
    }

    public void startThread(View view)
    {
        RateThread runnable2 = new RateThread();
        new Thread(runnable2).start();

        Convert = findViewById(R.id.Convert);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.5, 20);
        animation.setInterpolator(interpolator);
        Convert.startAnimation(animation);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.sample);
        mp.start();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Curr1 = List1.getSelectedItem().toString();
        Curr2 = List2.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class RateThread extends Thread{
        @Override
        public void run() {
            String conversion = Curr1 + "_" + Curr2;
            Double rate;
            Double result;
            String url = "https://free.currconv.com/api/v7/convert?q="+ conversion +"&compact=ultra&apiKey=92139c44c58c2a36a2d3";
            try {
                URL rateAPI = new URL(url);
                HttpURLConnection rateConnection = (HttpURLConnection) rateAPI.openConnection();
                InputStream rateInputStream = rateConnection.getInputStream();
                BufferedReader rateInput = new BufferedReader(new InputStreamReader(rateInputStream));
                String rateString = rateInput.readLine();
                JSONObject rateJO = new JSONObject(rateString);
                rate = Double.parseDouble(rateJO.get(conversion).toString());
                result = rate * Double.parseDouble(Number.getText().toString());
                Result.setText(String.valueOf(result));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ExampleThread extends Thread{

        @Override
        public void run() {
            String data = "";
            String line = "";
            try {
                URL listURL = new URL("https://free.currconv.com/api/v7/currencies?apiKey=92139c44c58c2a36a2d3");
                HttpURLConnection listConnection = (HttpURLConnection) listURL.openConnection();
                InputStream listInputStream = listConnection.getInputStream();
                BufferedReader listReader = new BufferedReader(new InputStreamReader(listInputStream));

                while (line != null)
                {
                    line = listReader.readLine();
                    data = data + line;
                }
                JSONObject listJO = new JSONObject(data);
                Iterator listIterator = listJO.getJSONObject("results").keys();
                while (listIterator.hasNext())
                {
                    list.add(listIterator.next().toString());
                }
                Collections.sort(list);
                strList = new String[list.size()];
                for (int i = 0; i<list.size(); i++)
                {
                    strList[i] = list.get(i);
                }
            } catch (MalformedURLException e) {
                System.out.println("Error in URL");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Error in Input Stream");
                e.printStackTrace();
            } catch (JSONException e) {
                System.out.println("Error in JSON");
                e.printStackTrace();
            }
        }
    }
}