package com.yizhen.testokhttp;




import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yizhen.testokhttp.adapter.ListPhotoAdapter;
import com.yizhen.testokhttp.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class ListPhotoActivity extends AppCompatActivity {

    OkHttpClient mOkHttpClient;

    RecyclerView rv_list_photo;

    ArrayList<String> mPhotoList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photo);

        LogUtil.setLog("", "222", 222);

        rv_list_photo = findViewById(R.id.rv_list_photo);
        rv_list_photo.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

        // 初始化数据
        mPhotoList = collectData();
        // 初始化网络请求
        initOkHttpClient();
        // 创建适配器
        ListPhotoAdapter adapter = new ListPhotoAdapter(mPhotoList, mOkHttpClient);
        // 设置适配器
        rv_list_photo.setAdapter(adapter);

    }

    /**
     * 初始化OkhttpClient
     */
    private void initOkHttpClient() {
        mOkHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = mOkHttpClient.newBuilder();
        builder.cache(new Cache(new File("ai"), 20 * 1024));
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                // 保存cookie通常使用SharedPreferences
                LogUtil.setLog(null, "httpUrl_saveFromResponse=", httpUrl);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl httpUrl) {

                // 从保存位置读取，注意此处不能为空，否则会导致空指针
                LogUtil.setLog(null, "httpUrl_loadForRequest=", httpUrl);

                return new ArrayList<>();
            }
        });
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                LogUtil.setLog(null, "chain_addInterceptor=", chain.toString());

                return null;
            }
        });
        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                LogUtil.setLog(null, "chain_addNetworkInterceptor=", chain.toString());

                return null;
            }
        });
        builder.build();
    }


    /**
     * 获取图片数据
     * @return
     */
    private ArrayList<String> collectData(){
        ArrayList<String> photoList = new ArrayList<String>();
        photoList.add("http://pic31.photophoto.cn/20140423/0005018369922390_b.jpg");
        photoList.add("http://pic28.photophoto.cn/20130929/0037037519732901_b.jpg");
        photoList.add("http://pic33.photophoto.cn/20141111/0005018379923550_b.jpg");
        photoList.add("http://img4.duitang.com/uploads/item/201603/13/20160313204326_3NkaL.jpeg");
        photoList.add("http://5b0988e595225.cdn.sohucs.com/images/20171223/ce378dd45b3c4222a4f7be729adeb215.jpeg");
        photoList.add("http://files.bbs.tl.changyou.com/data/attachment/forum/201802/04/112641cyd1dcoiok81obuo.jpg");
        photoList.add("http://img3.duitang.com/uploads/item/201609/01/20160901094037_3EnXB.png");
        photoList.add("http://img3.duitang.com/uploads/item/201602/20/20160220171041_hUyfT.jpeg");
        photoList.add("http://www.biaobaiju.com/uploads/20180225/12/1519534636-etMRDHiyoL.png");

        photoList.add("http://pic31.photophoto.cn/20140423/0005018369922390_b.jpg");
        photoList.add("http://pic28.photophoto.cn/20130929/0037037519732901_b.jpg");
        photoList.add("http://pic33.photophoto.cn/20141111/0005018379923550_b.jpg");
        photoList.add("http://img4.duitang.com/uploads/item/201603/13/20160313204326_3NkaL.jpeg");
        photoList.add("http://5b0988e595225.cdn.sohucs.com/images/20171223/ce378dd45b3c4222a4f7be729adeb215.jpeg");
        photoList.add("http://files.bbs.tl.changyou.com/data/attachment/forum/201802/04/112641cyd1dcoiok81obuo.jpg");
        photoList.add("http://img3.duitang.com/uploads/item/201609/01/20160901094037_3EnXB.png");
        photoList.add("http://img3.duitang.com/uploads/item/201602/20/20160220171041_hUyfT.jpeg");
        photoList.add("http://www.biaobaiju.com/uploads/20180225/12/1519534636-etMRDHiyoL.png");

        photoList.add("http://pic31.photophoto.cn/20140423/0005018369922390_b.jpg");
        photoList.add("http://pic28.photophoto.cn/20130929/0037037519732901_b.jpg");
        photoList.add("http://pic33.photophoto.cn/20141111/0005018379923550_b.jpg");
        photoList.add("http://img4.duitang.com/uploads/item/201603/13/20160313204326_3NkaL.jpeg");
        photoList.add("http://5b0988e595225.cdn.sohucs.com/images/20171223/ce378dd45b3c4222a4f7be729adeb215.jpeg");
        photoList.add("http://files.bbs.tl.changyou.com/data/attachment/forum/201802/04/112641cyd1dcoiok81obuo.jpg");
        photoList.add("http://img3.duitang.com/uploads/item/201609/01/20160901094037_3EnXB.png");
        photoList.add("http://img3.duitang.com/uploads/item/201602/20/20160220171041_hUyfT.jpeg");
        photoList.add("http://www.biaobaiju.com/uploads/20180225/12/1519534636-etMRDHiyoL.png");

        photoList.add("http://pic31.photophoto.cn/20140423/0005018369922390_b.jpg");
        photoList.add("http://pic28.photophoto.cn/20130929/0037037519732901_b.jpg");
        photoList.add("http://pic33.photophoto.cn/20141111/0005018379923550_b.jpg");
        photoList.add("http://img4.duitang.com/uploads/item/201603/13/20160313204326_3NkaL.jpeg");
        photoList.add("http://5b0988e595225.cdn.sohucs.com/images/20171223/ce378dd45b3c4222a4f7be729adeb215.jpeg");
        photoList.add("http://files.bbs.tl.changyou.com/data/attachment/forum/201802/04/112641cyd1dcoiok81obuo.jpg");
        photoList.add("http://img3.duitang.com/uploads/item/201609/01/20160901094037_3EnXB.png");
        photoList.add("http://img3.duitang.com/uploads/item/201602/20/20160220171041_hUyfT.jpeg");
        photoList.add("http://www.biaobaiju.com/uploads/20180225/12/1519534636-etMRDHiyoL.png");

        photoList.add("http://pic31.photophoto.cn/20140423/0005018369922390_b.jpg");
        photoList.add("http://pic28.photophoto.cn/20130929/0037037519732901_b.jpg");
        photoList.add("http://pic33.photophoto.cn/20141111/0005018379923550_b.jpg");
        photoList.add("http://img4.duitang.com/uploads/item/201603/13/20160313204326_3NkaL.jpeg");
        photoList.add("http://5b0988e595225.cdn.sohucs.com/images/20171223/ce378dd45b3c4222a4f7be729adeb215.jpeg");
        photoList.add("http://files.bbs.tl.changyou.com/data/attachment/forum/201802/04/112641cyd1dcoiok81obuo.jpg");
        photoList.add("http://img3.duitang.com/uploads/item/201609/01/20160901094037_3EnXB.png");
        photoList.add("http://img3.duitang.com/uploads/item/201602/20/20160220171041_hUyfT.jpeg");
        photoList.add("http://www.biaobaiju.com/uploads/20180225/12/1519534636-etMRDHiyoL.png");

        photoList.add("http://pic31.photophoto.cn/20140423/0005018369922390_b.jpg");
        photoList.add("http://pic28.photophoto.cn/20130929/0037037519732901_b.jpg");
        photoList.add("http://pic33.photophoto.cn/20141111/0005018379923550_b.jpg");
        photoList.add("http://img4.duitang.com/uploads/item/201603/13/20160313204326_3NkaL.jpeg");
        photoList.add("http://5b0988e595225.cdn.sohucs.com/images/20171223/ce378dd45b3c4222a4f7be729adeb215.jpeg");
        photoList.add("http://files.bbs.tl.changyou.com/data/attachment/forum/201802/04/112641cyd1dcoiok81obuo.jpg");
        photoList.add("http://img3.duitang.com/uploads/item/201609/01/20160901094037_3EnXB.png");
        photoList.add("http://img3.duitang.com/uploads/item/201602/20/20160220171041_hUyfT.jpeg");
        photoList.add("http://www.biaobaiju.com/uploads/20180225/12/1519534636-etMRDHiyoL.png");
        return photoList;
    }




}
