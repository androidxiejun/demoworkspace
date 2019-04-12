package com.example.librafingergenerallibrary.listenner;

/**
 * 认证是否成功回调
 * Created by dhht on 17/5/3.
 */

public interface OnIdentitySuccessListener {
    /**
     * @param isSuccess 是否成功
     * @param id        操作人鉴权记录保存返回id,用于对操作记录做关联,
     *                  鉴权的时候，返回鉴权类的id,
     *                  录入指纹的时候返回指纹信息id
     *                  删除指纹的时候放入uuid
     */
    void onSuccess(boolean isSuccess, Object id);
}
