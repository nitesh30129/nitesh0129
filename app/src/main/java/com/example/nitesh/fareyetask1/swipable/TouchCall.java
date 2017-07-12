package com.example.nitesh.fareyetask1.swipable;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.nitesh.fareyetask1.DataModel;
import com.example.nitesh.fareyetask1.adapter.FetchAdapter;
import com.example.nitesh.fareyetask1.localDb.FareyeDb;

import java.util.ArrayList;


public class TouchCall extends ItemTouchHelper.Callback {

    private final FetchAdapter recyclerViewAdapter;
    ArrayList<DataModel> dataset;
    private int position;

    public TouchCall(FetchAdapter recyclerViewAdapter,ArrayList<DataModel> dataset) {
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.dataset = dataset;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position=0;
         position = viewHolder.getLayoutPosition();
        Log.d("onswiped",""+direction+position);
            if (direction == ItemTouchHelper.RIGHT) {
                Log.d("onswiped",""+position);
                removeData(position, dataset);
                Log.d("onswiped",""+position);
            }
            else
            {
                Log.d("onswiped",""+position+direction);
                favourite(position,dataset);
            }

    }

    private void favourite(int position,ArrayList<DataModel> dataset) {
        this.position = position;
        Log.d("RecyclerViewAdapter", "in favouriteItem");
        FareyeDb db = null;
        db.insertSingleData(dataset);

    }

    void removeData(int position, ArrayList<DataModel> arrayList) {
        Log.d("RecyclerViewAdapter", "in removeItem");

        int i=0;
        if ( arrayList != null ) {
            DataModel data = arrayList.get(position);
            recyclerViewAdapter.deleteitem(data);
            recyclerViewAdapter.arrayList.remove(position);
            recyclerViewAdapter.notifyDataSetChanged();
            i++;
            Log.d("changed dataset",""+i);
        }
    }
}