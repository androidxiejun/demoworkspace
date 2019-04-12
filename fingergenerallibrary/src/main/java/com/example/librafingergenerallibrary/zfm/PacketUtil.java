package com.example.librafingergenerallibrary.zfm;


import com.example.librafingergenerallibrary.util.BaseUtil;
import com.example.librafingergenerallibrary.util.RadixUtil;

/**
 * 指令包工具类
 * 作者：sean
 * 类描述：
 * 备注消息：
 * 修改时间：2017/10/31
 */

public class PacketUtil {
    /**
     * 计算校验和
     *
     * @param check
     * @return
     */
    public static byte[] doCheckSum(byte[] check) {
        short result = 0;
        for (int i = 0; i < check.length; i++) {
            int a = (int) check[i];
            a = a & 0xff;
            result += a;
        }
        //讲short转换为2个字节到数组
        byte[] b = new byte[2];
        for (int i = 0; i < 2; i++) {
            b[i] = (byte) (result >> 8 * (1 - i) & 0xFF);
        }
        return b;
    }


    /**
     * 检查pid
     *
     * @return
     */
    public static boolean checkPid(byte pid) {
        switch (pid) {
            case ZfmFingerConstants.FRAME_PID_ACK_PACKET:
            case ZfmFingerConstants.FRAME_PID_COMMAND_PACKET:
            case ZfmFingerConstants.FRAME_PID_DATA_PACKET:
            case ZfmFingerConstants.FRAME_PID_END_DATA_PACKET:
                return true;
            default:
                return false;
        }
    }

    public static byte[] getPacketByte(Packet packet) {
        if (packet == null) {
            return null;
        }
        int start = packet.getStart() != null ? packet.getStart().length : 0;
        int addr = packet.getAddr() != null ? packet.getAddr().length : 0;
        int pid = packet.getPid() != null ? packet.getPid().length : 0;
        int length = packet.getLength() != null ? packet.getLength().length : 0;
        int data = packet.getData() != null ? packet.getData().length : 0;
        int sum = packet.getSum() != null ? packet.getSum().length : 0;
        if (start == 0 || addr == 0 || pid == 0 || length == 0 || data == 0 || sum == 0) {
            return null;
        } else {
            int len = 0;
            byte[] bytes = new byte[start + addr + pid + length + data + sum];
            System.arraycopy(packet.getStart(), 0, bytes, len, start);
            len += start;
            System.arraycopy(packet.getAddr(), 0, bytes, len, addr);
            len += addr;
            System.arraycopy(packet.getPid(), 0, bytes, len, pid);
            len += pid;
            System.arraycopy(packet.getLength(), 0, bytes, len, length);
            len += length;
            System.arraycopy(packet.getData(), 0, bytes, len, data);
            len += data;
            System.arraycopy(packet.getSum(), 0, bytes, len, sum);
            return bytes;
        }
    }


    /**
     * 获取指令包
     */
    public static byte[] makeCommandPacket(byte[] cmd, byte[] data) {
//        if (cmd == null) {
//            return null;
//        }
        try {
            int packetLen = 12;
            int length = 3;
            if (data != null && data.length > 0) {
                packetLen = 12 + data.length;
            }

            byte[] packet = new byte[packetLen];

            //包头
            packet[0] = ZfmFingerConstants.FRAME_START[0];
            packet[1] = ZfmFingerConstants.FRAME_START[1];
            //地址
            packet[2] = ZfmFingerConstants.FRAME_DEFAULT_ADDR[0];
            packet[3] = ZfmFingerConstants.FRAME_DEFAULT_ADDR[1];
            packet[4] = ZfmFingerConstants.FRAME_DEFAULT_ADDR[2];
            packet[5] = ZfmFingerConstants.FRAME_DEFAULT_ADDR[3];
            //包标识
            packet[6] = ZfmFingerConstants.FRAME_PID_COMMAND_PACKET;

            //包长度
            if (data != null && data.length > 0) {
                length += data.length;
            }
            packet[7] = RadixUtil.shortToByte((short) length)[1];
            packet[8] = RadixUtil.shortToByte((short) length)[0];

            //指令码
            packet[9] = cmd[0];

            //额外数据
            if (data != null && data.length > 0) {
                System.arraycopy(data, 0, packet, 10, data.length);
            }

            byte[] doSum = new byte[packetLen - 8];
            System.arraycopy(packet, 6, doSum, 0, doSum.length);
            byte[] cks = doCheckSum(doSum);
            packet[packetLen - 2] = cks[0];
            packet[packetLen - 1] = cks[1];

            return packet;
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            return null;
        }
    }

