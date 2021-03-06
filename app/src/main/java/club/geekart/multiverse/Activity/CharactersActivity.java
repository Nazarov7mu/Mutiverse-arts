package club.geekart.multiverse.Activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import club.geekart.multiverse.Fragment.SlideshowDialogFragment;
import club.geekart.multiverse.Adapter.GalleryAdapter;
import club.geekart.multiverse.App.AppController;
import club.geekart.multiverse.Model.Image;
import club.geekart.multiverse.R;

public class CharactersActivity extends AppCompatActivity {

    private String TAG = CharactersActivity.class.getSimpleName();
    private static final String endpoint = "http://dc.geekart.club/Characters.json";
    private ArrayList<club.geekart.multiverse.Model.Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;

    //public String Character_name= JusticeCategoriesActivity.Category_name;
    public String Character_json= JusticeCategoriesActivity.Category_json;
    String Category_Name;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Category_Name= null;
            } else {
                Category_Name= extras.getString("category");
            }
        } else {
            Category_Name= (String) savedInstanceState.getSerializable("category");
        }

        MobileAds.initialize(this, getResources().getString(R.string.appID));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.Admob_interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        TextView toolbar_title= (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(Category_Name);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
                if (mInterstitialAd.isLoaded())
                    mInterstitialAd.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        fetchImages();
        //Toast.makeText(getApplicationContext(),JusticeCategoriesActivity.Category_name,Toast.LENGTH_LONG).show();
    }

    private void fetchImages() {

        pDialog.setMessage("Downloading ...");
        //pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(endpoint,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        images.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Image image = new Image();
                                image.setName(object.getString("name"));

                                JSONObject url = object.getJSONObject("url");
                                image.setSmall(url.getString("large"));
                                image.setMedium(url.getString("large"));
                                image.setLarge(url.getString("large"));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    image.setTimestamp(object.getString("timestamp"));
                                }
                                if (object.getString("category").equals(Category_Name))
                                {
                                    images.add(image);
                                }


                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }
}






























