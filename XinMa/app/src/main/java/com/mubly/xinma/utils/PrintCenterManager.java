package com.mubly.xinma.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dothantech.lpapi.LPAPI;
import com.dothantech.printer.IDzPrinter;
import com.mubly.xinma.base.CrossApp;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.model.PrintContentBean;
import com.mubly.xinma.model.PrinterData;
import com.mubly.xinma.model.TemplateBean;


import java.util.List;

import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;
import static com.dothantech.lpapi.IAtBitmap.DrawParamName.FONT_NAME;

/**
 * 打印中心管理类
 *
 * @author zhouhongmin
 * @date 2019-06-18
 */
public class PrintCenterManager {

    private static volatile PrintCenterManager mInstance;

    Context mApplicationContext;

    private final LPAPI.Callback mCallback = new LPAPI.Callback() {

        /****************************************************************************************************************************************/
        // 所有回调函数都是在打印线程中被调用，因此如果需要刷新界面，需要发送消息给界面主线程，以避免互斥等繁琐操作。

        /****************************************************************************************************************************************/

        // 打印机连接状态发生变化时被调用
        @Override
        public void onStateChange(IDzPrinter.PrinterAddress arg0, IDzPrinter.PrinterState arg1) {
            final IDzPrinter.PrinterAddress printer = arg0;
            switch (arg1) {
                case Connected:
                case Connected2:
                    if (null != connectIngPrint) {
                        mPrinterAddress = connectIngPrint;
                    }
                    PrinterData.saveCurrentPrint(mPrinterAddress);
                    LiveDataBus.get().with("printConnect").postValue(true);
                    break;

                case Disconnected:
                    LiveDataBus.get().with("printConnect").postValue(false);
                    break;

                default:
                    break;
            }
        }

        // 蓝牙适配器状态发生变化时被调用
        @Override
        public void onProgressInfo(IDzPrinter.ProgressInfo arg0, Object arg1) {
        }

        @Override
        public void onPrinterDiscovery(IDzPrinter.PrinterAddress arg0, IDzPrinter.PrinterInfo arg1) {
        }

        // 打印标签的进度发生变化是被调用
        @Override
        public void onPrintProgress(IDzPrinter.PrinterAddress address, Object bitmapData, IDzPrinter.PrintProgress progress, Object addiInfo) {
            switch (progress) {
                case Success:
                    LiveDataBus.get().with("printing").postValue(true);
                    break;
                case Failed:
                    LiveDataBus.get().with("printing").postValue(false);
                    break;
                default:
                    break;
            }
        }
    };
    private LPAPI api;
    // 上次连接成功的设备对象
    private IDzPrinter.PrinterAddress mPrinterAddress = null;
    private IDzPrinter.PrinterAddress connectIngPrint = null;

    private PrintCenterManager() {
        mApplicationContext = CrossApp.get();
        this.api = LPAPI.Factory.createInstance(mCallback);
        mPrinterAddress = PrinterData.getCurrentPrint();
        // 尝试连接上次成功连接的打印机
        if (mPrinterAddress != null) {
            if (api.openPrinterByAddress(mPrinterAddress)) {
                // 连接打印机的请求提交成功，刷新界面提示
                onPrinterConnecting(mPrinterAddress, false);
                return;
            }
        }
    }

    public static PrintCenterManager getInstance() {
        if (mInstance == null) {
            synchronized (PrintCenterManager.class) {
                if (mInstance == null) {
                    mInstance = new PrintCenterManager();
                }
            }
        }
        return mInstance;
    }

    public void connectingPrint(IDzPrinter.PrinterAddress printer, CallBack callBack) {
        if (api.openPrinterByAddress(printer)) {
            this.connectIngPrint = printer;
            callBack.callBack(null);
            return;
        }
    }

    // 连接打印机请求成功提交时操作
    private void onPrinterConnecting(IDzPrinter.PrinterAddress printer, boolean showDialog) {
        CommUtil.ToastU.showToast("打印机连接成功");
    }

    public IDzPrinter.PrinterAddress getCurrentPrint() {
        return mPrinterAddress;
    }

    public void setmCurrentPrint(IDzPrinter.PrinterAddress mPrinterAddress) {
        this.mPrinterAddress = mPrinterAddress;
    }

    public List<IDzPrinter.PrinterAddress> getAllPrint() {
        return api.getAllPrinterAddresses(null);
    }

