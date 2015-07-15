package cn.xm.weidongjian.progressbutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import cn.xm.weidongjian.progressbuttonlib.ProgressButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (ProgressButton) findViewById(R.id.button);
        button.setOnClickListener(this);
        button.setOnAnimFinishListener(new ProgressButton.onAnimFinish() {
            @Override
            public void onFinish() {
                Log.d("debug", "onFinish");
            }
        });
        findViewById(R.id.stop).setOnClickListener(this);
        findViewById(R.id.finish).setOnClickListener(this);
        findViewById(R.id.remove).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                button.startRotate();
                break;
            case R.id.stop:
                button.animError();
                break;
            case R.id.finish:
                button.animFinish();
                break;
            case R.id.remove:
                button.removeDrawable();
                break;
            default:
                break;
        }
    }
}
