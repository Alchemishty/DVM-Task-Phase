package com.example.scrapit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.w3c.dom.Text;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String itemName, searchValue, URL, imgUrl, amazonLink;
    EditText EditText;
    TextView text;
    Button button;
    ImageView image;
    boolean hasFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.name);
        button = (Button) findViewById(R.id.button) ;
        EditText = (EditText) findViewById(R.id.EditText);
        image = (ImageView) findViewById(R.id.iv);
        amazonLink = "https://www.amazon.in/s?k=";

        button.setOnClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View v) {
        new MyTask().execute();
        EditText.setText("");
    };

    public void failedSearch(boolean hasFailed) {
        if(hasFailed)
        {   Toast toast = Toast.makeText(getApplicationContext(),"Failed to find a match", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void>{
        String result;
        @Override
        protected Void doInBackground(Void... voids) {
            Document doc = null;
            try {
                hasFailed = false;
                searchValue = EditText.getText().toString();
                URL = amazonLink + searchValue;
                doc = Jsoup.connect(URL)
                        .timeout(6000).get();

                Element name = doc.select("div[data-component-type='s-search-result']").select("h2 > a > span").first();
                if(name == null)
                {
                    hasFailed = true;
                    return null;
                }
                itemName = name.text();
                Element image = doc.select("div[data-component-type='s-search-result']").select("img").first();
                imgUrl = image.absUrl("src");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            failedSearch(hasFailed);
            Glide.with(MainActivity.this)
                    .load(imgUrl)
                    .fitCenter()
                    .into(image);
            text.setText(itemName);
            super.onPostExecute(aVoid);
        }
    }
}