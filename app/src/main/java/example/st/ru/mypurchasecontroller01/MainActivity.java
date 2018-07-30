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
import example.st.ru.mypurchasecontroller01.model.Item;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    TextView barcodeResult;
    TextView productInfo;
    ListView listView;

    int[] img_src=new int[]{R.drawable.logo_5ka, R.drawable.lenta, R.drawable.stock_8_z066};

    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //barcodeResult=(TextView)findViewById(R.id.barcode_result2);
        //productInfo=(TextView)findViewById(R.id.product_info);
        //listView=(ListView)findViewById(R.id.list_dynamic);
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

                    String iyo=barcode.rawValue;

                    Naslednik naslednik=new Naslednik(iyo);
                    naslednik.setBrc(iyo);

                    ArrayList<Item> arrayOfPrices=new ArrayList<>();
                    MyRequestAdapter adapter=new MyRequestAdapter(this, arrayOfPrices);
                    ListView itemsListView=findViewById(R.id.list_dynamic);
                    itemsListView.setAdapter(adapter);

                    //barcodeResult.setText("Product barcode "+barcode.displayValue);
                }
            }else {
                barcodeResult.setText("No barcode found");
            }
        }
            super.onActivityResult(requestCode, resultCode, data);
    }
}
