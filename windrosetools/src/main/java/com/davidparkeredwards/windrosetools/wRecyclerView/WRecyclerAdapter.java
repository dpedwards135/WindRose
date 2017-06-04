package com.davidparkeredwards.windrosetools.wRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //Define a viewHolder for each type of view required.

    private WRecyclerObject[] constructorObjects;

    public WRecyclerAdapter(WRecyclerObject[] constructorObjects) {
    }


    class ViewHolder0 extends RecyclerView.ViewHolder {
        ...
        public ViewHolder0(View itemView){
        ...
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        ...

        public ViewHolder2(View itemView) {
        ...
        }

    }

    @Override
    public int getItemViewType(int position) {

        int viewType;
        Object object = constructorObjects[position];
        return object.getWRecyclerViewType;

        //return super.getItemViewType(position);


    }

    /*
        @Override
        public int getItemViewType(int position) {
            // Just as an example, return 0 or 2 depending on position
            // Note that unlike in ListView adapters, types don't have to be contiguous
            return position % 2 * 2;
        }
        */

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case 0: return new MyAdapter.ViewHolder0(...);
                case 2: return new MyAdapter.ViewHolder2(...);
             ...
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    MyAdapter.ViewHolder0 viewHolder0 = (MyAdapter.ViewHolder0)holder;
                ...
                    break;

                case 2:
                    MyAdapter.ViewHolder2 viewHolder2 = (MyAdapter.ViewHolder2)holder;
                ...
                    break;
            }
        }
}



    /*
    private ArrayList<String> mText;

    //1
    public static class TextHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //2

        private TextView mItemDescription;

        //4
        public TextHolder(View v) {
            super(v);

            //Put ViewHolder definition here. Change the viewHolder to meet the types needed

            mItemDescription = (TextView) v.findViewById(R.id.r_text_view);
            v.setOnClickListener(this);
        }

        //5
        @Override
        public void onClick(View v) {
            Log.d("RecyclerView", "CLICK!" + mItemDescription.getText());
        }

        public void bindText(String text) {

            mItemDescription.setText(text);
        }
    }

    public WRecyclerAdapter(ArrayList text) {
        mText = text;
    }


    @Override
    public TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_row, parent, false);
        return new TextHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(TextHolder textHolder, int position) {

        String text = mText.get(position);
        Log.i("onBindViewHolder", "onBindViewHolder: " + text.toString());
        textHolder.bindText(text);

    }

    @Override
    public int getItemCount() {
        Log.i("getItemCount", "getItemCount: " + mText.size());
        return mText.size();
    }
    */
}

