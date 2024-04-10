package ro.pub.cs.systems.eim.Colocviu1_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

public class Colocviu1_2MainActivity extends AppCompatActivity {

    EditText nextTerm;
    TextView allTerms;
    Button addButton, computeButton;
    String sum;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, intent.getStringExtra(Constants.Broadcast), Toast.LENGTH_LONG).show();
        }
    }

    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_2_main);

        intentFilter.addAction(Constants.Action_Type);

        nextTerm = findViewById(R.id.next_term);
        allTerms = findViewById(R.id.all_terms);
        addButton = findViewById(R.id.add_button);
        computeButton = findViewById(R.id.compute_button);

        allTerms.setText("");

        addButton.setOnClickListener(v -> {
            String currentText = allTerms.getText().toString();
            Log.d("debug", "onCreate: "+ currentText);
            String next = nextTerm.getText().toString();
            if (currentText.isEmpty()) {
                allTerms.setText(next);
            } else {
                allTerms.setText(currentText + " + " + next);
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                sum = result.getData().getStringExtra(Constants.Result);
                Toast.makeText(this, "Sum is:" + sum, Toast.LENGTH_LONG).show();
                if(Integer.parseInt(sum) > 10) {
                    Intent intent = new Intent(getApplicationContext(), Colocviu1_2Service.class);
                    intent.putExtra(Constants.ServiceSum, Integer.parseInt(sum));
                    getApplicationContext().startService(intent);
                }
            }
        });

        computeButton.setOnClickListener(v -> {
            Intent secondaryActivity = new Intent(getApplicationContext(), Colocviu1_2SecondaryActivity.class);
            secondaryActivity.putExtra(Constants.All_Terms, String.valueOf(allTerms.getText()));
            activityResultLauncher.launch(secondaryActivity);

        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.Saved_Sum, sum);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d("Restore", "Saved Sum is: " + savedInstanceState.getString(Constants.Saved_Sum));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}