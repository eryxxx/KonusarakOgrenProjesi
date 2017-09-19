package com.example.asus.konusarakogrenprojesi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class PageActivity extends AppCompatActivity {
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        webView=(WebView) findViewById(R.id.webView);
        final Bundle bundle=getIntent().getExtras();
        final String getContent=bundle.getString("getContent");
       // Log.v(getContent, "did something");
        final String getUrl=bundle.getString("getUrl");
        webView.loadUrl(getUrl);

        Button button = (Button) findViewById(R.id.listButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), wordActivity.class);
                intent.putExtra("getUrl",getUrl);

                Log.v("URL",getUrl);

                startActivity(intent);


            }
        });

    }






}
