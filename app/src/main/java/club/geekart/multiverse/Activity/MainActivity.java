package club.geekart.multiverse.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.florent37.tutoshowcase.TutoShowcase;
import com.github.pwittchen.swipe.library.rx2.Swipe;
import com.github.pwittchen.swipe.library.rx2.SwipeListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import club.geekart.multiverse.R;

public class MainActivity extends AppCompatActivity {

    private Swipe swipe;
    ImageButton about;

    String lastSwipe="";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuto_showcase_activity_main);
        View view = (View) findViewById(R.id.main_id);




        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
                {   // Toast.makeText(MainActivity.this, "Down", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_UP)
                if(Internet_Connection())
                {
                    //Toast.makeText(MainActivity.this, lastSwipe, Toast.LENGTH_SHORT).show();
                    if(lastSwipe.equals("right")) {
                        lastSwipe="";
                        CallInjusticeCategories();

                    }
                    if(lastSwipe.equals("left")){
                        lastSwipe="";
                        CallJusticeCategories();

                    }
                    if(lastSwipe.equals("up")){
                        lastSwipe="";


                    }
                    if(lastSwipe.equals("down")) {
                        lastSwipe="";
                        //CallCategories();
                        CallOthers();

                    }

                    return false;
                }
                else
                {
                    if(!Internet_Connection())
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialogTheme);
                        builder .setTitle(R.string.internet_error)
                                .setMessage(R.string.internet_error_des)
                                .setCancelable(true)
                                .setPositiveButton("reload", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = getBaseContext().getPackageManager()
                                                .getLaunchIntentForPackage( getBaseContext().getPackageName());
                                        if(i != null)
                                        {
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                                })
                                .setNegativeButton("exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //deleteCache(getApplicationContext());
                                        MainActivity.this.finish();

                                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                        homeIntent.addCategory(Intent.CATEGORY_HOME);
                                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(homeIntent);
                                        finishAffinity();
                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
                return false;
            }
        });


        swipe = new Swipe(25,50);

        about = (ImageButton) findViewById(R.id.about);
       // about.setVisibility(View.INVISIBLE);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAbout = new Intent(MainActivity.this, AboutUs.class);
                startActivity(gotoAbout);
            }
        });

        swipe.setListener(new SwipeListener() {

            @Override public void onSwipingLeft(final MotionEvent event) {

            }

            @Override public boolean onSwipedLeft(final MotionEvent event) {
                lastSwipe="left";
               // CallGood();
                return true;
            }

            @Override public void onSwipingRight(final MotionEvent event) {

            }

            @Override public boolean onSwipedRight(final MotionEvent event) {
                lastSwipe="right";
               // CallEvil();
                return true;
            }

            @Override public void onSwipingUp(final MotionEvent event) {

            }

            @Override public boolean onSwipedUp(final MotionEvent event) {
                lastSwipe="up";
                //CallCategories();
                return true;
            }

            @Override public void onSwipingDown(final MotionEvent event) {

            }

            @Override public boolean onSwipedDown(final MotionEvent event) {
                lastSwipe="down";
                //CallOthers();
                return true;
            }

        });

        // Initialize displayTuto button
        /*findViewById(R.id.display).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayTuto();
            }
        });*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                displayTutoLeft();
            }
        }, 300);

       // if(swipeactivator)
        boolean oneswiper=true;


    }

    //INTENTS

    public void CallOthers() {
        Intent intent = new Intent(getApplicationContext(), Others.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }
    public void CallJusticeCategories() {
        Intent intent = new Intent(getApplicationContext(), JusticeCategoriesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }
    public void CallInjusticeCategories() {
        Intent intent = new Intent(getApplicationContext(), InjusticeCategoriesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    //Set up TUTORIAL
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.tutoshowcase_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void displayTutoLeft() {
        TutoShowcase.from(this)
                .setListener(new TutoShowcase.Listener() {
                    @Override
                    public void onDismissed() {
                        //Toast.makeText(MainActivity.this, "Tutorial dismissed", Toast.LENGTH_SHORT).show();
                        displayTutoRight();
                    }
                })
                .setContentView(R.layout.tuto_showcase_swipe_left)
                .setFitsSystemWindows(true)
                .on(R.id.swipable)
                .displaySwipableLeft()
                .delayed(399)
                .animated(true)
                //.show();
                .showOnce(String.valueOf(1)); //просто поменяйте эту строчку на .show();
                                              //если хотите потестить tutoshow несколько раз
                                              //новая компиляция не запустит его тк в Кеше осталась
                                              //инфа что ее запускали уже. (я через смартфон проверил)
    }
    protected void displayTutoRight() {
        TutoShowcase.from(this)
                .setListener(new TutoShowcase.Listener() {
                    @Override
                    public void onDismissed() {
                        //Toast.makeText(MainActivity.this, "Tutorial dismissed", Toast.LENGTH_SHORT).show();
                        displayScrollable();
                    }
                })
                .setContentView(R.layout.tuto_showcase_swipe_right)
                .setFitsSystemWindows(true)
                .on(R.id.swipable)
                .displaySwipableRight()
                .delayed(399)
                .animated(true)
                .show();

    }

    protected void displayScrollable() {
        TutoShowcase.from(this)
                .setListener(new TutoShowcase.Listener() {
                    @Override
                    public void onDismissed() {
                        displayAbout();
                    }
                })
                .setContentView(R.layout.tuto_showcase_up_down)
                .setFitsSystemWindows(true)
                .on(R.id.swipable)
                .displayScrollable()
                .delayed(399)
                .animated(true)
                .show();
    }

    protected void displayAbout() {
        TutoShowcase.from(this)
                .setListener(new TutoShowcase.Listener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onDismissed() {
                        //Intent gotoAbout = new Intent(MainActivity.this, AboutUs.class);
                        //startActivity(gotoAbout);

                    }
                })
                .setContentView(R.layout.tuto_showcase_about)
                .setFitsSystemWindows(true)
                .on(R.id.about)
                .addCircle()
                .withBorder()
                .onClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent gotoAbout = new Intent(MainActivity.this, AboutUs.class);
                        startActivity(gotoAbout);
                    }
                })
                .show();

    }

    private boolean Internet_Connection()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager !=null)
        {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        else
            return false;
    }
    /*protected void onLeaveThisActivity() {
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
    }*/


    @Override public boolean dispatchTouchEvent(MotionEvent event) {
        swipe.dispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

   @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        onStartNewActivity();
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
        onStartNewActivity();
    }

    protected void onStartNewActivity() {

    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finishAffinity();
        } else {
            StyleableToast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_LONG, R.style.mytoastset).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }
}
