package bank.ruts.ac.th.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public FirebaseDatabase firebaseDatabase,databasetext;
    public DatabaseReference databaseReference_led1,datagetFirebase;

    public TextView txtLight;
    public Button led;

    public Integer value,value_refer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLight = (TextView)findViewById(R.id.txtSensor);
        led = (Button)findViewById(R.id.btnLed);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference_led1 = firebaseDatabase.getReference("Home/Bedroom/led1");

        databaseReference_led1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(Integer.class);
                if(value == 1){
                    led.setText("LED1 ON");
                    led.setBackgroundColor(led.getContext().getResources().getColor(R.color.colorPrimary));
                    value_refer = 0;
                }
                else {
                    led.setText("LED OFF");
                    led.setBackgroundColor(led.getContext().getResources().getColor(R.color.colorAccent));
                    value_refer = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        led.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference_led1.setValue(value_refer);

            }
        });


        databasetext = FirebaseDatabase.getInstance();
        datagetFirebase = databasetext.getReference("Home/Sensor");

        datagetFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map map = (Map)dataSnapshot.getValue();
                String data1 = String.valueOf(map.get("Light"));
                txtLight.setText(data1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
