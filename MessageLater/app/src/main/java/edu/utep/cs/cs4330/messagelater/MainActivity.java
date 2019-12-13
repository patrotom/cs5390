package edu.utep.cs.cs4330.messagelater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText phoneEdit, delayEdit, msgEdit;
    private static final int REQUEST_SMS_PERMISSION = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneEdit = findViewById(R.id.phoneEdit);
        delayEdit = findViewById(R.id.delayEdit);
        msgEdit = findViewById(R.id.msgEdit);

        requestSmsPermission();
    }

    public void sendClicked(View view) {
        Intent intent = new Intent(this, MessageIntentService.class);
        intent.putExtra("phone", phoneEdit.getText().toString());
        intent.putExtra("delay", delayEdit.getText().toString());
        intent.putExtra("message", msgEdit.getText().toString());
        startService(intent);
    }

    private void requestSmsPermission() {
        String smsPermission = Manifest.permission.SEND_SMS;
        int grant = ContextCompat.checkSelfPermission(this, smsPermission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[] { smsPermission };
            ActivityCompat.requestPermissions(this, permissions, REQUEST_SMS_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SMS_PERMISSION) {
            showToast(grantResults[0] == PackageManager.PERMISSION_GRANTED ?
                    "Permission granted!" : "Permission not granted!");
        }
    }

    private void showToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }
}
