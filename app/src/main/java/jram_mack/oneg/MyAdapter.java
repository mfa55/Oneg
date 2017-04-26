package jram_mack.oneg;
/**
 * @author  JRAM-MACK
 * @author  CMPS253
 * @since 2/11/2017
 *
 * @version 1.0
 *
 * card view for the home activity, where the user can accept or reject any request and call the recipient as well
 *
 */

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

import static jram_mack.oneg.HomeActivity.listOfRequestsHome;
import static jram_mack.oneg.HomeActivity.mDatabase;
import static jram_mack.oneg.Accepted.acceptedRequestsListItem;
import static jram_mack.oneg.Accepted.listOfAcceptedRequests;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {
    private List<RecyclerItem> listItems;
    private Context mContext;
    public static int index;

    /**
     *
     * @param listItems list containing all the cards
     * @param mContext context of the card view
     */
    public MyAdapter(List<RecyclerItem> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);

        return new ViewHolder(v);
    }


    /**
     *
     * @param holder : card view that user is clicking on
     * @param position : position of the card that was clicked in the list
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        index = position;
        final RecyclerItem itemList = listItems.get(position);
        holder.txtTitle.setText(itemList.getTitle());
        holder.txtDescription.setText(itemList.getDescription());

        holder.callCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", listOfRequestsHome.get(position).getPhoneNumberOnListView(), null)));
            }
        });
        holder.txtAccept.setOnClickListener(new View.OnClickListener() { //Accept Button
            @Override
            public void onClick(View view) {
                Request r = listOfRequestsHome.get(position);
                r.acceptUnit();
                listItems.remove(position);
                if(r.getUnits() != 0){
                    listItems.add(position, new RecyclerItem(r.getHospital(), r.toString()));
                    listItems.remove(position);

                } else{
                    listOfRequestsHome.get(position).reverseStatus();
                    mDatabase.child(r.getKey()).child("status").setValue(r.getStatus());

                }
                try {
                    mDatabase.child(r.getKey()).child("units").setValue(r.getUnits());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mDatabase.child(r.getKey()).child(RegisterActivity.user.getPhoneNumber()).setValue("Accepted");
                listOfAcceptedRequests.add(listOfRequestsHome.get(position));
                acceptedRequestsListItem.add(new RecyclerItem(r.getHospital(), r.toString()));
                listOfRequestsHome.remove(position);
                notifyDataSetChanged();
                Toast.makeText(mContext, "REQUEST ACCEPTED",Toast.LENGTH_LONG).show();
            }
        });
        holder.txtRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request r = listOfRequestsHome.get(position);
                mDatabase.child(r.getKey()).child(RegisterActivity.user.getPhoneNumber()).setValue("Removed");
                listItems.remove(position);
                notifyDataSetChanged();
                Toast.makeText(mContext, "REQUEST REJECTED",Toast.LENGTH_LONG).show();

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
        public TextView txtOptionDigit;
        public ImageButton callCircle;
        public TextView txtAccept;
        public TextView txtRejected;
        public ViewHolder(final View itemView) {
            super(itemView);


            callCircle = (ImageButton) itemView.findViewById(R.id.circlecall);
            txtDescription =(TextView) itemView.findViewById(R.id.txtDescription);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtOptionDigit = (TextView) itemView.findViewById(R.id.txtOptionDigit);
            txtAccept = (TextView)itemView.findViewById(R.id.txtaccept);
            txtRejected= (TextView)itemView.findViewById(R.id.textrejected);

        }
//        private void dialContactPhone(final String phoneNumber) {
//            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
//        }



    }

}