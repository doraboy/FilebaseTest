package tw.dora.filebasetest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase,mDatabase2;
    private FirebaseDatabase fdb;
    private EditText username;
    private TextView mesg;
    private Button testme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testme = findViewById(R.id.testme);
        username = findViewById(R.id.username);
        mesg = findViewById(R.id.mesg);

        testme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(null);
            }
        });


        fdb = FirebaseDatabase.getInstance();
        mDatabase = fdb.getReference("bike");



        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                // ...
                Bike b2 = dataSnapshot.getValue(Bike.class);
                if (b2 != null) {
                    mesg.setText(b2.name + ":" + b2.speed);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(postListener);
    }

    //丟物件屬性上去
    public void sendData(View view) {
        Log.v("brad", "sendData");
        Bike b1 = new Bike();
        b1.speed = 0.1*((int)(Math.random()*10000));
        b1.name = "Brad";
        mDatabase.setValue(b1);
    }

    public void test1(View view) {
        //直接丟值上去
        FirebaseDatabase fdb2 = FirebaseDatabase.getInstance();
        mDatabase2 = fdb2.getReference("account");
        mDatabase2.setValue(username.getText().toString());
    }
}
