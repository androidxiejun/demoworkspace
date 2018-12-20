package com.example.testarf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.testarf.com.security.cipher.sm.RSAUtil;
import com.example.testarf.smutil.SM2Utils;
import com.example.testarf.smutil.Util;

import org.bouncycastle.util.encoders.Base64;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG=MainActivity.class.getSimpleName();
    // 国密规范测试公钥
    private  String pubk = "040AE4C7798AA0F119471BEE11825BE46202BB79E2A5844495E97C04FF4DF2548A7C0240F88F1CD4E16352A73C17B7F16F07353E53A176D684A9FE0C6BB798E857";
    private  String pubk2 = "04EFE71C4F18BF7186A905A6CC30E4EEC3831ED4F62AA99BABE6EF8EAAA45CA623C27BE6F24EF563CF148B31259925D13A15289E07FF841D67E801A05F9683C66D";
    private String pubkS = new String(Base64.encode(com.example.testarf.com.security.cipher.sm.Util.hexToByte(pubk)));
    private String pubkS2 = new String(Base64.encode(com.example.testarf.com.security.cipher.sm.Util.hexToByte(pubk2)));
    private byte[] cipherText = new byte[0];

    // 国密规范测试私钥
    String prik = "128B2FA8BD433C6C068C8D803DFF79792A519A55171B1B650C23661D15897263";
    String prik2 = "43644864A9C855AF6876F8C98A539F57DEC58F6DF3A5AA542B7449AE63E6D676";
    String prikS = new String(Base64.encode(com.example.testarf.com.security.cipher.sm.Util.hexToByte(prik)));
    String prikS2 = new String(Base64.encode(com.example.testarf.com.security.cipher.sm.Util.hexToByte(prik2)));

    String plainText = "hello kitty";
    byte[] sourceDataB = plainText.getBytes();

    private static final String DATA="D8B88534AA861A6F00863B12F421371ED3F93BB5E13316840B7B4879B2FA4DE9D801B233833AD0B4CC2C8359C64876E698458F6AFA59BCB91792554EF2E5B3F7EC9B1587A62101AB3CC41D34A40BC5DF00DBB65D9D3A6220B77E7C8FFAB15E768E18";
    private static final String DATA2="04619A44D1EC9861C7F64BE8F558A618D0C53224B3ED47FC4DA78538F6AE9245251AF50B4EDEB74C709DA9BDF40C541707BCA5E9F0B691DAC6402C00B2275F42CDA5BCC0620DEFB4FFF98F1985E5ACE8CDA90AA1F1C40DB13F6DC3986CBCC9A1B2238E0EB194B27C57E33FF345F7D011A9BC2576EB377BC6715C3E7C5CB7BA3CB6";

    // 国密规范测试用户ID
    private  String userId = "ALICE123@YAHOO.COM";

    private   byte[] c = new byte[0];;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.sm2_key_btn).setOnClickListener(this);
        findViewById(R.id.sm2_encr_btn).setOnClickListener(this);
        findViewById(R.id.sm2_decr_btn).setOnClickListener(this);
        findViewById(R.id.sm2_sign_btn).setOnClickListener(this);
        findViewById(R.id.sm2_verifysign_btn).setOnClickListener(this);
        findViewById(R.id.sm2_encry_btnb).setOnClickListener(this);
        findViewById(R.id.sm2_decry_btnb).setOnClickListener(this);
        findViewById(R.id.rsa_encry_btn).setOnClickListener(this);
        findViewById(R.id.rsa_decry_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sm2_key_btn://获取SM2密钥
                SM2Utils.generateKeyPair();
                break;
            case R.id.sm2_encr_btn://使用SM2进行加密
                String encryData="0001020304";
                String plainText = "0";
//                byte[] sourceData = plainText.getBytes();
                byte[] sourceData =new byte[1];
                sourceData[0]=0;
                try {
//                    SM2Utils.encrypt(Util.hexToByte(SM2Utils.sm2PubliKey),sourceData);
                    SM2Utils.encrypt(Util.hexToByte(pubk2),Util.hexStringToBytes(encryData));
                } catch (IOException e) { 
                    e.printStackTrace();
                }
                break;
            case R.id.sm2_decr_btn://使用SM2进行解密
                try {
//                    String result=new String(SM2Utils.decrypt(Util.hexToByte(SM2Utils.sm2PrivateKey), Util.hexToByte(SM2Utils.encrptStr)));
                    byte [] data=SM2Utils.decrypt(Util.hexToByte(prik2),Util.hexToByte(SM2Utils.encrptStr));
                    Log.i(MainActivity.TAG,"解密A获取到的数据:"+ java.util.Arrays.toString(data));
//                    String result=new String(SM2Utils.decrypt(Util.hexToByte(prik2),Util.hexToByte(SM2Utils.encrptStr)));
//                    Log.i(MainActivity.TAG,"解密A获取到的数据:"+result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.sm2_sign_btn://使用SM2生成数字证书

                try {
                    c = com.example.testarf.com.security.cipher.sm.SM2Utils.sign(userId.getBytes(), Base64.decode(prikS.getBytes()), sourceDataB);
                    Log.i(MainActivity.TAG,"生成的签名证书----"+ com.example.testarf.com.security.cipher.sm.Util.getHexString(c));
                    Log.i(MainActivity.TAG,"生成的签名证书长度----"+ com.example.testarf.com.security.cipher.sm.Util.getHexString(c).length());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.sm2_verifysign_btn://使用SM2验证数字证书
                try {
                    boolean vs = com.example.testarf.com.security.cipher.sm.SM2Utils.verifySign(userId.getBytes(), Base64.decode(pubkS.getBytes()), sourceDataB, c);
                    Log.i(MainActivity.TAG,"验签是否通过-----"+true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.sm2_encry_btnb://使用SM2进行加密
                byte[] sourceData3 =new byte[1];
                sourceData3[0]=0;
                try {
                    cipherText = com.example.testarf.com.security.cipher.sm.SM2Utils.encrypt(Base64.decode(pubkS2.getBytes()), sourceData3);
                    Log.i(MainActivity.TAG,"加密后的数据----"+new String(Base64.encode(cipherText)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.sm2_decry_btnb://使用SM2进行解密
                try {
//                    plainText = new String(com.example.testarf.com.security.cipher.sm.SM2Utils.decrypt(Base64.decode(prikS2.getBytes()), cipherText));
                    byte[] data=com.example.testarf.com.security.cipher.sm.SM2Utils.decrypt(Base64.decode(prikS2.getBytes()), cipherText);
                    Log.i(MainActivity.TAG,"解密后的数据----"+ java.util.Arrays.toString(data));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rsa_encry_btn:
                String encryData2=RSAUtil.encryptPublicData("asdfghjkasdfghjk");
                Log.i(MainActivity.TAG,"RSA加密后的数据-----"+encryData2);
                break;
            case R.id.rsa_decry_btn:
                String decryData=RSAUtil.decryptPrivateData();
                Log.i(MainActivity.TAG,"RSA解密后的数据-----"+decryData);
                break;


        }
    }
}
