package example.st.ru.mypurchasecontroller01.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

import example.st.ru.mypurchasecontroller01.Naslednik;
import example.st.ru.mypurchasecontroller01.R;
import example.st.ru.mypurchasecontroller01.ScanBarcodeActivity;
import example.st.ru.mypurchasecontroller01.model.Item;

import static android.content.ContentValues.TAG;
import static example.st.ru.mypurchasecontroller01.Naslednik.brc;


public class MyRequestAdapter extends BaseAdapter {
        private Context context;
        //private int[] my_prices;
        private ArrayList<Item> items;

        public MyRequestAdapter(Context context, final ArrayList<Item> items) {
            this.context=context;
            this.items =items;

            FirebaseFirestore db=FirebaseFirestore.getInstance();

                DocumentReference docRef=db.collection("goods").document(brc);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document=task.getResult();
                            if (document.exists()){

                                Object a=document.get("prices");

                                //items.add(a);

//                                for (int i=0;i<price_info.length();i++){
//                                    items.add(i,price_info);
//                                }

                            }else {
                                Log.d(TAG, "No such document");
                            }
                        }else {
                            Log.w(TAG, "Get failed with ", task.getException());
                        }
                    }
                });

        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return (position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView==null){
                convertView=LayoutInflater.from(context).inflate(R.layout.request_data,parent,false);
            }

            Item currentGoods=(Item) getItem(position);

            TextView iv_price=(TextView)convertView.findViewById(R.id.iv_item_price);
            TextView iv_shop=(TextView)convertView.findViewById(R.id.iv_item_shop);

            if (currentGoods != null) {
                iv_price.setText(currentGoods.getItem_price());
            }
            if (currentGoods != null) {
                iv_shop.setText(currentGoods.getItem_shop());
            }


            return convertView;
        }
    }