package fr.eni.lokacar.lokacar.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import fr.eni.lokacar.lokacar.R;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.fragment.ListFragment;

public class RecycledModeleAdapter extends Adapter<RecycledModeleAdapter.ViewHolder> {

    private List<Vehicule> lstVehicules;
    private ListFragment.OnListFragmentInteractionListener mListener;

    /**
     * Constructeur
     * @param items
     * @param listener
     */
    public RecycledModeleAdapter(List<Vehicule> items, ListFragment.OnListFragmentInteractionListener listener) {
        lstVehicules = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_list_list,parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.mItem = lstVehicules.get(position);
        holder.mMarque.setText(lstVehicules.get(position).getMarque().getLibelle());
        holder.mEssence.setText(lstVehicules.get(position).getTypeCarburant().getLibelle());
        holder.mPrixJour.setText(lstVehicules.get(position).getPrixJour()+" €/Jours");
        holder.mTypeVehicule.setText(lstVehicules.get(position).getTypeVehicule().getLibelle());
        holder.mKilometre.setText(lstVehicules.get(position).getKilometrage()+" Km");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstVehicules.size();
    }

    /**
     * ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mMarque;
        public final TextView mEssence;
        public final TextView mPrixJour;
        public final TextView mTypeVehicule;
        public final TextView mKilometre;
        public Vehicule mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mMarque = view.findViewById(R.id.marqueVehicule);
            mEssence = view.findViewById(R.id.typeEssence);
            mPrixJour = view.findViewById(R.id.prixJour);
            mTypeVehicule = view.findViewById(R.id.typeVehicule);
            mKilometre = view.findViewById(R.id.kilometres);
        }
    }
}
