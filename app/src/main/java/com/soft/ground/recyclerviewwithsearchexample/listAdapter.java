package com.soft.ground.recyclerviewwithsearchexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class listAdapter extends RecyclerView.Adapter<listAdapter.WarehouseViewHolder> implements Filterable {

    public List<MyObj> myObjsList, myObjsListFiltered;
    private listAdapter.RecyclerViewOnClickListener listener;

    public listAdapter(RecyclerViewOnClickListener listener) {

        this.listener = listener;

    }

    @Override
    public WarehouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);

        return new WarehouseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WarehouseViewHolder holder, int position) {

        View finalView = holder.itemView;

        MyObj myObj = myObjsListFiltered.get(position);

        holder.itemView.setOnClickListener(view -> {


            if (myObjsListFiltered.get(position).getChecked() == 1) {

                myObjsListFiltered.get(position).setChecked(0);
                holder.chBox.setChecked(false);

            } else {

                myObjsListFiltered.get(position).setChecked(1);
                holder.chBox.setChecked(true);
            }

        });

        holder.title.setText(myObj.getTitle());


        if (myObj.getChecked() == 1) {

            holder.chBox.setChecked(true);

        } else {

            holder.chBox.setChecked(false);
        }

        holder.chBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {

                listener.recyclerViewClick(position, myObjsListFiltered, 1);

            } else {

                listener.recyclerViewClick(position, myObjsListFiltered, 0);
            }

        });


    }

    public void setWarehouseList(final List<MyObj> myObjsList) {

        if (this.myObjsList == null) {

            this.myObjsList = this.myObjsListFiltered = myObjsList;
            notifyItemChanged(0, myObjsListFiltered.size());

        } else {

            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {

                @Override
                public int getOldListSize() {
                    return listAdapter.this.myObjsList.size();
                }

                @Override
                public int getNewListSize() {
                    return myObjsList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return listAdapter.this.myObjsList.get(oldItemPosition).getTitle().equals(myObjsList.get(newItemPosition).getTitle());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    MyObj newMyObj = listAdapter.this.myObjsList.get(oldItemPosition);

                    MyObj oldMyObj = myObjsList.get(newItemPosition);

                    return newMyObj.getTitle().equals(oldMyObj.getTitle());
                }
            });

            this.myObjsList = this.myObjsListFiltered = myObjsList;
            result.dispatchUpdatesTo(this);
        }

    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    myObjsListFiltered = myObjsList;

                } else {

                    List<MyObj> filteredList = new ArrayList<>();

                    for (MyObj myObj : myObjsList) {

                        if (myObj.getTitle().toLowerCase().contains(charString.toLowerCase())) {

                            filteredList.add(myObj);
                        }
                    }

                    myObjsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = myObjsListFiltered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                myObjsListFiltered = (ArrayList<MyObj>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {

        if (myObjsList != null) {

            return myObjsListFiltered.size();

        } else {

            return 0;
        }
    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public interface RecyclerViewOnClickListener {
        void recyclerViewClick(int position, List<MyObj> warehouseList, int param);
    }

    public class WarehouseViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        CheckBox chBox;

        WarehouseViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.textViewTitle);
            chBox = view.findViewById(R.id.checkBox);
        }

    }


}
