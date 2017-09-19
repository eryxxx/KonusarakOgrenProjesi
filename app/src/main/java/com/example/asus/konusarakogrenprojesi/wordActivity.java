package com.example.asus.konusarakogrenprojesi;

import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class wordActivity extends AppCompatActivity   {
    String first,second,third,fourth,fifth,firstw,secondw,thirdw,fourthw,fifthw;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        final Bundle bundle=getIntent().getExtras();
        String url=bundle.getString("getUrl");
        new JSoupParse().execute(url);



    }





    private static Set<String> findDuplicateWord(String iString) {
        if(iString == null || iString.isEmpty()){
            return Collections.emptySet();
        }else{
            Set<String> duplicateWords=new HashSet<String>();
            iString = iString.replace("\"", "").replace("'", "").replace("-", " ").replace("\\n", " ").replace(".", " ").replace(",", " ").replace(";", " ").replace(":", " ").toLowerCase();
            String [] words=iString.split("\\s+");
            Set<String> set=new HashSet<String>();
            for(String word:words){
                if(!set.add(word)){

                    duplicateWords.add(word);
                }
            }
            return duplicateWords;

        }

    }

    private static String yandex_translate(String yandex_lang,String yandex_text) throws IOException, ParseException {

        String yandex_key = "trnsl.1.1.20170125T115540Z.4f982b8f890a7c24.75feb0af0d44e2aa8a7ec0bd6bf7542f7b389055";


        String yandex_url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="+yandex_key+"&lang="+yandex_lang+"&text="+yandex_text;


        URL yandexurl = new URL(yandex_url);

        try {

            URLConnection httpUrlConnection = yandexurl.openConnection();

            InputStream inputStream = httpUrlConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


            String line=bufferedReader.readLine();


            JsonParser parser = new JsonParser();


            JsonObject jsonObject = (JsonObject) parser.parse(line);;


            JsonArray msg = (JsonArray)jsonObject.get("text");

            return msg.get(0).toString();

        } catch (Exception e) {
            return e.getMessage();
        }


    }


    public  class JSoupParse extends AsyncTask<String,String,String>{

        ProgressDialog progressDialog;
        //String first,second,third,fourth,fifth,firstw,secondw,thirdw,fourthw,fifthw;




        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            progressDialog=new ProgressDialog(wordActivity.this);
            progressDialog.setMessage("loading...");
            progressDialog.show();
        }



        @Override
        protected String doInBackground(String... strings) {
            String url=strings[0];

            try {
                Document doc = Jsoup.connect(url).timeout(20000).get();
                Elements e= doc.getElementsByAttributeValueContaining("class", "article-body-component");
                Element e1=e.first();
                Elements paragraph=e1.getElementsByTag("p");

                String pConcatenated="";
                for(Element ParagraphText:paragraph)
                {

                    pConcatenated+= ParagraphText.text();
                }
                Log.v("pConcatenated",pConcatenated);

                Set<String> duplicateWords=findDuplicateWord(pConcatenated);
                Log.v("Words",duplicateWords.toString());
                String [] st=new String[duplicateWords.size()];
                duplicateWords.toArray(st);
                Log.v("Words",st[0]);
                Log.v("Words",st[1]);
                Log.v("Words",st[2]);
                Log.v("Words",st[3]);
                Log.v("Words",st[4]);
                first=st[0];
                second=st[1];
                third=st[2];
                fourth=st[3];
                fifth=st[4];
                firstw=yandex_translate("en-tr",st[0]);
                secondw=yandex_translate("en-tr",st[1]);
                thirdw=yandex_translate("en-tr",st[2]);
                fourthw=yandex_translate("en-tr",st[3]);
                fifthw=yandex_translate("en-tr",st[4]);

                Log.v("Translate",firstw);
                Log.v("Translate",secondw);
                Log.v("Translate",thirdw);
                Log.v("Translate",fourthw);
                Log.v("Translate",fifthw);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }


            return null;

        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            TextView txtfirstWord=(TextView)findViewById(R.id.firstWord);
            TextView txtsecondWord=(TextView)findViewById(R.id.secondWord);
            TextView txtthirdWord=(TextView)findViewById(R.id.thirdWord);
            TextView txtfourthWord=(TextView)findViewById(R.id.fourthWord);
            TextView txtfifthWord=(TextView)findViewById(R.id.fifthWord);
            TextView txtfirstTranslatedWord=(TextView)findViewById(R.id.firstTranslatedWord);
            TextView txtsecondTranslatedWord=(TextView)findViewById(R.id.secondTranslatedWord);
            TextView txtthirdTranslatedWord=(TextView)findViewById(R.id.thirdTranslatedWord);
            TextView txtfourthTranslatedWord=(TextView)findViewById(R.id.fourthTranslatedWord);
            TextView txtfifthTranslatedWord=(TextView)findViewById(R.id.fifthTranslatedWord);
            txtfirstTranslatedWord.setText(firstw);
            txtfifthTranslatedWord.setText(fifthw);
            txtfourthTranslatedWord.setText(fourthw);
            txtsecondTranslatedWord.setText(secondw);
            txtthirdTranslatedWord.setText(thirdw);

            txtfirstWord.setText(first);
            txtsecondWord.setText(second);
            txtthirdWord.setText(third);
            txtfourthWord.setText(fourth);
            txtfifthWord.setText(fifth);



        }
    }




}
