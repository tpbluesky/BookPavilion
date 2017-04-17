package com.tpbluesky.bookpavilion.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.mylhyl.zxing.scanner.common.Intents;
import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.adapters.AbstractBaseAdapter;
import com.tpbluesky.bookpavilion.adapters.RankGridViewAdapter;
import com.tpbluesky.bookpavilion.adapters.RecommendGridViewAdapter;
import com.tpbluesky.bookpavilion.atys.AdDetailActivity;
import com.tpbluesky.bookpavilion.atys.BookDetailActivity;
import com.tpbluesky.bookpavilion.atys.MainActivity;
import com.tpbluesky.bookpavilion.atys.ScannerActivity;
import com.tpbluesky.bookpavilion.atys.SearchActivity;
import com.tpbluesky.bookpavilion.http.HttpUtils;
import com.tpbluesky.bookpavilion.http.IndexBookStrategy;
import com.tpbluesky.bookpavilion.http.JsonResult;
import com.tpbluesky.bookpavilion.model.Activity;
import com.tpbluesky.bookpavilion.model.Book;
import com.tpbluesky.bookpavilion.tools.NetworkUtils;
import com.tpbluesky.bookpavilion.tools.StringUtils;
import com.tpbluesky.bookpavilion.views.AdViewPager;
import com.tpbluesky.bookpavilion.views.FixedPtrFrameLayout;
import com.tpbluesky.bookpavilion.views.FullGridView;
import com.tpbluesky.bookpavilion.views.ObservableScrollView;
import com.tpbluesky.bookpavilion.views.ToastMaker;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 用于呈现首页的Fragment
 */
@ContentView(R.layout.fragment_index)
public class IndexFragment extends BaseFragment {

    private final static String TAG = "IndexFragment";

    @ViewInject(R.id.scrollView)
    private ObservableScrollView scrollView;

    @ViewInject(R.id.gv_rank)
    private GridView gv_rank;   //五个榜单

    @ViewInject(R.id.ad_viewpager)
    private AdViewPager ad_viewpager;   //广告轮换栏
    private List<Activity> adDatas = new ArrayList<Activity>();   //ViewPager上的图片数据


    @ViewInject(R.id.gv_recommend)
    private FullGridView gv_recommend;    //推荐列表
    private AbstractBaseAdapter mRecommedGridViewAdapter; //GrideView数据适配器
    private List<Book> bookDatas = new ArrayList<Book>();   //GridView上的数据

    @ViewInject(R.id.ptr_content)
    private FixedPtrFrameLayout ptr_content;

    @ViewInject(R.id.iv_index_category)
    private ImageView iv_index_category;
    @ViewInject(R.id.ll_index_search)
    private LinearLayout ll_index_search;
    @ViewInject(R.id.iv_index_scan)
    private ImageView iv_index_scan;

    //请求第几页内容
    private int page = 1;

    private boolean finishLoadRecomend = false;
    private boolean loadMore = false;

