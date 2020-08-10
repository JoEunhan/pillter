package com.nani.pillter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



    public class MainActivity extends BaseActivity implements View.OnLongClickListener,View.OnClickListener {
        @BindView(R.id.user_inform)
        Button user_inform;
        @BindView(R.id.disease_inform)
        Button disease_inform;
        @BindView(R.id.upload_pill)
        Button upload_pill;
        @BindView(R.id.arlam)
        Button arlam;
        @BindView(R.id.chat_famlily)
        Button chat_family;
        @BindView(R.id.cash)
        Button cash;


        @BindView(R.id.mypage_layout)
        LinearLayout mypage_layout;

        Context context = this;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            connectOnClickListner();
            user_inform.setOnLongClickListener(this);
            disease_inform.setOnLongClickListener(this);
            upload_pill.setOnLongClickListener(this);
            arlam.setOnLongClickListener(this);
            chat_family.setOnLongClickListener(this);
            cash.setOnLongClickListener(this);
            mypage_layout.setOnLongClickListener(this);


        }

        public List<View> getAllButtons(ViewGroup layout) {
            List<View> viewList = new ArrayList<>();
            for (int i = 0; i < layout.getChildCount(); i++) {
                View v = layout.getChildAt(i); //해당 테이블로우 전부 가져오기
                viewList.addAll(v.getTouchables()); //해당 테이블로우의 버튼 id 전부 가져오기
            }
            return viewList;
        }

        private void connectOnClickListner() {
            Log.d("test", "========================= connectOnClickListener =======================");

            List<View> btnList = getAllButtons(mypage_layout);
            for (View btn : btnList) {
                btn.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.user_inform:
                    toastShow("회원님의 정보 입니다.");
                    newStartActivity(MypageActivity.class);
                    break;
                case R.id.disease_inform:
                    toastShow("알약 질병 정보를 변경하실 수 있습니다.");
                    newStartActivity(disease_list.class);
                    break;
                case R.id.upload_pill:
                    toastShow("드시기 시작한 알약을 입력하세요.");
                    newStartActivity(drug_list.class);
                    break;

            }
        }

        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.user_inform:
                    toastShow("회원님의 정보 입니다.");
                    newStartActivity(MypageActivity.class);
                    break;

                case R.id.disease_inform:
                    toastShow("알약 질병 정보를 변경하실 수 있습니다.");
                    newStartActivity(disease_list.class);
                    break;
                case R.id.upload_pill:
                    toastShow("드시기 시작한 알약을 입력하세요.");
                    newStartActivity(drug_list.class);
                    break;

            }
            return true;
        }

    }