    public static byte[] makeDataPacket(byte flag, byte[] data) {
//        if (cmd == null) {
//            return null;
//        }
        try {
            int packetLen = 12;
            int length = 3;
            if (data != null && data.length > 0) {
                packetLen = 12 + data.length;
            }

            byte[] packet = new byte[packetLen];

            //包头
            packet[0] = ZfmFingerConstants.FRAME_START[0];
            packet[1] = ZfmFingerConstants.FRAME_START[1];
            //地址
            packet[2] = ZfmFingerConstants.FRAME_DEFAULT_ADDR[0];
            packet[3] = ZfmFingerConstants.FRAME_DEFAULT_ADDR[1];
            packet[4] = ZfmFingerConstants.FRAME_DEFAULT_ADDR[2];
            packet[5] = ZfmFingerConstants.FRAME_DEFAULT_ADDR[3];
            //包标识
            packet[6] = flag;

            //包长度
            if (data != null && data.length > 0) {
                length += data.length;
            }
            packet[7] = RadixUtil.shortToByte((short) length)[1];
            packet[8] = RadixUtil.shortToByte((short) length)[0];

            //额外数据
            if (data != null && data.length > 0) {
                System.arraycopy(data, 0, packet, 9, data.length);
            }

            byte[] doSum = new byte[packetLen - 8];
            System.arraycopy(packet, 6, doSum, 0, doSum.length);
            byte[] cks = doCheckSum(doSum);
            packet[packetLen - 2] = cks[0];
            packet[packetLen - 1] = cks[1];

            return packet;
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            return null;
        }
    }


//
//
//    /**
//     * 获取指令包
//     */
//    public static byte[] getCommandPacket(byte[] cmd,byte[] data) {
//        if (cmd==null||data==null){
//            return null;
//        }
//        try {
//            int packetLen = ZfmFingerConstants.CMD_FIRST_LEN+ZfmFingerConstants.CKS_LEN+data.length;
//            int length = 3;
//
//
//            if(data!=null){
//                packetLen+=data.length;
//            }
//
//            byte[] packet = new byte[packetLen];
//
//            //包头
//            packet[0] = ZfmFingerConstants.FRAME_START[0];
//            packet[1] = ZfmFingerConstants.FRAME_START[1];
//            //地址
//            packet[2] = ZfmFingerConstants.FRAME_DEFAULT_ADDR[0];
//            packet[3] = ZfmFingerConstants.FRAME_DEFAULT_ADDR[1];
//            packet[4] = ZfmFingerConstants.FRAME_DEFAULT_ADDR[2];
//            packet[5] = ZfmFingerConstants.FRAME_DEFAULT_ADDR[3];
//            //包标识
//            packet[6] = ZfmFingerConstants.FRAME_PID_COMMAND_PACKET;
//
//            //包长度
//            if (data!=null){
//                length +=data.length;
//            }
//            packet[7]  = FingerUtil.shortToByte((short) length)[0];
//            packet[8]  =FingerUtil.shortToByte((short) length)[1];
//
//            //指令码
//            packet[9]  =cmd[0];
//
//            //额外数据
//            if(data!=null){
//                System.arraycopy(data,0,packet,10,data.length);
//            }
//
//            byte[] cks = FingerUtil.checkSum(packet, packetLen - ZfmFingerConstants.CKS_LEN);
//            packet[packetLen - 2] = cks[0];
//            packet[packetLen - 1] = cks[1];
//
//            return packet;
//        } catch (Exception e) {
//     Util.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
//            return null;
//        }
//    }
//
//    /**
//     * 判断包类型
//     * @param data
//     * @return
//     */
//    public static byte getPacketTag(byte[] data){
//        if (data==null){
//            return  0;
//        }else if(data.length<7){
//            return 0;
//        }else if(data[7]==ZfmFingerConstants.FRAME_PID_COMMAND_PACKET){
//            return ZfmFingerConstants.FRAME_PID_COMMAND_PACKET;
//        }else if(data[7]==ZfmFingerConstants.FRAME_PID_DATA_PACKET){
//            return ZfmFingerConstants.FRAME_PID_DATA_PACKET;
//        }else if(data[7]==ZfmFingerConstants.FRAME_PID_ACK_PACKET){
//            return ZfmFingerConstants.FRAME_PID_ACK_PACKET;
//        }else if(data[7]==ZfmFingerConstants.FRAME_PID_END_DATA_PACKET){
//            return ZfmFingerConstants.FRAME_PID_END_DATA_PACKET;
//        }else {
//            return 0;
//        }
//    }
//    /**
//     * 判断是否是应答包
//     * @param data
//     * @return
//     */
//    public static boolean isAckPacket(byte[] data){
//        if (data==null){
//            return  false;
//        }else if(data.length<7){
//            return false;
//        }else if(data[7]==ZfmFingerConstants.FRAME_PID_ACK_PACKET){
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    /**
//     * 判断是否是结束包
//     * @param data
//     * @return
//     */
//    public static boolean isEndDataPacket(byte[] data){
//        if (data==null){
//            return  false;
//        }else if(data.length<7){
//            return false;
//        }else if(data[7]==ZfmFingerConstants.FRAME_PID_END_DATA_PACKET){
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    /**
//     * 将字节数组转化为packet
//     */
//
//    public static Packet  getPacket(byte[] data){
//        if(data==null){
//            return null;
//        }else if(data.length<=7){
//            return null;
//        }else if(data.length<=11){
//            return  null;
//        }else {
//            Packet packet = new Packet();
//            int len = data.length;
//            packet.setStart(new byte[]{data[0],data[1]});
//            packet.setAddr(new byte[]{data[2],data[3],data[4],data[5]});
//            packet.setPid(new byte[]{data[6]});
//
//            packet.setLength(new byte[]{data[7]});
//            packet.setSum(new byte[]{data[len-2],data[len-1]});
//
//            byte[] mData = new byte[data.length-11];
//            for (int i = 0; i < len-2; i++) {
//                mData[i] = data[len-7];
//            }
//            packet.setData(mData);
//            return packet;
//        }
//
//    }

}
