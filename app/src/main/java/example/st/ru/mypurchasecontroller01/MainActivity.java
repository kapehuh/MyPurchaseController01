package example.st.ru.mypurchasecontroller01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import example.st.ru.mypurchasecontroller01.adapter.MyRequestAdapter;
import example.st.ru.mypurchasecontroller01.model.Goods;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    TextView barcodeResult;
    TextView productInfo;
    ListView listView;


    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barcodeResult=(TextView)findViewById(R.id.barcode_result2);
        //productInfo=(TextView)findViewById(R.id.product_info);
        listView=(ListView)findViewById(R.id.list_dynamic);

    }


    //add event barcode button
    public void scanBarcode(View v) {
        Intent intent=new Intent(this, ScanBarcodeActivity.class);
        startActivityForResult(intent,0);
    }



    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (requestCode==0){
            if (resultCode== CommonStatusCodes.SUCCESS){
                if (data!=null){
                    Barcode barcode=data.getParcelableExtra("barcode");

                    ArrayList<Goods> arrayList= new ArrayList<>();
                    MyRequestAdapter adapter=new MyRequestAdapter(this,0, arrayList);
                    ListView listView=(ListView)findViewById(R.id.list_dynamic);
                    listView.setAdapter(adapter);



                    DocumentReference docRef=db.collection("goods").document(barcode.rawValue);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot document=task.getResult();
                                if (document.exists()){
                                    //TODO
                                    barcodeResult.setText("123"+document.get("productname"));
                                }else {
                                    Log.d(TAG, "No such document");
                                }
                            }else {
                                Log.w(TAG, "Get failed with ", task.getException());
                            }
                        }
                    });
                    //barcodeResult.setText("Product barcode "+barcode.displayValue);
                }
            }else {
                barcodeResult.setText("No barcode found");
            }
        }
            super.onActivityResult(requestCode, resultCode, data);
    }
}
