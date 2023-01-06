package com.example.capsuls_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class _13NearestPharm extends AppCompatActivity {
    public ListView orderListView;
    public ArrayAdapter ListAdapter;
    public ArrayList<String> nameofPharma;
    public ArrayList<VMPharma> pharmas;
    private ProgressDialog createNewDialog;
    public AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_13_nearest_pharm);

        orderListView=findViewById(R.id.orederDetailsListView4);
        pharmas=new ArrayList<VMPharma>();
        nameofPharma=new ArrayList<String>();
        dialog= new AlertDialog.Builder(_13NearestPharm.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Oppps !")
                .setMessage("There aren't any Nearest Pharmacy")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent m=new Intent(_13NearestPharm.this,_4Main_Activity.class);
                        startActivity(m);
                    }
                });
        updateScreenData();


    }

    private void updateScreenData() {
        pharmas.clear();
        nameofPharma.clear();
        createNewDialog = new ProgressDialog(_13NearestPharm.this);
        createNewDialog.setMessage("Please Wait ... ");
        createNewDialog.show();
        FirebaseApp.initializeApp(_13NearestPharm.this);
        FirebaseDatabase.getInstance().getReference().child("Pharma").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    VMPharma fetchedItem=child.getValue(VMPharma.class);

                    nameofPharma.add(fetchedItem.Name);
                    pharmas.add(fetchedItem);
                    //myItemsIds.add(child.getKey());
                }
                if(pharmas.size() >0){
                    ListAdapter=new ArrayAdapter
                            (getApplicationContext(),R.layout.seclectitemsfromlist,R.id.ItemsNameField,nameofPharma){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view= super.getView(position, convertView, parent);
                            TextView employeeEmailTextView=view.findViewById(R.id.PriceField);
                            employeeEmailTextView.setText("Responsible Doctor : "+pharmas.get(position).responsiblePerson+"");
                            TextView DescriptionView=view.findViewById(R.id.descriptionField);
                            DescriptionView.setText(pharmas.get(position).Address+"");
                            TextView QtnView=view.findViewById(R.id.ItemQuantityField);
                            QtnView.setVisibility(View.INVISIBLE);
                            CircleImageView imageView=view.findViewById(R.id.itemImage);
                            Glide.
                                    with(getApplicationContext()).load(pharmas
                                            .get(position).ImageLink).into(imageView);
                            Button addToCart=view.findViewById(R.id.addingitemsButton);
                            addToCart.setText("Get Directions ");
                            addToCart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String uri = String.format(Locale.getDefault(), "http://maps.google.com/maps?q=loc:%f,%f"
                                            , pharmas.get(position).latauide
                                            ,pharmas.get(position).longitude);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                    startActivity(intent);
                                }
                            });
                            return view;
                        }
                    };
                    orderListView.setAdapter(ListAdapter);
                    createNewDialog.dismiss();
                    ListAdapter.notifyDataSetChanged();
                }else{
                    dialog.show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Back(View view) {
        finish();
    }
}