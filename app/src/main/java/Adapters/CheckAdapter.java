package Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.gqoha.developerx.bandl.CheckoutFragment;
import com.gqoha.developerx.bandl.CustomDialogFragment;
import com.gqoha.developerx.bandl.R;

import data.DatabaseCrud;
import data.JobContracts;

/**
 * Created by DeveloperX on 18-Nov-17.
 */

public class CheckAdapter  extends RecyclerView.Adapter<CheckAdapter.ViewHolder>  {
    private Cursor mCurso ;
    private Context mContext;
    public TextView total;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name_food;
        public TextView descr;
        public TextView price;

        ImageButton delete;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.eces_e, parent, false));

            name_food = (TextView) itemView.findViewById(R.id.name);
            descr = (TextView) itemView.findViewById(R.id.descr);
            price = (TextView) itemView.findViewById(R.id.price);

            delete=itemView.findViewById(R.id.remove);
            delete.setOnClickListener(this);
            delete.setFocusable(true);
            delete.setTag("del");
        }

        @Override
        public void onClick(View view) {

            int adapter_posotion = getAdapterPosition();
            mCurso.moveToPosition(adapter_posotion);
            int ID_CULUMN_INDEX = mCurso.getColumnIndex(JobContracts.CheckOut._ID);
            int p = mCurso.getInt(ID_CULUMN_INDEX);

            if (view.getTag() == "del") {
                DatabaseCrud.ToDeleteCheck(mContext,p+"");
                Log.v("del","del");
                sendMessage();
//                total.setText(DatabaseCrud.AutoRefresh(mContext)+"");
//                notifyDataSetChanged();
            } else {

            }
        }
    }

    public CheckAdapter(Context context,TextView t) {
        mContext=context;
        FragmentActivity c;
        c=(FragmentActivity)mContext;
        total=t;
    }
    @Override
    public CheckAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CheckAdapter.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(CheckAdapter.ViewHolder holder, int position) {
        mCurso.moveToPosition(position);
        holder.name_food.setText(mCurso.getString(CheckoutFragment.COL_NAME));
        holder.descr.setText(mCurso.getString(CheckoutFragment.COL_DESC));
        holder.price.setText(mCurso.getString(CheckoutFragment.COL_PRICE));
//        holder.name_food.setText("name");
//        holder.descr.setText("price");
//        holder.price.setText("desc");


    }

    public void swapCursor(Cursor newCursor)
    {
        mCurso =newCursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        if(null==mCurso){
            Log.v("debug","0");

            return 0;}

        return mCurso.getCount();
    }
    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }


}