    @Override
    protected void initViews() {

        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
        ptr_content.setHeaderView(header);
        ptr_content.addPtrUIHandler(header);

        final PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(getActivity());
        ptr_content.setFooterView(footer);
        ptr_content.addPtrUIHandler(footer);

        ptr_content.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                if (!NetworkUtils.isConnected(getActivity())) {
                    showNetWorkErrorDialog();
                    return;
                }
                page++;
                loadMore = true;
                loadRecommendDatas();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                finishLoadRecomend = false;
                bookDatas.clear();
                loadRecommendDatas();
                ptr_content.setMode(PtrFrameLayout.Mode.BOTH);
            }
        });

        ad_viewpager.setNestParent(ptr_content);
        ad_viewpager.setImageByRes(new Integer[]{R.mipmap.activity_one, R.mipmap.activity_two, R.mipmap.activity_three});

        gv_rank.setAdapter(new RankGridViewAdapter(getActivity()));

        mRecommedGridViewAdapter = new RecommendGridViewAdapter(getActivity(), bookDatas);
        gv_recommend.setAdapter(mRecommedGridViewAdapter);

        loadAdViewPagerDatas();
        loadRecommendDatas();
    }

    /**
     * 初始化ViewPager上显示的数据
     */
    private void loadAdViewPagerDatas() {
        adDatas.clear();
        Map<String, String> map = new HashMap<String, String>();
        map.put("service", "Activity.getActivityList");
        HttpUtils.httpPost(getActivity(), map, new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        JsonResult<List<Activity>> jsonResult = StringUtils.String2Gson(result, new TypeToken<JsonResult<List<Activity>>>() {
                        }.getType());
                        //正确获取数据
                        if (jsonResult.getCode() == 0) {
                            List<Activity> datas = jsonResult.getInfo();
                            adDatas.addAll(datas);

                            List<String> urls = new ArrayList<String>();
                            for (Activity ab : datas) {
                                urls.add(ab.getImage_src());
                            }
                            ad_viewpager.setImageByUrl(urls);
                            ad_viewpager.setmOnItemClickListener(new AdViewPager.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    AdDetailActivity.startActivity(getActivity(), adDatas.get(position).getActivity_url());
                                }
                            });
                        } else {
                            ToastMaker.makeShortToast(jsonResult.getMsg());
                        }
                    }

                    @Override
                    public void onFail(String error) {
                        ToastMaker.makeShortToast(error);
                    }
                }
        );
    }

    /**
     * 加载GridView上显示的数据
     */
    private void loadRecommendDatas() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("service", "Book.GetBooksOrderBy");
        map.put("pageNum", page + "");
        HttpUtils.httpPost(getActivity(), map, new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        JsonResult<List<Book>> jsonResult = StringUtils.String2Gson(result, new TypeToken<JsonResult<List<Book>>>() {
                        }.getType(), new IndexBookStrategy());
                        //正确获取数据
                        if (jsonResult.getCode() == 0) {
                            List<Book> datas = jsonResult.getInfo();
                            bookDatas.addAll(datas);
                            mRecommedGridViewAdapter.setDatas(bookDatas);
                        } else {
                            //没有更多数据
                            page--;
                            ToastMaker.makeShortToast("没有更多数据了");
                            ptr_content.setMode(PtrFrameLayout.Mode.REFRESH);
                        }
                        finishLoadRecomend = true;
                        if ((finishLoadRecomend) || loadMore) {
                            ptr_content.refreshComplete();
                        }
                    }

                    @Override
                    public void onFail(String error) {
                        page--;
                        ptr_content.refreshComplete();
                        ToastMaker.makeShortToast(error);
                    }
                }
        );
    }


    /**
     * 单击事件
     */
    @Event({R.id.iv_index_category, R.id.ll_index_search, R.id.iv_index_scan})
    private void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_index_category:
                ((MainActivity) getActivity()).setCurrentFragmennt(1);
                break;
            case R.id.ll_index_search:
                SearchActivity.startActivity(getActivity());
                break;
            case R.id.iv_index_scan:
                startActivityForResult(new Intent(getActivity(), ScannerActivity.class), ScannerActivity.REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == android.app.Activity.RESULT_OK) {
            switch (requestCode) {
                case ScannerActivity.REQUEST_CODE:
                    String stringExtra = data.getStringExtra(Intents.Scan.RESULT);
                    ToastMaker.makeShortToast(stringExtra);
                    BookDetailActivity.startActivity(getActivity(),
                            BookDetailActivity.TYPE_ISBN,
                            stringExtra, false);
                    break;
            }
        }
    }

    @Event(value = R.id.gv_recommend, type = AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.gv_recommend:
                BookDetailActivity.startActivity(getActivity(),
                        BookDetailActivity.TYPE_ID,
                        bookDatas.get(position).id, false);
                break;
        }
    }
}
