package com.example.poyominder;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Medicine> mMedicine;
    private String type;

    public MedicineAdapter(Context mContext, ArrayList<Medicine> mMedicine, String type) {
        this.mContext = mContext;
        this.mMedicine = mMedicine;
        this.type = type;
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
        holder.medocLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("touchède");
                for (Integer i = 0; i < medoc.prescription.size(); i ++) {
                    //System.out.println(type);
                    //System.out.println("presc" + medoc.prescription.get(i));

                    if (medoc.prescription.get(i).equals(type)) {
                        medoc.hasPrisSonMedoc.set(i, true);
                        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("planning").document(medoc.id).update("hasPrisSonMedoc", medoc.hasPrisSonMedoc);

                        String imageUri = "https://i.goopics.net/XwQJV.png";
                        Picasso.with(mContext).load(imageUri).into(holder.imageHasPrisSonMedoc);

                        //System.out.println("modifiède");
                    }
                }
                System.out.println("New print" + medoc.hasPrisSonMedoc);
                System.out.println("New print" + medoc.prescription);
                System.out.println(FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("planning").document(String.valueOf(medoc.hasPrisSonMedoc)));
            }
        });



        for (Integer i = 0; i < medoc.prescription.size(); i ++) {
            if (medoc.prescription.get(i).equals(type)) {
                if (medoc.hasPrisSonMedoc.get(i).equals(true)) {
                    String imageUri = "https://i.goopics.net/XwQJV.png";
                    Picasso.with(mContext).load(imageUri).into(holder.imageHasPrisSonMedoc);
                } else {
                    String imageUri = "https://image.flaticon.com/icons/png/512/753/753345.png";
                    Picasso.with(mContext).load(imageUri).into(holder.imageHasPrisSonMedoc);
                }
            }
        }


        /*for (Integer i = 0; i < medoc.prescription.size(); i ++) {
            if (medoc.hasPrisSonMedoc.get(i).equals(true)) {
                String imageUri = "https://image.flaticon.com/icons/png/512/1828/1828640.png";
                Picasso.with(mContext).load(imageUri).into(holder.imageHasPrisSonMedoc);

            } else {
                String imageUri = "https://image.flaticon.com/icons/png/512/753/753345.png";
                Picasso.with(mContext).load(imageUri).into(holder.imageHasPrisSonMedoc);
                
            }

        }*/




        if (medoc.type.equals("Comprimé")) {
            String imageUri = "https://image.flaticon.com/icons/png/512/1012/1012571.png";
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
        else if (medoc.type.equals("Sirop")) {
            String imageUri = "https://image.flaticon.com/icons/png/512/3845/3845006.png";
            Picasso.with(mContext).load(imageUri).into(holder.imageMedoc);
        }
        else if (medoc.type.equals("Antibiotique")) {
            String imageUri = "https://image.flaticon.com/icons/png/512/4189/4189111.png";
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
        private ImageView imageMedoc, imageHasPrisSonMedoc;
        private ConstraintLayout medocLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomMedoc = itemView.findViewById(R.id.nom_recycleur_Medicament);
            descMedoc = itemView.findViewById(R.id.description_recycleur_Medicament);
            imageMedoc = itemView.findViewById(R.id.image_recycleur_Medicament);
            medocLayout = itemView.findViewById(R.id.medocLayout);
            imageHasPrisSonMedoc = itemView.findViewById(R.id.check_has_pris_son_medoc);
        }
    }
}
