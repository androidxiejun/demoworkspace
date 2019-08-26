package com.example.dhht_70.demotest3;

import android.content.Context;
import android.util.Log;

import com.unisound.client.SpeechConstants;
import com.unisound.client.SpeechSynthesizer;
import com.unisound.client.SpeechSynthesizerListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xyuxiao on 2017/9/25.
 */

public class UscOperate {
    private SpeechSynthesizer mTTSPlayer;
    private static Context mContext;

    private UscOperate(Context context) {
        this.mContext = context;
    }

    public static UscOperate getInstance(Context context){
        mContext = context;
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder{
        private static final UscOperate INSTANCE = new UscOperate(mContext);
    }

    /**
     * 初始化本地离线TTS
     */
    public void initTts() {
        // 初始化语音合成对象

        mTTSPlayer = new SpeechSynthesizer(mContext, Config.APP_KEY, Config.SECRET);
        // 设置本地合成
        mTTSPlayer.setOption(SpeechConstants.TTS_SERVICE_MODE, SpeechConstants.TTS_SERVICE_MODE_LOCAL);
        //getAssets().

        File _FrontendModelFile = new File(Config.FRONTEND_MODEL);
        if (!_FrontendModelFile.exists()) {
            //            Toast.makeText(mContext, "文件：" + Config.FRONTEND_MODEL + "不存在，请将assets下相关文件拷贝到SD卡指定目录！", Toast.LENGTH_SHORT).show();
            Log.e("======>UscOperate.class", "文件：" + Config.FRONTEND_MODEL + "不存在，请将assets下相关文件拷贝到SD卡指定目录！");
            copy(mContext,Config.FRONTEND_MODEL_NAME,Config.BASE_DIR,Config.FRONTEND_MODEL_NAME);
        }
        File _BackendModelFile = new File(Config.BACKEND_MODEL);
        if (!_BackendModelFile.exists()) {
            //            Toast.makeText(mContext, "文件：" + Config.BACKEND_MODEL + "不存在，请将assets下相关文件拷贝到SD卡指定目录！", Toast.LENGTH_SHORT).show();
            Log.e("======>UscOperate.class", "文件：" + Config.BACKEND_MODEL + "不存在，请将assets下相关文件拷贝到SD卡指定目录！");
            copy(mContext,Config.BACKEND_MODEL_NAME,Config.BASE_DIR,Config.BACKEND_MODEL_NAME);
        }

        // 设置前端模型
        mTTSPlayer.setOption(SpeechConstants.TTS_KEY_FRONTEND_MODEL_PATH, Config.FRONTEND_MODEL);
        // 设置后端模型
        mTTSPlayer.setOption(SpeechConstants.TTS_KEY_BACKEND_MODEL_PATH, Config.BACKEND_MODEL);

        mTTSPlayer.setOption(SpeechConstants.TTS_KEY_VOICE_VOLUME, 100);

        try {
            // 初始化合成引擎
            mTTSPlayer.init(null);
        }catch (Exception e){
            e.printStackTrace();
        }


        // 设置回调监听
        mTTSPlayer.setTTSListener(new SpeechSynthesizerListener() {

            @Override
            public void onEvent(int type) {
                switch (type) {
                    case SpeechConstants.TTS_EVENT_INIT:
                        // 初始化成功回调
                        break;
                    case SpeechConstants.TTS_EVENT_SYNTHESIZER_START:
                        // 开始合成回调
                        break;
                    case SpeechConstants.TTS_EVENT_SYNTHESIZER_END:
                        // 合成结束回调
                        break;
                    case SpeechConstants.TTS_EVENT_BUFFER_BEGIN:
                        // 开始缓存回调
                        break;
                    case SpeechConstants.TTS_EVENT_BUFFER_READY:
                        // 缓存完毕回调
                        break;
                    case SpeechConstants.TTS_EVENT_PLAYING_START:
                        // 开始播放回调
                        break;
                    case SpeechConstants.TTS_EVENT_PLAYING_END:
                        // 播放完成回调
                        break;
                    case SpeechConstants.TTS_EVENT_PAUSE:
                        // 暂停回调
                        break;
                    case SpeechConstants.TTS_EVENT_RESUME:
                        // 恢复回调
                        break;
                    case SpeechConstants.TTS_EVENT_STOP:
                        // 停止回调
                        break;
                    case SpeechConstants.TTS_EVENT_RELEASE:
                        // 释放资源回调
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onError(int type, String errorMSG) {
                // 语音合成错误回调
                Log.e("======>UscOperate.class", "语音合成错误，错误类型：" + type + ",错误信息:" + errorMSG);
            }
        });

    }

    /**
     * 播放
     *
     * @param playText
     */
    public void ttsPlay(String playText) {
        try {
            mTTSPlayer.playText(playText);
        } catch (Exception e) {
            //BaseUtil.saveErrorMsgToLocal(e.getMessage(), "usclibrary");
            e.printStackTrace();
            Log.e("======>UscOperate.class", "播放失败，失败原因：" + e.toString());
        }
    }

    /**
     * 主动停止识别
     */
    public void ttsStop() {
        if (mTTSPlayer != null) {
            mTTSPlayer.stop();
        }
    }

    /**
     * 主动释放离线引擎
     */
    public void onDestroy() {
        if (mTTSPlayer != null) {
            mTTSPlayer.release(SpeechConstants.TTS_RELEASE_ENGINE, null);
        }
    }

    public static void copy(Context context,String ASSETS_NAME,String savePath, String saveName){
        String filename = savePath +File.separator+ saveName;
        File dir = new File(savePath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        InputStream is = null;
        FileOutputStream fos = null;

        if (!new File(filename).exists()){
            try {
                is = context.getAssets().open(ASSETS_NAME);
                fos = new FileOutputStream(filename);

                byte[] buffer = new byte[7168];
                int count = 0;
                while ((count = is.read(buffer)) > 0){
                    fos.write(buffer,0,count);
                }
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Log.d("======>UscOperate.class",ASSETS_NAME+"已存在");
        }

    }


}
