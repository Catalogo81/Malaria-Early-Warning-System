package com.example.malariaearlywarningsystemmews.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.classes.AutumnIndicators;
import com.example.malariaearlywarningsystemmews.ikindicators.Report_IK_Indicators;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AutumnIndicatorsAdapter extends FirebaseRecyclerAdapter<AutumnIndicators, AutumnIndicatorsAdapter.MyAutumnViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param autumnOptions
     */
    public AutumnIndicatorsAdapter(@NonNull FirebaseRecyclerOptions<AutumnIndicators> autumnOptions) {
        super(autumnOptions);
    }

    @Override
    protected void onBindViewHolder(@NonNull AutumnIndicatorsAdapter.MyAutumnViewHolder holder, int position, @NonNull AutumnIndicators model) {

        //setting the item in the rv
        holder.tvIndicator.setText(model.getIkDescription());

        String season;
        season = model.getIkSeason();

        holder.tvIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the item clicked and pass it to the next activity with an intent
                Intent ikIntent = new Intent(v.getContext(), Report_IK_Indicators.class);
                ikIntent.putExtra("indicator", model.getIkDescription());
                v.getContext().startActivity(ikIntent);

            }
        });
    }

    @NonNull
    @Override
    public AutumnIndicatorsAdapter.MyAutumnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_items, parent, false);

        return new AutumnIndicatorsAdapter.MyAutumnViewHolder(view);
    }

    class MyAutumnViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvIndicator;

        public MyAutumnViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIndicator = itemView.findViewById(R.id.tvIndicator);

        }
    }
}
