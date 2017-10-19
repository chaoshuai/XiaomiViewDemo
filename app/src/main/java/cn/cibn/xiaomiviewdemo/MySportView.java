package cn.cibn.xiaomiviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by 15210 on 2017/10/19.
 */

public class MySportView extends RelativeLayout {
    private LoadingLineView loadingLineView;
    private View view;

    public MySportView(Context context) {
        this(context,null);
    }

    public MySportView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MySportView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.my_sport_layout, this);
        loadingLineView = (LoadingLineView) view.findViewById(R.id.loading_line_view);
    }

    public void startLoadingAnim(){
        loadingLineView.animate().rotation(90).setDuration(5000);
    }
}
