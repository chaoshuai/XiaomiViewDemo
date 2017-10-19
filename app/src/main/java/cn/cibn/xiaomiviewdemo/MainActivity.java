package cn.cibn.xiaomiviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private RoundIndicatorView roundIndicatorView;
    private MySportView mySportView;
    private XiaoMiSportView xiaoMiSportView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mySportView = (MySportView) findViewById(R.id.my_sportview);
//        mySportView.startLoadingAnim();
//        mySportView.animate().rotation(360).setDuration(5000);

//        roundIndicatorView = (RoundIndicatorView) findViewById(R.id.riv);
//        roundIndicatorView.setMaxNum(8000);
//        roundIndicatorView.setCurrentNumAnim(5000);

        xiaoMiSportView = (XiaoMiSportView) findViewById(R.id.xmp);
        xiaoMiSportView.setMaxNum(8000);
        xiaoMiSportView.setCurrentNumAnim(4000);

    }
}
