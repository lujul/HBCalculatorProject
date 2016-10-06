package laurentesp.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.text_view_0);
        textView.setText(Calculator.getLastOperand());
    }

    public void clickMeth(View view){
        final TextView textView = (TextView) findViewById(R.id.text_view_0);
        String butClicked = view.getTag().toString();
        textView.setText(Calculator.getValOutToShowtoUser(butClicked, textView.getText().toString()));
    }
}
