package clBrain.db;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminAuth2 extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Admin").child("Password");

    EditText edtPassword;
    Button btnInput;
    String RealPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_auth);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RealPass = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        edtPassword = ( EditText) findViewById(R.id.adminpasswordinput_edt_Password);
        edtPassword.setText(GlobalValues.AdminPassword);
        btnInput = (Button) findViewById(R.id.adminpasswordinput_btn_confirm_password);
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RealPass.equals(edtPassword.getText().toString())){
                    if (((CheckBox)findViewById(R.id.admin_auth_remember_password)).isChecked()){
                        GlobalValues.AdminPassword = edtPassword.getText().toString();
                    }
                    startActivity(new Intent(getApplicationContext(), AdminOptions.class));
                }
                else {
                    Toast.makeText(AdminAuth2.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                    edtPassword.setText("");
                }
            }
        });
    }

}
