package com.yizhen.testuseaar;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.yizhen.piclib.Utils;

/**
 * 如何加载 .aar 包。（这个包和 .jar 类似）
 * aar和jar最大的区别是，aar包括了res里文件，而jar只有java文件。
 * 加载aar包是，最主要的是配置 build.gradle（module目录下的），需要加入的内容如下：
 *
 * 根目录下：repositories{
                flatDir {
                    dirs 'libs'
                }
            }
 dependencies 里加入
        compile(name:'piclib-debug', ext:'aar')  // piclib-debug 可变的，
                                                // 根据实际的名称来写，不需要加后缀 .aar，因为后面会ext 会加入。
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_title = findViewById(R.id.tv_title);

        Utils.showLog(this, tv_title);

    }
}
