package com.example.capsuls_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;
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
                .setMessage("There aren't any Latest Report")
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
        FirebaseDatabase.getInstance().getReference().child("Order").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    VMOrder fetchedItem=child.getValue(VMOrder.class);
                    //LatLng cenLoc=new LatLng(fetchedItem.Latitude,fetchedItem.Longitude);
                    if(!fetchedItem.IsCompleted &&
                            fetchedItem.Status.equals("Completed")){
                        orderArrayList.add(fetchedItem);
                        orderDateArrayList.add(fetchedItem.Notes);
                        orderIDArrayList.add(child.getKey());
                    }
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
                            textView2.setText("Distance :  "+getDistance(cenLoc,new LatLng(lat2,lon2))+" Km");
                            button2.setText("Get Details");
                            button2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String uri = String.format(Locale.getDefault(), "http://maps.google.com/maps?q=loc:%f,%f"
                                            , orderArrayList.get(position).Latitude
                                            ,orderArrayList.get(position).Longitude);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                    startActivity(intent);
                                }
                            });
                            return view;
                        }
                    };
                    availableItemsList.setAdapter(availableItemsListAdapter);
                    createNewDialog.dismiss();
                    availableItemsListAdapter.notifyDataSetChanged();}else{
                    dialog.show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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