package com.example.capsuls_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import com.google.android.gms.maps.model.LatLng;
import de.hdodenhof.circleimageview.CircleImageView;

public class _11PendingOrderList extends AppCompatActivity {
    public ArrayList<VMOrder> orderArrayList;
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
        setContentView(R.layout.activity_11_pending_order_list);
        availableItemsList=findViewById(R.id.Pendinglatest);
        orderArrayList=new ArrayList<VMOrder>();
        orderDateArrayList=new ArrayList<String>();
        orderIDArrayList=new ArrayList<String>();
        dialog= new AlertDialog.Builder(_11PendingOrderList.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Oppps !")
                .setMessage("There aren't any Pending Orders")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent m=new Intent(_11PendingOrderList.this,_4Main_Activity.class);
                        startActivity(m);
                    }
                });
        updateScreenData();
    }
    private void updateScreenData() {
        orderArrayList.clear();
        orderDateArrayList.clear();
        orderIDArrayList.clear();
        createNewDialog = new ProgressDialog(_11PendingOrderList.this);
        createNewDialog.setMessage("Please Wait ... ");
        createNewDialog.show();
        FirebaseApp.initializeApp(_11PendingOrderList.this);
        FirebaseDatabase.getInstance().getReference().child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    VMOrder fetchedItem=child.getValue(VMOrder.class);
                    //LatLng cenLoc=new LatLng(fetchedItem.Latitude,fetchedItem.Longitude);

                        orderArrayList.add(fetchedItem);
                        orderDateArrayList.add(fetchedItem.Notes);
                        orderIDArrayList.add(child.getKey());

                }
                if(orderArrayList.size()>0) {
                    availableItemsListAdapter=new ArrayAdapter
                            (getApplicationContext(),R.layout.zlistdesign,R.id.textview1,orderDateArrayList){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view= super.getView(position, convertView, parent);
                            CircleImageView imageView = view.findViewById(R.id.image);
                            //textview1 is filled using adapter
                            TextView textView2        = view.findViewById(R.id.textview2);
                            Button button1            = view.findViewById(R.id.button1);
                            Button button2            = view.findViewById(R.id.button2);

                            imageView.setImageResource(R.drawable.images);
                            textView2.setText(orderArrayList.get(position).Status);
                            button1.setVisibility(View.INVISIBLE);
                            LatLng cenLoc=new LatLng(orderArrayList.get(position).Latitude,orderArrayList.get(position).Longitude);
                            textView2.setText("Address : "+orderArrayList.get(position).Address);
                            button2.setText("Set as Completed");
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
                                                    callPhoneNumber(_2Login.currentUser.PhoneNumber);
                                                }
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            //Toast.makeText(, error.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                            return view;
                        }
                    };
                    availableItemsList.setAdapter(availableItemsListAdapter);
                    createNewDialog.dismiss();
                    availableItemsListAdapter.notifyDataSetChanged();}
                else{
                    dialog.show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void callPhoneNumber(String phone) {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(_11PendingOrderList.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void onMenuClicked(View view) {
        Intent moving=new Intent(getApplicationContext(),_4Main_Activity.class);
        startActivity(moving);
    }

    public float getDistance(LatLng my_latlong, LatLng reportLoc) {
        Location l1 = new Location("One");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);

        Location l2 = new Location("Two");
        l2.setLatitude(reportLoc.latitude);
        l2.setLongitude(reportLoc.longitude);

        float distance = l1.distanceTo(l2)/1000;
        return distance;
    }
}