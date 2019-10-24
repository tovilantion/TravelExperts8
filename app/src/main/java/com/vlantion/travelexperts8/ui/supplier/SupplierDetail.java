package com.vlantion.travelexperts8.ui.supplier;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.vlantion.travelexperts8.Customer.Customer;
import com.vlantion.travelexperts8.R;
import com.vlantion.travelexperts8.Supplier.Supplier;
import com.vlantion.travelexperts8.ui.customer.CustomerDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SupplierDetail extends AppCompatActivity {
    EditText etSupplierId, etSupName;
    Button btnSave, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_detail);

        etSupplierId = findViewById(R.id.etSupplierId);
        etSupName = findViewById(R.id.etSupName);

        Supplier supplier = (Supplier) getIntent().getSerializableExtra("supplier");
        etSupplierId.setText(supplier.getSupplierId()+"");
        etSupName.setText(supplier.getSupName());



    } // end oncreate


    public void btnSupplierSaveClick(View v)
    {
        new SupplierDetail.postSupplier().execute(
                etSupplierId.getText().toString(),
                etSupName.getText().toString()
        );

        Toast.makeText(this, "Supplier " + etSupName.getText().toString() + " updated", Toast.LENGTH_LONG).show();

    }

    public void btnBookingDeleteClick(View view) {
        new SupplierDetail.deleteSupplier().execute(etSupplierId.getText().toString());
        Toast.makeText(this, "Supplier " + etSupName.getText().toString() + " deleted", Toast.LENGTH_LONG).show();


    }


    //HTTP POST Method
//http://10.0.2.2:8080/Workshop/rs/supplier/postsupplier

    public class postSupplier extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result){
            //Do something after
            Log.d("Result: ", result);
        }

        @Override
        protected String doInBackground(String... params) {
            String status = "Fail";

            try {

                URL url = new URL("http://10.0.2.2:8080/Workshop/rs/supplier/postsupplier");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
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

    }//end postSupplier

    public class deleteSupplier extends AsyncTask<String, Void, String>{

        @Override
        protected void onPostExecute(String result){
            //Do something after
            Log.d("Result: ", result);
        }

        @Override
        protected String doInBackground(String... params) {
            String status = "Fail";

            try {

                URL url = new URL("http://10.0.2.2:8080/Workshop/rs/supplier/deletesupplier/" + params[0]); //param[0] is supplierId
                Log.d("URL:", url.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setDoOutput(true);
                conn.setDoInput(true);

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
            }

            return status;
        }
    }//end deleteSupplier

}
