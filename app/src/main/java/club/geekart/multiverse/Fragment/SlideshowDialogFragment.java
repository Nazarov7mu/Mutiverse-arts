package club.geekart.multiverse.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import club.geekart.multiverse.Helper.SaveImageHelper;
import club.geekart.multiverse.Model.Image;
import club.geekart.multiverse.R;
import dmax.dialog.SpotsDialog;
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

public class SlideshowDialogFragment extends DialogFragment {

    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<club.geekart.multiverse.Model.Image> images;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    private int selectedPosition = 0;
    Image image;

    AlertDialog dialog;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    FabSpeedDial fabSpeedDial;

    public static String url_of_any_image = "null";
    public static String name_of_any_image = "null";


    public static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        // lblCount = (TextView) v.findViewById(R.id.lbl_count);
        lblTitle = (TextView) v.findViewById(R.id.title);
        lblDate = (TextView) v.findViewById(R.id.date);
        fabSpeedDial = (FabSpeedDial) v.findViewById(R.id.fab);


        // Initialize MENU buttons


        fabSpeedDial.addOnStateChangeListener(new FabSpeedDial.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean open) {

            }
        });

        fabSpeedDial.addOnMenuItemClickListener(new FabSpeedDial.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionButton fab, TextView textView, int itemId) {
                // do something
                //Toast.makeText(getActivity(), "Click: " + textView.getText().toString(), Toast.LENGTH_SHORT).show();

                if (textView.getText().toString().equals("Download"))
                    Download_Image();
                if (textView.getText().toString().equals("Share"))
                    Share();
                if (textView.getText().toString().equals("Set as wallpaper"))
                    Set_As_Wallpaper();

            }
        });


        images = (ArrayList<Image>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");

        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + images.size());

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        setCurrentItem(selectedPosition);


        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //  page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    private void displayMetaInfo(int position) {
        //lblCount.setText((position + 1) + " of " + images.size());

        Image image = images.get(position);
        lblTitle.setText(image.getName());
        lblDate.setText(image.getTimestamp());

        url_of_any_image = image.getLarge();
        name_of_any_image = image.getName();
    }

    private com.squareup.picasso.Target target = new com.squareup.picasso.Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getActivity().getApplicationContext());
            try {
                wallpaperManager.setBitmap(bitmap);
                //Snackbar.make(rootLayout, "Wallpaper was set", Snackbar.LENGTH_SHORT).show();//change
                dialog.dismiss();
                StyleableToast.makeText(getActivity().getApplicationContext(), "Wallpaper set", Toast.LENGTH_LONG, R.style.mytoastset).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    private com.squareup.picasso.Target targetDownload = new com.squareup.picasso.Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
           // WallpaperManager wallpaperManager = WallpaperManager.getInstance(getActivity().getApplicationContext());

                MediaStore.Images.Media.insertImage(getActivity().getApplicationContext().getContentResolver(),bitmap,name_of_any_image,"Wizarding arts");
                //Snackbar.make(rootLayout, "Wallpaper was set", Snackbar.LENGTH_SHORT).show();//change
            dialog.dismiss();
            StyleableToast.makeText(getActivity().getApplicationContext(), "Download success", Toast.LENGTH_LONG, R.style.mytoastset).show();

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

    }

    public void Share() {
        Intent sharingIntent = new Intent();

        sharingIntent.setAction(Intent.ACTION_SEND);

        sharingIntent.setType("text/plain");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, name_of_any_image);

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, url_of_any_image);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }


    public void Download_Image() {
        //Toast.makeText(getActivity(), "Click: on download", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Image.PERMISSION_REQUEST_CODE);
            } else {
                dialog = new SpotsDialog(getActivity());
                dialog.show();
                dialog.setMessage("Please Wait ...");

                String filename = name_of_any_image;
                Picasso.with(getActivity().getBaseContext())
                        .load(url_of_any_image)
                        .into(targetDownload);


            }
    }


    public void Set_As_Wallpaper() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        //image = images.get(currentpos);
        dialog = new SpotsDialog(getActivity());
        dialog.show();
        dialog.setMessage("Please Wait ...");

        Picasso.with(getActivity())
                .load(url_of_any_image)
                .centerCrop()
                .resize(width,height)
                .into(target);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dialog = new SpotsDialog(getActivity());
                    dialog.show();
                    dialog.setMessage("Please Wait ...");

                    String filename = name_of_any_image;

                    Picasso.with(getActivity().getBaseContext())
                            .load(url_of_any_image)
                            .into(targetDownload);
                } else
                    StyleableToast.makeText(getActivity(), "You need to accept this permission to download image", Toast.LENGTH_LONG, R.style.mytoasterror).show();
                //Toast.makeText(this, "You need to accept this permission to download image", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }





     /* Glide.with(getActivity()).load(image.getLarge())
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super
                                                Bitmap> glideAnimation) {

                        if (isStoragePermissionGranted())
                            try {
                                WallpaperManager.getInstance(getActivity()).setBitmap(resource);
                            }
                            catch (Exception e)
                            {
                            }

                    }
                });  */
    //Toast.makeText(getActivity(), "Click: on Set_As_Wallpaper", Toast.LENGTH_SHORT).show();


    public static Bitmap cropCenter(Bitmap bmp) {
        int dimension = Math.min(bmp.getWidth(), bmp.getHeight());
        return ThumbnailUtils.extractThumbnail(bmp, dimension, dimension);
    }


    //  adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);
            final ProgressBar progressBar = view.findViewById(R.id.progress);
            image = images.get(position);

            Glide.with(getActivity()).load(image.getLarge())
                    .thumbnail(0.5f)
                    .dontAnimate()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(imageViewPreview);

            container.addView(view);

            return view;
        }


        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}




























