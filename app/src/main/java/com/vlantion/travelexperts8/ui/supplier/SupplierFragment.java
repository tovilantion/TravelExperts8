package com.vlantion.travelexperts8.ui.supplier;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.vlantion.travelexperts8.Customer.Customer;
import com.vlantion.travelexperts8.R;
import com.vlantion.travelexperts8.Supplier.Supplier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class SupplierFragment extends Fragment {
    ListView lvSuppliers;
    private SupplierViewModel supplierViewModel;
    View myViewSupplier;
    Button btnAddSupplier;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myViewSupplier = inflater.inflate(R.layout.fragment_supplier, container, false);
        lvSuppliers = myViewSupplier.findViewById(R.id.lvSuppliers);

        new getSuppliers().execute("http://10.0.2.2:8080/Workshop/rs/supplier/getallsuppliers");

        new putSupplier().execute("100", "test");


        supplierViewModel =
                ViewModelProviders.of(this).get(SupplierViewModel.class);

        final TextView textView = myViewSupplier.findViewById(R.id.text_gallery);
        supplierViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return myViewSupplier;
    }


    //HTTP GET METHOD REQUEST
//http://10.0.2.2:8080/Workshop/rs/supplier/getallsuppliers

    public  class getSuppliers extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        @Override
        protected String doInBackground(String... params){
            String stringUrl = params[0];
            String result = null;
            String inputLine;


            try{
                java.net.URL myUrl = new URL(stringUrl);

                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
                Log.d("Result: ", result);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                result = null;
            }
            return result;

        }

        @Override
        protected void onPostExecute(String result){
            try {
                ArrayList<Supplier> listdata = new ArrayList<Supplier>();

                JSONArray supplierJsonList = new JSONArray(result);
                for (int i = 0; i < supplierJsonList.length(); i++){
                    JSONObject c = supplierJsonList.getJSONObject(i);
                    int supplierId = c.getInt("supplierId");
                    String supName = c.optString("supName"); //optional null value


                   Supplier supplier = new Supplier(supplierId, supName);

                    listdata.add(supplier);

                    ArrayAdapter<Supplier> itemsAdapter =
                            new ArrayAdapter<Supplier>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listdata);

                    lvSuppliers.setAdapter(itemsAdapter);

                    lvSuppliers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intentSupplier = new Intent(getActivity().getApplicationContext(), SupplierDetail.class);
                            intentSupplier.putExtra("supplier", (Supplier) lvSuppliers.getAdapter().getItem(position));
                            startActivity(intentSupplier);
                        }
                    });
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }

    //HTTP PUT Method
//http://10.0.2.2:8080/Workshop/rs/supplier/putsupplier

    public class putSupplier extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result){
            //Do something after
            Log.d("Result: ", result);
        }

        @Override
        protected String doInBackground(String... params) {
            String status = "Fail";

            try {

                URL url = new URL("http://10.0.2.2:8080/Workshop/rs/supplier/putsupplier");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("supplierId", Integer.valueOf(params[0]));
                jsonParam.put("supName", params[1]);



                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
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

    }//end putSupplier

    public void btnAddSupplierClick(View v){

    }

}
