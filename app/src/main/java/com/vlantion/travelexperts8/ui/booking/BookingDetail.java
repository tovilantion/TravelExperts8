package com.vlantion.travelexperts8.ui.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vlantion.travelexperts8.Booking.Booking;
import com.vlantion.travelexperts8.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class BookingDetail extends AppCompatActivity {
    EditText etBookingId, etBookingDate, etBookingNo, etTravelerCount,
            etCustomerId, etTripTypeId, etPackageId;
    Booking booking;
    Button btnBookingSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        etBookingId = findViewById(R.id.etBookingId);
        etBookingDate = findViewById(R.id.etBookingDate);
        etBookingNo = findViewById(R.id.etBookingNo);
        etTravelerCount = findViewById(R.id.etTravelerCount);
        etCustomerId = findViewById(R.id.etCustomerId);
        etTripTypeId = findViewById(R.id.etTripTypeId);
        etPackageId = findViewById(R.id.etPackageId);


        booking = (Booking) getIntent().getSerializableExtra(("booking"));
//        Log.d("id", booking.getBookingNo()+ "");
        if(booking != null) {
//            Toast.makeText(this, booking.getBookingId(), Toast.LENGTH_LONG).show();
//                    Log.d("BOOKING", booking.toString());


            etBookingId.setText(booking.getBookingId() + "");
            etBookingNo.setText(booking.getBookingNo());
            etTravelerCount.setText(booking.getTravelerCount() + "");
            etCustomerId.setText(booking.getCustomerId() + "");
            etTripTypeId.setText(booking.getTripTypeId());
            etBookingDate.setText(booking.getBookingDate() + "");
            etPackageId.setText(booking.getPackageId() + "");
        }


    }// end on create

    public void btnBookingSaveClick(View v)
    {
        new postBooking().execute(
                etBookingId.getText().toString(),
                etBookingDate.getText().toString(),
                etBookingNo.getText().toString(),
                etTravelerCount.getText().toString(),
                etCustomerId.getText().toString(),
                etTripTypeId.getText().toString(),
                etPackageId.getText().toString()
        );
        Toast.makeText(this, "Booking " + etBookingId.getText().toString() + " updated", Toast.LENGTH_LONG).show();
    }

    //HTTP POST Method
    //http://10.0.2.2:8080/Workshop/rs/booking/postbooking

    public class postBooking extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            //Do something after
            Log.d("Result: ", result);
        }

        @Override
        protected String doInBackground(String... params) {
            String status = "Fail";

            try {

                URL url = new URL("http://10.0.2.2:8080/Workshop/rs/booking/postbooking");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                // conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("bookingId", Integer.valueOf(params[0]));
                jsonParam.put("bookingDate", params[1]);
                jsonParam.put("bookingNo", params[2]);
                jsonParam.put("travelerCount", Integer.valueOf(params[3]));
                jsonParam.put("customerId", Integer.valueOf(params[4]));
                jsonParam.put("tripTypeId", params[5]);
                jsonParam.put("packageId", params[6]);


                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonParam.toString());
                os.flush();
                os.close();
                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG", conn.getResponseMessage());

                if (conn.getResponseCode() == 200) {
                    status = "Success";
                } else {
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
    }


    public void btnDeleteBookingClick(View v) {
        Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();
    }
} // end booking detail class
