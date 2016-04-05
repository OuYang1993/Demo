package com.ouyang.databinding;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.ouyang.databinding.activity.MainActivity;

import java.io.PrintWriter;

import static java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by OuYang on 2015/11/10.
 * 崩溃捕获
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private Context context;
    private final String TAG = CrashHandler.class.getSimpleName();


    public CrashHandler(Context context) {
        this.context = context;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(ex);
    }

    /**
     * 自定义异常处理:收集错误信息&发送错误报告
     *
     * @param ex
     * @return true:处理了该异常信息;否则返回false
     */
    private void handleException(final Throwable ex) {
        if (ex == null || context == null) {

            System.out.println("null   ======================");
            return;
        }
//        saveToSDCard(ex);

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                showAlertDialog();
                Looper.loop();

            }
        }.start();
    }



    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.theme_dialog_light);
        builder.setMessage("由于发生了一个未知错误，应用已关闭，我们对此引起的不便表示抱歉您可以将错误信息上传到我们的服务器，帮助我们尽快解决该问题，谢谢！");
        builder.setTitle("抱歉");
        builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
//
//    private void saveToSDCard(Throwable ex) {
//        try {
//            boolean append = false;
//            File directory = Environment.getExternalStorageDirectory();
//            String packageName = context.getPackageName();
//            String path = directory.getAbsolutePath();
//
//
//            File logDirectory = new File(path + "/" + packageName);
//            LogUtil.e(TAG, "logDirectory path : " + logDirectory.getAbsolutePath());
//            if (!logDirectory.exists()) {//如果文件夹不存在,创建文件夹
//                boolean success = logDirectory.mkdir();
//                if (success) {
//                    LogUtil.e(TAG, "created file path: " + logDirectory.getAbsolutePath());
//                }
//            } else if (!logDirectory.isDirectory()) {
//                //如果存在同名文件,判断是不是文件夹,如果不是文件夹,则先删除,再创建
//                boolean delete = logDirectory.delete();
//                if (delete) {
//                    boolean success = logDirectory.mkdirs();
//                    if (success) {
//                        LogUtil.e(TAG, "deleted  and created file path: " + logDirectory.getAbsolutePath());
//                    }
//                }
//            }
//            SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.CHINA);
//            String fileName = format.format(new Date());
//            fileName = logDirectory + "/" + fileName + "_" + Build.MODEL + ".log";
//            File file = new File(fileName);
//
//            if (System.currentTimeMillis() - file.lastModified() > 5000) {
//                append = true;
//            }
//            LogUtil.e(TAG, "log file path : " + file.getAbsolutePath());
//            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
//            // 导出发生异常的时间
//            pw.println(new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA).format(new Date()));
//            // 导出手机信息
//            dumpPhoneInfo(pw);
//            pw.println();
//            // 导出异常的调用栈信息
//            ex.printStackTrace(pw);
//            pw.println();
//            pw.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        // 应用的版本名称和版本号
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.println("App Version: ");
        pw.print("versionName:" + pi.versionName);
        pw.print('\t');
        pw.println("versionCode: " + pi.versionCode);
        pw.println();

        // android版本号
        pw.println("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("\t");
        pw.println("api int:" + Build.VERSION.SDK_INT);
        pw.println();

        // 手机制造商
        pw.println("Vendor: ");
        pw.println(Build.MANUFACTURER);
        pw.println();

        // 手机型号
        pw.println("Model: ");
        pw.println(Build.MODEL);
        pw.println();

        // cpu架构
        pw.println("CPU ABI: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String[] abis = Build.SUPPORTED_ABIS;
            StringBuilder builder = new StringBuilder();
            for (String abi : abis) {
                builder.append(abi);
                builder.append("\t");
            }
            pw.println(builder.toString());
        } else {
            pw.println(Build.CPU_ABI);
        }
        pw.println();
    }

}
