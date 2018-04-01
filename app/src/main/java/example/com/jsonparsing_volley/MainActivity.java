package example.com.jsonparsing_volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //View to display names
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> namesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        namesList = new ArrayList<>();
        getStringRequest();
    }

    private void getStringRequest() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://api.themoviedb.org/3/movie/top_rated?api_key=7e8f60e325cd06e164799af1e317d7a7";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                MoviesResponse mResponse = gson.fromJson(response, MoviesResponse.class);
                List<Movie> mModel = new ArrayList<>();
                mModel.addAll(mResponse.getResults());

                for (int i = 0; i < mModel.size(); i++) {
                    // namesList is filled with title names from the movies object
                    namesList.add(mModel.get(i).getOriginalTitle());
                }
                // setting datas into the adapter
                adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, namesList);
                // setting adapter in the list
                listView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Sorry, no data available", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
