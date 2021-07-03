package com.example.poyominder;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Medicine> mMedicine;

    public MedicineAdapter(Context mContext, ArrayList<Medicine> mMedicine) {
        this.mContext = mContext;
        this.mMedicine = mMedicine;
    }


    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.affichage_medicaments, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicineAdapter.ViewHolder holder, int position) {
        Medicine medoc = mMedicine.get(position);

        holder.nomMedoc.setText(medoc.name);
        holder.descMedoc.setText(medoc.description);

        if (medoc.type.equals("Comprimé")) {
            String imageUri = "https://image.flaticon.com/icons/png/512/1012/1012571.png"; //@drawable/comprime.png
            Picasso.with(mContext).load(imageUri).into(holder.imageMedoc);
        }
        else if (medoc.type.equals("Gélule")) {
            String imageUri = "https://image.flaticon.com/icons/png/512/720/720930.png";
            Picasso.with(mContext).load(imageUri).into(holder.imageMedoc);
        }
        else if (medoc.type.equals("Pommade")) {
            String imageUri = "https://image.flaticon.com/icons/png/512/822/822175.png";
            Picasso.with(mContext).load(imageUri).into(holder.imageMedoc);
        }

    }

    @Override
    public int getItemCount() {
        return mMedicine.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomMedoc;
        private TextView descMedoc;
        private ImageView imageMedoc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomMedoc = itemView.findViewById(R.id.nom_recycleur_Medicament);
            descMedoc = itemView.findViewById(R.id.description_recycleur_Medicament);
            imageMedoc = itemView.findViewById(R.id.image_recycleur_Medicament);
        }
    }
}
