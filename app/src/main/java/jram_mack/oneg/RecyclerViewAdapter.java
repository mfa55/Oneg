package jram_mack.oneg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static jram_mack.oneg.Accepted.listOfAcceptedRequests;

/**
 * Created by white_000 on 4/14/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<RecyclerItem> listItems;
    private Context mContext;
    public RecyclerViewAdapter(List<RecyclerItem> listItems, Context mContext){
        this.listItems = listItems;
        this.mContext = mContext;
    }
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_3,parent,false);

        return new RecyclerViewAdapter.ViewHolder(v);
    }





    @Override

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final RecyclerItem itemList = listItems.get(position);
        holder.txtTitle.setText(itemList.getTitle());
        holder.txtDescription.setText(itemList.getDescription());
        holder.callCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", listOfAcceptedRequests.get(position).getPhoneNumberOnListView(), null)));
            }
        });

    }
    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;
        public TextView txtDescription;

        public ImageButton callCircle;
        public ViewHolder(final View itemView) {
            super(itemView);
            callCircle = (ImageButton) itemView.findViewById(R.id.circlecall2);
            txtDescription =(TextView) itemView.findViewById(R.id.txtDescription3);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle3);



        }
//
    }
}
