package club.geekart.multiverse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yayandroid.parallaxrecyclerview.ParallaxRecyclerView;

import club.geekart.multiverse.Adapter.InjusticeRecyclerAdapter;
import club.geekart.multiverse.Adapter.JusticeRecyclerAdapter;
import club.geekart.multiverse.R;

public class InjusticeCategoriesActivity extends AppCompatActivity{


    public static String Category_name;
    public static String Category_json;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.injustice_activity);
        ParallaxRecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(InjusticeCategoriesActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new InjusticeRecyclerAdapter(InjusticeCategoriesActivity.this));

        recyclerView.addOnItemTouchListener(

                 new RecyclerItemClickListener(InjusticeCategoriesActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    public void onItemClick(View view, int position) {
                        // do whatever
                        //json_of_category = "";
                        String toolbar_title= "";

                        if(position == 0)
                        {
                            Category_name ="Darkseid";
                        }else if(position == 1){
                            Category_name = "Joker";
                        }else if(position == 2){
                            Category_name = "Harley Quinn";
                        }else if(position == 3){
                            Category_name = "Black Manta";
                        }else if(position == 4){
                            Category_name = "Reverse Flash";
                        }else if(position == 5){
                            Category_name = "Black Adam";
                        }else if(position == 6){
                            Category_name = "Deathstroke";
                        }else if(position == 7){
                            Category_name = "Bane";
                        }else if(position == 8){
                            Category_name = "Brainiac";
                        }else if(position == 9){
                            Category_name = "Deadshot";
                        }

                        Intent intent = new Intent(getApplicationContext(), CharactersActivity.class);
                        intent.putExtra("category", Category_name);
                        startActivity(intent);
                    }

                     @Override
                     public void onLongItemClick(View view, int position) {

                     }

                    /*@Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }*/
                })
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

}
