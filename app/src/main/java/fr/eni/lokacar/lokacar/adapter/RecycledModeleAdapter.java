package fr.eni.lokacar.lokacar.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import fr.eni.lokacar.lokacar.R;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.fragment.ListFragment;

public class RecycledModeleAdapter extends Adapter<RecycledModeleAdapter.ViewHolder> {

    private List<Vehicule> lstVehicules;
    private ListFragment.OnListFragmentInteractionListener mListener;

    /**
     * Constructeur
     *
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
                        .inflate(R.layout.fragment_list_list, parent,
                                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Resources res = holder.itemView.getContext().getResources();
        holder.mItem = lstVehicules.get(position);
        holder.mMarque.setText(lstVehicules.get(position).getMarque().getLibelle());
        holder.mEssence.setText(lstVehicules.get(position).getTypeCarburant().getLibelle());
        holder.mPrixJour.setText(lstVehicules.get(position).getPrixJour() + " €/Jours");
        holder.mTypeVehicule.setText(lstVehicules.get(position).getTypeVehicule().getLibelle());
        holder.mKilometre.setText(lstVehicules.get(position).getKilometrage() + " Km");
        holder.mDesignation.setText(lstVehicules.get(position).getDesignation());
        holder.mImmatriculation.setText(lstVehicules.get(position).getImmatriculation());

        //Recupération de la photo
        try{
            String srcPhoto = lstVehicules.get(position).getPhoto().getUri();
            if (srcPhoto != null || srcPhoto.equals("TEST")) {
                File imgFile = new File(srcPhoto);
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    holder.mPhotoVehicule.setImageBitmap(myBitmap);
                }
            }

        }catch(Exception e){
            Log.v("Prb image -> ", e.getMessage());
        }

        if (holder.mItem.isEnLocation()){

            holder.message.setText("Retour du véhicule");
            holder.message.setBackgroundColor(res.getColor(R.color.colorRedButton));
        } else {
            holder.message.setBackgroundColor( res.getColor(R.color.colorGreenDark));
            holder.message.setText("Louer ce véhicule");
        }

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
        public final TextView message;
        public final TextView mDesignation;
        public final TextView mImmatriculation;
        public final ImageView mPhotoVehicule;
        public final CardView cardView;
        public Vehicule mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            cardView = view.findViewById(R.id.cardView);
            message = view.findViewById(R.id.messageCardView);
            mDesignation = view.findViewById(R.id.disignationVehicule);
            mMarque = view.findViewById(R.id.marqueVehicule);
            mEssence = view.findViewById(R.id.typeEssence);
            mPrixJour = view.findViewById(R.id.prixJour);
            mTypeVehicule = view.findViewById(R.id.typeVehicule);
            mKilometre = view.findViewById(R.id.kilometres);
            mImmatriculation = view.findViewById(R.id.immatriculationVehicule);
            mPhotoVehicule = view.findViewById(R.id.photoVehicule);
        }
    }
}
