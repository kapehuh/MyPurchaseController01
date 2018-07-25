package example.st.ru.mypurchasecontroller01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
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

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    TextView barcodeResult;
    TextView productInfo;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barcodeResult=(TextView)findViewById(R.id.barcode_result);
        productInfo=(TextView)findViewById(R.id.product_info);
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

                    DocumentReference docRef=db.collection("goods").document(barcode.rawValue);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot document=task.getResult();
                                if (document.exists()){
                                    productInfo.setText("Product info: "+document.getData());
                                }else {
                                    Log.d(TAG, "No such document");
                                }
                            }else {
                                Log.w(TAG, "Get failed with ", task.getException());
                            }
                        }
                    });


                    barcodeResult.setText("Barcode : "+barcode.displayValue);
                }
            }else {
                barcodeResult.setText("No barcode found");
            }
        }

        // ====================================================trying to receive data from db +
//        try{
//            db.collection("goods")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()){
//                                for (QueryDocumentSnapshot document: task.getResult()){
//                                    Log.d(TAG, document.getId()+"=> "+document.getData());
//                                    productInfo.setText("Product info : "+document.getData().toString());
//                                }
//                            }else {
//                                Log.w(TAG, "Error getting documents.", task.getException());
//                            }
//                        }
//                    });
//        }catch (Exception e){
//            e.printStackTrace();
//            productInfo.setText("Product not found");
//        }
        // ======================================================= N 2
//        try{
//        }catch (Exception e){
//            e.printStackTrace();
//            productInfo.setText("Product not found");
//        }
        // ========================================================== N 3
//        try {
//            CollectionReference colRef = db.collection("goods");
//            final Query query;
//            if (data != null) {
//                query = colRef.whereEqualTo("barcode", data.getParcelableExtra("barcode"));
//            }
//            colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        QuerySnapshot querySnapshot = task.getResult();
//                        if (querySnapshot.isEmpty()) {
//                            Log.w(TAG, "Get failed with ", task.getException());
//                        } else {
//                            productInfo.setText("Product info: " + querySnapshot.getDocuments());
//                            Log.d(TAG, "No such document");
//                        }
//                    }
//                }
//            });
            //==================================================================================


            super.onActivityResult(requestCode, resultCode, data);
    }
}
