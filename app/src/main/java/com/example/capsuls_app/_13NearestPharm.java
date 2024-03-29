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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class _13NearestPharm extends AppCompatActivity {
    public ArrayList<VMPharma> orderArrayList;
    public ArrayList<String>   orderDateArrayList;
    public ArrayList<String>   orderIDArrayList;
    public ListView availableItemsList;
    public ArrayAdapter availableItemsListAdapter;
    private ProgressDialog createNewDialog;
    public static VMOrder      SelectedOrder;
    public AlertDialog.Builder dialog;
    public static double lat2,lon2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_13_nearest_pharm);
        availableItemsList=findViewById(R.id.orederDetailsListView4);
        orderArrayList=new ArrayList<VMPharma>();
        orderDateArrayList=new ArrayList<String>();
        orderIDArrayList=new ArrayList<String>();
        dialog= new AlertDialog.Builder(_13NearestPharm.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Opppppppps !")
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
        orderArrayList.clear();
        orderDateArrayList.clear();
        orderIDArrayList.clear();
        createNewDialog = new ProgressDialog(_13NearestPharm.this);
        createNewDialog.setMessage("Please Wait ... ");
        createNewDialog.show();
        FirebaseApp.initializeApp(_13NearestPharm.this);
        FirebaseDatabase.getInstance().getReference().child("Pharma").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("showData","Data Changerd");
                Log.i("showData",snapshot.getChildrenCount()+"");
                for (DataSnapshot child: snapshot.getChildren()) {
                    VMPharma fetchedItem=child.getValue(VMPharma.class);
                    //LatLng cenLoc=new LatLng(fetchedItem.Latitude,fetchedItem.Longitude);

                    orderArrayList.add(fetchedItem);
                    orderDateArrayList.add(fetchedItem.Name);
                    orderIDArrayList.add(child.getKey());

                }
                orderDateArrayList.add("HOI");
                orderIDArrayList.add("");
                orderArrayList.add(new VMPharma("Rasya Pharmacy","","Amman ",
                        "065353461","Ali bani hassan",32.0272776,35.8419917,"Dr-Nuha Ayman"));
                if(orderArrayList.size() > 1) {
                    Log.i("showData","Entered Data");
                    availableItemsListAdapter=new ArrayAdapter
                            (getApplicationContext(),R.layout.zlistdesign,R.id.textview1,orderDateArrayList){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view= super.getView(position, convertView, parent);
                            CircleImageView imageView = view.findViewById(R.id.image);
                            //textview1 is filled using adapter
                            TextView textView2        = view.findViewById(R.id.textview2);
                            TextView textView3        = view.findViewById(R.id.textview3);
                            Button button1            = view.findViewById(R.id.button1);
                            Button button2            = view.findViewById(R.id.button2);

                            imageView.setImageResource(R.drawable.mde);
                            textView2.setText(orderArrayList.get(position).Address);
                            button1.setVisibility(View.INVISIBLE);
                            button2.setVisibility(View.INVISIBLE);
                            //LatLng cenLoc=new LatLng(orderArrayList.get(position).Latitude,orderArrayList.get(position).Longitude);
                            textView2.setText("Address : "+orderArrayList.get(position).Address);
                            textView3.setText("Responsible Person :"+orderArrayList.get(position).responsiblePerson);
                            /*button2.setText("Set as Completed");
                            button2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    FirebaseDatabase.getInstance().getReference().child("Orders").child(orderIDArrayList.get(position)).child("Status").setValue("Completed");
                                }
                            });
                            button1.setText("Call Customer");
                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference().child("User");
                                    final Query AccountInfoQuery = DbRef.orderByChild("Email").equalTo(_2Login.currentUser.Email);
                                    AccountInfoQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot center : snapshot.getChildren()) {
                                                VMUsers fetchedUser = center.getValue(VMUsers.class);
                                                if (fetchedUser.Email.equals(_2Login.currentUser.Email)){
                                                    //callPhoneNumber(_2Login.currentUser.PhoneNumber);
                                                }
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            //Toast.makeText(, error.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });*/
                            Log.i("showData","Filled 1dd");
                            return view;
                        }
                    };
                    Log.i("showData","Reached");
                    availableItemsList.setAdapter(availableItemsListAdapter);
                    createNewDialog.dismiss();
                    availableItemsListAdapter.notifyDataSetChanged();
                }
                else{
                    dialog.show();
                }
                availableItemsList.setAdapter(availableItemsListAdapter);
                createNewDialog.dismiss();
                availableItemsListAdapter.notifyDataSetChanged();
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