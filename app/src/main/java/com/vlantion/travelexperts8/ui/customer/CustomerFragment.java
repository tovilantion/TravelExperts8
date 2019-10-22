package com.vlantion.travelexperts8.ui.customer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.vlantion.travelexperts8.Customer.Customer;
import com.vlantion.travelexperts8.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CustomerFragment extends Fragment {
    ListView lvCustomers;
    private CustomerViewModel customerViewModel;
    View myView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_customer, container, false);
        lvCustomers = myView.findViewById(R.id.lvCustomer);

        new getCustomers().execute("http://10.0.2.2:8080/Workshop(3)/rs/customer/getallcustomers");

        customerViewModel =
                ViewModelProviders.of(this).get(CustomerViewModel.class);

        final TextView textView = myView.findViewById(R.id.text_gallery);
        customerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return myView;
    }

    //HTTP GET METHOD REQUEST
//http://10.0.2.2:8080/Workshop/rs/customer/getallcustomers

    public class getCustomers extends AsyncTask<String, Void, String> {

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
                ArrayList<Customer> listdata = new ArrayList<Customer>();

                JSONArray customerJsonList = new JSONArray(result);
                for (int i = 0; i < customerJsonList.length(); i++){
                    JSONObject c = customerJsonList.getJSONObject(i);
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
                            custCity,  custProv,
                            custPostal,custCountry,
                            custHomePhone,  custBusPhone,
                            custEmail, agentId);

                    listdata.add(customer);
                    Log.d("List data 1: ", listdata.get(0).getCustFirstName());

                    ArrayAdapter<Customer> itemsAdapter =
                            new ArrayAdapter<Customer>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listdata);

                    lvCustomers.setAdapter(itemsAdapter);
                    //tvCustName.setText(listdata.get(0).getCustFirstName());

                    lvCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity().getApplicationContext(), CustomerDetail.class);
                            intent.putExtra("customer", (Customer) lvCustomers.getAdapter().getItem(position));
                            startActivity(intent);
                        }
                    });
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }
}