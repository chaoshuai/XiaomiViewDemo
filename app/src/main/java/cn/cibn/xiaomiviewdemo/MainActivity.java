package cn.cibn.xiaomiviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private RoundIndicatorView roundIndicatorView;
    private MySportView mySportView;
    private XiaoMiSportView xiaoMiSportView;
    private Button button;
    private boolean isNeedFresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xiaoMiSportView = (XiaoMiSportView) findViewById(R.id.xmp);
        button = (Button) findViewById(R.id.button);

        xiaoMiSportView.setMaxNum(8000);
//        xiaoMiSportView.setCurrentNumAnim(4000);
        xiaoMiSportView.setCurrentNum(4000);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiaoMiSportView.setIsNeedFresh(isNeedFresh);
                isNeedFresh = !isNeedFresh;
            }
        });
    }
}
