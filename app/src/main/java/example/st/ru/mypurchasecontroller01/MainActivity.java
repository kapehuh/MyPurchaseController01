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
                    barcodeResult.setText("Barcode : "+barcode.displayValue);
                    //productInfo.setText((CharSequence) FBdata.collection("goods").document(""+barcodeResult));
                }
            }else {
                barcodeResult.setText("No barcode found");
            }
        }

        db.collection("goods")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Log.d(TAG, document.getId()+"=> "+document.getData());
                                productInfo.setText("Product info : "+document.getData());
                            }
                        }else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        super.onActivityResult(requestCode, resultCode, data);
    }
}
