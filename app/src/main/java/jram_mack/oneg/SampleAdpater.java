package jram_mack.oneg;


/**
 * @author  JRAM-MACK
 * @author  CMPS253
 * @since 2/11/2017
 *
 * @version 1.0
 *
 * this class defines the shape of the card view in MyRequestsActivity
 *
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static jram_mack.oneg.MyRequestsActivity.listOfMyRequests;


public class SampleAdpater extends RecyclerView.Adapter<SampleAdpater.ViewHolder>  {
    private List<RecyclerItem> listItems;
    private Context mContext;
    protected DatabaseReference mDatabase;

    /**
     *
     * @param listItems list containing all the cards
     * @param mContext context of the card view
     */
    public SampleAdpater(List<RecyclerItem> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    @Override
    public SampleAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_two,parent,false);

        return new SampleAdpater.ViewHolder(v);
    }


    /**
     *
     * @param holder : card view that user is clicking on
     * @param position : position of the card that was clicked in the list
     */
    @Override

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final RecyclerItem itemList = listItems.get(position);
        holder.txtTitle.setText(itemList.getTitle());
        holder.txtDescription.setText(itemList.getDescription());

        holder.txtRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase = FirebaseDatabase.getInstance().getReference("Requests" + "/"
                        + RegisterActivity.user.getCity() + "/" + RegisterActivity.user.getBloodType()
                );
                Request r = listOfMyRequests.get(position);
                listItems.remove(position);

                try{
                    listOfMyRequests.get(position).reverseStatus();

                    mDatabase.child(r.getKey()).child("status").setValue(r.getStatus());
                } catch (Exception e){
                    e.printStackTrace();
                }
                mDatabase.child(r.getKey()).child("status").setValue(r.getStatus());

                //NEW

                mDatabase = FirebaseDatabase.getInstance().getReference("ListOfAllRequests");

                try{
                    mDatabase.child(r.getKey()).child("status").setValue(r.getStatus());
                } catch (Exception e){
                    e.printStackTrace();
                }
                mDatabase.child(r.getKey()).child("status").setValue(r.getStatus());
//

                listOfMyRequests.remove(position);
                notifyDataSetChanged();
                Toast.makeText(mContext, "REQUEST REMOVED",Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     *
     * @return the UI of the MyRequests card view
     */
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
