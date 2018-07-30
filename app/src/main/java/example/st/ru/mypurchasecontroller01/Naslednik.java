package example.st.ru.mypurchasecontroller01;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

import example.st.ru.mypurchasecontroller01.adapter.MyRequestAdapter;

import static android.content.ContentValues.TAG;

public class Naslednik {
    public static String brc;

    public Naslednik(String brc) {
        Naslednik.brc = brc;
    }

    public String getBrc() {
        return brc;
    }

    public void setBrc(String brc) {
        this.brc = brc;
    }


        //FirebaseFirestore db=FirebaseFirestore.getInstance();

        //String brc=scanBarcodeActivity.getIntent().getParcelableExtra("barcode");
        //Barcode barcode=data.getParcelableExtra("barcode");

//        DocumentReference docRef=db.collection("goods").document(brc);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot document=task.getResult();
//                    if (document.exists()){
//                        for (int i = 0; i< Objects.requireNonNull(document.getString("prices")).length(); i++){
//                            goods.add(document.get("prices"));
//                        }
//                    }else {
//                        Log.d(TAG, "No such document");
//                    }
//                }else {
//                    Log.w(TAG, "Get failed with ", task.getException());
//                }
//            }
//        });

}
