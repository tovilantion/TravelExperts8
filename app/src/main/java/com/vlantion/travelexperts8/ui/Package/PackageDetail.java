package com.vlantion.travelexperts8.ui.Package;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vlantion.travelexperts8.Package.Package;
import com.vlantion.travelexperts8.Customer.CustomerDB;
import com.vlantion.travelexperts8.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PackageDetail extends AppCompatActivity {
    EditText etPackageId, etPkgName, etPkgStartDate, etPkgEndDate,
            etPkgDesc, etPkgBasePrice, etPkgAgencyCommission;
    Package packages;
    Button btnPackEdit, btnPackSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);

        etPackageId = findViewById(R.id.etPackageId);
        etPkgName = findViewById(R.id.etPkgName);
        etPkgStartDate = findViewById(R.id.etPkgStartDate);
        etPkgEndDate = findViewById(R.id.etPkgEndDate);
        etPkgDesc = findViewById(R.id.etPkgDesc);
        etPkgBasePrice = findViewById(R.id.etPkgBasePrice);
        etPkgAgencyCommission = findViewById(R.id.etPkgAgencyCommission);


        packages = (Package) getIntent().getSerializableExtra(("packages"));
        etPackageId.setText(packages.getPackageId() + "");
        etPkgName.setText(packages.getPkgName());
        etPkgStartDate.setText(packages.getPkgStartDate() + "");
        etPkgEndDate.setText(packages.getPkgEndDate() + "");
        etPkgDesc.setText(packages.getPkgDesc());
        etPkgBasePrice.setText(packages.getPkgBasePrice() + "");
        etPkgAgencyCommission.setText(packages.getPkgAgencyCommission() + "");



        /**
         * public Customer(int customerId, String custFirstName, String custLastName, String custAddress, String custCity,
         * 			String custProv, String custPostal, String custCountry, String custHomePhone, String custBusPhone,
         * 			String custEmail, int agentId) {
         * */



        //Test postCustomers
//        new postCustomer().execute("143", "2", "Test", "Darren", "Darren", "Darren", "Darren", "Darren", "Darren", "Darren", "Darren", "AB");


    } // end oncreate

    public void btnSaveClick(View v)
    {
        new postPackage().execute(
                etPackageId.getText().toString(),
                etPkgName.getText().toString(),
                etPkgStartDate.getText().toString(),
                etPkgEndDate.getText().toString(),
                etPkgDesc.getText().toString(),
                etPkgBasePrice.getText().toString(),
                etPkgAgencyCommission.getText().toString());

        Toast.makeText(this, "Package " + etPackageId.getText().toString() + " updated", Toast.LENGTH_LONG).show();
    }

    public void btnDeletePackageClick(View v){
        new deletePackage().execute(etPackageId.getText().toString());
        Toast.makeText(this, "Package " + etPackageId.getText().toString() + " deleted", Toast.LENGTH_LONG).show();

    }


    //HTTP POST Method
//http://10.0.2.2:8080/Workshop/rs/package/postpackage

    public class postPackage extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result){
            //Do something after
            Log.d("Result: ", result);
        }

        @Override
        protected String doInBackground(String... params) {
            String status = "Fail";

            try {

                URL url = new URL("http://10.0.2.2:8080/Workshop/rs/package/postpackage");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                // conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("packageId", Integer.valueOf(params[0]));
                jsonParam.put("pkgName", params[1]);
                jsonParam.put("pkgStartDate", params[2]);
                jsonParam.put("pkgEndDate", params[3]);
                jsonParam.put("pkgDesc", params[4]);
                jsonParam.put("pkgBasePrice", params[5]);
                jsonParam.put("pkgAgencyCommission", params[6]);




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


//HTTP DELETE Method
//http://10.0.2.2:8080/Workshop/rs/package/deletepackage/{id}

    public class deletePackage extends AsyncTask<String, Void, String>{

        @Override
        protected void onPostExecute(String result){
            //Do something after
            Log.d("Result: ", result);
        }

        @Override
        protected String doInBackground(String... params) {
            String status = "Fail";

            try {

                URL url = new URL("http://10.0.2.2:8080/Workshop/rs/package/deletepackage/" + params[0]); //param[0] is packageId
                Log.d("URL:", url.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                // conn.setRequestProperty("Accept","application/json");
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
    }//end deletePackage

}


//Test postCustomers
