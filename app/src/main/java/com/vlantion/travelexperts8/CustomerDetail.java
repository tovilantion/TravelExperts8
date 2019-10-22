package com.vlantion.travelexperts8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.vlantion.travelexperts8.Customer.Customer;
import com.vlantion.travelexperts8.Customer.CustomerDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class CustomerDetail extends AppCompatActivity {
    EditText etCustomerId, etCustFirstName, etCustLastName, etCustAddress,
        etCustCity, etCustProv, etCustPostal, etCustCountry, etCustHomePhone,
        etCustBusPhone, etCustEmail, etAgentId;
    Customer customer;
    Button btnCustEdit, btnCustSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        etCustomerId = findViewById(R.id.etCustomerId);
        etCustFirstName = findViewById(R.id.etCustFirstName);
        etCustLastName = findViewById(R.id.etCustLastName);
        etCustAddress = findViewById(R.id.etCustAddress);
        etCustCity = findViewById(R.id.etCustCity);
        etCustProv = findViewById(R.id.etCustProv);
        etCustPostal = findViewById(R.id.etCustPostal);
        etCustCountry = findViewById(R.id.etCustHomePhone);
        etCustHomePhone = findViewById(R.id.etCustHomePhone);
        etCustBusPhone = findViewById(R.id.etCustBusPhone);
        etCustEmail = findViewById(R.id.etCustEmail);
        etAgentId = findViewById(R.id.etAgentId);

        customer = (Customer) getIntent().getSerializableExtra(("customer"));
        etCustomerId.setText(customer.getCustomerId() + "");

    }

    //HTTP POST Method
//http://10.0.2.2:8080/Workshop/rs/customer/postcustomer

    public class postCustomer extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result){
            //Do something after
            Log.d("Result: ", result);
        }

        @Override
        protected String doInBackground(String... params) {
            String status = "Fail";

            try {

                URL url = new URL("http://10.0.2.2:8080/Workshop(3)/rs/customer/postcustomer");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                // conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("customerId", Integer.valueOf(params[0]));
                jsonParam.put("agentId", Integer.valueOf(params[1]));
                jsonParam.put("custAddress", params[2]);
                jsonParam.put("custBusPhone", params[3]);
                jsonParam.put("custCity", params[4]);
                jsonParam.put("custCountry", params[5]);
                jsonParam.put("custEmail", params[6]);
                jsonParam.put("custFirstName", params[7]);
                jsonParam.put("custHomePhone", params[8]);
                jsonParam.put("custLastName", params[9]);
                jsonParam.put("custPostal", params[10]);
                jsonParam.put("custProv", params[11]);


                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonParam.toString());
                os.flush();
                os.close();
                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG", conn.getResponseMessage());

                if (conn.getResponseCode() == 200){
                    status = "Success";
                }else{
                    status = "Fail";
                }
                conn.disconnect();
            } catch (
                    ProtocolException ex) {
                ex.printStackTrace();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            return status;
        }
    }//end postCustomer
}
