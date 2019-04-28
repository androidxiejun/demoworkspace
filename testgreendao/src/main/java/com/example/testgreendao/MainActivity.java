package com.example.testgreendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testgreendao.entity.bean.Gun;
import com.example.testgreendao.entity.bean.GunData;
import com.example.testgreendao.entity.bean.Score;
import com.example.testgreendao.entity.bean.Student;
import com.example.testgreendao.entity.bean.StudentScore;
import com.example.testgreendao.entity.bean.User;
import com.example.testgreendao.entity.dao.ScoreDao;
import com.example.testgreendao.entity.dao.StudentDao;
import com.example.testgreendao.entity.sql.GreenDaoUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private TextView mTxt;
    private Button mSaveBtn;
    private Button mGetBtn;
    private Button mUpdateBtn;
    private Button mDelBtn;
    private Button mMutiSaveBtn;
    private Button mMutiGetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTxt = findViewById(R.id.txt);
        mSaveBtn = findViewById(R.id.data_save);
        mGetBtn = findViewById(R.id.data_get);
        mDelBtn = findViewById(R.id.data_del);
        mUpdateBtn = findViewById(R.id.data_update);
        mMutiSaveBtn = findViewById(R.id.data_muti_save);
        mMutiGetBtn = findViewById(R.id.data_muti_get);
        mSaveBtn.setOnClickListener(this);
        mGetBtn.setOnClickListener(this);
        mDelBtn.setOnClickListener(this);
        mUpdateBtn.setOnClickListener(this);
        mMutiSaveBtn.setOnClickListener(this);
        mMutiGetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.data_save:
                List<Gun> gunList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    Gun gun = new Gun();
                    gun.setGunName("手枪" + i);
                    gun.setGunNo("999" + i);
                    gun.setCustomId((long) 111);
                    gunList.add(gun);
                    GreenDaoUtil.getInstance().getDaoSession().getGunDao().save(gun);
                }
                User user = new User();
                user.setId((long) 222);
                user.setUserName("老谢");
                user.setUserNo("987678");
                user.setGuns(gunList);
                GreenDaoUtil.getInstance().getDaoSession().getUserDao().save(user);
                GunData gunData = new GunData();
                gunData.setGunName("九八式手枪");
                gunData.setGunNo("12345");
                gunData.setHanderName("老张");
                gunData.setHanderNo("87456322");
                gunData.setUserId(user.getId());
                GreenDaoUtil.getInstance().getDaoSession().getGunDataDao().save(gunData);
                break;
            case R.id.data_get:
                List<GunData> gunData1 = GreenDaoUtil.getInstance().getDaoSession().getGunDataDao().loadAll();
                for (GunData gunData2 : gunData1) {
                    Log.i(TAG, "枪名称-----" + gunData2.getGunName());
                    Log.i(TAG, "枪用户名-----" + gunData2.getMUser().getUserName());
                }
                User user1 = GreenDaoUtil.getInstance().getDaoSession().getUserDao().load((long) 222);
                for (Gun gun : user1.getGuns()) {
                    Log.i(TAG, "gun-----" + gun.getGunName());
                }
                break;
            case R.id.data_del:
                GreenDaoUtil.getInstance().getDaoSession().getUserDao().deleteAll();
                GreenDaoUtil.getInstance().getDaoSession().getGunDataDao().deleteAll();
                GreenDaoUtil.getInstance().getDaoSession().getStudentDao().deleteAll();
                ;
                GreenDaoUtil.getInstance().getDaoSession().getScoreDao().deleteAll();
                GreenDaoUtil.getInstance().getDaoSession().getStudentScoreDao().deleteAll();
                break;
            case R.id.data_update:
                List<Gun> gunList1 = GreenDaoUtil.getInstance().getDaoSession().getGunDao().loadAll();
                for (int i = 0; i < gunList1.size(); i++) {
                    Gun gun = gunList1.get(i);
                    gun.setGunName("老炮儿----" + i);
                    GreenDaoUtil.getInstance().getDaoSession().getGunDao().update(gun);
                }
                break;
            case R.id.data_muti_save:
                Student student = new Student();
                student.setAge("26");
                student.setName("老牛");
                GreenDaoUtil.getInstance().getDaoSession().getStudentDao().save(student);
                for (int i = 0; i < 5; i++) {
                    StudentScore studentScore = new StudentScore();
                    Score score = new Score();
                    score.setScoreCode("111" + i);
                    score.setScoreName("数学" + i);
                    GreenDaoUtil.getInstance().getDaoSession().getScoreDao().save(score);
                    studentScore.setScoreId(score.getId());
                    studentScore.setStudentId(student.getId());
                    GreenDaoUtil.getInstance().getDaoSession().getStudentScoreDao().save(studentScore);
                }
//
//                Score scoreNew = new Score();
//                scoreNew.setScoreName("英语");
//                scoreNew.setScoreCode("666");
//                GreenDaoUtil.getInstance().getDaoSession().getScoreDao().save(scoreNew);
//                for (int i = 0; i < 6; i++) {
//                    Student student1 = new Student();
//                    student1.setName("老王" + i);
//                    student1.setAge("1" + i);
//                    GreenDaoUtil.getInstance().getDaoSession().getStudentDao().save(student1);
//                    StudentScore studentScore = new StudentScore();
//                    studentScore.setStudentId(student1.getId());
//                    studentScore.setScoreId(scoreNew.getId());
//                    GreenDaoUtil.getInstance().getDaoSession().getStudentScoreDao().save(studentScore);
//                }

                break;
            case R.id.data_muti_get:
                Student student1 = GreenDaoUtil.getInstance().getDaoSession().getStudentDao().queryBuilder().where(StudentDao.Properties.Name.eq("老牛")).unique();
                List<Score> scoreList = student1.getScoreList();
                if (scoreList != null && scoreList.size() > 0)
                    for (Score score : scoreList) {
                        Log.i(TAG, "课程名称-----" + score.getScoreName());
                    }
//                List<Score> scores = GreenDaoUtil.getInstance().getDaoSession().getScoreDao().loadAll();
//                if(scores!=null&&scores.size()>0){
//                    for (Score score:scores){
//                        Log.i(TAG,"scoreCode-----"+score.getScoreCode());
//                    }
//                }
//                Score score = GreenDaoUtil.getInstance().getDaoSession().getScoreDao().queryBuilder().where(ScoreDao.Properties.ScoreCode.eq("666")).unique();
//                List<Student> studentList = score.getStudentList();
//                if (studentList != null && studentList.size() > 0) {
//                    for (Student student2 : studentList) {
//                        Log.i(TAG, "学生名称-----" + student2.getName());
//                    }
//                }
                break;
        }
    }
}
