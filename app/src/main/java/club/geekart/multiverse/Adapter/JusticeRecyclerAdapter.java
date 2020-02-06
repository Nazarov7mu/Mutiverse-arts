package club.geekart.multiverse.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import club.geekart.multiverse.R;
import com.squareup.picasso.Picasso;
import com.yayandroid.parallaxrecyclerview.ParallaxViewHolder;

public class JusticeRecyclerAdapter extends RecyclerView.Adapter<JusticeRecyclerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private String[] imageUrls = new String[]{
            "http://dc.geekart.club/CategoryImages/Superman.jpg",
            "http://dc.geekart.club/CategoryImages/Batman.jpg",
            "http://dc.geekart.club/CategoryImages/Wonder.jpg",
            "http://dc.geekart.club/CategoryImages/Aquaman.jpg",
            "http://dc.geekart.club/CategoryImages/Flash.jpg",
            "http://dc.geekart.club/CategoryImages/Shazam.jpg",
            "http://dc.geekart.club/CategoryImages/Cyborg.jpg",
            "http://dc.geekart.club/CategoryImages/Lantern.jpg",
            "http://dc.geekart.club/CategoryImages/Supergirl.jpg",
            "http://dc.geekart.club/CategoryImages/Arrow.jpg",
    };
    private String[] categoryNames = new String[]{
            "Superman",
            "Batman",
            "Wonder Woman",
            "Aquaman",
            "The Flash",
            "Shazam",
            "Cyborg",
            "Green Lantern",
            "Supergirl",
            "Green Arrow",
    };

    public JusticeRecyclerAdapter(Context context) {
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
