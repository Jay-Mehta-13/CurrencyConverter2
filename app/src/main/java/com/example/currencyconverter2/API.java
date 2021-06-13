//package com.example.currencyconverter2;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Iterator;
//
//public class API extends Thread{
//    public static ArrayList<String> list = new ArrayList<String>();
//
//    public static ArrayList getList()
//    {
//        System.out.println("Inside getList!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                String data = "";
//                String line = "";
//                try {
//                    URL listURL = new URL("https://free.currconv.com/api/v7/countries?apiKey=92139c44c58c2a36a2d3");
//                    HttpURLConnection listConnection = (HttpURLConnection) listURL.openConnection();
//                    InputStream listInputStream = listConnection.getInputStream();
//                    BufferedReader listReader = new BufferedReader(new InputStreamReader(listInputStream));
//
//                    while (line != null)
//                    {
//                        line = listReader.readLine();
//                        data = data + line;
//                    }
//                    JSONObject listJO = new JSONObject(data);
//                    Iterator listIterator = listJO.getJSONObject("results").keys();
//                    while (listIterator.hasNext())
//                    {
//                        list.add(listIterator.next().toString());
//                    }
//
//                    System.out.println(list);
//                } catch (MalformedURLException e) {
//                    System.out.println("Error in URL");
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    System.out.println("Error in Input Stream");
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    System.out.println("Error in JSON");
//                    e.printStackTrace();
//                }
//        return list;
//    }
//}
