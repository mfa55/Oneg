package jram_mack.oneg;

/**
 * Created by white_000 on 3/30/2017.
 * Adapter For MyRequest Activity
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static jram_mack.oneg.MyRequestsActivity.listOfMyRequests;
import static jram_mack.oneg.HomeActivity.mDatabase;

public class SampleAdpater extends RecyclerView.Adapter<SampleAdpater.ViewHolder>  {
    private List<RecyclerItem> listItems;
    private Context mContext;
    public SampleAdpater(List<RecyclerItem> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }
    @Override
    public SampleAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_two,parent,false);

        return new SampleAdpater.ViewHolder(v);
    }



    @Override

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final RecyclerItem itemList = listItems.get(position);
        holder.txtTitle.setText(itemList.getTitle());
        holder.txtDescription.setText(itemList.getDescription());

        holder.txtRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestFunction r = listOfMyRequests.get(position);
                listItems.remove(position);

                try{
                    listOfMyRequests.get(position).reverseStatus();
                    mDatabase.child(r.getKey()).child("status").setValue(r.getStatus());
                } catch (Exception e){
                    e.printStackTrace();
                }
                mDatabase.child(r.getKey()).child("status").setValue(r.getStatus());

                listOfMyRequests.remove(position);
                notifyDataSetChanged();
                Toast.makeText(mContext, "REQUEST REMOVED",Toast.LENGTH_LONG).show();

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

        public TextView txtRemove;
        public ViewHolder(final View itemView) {
            super(itemView);


            txtRemove = (TextView) itemView.findViewById(R.id.txtRemove);
            txtDescription =(TextView) itemView.findViewById(R.id.txtDescription2);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle2);



        }
//
    }

}
