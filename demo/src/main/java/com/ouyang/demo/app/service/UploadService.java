package com.ouyang.demo.app.service;

import android.app.IntentService;
import android.content.Intent;

import java.io.File;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class UploadService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_UPLOAD = "com.ouyang.demo.app.service.action.UPLOAD";
    public static final String ACTION_BAZ = "com.ouyang.demo.app.service.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.ouyang.demo.app.service.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.ouyang.demo.app.service.extra.PARAM2";

    public UploadService() {
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPLOAD.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionUpload();
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpload() {
        try {
            Thread.sleep(15 * 1000);
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAA");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
