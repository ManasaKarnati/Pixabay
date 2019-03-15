package com.example.acer.pixabay;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ImageDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>
{
    RecyclerView rv;
    ProgressBar pb;
    ArrayList<ImageModels> imageModels;
    String imageurl="https://pixabay.com/api/?key=10850293-beb2bf524b904f5509c8eb1b8&q=";
    String string;
    public static final int Loader_id=12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        imageModels=new ArrayList<>();
        pb=findViewById(R.id.progressbar);
        rv=findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(new PixabayAdapter(this,imageModels));
        string=getIntent().getStringExtra("data");
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

            getSupportLoaderManager().initLoader(Loader_id,null,this);
        }
        else
        {
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
            alertDialog.setTitle("Alert Box");
            alertDialog.setMessage("You havent entered any Number");
            alertDialog.setIcon(R.drawable.ic_add_alert_black_24dp);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    finish();
                }
            });
            AlertDialog dialog=alertDialog.create();
            dialog.show();
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground()
            {
                try
                {
                    URL url = new URL(imageurl+string);
                    Log.i("imageurl", String.valueOf(url));
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Scanner scanner = new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()) {
                        return scanner.next();
                    } else {
                        return null;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                pb.setVisibility(View.VISIBLE);
                forceLoad();
            }
        }
                ;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s)
    {
        pb.setVisibility(View.GONE);

        if (s!=null)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("hits");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject hits = jsonArray.getJSONObject(i);

                    String imageurl = hits.getString("largeImageURL");
                    Log.i("image",imageurl.toString());
                    imageModels.add(new ImageModels(imageurl));


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
    public static int index = -1;
    public static int top = -1;
    LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);

    @Override
    public void onPause()
    {
        super.onPause();
        index = mLayoutManager.findFirstVisibleItemPosition();
        View v = rv.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() -rv.getPaddingTop());
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(index != -1)
        {
            mLayoutManager.scrollToPositionWithOffset( index, top);
        }
    }


}
