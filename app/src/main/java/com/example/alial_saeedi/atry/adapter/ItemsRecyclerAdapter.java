package com.example.alial_saeedi.atry.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alial_saeedi.atry.DetailsActivity;
import com.example.alial_saeedi.atry.LoginActivity;
import com.example.alial_saeedi.atry.MainBoardUser;
import com.example.alial_saeedi.atry.R;
import com.example.alial_saeedi.atry.data.Event;
import com.example.alial_saeedi.atry.touch.EventTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class ItemsRecyclerAdapter
        extends RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder>
        implements EventTouchHelperAdapter {

    private List<Event> ItemsList;
    private Context context;
    private String userId;
    private Realm realmItem;
    private String eventDetails;


    public ItemsRecyclerAdapter(Context context, Realm realmItem, String userId) {
        this.context = context;
        this.realmItem = realmItem;
        this.userId = userId;
        RealmResults<Event> ItemResult = realmItem.where(Event.class).contains("userId", userId).findAll().sort("eventDate", Sort.ASCENDING);

        String a = "";
        ItemsList = new ArrayList<Event>();

        for (int i = 0; i < ItemResult.size(); i++) {
            ItemsList.add(ItemResult.get(i));

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.eventDate.setText(ItemsList.get(position).getEventDate());
        holder.eventTime.setText(ItemsList.get(position).getEventTime());

        holder.eventTitle.setText(ItemsList.get(position).getEventTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String any= ItemsList.get(position).getEventTitle();
                int i = position;
                eventDetails = ItemsList.get(position).getEventDetail();

                Intent detailIntent = new Intent(context, DetailsActivity.class);
                detailIntent.putExtra("eventDetails" , eventDetails);
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemsList.size();
    }


    public void addItem(Event barcaItem) {

        ItemsList.add(0, barcaItem);

        notifyItemInserted(0);
    }

    public void updateItem(String itemID, int positionToEdit) {
        Event barcaItem = realmItem.where(Event.class)
                .equalTo("itemID", itemID)
                .findFirst();

        ItemsList.set(positionToEdit, barcaItem);

        notifyItemChanged(positionToEdit);
    }


    @Override
    public void onItemDismiss(int position) {
        realmItem.beginTransaction();
        ItemsList.get(position).deleteFromRealm();
        realmItem.commitTransaction();


        ItemsList.remove(position);

        // refreshes the whole list
        //notifyDataSetChanged();
        // refreshes just the relevant part that has been deleted
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        /*ItemsList.add(toPosition, ItemsList.get(fromPosition));
        ItemsList.remove(fromPosition);*/

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(ItemsList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(ItemsList, i, i - 1);
            }
        }


        notifyItemMoved(fromPosition, toPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView eventTime;
        private TextView eventDate;
        private TextView eventTitle;
        private TextView eventDetail;


        public ViewHolder(View itemView) {
            super(itemView);

            eventDate = (TextView) itemView.findViewById(R.id.event_date);
            eventTime = (TextView) itemView.findViewById(R.id.event_time);
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);

        }
    }

    public void deleteAll_Events() {
        realmItem.beginTransaction();
        realmItem.deleteAll();
        realmItem.commitTransaction();


        ItemsList.clear();
        notifyDataSetChanged();
    }

}
