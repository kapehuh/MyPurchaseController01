package example.st.ru.mypurchasecontroller01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends Activity {
    TextView barcodeResult;
    TextView productInfo;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==0){
            if (resultCode== CommonStatusCodes.SUCCESS){
                if (data!=null){
                    Barcode barcode=data.getParcelableExtra("barcode");
                    barcodeResult.setText("Barcode value : "+barcode.displayValue);
                }
            }else {
                barcodeResult.setText("No barcode found");
            }
        }

        //getting data from firestore

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference frDb=db.collection("goods");
        Query goodesBarcode = frDb.whereEqualTo("barcode",barcodeResult);

        super.onActivityResult(requestCode, resultCode, data);

    }
}
