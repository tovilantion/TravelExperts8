package com.vlantion.travelexperts8.Customer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class CustomerDB {

    public static void getCustomers() {
        JSONObject jObj = null;
        String json = "";


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL("http://10.0.2.2:8080/Workshop/rs/customer/getallcustomers");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //conn.setRequestMethod("GET");
                    //conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    // conn.setRequestProperty("Accept","application/json");
                    //conn.setDoOutput(true);
                    //conn.setDoInput(true);

                    InputStream iS = conn.getInputStream();
                    InputStreamReader reader = new InputStreamReader(iS);
                    BufferedReader bufferedReader = new BufferedReader((reader));
                    String line;
                    ArrayList<Customer> customerList = new ArrayList<Customer>();


                    // Save server response text.
                    StringBuffer readTextBuf = new StringBuffer();

                    line = bufferedReader.readLine();
                    Log.d("GET: ", line);


                    JSONArray customers = new JSONArray(line);

                    //Loop through all contacts

                    for (int i = 0; i < customers.length(); i++) {
                        JSONObject c = customers.getJSONObject(i);
                        int customerId = c.getInt("customerId");
                        String custFirstName = c.getString("custFirstName");
                        String custLastName = c.getString("custLastName");
                        String custAddress = c.getString("custAddress");
                        String custCity = c.getString("custCity");
                        String custProv = c.getString("custProv");
                        String custPostal = c.getString("custPostal");
                        String custCountry = c.getString("custCountry");
                        String custHomePhone = c.getString("custHomePhone");
                        String custBusPhone = c.getString("custBusPhone");
                        String custEmail = c.getString("custEmail");
                        int agentId = c.getInt("agentId");

                        Customer customer = new Customer(customerId, custFirstName,
                                custLastName, custAddress,
                                custCity, custProv,
                                custPostal, custCountry,
                                custHomePhone, custBusPhone,
                                custEmail, agentId);

                        customerList.add(customer);
                        Log.d("LOOP: ", customerList.get(i).getCustFirstName());
                    }
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
    } //end of getCustomers
}
