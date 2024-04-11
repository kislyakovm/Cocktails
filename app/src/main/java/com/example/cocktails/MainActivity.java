package com.example.cocktails;

import android.app.DownloadManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private CocktailAdapter cocktailAdapter;
    ArrayList<Cocktails> cocktails;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView=findViewById(R.id.RecycleView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cocktails = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        getCocktails();


    }

    private void getCocktails() {
        String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?f=a";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("drinks");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String title, picture, category, instruction;
                        title = jsonObject1.getString("strDrink");
                        picture = jsonObject1.getString("strDrinkThumb");
                        category = jsonObject1.getString("strCategory");
                        instruction = jsonObject1.getString("strInstructions");

                        Cocktails cocktail = new Cocktails();
                        cocktail.setTitle(title);
                        cocktail.setPictureUrl(picture);
                        cocktail.setCategory(category);
                        cocktail.setInstructions(instruction);

                        cocktails.add(cocktail);

                    }

                    cocktailAdapter = new CocktailAdapter(MainActivity.this, cocktails);
                    recyclerView.setAdapter(cocktailAdapter);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

}