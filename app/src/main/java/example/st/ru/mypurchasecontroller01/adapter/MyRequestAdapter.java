package example.st.ru.mypurchasecontroller01.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import example.st.ru.mypurchasecontroller01.R;
import example.st.ru.mypurchasecontroller01.ScanBarcodeActivity;
import example.st.ru.mypurchasecontroller01.model.Goods;

import static android.content.ContentValues.TAG;


public class MyRequestAdapter extends ArrayAdapter<Goods> {

    public MyRequestAdapter(@NonNull Context context, int resource, @NonNull List<Goods> objects) {
        super(context, resource, objects);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Intent data = null;

        ScanBarcodeActivity scanBarcodeActivity=new ScanBarcodeActivity();
        scanBarcodeActivity.getIntent().getParcelableExtra("barcode");
        Barcode barcode=data.getParcelableExtra("barcode");

        DocumentReference docRef=db.collection("goods").document(barcode.rawValue);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document=task.getResult();
                    if (document.exists()){
                        //TODO

                    }else {
                        Log.d(TAG, "No such document");
                    }
                }else {
                    Log.w(TAG, "Get failed with ", task.getException());
                }
            }
        });
    }

    @NonNull
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Goods goods=getItem(position);
        if (convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.request_data, parent, false);
        }

        TextView name=(TextView) convertView.findViewById(R.id.text_view_item_name);
        TextView price=(TextView) convertView.findViewById(R.id.text_view_item_price);
        ImageView img=(ImageView) convertView.findViewById(R.id.ivIcon);


        if (goods != null) {
            name.setText(goods.getItemName());
            price.setText(goods.getItemPrice());
            img.setImageResource(goods.getImgResId());
        }


        return super.getView(position, convertView, parent);
    }
}
