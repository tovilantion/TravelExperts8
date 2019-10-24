package com.vlantion.travelexperts8.ui.booking;

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

import com.vlantion.travelexperts8.Booking.Booking;
import com.vlantion.travelexperts8.Customer.Customer;
import com.vlantion.travelexperts8.R;
import com.vlantion.travelexperts8.ui.customer.CustomerDetail;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingFragment extends Fragment {
    ListView lvBookings;
    private BookingViewModel bookingViewModel;
    View myView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_booking, container, false);
        lvBookings = myView.findViewById(R.id.lvBookings);

        new getBookings().execute("http://10.0.2.2:8080/Workshop/rs/booking/getallbookings");

        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);

        final TextView textView = myView.findViewById(R.id.text_slideshow);
        bookingViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return myView;
    }

    public class getBookings extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            String result = null;
            String inputLine;


            try {
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
                while ((inputLine = reader.readLine()) != null) {
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
        protected void onPostExecute(String result) {
            try {
                ArrayList<Booking> listdata = new ArrayList<Booking>();

                JSONArray bookingJsonList = new JSONArray(result);
                for (int i = 0; i < bookingJsonList.length(); i++) {
                    JSONObject c = bookingJsonList.getJSONObject(i);
                    int bookingId = c.getInt("bookingId");
                    String bookingNo = c.getString("bookingNo");
                    int travelerCount = c.getInt("travelerCount");
                    int customerId = c.getInt("customerId");
                    int packageId = c.getInt("packageId");
                    String tripTypeId = c.getString("tripTypeId");
                    //Convert String to Date
                    Date bookingDate = new SimpleDateFormat("MMM dd, YYYY, HH:mm:ss a").parse(c.getString("bookingDate"));


                    Booking booking = new Booking(bookingId, bookingDate, bookingNo, travelerCount, customerId, tripTypeId, packageId);

                    listdata.add(booking);

                    ArrayAdapter<Booking> itemsAdapter =
                            new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listdata);

                    //Update list view
                    lvBookings.setAdapter(itemsAdapter);

                    lvBookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intentBooking = new Intent(getActivity().getApplicationContext(), BookingDetail.class);
                            intentBooking.putExtra("booking", (Booking) lvBookings.getAdapter().getItem(position));
                            startActivity(intentBooking);
                        }
                    });

                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }//end getBookings


}
