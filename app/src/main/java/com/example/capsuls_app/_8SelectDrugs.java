package com.example.capsuls_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class _8SelectDrugs extends AppCompatActivity {
    public ListView availableItemsList;
    public ArrayAdapter availableItemsListAdapter;
    public static ArrayList<VMDrug> availableItems;
    public ArrayList<String> availableItemsNames,myItemsIds;
    private ProgressDialog createNewDialog;
    public static HashMap<String,Integer> cartOfItems;
    public static HashMap<String,Double> pricing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_8_select_drugs);
        availableItemsList=findViewById(R.id.ItemsList);
        availableItems=new ArrayList<VMDrug>();
        availableItemsNames=new ArrayList<String>();
        myItemsIds=new ArrayList<String>();
        cartOfItems=new HashMap<String,Integer> ();
        pricing=new HashMap<String,Double> ();
        updateScreenData();
    }
    private void updateScreenData() {
        availableItemsNames.clear();
        availableItems.clear();
        createNewDialog = new ProgressDialog(_8SelectDrugs.this);
        createNewDialog.setMessage("Please Wait ... ");
        createNewDialog.show();
        FirebaseApp.initializeApp(_8SelectDrugs.this);
        FirebaseDatabase.getInstance().getReference().child("Medicine").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    VMDrug fetchedItem=child.getValue(VMDrug.class);

                        availableItemsNames.add(fetchedItem.Name);
                        availableItems.add(fetchedItem);
                        myItemsIds.add(child.getKey());

                }
                availableItemsListAdapter=new ArrayAdapter
                        (getApplicationContext(),R.layout.seclectitemsfromlist,R.id.ItemsNameField,availableItemsNames){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view= super.getView(position, convertView, parent);
                        TextView employeeEmailTextView=view.findViewById(R.id.PriceField);
                        employeeEmailTextView.setText(availableItems.get(position).Price+"");
                        TextView DescriptionView=view.findViewById(R.id.descriptionField);
                        DescriptionView.setText(availableItems.get(position).Description+"");
                        TextView QtnView=view.findViewById(R.id.ItemQuantityField);
                        QtnView.setText("Qtn : "+availableItems.get(position).AvailableQtn);
                        CircleImageView imageView=view.findViewById(R.id.itemImage);
                        Glide.
                                with(getApplicationContext()).load(availableItems
                                        .get(position).ImageLink).into(imageView);
                        Button addToCart=view.findViewById(R.id.addingitemsButton);
                        addToCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addItemIntoHashMapCart
                                        (availableItemsNames.get(position),Double.parseDouble(availableItems.get(position).Price+""));

                            }
                        });
                        return view;
                    }
                };
                availableItemsList.setAdapter(availableItemsListAdapter);
                createNewDialog.dismiss();
                availableItemsListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onCheckOutDoneClicked(View view){
        Intent moveToCheckoutOrder=new Intent(_8SelectDrugs.this,_9SaveOrder.class);
        startActivity(moveToCheckoutOrder);

    }
    private void addItemIntoHashMapCart(String selectedItemsName,double selectedItemprice) {
        if (cartOfItems.containsKey(selectedItemsName)){
            //if the item Already exists
            //just i want to update the quantity
            cartOfItems.put(selectedItemsName, cartOfItems.get(selectedItemsName) + 1);
        }else {
            cartOfItems.put(selectedItemsName,1);
            pricing.put(selectedItemsName,selectedItemprice);
        }
    }

    public void onMenuClicked(View view) {
        Intent moving=new Intent(getApplicationContext(),_4Main_Activity.class);
        startActivity(moving);
    }
}