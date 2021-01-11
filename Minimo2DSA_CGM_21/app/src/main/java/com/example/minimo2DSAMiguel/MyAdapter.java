package com.example.minimo2DSAMiguel;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Repos> datos;

    Activity activity;

    // construcotr de la lista para tener referencia
    public MyAdapter(List<Repos> myDataset, Activity activity) {

        this.datos = myDataset;
        this.activity=activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nombretxt;
        public TextView lenguajetxt;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            lenguajetxt =  v.findViewById(R.id.lenguaje);
            nombretxt =  v.findViewById(R.id.NombreRepo);
        }
    }



    public void add(int position, Repos item) {
        datos.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(int position) {
        datos.remove(position);
        notifyItemRemoved(position);
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Repos repo = datos.get(position);
        holder.nombretxt.setText(repo.getName());
        holder.lenguajetxt.setText(repo.getLanguage());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return datos.size();
    } //retorna el tamaño de la lista
}