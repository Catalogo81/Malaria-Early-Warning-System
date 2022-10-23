package com.example.malariaearlywarningsystemmews.classes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.ikindicators.Select_IK_Indicator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import static androidx.core.content.ContextCompat.startActivity;

public class Adapter extends FirebaseRecyclerAdapter<Indicators, Adapter.ViewHolder>
{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Adapter(@NonNull FirebaseRecyclerOptions<Indicators> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Indicators model) {

        holder.tvIndicator.setText(model.getIkDescription());

        holder.tvIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                //downloadFile(holder.tvReadingUnits.getContext(), ".pdf", DIRECTORY_DOWNLOADS, image);
                //startActivity(new Intent(getApplicationContext(), Select_IK_Indicator.class));
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_items, parent, false);

        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIndicator = itemView.findViewById(R.id.tvIndicator);


        }
    }
}
