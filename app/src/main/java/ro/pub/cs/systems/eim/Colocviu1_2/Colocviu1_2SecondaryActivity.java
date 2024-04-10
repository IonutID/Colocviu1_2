package ro.pub.cs.systems.eim.Colocviu1_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Colocviu1_2SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu12_secondary);

        Intent intent = getIntent();

        String allTerms = intent.getStringExtra(Constants.All_Terms);
        if (allTerms != null) {
            String[] terms = allTerms.split("\\+");
            int sum = 0;
            for (String term : terms) {
                sum += Integer.parseInt(term.trim());
            }
            Log.d("TAG", "sum: " + sum);
            Intent result = new Intent();
            result.putExtra(Constants.Result, String.valueOf(sum));
            setResult(RESULT_OK, result);
            finish();
        }

    }
}