    // 判断当前打印机是否连接
    public boolean isPrinterConnected() {
        // 调用LPAPI对象的getPrinterState方法获取当前打印机的连接状态
        IDzPrinter.PrinterState state = api.getPrinterState();

        // 打印机未连接
        if (state == null || state.equals(IDzPrinter.PrinterState.Disconnected)) {
//            CommUtil.ToastU.showToast("打印机未连接，请先连接打印机！");
            return false;
        }

        // 打印机正在连接
        if (state.equals(IDzPrinter.PrinterState.Connecting)) {
            CommUtil.ToastU.showToast("正在连接打印机，请稍候！");
            return false;
        }
        // 打印机已连接
        return true;
    }

    private int fontStyle = 0;  //字体
    private double fontHeight = 0;  //字体

    public void prnit(TemplateBean prnitBean) {
        if (null == prnitBean) return;
        api.startJob(prnitBean.getWidth(), prnitBean.getHeight(), prnitBean.getOrientation());
        if (TextUtils.isEmpty(prnitBean.getPrintArray())) return;
        PrintContentBean printContent = JSON.parseObject(prnitBean.getPrintArray(), PrintContentBean.class);

        List<PrintContentBean.TextBean> texts = printContent.getText();
        if (printContent.getText() != null) {
            for (PrintContentBean.TextBean textBean : texts) {
                if (!TextUtils.isEmpty(textBean.getOrientation()))
                    api.setItemOrientation(Integer.parseInt(textBean.getOrientation()));  //设置旋转角度
                if (!TextUtils.isEmpty(textBean.getHorizontalAlignment()))
                    api.setItemHorizontalAlignment(Integer.parseInt(textBean.getHorizontalAlignment())); //对齐方式   水平
                if (!TextUtils.isEmpty(textBean.getVerticalAlignment()))
                    api.setItemVerticalAlignment(Integer.parseInt(textBean.getVerticalAlignment())); //对齐方式  垂直
//				绘制文字
                if (!TextUtils.isEmpty(textBean.getFontName()))   //设置字体  如果没有打印机会使用上一次设置的
                    api.setDrawParam(FONT_NAME, textBean.getFontName());
                if (!TextUtils.isEmpty(textBean.getFontStyle()))   //字体风格
                    fontStyle = Integer.parseInt(textBean.getFontStyle());
                if (!TextUtils.isEmpty(textBean.getFontHeight()))    //字体高
                    fontHeight = Double.parseDouble(textBean.getFontHeight());

                api.drawTextRegular(textBean.getContent(), Double.parseDouble(textBean.getX()),
                        Double.parseDouble(textBean.getY()), Double.parseDouble(textBean.getWidth()),
                        Double.parseDouble(textBean.getHeight()), fontHeight,
                        fontStyle, 1);
            }
        }
        //二维码
        List<PrintContentBean.QRCodeBean> qrcodes = printContent.getQRCode();
        if (qrcodes != null) {
            for (PrintContentBean.QRCodeBean qrcodeBean : qrcodes) {
                api.draw2DQRCode(qrcodeBean.getContent(), Double.valueOf(qrcodeBean.getX()),
                        Double.valueOf(qrcodeBean.getY()), Double.valueOf(qrcodeBean.getWidth()));
            }
        }
        //直线
        List<PrintContentBean.LineBean> lines = printContent.getLine();
        if (lines != null) {
            for (PrintContentBean.LineBean lineBean : lines) {
                api.drawLine(Double.valueOf(lineBean.getX1()), Double.valueOf(lineBean.getY1()),
                        Double.valueOf(lineBean.getX2()), Double.valueOf(lineBean.getY2())
                        , Double.valueOf(lineBean.getLineWidth()));
            }
        }
        //框
        List<PrintContentBean.RectangleBean> rectangles = printContent.getRectangle();
        if (rectangles != null) {
            for (PrintContentBean.RectangleBean rectangleBean : rectangles) {
                api.drawRectangle(Double.valueOf(rectangleBean.getX()), Double.valueOf(rectangleBean.getY()),
                        Double.valueOf(rectangleBean.getWidth()), Double.valueOf(rectangleBean.getHeight())
                        , Double.valueOf(rectangleBean.getLineWidth()));
            }
        }
        api.commitJob();
    }

    public List<Bitmap> getPages() {
        return api.getJobPages();
    }
}
