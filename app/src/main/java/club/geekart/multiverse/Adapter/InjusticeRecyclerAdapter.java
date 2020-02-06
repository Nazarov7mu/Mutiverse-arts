package club.geekart.multiverse.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yayandroid.parallaxrecyclerview.ParallaxViewHolder;

import club.geekart.multiverse.R;

public class InjusticeRecyclerAdapter extends RecyclerView.Adapter<InjusticeRecyclerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private String[] imageUrls = new String[]{
            "http://dc.geekart.club/CategoryImages/Darkseid.jpg",
            "http://dc.geekart.club/CategoryImages/Joker.jpg",
            "http://dc.geekart.club/CategoryImages/Harley.jpg",
            "http://dc.geekart.club/CategoryImages/Manta.jpg",
            "http://dc.geekart.club/CategoryImages/Reverse.jpg",
            "http://dc.geekart.club/CategoryImages/Adam.jpg",
            "http://dc.geekart.club/CategoryImages/Deathstroke.jpg",
            "http://dc.geekart.club/CategoryImages/Bane.jpg",
            "http://dc.geekart.club/CategoryImages/Brainiac.jpg",
            "http://dc.geekart.club/CategoryImages/Deadshot.jpg",
    };
    private String[] categoryNames = new String[]{
            "Darkseid",
            "Joker",
            "Harley Quinn",
            "Black Manta",
            "Reverse Flash",
            "Black Adam",
            "Deathstroke",
            "Bane",
            "Brainiac",
            "Deadshot",
    };

    public InjusticeRecyclerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Picasso.with(context).load(imageUrls[position % imageUrls.length]).into(viewHolder.getBackgroundImage());
        viewHolder.getTextView().setText(categoryNames[position]);

        // # CAUTION:
        // Important to call this method
        viewHolder.getBackgroundImage().reuse();
    }

    @Override
    public int getItemCount() {
        return 10;
    }
    /**
     * # CAUTION:
     * ViewHolder must extend from ParallaxViewHolder
     */
    public static class ViewHolder extends ParallaxViewHolder {

        private final TextView textView;

        private ViewHolder(View v) {
            super(v);

            textView = v.findViewById(R.id.label);
        }

        @Override
        public int getParallaxImageId() {
            return R.id.backgroundImage;
        }

        private TextView getTextView() {
            return textView;
        }
    }
}
