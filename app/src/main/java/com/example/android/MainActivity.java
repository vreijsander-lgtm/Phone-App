package com.example.android;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Executable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView scroller;
    public List<fotoObject> FotoList = new ArrayList<>();

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
        scroller = findViewById(R.id.scrolller);

        scroller.setLayoutManager(new LinearLayoutManager(this));

        ZeldaFotoAdapter adapter = new ZeldaFotoAdapter(FotoList);

        scroller.setAdapter(adapter);

        fetch_main(adapter);

    }
    private void fetch_main(ZeldaFotoAdapter adapter) {
        new Thread(() -> {
            try {
                URL url = new URL("https://botw-compendium.herokuapp.com/api/v3/compendium/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray dataArray = jsonObject.getJSONArray("data");

                runOnUiThread(() -> {

                    for (int i = 0; i < dataArray.length(); i++) {
                        try {
                            JSONObject item = dataArray.getJSONObject(i);
                            String imageUrl = item.getString("image");
                            String objectId = item.getString("id");
                            FotoList.add(new fotoObject(objectId, imageUrl));
//                            ImageView imageView = new ImageView(MainActivity.this);
//                            imageView.setLayoutParams(new LinearLayout.LayoutParams(
//                                    LinearLayout.LayoutParams.MATCH_PARENT,
//                                    LinearLayout.LayoutParams.WRAP_CONTENT
//                            ));
//                            imageView.setAdjustViewBounds(true);
//
//                            Glide.with(MainActivity.this)
//                                    .load(imageUrl)
//                                    .into(imageView);

                            //container.addView(imageView);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}