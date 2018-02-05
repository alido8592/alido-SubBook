/*
 * Copyright 2018 Team Me. CMPUT 301. University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at University of Alberta.
 * You may find a copy of the license in this project. Otherwise please contact alido@ualberta.ca.
 */

package comalido8592.github.alido_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import static java.lang.Float.parseFloat;

public class SingleSubscription extends AppCompatActivity {

    private EditText nameEntry;
    private EditText rateEntry;
    private EditText commentEntry;
    private DatePicker dateEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_subscription);

        nameEntry = (EditText) findViewById(R.id.name);
        rateEntry = (EditText) findViewById(R.id.rate);
        commentEntry = (EditText) findViewById(R.id.comment);
        dateEntry = (DatePicker) findViewById(R.id.datePicker);
        Button saveButton = (Button) findViewById(R.id.confirm);

        Intent intent = getIntent();

        Subscription selectedSub =
                (Subscription) intent.getExtras().getSerializable("Selected_Sub");
        final int position = intent.getIntExtra("position",-1);
        nameEntry.setText(selectedSub.getName());
        rateEntry.setText(Float.toString(selectedSub.getRate()));
        commentEntry.setText(selectedSub.getComment());
        dateEntry.updateDate(selectedSub.getDate().get(Calendar.YEAR),
                selectedSub.getDate().get(Calendar.MONTH),selectedSub.getDate().get(Calendar.DATE));

        saveButton.setOnClickListener(new View.OnClickListener() {
            // passes info back to main
            // from https://stackoverflow.com/questions/26703691/android-return-object-as-a-activity-result
            // 18-02-05
            @Override
            public void onClick(View v) {
                String subName = nameEntry.getText().toString();
                String subRate = rateEntry.getText().toString();
                float subRateValue = parseFloat(subRate);
                String subComment = commentEntry.getText().toString();
                int day = dateEntry.getDayOfMonth();
                int month = dateEntry.getMonth();
                int year = dateEntry.getYear();
                Calendar subDate = Calendar.getInstance();
                subDate.set(year,month,day);
                Subscription subscription = new Subscription(subName,subDate,
                                                            subRateValue,subComment);
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Subscription_New",subscription);
                bundle.putInt("position",position);
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });
    }
}
