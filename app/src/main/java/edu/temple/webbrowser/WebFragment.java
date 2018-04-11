package edu.temple.webbrowser;
//Sean McNamara
//This is just what are 'tabs' are going to be, they will be a simple web view
//more import stuffffffffff
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebFragment extends Fragment {

    //WebView object
    WebView web_view;
    //String to hold url
    String url;

    public WebFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web, container, false);
        web_view = (WebView) v.findViewById(R.id.web_view);

        WebSettings webSettings = web_view.getSettings();
        webSettings.setJavaScriptEnabled(true);

        web_view.setWebViewClient(new WebViewClient());
        //As long as there is a url we want to load it
        if (url != null) {
            web_view.loadUrl(url);
        }

        return v;
    }

    //A function that literally just sets our view to show the page requested by url
    public void set_url(String url) {
        //Set this url to the one inputed as the parameter
        this.url = url;
        //load the url to view
        web_view.loadUrl(url);
    }

}
