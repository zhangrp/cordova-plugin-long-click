/**
 * $Id: LongClickPlugin.java,v 1.0 17/1/13 下午2:08 zhangruiping Exp $
 */
package com.hh.cordova.plugin;

import android.content.Context;
import android.util.Log;
import android.view.View;
import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author zhangruiping
 * @version $Id: LongClickPlugin.java,v 1.1 17/1/13 下午2:08 zhangruiping Exp $
 *          Created on 17/1/13 下午2:08
 */
public class LongClickPlugin extends CordovaPlugin {

    static String TAG = "LongClickPlugin";

    private String doJavascript = null;


    public LongClickPlugin() {
    }

    public LongClickPlugin(Context context) {
        new CustomConfigXmlParser().parse(context);
    }

    public LongClickPlugin(XmlPullParser xmlParser) {
        new CustomConfigXmlParser().parse(xmlParser);
    }

    @Override
    public void initialize(CordovaInterface cordova, final CordovaWebView cordovaWebView) {
        Log.i(TAG, "LongClickPlugin init......");
        cordovaWebView.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View arg0) {
                try {
                    Log.d(TAG, "LongClickPlugin");
                    if (doJavascript != null && !"".equals(doJavascript)) {
                        Log.i(TAG, "LongClickPlugin doJavascript [" + doJavascript + "]");
                        cordovaWebView.loadUrl("javascript:" + doJavascript);
                        Log.i(TAG, "LongClickPlugin doJavascript [" + doJavascript + "] success!");
                    } else {
                        Log.w(TAG, "LongClickPlugin doJavascript is null.");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "LongClickPlugin doJavascript [" + doJavascript + "] error!");
                }
                return true;
            }
        });
    }


    @Override
    public void pluginInitialize() {
        if (doJavascript == null) {
            new CustomConfigXmlParser().parse(webView.getContext());
        }
    }

    private class CustomConfigXmlParser extends ConfigXmlParser {
        @Override
        public void handleStartTag(XmlPullParser xml) {
            String strNode = xml.getName();
            if (strNode.equals("preference")) {
                String name = xml.getAttributeValue(null, "name");
                String value = xml.getAttributeValue(null, "value");
                if (name != null && name.equals("longClickDoScript")) {
                    doJavascript = value;
                }
            }
        }

        @Override
        public void handleEndTag(XmlPullParser xml) {
        }
    }
}