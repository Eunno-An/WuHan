package com.example.wuhan.ui.notification;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wuhan.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class NotificationFragment extends Fragment {
    private TextView tv;
    private NotificationViewModel notificationViewModel;
    private RecyclerView recyclerView;
    private View root;
    private ArrayList<ItemObject> list = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationViewModel =
                ViewModelProviders.of(this).get(NotificationViewModel.class);
        root = inflater.inflate(R.layout.fragment_notification, container, false);


        recyclerView = (RecyclerView)root.findViewById(R.id.notificationList);
        TextView t = (TextView)root.findViewById(R.id.tv);

        //AsyncTask 작동시킴(파싱)
        new Description().execute();
        return root;
    }

    private class Description extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(root.getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for(int i = 0; i < 10; i++) {

                    char abc =(char) ('1'+i);
                    String aa = Character.toString(abc);
                    String url2 = "https://www.mohw.go.kr/react/al/sal0301ls.jsp?PAR_MENU_ID=04&MENU_ID=0403&BOARD_ID=140&page="+abc;
                    Document doc = Jsoup.connect(url2).get();

                    Elements mElementDataSize = doc.select("div[class=board_list] table tbody").select("tr"); //필요한 녀석만 꼬집어서 지정
                    int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                    for (Element elem : mElementDataSize) { //이렇게 요긴한 기능이
                        //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
                        String my_title = elem.select("a[class=txt_title]").text();
                        String id = elem.select("td[class=m_dp_n]").text().substring(0, 5);
                        String url = "http://www.mohw.go.kr" + elem.select("a[class = txt_title]").attr("href");
                        String day = elem.text();
                        int twotwo = day.indexOf("2020-");
                        if(twotwo == -1){
                            day = "";
                        }
                        else {
                            day = day.substring(twotwo, twotwo + 10);
                        }
                        //title: $(this).find('a.txt_title').text(),
                        // url: "http://www.mohw.go.kr"+ $(this).find('a.txt_title').attr('href'),
                        //id: $(this).find('td.m_dp_n').text().slice(0,5),
                        if (my_title.indexOf("코로나") != -1) {
                            list.add(new ItemObject(id, my_title, url, day));
                        }
                    }

                    //추출한 전체 <li> 출력해 보자.
                    Log.d("debug :", "List " + mElementDataSize);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.

            MyAdapter myAdapter = new MyAdapter(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);
            progressDialog.dismiss();
        }
    }

}