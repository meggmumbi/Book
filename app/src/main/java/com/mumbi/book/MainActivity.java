package com.mumbi.book;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mLoadingProgress;
    private RecyclerView rvBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingProgress = (ProgressBar) findViewById(R.id.progressBar);
        rvBooks = (RecyclerView) findViewById(R.id.Rv_books);
        LinearLayoutManager booksLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvBooks.setLayoutManager(booksLayoutManager);

        try {
            URL bookUrl = ApiUtil.buildUrl("cooking");
            new BooksQueryTask().execute(bookUrl);
        }
        catch (Exception e){
            Log.d("Error", e.getMessage());
        }

    }
    public  class BooksQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result  = null;

            try {
                result = ApiUtil.getJson(searchURL);
            }
            catch (IOException e){
                Log.d("Error", e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            TextView tverror = (TextView) findViewById(R.id.tv_error);
            mLoadingProgress.setVisibility(View.INVISIBLE);


            if (result == null ){
                rvBooks.setVisibility(View.INVISIBLE);
                tverror.setVisibility(View.VISIBLE);
            }
            else {
                rvBooks.setVisibility(View.VISIBLE);
                tverror.setVisibility(View.INVISIBLE);
            }
            ArrayList<Book> books  = ApiUtil.getBooksFromJson(result);
            String resultString = "";


            BooksAdapter adapter = new BooksAdapter(books);
            rvBooks.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }


}
