package eshore.gdtel.com.restartplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import eshore.gdtel.com.restartplan.helper.TestSingleton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_proposal = (TextView) findViewById(R.id.tv_proposal);

        tv_proposal.setText("方案");


    }

    @Override
    protected void onStart() {
        super.onStart();

        TestSingleton mSingleton = TestSingleton.getmSingleton();

        Log.e("mSingleton", "mSingleton="+mSingleton);

    }
}
